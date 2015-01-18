package com.rosten.app.employe
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class Attendance {
	String id
	
	//考勤日期
	Date attendDate=new Date()
	@GridColumn(name="考勤日期",colIdx=1,width="100px",formatter="attendance_formatTopic")
	def getFormatAttenDate(){
		if(attendDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(attendDate)
		}else{
			return ""
		}
	}
	
	@GridColumn(name="考勤类型",colIdx=2,width="100px")
	String attendType
	
	@GridColumn(name="填写人",colIdx=3,width="100px")
	String attendDrafter
	
	
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]
	
	List workerAttends
	
	static hasMany=[workerAttends:WorkerAttendance]
	
    static constraints = {
    }
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_EMP_ATTEND"
	}
}
