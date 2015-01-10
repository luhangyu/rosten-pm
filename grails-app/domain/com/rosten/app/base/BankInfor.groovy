package com.rosten.app.base

import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class BankInfor {
	/*
	 银行账号信息
	 */
	String id
	
	//账号名称
	@GridColumn(name="账号名称",colIdx=1,formatter="bankInfor_formatTopic")
	String accountName
	
	boolean accountIsDef = false
	
	//开户行
	@GridColumn(name="开户行",colIdx=2,width="60px")
	String accountBank
	
	//户主
	@GridColumn(name="户主",colIdx=3,width="80px")
	String accountMaster

	//主账户
//	@GridColumn(name="主账户",colIdx=4,width="80px")
//	String accountDefault

	//备注
	String accountRemark

	//创建日期
	Date createdDate = new Date()
	
    static constraints = {
		accountRemark nullable:true,blank:true
    }
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BASE_BANK"
	}
	
}
