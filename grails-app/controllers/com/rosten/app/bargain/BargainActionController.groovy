package com.rosten.app.bargain


import grails.converters.JSON
import com.rosten.app.system.User
import com.rosten.app.system.Attachment

class BargainActionController {

	def imgPath ="images/rosten/actionbar/"
	
	def bargainWord ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		actionList << createAction("退出",webPath + imgPath + "quit_1.gif","page_quit")
		actionList << createAction("保存文档",webPath + imgPath + "Save.gif","word_save")
		actionList << createAction("打印文档",webPath + imgPath + "word_print.png","weboffice_print")
		actionList << createAction("隐藏/显示菜单",webPath + imgPath + "word_menu.png","word_menu")
		
		render actionList as JSON
	}
	def bargainConfigView ={
		def actionList =[]
		def strname = "bargainConfig"
		actionList << createAction("退出",imgPath + "quit_1.gif","returnToMain")
		actionList << createAction("保存",imgPath + "Save.gif",strname + "_save")
		
		render actionList as JSON
	}
	//承包合同start---------
	def bargainForm ={
		def webPath = request.getContextPath() + "/"
		def actionList = []
		def strname = "bargain"
		actionList << createAction("返回",webPath + imgPath + "quit_1.gif","page_quit")
		
		if(params.id){
			def entity = Bargain.get(params.id)
			
			def wordOLE = Attachment.findAllByBeUseIdAndType(params.id,"wordOLE")
			
			def user = User.get(params.userid)
			if(user.equals(entity.currentUser)){
				//当前处理人
				switch (true){
					case entity.status.contains("新增"):
						actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
						if(wordOLE){
							actionList << createAction("查看合同正文",webPath +imgPath + "word_new.png","bargain_readWord")
						}else{
							actionList << createAction("创建合同正文",webPath +imgPath + "word_new.png","bargain_addWord")
						}
						actionList << createAction("提交",webPath +imgPath + "submit.png",strname + "_submit")
						break;
					case entity.status.contains("审核") || entity.status.contains("审批"):
						actionList << createAction("查看合同正文",webPath +imgPath + "word_new.png","bargain_readWord")
						actionList << createAction("填写意见",webPath +imgPath + "sign.png",strname + "_addComment")
						actionList << createAction("同意",webPath +imgPath + "ok.png",strname + "_submit")
						actionList << createAction("退回",webPath +imgPath + "back.png",strname + "_back")
						break;
					case entity.status.contains("已签发"):
						actionList << createAction("查看合同正文",webPath +imgPath + "word_new.png","bargain_readWord")
						actionList << createAction("填写意见",webPath +imgPath + "sign.png",strname + "_addComment")
						actionList << createAction("结束流程",webPath +imgPath + "submit.png",strname +"_submit")
						break;
					default :
						actionList << createAction("查看合同正文",webPath +imgPath + "word_new.png","bargain_readWord")
						actionList << createAction("填写意见",webPath +imgPath + "sign.png",strname + "_addComment")
						actionList << createAction("提交",webPath +imgPath + "submit.png",strname + "_submit")
						break;
				}
			}
		}else{
			actionList << createAction("保存",webPath +imgPath + "Save.gif",strname + "_save")
		}
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
