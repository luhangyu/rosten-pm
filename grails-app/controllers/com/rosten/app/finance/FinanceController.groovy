package com.rosten.app.finance
import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User

class FinanceController {
	def springSecurityService
	def financeService
	
	//报销管理<--start
	def expenseReimbursementAdd ={
		redirect(action:"expenseReimbursementShow",params:params)
	}
	def expenseReimbursementShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ExpenseReimbursement.get(params.id)
		}else{
			entity = new ExpenseReimbursement()
		}
		model["expenseReimbursement"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/finance/expenseReimbursement',model:model)
	}
	def expenseReimbursementSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ExpenseReimbursement()
		if(params.id && !"".equals(params.id)){
			entity = ExpenseReimbursement.get(params.id)
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
	def expenseReimbursementDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ExpenseReimbursement.get(it)
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
	def expenseReimbursementGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = financeService.getExpenseReimbursementListLayout()
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
			model["gridData"] = financeService.getExpenseReimbursementListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = financeService.getExpenseReimbursementCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	
	
	def  expenseListShow ={
		def model =[:]
		if(params.id){
			model["expenseReimbursementItem"] = ExpenseReimbursementItem.get(params.id)
		}else{
			model["expenseReimbursementItem"] = new ExpenseReimbursementItem()
		}
		render(view:'/finance/expenseReimbursementItemShow',model:model)
	}
	def expenseListGrid ={
		def json=[:]		
		def expenseReimbursement = ExpenseReimbursement.get(params.id)
		if(params.refreshHeader){
			json["gridHeader"] = financeService.getExpenseReimbursementItemListLayout()
		}
		
		//2014-9-1 增加搜索功能
		def searchArgs =[:]
		if(params.refreshData){
			if(!expenseReimbursement){
				json["gridData"] = ["identifier":"id","label":"name","items":[]]
			}else{
				def args =[:]
				int perPageNum = Util.str2int(params.perPageNum)
				int nowPage =  Util.str2int(params.showPageNum)
				
				args["offset"] = (nowPage-1) * perPageNum
				args["max"] = perPageNum
				args["expenseReimbursement"] = expenseReimbursement
				
				def gridData = financeService.getExpenseReimbursementItemListDataStore(args,searchArgs)
				json["gridData"] = gridData
			}
		}
		if(params.refreshPageControl){
			if(!expenseReimbursement){
				json["pageControl"] = ["total":"0"]
			}else{
				def total = financeService.getExpenseReimbursementItemCount(expenseReimbursement,searchArgs)
				json["pageControl"] = ["total":total.toString()]
			}
			
		}
		render json as JSON
	}
	
	//报销管理end-->
    
	
	
}
