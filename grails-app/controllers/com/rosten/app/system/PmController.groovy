package com.rosten.app.system

import grails.converters.JSON;
import com.rosten.app.workflow.FlowBusiness

import com.rosten.app.system.SystemCode

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
			
//			resource = new Resource()
//			resource.resourceName = "材料类型"
//			resource.url = "materialType"
//			resource.imgUrl = "images/rosten/navigation/rosten.png"
//			resource.serialNo = 5
//			model.addToResources(resource)

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
			resource.resourceName = "合同配置"
			resource.url = "bargainConfig"
			resource.imgUrl = "images/rosten/navigation/rosten.png"
			model.addToResources(resource)
			
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
			
//			resource = new Resource()
//			resource.resourceName = "视频监控"
//			resource.url = "vedioMonitor"
//			resource.imgUrl = "images/rosten/navigation/rosten.png"
//			model.addToResources(resource)
			
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
			
			//初始化代码配置功能,处理方式：先删除所有代码信息，然后添加----------------------------
			SystemCode.list().each{
				it.delete()
			}
			
			//初始化代码
			def rs_techGrade = new SystemCode(code:"rs_techGrade",name:"专业技术等级",company:company)
			rs_techGrade.addToItems(new SystemCodeItem(code:"001",name:"高级",serialNo:1))
			rs_techGrade.addToItems(new SystemCodeItem(code:"002",name:"中级",serialNo:2))
			rs_techGrade.addToItems(new SystemCodeItem(code:"003",name:"初级",serialNo:3))
			rs_techGrade.save()
			
			def rs_politicsStatus = new SystemCode(code:"rs_politicsStatus",name:"政治面貌",company:company)
			rs_politicsStatus.addToItems(new SystemCodeItem(code:"001",name:"党员",serialNo:1))
			rs_politicsStatus.addToItems(new SystemCodeItem(code:"002",name:"预备党员",serialNo:2))
			rs_politicsStatus.addToItems(new SystemCodeItem(code:"003",name:"团员",serialNo:3))
			rs_politicsStatus.addToItems(new SystemCodeItem(code:"003",name:"群众",serialNo:4))
			rs_politicsStatus.save()
			
			def rs_nation = new SystemCode(code:"rs_nation",name:"民族",company:company)
			rs_nation.addToItems(new SystemCodeItem(code:"001",name:"汉族",serialNo:1))
			rs_nation.addToItems(new SystemCodeItem(code:"002",name:"满族",serialNo:2))
			rs_nation.addToItems(new SystemCodeItem(code:"003",name:"朝鲜族",serialNo:3))
			rs_nation.save()
			
			def rs_health = new SystemCode(code:"rs_health",name:"健康情况",company:company)
			rs_health.addToItems(new SystemCodeItem(code:"001",name:"优秀",serialNo:1))
			rs_health.addToItems(new SystemCodeItem(code:"002",name:"良好",serialNo:2))
			rs_health.addToItems(new SystemCodeItem(code:"003",name:"一般",serialNo:3))
			rs_health.addToItems(new SystemCodeItem(code:"003",name:"不好",serialNo:4))
			rs_health.save()
			
			def rs_country = new SystemCode(code:"rs_country",name:"国籍",company:company)
			rs_country.addToItems(new SystemCodeItem(code:"001",name:"中国",serialNo:1))
			rs_country.addToItems(new SystemCodeItem(code:"002",name:"美国",serialNo:2))
			rs_country.addToItems(new SystemCodeItem(code:"003",name:"韩国",serialNo:3))
			rs_country.addToItems(new SystemCodeItem(code:"003",name:"日本",serialNo:4))
			rs_country.save()
			
			def rs_blood = new SystemCode(code:"rs_blood",name:"血型",company:company)
			rs_blood.addToItems(new SystemCodeItem(code:"001",name:"A型",serialNo:1))
			rs_blood.addToItems(new SystemCodeItem(code:"002",name:"B型",serialNo:2))
			rs_blood.addToItems(new SystemCodeItem(code:"003",name:"O型",serialNo:3))
			rs_blood.addToItems(new SystemCodeItem(code:"003",name:"AB型",serialNo:4))
			rs_blood.save()
			
			def rs_bargainConfig = new SystemCode(code:"rs_bargainConfig",name:"合同类型",company:company)
			rs_bargainConfig.addToItems(new SystemCodeItem(code:"001",name:"劳务合同",serialNo:1))
			rs_bargainConfig.save()
			
			def rs_supplierType = new SystemCode(code:"rs_supplierType",name:"供应商类型",company:company)
			rs_supplierType.addToItems(new SystemCodeItem(code:"001",name:"甲方",serialNo:1))
			rs_supplierType.addToItems(new SystemCodeItem(code:"002",name:"已方",serialNo:2))
			rs_supplierType.save()
			
			def rs_contactCropType = new SystemCode(code:"rs_contactCropType",name:"单位类型",company:company)
			rs_contactCropType.addToItems(new SystemCodeItem(code:"001",name:"甲方",serialNo:1))
			rs_contactCropType.addToItems(new SystemCodeItem(code:"002",name:"已方",serialNo:2))
			rs_contactCropType.save()
			
			def rs_accountBankType = new SystemCode(code:"rs_accountBankType",name:"开户行类型",company:company)
			rs_accountBankType.addToItems(new SystemCodeItem(code:"001",name:"中国银行",serialNo:1))
			rs_accountBankType.addToItems(new SystemCodeItem(code:"002",name:"建设银行",serialNo:2))
			rs_accountBankType.addToItems(new SystemCodeItem(code:"003",name:"工商银行",serialNo:3))
			rs_accountBankType.addToItems(new SystemCodeItem(code:"003",name:"农业银行",serialNo:4))
			rs_accountBankType.save()
			
			//----------------------------------------------------------------
			
			json = [result:'true']
		}catch(Exception e){
			log.debug(e);
			json = [result:'error']
		}
		render json as JSON
	}
}
