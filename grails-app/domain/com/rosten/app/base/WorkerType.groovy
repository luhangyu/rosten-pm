package com.rosten.app.base
import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class WorkerType {

	//工种
	String id
	
	//物料名称
	@GridColumn(name="工种名称",colIdx=1,formatter="workerType_formatTopic")
	String workerTypeName
	
	//物料类型
	@GridColumn(name="父工种",colIdx=2)
	String workerTypeParentName
	
	//状态：废弃？正常？
	@GridColumn(name="状态",colIdx=3)
	String workerTypeStatus
	
	
	String  workerTypeRemark
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_base_WorkerType"
	}
	
}