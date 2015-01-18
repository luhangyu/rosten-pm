package com.rosten.app.employe

import com.rosten.app.util.GridUtil
import com.rosten.app.system.Company

class EmployeService {

   //考勤父类-start--------------------
	def getAttendanceListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new Attendance())
	}
	def getAttendanceListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllAttendance(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllAttendance={offset,max,company,searchArgs->
		def c = Attendance.createCriteria()
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
	def getAttendanceCount ={company,searchArgs->
		def c = Attendance.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//end--------------------
	
	
	//考勤子类-start--------------------
	def getWorkerAttendanceListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new WorkerAttendance())
	}
	def getWorkerAttendanceListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllWorkerAttendance(offset,max,params.attendance,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllWorkerAttendance={offset,max,attendance,searchArgs->
		def c = WorkerAttendance.createCriteria()
		def pa=[max:max,offset:offset]
		def query = {
			eq("attendance",attendance)
			order("createdDate", "desc")
			
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.list(pa,query)
	}
	def getWorkerAttendanceCount ={attendance,searchArgs->
		def c = WorkerAttendance.createCriteria()
		def query = {
			eq("attendance",attendance)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//考勤子类-end--------------------
	
	
}
