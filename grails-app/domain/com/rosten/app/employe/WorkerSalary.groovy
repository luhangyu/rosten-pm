package com.rosten.app.employe
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class WorkerSalary {
	String id
	
	@GridColumn(name="员工姓名",colIdx=1,width="80px",formatter="workerSalary_formatTopic")
	String workerName
	
	@GridColumn(name="部门",colIdx=2,width="80px")
	String workerDepart
	
	@GridColumn(name="工资类型",colIdx=3,width="60px")
	String salaryType
	
	@GridColumn(name="工资",colIdx=4,width="40px")
	Double salaryRmb
	
	@GridColumn(name="生活费",colIdx=5,width="40px")
	Double livingRmb
	
	@GridColumn(name="应发",colIdx=6,width="40px")
	Double shouldRmb
	
	@GridColumn(name="扣除",colIdx=7,width="40px")
	Double deductRmb
	
	@GridColumn(name="奖励",colIdx=8,width="40px")
	Double bonusRmb
	
	@GridColumn(name="实发",colIdx=9,width="40px")
	Double finalRmb
	
	//备注
	@GridColumn(name="备注",colIdx=91)
	String wrkSlyRemark
	
	@GridColumn(name="操作",colIdx=92,width="40px",formatter="workerSalary_action")
	def workerSalaryId(){
		return id
	}
	
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [salary:Salary]
	
    static constraints = {
		wrkSlyRemark nullable:true,blank:true
		workerDepart nullable:true,blank:true
    }
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_EMP_WRKSLY"
	}
}
