package com.rosten.app.project
import java.util.Date;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company
import com.rosten.app.system.User;


//施工方案（带流程）
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
	
	
	//流程相关字段信息----------------------------------------------------------
	
	//当前处理人
	User currentUser

	@GridColumn(name="当前处理人",width="60px",colIdx=8)
	def getCurrentUserName(){
		if(currentUser!=null){
			return currentUser.getFormattedName()
		}else{
			return ""
		}
	}

	//当前处理部门
	String currentDepart
	
	//当前处理时间
	Date currentDealDate
	
	//缺省读者；*:允许所有人查看,[角色名称]:允许角色,user:普通人员查看
	String defaultReaders="[应用管理员]"
	def addDefaultReader(String userRole){
		if(defaultReaders==null || "".equals(defaultReaders)){
			defaultReaders = userRole
		}else{
			defaultReaders += "," + userRole
		}
	}
	
	//起草人
	User drafter

	def getFormattedDrafter(){
		if(drafter!=null){
			return drafter.getFormattedName()
		}else{
			return ""
		}
	}

	//起草部门
	String drafterDepart

	//流程定义id
	String processDefinitionId
	
	//流程id
	String processInstanceId
	
	//任务id
	String taskId
	
	//状态
	@GridColumn(name="状态",width="60px",colIdx=9)
	String status = "新增"
	
	//--------------------------------------------------------------------------
	
	
	static belongsTo = [company:Company]
	
    static constraints = {
    }
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_PRO_CONAPP"
	}
}
