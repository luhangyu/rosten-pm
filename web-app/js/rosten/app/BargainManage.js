/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
    
	
	//承包合同
	undertakeBargain_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:undertakeBargain_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	undertakeBargain_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("undertakeBargain", rosten.webPath + "/bargain/undertakeBargainShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_undertakeBargain = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("undertakeBargain", rosten.webPath + "/bargain/undertakeBargainAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_undertakeBargain = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("undertakeBargain", rosten.webPath + "/bargain/undertakeBargainShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_undertakeBargain = function() {
		change_undertakeBargain();
	};
	delete_undertakeBargain = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/bargain/undertakeBargainDelete", content,rosten.deleteCallback);
		};
	};
	//承包合同end---------------------------
	
	
	
	
	/*
	 * 此功能默认必须存在
	 */
	show_bargainNaviEntity = function(oString) {
		
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		
		switch (oString) {
		case "undertakeBargain":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/bargainAction/undertakeBargainView?userId=" + userid,
				gridSrc : rosten.webPath + "/bargain/undertakeBargainGrid?companyId=" + companyId + "&userId=" + userid
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
	connect.connect("show_naviEntity", show_bargainNaviEntity);
});
