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
	
	
	
	@GridColumn(name="出勤",colIdx=3,width="50px")
	Double workNumber
	
	@GridColumn(name="事假",colIdx=4,width="50px")
	Double affairsNumber
	
	@GridColumn(name="病假",colIdx=5,width="50px")
	Double illNumber
	
	@GridColumn(name="旷工",colIdx=6,width="50px")
	Double awayNumber
	
	@GridColumn(name="迟到",colIdx=7,width="50px")
	Double lateNumber
	
	@GridColumn(name="早退",colIdx=8,width="50px")
	Double earlyAwayNumber
	
	//创建日期
	Date createdDate = new Date()
	
	//备注
	//@GridColumn(name="备注",colIdx=91)
	String attendRemark
	
	@GridColumn(name="操作",colIdx=9,formatter="workerAttendance_action")
	def workerAttendanceId(){
		return id
	}
	
	static constraints = {
		
	}
	static belongsTo = [attendance:Attendance]

	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_EMP_WORKATT"
	}
	
}
