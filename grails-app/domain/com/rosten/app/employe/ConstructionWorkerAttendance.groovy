package com.rosten.app.employe
import java.util.Date;

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class ConstructionWorkerAttendance {
	/*
	 往来单位信息
	 */
	String id
	
	//单位名称
	@GridColumn(name="单位名称",colIdx=1,formatter="constructionWorkerAttendance_formatTopic")
	String ConstructionWorkerAttendanceName
	
	//类型甲方、分包商、材料商、监理单位
	@GridColumn(name="类型",colIdx=2,width="60px")
	String ConstructionWorkerAttendanceType
    static constraints = {
    }
	
	//备注
	String ConstructionWorkerAttendanceRemark

	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_EMP_CONWORK"
	}
	
}
