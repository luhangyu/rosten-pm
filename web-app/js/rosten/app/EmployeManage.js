/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
    
	
	/*
	 * 考勤父表
	 */
	attendance_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:attendance_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	attendance_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("attendance", rosten.webPath + "/employe/attendanceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_attendance = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("attendance", rosten.webPath + "/employe/attendanceAdd?companyId=" + companyId + "&userid=" + userid + "&type=" + rosten.kernel.navigationEntity);
    };
	change_attendance = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("attendance", rosten.webPath + "/employe/attendanceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_attendance = function() {
		change_attendance();
	};
	delete_attendance = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/employe/attendanceDelete", content,rosten.deleteCallback);
		};
	};
	//考勤父表end---------------------------
	
//	//考勤子表start----------------
//	workerAttendance_formatTopic = function(value,rowIndex){
//		return "<a href=\"javascript:workerAttendance_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
//	};
//	workerAttendance_onMessageOpen = function(rowIndex){
//        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
//        var userid = rosten.kernel.getUserInforByKey("idnumber");
//		var companyId = rosten.kernel.getUserInforByKey("companyid");
//		rosten.openNewWindow("workerAttendance", rosten.webPath + "/employe/workerAttendanceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
//		rosten.kernel.getGrid().clearSelected();
//	};
//	add_workerAttendance = function() {
//		var userid = rosten.kernel.getUserInforByKey("idnumber");
//        var companyId = rosten.kernel.getUserInforByKey("companyid");
//        rosten.openNewWindow("workerAttendance", rosten.webPath + "/employe/workerAttendanceAdd?companyId=" + companyId + "&userid=" + userid);
//    };
//	change_workerAttendance = function() {
//		var unid = rosten.getGridUnid("single");
//		if (unid == "")
//			return;
//		var userid = rosten.kernel.getUserInforByKey("idnumber");
//		var companyId = rosten.kernel.getUserInforByKey("companyid");
//		rosten.openNewWindow("workerAttendance", rosten.webPath + "/employe/workerAttendanceShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
//	};
//	read_workerAttendance = function() {
//		change_workerAttendance();
//	};
//	delete_workerAttendance = function() {
//		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
//		_1.callback = function() {
//			var unids = rosten.getGridUnid("multi");
//			if (unids == "")
//				return;
//			var content = {};
//			content.id = unids;
//			rosten.readNoTime(rosten.webPath + "/employe/workerAttendanceDelete", content,rosten.deleteCallback);
//		};
//	};
	//员工考勤end----------------
	
	/*
	 * 工资父表
	 */
	salary_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:salary_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	salary_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("salary", rosten.webPath + "/employe/salaryShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_salary = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("salary", rosten.webPath + "/employe/salaryAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_salary = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("salary", rosten.webPath + "/employe/salaryShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_salary = function() {
		change_salary();
	};
	delete_salary = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/employe/salaryDelete", content,rosten.deleteCallback);
		};
	};
	//考勤父表end---------------------------
	
	
	
	
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
				actionBarSrc : rosten.webPath + "/employeAction/attendanceView?userId=" + userid,
				gridSrc : rosten.webPath + "/employe/attendanceGrid?companyId=" + companyId + "&userId=" + userid + "&type=" + oString
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		case "officeWorkerAttendance":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/employeAction/attendanceView?userId=" + userid,
				gridSrc : rosten.webPath + "/employe/attendanceGrid?companyId=" + companyId + "&userId=" + userid + "&type=" + oString
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		
		case "salarySend":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/employeAction/salaryView?userId=" + userid,
				gridSrc : rosten.webPath + "/employe/salaryGrid?companyId=" + companyId + "&userId=" + userid + "&type=" + oString
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		}
	}
	connect.connect("show_naviEntity", show_employeNaviEntity);
});
