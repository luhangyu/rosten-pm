package com.rosten.app.employe
import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company


class OfficeWorkerAttendance {

	/*
	 行政员工考勤信息
	 */
	String id
	
	//账号名称
	@GridColumn(name="员工姓名",width="100px",colIdx=1,formatter="officeWorkerAttendance_formatTopic")
	String offAttenName
	
	//当前部门
	@GridColumn(name="部门",width="160px",colIdx=2)
	String offAttenDepart
	
	//考勤日期
	Date offAttenDate=new Date()
	@GridColumn(name="考勤日期",colIdx=3)
	def getFormatteoffAttenDate(){
		if(offAttenDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(offAttenDate)
		}else{
			return ""
		}
	}
	
	//备注
	String officeWorkerAttendanceRemark

	
	//创建日期
	Date createdDate = new Date()
	
	double workNumber
	double affairsNumber
	double illNumber
	double awayNumber
	double lateNumber
	double earlyAwayNumber
	
	static constraints = {
		offAttenDate nullable:true,blank:true
	}
	static belongsTo = [company:Company]

	
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_EMP_OFFWORK"
	}
	
}
