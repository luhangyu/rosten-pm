package com.rosten.app.base

import com.rosten.app.util.GridUtil
import com.rosten.app.system.Company

class BaseinforService {
	//公司信息
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
	
	
	
	//银行账号信息
	def getBankInforListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new BankInfor())
	}
	def getBankInforListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllBankInfor(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllBankInfor={offset,max,company,searchArgs->
		def c = BankInfor.createCriteria()
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
	def getBankInforCount ={company,searchArgs->
		def c = BankInfor.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	
	
	
	
	//往来单位信息
	def getContactCorpListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new ContactCorp())
	}
	def getContactCorpListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllContactCorp(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllContactCorp={offset,max,company,searchArgs->
		def c = ContactCorp.createCriteria()
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
	def getContactCorpCount ={company,searchArgs->
		def c = ContactCorp.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	
	
	//----------------------------------------------------1
	//供应商
	def getSupplierListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new Supplier())
	}
	def getSupplierListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllSupplier(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllSupplier={offset,max,company,searchArgs->
		def c = Supplier.createCriteria()
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
	def getSupplierCount ={company,searchArgs->
		def c = Supplier.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//----------------------------------------------------1
	
	
	
	//----------------------------------------------------2
	//材料信息
	def getMaterialInfoListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new MaterialInfo())
	}
	def getMaterialInfoListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllMaterialInfo(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllMaterialInfo={offset,max,company,searchArgs->
		def c = MaterialInfo.createCriteria()
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
	def getMaterialInfoCount ={company,searchArgs->
		def c = MaterialInfo.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//----------------------------------------------------2
	
	
	
	
	//----------------------------------------------------3
	//材料类型
	def getMaterialTypeListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new MaterialType())
	}
	def getMaterialTypeListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllMaterialType(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllMaterialType={offset,max,company,searchArgs->
		def c = MaterialType.createCriteria()
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
	def getMaterialTypeCount ={company,searchArgs->
		def c = MaterialType.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//----------------------------------------------------3
	
	
	//----------------------------------------------------4
	//物料单位 141220
	def getMaterialUnitListLayout ={
		def gridUtil = new GridUtil()
		return gridUtil.buildLayoutJSON(new MaterialUnit())
	}
	def getMaterialUnitListDataStore ={params,searchArgs->
		Integer offset = (params.offset)?params.offset.toInteger():0
		Integer max = (params.max)?params.max.toInteger():15
		def propertyList = getAllMaterialUnit(offset,max,params.company,searchArgs)

		def gridUtil = new GridUtil()
		return gridUtil.buildDataList("id","title",propertyList,offset)
	}
	private def getAllMaterialUnit={offset,max,company,searchArgs->
		def c = MaterialUnit.createCriteria()
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
	def getMaterialUnitCount ={company,searchArgs->
		def c = MaterialUnit.createCriteria()
		def query = {
			eq("company",company)
			searchArgs.each{k,v->
				like(k,"%" + v + "%")
			}
		}
		return c.count(query)
	}
	//----------------------------------------------------4
	
}
