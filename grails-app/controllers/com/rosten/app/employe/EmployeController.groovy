package com.rosten.app.employe

import org.apache.jasper.compiler.Node.ParamsAction;

import grails.converters.JSON

import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User

class EmployeController {

	def springSecurityService
	def employeService
	
	private def attendanceType=["constructionWorkerAttendance":"大点工考勤","officeWorkerAttendance":"员工考勤"]
	
	
	//考勤父表<--start
	def attendanceAdd ={
		redirect(action:"attendanceShow",params:params)
	}
	def attendanceShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = Attendance.get(params.id)
		}else{
			entity = new Attendance()
			entity.attendDrafter = currentUser.getFormattedName()
		}

		model["attendance"] = entity
		model["user"] = currentUser
		
		switch(params.type){
			case "constructionWorkerAttendance":
			entity.attendType = "大点工考勤"
			
			break;
			case "officeWorkerAttendance":
			entity.attendType = "员工考勤"
			
			break;
		}

		
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/employe/attendance',model:model)
	}
	def attendanceSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new Attendance()
		if(params.id && !"".equals(params.id)){
			entity = Attendance.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
		entity.attendDate = Util.convertToTimestamp(params.attendDate)
		
		if(entity.workerAttends){
			def _list = []
			_list += entity.workerAttends
			entity.workerAttends.clear()
			_list.each{
				it.delete()
			}
		}
		JSON.parse(params.workerAttendsValues).eachWithIndex{elem, i ->
			def workerAttendance = new WorkerAttendance(elem)
			workerAttendance.clearErrors()
			entity.addToWorkerAttends(workerAttendance)
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
	def attendanceDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = Attendance.get(it)
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
	def attendanceGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = employeService.getAttendanceListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		if(params.type && !"".equals(params.type)){
			switch (params.type){
				case "officeWorkerAttendance":
					searchArgs["attendType"] = this.attendanceType[params.type]
					break;
				case "constructionWorkerAttendance":
					searchArgs["attendType"] = this.attendanceType[params.type]
					break;
				default:
				searchArgs["attendType"] = params.type
			}
		}else{
			searchArgs["attendType"] = params.type
		}
		
		
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			model["gridData"] = employeService.getAttendanceListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = employeService.getAttendanceCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//考勤父表end-->
	
	
	//考勤子表<--start
	def workerAttendanceAdd ={
		redirect(action:"workerAttendanceShow",params:params)
	}
	def workerAttendanceShow ={
		def model =[:]
		model["isShowField"] = true
		if(params.id){
			model["workerAttendance"] = WorkerAttendance.get(params.id)			
		}else{
			def workerAttendance = new WorkerAttendance()
			if("大点工考勤".equals(params.attendtype)){
				model["isShowField"] = false
			}			
			model["workerAttendance"] = workerAttendance
		}
		render(view:'/employe/workerAttendance',model:model)
	}
	def workerAttendanceGrid ={
		def json=[:]
		def attendance = Attendance.get(params.id)
		
		
		
		if(params.refreshHeader){
			
			
			if("大点工考勤".equals(params.attendtype)){
				def _gridHeader =[]
				_gridHeader << ["name":"序号","width":"26px","colIdx":0,"field":"rowIndex"]
				_gridHeader << ["name":"员工姓名","field":"attendUserName","width":"100px","colIdx":1,"formatter":"workerAttendance_formatTopic"]
				_gridHeader <<["name":"部门","field":"attendDepart","width":"100px","colIdx":2]
				_gridHeader <<["name":"出勤","field":"workNumber","width":"30px","colIdx":3]
				_gridHeader <<["name":"操作","field":"workerAttendanceId","width":"40px","colIdx":91,"formatter":"workerAttendance_action"]
				json["gridHeader"] = _gridHeader
						
			}else{
				json["gridHeader"] = employeService.getWorkerAttendanceListLayout()
			}
			
		}
		def searchArgs =[:]
		if(params.refreshData){
			if(!attendance){
				json["gridData"] = ["identifier":"id","label":"name","items":[]]
			}else{
				def args =[:]
				int perPageNum = Util.str2int(params.perPageNum)
				int nowPage =  Util.str2int(params.showPageNum)
				
				args["offset"] = (nowPage-1) * perPageNum
				args["max"] = perPageNum
				args["attendance"] = attendance
				
				def gridData = employeService.getWorkerAttendanceListDataStore(args,searchArgs)
				json["gridData"] = gridData
			}
		}
		if(params.refreshPageControl){
			if(!attendance){
				json["pageControl"] = ["total":"0"]
			}else{
				def total = employeService.getWorkerAttendanceCount(attendance,searchArgs)
				json["pageControl"] = ["total":total.toString()]
			}
			
		}
		render json as JSON
	}
	
	//考勤子类end-->
	
	
	
	
	
	
	/*
	 * 工资父表-----------start
	 */
	def salaryAdd ={
		redirect(action:"salaryShow",params:params)
	}
	def salaryShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = Salary.get(params.id)
		}else{
			entity = new Salary()
			entity.salaryMaker = currentUser.getFormattedName()
		}

		model["salary"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/employe/salary',model:model)
	}
	def salarySave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new Salary()
		if(params.id && !"".equals(params.id)){
			entity = Salary.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
		entity.wageDay = Util.convertToTimestamp(params.wageDay)
		entity.beginDay = Util.convertToTimestamp(params.beginDay)
		entity.endDay = Util.convertToTimestamp(params.endDay)
		
		if(entity.workerSalaries){
			def _list = []
			_list += entity.workerSalaries
			entity.workerSalaries.clear()
			_list.each{
				it.delete()
			}
		}
		JSON.parse(params.workerSalariesValues).eachWithIndex{elem, i ->
			def workerSalary = new WorkerSalary(elem)
			workerSalary.clearErrors()
//			workerSalary.createdDate = Util.convertToTimestamp(elem.createdDate)
			entity.addToWorkerSalaries(workerSalary)
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
	def salaryDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = Salary.get(it)
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
	def salaryGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = employeService.getSalaryListLayout()
		}
		
		//增加查询条件
		def searchArgs =[:]
		if(params.type && !"".equals(params.type)){
			switch (params.type){
//				case "officeWorkerAttendance":
//					searchArgs["attendType"] = this.attendanceType[params.type]
//					break;
//				case "constructionWorkerAttendance":
//					searchArgs["attendType"] = this.attendanceType[params.type]
//					break;
//				default:
//				searchArgs["attendType"] = params.type
			}
		}else{
			searchArgs["attendType"] = params.type
		}
		
		
		if(params.refreshData){
			def args =[:]
			int perPageNum = Util.str2int(params.perPageNum)
			int nowPage =  Util.str2int(params.showPageNum)
			
			args["offset"] = (nowPage-1) * perPageNum
			args["max"] = perPageNum
			args["company"] = company
			model["gridData"] = employeService.getSalaryListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = employeService.getSalaryCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//-------工资父表end-->
	
	
	//-----------------------------------工资子表<--start
	def workerSalaryAdd ={
		redirect(action:"workerSalaryShow",params:params)
	}
	def workerSalaryShow ={
		def model =[:]
		
		if(params.id){
			model["workerSalary"] = WorkerSalary.get(params.id)
		}else{
			def workerSalary = new WorkerSalary()			
			model["workerSalary"] = workerSalary
		}
		render(view:'/employe/workerSalary',model:model)
	}
	def workerSalaryGrid ={
		def json=[:]
		def salary = Salary.get(params.id)
		
		if(params.refreshHeader){
			
				json["gridHeader"] = employeService.getWorkerSalaryListLayout()
			
		}
		def searchArgs =[:]
		if(params.refreshData){
			if(!salary){
				json["gridData"] = ["identifier":"id","label":"name","items":[]]
			}else{
				def args =[:]
				int perPageNum = Util.str2int(params.perPageNum)
				int nowPage =  Util.str2int(params.showPageNum)
				
				args["offset"] = (nowPage-1) * perPageNum
				args["max"] = perPageNum
				args["salary"] = salary
				
				def gridData = employeService.getWorkerSalaryListDataStore(args,searchArgs)
				json["gridData"] = gridData
			}
		}
		if(params.refreshPageControl){
			if(!salary){
				json["pageControl"] = ["total":"0"]
			}else{
				def total = employeService.getWorkerSalaryCount(salary,searchArgs)
				json["pageControl"] = ["total":total.toString()]
			}
			
		}
		render json as JSON
	}
	
	//----------------------工资子类end-->

}
