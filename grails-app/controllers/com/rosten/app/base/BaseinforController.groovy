package com.rosten.app.base

import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User

class BaseinforController {
	def springSecurityService
	def baseinforService
	
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
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ContactCorp.get(params.id)
		}else{
			entity = new ContactCorp()
		}
		model["ContactCorp"] = entity
		model["user"] = currentUser
		
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
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = Supplier.get(params.id)
		}else{
			entity = new Supplier()
		}
		model["supplier"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/Supplier',model:model)
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
	
	
	
	//2014-12-05 xkf-----材料类型-------------------------------------------------------------------
	def materialTypeAdd ={
		redirect(action:"MaterialTypeShow",params:params)
	}
	def materialTypeShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = MaterialType.get(params.id)
		}else{
			entity = new MaterialType()
		}
		model["materialType"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/baseinfor/MaterialType',model:model)
	}
	def materialTypeSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new MaterialType()
		if(params.id && !"".equals(params.id)){
			entity = MaterialType.get(params.id)
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
	def materialTypeDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = MaterialType.get(it)
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
	def materialTypeGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = baseinforService.getMaterialTypeListLayout()
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
			model["gridData"] = baseinforService.getMaterialTypeListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = baseinforService.getMaterialTypeCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	//--------------------------------------------------------------------------------------------------
	
	
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
	
	
}
