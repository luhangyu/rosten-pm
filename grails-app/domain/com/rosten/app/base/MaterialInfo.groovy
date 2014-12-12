package com.rosten.app.base
import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class MaterialInfo {
	//物料信息
    String id
	
	//物料名称
	@GridColumn(name="材料名称",colIdx=1,formatter="materialInfo_formatTopic")
	String materialInfoName
	
	//物料类型
	@GridColumn(name="材料类型",colIdx=2)
	String materialInfoType
	
	//物料类型
	@GridColumn(name="材料小类",colIdx=2)
	String materialInfoSonType
	
	//采购材料单位
	@GridColumn(name="材料采购单位",colIdx=3)
	String materialInfoPurchaseUnit
	
	//换算数量
	@GridColumn(name="换算数量",colIdx=3)
	double materialInfoQuantity
	
	//材料领用单位
	@GridColumn(name="材料领用单位",colIdx=3)
	String materialInfoGetUnit
	
	//材料规格
	String materialInfoSpecification
	
	//材料品牌
	String materialInfoBrand
	
	//进价参考价
	double materialInfoRefPrice
	
	//批发价
	double materialInfoWPrice
	
	//零售价
	double materialInfoRPrice
	
	//底线价
	double materialInfoLowestPrice
	
	String materialInfoRemark
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BASE_MAT"
	}
}
