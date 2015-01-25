package com.rosten.app.project
import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User
import com.rosten.app.system.Depart
import com.rosten.app.base.ContactCorp

import com.rosten.app.gtask.GtaskService
import com.rosten.app.gtask.Gtask
import com.rosten.app.share.ShareService
import com.rosten.app.workflow.FlowBusiness
import com.rosten.app.system.Attachment

class ProjectController {

    def springSecurityService
	def projectService
	def shareService
	def gtaskService
	
	//获取项目列表（合同表单“项目名称”字段选择数据）
	def getProjectSelect ={
		def _List =[]
		def company = Company.get(params.companyId)
		ProjectManage.findAllByCompany(company).each{
			def json=[:]
			json["id"] = it.id
			json["name"] = it.projName
			json["projNo"] = it.projNo
//			json["cCorpLeaderDuty"] = it.cCorpLeaderDuty
//			json["contactCorpPhone"] = it.contactCorpPhone
//			json["contactCorpPost"] = it.contactCorpPost
//			json["contactCorpAddr"] = it.contactCorpAddr
			_List << json
		}
		render _List as JSON
	}
	
	//项目管理
	def projectManageAdd ={
		redirect(action:"projectManageShow",params:params)
	}
	def projectManageShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ProjectManage.get(params.id)
		}else{
			entity = new ProjectManage()
		}
		model["projectManage"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/project/projectManage',model:model)
	}
	def projectManageSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ProjectManage()
		if(params.id && !"".equals(params.id)){
			entity = ProjectManage.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
		entity.projEndDate = Util.convertToTimestamp(params.projectEndDate)
		entity.projStartDate = Util.convertToTimestamp(params.projectStartDate)
		
		if(params.supCorpId){
			def suppCorpOBJ = ContactCorp.get(params.supCorpId)
			if(suppCorpOBJ){
				entity.supCorp = suppCorpOBJ
			}
		}
		if(params.constCorpId){
			def constCorpOBJ = ContactCorp.get(params.constCorpId)
			if(constCorpOBJ){
				entity.constCorp = constCorpOBJ
			}
		}
		
		if(entity.save(flush:true)){
			model["id"] = entity.id
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def projectManageDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ProjectManage.get(it)
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
	def projectManageGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = projectService.getProjectManageListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		if(params.projNo && !"".equals(params.projNo)) searchArgs["projNo"] = params.projNo
		if(params.projName && !"".equals(params.projName)) searchArgs["projName"] = params.projName
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			model["gridData"] = projectService.getProjectManageListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = projectService.getProjectManageCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	def projectManageSearchView ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def company= currentUser.company
		model["company"] = company
		//往来单位类型
		//model["contactCropTypeList"] = shareService.getSystemCodeItems(company,"rs_contactCropType")
		render(view:'/project/projectManageSearch',model:model)
	}
	//项目管理end-->
	
	
	//项目计划start-------------------------------------------
	def projectPlanAdd ={
		redirect(action:"projectPlanShow",params:params)
	}
	def projectPlanShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ProjectPlan.get(params.id)
		}else{
			entity = new ProjectPlan()
		}
		model["projectPlan"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/project/projectPlan',model:model)
	}
	def projectPlanSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ProjectPlan()
		if(params.id && !"".equals(params.id)){
			entity = ProjectPlan.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
		entity.consPlanDate = Util.convertToTimestamp(params.consPlanDate)
		entity.consTrueDate = Util.convertToTimestamp(params.consTrueDate)
		
		if(params.projectBelongId){
			def OBJ = ProjectManage.get(params.projectBelongId)
			if(OBJ){
				entity.projectBelong = OBJ
			}
		}
		
		if(entity.constructLogs){
			def _list = []
			_list += entity.constructLogs
			entity.constructLogs.clear()
			_list.each{
				it.delete()
			}
		}
		
		JSON.parse(params.constructLogsValues).eachWithIndex{elem, i ->
			def constructLog = new ConstructLog(elem)
			constructLog.clearErrors()
			constructLog.constructDate = Util.convertToTimestamp(elem.getFormatteConstructDate)
			
			entity.addToConstructLogs(constructLog)
		}
		
		
		if(entity.save(flush:true)){
			model["id"] = entity.id
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def projectPlanDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ProjectPlan.get(it)
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
	def projectPlanGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = projectService.getProjectPlanListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			model["gridData"] = projectService.getProjectPlanListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = projectService.getProjectPlanCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//项目计划end-->
	

	
	//施工方案start
	def constructApproveAdd ={
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
		redirect(action:"constructApproveShow",params:params)
	}
	def constructApproveShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ConstructApprove.get(params.id)
		}else{
			entity = new ConstructApprove()
		}
		model["constructApprove"] = entity
		model["user"] = currentUser
		
		
		if(!currentUser.equals(entity.currentUser)){
			//当前登录用户不是当前处理人，则不允许修改相关信息
			//fa.readOnly +=["bargainName","bargainType","bargainNo","bargainMoney","bargainMaker","bargainSignDate","bargainPayMemo","bargainVendor","bargainPurchaser"]
			model["isShowFile"] = false
		}else{
			model["isShowFile"] = true
		}
		
		//流程相关信息----------------------------------------------
		model["relationFlow"] = params.relationFlow
		model["flowCode"] = params.flowCode
		//------------------------------------------------------
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/project/constructApprove',model:model)
	}
	def constructApproveSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ConstructApprove()
		if(params.id && !"".equals(params.id)){
			entity = ConstructApprove.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
//		entity.projStartDate = Util.convertToTimestamp(params.projectStartDate)
		if(params.projectBelongId){
			def OBJ = ProjectManage.get(params.projectBelongId)
			if(OBJ){
				entity.projectBelong = OBJ
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
	def constructApproveDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ConstructApprove.get(it)
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
	def constructApproveGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = projectService.getConstructApproveListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			model["gridData"] = projectService.getConstructApproveListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = projectService.getConstructApproveCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//施工方案end-->
	
	
	
	//施工日志start
	def constructLogAdd ={
		redirect(action:"constructLogShow",params:params)
	}
	
	def constructLogShow ={
		def model =[:]
		if(params.id){
			model["constructLog"] = ConstructLog.get(params.id)
		}else{
			def constructLog = new ConstructLog()
			constructLog.projectPlan = ProjectPlan.get(params.planId)
			
			model["constructLog"] = constructLog
		}
		render(view:'/project/constructLog',model:model)
	}
	
	
	def constructLogGrid ={
		def json=[:]
		def projectPlan = ProjectPlan.get(params.id)
		if(params.refreshHeader){
			json["gridHeader"] = projectService.getConstructLogListLayout()
		}
		
		//2014-9-1 增加搜索功能
		def searchArgs =[:]
		if(params.refreshData){
			if(!projectPlan){
				json["gridData"] = ["identifier":"id","label":"name","items":[]]
			}else{
				def args =[:]
				int perPageNum = Util.str2int(params.perPageNum)
				int nowPage =  Util.str2int(params.showPageNum)
				
				args["offset"] = (nowPage-1) * perPageNum
				args["max"] = perPageNum
				args["projectPlan"] = projectPlan
				
				def gridData = projectService.getConstructLogListDataStore(args,searchArgs)
				json["gridData"] = gridData
			}
		}
		
		
		if(params.refreshPageControl){
			if(!projectPlan){
				json["pageControl"] = ["total":"0"]
			}else{
				def total = projectService.getConstructLogCount(projectPlan,searchArgs)
				json["pageControl"] = ["total":total.toString()]
			}
		}
		render json as JSON
	}
	
	//施工日志end-->
	
	
	
	
	
	
}
