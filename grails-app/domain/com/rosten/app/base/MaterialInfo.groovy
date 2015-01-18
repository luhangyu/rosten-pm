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
	def getMatInforType(){
		return matInfoType?.matTypeName
	}
	MaterialType matInfoType
	
	//物料类型
	@GridColumn(name="材料小类",colIdx=3)
	String matInfoSonType
	
	//采购材料单位
	@GridColumn(name="大单位",colIdx=5)
	def getMatInfoPurUnit(){
		return matInfoPurUnit?.matUnitName
	}
	MaterialUnit matInfoPurUnit
	
	//材料领用单位
	@GridColumn(name="小单位",colIdx=6)
	def getMatInfoGetUnit(){
		return matInfoPurUnit?.matUnitName
	}
	MaterialUnit matInfoGetUnit
	
	//换算数量
	@GridColumn(name="换算数量",colIdx=4)
	double matInfoQuantity
	
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
	
	static constraints = {
		matInfoGetUnit nullable:true,blank:true
		matInfoPurUnit nullable:true,blank:true
		matInfoType nullable:true,blank:true
	}
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BASE_MAT"
	}
}
