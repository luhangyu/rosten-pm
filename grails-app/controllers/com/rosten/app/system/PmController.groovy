package com.rosten.app.system

import grails.converters.JSON;
import com.rosten.app.workflow.FlowBusiness

class PmController {
	def systemService
	
	def modelInit ={
		/*
		 * 初始化菜单功能序号统一从7开始
		 */
		
		def json,model,resource
		def company = Company.get(params.id)
		def path = request.contextPath
		
		try{
			//删除当前单位下面的所有模块信息（除系统管理等基础模块）
			def modelCodes = ["system","workflow","public","sms","question","personconfig"]
			Model.findAllByCompany(company).each{
				if(!modelCodes.contains(it.modelCode)){
					FlowBusiness.findAllByModel(it).each{item->
						item.model = null
						item.save()
					}
					it.delete()
				}
			}
			
			//增加系统特有的功能模块
			model = new Model(company:company)
			model.modelName = "基础信息"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "baseInfor"
			model.serialNo = 7
			
			resource = new Resource()
			resource.resourceName = "公司信息"
			resource.url = "companyInfor"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			resource.serialNo = 1
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "银行账号"
			resource.url = "bankInfor"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			resource.serialNo = 2
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "往来单位"
			resource.url = "contactCorp"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			resource.serialNo = 3
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "供应商"
			resource.url = "supplier"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			resource.serialNo = 4
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "材料类型"
			resource.url = "materialType"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			resource.serialNo = 5
			model.addToResources(resource)

			resource = new Resource()
			resource.resourceName = "材料单位"
			resource.url = "materialUnit"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			resource.serialNo = 6
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "材料信息"
			resource.url = "materialInfo"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			resource.serialNo = 7
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "员工管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "employe"
			model.serialNo = 8
					
			resource = new Resource()
			resource.resourceName = "账号管理"
			resource.url = "staffManage"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "员工管理"
			resource.url = "staffRegi"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "员工考勤"
			resource.url = "officeWorkerAttendance"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
					
			resource = new Resource()
			resource.resourceName = "大点工考勤"
			resource.url = "constructionWorkerAttendance"
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
			model.modelName = "通知公告"
			model.modelCode = "bbs"
			model.modelUrl = path + "/system/navigation"
			model.serialNo = 9
			model.description ="公告栏模块"
	
			resource = new Resource()
			resource.resourceName = "配置文档"
			resource.url = "bbsConfigManage"
			resource.imgUrl = "images/rosten/navigation/config.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "各人待办"
			resource.url = "mybbsManage"
			resource.imgUrl = "images/rosten/navigation/bbs_my.gif"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "最新公告"
			resource.url = "newbbsManage"
			resource.imgUrl = "images/rosten/navigation/bbs_new.gif"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "所有公告"
			resource.url = "allbbsManage"
			resource.imgUrl = "images/rosten/navigation/bbs_all.gif"
			model.addToResources(resource)
			
			model.save()
			
			
			model = new Model(company:company)
			model.modelName = "合同管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "bargainManage"
			model.serialNo = 10
			
			resource = new Resource()
			resource.resourceName = "总包合同"
			resource.url = "totalpackageBargain"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "分包合同"
			resource.url = "subpackageBargain"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "采购合同"
			resource.url = "purchaseBargain"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "销售合同"
			resource.url = "salesBargain"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "项目管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "project"
			model.serialNo = 11
			
			resource = new Resource()
			resource.resourceName = "项目管理"
			resource.url = "projectManage"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "施工方案"
			resource.url = "constructApprove"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "项目计划"
			resource.url = "projectPlan"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "施工日志"
			resource.url = "constructLog"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			
			resource = new Resource()
			resource.resourceName = "视频监控"
			resource.url = "vedioMonitor"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "财务管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "finance"
			model.serialNo = 12
			
			resource = new Resource()
			resource.resourceName = "报销管理"
			resource.url = "expenseReimbursement"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "借支管理"
			resource.url = "borrowManage"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "费用报备"
			resource.url = "expenseReport"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "还款管理"
			resource.url = "repayment"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "应付款管理"
			resource.url = "paymentPlan"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "应收款管理"
			resource.url = "receiptPlan"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "付款"
			resource.url = "payment"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "收款"
			resource.url = "receipt"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "物资管理"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "material"
			model.serialNo = 13
			
			resource = new Resource()
			resource.resourceName = "采购计划"
			resource.url = "purchasePlan"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "采购管理"
			resource.url = "purchaseManage"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "库房管理"
			resource.url = "warehouseManage"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "入库"
			resource.url = "warehouseIn"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "出库"
			resource.url = "warehouseOut"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "报损"
			resource.url = "breakage"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "损益"
			resource.url = "profitAndLoss"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "调拨"
			resource.url = "transfer"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			resource = new Resource()
			resource.resourceName = "盘点"
			resource.url = "check"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
			model.save()
			
			model = new Model(company:company)
			model.modelName = "统计分析"
			model.modelUrl = path + "/system/navigation"
			model.modelCode = "static"
			model.description ="统计分析"
			model.serialNo = 14
			
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
