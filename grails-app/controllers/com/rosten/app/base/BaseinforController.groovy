package com.rosten.app.base

import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User

import com.rosten.app.share.ShareService

class BaseinforController {
	def springSecurityService
	def baseinforService
	def shareService
	
	//获取往来单位信息列表数据
	def getContactCorpSelect ={
		def _List =[]
		def company = Company.get(params.companyId)
		ContactCorp.findAllByCompany(company).each{
			def json=[:]
			json["id"] = it.id
			json["name"] = it.contactCorpName
			json["contCorpLeader"] = it.contCorpLeader
			json["cCorpLeaderDuty"] = it.cCorpLeaderDuty
			json["contactCorpPhone"] = it.contactCorpPhone
			json["contactCorpPost"] = it.contactCorpPost
			json["contactCorpAddr"] = it.contactCorpAddr
			_List << json
		}
		render _List as JSON
	}
	
	//获取银行账号列表数据
	def getBankInforSelect ={
		def _List =[]
		def company = Company.get(params.companyId)
		BankInfor.findAllByCompany(company).each{
			def json=[:]
			json["id"] = it.id
			json["name"] = it.accountName
			_List << json
		}
		render _List as JSON
	}
	
	//
	def contactCorpGet ={
		
		
		println "params.corpId"
		println params.corpId
		//if(params.id){
			//402880e4-4a46b210-014a-46bdce0d-0002
			//entity = ContactCorp.get(params.corpId)
		def entity = ContactCorp.findAllWhere(contactCorpName:params.corpId)
		if(entity){
			println "find nothing"
		}
		
		jsonStr= [contactCorpVendor:entity]
		
		
		render jsonStr as JSON
		
	}
	
	
	//1.公司基本信息
	def companyInforAdd ={
		redirect(action:"companyInforShow",params:params)
	}
	def companyInforShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = CompanyInfor.get(params.id)
		}else{
			entity = new CompanyInfor()
		}
		model["companyInfor"] = entity
		model["user"] = currentUser 
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/companyInfor',model:model)
	}
	def companyInforSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new CompanyInfor()
		if(params.id && !"".equals(params.id)){
			entity = CompanyInfor.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		if(entity.save(flush:true)){
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def companyInforDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = CompanyInfor.get(it)
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
	def companyInforGrid ={
		
		
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getCompanyInforListLayout()
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
			model["gridData"] = baseinforService.getCompanyInforListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = baseinforService.getCompanyInforCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	
	
	//2014-11-22 xkf-----银行账号信息-------------------------------------------------------------------
	def bankInforAdd ={
		redirect(action:"bankInforShow",params:params)
	}
	def bankInforShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = BankInfor.get(params.id)
		}else{
			entity = new BankInfor()
		}
		model["bankInfor"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/bankInfor',model:model)
	}
	def bankInforSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new BankInfor()
		if(params.id && !"".equals(params.id)){
			entity = BankInfor.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		if(entity.save(flush:true)){
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def bankInforDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = BankInfor.get(it)
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
	def bankInforGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getBankInforListLayout()
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
			model["gridData"] = baseinforService.getBankInforListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = baseinforService.getBankInforCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	//--------------------------------------------------------------------------------------------------
	
	//2014-11-25 xkf-----往来单位信息-------------------------------------------------------------------
	def contactCorpAdd ={
		redirect(action:"ContactCorpShow",params:params)
	}

	def contactCorpShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def company = Company.get(params.companyId)
		model["company"] = company
		
		def entity
		if(params.id){
			entity = ContactCorp.get(params.id)
		}else{
			entity = new ContactCorp()
		}
		model["ContactCorp"] = entity
		model["user"] = currentUser
		
		//往来单位类型
		model["contactCropTypeList"] = shareService.getSystemCodeItems(company,"rs_contactCropType")
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/ContactCorp',model:model)
	}
	
	def contactCorpSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ContactCorp()
		if(params.id && !"".equals(params.id)){
			entity = ContactCorp.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		if(entity.save(flush:true)){
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def contactCorpDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ContactCorp.get(it)
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
	def contactCorpGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getContactCorpListLayout()
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
			model["gridData"] = baseinforService.getContactCorpListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = baseinforService.getContactCorpCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	//--------------------------------------------------------------------------------------------------
	
	
	//2014-12-05 xkf-----供应商-------------------------------------------------------------------
	def supplierAdd ={
		redirect(action:"SupplierShow",params:params)
	}
	def supplierShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def company= Company.get(params.companyId)
		model["company"] = company
		
		def entity
		if(params.id){
			entity = Supplier.get(params.id)
		}else{
			entity = new Supplier()
		}
		model["supplier"] = entity
		model["user"] = currentUser
		
		//供应商类型
		model["supplierList"] = shareService.getSystemCodeItems(company,"rs_supplierType")
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/Supplier',model:model)
	}
	def supplierSearchView ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		
		def dataList = Depart.findAllByCompany(currentUser.company,[sort: "serialNo", order: "desc"])
		model["departList"] = dataList
		model["statusList"] = this.bargainStatus
		
		render(view:'/baseinfor/supplierSearch',model:model)
	}
	def supplierSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new Supplier()
		if(params.id && !"".equals(params.id)){
			entity = Supplier.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		if(entity.save(flush:true)){
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def supplierDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = Supplier.get(it)
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
	def supplierGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getSupplierListLayout()
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
			model["gridData"] = baseinforService.getSupplierListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = baseinforService.getSupplierCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	//--------------------------------------------------------------------------------------------------
	
	//2014-12-05 xkf-----材料信息-------------------------------------------------------------------
	def materialInfoAdd ={
		redirect(action:"MaterialInfoShow",params:params)
	}
	def materialInfoShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = MaterialInfo.get(params.id)
		}else{
			entity = new MaterialInfo()
		}
		model["materialInfo"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/MaterialInfo',model:model)
	}
	def materialInfoSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new MaterialInfo()
		if(params.id && !"".equals(params.id)){
			entity = MaterialInfo.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		if(entity.save(flush:true)){
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def materialInfoDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = MaterialInfo.get(it)
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
	def materialInfoGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getMaterialInfoListLayout()
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
			model["gridData"] = baseinforService.getMaterialInfoListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = baseinforService.getMaterialInfoCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	//--------------------------------------------------------------------------------------------------
	
	//2014-12-19 lhy-----修改为树形展示
	def materialType = {
		def model =[:]
		model["company"] = Company.get(params.companyId)
		render(view:'/baseinfor/materialType',model:model)
	}
	def matTypeCreate ={
		def model =[:]
		model["parentId"] = params.parentId
		model["companyId"] = params.companyId
		model["materialType"] = new MaterialType()
		
		//增加对是否二级单位进行控制
		def parent = MaterialType.get(params.parentId)
		if(parent){
			//2014-12-13---增加更换部门功能
			model["materialType"].parent = parent
		}
		
		render(view:'/baseinfor/materialType_edit',model:model)
	}
	def matTypeShow ={
		def model =[:]
		model["materialType"] = MaterialType.get(params.id)
		render(view:'/baseinfor/materialType_edit',model:model)
	}
	def matTypeSave ={
		def entity
		if(params.id){
			entity = MaterialType.get(params.id)
			entity.properties = params
			entity.clearErrors()
			
			//判断部门名称是否已经存在
			def _entity = MaterialType.findByCompanyAndMatTypeName(entity.company,params.matTypeName)
			if(_entity && !params.id.equals(_entity.id)){
				//已经存在
				flash.message = "<"+params.matTypeName+">已经存在，请重新输入！"
				render(view:'/baseinfor/materialType_edit',model:[materialType:entity,parentId:params.parentId,companyId:params.companyId])
				return
			}
			
			if(entity.save(flush:true)){
				
				//2014-12-13------增加更换部门功能-----------------------------------
				if(params.parentId){
					if(entity.parent && !params.parentId.equals(entity.parent.id)){
						def oldParent = entity.parent
						oldParent.removeFromChildren(entity)
						oldParent.save()
						
						def parent = MaterialType.get(params.parentId)
						parent.addToChildren(entity)
						parent.save(flush:true)
					}
				}
				//-------------------------------------------------------------
				
				flash.refreshTree = true;
				flash.message = "'"+entity.matTypeName+"' 已成功保存！"
				render(view:'/baseinfor/materialType_edit',model:[materialType:entity])
			}else{
				render(view:'/baseinfor/materialType_edit',model:[materialType:entity])
			}
		}else{
			entity = new MaterialType()
			entity.properties = params
			entity.clearErrors()
			
			def company = Company.get(params.companyId)
			
			//判断部门名称是否已经存在
			def _entity = MaterialType.findByCompanyAndMatTypeName(company,params.matTypeName)
			if(_entity){
				//已经存在
				flash.message = "<"+params.matTypeName+">已经存在，请重新输入！"
				render(view:'/baseinfor/materialType_edit',model:[materialType:entity,parentId:params.parentId,companyId:params.companyId])
				return
			}
			
			entity.company = company
			entity.save(flush:true)
			
			if(params.parentId){
				def parent = MaterialType.get(params.parentId)
				parent.addToChildren(entity)
				parent.save(flush:true)
			}
			
			flash.refreshTree = true;
			flash.message = "'"+entity.matTypeName+"' 已成功保存！"
			render(view:'/baseinfor/materialType_edit',model:[materialType:entity])
		}
	}
	def matTypeDelete ={
		def ids = params.id.split(",")
		def name
		try{
			ids.each{
				def materialType = MaterialType.get(it)
				if(materialType){
					name = materialType.matTypeName
					baseinforService.deleteMatType(materialType)
				}
			}
		}catch(Exception e){
			println e
		}
		render "<script type='text/javascript'>refreshObjTree()</script><h3>&nbsp;&nbsp;材料类型<"+name+">及其下级节点信息已删除！</h3>"
	}
	
	def matTypeTreeDataStore ={
		def company = Company.get(params.companyId)
		def dataList = MaterialType.findAllByCompany(company,[sort: "serialNo", order: "asc"])
		dataList.removeAll([null])
		
		def json = [identifier:'id',label:'name',items:[]]
		dataList.each{
			def sMap = ["id":it.id,"name":it.matTypeName,"parentId":it.parent?.id,"children":[]]
			def childMap
			it.getSortMatType().each{item->
				if(null!=item){
					childMap = ["_reference":item.id]
					sMap.children += childMap
				}
			}
			json.items+=sMap
		}
		render json as JSON
	}
	
	//-----------------------------------------------------------------------------------------
	
	//2014-12-05 xkf-----工种-------------------------------------------------------------------
	def workerTypeAdd ={
		redirect(action:"WorkerTypeShow",params:params)
	}
	def workerTypeShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = WorkerType.get(params.id)
		}else{
			entity = new WorkerType()
		}
		model["workerType"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/WorkerType',model:model)
	}
	def workerTypeSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new WorkerType()
		if(params.id && !"".equals(params.id)){
			entity = WorkerType.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		if(entity.save(flush:true)){
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def workerTypeDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = WorkerType.get(it)
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
	def workerTypeGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getWorkerTypeListLayout()
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
			model["gridData"] = baseinforService.getWorkerTypeListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = baseinforService.getWorkerTypeCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	//--------------------------------------------------------------------------------------------------
	
	
	//2014-12-20 xkf-----物料单位-------------------------------------------------------------------
	def materialUnitAdd ={
		redirect(action:"materialUnitShow",params:params)
	}
	def materialUnitShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = MaterialUnit.get(params.id)
		}else{
			entity = new MaterialUnit()
		}
		model["materialUnit"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/MaterialUnit',model:model)
	}
	def materialUnitSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new MaterialUnit()
		if(params.id && !"".equals(params.id)){
			entity = MaterialUnit.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		if(entity.save(flush:true)){
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def materialUnitDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = MaterialUnit.get(it)
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
	def materialUnitGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getMaterialUnitListLayout()
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
			model["gridData"] = baseinforService.getMaterialUnitListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = baseinforService.getMaterialUnitCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	//--------------------------------------------------------------------------------------------------
	
	
}
