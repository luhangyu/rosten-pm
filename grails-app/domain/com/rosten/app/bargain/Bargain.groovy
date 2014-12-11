package com.rosten.app.bargain

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company
import com.rosten.app.base.ContactCorp
import com.rosten.app.system.Attachment



class Bargain {

   /*
	 合同信息
	 */
	
	String id
	
	//合同名称
	@GridColumn(name="合同名称",colIdx=1,formatter="bargain_formatTopic")
	String bargainName
	
	//合同类型
	@GridColumn(name="合同类型",colIdx=2,width="60px")
	String bargainType
	
	//合同号
	@GridColumn(name="合同号",colIdx=3,width="60px")
	String bargainNo
	
	//合同金额
	@GridColumn(name="合同金额",colIdx=6)
	Long bargainMoney
	
	
	
	//甲方
	@GridColumn(name="合同甲方",colIdx=4,width="220px")
	String bargainVendor
	
	//合同甲方单位名称
	ContactCorp BargainVendorCorpName
	def getBargainVendorName(){
		return BargainVendorCorpName?.contactCorpName
	}
	
	//乙方
	@GridColumn(name="合同乙方",colIdx=5,width="220px")
	String bargainPurchaser
	
	//合同乙方单位名称
	ContactCorp BargainPurchaserCorpName
	def getBargainPurchaserName(){
		return BargainPurchaserCorpName?.contactCorpName
	}
	
	//制表人
	String bargainMaker
	
	Date bargainSigningDate = new Date()
	
	@GridColumn(name="签订日期",colIdx=7)
	def getFormatteBargainSignDate(){
		if(bargainSigningDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(bargainSigningDate)
		}else{
			return ""
		}
	}
	
	//付款情况
	String bargainPayMemo
	
	//附件
	//Attachment attachment
	
	
	//创建日期
	Date createdDate = new Date()
	
	
	static belongsTo = [company:Company]
	
	List items
	static hasMany=[bargainGoods:BargainGoods]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_Bargain"
	}
}
