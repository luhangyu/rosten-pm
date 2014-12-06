package com.rosten.app.base

import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class ContactCorp {

	/*
	 往来单位信息
	 */
	String id
	
	//单位名称
	@GridColumn(name="单位名称",colIdx=1,formatter="contactCorp_formatTopic")
	String contactCorpName
	
	//类型甲方、分包商、材料商、监理单位
	@GridColumn(name="类型",colIdx=2,width="60px")
	String contactCorpType
	
	//省份
	String contactCorpProvince

	//地址
	String contactCorpAddress

	//联系人
	String contactCorpLinkMan
	
	//联系电话
	String contactCorpPhone
	
	//传真
	String contactCorpFax
	
	//主账户
	@GridColumn(name="法人",colIdx=4,width="80px")
	String contactCorpLealPerson
	
	//税务登记号
	String contactCorpTax
	
	//营业执照
	String contactCorpLicense
	
	//网址
	String contactCorpWebSite
	
	//email
	String contactCorpEMail
	
	//备注
	String contactCorpRemark

	//创建日期
	Date createdDate = new Date()
	
	static constraints = {
		contactCorpRemark nullable:true,blank:true
		contactCorpType nullable:true,blank:true
	}
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_contactcorp_infor"
	}
  
	
	
}
