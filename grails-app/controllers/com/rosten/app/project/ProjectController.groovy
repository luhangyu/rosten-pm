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
}
