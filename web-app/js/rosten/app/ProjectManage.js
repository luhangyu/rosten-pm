/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
    
	
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
				gridSrc : rosten.webPath + "/project/projectManageGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		case "officeWorkerAttendance":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/employeAction/officeWorkerAttendanceView?userId=" + userid,
				gridSrc : rosten.webPath + "/employe/officeWorkerAttendanceGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		}
	}
	connect.connect("show_naviEntity", show_projectNaviEntity);
});
