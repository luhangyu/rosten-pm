package com.rosten.app.project
import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company


//项目计划
class ConstructApprove {
	String id
	
	//项目
	ProjectManage projectBelong
	
	@GridColumn(name="所属项目",width="200px",colIdx=1,formatter="constructApprove_formatTopic")
	def getProName(){
		if(projectBelong!=null){
			return projectBelong.projName
		}else{
			return ""
		}
	}
	
	//方案名称
	@GridColumn(name="方案名称",width="300px",colIdx=2)
	String approveName
	
	//方案要点
	String approvePoint
	
	
	//备注
	@GridColumn(name="备注",colIdx=4)
	String approveRemark
	
	
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
		table "RS_PRO_CONAPP"
	}
}
