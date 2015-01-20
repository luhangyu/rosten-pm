package com.rosten.app.employe
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company


class Salary {
	String id
	
	//标题
	String salaryName
	
	Date wageDay=new Date()
	@GridColumn(name="发放日期",colIdx=1,width="100px",formatter="salary_formatTopic")
	def getFormatWageDay(){
		if(wageDay!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(wageDay)
		}else{
			return ""
		}
	}
	
	
	Date beginDay=new Date()
	@GridColumn(name="开始日期",colIdx=2,width="100px")
	def getFormatBeginDay(){
		if(beginDay!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(beginDay)
		}else{
			return ""
		}
	}
	
	Date endDay=new Date()
	@GridColumn(name="结束日期",colIdx=3,width="100px")
	def getFormatEndDay(){
		if(endDay!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(endDay)
		}else{
			return ""
		}
	}
	
	//合计
	@GridColumn(name="工资总额",colIdx=4,width="100px")
	Double salarySum

	//填写人
	@GridColumn(name="填写人",colIdx=5,width="100px")
	String salaryMaker
	
	//创建日期
	Date createdDate = new Date()
	
	List workerSalaries
	
	static hasMany=[workerSalaries:WorkerSalary]

	static belongsTo = [company:Company]
	
    static constraints = {
		
    }
	
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_EMP_SALARY"
	}
}
