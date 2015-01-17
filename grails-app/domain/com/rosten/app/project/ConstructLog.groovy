package com.rosten.app.project
import java.util.Date;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class ConstructLog {
	String id
	
	//项目
	ProjectPlan projPlanBelong
	
	@GridColumn(name="所属项目计划",width="200px",colIdx=1,formatter="constructLog_formatTopic")
	def getProName(){
		if(projPlanBelong!=null){
			return projPlanBelong.getProName()
		}else{
			return ""
		}
	}
	
	//施工部位
	@GridColumn(name="部位",colIdx=2,width="100px")
	String constructPart
	
	
	Date constructDate = new Date()
	@GridColumn(name="施工日期",colIdx=3,width="80px")
	def getFormatteConstructDate(){
		if(constructDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(constructDate)
		}else{
			return ""
		}
	}
	
	//完成工程量
	String consDoneQutt
	
	//完成 百分比
	String consDoneRate
	
	//填报人
	String logMaker
	
	
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
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_PRO_CONLOG"
	}
}
