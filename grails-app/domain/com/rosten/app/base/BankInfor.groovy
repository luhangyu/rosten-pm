package com.rosten.app.base

import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class BankInfor {
	/*
	 银行账号信息
	 */
	String id
	
	@GridColumn(name="默认",colIdx=1,width="30px",formatter="formatIsDefault")
	boolean accountIsDef = false
	
	//账号名称
	@GridColumn(name="账号名称",colIdx=2,width="500px",formatter="bankInfor_formatTopic")
	String accountName
	
	//开户行
	@GridColumn(name="开户行",colIdx=3,width="200px")
	String accountBank
	
	//户主
	@GridColumn(name="户主",colIdx=4,width="180px")
	String accountMaster

	//备注
	@GridColumn(name="备注",colIdx=5)
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
