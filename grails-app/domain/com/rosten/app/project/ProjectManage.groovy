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
	String projectName
	
	//项目编号
	@GridColumn(name="项目编号",width="160px",colIdx=2)
	String projectNo
	
	//建设单位
	@GridColumn(name="建设单位",width="200px",colIdx=3)
	String constructionCorp
	//建设方代表
	String constructionCorpRepresentative
	//建设方电话
	String constructionCorpPhone
	
	//监理单位
	@GridColumn(name="监理单位",width="200px",colIdx=4)
	String supervisorCorp
	//监理方代表
	String supervisorCorpRepresentative
	//监理方电话
	String supervisorCorpPhone
	//项目经理
	String projectManager
	//项目副经理
	String projectAssistantManager
	//工地名称
	String projectWorkPlace
	//工地地址
	String projectWorkPlaceAddress
	
	//结构
	String projectConstruction
	//工作量
	String projectWorkload
	//开工日期
	Date projectStartDate=new Date()
	def getFormatteprojectStartDate(){
		if(projectStartDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(projectStartDate)
		}else{
			return ""
		}
	}
	//计划竣工日期
	Date projectEndDate=new Date()
	def getFormatteprojectEndDate(){
		if(projectEndDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(projectEndDate)
		}else{
			return ""
		}
	}
	//材料预算全额
	Long projectMaterialMoney
	
	String projectRemark
	
	//创建日期
	Date createdDate = new Date()
	
    static constraints = {
    }
	
	static belongsTo = [company:Company]

	static mapping = {
			id generator:'uuid.hex',params:[separator:'-']
			table "RS_Project_ProjectManage"
		}
}
