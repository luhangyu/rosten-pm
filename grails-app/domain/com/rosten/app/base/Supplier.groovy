package com.rosten.app.base
import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class Supplier {
	//供应商管理
	String id
	
	//单位名称
	@GridColumn(name="供应商名称",colIdx=1,formatter="supplier_formatTopic")
	String suppName
	
	//单位名称
	@GridColumn(name="类型",colIdx=2,width="100px")
	String suppType
	
	//账号
	String suppBankAccNo
	//开户行
	String suppBankAccBank
	//户主
	String suppBankAccMaster
	

	//地址
	@GridColumn(name="地址",colIdx=5)
	String suppAddress
	
	//邮编
	String suppPost
	
	
	//联系电话
	@GridColumn(name="联系电话",colIdx=3,width="130px")
	String suppPhone
	
	//传真
	String suppFax
	
	//法人
	@GridColumn(name="法人",colIdx=4,width="100px")
	String suppLeader
	
	//法人职务
	String suppLeaderDuty
	//税务登记号
	String suppTax
	
	
	//网址
	String suppWebSite
	
	//email
	String suppEMail
	
	
	//助记码
	String suppCode
	
	//备注
	String suppRemark
	
	//供应商评分
	Long suppGrade
	
	//创建日期
	Date createdDate = new Date()
	
	static constraints = {
		suppRemark nullable:true,blank:true
		suppGrade nullable:true,blank:true
	}
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BASE_SUPP"
	}
}
