package com.rosten.app.system

import grails.converters.JSON;

class PmController {
	def systemService
	
	def modelInit ={
		def json,model,resource
		def company = Company.get(params.id)
		def path = request.contextPath
		
		try{
			//删除当前单位下面的所有模块信息（除系统管理等基础模块）
			def modelCodes = ["system","workflow","public","sms","question","personconfig"]
			Model.findAllByCompany(company).each{
				if(!modelCodes.contains(it.modelCode)){
					it.delete()
				}
			}
			//增加资产系统特有的功能模块
			model = new Model(company:company)
			model.modelName = "基本信息"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "baseInfor"
			model.serialNo = 4
			
			resource = new Resource()
			resource.resourceName = "公司信息"
			resource.url = "companyInfor"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "银行账号"
			resource.url = "bankInfor"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "员工管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "staffManage"
			model.serialNo = 5
					
			resource = new Resource()
			resource.resourceName = "员工考勤"
			resource.url = "staffCheckin"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
					
			resource = new Resource()
			resource.resourceName = "大点工考勤"
			resource.url = "djgcheckin"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "工资发放"
			resource.url = "salarySend"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "工资汇总"
			resource.url = "salaryStatic"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "合同管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "bargainManage"
			model.serialNo = 6
			
			resource = new Resource()
			resource.resourceName = "承包合同"
			resource.url = "cbBargain"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "分包合同"
			resource.url = "fbBargain"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "采购合同"
			resource.url = "cgBargain"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "其他合同"
			resource.url = "qtBargain"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "项目管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "planManage"
			model.serialNo = 7
			
			resource = new Resource()
			resource.resourceName = "项目管理"
			resource.url = "planManage"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "施工方案审批"
			resource.url = "constructApprove"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "施工日志"
			resource.url = "constructLog"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "视频监控"
			resource.url = "vedioCheck"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "财务管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "financeManage"
			model.serialNo = 8
			
			resource = new Resource()
			resource.resourceName = "报销管理"
			resource.url = "bxgl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "借支管理"
			resource.url = "jzgl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "费用报备"
			resource.url = "fybb"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "应付款管理"
			resource.url = "yfkgl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "物资管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "wzManage"
			model.serialNo = 9
			
			resource = new Resource()
			resource.resourceName = "采购计划"
			resource.url = "cgjh"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "采购管理"
			resource.url = "cggl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "库房管理"
			resource.url = "kfgl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "入库"
			resource.url = "rk"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "出库"
			resource.url = "ck"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "信息管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "inforManage"
			model.serialNo = 10
			
			resource = new Resource()
			resource.resourceName = "往来单位管理"
			resource.url = "wldwgl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "供应商评价"
			resource.url = "gyspj"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "材料分类管理"
			resource.url = "clflgl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "材料信息管理"
			resource.url = "clxxgl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "工作分类管理"
			resource.url = "gzflgl"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "统计分析"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "static"
			model.description ="统计分析"
			model.serialNo = 11
			
			resource = new Resource()
			resource.resourceName = "统计分析"
			resource.url = "static"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			//增加人事系统特有的服务列表信息
			NormalService.findAllByCompany(company).each{
				it.delete()
			}
			
			systemService.initData_service(path,company)
			
			json = [result:'true']
		}catch(Exception e){
			log.debug(e);
			json = [result:'error']
		}
		render json as JSON
	}
}
