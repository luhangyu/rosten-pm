package com.rosten.app.employe

import com.rosten.app.util.GridUtil
import com.rosten.app.system.Company

class EmployeService {

   //点工考勤-start--------------------
	def getConstructionWorkerAttendanceListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new ConstructionWorkerAttendance())
	}
	def getConstructionWorkerAttendanceListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllConstructionWorkerAttendance(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllConstructionWorkerAttendance={offset,max,company,searchArgs->
		def c = ConstructionWorkerAttendance.createCriteria()
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
	def getConstructionWorkerAttendanceCount ={company,searchArgs->
		def c = ConstructionWorkerAttendance.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//点工考勤-end--------------------
	
	
	//员工行政人员考勤-start--------------------
	def getOfficeWorkerAttendanceListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new OfficeWorkerAttendance())
	}
	def getOfficeWorkerAttendanceListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllOfficeWorkerAttendance(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllOfficeWorkerAttendance={offset,max,company,searchArgs->
		def c = OfficeWorkerAttendance.createCriteria()
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
	def getOfficeWorkerAttendanceCount ={company,searchArgs->
		def c = OfficeWorkerAttendance.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//员工行政人员考勤-end--------------------
	
	
}
