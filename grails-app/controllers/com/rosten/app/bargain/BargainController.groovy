package com.rosten.app.bargain



import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User


class BargainController {

	def springSecurityService
	def bargainService
	
	//承包合同<--start
	def bargainAdd ={
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
		}
		model["bargain"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/bargain/bargain',model:model)
	}
	def bargainSave ={
		def model=[:]
		
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
	
	//承包合同end-->
	
	
	
	

}

