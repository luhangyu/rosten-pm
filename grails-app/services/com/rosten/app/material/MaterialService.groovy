package com.rosten.app.material

import com.rosten.app.util.GridUtil
import com.rosten.app.system.Company

class MaterialService {

	//采购计划-start--------------------
	def getPurchasePlanListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new PurchasePlan())
	}
	def getPurchasePlanListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllPurchasePlan(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllPurchasePlan={offset,max,company,searchArgs->
		def c = PurchasePlan.createCriteria()
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
	def getPurchasePlanCount ={company,searchArgs->
		def c = PurchasePlan.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//采购计划-end--------------------
	
	
	//采购单管理-start--------------------
	def getPurchaseManageListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new PurchaseManage())
	}
	def getPurchaseManageListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllPurchaseManage(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllPurchaseManage={offset,max,company,searchArgs->
		def c = PurchaseManage.createCriteria()
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
	def getPurchaseManageCount ={company,searchArgs->
		def c = PurchaseManage.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//采购单管理-end--------------------
	
	
}
