package com.rosten.app.project
import java.util.Date;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class ConstructLog {
	String id
	
	
	@GridColumn(name="所属项目计划",width="200px",colIdx=1,formatter="constructLog_formatTopic")
	def getProjPlanName(){return projectPlan?projectPlan.getProName():""}
	
	//施工部位
	@GridColumn(name="部位",colIdx=2,width="80px")
	def getConstructPart(){return projectPlan?projectPlan.constructPart:""}
	
	
	Date constructDate=new Date()
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
	@GridColumn(name="完成工程量",colIdx=4,width="80px")
	String consDoneQutt
	
	//完成 百分比
	@GridColumn(name="完成百分比",colIdx=5,width="80px")
	String consDoneRate
	
	//填报人
	@GridColumn(name="填报人",colIdx=6,width="100px")
	String logMaker
	
	
	//创建日期
	Date createdDate = new Date()
	//@GridColumn(name="起草时间",width="150px",colIdx=3)
	def getFormatteCreatedDate(){
		if(createdDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(createdDate)
		}else{
			return ""
		}
	}
	
	@GridColumn(name="操作",colIdx=7,formatter="constructLog_action")
	def constructLogId(){
		return id
	}
	
	static belongsTo = [projectPlan:ProjectPlan]
	
	static transients = ["projPlanName","constructPart"]
	
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_PRO_CONLOG"
	}
}
