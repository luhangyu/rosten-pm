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
	def undertakeBargainAdd ={
		redirect(action:"undertakeBargainShow",params:params)
	}
	def undertakeBargainShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = UndertakeBargain.get(params.id)
		}else{
			entity = new UndertakeBargain()
		}
		model["undertakeBargain"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/bargain/undertakeBargain',model:model)
	}
	def undertakeBargainSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new UndertakeBargain()
		if(params.id && !"".equals(params.id)){
			entity = UndertakeBargain.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		//日期字段值处理，convertToTimestamp
		entity.undertakeBargainSignDate = Util.convertToTimestamp(params.undertakeBargainSignDate)
		
		
		
		
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
	def undertakeBargainDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = UndertakeBargain.get(it)
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
	def undertakeBargainGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = bargainService.getUndertakeBargainListLayout()
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
			model["gridData"] = bargainService.getUndertakeBargainListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = bargainService.getUndertakeBargainCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//承包合同end-->
	
	
	
	

}

