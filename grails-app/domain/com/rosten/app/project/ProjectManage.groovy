package com.rosten.app.project
import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company
class ProjectManage {
	/*
	 项目
	 */
	String id
	
	//项目名称
	@GridColumn(name="项目名称",width="200px",colIdx=1,formatter="projectManage_formatTopic")
	String projName
	
	//项目编号
	@GridColumn(name="项目编号",width="160px",colIdx=2)
	String projNo
	
	//建设单位
	@GridColumn(name="建设单位",width="200px",colIdx=3)
	String constCorp
	//建设方代表
	String constCorpDele
	//建设方电话
	String constCorpPhone
	
	//监理单位
	@GridColumn(name="监理单位",width="200px",colIdx=4)
	String supCorp
	//监理方代表
	String supCorpDele
	//监理方电话
	String supCorpPhone
	//项目经理
	String projectManager
	//项目副经理
	String projAssManager
	//工地名称
	String projWorkPlace
	//工地地址
	String projWrkPlaceAdd
	
	//结构
	String projConst
	//工作量
	String projWrkload
	//开工日期
	Date projStartDate=new Date()
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
    }
	
	static belongsTo = [company:Company]

	static mapping = {
			id generator:'uuid.hex',params:[separator:'-']
			table "RS_PRO"
		}
}
