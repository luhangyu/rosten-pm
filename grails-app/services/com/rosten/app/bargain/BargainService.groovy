package com.rosten.app.bargain

import com.rosten.app.util.GridUtil
import com.rosten.app.system.Company

class BargainService {

    //承包合同-start--------------------
	def getBargainListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new Bargain())
	}
	def getBargainListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllBargain(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllBargain={offset,max,company,searchArgs->
		def c = Bargain.createCriteria()
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
	def getBargainCount ={company,searchArgs->
		def c = Bargain.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//点工考勤-end--------------------
}
