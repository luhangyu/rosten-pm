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
	@GridColumn(name="部门",width="150px",colIdx=2)
	String offAttenDepart
	
	//考勤日期
	Date offAttenDate=new Date()
	@GridColumn(name="考勤日期",colIdx=3,width="150px")
	def getFormatteoffAttenDate(){
		if(offAttenDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(offAttenDate)
		}else{
			return ""
		}
	}
	
	//创建日期
	Date createdDate = new Date()
	
	@GridColumn(name="出勤",colIdx=4,width="50px")
	Double workNumber
	
	@GridColumn(name="事假",colIdx=5,width="50px")
	Double affairsNumber
	
	@GridColumn(name="病假",colIdx=6,width="50px")
	Double illNumber
	
	@GridColumn(name="旷工",colIdx=7,width="50px")
	Double awayNumber
	
	@GridColumn(name="迟到",colIdx=8,width="50px")
	Double lateNumber
	
	@GridColumn(name="早退",colIdx=9,width="50px")
	Double earlyAwayNumber
	
	//备注
	@GridColumn(name="备注",colIdx=91)
	String offAtteRemark
	
	
	static constraints = {
		offAttenDate nullable:true,blank:true
	}
	static belongsTo = [company:Company]

	
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_EMP_OFFWORK"
	}
	
}
