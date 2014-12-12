package com.rosten.app.material
import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company


class PurchasePlan {
	/*
	 采购计划
	 */
	String id
	
	//账号名称
	@GridColumn(name="计划名称",width="100px",colIdx=1,formatter="purchasePlan_formatTopic")
	String purchasePlanName
	
	//当前部门
	@GridColumn(name="计划单号",width="160px",colIdx=2)
	String purchasePlanNo
	
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]

	static mapping = {
			id generator:'uuid.hex',params:[separator:'-']
			table "RS_MAT_PURPLAN"
		}
		
 
}
