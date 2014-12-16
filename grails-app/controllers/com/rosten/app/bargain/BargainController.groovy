package com.rosten.app.bargain

import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User
import com.rosten.app.base.ContactCorp



class BargainController {

	def springSecurityService
	def bargainService
	
	//合同<--start
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
			entity.bargainMaker = currentUser.getFormattedName()
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
		entity.bargainSigningDate = Util.convertToTimestamp(params.bargainSigningDate)
		
		
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
	
	//合同end-->
	
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
			
		def bargainVendorCorpName = bargain?.BargainPurchaserCorpName
		def contactCorpName = bargainVendorCorpName?.contactCorpName
		
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

