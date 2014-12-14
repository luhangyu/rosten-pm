package com.rosten.app.bargain

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company
import com.rosten.app.system.User;
import com.rosten.app.base.ContactCorp
import com.rosten.app.system.Attachment

class Bargain {

   /*
	 合同信息
	 */
	
	String id
	
	//合同名称
	@GridColumn(name="合同名称",colIdx=1,formatter="bargain_formatTopic")
	String bargainName
	
	//合同类型
	@GridColumn(name="合同类型",colIdx=2,width="60px")
	String bargainType
	
	//合同号
	@GridColumn(name="合同号",colIdx=3,width="60px")
	String bargainNo
	
	//合同金额
	@GridColumn(name="合同金额",colIdx=6)
	Long bargainMoney
	
	//甲方
	@GridColumn(name="合同甲方",colIdx=4,width="220px")
	String bargainVendor
	
	//合同甲方单位
	ContactCorp barVendorCorp
	
	//乙方
	@GridColumn(name="合同乙方",colIdx=5,width="220px")
	String bargainPurchaser
	
	//合同乙方单位
	ContactCorp barPurchaserCorp
	
	//制表人
	String bargainMaker
	
	Date bargainSignDate = new Date()
	
	@GridColumn(name="签订日期",colIdx=7)
	def getFormatteBargainSignDate(){
		if(bargainSignDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(bargainSignDate)
		}else{
			return ""
		}
	}
	
	//付款情况
	String bargainPayMemo
	
	//创建日期
	Date createdDate = new Date()
	
	
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
	
	List bargainGoods
	static hasMany=[bargainGoods:BargainGoods,hasReaders:User,readers:User]
	
	static constraints = {
		barVendorCorp nullable:true,blank:true
		bargainPurchaser nullable:true,blank:true
		bargainPayMemo nullable:true,blank:true
		
		//流程相关-------------------------------------------------------------
		defaultReaders nullable:true,blank:true
		currentUser nullable:true,blank:true
		currentDepart nullable:true,blank:true
		currentDealDate nullable:true,blank:true
		drafter nullable:true,blank:true
		drafterDepart nullable:true,blank:true
		
		processInstanceId nullable:true,blank:true
		taskId nullable:true,blank:true
		processDefinitionId nullable:true,blank:true
		//-------------------------------------------------------------------
		
	}
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BARGAIN"
	}
}
