/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
    
	
	//采购计划
	purchasePlan_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:purchasePlan_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	purchasePlan_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("purchasePlan", rosten.webPath + "/material/purchasePlanShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_purchasePlan = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("purchasePlan", rosten.webPath + "/material/purchasePlanAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_purchasePlan = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("purchasePlan", rosten.webPath + "/material/purchasePlanShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_purchasePlan = function() {
		change_purchasePlan();
	};
	delete_purchasePlan = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/material/purchasePlanDelete", content,rosten.deleteCallback);
		};
	};
	//采购计划end---------------------------
	
	
	//采购单管理-------start--------------------
	purchaseManage_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:purchaseManage_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	purchaseManage_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("purchaseManage", rosten.webPath + "/material/purchaseManageShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_purchaseManage = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("purchaseManage", rosten.webPath + "/material/purchaseManageAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_purchaseManage = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("purchaseManage", rosten.webPath + "/material/purchaseManageShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_purchaseManage = function() {
		change_purchaseManage();
	};
	delete_purchaseManage = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/material/purchaseManageDelete", content,rosten.deleteCallback);
		};
	};
	//采购单管理end---------------------------
	
	
	
	/*
	 * 此功能默认必须存在
	 */
	show_materialNaviEntity = function(oString) {
		console.log("show_materialNaviEntity:"+oString);
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		
		switch (oString) {
		case "purchasePlan":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/materialAction/purchasePlanView?userId=" + userid,
				gridSrc : rosten.webPath + "/material/purchasePlanGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		case "purchaseManage":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/materialAction/purchaseManageView?userId=" + userid,
				gridSrc : rosten.webPath + "/material/purchaseManageGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		}
	}
	connect.connect("show_naviEntity", show_materialNaviEntity);
});
