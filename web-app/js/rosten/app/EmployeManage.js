/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
    
	
	//点工考勤
	constructionWorkerAttendance_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:constructionWorkerAttendance_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	constructionWorkerAttendance_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("constructionWorkerAttendance", rosten.webPath + "/employe/constructionWorkerAttendanceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_constructionWorkerAttendance = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("constructionWorkerAttendance", rosten.webPath + "/employe/constructionWorkerAttendanceAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_constructionWorkerAttendance = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("constructionWorkerAttendance", rosten.webPath + "/employe/constructionWorkerAttendanceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_constructionWorkerAttendance = function() {
		change_constructionWorkerAttendance();
	};
	delete_constructionWorkerAttendance = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/employe/constructionWorkerAttendanceDelete", content,rosten.deleteCallback);
		};
	};
	//点工考勤end---------------------------
	
	//员工考勤start----------------
	officeWorkerAttendance_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:officeWorkerAttendance_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	officeWorkerAttendance_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("officeWorkerAttendance", rosten.webPath + "/employe/officeWorkerAttendanceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_officeWorkerAttendance = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("officeWorkerAttendance", rosten.webPath + "/employe/officeWorkerAttendanceAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_officeWorkerAttendance = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("officeWorkerAttendance", rosten.webPath + "/employe/officeWorkerAttendanceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_officeWorkerAttendance = function() {
		change_officeWorkerAttendance();
	};
	delete_officeWorkerAttendance = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/employe/officeWorkerAttendanceDelete", content,rosten.deleteCallback);
		};
	};
	//员工考勤end----------------
	
	
	/*
	 * 此功能默认必须存在
	 */
	show_employeNaviEntity = function(oString) {
		
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		
		switch (oString) {
		case "constructionWorkerAttendance":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/employeAction/constructionWorkerAttendanceView?userId=" + userid,
				gridSrc : rosten.webPath + "/employe/constructionWorkerAttendanceGrid?companyId=" + companyId + "&userId=" + userid
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
	connect.connect("show_naviEntity", show_employeNaviEntity);
});
