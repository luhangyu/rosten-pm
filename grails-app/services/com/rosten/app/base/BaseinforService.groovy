package com.rosten.app.base

import com.rosten.app.util.GridUtil
import com.rosten.app.system.Company

class BaseinforService {
	def getCompanyInforListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new CompanyInfor())
	}
	def getCompanyInforListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllCompanyInfor(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllCompanyInfor={offset,max,company,searchArgs->
		def c = CompanyInfor.createCriteria()
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
	def getCompanyInforCount ={company,searchArgs->
		def c = CompanyInfor.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
}
