package com.rosten.app.project
import grails.converters.JSON
import com.rosten.app.util.FieldAcl
import com.rosten.app.util.SystemUtil
import com.rosten.app.util.Util
import com.rosten.app.system.Company
import com.rosten.app.system.User
import com.rosten.app.base.ContactCorp

class ProjectController {

    def springSecurityService
	def projectService
	
	
	//获取项目列表（合同表单“项目名称”字段选择数据）
	def getProjectSelect ={
		def _List =[]
		def company = Company.get(params.companyId)
		ProjectManage.findAllByCompany(company).each{
			def json=[:]
			json["id"] = it.id
			json["name"] = it.projName
			json["projNo"] = it.projNo
//			json["cCorpLeaderDuty"] = it.cCorpLeaderDuty
//			json["contactCorpPhone"] = it.contactCorpPhone
//			json["contactCorpPost"] = it.contactCorpPost
//			json["contactCorpAddr"] = it.contactCorpAddr
			_List << json
		}
		render _List as JSON
	}
	
	//项目管理
	def projectManageAdd ={
		redirect(action:"projectManageShow",params:params)
	}
	def projectManageShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ProjectManage.get(params.id)
		}else{
			entity = new ProjectManage()
		}
		model["projectManage"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/project/projectManage',model:model)
	}
	def projectManageSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ProjectManage()
		if(params.id && !"".equals(params.id)){
			entity = ProjectManage.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
		entity.projEndDate = Util.convertToTimestamp(params.projectEndDate)
		entity.projStartDate = Util.convertToTimestamp(params.projectStartDate)
		
		if(params.supCorpId){
			def suppCorpOBJ = ContactCorp.get(params.supCorpId)
			if(suppCorpOBJ){
				entity.supCorp = suppCorpOBJ
			}
		}
		if(params.constCorpId){
			def constCorpOBJ = ContactCorp.get(params.constCorpId)
			if(constCorpOBJ){
				entity.constCorp = constCorpOBJ
			}
		}
		
		if(entity.save(flush:true)){
			model["id"] = entity.id
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def projectManageDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ProjectManage.get(it)
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
	def projectManageGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = projectService.getProjectManageListLayout()
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
			model["gridData"] = projectService.getProjectManageListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = projectService.getProjectManageCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//项目管理end-->
	
	
	//项目计划start-------------------------------------------
	def projectPlanAdd ={
		redirect(action:"projectPlanShow",params:params)
	}
	def projectPlanShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ProjectPlan.get(params.id)
		}else{
			entity = new ProjectPlan()
		}
		model["projectPlan"] = entity
		model["user"] = currentUser
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/project/projectPlan',model:model)
	}
	def projectPlanSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ProjectPlan()
		if(params.id && !"".equals(params.id)){
			entity = ProjectPlan.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
		entity.consPlanDate = Util.convertToTimestamp(params.consPlanDate)
		entity.consTrueDate = Util.convertToTimestamp(params.consTrueDate)
		
		if(params.projectBelongId){
			def OBJ = ProjectManage.get(params.projectBelongId)
			if(OBJ){
				entity.projectBelong = OBJ
			}
		}
		
		if(entity.constructLogs){
			def _list = []
			_list += entity.constructLogs
			entity.constructLogs.clear()
			_list.each{
				it.delete()
			}
		}
		
		JSON.parse(params.constructLogsValues).eachWithIndex{elem, i ->
			def constructLog = new ConstructLog(elem)
			constructLog.clearErrors()
			constructLog.constructDate = Util.convertToTimestamp(elem.getFormatteConstructDate)
			
			entity.addToConstructLogs(constructLog)
		}
		
		
		if(entity.save(flush:true)){
			model["id"] = entity.id
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def projectPlanDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ProjectPlan.get(it)
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
	def projectPlanGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = projectService.getProjectPlanListLayout()
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
			model["gridData"] = projectService.getProjectPlanListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = projectService.getProjectPlanCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//项目计划end-->
	
	
	
	
	
	
	//施工方案start
	def constructApproveAdd ={
		redirect(action:"constructApproveShow",params:params)
	}
	def constructApproveShow ={
		def model =[:]
		def currentUser = springSecurityService.getCurrentUser()
		model["company"] = Company.get(params.companyId)
		
		def entity
		if(params.id){
			entity = ConstructApprove.get(params.id)
		}else{
			entity = new ConstructApprove()
		}
		model["constructApprove"] = entity
		model["user"] = currentUser
		
		model["isShowFile"]=true
		
		FieldAcl fa = new FieldAcl()
		model["fieldAcl"] = fa
		render(view:'/project/constructApprove',model:model)
	}
	def constructApproveSave ={
		def model=[:]
		
		def company = Company.get(params.companyId)
		def entity = new ConstructApprove()
		if(params.id && !"".equals(params.id)){
			entity = ConstructApprove.get(params.id)
		}else{
			entity.company = company
		}
		
		entity.properties = params
		entity.clearErrors()
		
		//日期字段值处理，convertToTimestamp
//		entity.projStartDate = Util.convertToTimestamp(params.projectStartDate)
		if(params.projectBelongId){
			def OBJ = ProjectManage.get(params.projectBelongId)
			if(OBJ){
				entity.projectBelong = OBJ
			}
		}

		if(entity.save(flush:true)){
			model["id"] = entity.id
			model["result"] = "true"
		}else{
			entity.errors.each{
				println it
			}
			model["result"] = "false"
		}
		render model as JSON
	}
	def constructApproveDelete ={
		def ids = params.id.split(",")
		def json
		try{
			ids.each{
				def entity = ConstructApprove.get(it)
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
	def constructApproveGrid ={
		def model=[:]
		def company = Company.get(params.companyId)
		if(params.refreshHeader){
			model["gridHeader"] = projectService.getConstructApproveListLayout()
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
			model["gridData"] = projectService.getConstructApproveListDataStore(args,searchArgs)
			
		}
		if(params.refreshPageControl){
			def total = projectService.getConstructApproveCount(company,searchArgs)
			model["pageControl"] = ["total":total.toString()]
		}
		render model as JSON
	}
	
	//施工方案end-->
	
	
	
	
	//施工日志start
	def constructLogAdd ={
		redirect(action:"constructLogShow",params:params)
	}
	
	def constructLogShow ={
		def model =[:]
		if(params.id){
			model["constructLog"] = ConstructLog.get(params.id)
		}else{
			def constructLog = new ConstructLog()
			constructLog.projectPlan = ProjectPlan.get(params.planId)
			
			model["constructLog"] = constructLog
		}
		render(view:'/project/constructLog',model:model)
	}
	
	
	def constructLogGrid ={
		def json=[:]
		def projectPlan = ProjectPlan.get(params.id)
		if(params.refreshHeader){
			json["gridHeader"] = projectService.getConstructLogListLayout()
		}
		
		//2014-9-1 增加搜索功能
		def searchArgs =[:]
		if(params.refreshData){
			if(!projectPlan){
				json["gridData"] = ["identifier":"id","label":"name","items":[]]
			}else{
				def args =[:]
				int perPageNum = Util.str2int(params.perPageNum)
				int nowPage =  Util.str2int(params.showPageNum)
				
				args["offset"] = (nowPage-1) * perPageNum
				args["max"] = perPageNum
				args["projectPlan"] = projectPlan
				
				def gridData = projectService.getConstructLogListDataStore(args,searchArgs)
				json["gridData"] = gridData
			}
		}
		
		
		if(params.refreshPageControl){
			if(!projectPlan){
				json["pageControl"] = ["total":"0"]
			}else{
				def total = projectService.getConstructLogCount(projectPlan,searchArgs)
				json["pageControl"] = ["total":total.toString()]
			}
		}
		render json as JSON
	}
	
	//施工日志end-->
	
	
	
	
	
	
}
