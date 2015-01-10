package com.rosten.app.bargain

import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User
import com.rosten.app.system.Depart
import com.rosten.app.system.Attachment
import com.rosten.app.base.ContactCorp

import com.rosten.app.share.ShareService
import com.rosten.app.gtask.GtaskService
import com.rosten.app.gtask.Gtask
import com.rosten.app.workflow.FlowBusiness

class BargainController {

	def springSecurityService
	def bargainService
	def shareService
	def gtaskService
	
	private def bargainStatus = ["新增","已结束"]
	private def bargainType=["totalpackageBargain":"总包合同"]
	
	//合同<--start
	def bargainAdd ={
		if(params.flowCode){
			//需要走流程
			def company = Company.get(params.companyId)
			def flowBusiness = FlowBusiness.findByFlowCodeAndCompany(params.flowCode,company)
			if(flowBusiness && !"".equals(flowBusiness.relationFlow)){
				params.relationFlow = flowBusiness.relationFlow
			}else{
				//不存在流程引擎关联数据
				render '<h2 style="color:red;width:660px;margin:0 auto;margin-top:60px">当前业务不存在流程设置，无法创建，请联系管理员！</h2>'
				return
			}
		}
		redirect(action:"bargainShow",params:params)
	}
	def bargainShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = Bargain.get(params.id)
		}else{
			entity = new Bargain()
			entity.bargainMaker = currentUser.getFormattedName()
			entity.currentUser = currentUser
		}
		model["bargain"] = entity
		model["user"] = currentUser
		
		println params.type
		
		switch(params.type){
			case "totalpackageBargain":
			entity.bargainType = "总包合同"
			break;
			case "subpackageBargain":
			entity.bargainType = "分包合同"
			break;
			case "purchaseBargain":
			entity.bargainType = "采购合同"
			break;
			case "salesBargain":
			entity.bargainType = "销售合同"
			break;
		}
		
//		if(params.type.equals("total")){
//			entity.bargainType = "总包合同"
//		}
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		
		if(!currentUser.equals(entity.currentUser)){
			//当前登录用户不是当前处理人，则不允许修改相关信息
			fa.readOnly +=["bargainName","bargainType","bargainNo","bargainMoney","bargainMaker","bargainSignDate","bargainPayMemo","bargainVendor","bargainPurchaser"]
			model["isShowFile"] = false
		}else{
			model["isShowFile"] = true
		}
		
		//流程相关信息----------------------------------------------
		model["relationFlow"] = params.relationFlow
		model["flowCode"] = params.flowCode
		//------------------------------------------------------
		
		render(view:'/bargain/bargain',model:model)
	}
	def bargainSearchView ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		
		def dataList = Depart.findAllByCompany(currentUser.company,[sort: "serialNo", order: "desc"])
		model["departList"] = dataList
		
		//政治面貌
		model["statusList"] = this.bargainStatus
		
		render(view:'/bargain/bargainSearch',model:model)
	}
	def bargainSave ={
		def model=[:]
		
		def currentUser = springSecurityService.getCurrentUser()
		def company = Company.get(params.companyId)
		def entity = new Bargain()
		if(params.id && !"".equals(params.id)){
			entity = Bargain.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
		entity.bargainSignDate = Util.convertToTimestamp(params.bargainSignDate)
		
		
		if(entity.bargainGoods){
			def _list = []
			_list += entity.bargainGoods
			entity.bargainGoods.clear()
			_list.each{
				it.delete()
			}
		}
		
		//清单表保存
		if("采购合同".equals(params.bargainType)){
			JSON.parse(params.bargainGoodsValues).eachWithIndex{elem, i ->
				def bargainGoods = new BargainGoods(elem)
				bargainGoods.clearErrors()
				entity.addToBargainGoods(bargainGoods)
			}
		}
		
		//两个类字段保存
		if(params.barVendorCorpId){
			def bargainVendorCorpOBJ = ContactCorp.get(params.barVendorCorpId)
			if(bargainVendorCorpOBJ){
				entity.barVendorCorp = bargainVendorCorpOBJ
			}
		}
		
		if(params.barPurchaserCorpId){
			def bargainPurchaserCorpOBJ = ContactCorp.get(params.barPurchaserCorpId)
			if(bargainPurchaserCorpOBJ){
				entity.barPurchaserCorp = bargainPurchaserCorpOBJ
			}
		}
		
		//判断是否需要走流程
		def _status
		if(params.relationFlow){
			//需要走流程
			if(params.id){
				_status = "old"
			}else{
				_status = "new"
			}
			//流程引擎相关信息处理
			shareService.businessFlowSave(entity,currentUser,params.relationFlow)
		}
		
		if(entity.save(flush:true)){
			model["id"] = entity.id
			
			//流程引擎相关日志信息
			if("new".equals(_status)){
				//添加日志
				shareService.addFlowLog(entity.id,params.flowCode,currentUser,"新增合同申请信息")
			}
			
			//增加附件功能
			if(params.attachmentIds){
				params.attachmentIds.split(",").each{
					def attachment = Attachment.get(it)
					attachment.beUseId = entity.id
					attachment.save(flush:true)
				}
			}
			
			model["result"] = "true"

		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	
	//提交下一步操作
	def bargainFlowDeal ={
		def json=[:]
		
		def entity = Bargain.get(params.id)
		def currentUser = springSecurityService.getCurrentUser()
		def company = currentUser.company
		def frontStatus = entity.status
		def nextUserName=[]
		
		//流程引擎相关信息处理-------------------------------------------------------------------------------------
		def map =[:]
		if(params.conditionName){
			map[params.conditionName] = params.conditionValue
		}
		def nextInfor = shareService.businessFlowDeal(company,entity,params.dealUser,map,"bargainManage","bargainApply")
		//--------------------------------------------------------------------------------------------------
		
		//增加待办事项
		if(nextInfor.nextUser && nextInfor.nextUser.size()>0){
			def args = [:]
			args["type"] = "【合同管理】"
			args["content"] = "请您审核名称为  【" + entity.bargainName +  "】 的合同申请"
			args["contentStatus"] = nextInfor.nextStatus
			args["contentId"] = entity.id
			args["user"] = nextInfor.nextUser[0]
			args["company"] = company
			gtaskService.addGtask(args)
			
			nextUserName << nextInfor.nextUser[0].getFormattedName()
		}
		
		//判断下一处理人是否与当前处理人员为同一人
		if(currentUser.equals(entity.currentUser)){
			json["refresh"] = true
		}
		
		//修改代办事项状态
		def gtask = Gtask.findWhere(
			user:currentUser,
			company:company,
			contentId:entity.id,
			contentStatus:frontStatus,
			status:"0"
		)
		if(gtask!=null){
			gtask.dealDate = new Date()
			gtask.status = "1"
			gtask.save(flush:true)
		}
		
		if(entity.save(flush:true)){
			//添加日志
			def logContent
			switch (true){
				case entity.status.contains("已结束"):
					logContent = "结束流程"
					break
				case entity.status.contains("归档"):
					logContent = "归档"
					break
				case entity.status.contains("不同意"):
					logContent = "不同意！"
					break
				default:
					logContent = "提交" + entity.status + "【" + nextUserName.join("、") + "】"
					break
			}
			shareService.addFlowLog(entity.id,"bargainApply",currentUser,logContent)
			
			json["nextUserName"] = nextUserName.join("、")
			json["result"] = true
		}else{
			entity.errors.each{
				println it
			}
			json["result"] = false
		}
		render json as JSON
	}
	
	//退回功能
	def bargainFlowBack ={
		def json=[:]
		def entity = Bargain.get(params.id)
		
		def currentUser = springSecurityService.getCurrentUser()
		def company = currentUser.company
		def frontStatus = entity.status
		def nextUser
		
		try{
			
			def nextInfor = shareService.businessFlowBack(entity)
			if(nextInfor.nextUser){
				nextUser = nextInfor.nextUser
				//增加待办事项
				def args = [:]
				args["type"] = "【合同管理】"
				args["content"] = "名称为  【" + entity.bargainName +  "】 的合同申请被退回，请查看！"
				args["contentStatus"] = nextInfor.nextStatus
				args["contentId"] = entity.id
				args["user"] = nextUser
				args["company"] = company
				gtaskService.addGtask(args)
			}
			
			//修改代办事项状态
			def gtask = Gtask.findWhere(
				user:currentUser,
				company:company,
				contentId:entity.id,
				contentStatus:frontStatus,
				status:"0"
			)
			if(gtask!=null){
				gtask.dealDate = new Date()
				gtask.status = "1"
				gtask.save(flush:true)
			}
			
			entity.save(flush:true)
			
			//判断下一处理人是否与当前处理人员为同一人
			if(currentUser.equals(nextUser)){
				json["refresh"] = true
			}
			
			//添加日志
			def logContent = "退回【" + nextUser?.getFormattedName() + "】"
			shareService.addFlowLog(entity.id,"bargainApply",currentUser,logContent)
				
			json["result"] = true
			json["nextUserName"] = nextUser?.getFormattedName()
			
		}catch(Exception e){
			json["result"] = false
		}
		render json as JSON
	}
	def bargainDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = Bargain.get(it)
				if(entity){
					entity.delete(flush: true)
				}
			}
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
	def bargainGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = bargainService.getBargainListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		
		if(params.bargainNo && !"".equals(params.bargainNo)) searchArgs["bargainNo"] = params.bargainNo
		if(params.bargainName && !"".equals(params.bargainName)) searchArgs["bargainName"] = params.bargainName
		if(params.departName && !"".equals(params.departName)) searchArgs["currentDepart"] = params.departName
		if(params.type && !"".equals(params.type)){
			switch (params.type){
				case "totalpackageBargain":
					searchArgs["bargainType"] = this.bargainType[params.type]
					break;
			}
		}else{
			searchArgs["bargainType"] = params.type
		}
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			model["gridData"] = bargainService.getBargainListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = bargainService.getBargainCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//采购货物明细start
	def bargainGoodsAdd ={
		redirect(action:"bargainGoodsShow",params:params)
	}
	def bargainGoodsShow ={
		def model =[:]
		if(params.id){
			model["bargainGoods"] = BargainGoods.get(params.id)
		}else{
			model["bargainGoods"] = new BargainGoods()
		}
		render(view:'/bargain/bargainGoods',model:model)
	}
	def bargainGoodsGrid ={
		def json=[:]
		def bargain = Bargain.get(params.id)
			
		if(params.refreshHeader){
			json["gridHeader"] = bargainService.getBargainGoodsListLayout()
		}
		//2014-9-1 增加搜索功能
		def searchArgs =[:]
		if(params.refreshData){
			if(!bargain){
				json["gridData"] = ["identifier":"id","label":"name","items":[]]
			}else{
				def args =[:]
				int perPageNum = Util.str2int(params.perPageNum)
				int nowPage =  Util.str2int(params.showPageNum)
				
				args["offset"] = (nowPage-1) * perPageNum
				args["max"] = perPageNum
				args["bargain"] = bargain
				
				def gridData = bargainService.getBargainGoodsListDataStore(args,searchArgs)
				json["gridData"] = gridData
			}
		}
		if(params.refreshPageControl){
			if(!bargain){
				json["pageControl"] = ["total":"0"]
			}else{
				def total = bargainService.getBargainGoodsCount(bargain,searchArgs)
				json["pageControl"] = ["total":total.toString()]
			}
			
		}
		render json as JSON
	}
	
	//采购货物明细end
	

}

