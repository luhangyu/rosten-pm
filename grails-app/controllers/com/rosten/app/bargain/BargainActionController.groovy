package com.rosten.app.bargain


import grails.converters.JSON
import com.rosten.app.system.User

class BargainActionController {

	def imgPath ="images/rosten/actionbar/"
	
	//承包合同start---------
	def bargainForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "bargain"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		render actionList as JSON
	}
	def bargainView ={
		def actionList =[]
		def user = User.get(params.userId)
		
		def strname = "bargain"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("新建",imgPath + "add.png","add_"+ strname)
		
		if("admin".equals(user.getUserType()) || user.getAllRolesValue().contains("系统管理员")){
			actionList << createAction("删除",imgPath + "delete.png","delete_" + strname)
		}
		
		actionList << createAction("刷新",imgPath + "fresh.gif","freshGrid")
		
		render actionList as JSON
	}
	//承包合同end------
	
	
	
	
	private def createAction={name,img,action->
		def model =[:]
		model["name"] = name
		model["img"] = img
		model["action"] = action
		return model
	}
	
}
