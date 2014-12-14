package com.rosten.app.finance
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class ExpenseReimbursement {

   /*
	 报销管理
	 */
	String id
	
	//项目名称
	@GridColumn(name="项目名称",width="180px",colIdx=1,formatter="expenseReimbursement_formatTopic")
	String expRmbName
	
	//项目归属部门
	String expRmbBelongDept
	
	//是否冲抵借款
	boolean IsOffset = false
	def getIsOffsetValue(){
		if(isOffset)return "是"
		else return "否"
	}
	
	//费用类型
	@GridColumn(name="费用类型",width="160px",colIdx=3)
	String expRmbType
	
	//票面金额
	@GridColumn(name="票面金额",width="160px",colIdx=5)
	Long expRmbBillMoney
	
	//实报金额
	@GridColumn(name="实报金额",width="160px",colIdx=4)
	Long expRmbMoney
	
	//实报金额
	@GridColumn(name="票据张数",width="60px",colIdx=6)
	Long expRmbPaperNum
	
	String expRmbRemark
	
	//实报金额
	@GridColumn(name="报销人",width="80px",colIdx=2)
	String expRmbPerson
	
	//报销日期
	@GridColumn(name="报销日期",colIdx=7)
	Date expRmbDate=new Date()
	def getFormatteExpRmbDate(){
		if(expRmbDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(expRmbDate)
		}else{
			return ""
		}
	}
	
	//创建日期
	Date createdDate = new Date()
	
	
	//报销清单
	List items
	static hasMany=[items:ExpenseReimbursementItem]
	
	static belongsTo = [company:Company]
	
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_FIN_EXPRB"
	}
	
}
