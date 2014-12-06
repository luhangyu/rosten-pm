package com.rosten.app.bargain
import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company


class UndertakeBargain {

	/*
	 承包合同信息
	 */
	String id
	
	//合同名称
	@GridColumn(name="合同名称",colIdx=1,formatter="undertakeBargain_formatTopic")
	String undertakeBargainName
	
	//合同类型
	@GridColumn(name="合同类型",colIdx=2,width="60px")
	String undertakeBargainType
	
	//合同号
	@GridColumn(name="合同号",colIdx=3,width="60px")
	String undertakeBargainNo
	
	
	//合同金额
	@GridColumn(name="合同金额",colIdx=6)
	Long undertakeBargainMoney
	//合同甲方
	@GridColumn(name="合同甲方",colIdx=4,width="220px")
	String undertakeBargainVendor
	//合同甲方单位名称
	String undertakeBargainVendorCropName
	//合同甲方单位地址
	String undertakeBargainVendorAddress
	//合同甲方邮编
	String undertakeBargainVendorPost
	//合同甲方单位电话
	String undertakeBargainVendorPhone
	//合同甲方单位法人
	String undertakeBargainVendorBoss
	//合同甲方单位法人职务
	String undertakeBargainVendorDuty
	
	//合同乙方
	@GridColumn(name="合同乙方",colIdx=5,width="220px")
	String undertakeBargainPurchaser
	//合同乙方单位名称
	String undertakeBargainPurchaserCropName
	//合同乙方单位地址
	String undertakeBargainPurchaserAddress
	//合同乙方邮编
	String undertakeBargainPurchaserPost
	//合同乙方单位电话
	String undertakeBargainPurchaserPhone
	//合同乙方单位法人
	String undertakeBargainPurchaserBoss
	//合同乙方单位法人职务
	String undertakeBargainPurchaserDuty
	
	//制表人
	String undertakeBargainMaker
	
	//付款情况
	String undertakeBargainPayMemo
	
	//签订日期
	Date undertakeBargainSignDate= new Date()
	def getFormatteundertakeBargainSignDate(){
		if(undertakeBargainSignDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(undertakeBargainSignDate)
		}else{
			return ""
		}
	}
	
	
	//创建日期
	Date createdDate = new Date()
	
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_Bargain_Undertake"
	}
	
}
