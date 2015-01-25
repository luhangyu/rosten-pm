/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
    
	//项目管理搜索
	projectManage_search = function(){
		var content = {};
		
		var s_projName = registry.byId("s_projName");
		if(s_projName.get("value")!=""){
			content.projName = s_projName.get("value");
		}
		
		var s_projNo = registry.byId("s_projNo");
		if(s_projNo.get("value")!=""){
			content.projNo = s_projNo.get("value");
		}
		
		switch(rosten.kernel.navigationEntity) {
		default:
			rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
			break;
		}
	};
	projectManage_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		default:
			registry.byId("s_contactCorpType").set("value","");
			registry.byId("s_contactCorpName").set("value","");
			
			rosten.kernel.refreshGrid();
			break;
		}	
	};
	
	//项目管理
	projectManage_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:projectManage_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	projectManage_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("projectManage", rosten.webPath + "/project/projectManageShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_projectManage = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("projectManage", rosten.webPath + "/project/projectManageAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_projectManage = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("projectManage", rosten.webPath + "/project/projectManageShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_projectManage = function() {
		change_projectManage();
	};
	delete_projectManage = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/project/projectManageDelete", content,rosten.deleteCallback);
		};
	};
	//项目管理end---------------------------
	
	//项目计划start-----------------------
	projectPlan_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:projectPlan_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	projectPlan_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("projectPlan", rosten.webPath + "/project/projectPlanShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_projectPlan = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("projectPlan", rosten.webPath + "/project/projectPlanAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_projectPlan = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("projectPlan", rosten.webPath + "/project/projectPlanShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_projectPlan = function() {
		change_projectPlan();
	};
	delete_projectPlan = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/project/projectPlanDelete", content,rosten.deleteCallback);
		};
	};
	//项目计划end---------------------------
	
	
	

	//施工方案
	constructApprove_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:constructApprove_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	constructApprove_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("constructApprove", rosten.webPath + "/project/constructApproveShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_constructApprove = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("constructApprove", rosten.webPath + "/project/constructApproveAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_constructApprove = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("constructApprove", rosten.webPath + "/project/constructApproveShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_constructApprove = function() {
		change_constructApprove();
	};
	delete_constructApprove = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/project/constructApproveDelete", content,rosten.deleteCallback);
		};
	};
	//项目管理end---------------------------
	
	
	
	/*
	 * 此功能默认必须存在
	 */
	show_projectNaviEntity = function(oString) {
		
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		
		switch (oString) {
		case "projectManage":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/projectAction/projectManageView?userId=" + userid,
				searchSrc:rosten.webPath + "/project/projectManageSearchView",
				gridSrc : rosten.webPath + "/project/projectManageGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "projectPlan":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/projectAction/projectPlanView?userId=" + userid,
				gridSrc : rosten.webPath + "/project/projectPlanGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "constructApprove":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/projectAction/constructApproveView?userId=" + userid,
				gridSrc : rosten.webPath + "/project/constructApproveGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		}
	}
	connect.connect("show_naviEntity", show_projectNaviEntity);
});
