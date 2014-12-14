package com.rosten.app.base
import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class MaterialInfo {
	//物料信息
    String id
	
	//物料名称
	@GridColumn(name="材料名称",colIdx=1,formatter="materialInfo_formatTopic")
	String matInfoName
	
	//物料类型
	@GridColumn(name="材料类型",colIdx=2)
	String matInfoType
	
	//物料类型
	@GridColumn(name="材料小类",colIdx=2)
	String matInfoSonType
	
	//采购材料单位
	@GridColumn(name="材料采购单位",colIdx=3)
	String matInfoPurchaseUnit
	
	//换算数量
	@GridColumn(name="换算数量",colIdx=3)
	double matInfoQuantity
	
	//材料领用单位
	@GridColumn(name="材料领用单位",colIdx=3)
	String matInfoGetUnit
	
	//材料规格
	String matInfoNorms
	
	//材料品牌
	String matInfoBrand
	
	//进价参考价
	double mInfoRefPrice
	
	//批发价
	double mInfoWPrice
	
	//零售价
	double mInfoRPrice
	
	//底线价
	double mInfoLowPrice
	
	String matInfoRemark
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BASE_MAT"
	}
}
