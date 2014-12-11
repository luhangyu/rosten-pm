package com.rosten.app.base
import java.util.Date;
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class MaterialType {
	//物料分类
    String id
	
	//物料名称
	@GridColumn(name="父材料类型",colIdx=2)
	String materialTypeParentName
	
	//物料类型
	@GridColumn(name="类型名称",colIdx=1,formatter="materialType_formatTopic")
	String materialTypeName
	
	//物料计量单位
	@GridColumn(name="物料计量单位",colIdx=2)
	String materialTypeBaseUnit
	
	
	String materialTypeRemark
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_base_MaterialType"
	}
}
