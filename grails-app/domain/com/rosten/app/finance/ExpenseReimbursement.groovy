package com.rosten.app.finance
import java.util.Date;
import java.text.SimpleDateFormat

import com.rosten.app.annotation.GridColumn
import com.rosten.app.system.Company

class ExpenseReimbursement {

   /*
	 报销管理
	 */
	String id
	
	//项目名称
	@GridColumn(name="项目名称",width="100px",colIdx=1,formatter="expenseReimbursement_formatTopic")
	String ExpenseReimbursementName
	
	//项目归属部门
	String ExpenseReimbursementBelongDept
	
	//是否冲抵借款
	boolean IsOffset = false
	def getIsOffsetValue(){
		if(isOffset)return "是"
		else return "否"
	}
	
	//费用类型
	@GridColumn(name="费用类型",width="160px",colIdx=2)
	String ExpenseReimbursementType
	
	//票面金额
	@GridColumn(name="票面金额",width="160px",colIdx=3)
	Long ExpenseReimbursementBillMoney
	
	//实报金额
	@GridColumn(name="实报金额",width="160px",colIdx=4)
	Long ExpenseReimbursementMoney
	
	Long ExpenseReimbursementPaperNum
	
	String ExpenseReimbursementRemark
	
	String ExpenseReimbursementPerson
	
	//申请日期
	Date ExpenseReimbursementDate=new Date()
	def getFormatteExpenseReimbursementDate(){
		if(ExpenseReimbursementDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(ExpenseReimbursementDate)
		}else{
			return ""
		}
	}
	
	//创建日期
	Date createdDate = new Date()
	
	static belongsTo = [company:Company]
	
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_Finance_ExpReimburse"
	}
	
}
