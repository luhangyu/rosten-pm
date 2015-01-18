package com.rosten.app.project
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

/*
 * 项目计划
 */
class ProjectPlan {
	String id
	
	//项目
	ProjectManage projectBelong

	@GridColumn(name="所属项目",width="200px",colIdx=1,formatter="projectPlan_formatTopic")
	def getProName(){
		if(projectBelong!=null){
			return projectBelong.projName
		}else{
			return ""
		}
	}
	
	//施工部位
	@GridColumn(name="部位",colIdx=2,width="100px")
	String constructPart
	
	
	Date consPlanDate = new Date()
	@GridColumn(name="计划开工日期",colIdx=3,width="80px")
	def getFormatteConsPlanDate(){
		if(consPlanDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(consPlanDate)
		}else{
			return ""
		}
	}
	
	Date consTrueDate = new Date()
	@GridColumn(name="实际开工日期",colIdx=4,width="80px")
	def getFormatteConsTrueDate(){
		if(consTrueDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(consTrueDate)
		}else{
			return ""
		}
	}
	
	//工程量
	String constructQutt
	
	//工期
	String constructDays
	
	
	//创建日期
	Date createdDate = new Date()
	@GridColumn(name="起草时间",width="150px",colIdx=3)
	def getFormatteCreatedDate(){
		if(createdDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(createdDate)
		}else{
			return ""
		}
	}
	
	static belongsTo = [company:Company]
	
	List constructLog
	
	static hasMany=[constructLog:ConstructLog]
	
	static constraints = {
	}
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_PRO_PLAN"
	}

}
