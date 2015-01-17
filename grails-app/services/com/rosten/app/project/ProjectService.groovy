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
	
	//项目计划start--------------------
	def getProjectPlanListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new ProjectPlan())
	}
	def getProjectPlanListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllProjectPlan(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllProjectPlan={offset,max,company,searchArgs->
		def c = ProjectPlan.createCriteria()
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
	def getProjectPlanCount ={company,searchArgs->
		def c = ProjectPlan.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//项目计划-end--------------------
	
	
	//施工方案start--------------------
	def getConstructApproveListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new ConstructApprove())
	}
	def getConstructApproveListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllConstructApprove(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllConstructApprove={offset,max,company,searchArgs->
		def c = ConstructApprove.createCriteria()
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
	def getConstructApproveCount ={company,searchArgs->
		def c = ConstructApprove.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//施工方案-end--------------------
	

	
	//施工日志--------------------------
	def getConstructLogListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new ConstructLog())
	}
	def getConstructLogListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllConstructLogList(offset,max,params.projectPlan,searchArgs)
		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllConstructLogList={offset,max,projectPlan,searchArgs->
		def c = ConstructLog.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("projectPlan",projectPlan)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.list(pa,query)
	}
	def getConstructLogCount ={projectPlan,searchArgs->
		def c = ConstructLog.createCriteria()
		def query = {
			eq("projectPlan",projectPlan)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	
	
}
