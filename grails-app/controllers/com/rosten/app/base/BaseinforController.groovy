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
	
	//公司基本信息
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
	
}
