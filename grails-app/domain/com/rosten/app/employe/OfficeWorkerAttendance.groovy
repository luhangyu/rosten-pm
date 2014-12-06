package com.rosten.app.employe
import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company


class OfficeWorkerAttendance {

	/*
	 员工考勤信息
	 */
	String id
	
	//账号名称
	@GridColumn(name="员工姓名",width="100px",colIdx=1,formatter="officeWorkerAttendance_formatTopic")
	String officeAttendanceName
	
	//当前部门
	@GridColumn(name="部门",width="160px",colIdx=2)
	String officeAttendanceDepart
	
	//考勤日期
	Date officeAttendanceDate=new Date()
	@GridColumn(name="考勤日期",colIdx=3)
	def getFormatteofficeAttendanceDate(){
		if(officeAttendanceDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(officeAttendanceDate)
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
		officeAttendanceDate nullable:true,blank:true
	}
	static belongsTo = [company:Company]

	
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_Attendance_OWorker"
	}
	
}
