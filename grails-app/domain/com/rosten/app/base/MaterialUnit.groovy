package com.rosten.app.base

import java.util.Date;
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class MaterialUnit {

	String id
	
	//物料单位名称
	@GridColumn(name="物料单位名称",colIdx=1,formatter="materialUnit_formatTopic")
	String matUnitName
	
	//换算单位名称
	@GridColumn(name="换算单位名称",colIdx=2)
	String matToUnitName
	
	//状态：废弃？正常？
	@GridColumn(name="换算值",colIdx=3)
	Long matUnitConv
	
	String  matUnitRemark
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BASE_MATUNIT"
	}
}
