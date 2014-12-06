package com.rosten.app.material
import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User

class MaterialController {

	def springSecurityService
	def materialService
	
	//采购计划<--start
	def purchasePlanAdd ={
		redirect(action:"purchasePlanShow",params:params)
	}
	def purchasePlanShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = PurchasePlan.get(params.id)
		}else{
			entity = new PurchasePlan()
		}
		model["purchasePlan"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/material/purchasePlan',model:model)
	}
	def purchasePlanSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new PurchasePlan()
		if(params.id && !"".equals(params.id)){
			entity = PurchasePlan.get(params.id)
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
	def purchasePlanDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = PurchasePlan.get(it)
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
	def purchasePlanGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = materialService.getPurchasePlanListLayout()
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
			model["gridData"] = materialService.getPurchasePlanListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = materialService.getPurchasePlanCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	// 采购计划end-->
	
	
	//采购单管理<--start
	def purchaseManageAdd ={
		redirect(action:"purchaseManageShow",params:params)
	}
	def purchaseManageShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = PurchaseManage.get(params.id)
		}else{
			entity = new PurchaseManage()
		}
		model["purchaseManage"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/material/purchaseManage',model:model)
	}
	def purchaseManageSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new PurchaseManage()
		if(params.id && !"".equals(params.id)){
			entity = PurchaseManage.get(params.id)
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
	def purchaseManageDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = PurchaseManage.get(it)
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
	def purchaseManageGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = materialService.getPurchaseManageListLayout()
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
			model["gridData"] = materialService.getPurchaseManageListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = materialService.getPurchaseManageCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	// 采购单管理end-->
	
	
}
