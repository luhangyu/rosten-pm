package com.rosten.app.base

import java.util.Date;
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class CompanyInfor {
	/*
	 * 公司信息
	 */
	String id
	
	//公司名称
	@GridColumn(name="公司名称",colIdx=1,formatter="companyInfor_formatTopic")
	String companyName
	
	//简称
	@GridColumn(name="公司简称",colIdx=2,width="60px")
	String companyAbbreviation
	
	//电话号码
	@GridColumn(name="电话号码",colIdx=3,width="80px")
	String companyPhone

	//传真号码
	@GridColumn(name="传真号码",colIdx=4,width="80px")
	String companyFax

	//公司地址
	@GridColumn(name="公司地址",colIdx=5)
	String companyAddress
	
	//公司网址
	String companyWebsite
	
	//公司邮箱
	String companyMail
	
	//内容描述
	String description

	//创建日期
	Date createdDate = new Date()
	
    static constraints = {
		companyAbbreviation nullable:true,blank:true
		companyPhone nullable:true,blank:true
		companyFax nullable:true,blank:true
		companyAddress nullable:true,blank:true
		companyMail nullable:true,blank:true
		companyWebsite nullable:true,blank:true
		description nullable:true,blank:true
    }
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_COMPANY_INFOR"
	}
}
