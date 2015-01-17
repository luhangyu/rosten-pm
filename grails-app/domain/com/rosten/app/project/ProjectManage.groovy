package com.rosten.app.project
import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company
import com.rosten.app.base.ContactCorp

class ProjectManage {
	/*
	 项目
	 */
	String id
	
	//项目名称
	@GridColumn(name="项目名称",width="200px",colIdx=1,formatter="projectManage_formatTopic")
	String projName
	
	//项目编号
	@GridColumn(name="项目编号",width="110px",colIdx=2)
	String projNo
	
	//建设单位
	@GridColumn(name="建设单位",width="150px",colIdx=3)
	def getconstCorpName(){
		if(constCorp!=null){
			return constCorp.contactCorpName
		}else{
			return ""
		}
	}
	
	ContactCorp constCorp
	
	//建设方代表
	//String constCorpDele
	//建设方电话
	//String constCorpPhone
	
	//监理单位
	@GridColumn(name="监理单位",width="150px",colIdx=4)
	def getsupCorpName(){
		if(supCorp!=null){
			return supCorp.contactCorpName
		}else{
			return ""
		}
	}
	ContactCorp supCorp
	//监理方代表
	//String supCorpDele
	//监理方电话
	//String supCorpPhone
	//项目经理
	@GridColumn(name="项目经理",width="60px",colIdx=5)
	String projectManager
	//项目副经理
	String projAssManager
	//工地名称
	@GridColumn(name="工地名称",width="150px",colIdx=6)
	String projWorkPlace
	//工地地址
	String projWrkPlaceAdd
	
	//结构
	String projConst
	//工作量
	String projWrkload
	//开工日期
	Date projStartDate=new Date()
	@GridColumn(name="开工日期",width="100px",colIdx=7)
	def getFormatteprojectStartDate(){
		if(projStartDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(projStartDate)
		}else{
			return ""
		}
	}
	//计划竣工日期
	Date projEndDate=new Date()
	@GridColumn(name="计划竣工日期")
	def getFormatteprojectEndDate(){
		if(projEndDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(projEndDate)
		}else{
			return ""
		}
	}
	
	//材料预算全额
	Long projMatMoney
	
	String projRemark
	
	//创建日期
	Date createdDate = new Date()
	
    static constraints = {
		constCorp nullable:true,blank:true
		supCorp nullable:true,blank:true
		
    }
	
	static belongsTo = [company:Company]

	static mapping = {
			id generator:'uuid.hex',params:[separator:'-']
			table "RS_PRO"
		}
}
