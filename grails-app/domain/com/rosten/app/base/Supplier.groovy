package com.rosten.app.base
import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class Supplier {
	//供应商管理
	String id
	
	//单位名称
	@GridColumn(name="客户名称",colIdx=1,formatter="supplier_formatTopic")
	String supplierName
	
	
	//账号
	String supplierBankAccountNo
	//开户行
	String supplierBankAccountBank
	//户主
	String supplierBankAccountMaster
	

	//地址
	@GridColumn(name="地址",colIdx=2,width="280px")
	String supplierAddress
	
	//邮编
	String supplierPost
	
	
	//联系电话
	@GridColumn(name="联系电话",colIdx=7,width="80px")
	String supplierPhone
	
	//传真
	String supplierFax
	
	//法人
	@GridColumn(name="法人",colIdx=4,width="80px")
	String supplierLealPerson
	
	//法人职务
	String supplierLealPersonDuty
	//税务登记号
	String supplierTax
	
	
	//网址
	String supplierWebSite
	
	//email
	String supplierEMail
	
	
	//助记码
	String supplierCode
	
	//备注
	String supplierRemark
	
	//供应商评分
	Long supplierGrade
	
	//创建日期
	Date createdDate = new Date()
	
	static constraints = {
		supplierRemark nullable:true,blank:true
		
	}
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_base_supplier"
	}
}
