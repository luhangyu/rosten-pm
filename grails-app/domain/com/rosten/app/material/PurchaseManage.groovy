package com.rosten.app.material
import java.util.Date;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class PurchaseManage {

    /*
	 采购单管理
	 */
	String id
	
	//账号名称
	@GridColumn(name="采购单名称",width="100px",colIdx=1,formatter="purchaseManage_formatTopic")
	String purchaseName
	
	//当前部门
	@GridColumn(name="采购单单号",width="160px",colIdx=2)
	String purchaseNo
	
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]

	static mapping = {
			id generator:'uuid.hex',params:[separator:'-']
			table "RS_Material_purchaseManage"
		}
}
