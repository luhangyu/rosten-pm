package com.rosten.app.finance
import com.rosten.app.util.GridUtil
import com.rosten.app.system.Company

class FinanceService {

    //报销管理start--------------------
	def getExpenseReimbursementItemListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new ExpenseReimbursementItem())
	}
	def getExpenseReimbursementItemListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllExpenseReimbursementItemListItem(offset,max,params.ExpenseReimbursement,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllExpenseReimbursementItemListItem={offset,max,ExpenseReimbursement,searchArgs->
		def c = ExpenseReimbursementItem.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("expenseReimbursement",ExpenseReimbursement)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.list(pa,query)
	}
	def getExpenseReimbursementItemCount ={trainCourse,searchArgs->
		def c = ExpenseReimbursementItem.createCriteria()
		def query = {
			eq("expenseReimbursement",expenseReimbursement)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	
	
	
	
	def getExpenseReimbursementListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new ExpenseReimbursement())
	}
	def getExpenseReimbursementListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllExpenseReimbursement(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllExpenseReimbursement={offset,max,company,searchArgs->
		def c = ExpenseReimbursement.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("company",company)
			order("createdDate", "desc")
			
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.list(pa,query)
	}
	def getExpenseReimbursementCount ={company,searchArgs->
		def c = ExpenseReimbursement.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//报销管理-end--------------------
}
