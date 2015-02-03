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
	
	
	//获取材料信息清单数据
	def getMatInfoListSelect ={
		def _List =[]
		def Unit =null
		def company = Company.get(params.companyId)
		MaterialInfo.findAllByCompany(company).each{
			def json=[:]
			json["id"] = it.id
			json["name"] = it.matInfoName
			json["matInfoBrand"] = it.matInfoBrand			
			json["mInfoRPrice"] = it.mInfoRPrice
			
						
			Unit = MaterialUnit.get(it.matInfoPurUnitId)
			json["matInfoPurUnit"] = ""
			if (Unit!=null){
				json["matInfoPurUnit"] = Unit.matUnitName
			}
			
			
			_List << json
		}
		render _List as JSON
	}
	
	//获取材料计量单位列表
	def getMatUnitSelect ={
		def _List =[]
		def company = Company.get(params.companyId)
		def type = MaterialType.get(params.matInfoTypeId)
		MaterialUnit.findAllByCompanyAndMatType(company,type).each{
			def json=[:]
			json["id"] = it.id
			json["name"] = it.matUnitName
			_List << json
		}
		render _List as JSON
	}
	
	//获取往来单位信息列表数据
	def getContactCorpSelect ={
		def _List =[]
		def company = Company.get(params.companyId)
		//传入过滤参数：如甲方、乙方等 params.contactCorpType
		def contactCorpType=params.contactCorpType
		ContactCorp.findAllByCompanyAndContactCorpType(company,contactCorpType).each{
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
	
	//设置默认公司；同时自动修改系统管理里的单位信息
	def companyInforSetDefault ={
		def json
		try{
			//默认值允许单选
			def companyInfor = CompanyInfor.get(params.id)
			if(companyInfor){
				if("true".equals(params.companyIsDef)){
					companyInfor.companyIsDef = true
				}else{
					companyInfor.companyIsDef = false
				}
			}
			//去除其他的默认信息
			CompanyInfor.list().each{
				if(!companyInfor.equals(it)){
					it.companyIsDef = false
					it.save()
				}
			}
			companyInfor.save()
			//修改系统的单位信息
			def company = companyInfor.company
			company.companyName=companyInfor.companyName
			company.shortName=companyInfor.companyAbbr
			company.companyPhone=companyInfor.companyPhone
			company.companyFax=companyInfor.companyFax
			company.companyAddress=companyInfor.companyAddress
			
			
			company.save(flush:true)
			
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
	}
	
	//2014-11-22 xkf-----银行账号信息-------------------------------------------------------------------
	def bankInforAdd ={
		redirect(action:"bankInforShow",params:params)
	}
	def bankInforShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def company = Company.get(params.companyId)
		model["company"] = company
		
		def entity
		if(params.id){
			entity = BankInfor.get(params.id)
		}else{
			entity = new BankInfor()
		}

		//开户行类型
		model["accountBankTypeList"] = shareService.getSystemCodeItems(company,"rs_accountBankType")
		
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
	
	//设置为缺省
	def bankInforSetDefault ={
		def json
		try{
			//默认值允许单选
			def bankInfor = BankInfor.get(params.id)
			if(bankInfor){
				if("true".equals(params.accountIsDef)){
					bankInfor.accountIsDef = true
				}else{
					bankInfor.accountIsDef = false
				}
			}
			//去除其他的默认信息
			BankInfor.list().each{
				if(!bankInfor.equals(it)){
					it.accountIsDef = false
					it.save()
				}
			}
			bankInfor.save(flush:true)
			
			json = [result:'true']
		}catch(Exception e){
			json = [result:'error']
		}
		render json as JSON
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
			entity.contCorpStatus="正常"
		}
		model["contactCorp"] = entity
		model["user"] = currentUser
		
		//往来单位类型
		model["contactCropTypeList"] = shareService.getSystemCodeItems(company,"rs_contactCropType")
		//开户行类型
		model["contactCorpAccBankList"] = shareService.getSystemCodeItems(company,"rs_accountBankType")
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/contactCorp',model:model)
	}
	
	def contactCorpSearchView ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def company= currentUser.company
		model["company"] = company
		//往来单位类型
		model["contactCropTypeList"] = shareService.getSystemCodeItems(company,"rs_contactCropType")

		render(view:'/baseinfor/contactCorpSearch',model:model)
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
		if(params.contactCorpName && !"".equals(params.contactCorpName)) searchArgs["contactCorpName"] = params.contactCorpName
		if(params.contactCorpType && !"".equals(params.contactCorpType)) searchArgs["contactCorpType"] = params.contactCorpType
		
		
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
		//开户行类型
		model["suppBankAccBankList"] = shareService.getSystemCodeItems(company,"rs_accountBankType")
		
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/supplier',model:model)
	}
	
	def supplierSearchView ={
		
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		def company= currentUser.company		
		model["company"] = company
		//供应商类型
		model["suppliers"] = shareService.getSystemCodeItems(company,"rs_supplierType")

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
		if(params.suppName && !"".equals(params.suppName)) searchArgs["suppName"] = params.suppName
		if(params.suppType && !"".equals(params.suppType)) searchArgs["suppType"] = params.suppType
		if(params.suppCode && !"".equals(params.suppCode)) searchArgs["suppCode"] = params.suppCode
		
		
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
			def mateType = MaterialType.get(params.type)
			entity.matInfoType = mateType
		}
		model["materialInfo"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/materialInfo',model:model)
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
		
		//类字段保存
		if(params.matInfoPurUnitId){
			def POBJ = MaterialUnit.get(params.matInfoPurUnitId)
			if(POBJ){
				entity.matInfoPurUnit = POBJ
			}
		}
		if(params.matInfoGetUnitId){
			def GOBJ = MaterialUnit.get(params.matInfoGetUnitId)
			if(GOBJ){
				entity.matInfoGetUnit = GOBJ
			}
		}
		entity.matInfoType= MaterialType.get(params.matInfoTypeId)
		
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
		
		def currentUser = springSecurityService.getCurrentUser()
		def company = currentUser.company
		
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getMaterialInfoListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		if(params.searchId && !"".equals(params.searchId)){
			 if(!"all".equals(params.searchId)){
				 searchArgs["matInfoType"] = MaterialType.get(params.searchId)
			 }
		}
		
		if(params.materialInforName && !"".equals(params.materialInforName)){
			searchArgs["matInfoName"] = params.materialInforName
		}
		
		if(params.materialType && !"".equals(params.materialType)){
			searchArgs["matInfoType"] = MaterialType.findByMatTypeName(params.materialType)
	   }
		
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
	
	//2015-1-18-------lhy---增加搜索功能
	def materialInforSearchView ={
		def model =[:]
		
		def mateType = MaterialType.get(params.searchId)
		if(mateType){
			model["mateTypeName"] = mateType?.matTypeName
		}else{
			def mateTypeList = MaterialType.list()
			model["mateTypeList"] = mateTypeList
		}
		
		render(view:'/baseinfor/materialInforSearch',model:model)
	}
	def materailManageShow ={
		def model =[:]
		model["searchId"] = params.id
		render(view:'/baseinfor/materialInforManage',model:model)
	}
	def materailManageShow1 ={
		def model =[:]
		model["searchId"] = params.id
		render(view:'/baseinfor/materialInforManageShow',model:model)
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
		
		def company = Company.get(params.companyId)
		
		model["materialType"] = new MaterialType(company:company)
		
		
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
		def entity = MaterialType.get(params.id)
		model["materialType"] = entity
		
		model["parentId"] = entity.parent?.id
		model["companyId"] = entity.company.id
		render(view:'/baseinfor/materialType_edit',model:model)
	}
	def matTypeSave ={
		def json = [:]
		
		def entity
		if(params.id){
			entity = MaterialType.get(params.id)
			entity.properties = params
			entity.clearErrors()
			
			//判断部门名称是否已经存在
			def _entity = MaterialType.findByCompanyAndMatTypeName(entity.company,params.matTypeName)
			if(_entity && !params.id.equals(_entity.id)){
				//已经存在
				json["result"] = "exist"
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
				
				json["result"] = true
			}else{
				json["result"] = false
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
				json["result"] = "exist"
			}
			
			entity.company = company
			entity.save(flush:true)
			
			if(params.parentId){
				def parent = MaterialType.get(params.parentId)
				parent.addToChildren(entity)
				parent.save(flush:true)
			}
			
			json["result"] = true
		}
		render json as JSON
	}
	def matTypeDelete ={
		def json=[:]
		def ids = params.id.split(",")
		try{
			ids.each{
				def materialType = MaterialType.get(it)
				if(materialType){
					baseinforService.deleteMatType(materialType)
				}
			}
			json["result"] = true
		}catch(Exception e){
			println e
			json["result"] = false
		}
		render json as JSON
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
		render(view:'/baseinfor/workerType',model:model)
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
		render(view:'/baseinfor/materialUnit',model:model)
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
		
		entity.matType = MaterialType.get(params.matTypeId)
		
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
