package com.rosten.app.base
import java.util.Date;
import java.util.List;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class MaterialType {
	//物料分类
    String id
	
	//物料名称
	String matTypeName
	
	//物料计量单位
	String matTypeBaseUnit
	
	//显示顺序
	Integer serialNo
	
	//按排序号顺序获取
	def getSortMatType(){
		return this.children.sort{e1,e2->
			def _1 = e1.serialNo?e1.serialNo:100
			def _2 = e2.serialNo?e2.serialNo:100
			return _1 - _2
		}
	}
	
	//备注
	String matTypeRemark
	
	//创建日期
	Date createdDate = new Date()
	
	//上级
	MaterialType parent
	
	List children
	static hasMany = [children:MaterialType]
	
	static belongsTo = [company:Company]
	
	static constraints = {
		parent nullable:true,blank:true
		serialNo nullable:true,blank:true
		matTypeBaseUnit nullable:true,blank:true
		matTypeRemark nullable:true,blank:true
	}
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_BASE_MATTYPE"
	}
}
