package com.rosten.app.employe

import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User


class EmployeController {

	def springSecurityService
	def employeService
	
	//点工考勤<--start
	def constructionWorkerAttendanceAdd ={
		redirect(action:"constructionWorkerAttendanceShow",params:params)
	}
	def constructionWorkerAttendanceShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ConstructionWorkerAttendance.get(params.id)
		}else{
			entity = new ConstructionWorkerAttendance()
		}
		model["constructionWorkerAttendance"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/employe/constructionWorkerAttendance',model:model)
	}
	def constructionWorkerAttendanceSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ConstructionWorkerAttendance()
		if(params.id && !"".equals(params.id)){
			entity = ConstructionWorkerAttendance.get(params.id)
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
	def constructionWorkerAttendanceDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ConstructionWorkerAttendance.get(it)
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
	def constructionWorkerAttendanceGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = employeService.getConstructionWorkerAttendanceListLayout()
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
			model["gridData"] = employeService.getConstructionWorkerAttendanceListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = employeService.getConstructionWorkerAttendanceCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//点工考勤end-->
	
	
	//员工（行政人员）考勤<--start
	def officeWorkerAttendanceAdd ={
		redirect(action:"officeWorkerAttendanceShow",params:params)
	}
	def officeWorkerAttendanceShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = OfficeWorkerAttendance.get(params.id)
		}else{
			entity = new OfficeWorkerAttendance()
		}
		model["officeWorkerAttendance"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/employe/officeWorkerAttendance',model:model)
	}
	def officeWorkerAttendanceSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new OfficeWorkerAttendance()
		if(params.id && !"".equals(params.id)){
			entity = OfficeWorkerAttendance.get(params.id)
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
	def officeWorkerAttendanceDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = OfficeWorkerAttendance.get(it)
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
	def officeWorkerAttendanceGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = employeService.getOfficeWorkerAttendanceListLayout()
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
			model["gridData"] = employeService.getOfficeWorkerAttendanceListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = employeService.getOfficeWorkerAttendanceCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//员工（行政人员）end-->
	
	

}
