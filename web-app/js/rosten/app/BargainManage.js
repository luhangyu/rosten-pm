/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
    
	
	//承包合同
	bargain_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:bargain_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	bargain_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("bargain", rosten.webPath + "/bargain/bargainShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_bargain = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("bargain", rosten.webPath + "/bargain/bargainAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_bargain = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("bargain", rosten.webPath + "/bargain/bargainShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_bargain = function() {
		change_bargain();
	};
	delete_bargain = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/bargain/bargainDelete", content,rosten.deleteCallback);
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
		case "totalpackageBargain":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/bargainAction/bargainView?userId=" + userid,
				gridSrc : rosten.webPath + "/bargain/bargainGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "subpackageBargain":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/bargainAction/bargainView?userId=" + userid,
				gridSrc : rosten.webPath + "/bargain/bargainGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "purchaseBargain":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/bargainAction/bargainView?userId=" + userid,
				gridSrc : rosten.webPath + "/bargain/bargainGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "salesBargain":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/bargainAction/bargainView?userId=" + userid,
				gridSrc : rosten.webPath + "/bargain/bargainGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;			
			
			
		}
	}
	connect.connect("show_naviEntity", show_bargainNaviEntity);
});
