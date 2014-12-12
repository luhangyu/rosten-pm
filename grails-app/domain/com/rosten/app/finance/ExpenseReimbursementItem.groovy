package com.rosten.app.finance
import com.rosten.app.finance.ExpenseReimbursement
import java.text.SimpleDateFormat
import com.rosten.app.system.Company
import com.rosten.app.annotation.GridColumn
import com.rosten.app.util.SystemUtil

class ExpenseReimbursementItem {

	String id
	
	//发生日期
	Date ExpenseReimHappenDate=new Date()
	@GridColumn(name="发生日期",formatter="expenseReimburseItem_formatTopic",colIdx=1,width="100px")
	def getFormatteExpenseReimHappenDate(){
		if(ExpenseReimHappenDate!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd")
			return sd.format(ExpenseReimHappenDate)
		}else{
			return ""
		}
	}
	
	//费用类型
	@GridColumn(name="费用类型",width="160px",colIdx=2)
	String ExpenseReimItemType
	
	//报销金额
	@GridColumn(name="报销金额",width="160px",colIdx=3)
	Long ExpenseReimItemMoney
	
	//票据张数
	@GridColumn(name="票据张数",width="60px",colIdx=4)
	Long ExpenseReimItemPaperNum
	
	//备注
	@GridColumn(name="备注",colIdx=5)
	String ExpenseReimItemRemark
	
	
	
	static belongsTo = [expenseReimbursement:ExpenseReimbursement]
	
	static mapping = {
		id generator:'uuid.hex',params:[separator:'-']
		table "RS_FIN_EXPRBI"
	}
}
