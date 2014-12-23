package com.rosten.app.base

import grails.converters.JSON
import com.rosten.app.system.User

class BaseinforActionController {
	def imgPath ="images/rosten/actionbar/"
	
	//公司信息
	def companyInforForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "companyInfor"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
    def companyInforView ={
		def actionList =[]
		def user = User.get(params.userId)
		
		def strname = "companyInfor"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新增公司",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除公司",imgPath + "delete.png","delete_" + strname)
		}
		
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		
		render actionList as JSON
	}
	
	//银行账号信息141122
	def bankInforForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "bankInfor"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
	def bankInforView ={
		def actionList =[]
		def user = User.get(params.userId)
		def strname = "bankInfor"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新增银行账号",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除银行账号",imgPath + "delete.png","delete_" + strname)
		}
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		render actionList as JSON
	}
	
	//往来单位信息141126
	def contactCorpForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "contactCorp"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
	def contactCorpView ={
		def actionList =[]
		def user = User.get(params.userId)
		def strname = "contactCorp"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新增往来单位",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除",imgPath + "delete.png","delete_" + strname)
		}
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		render actionList as JSON
	}
	
	
	//供应商141205
	def supplierForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "supplier"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
	def supplierView ={
		def actionList =[]
		def user = User.get(params.userId)
		def strname = "supplier"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新增供应商",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除",imgPath + "delete.png","delete_" + strname)
		}
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		render actionList as JSON
	}
	
	//材料信息141205
	def materialInfoForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "materialInfo"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
	def materialInfoView ={
		def actionList =[]
		def user = User.get(params.userId)
		def strname = "materialInfo"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新增材料信息",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除",imgPath + "delete.png","delete_" + strname)
		}
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		render actionList as JSON
	}
	
	
	//材料分类141205
	def materialTypeForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "materialType"
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
	def materialTypeView ={
		def actionList =[]
		def user = User.get(params.userId)
		def strname = "materialType"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新增材料类别",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除",imgPath + "delete.png","delete_" + strname)
		}
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		render actionList as JSON
	}
	
	
	//141220物料单位
	def materialUnitForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "materialUnit"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
	def materialUnitView ={
		def actionList =[]
		def user = User.get(params.userId)
		def strname = "materialUnit"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新增",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除",imgPath + "delete.png","delete_" + strname)
		}
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		render actionList as JSON
	}
	
	//工种分类141205
	def workerTypeForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "workerType"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
	def workerTypeView ={
		def actionList =[]
		def user = User.get(params.userId)
		def strname = "workerType"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新增工种",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除",imgPath + "delete.png","delete_" + strname)
		}
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		render actionList as JSON
	}
	private def createAction={name,img,action->
		def model =[:]
		model["name"] = name
		model["img"] = img
		model["action"] = action
		return model
	}
}
