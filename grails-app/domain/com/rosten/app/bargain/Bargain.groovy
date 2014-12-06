package com.rosten.app.bargain

import java.util.Date;
import java.text.SimpleDateFormat
import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company
import com.rosten.app.base.ContactCorp



class Bargain {

   /*
	 合同信息
	 */
	String id
	
	//合同名称
	@GridColumn(name="合同名称",colIdx=1,formatter="bargain_formatTopic")
	String BargainName
	
	//合同类型
	@GridColumn(name="合同类型",colIdx=2,width="60px")
	String BargainType
	
	//合同号
	@GridColumn(name="合同号",colIdx=3,width="60px")
	String BargainNo
	
	//合同金额
	@GridColumn(name="合同金额",colIdx=6)
	Long BargainMoney
	
	//合同甲方
	ContactCorp BargainVendor
	@GridColumn(name="合同甲方",colIdx=4,width="220px")
	def getContactCorpName(){
		return BargainVendor?.contactCorpName
	}
	
		
	//合同乙方
	@GridColumn(name="合同乙方",colIdx=5,width="220px")
	ContactCorp BargainPurchaser
	
	
	//制表人
	String BargainMaker
	
	//付款情况
	String BargainPayMemo
	
	//签订日期
	Date BargainSignDate = new Date()
	def getFormatteBargainSignDate(){
		if(BargainSignDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(BargainSignDate)
		}else{
			return ""
		}
	}
	
	
	//创建日期
	Date createdDate = new Date()
	
	
	static belongsTo = [company:Company]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_Bargain"
	}
}
