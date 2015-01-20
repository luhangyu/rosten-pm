package com.rosten.app.employe
import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company


class WorkerAttendance {

	
	/*
	 员工考勤信息
	 */
	String id
	
	//账号名称
	@GridColumn(name="员工姓名",colIdx=1,width="100px",formatter="workerAttendance_formatTopic")
	String attendUserName
	
	//当前部门
	@GridColumn(name="部门",colIdx=2,width="100px")
	String attendDepart
	
	@GridColumn(name="出勤",colIdx=3,width="30px")
	Double workNumber = 1
	
	@GridColumn(name="事假",colIdx=4,width="30px")
	Double affairsNumber = 0
	
	@GridColumn(name="病假",colIdx=5,width="30px")
	Double illNumber = 0
	
	@GridColumn(name="旷工",colIdx=6,width="30px")
	Double awayNumber = 0
	
	@GridColumn(name="迟到",colIdx=7,width="30px")
	Double lateNumber = 0
	
	@GridColumn(name="早退",colIdx=8,width="30px")
	Double earlyAwayNumber = 0
	
	//创建日期
	Date createdDate = new Date()
	
	//备注
	@GridColumn(name="备注",colIdx=9)
	String attendRemark
	
	@GridColumn(name="操作",colIdx=91,width="40px",formatter="workerAttendance_action")
	def workerAttendanceId(){
		return id
	}
	
	static constraints = {
		affairsNumber nullable:true,blank:true
		illNumber nullable:true,blank:true
		awayNumber nullable:true,blank:true
		lateNumber nullable:true,blank:true
		earlyAwayNumber nullable:true,blank:true
		
		attendDepart nullable:true,blank:true
		
	}
	static belongsTo = [attendance:Attendance]

	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_EMP_WORKATT"
	}
	
}
