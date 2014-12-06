package com.rosten.app.project
import com.rosten.app.util.GridUtil
import com.rosten.app.system.Company

class ProjectService {
	//项目管理start--------------------
	def getProjectManageListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new ProjectManage())
	}
	def getProjectManageListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllProjectManage(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllProjectManage={offset,max,company,searchArgs->
		def c = ProjectManage.createCriteria()
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
	def getProjectManageCount ={company,searchArgs->
		def c = ProjectManage.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//项目管理-end--------------------

}
