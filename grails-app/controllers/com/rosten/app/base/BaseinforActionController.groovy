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
	
	
	
	private def createAction={name,img,action->
		def model =[:]
		model["name"] = name
		model["img"] = img
		model["action"] = action
		return model
	}
}
