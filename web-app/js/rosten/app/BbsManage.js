/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
	
	bbs_search = function(){
		var content = {};
		
		switch(rosten.kernel.navigationEntity) {
		default:
			var serialNo = registry.byId("s_serialno");
			if(serialNo.get("value")!=""){
				content.serialNo = serialNo.get("value");
			}
			
			var topic = registry.byId("s_topic");
			if(topic.get("value")!=""){
				content.topic = topic.get("value");
			}
			
			var status = registry.byId("s_status");
			if(status.get("value")!=""){
				content.status = status.get("value");
			}
			break;
		}

//		var count = Object.keys(content).length
//		if(count==0) return ;
		
		rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
	};
	bbs_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		default:
			registry.byId("s_serialno").set("value","");
			registry.byId("s_topic").set("value","");
			registry.byId("s_status").set("value","");
			break;
		}	
		
		rosten.kernel.refreshGrid();
	};
	
	bbs_changeStatus = function(){
			
			
	};
	bbs_changeUser = function(){
			
	};
	formatBbsLevel = function(value){
		if(value && value!=""){
			var imgs = general.splitString(value,",");
			var _values=""
			for(var i = 0; i < imgs.length; i ++){
				if(_values==""){
					_values = "<img style=\"margin-left:4px\" src=\"" + rosten.webPath + "/" + imgs[i] + "\" />";
				}else{
					_values += "<img style=\"margin-left:4px\" src=\"" + rosten.webPath + "/" + imgs[i] + "\" />";
				}
			}
			return _values;
		}else{
			return "";
		}
	};
	bbs_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:bbs_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	bbs_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("bbs", rosten.webPath + "/bbs/bbsShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_bbs = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("bbs", rosten.webPath + "/bbs/bbsAdd?companyId=" + companyId + "&userid=" + userid+ "&flowCode=bbs");
    };
	change_bbs = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("bbs", rosten.webPath + "/bbs/bbsShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_bbs = function() {
		change_bbs();
	};
	delete_bbs = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/bbs/bbsDelete", content,
					rosten.deleteCallback);
		};
	};
    
	/*
	 * 此功能默认必须存在
	 */
	show_bbsNaviEntity = function(oString) {
		
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		
		switch (oString) {
		case "allbbsManage":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/bbsAction/allbbsView?userId=" + userid,
				searchSrc:rosten.webPath + "/bbs/bbsSearchView",
				gridSrc : rosten.webPath + "/bbs/bbsGrid?companyId=" + companyId + "&userId=" + userid + "&type=all"
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "newbbsManage":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/bbsAction/newbbsView",
				searchSrc:rosten.webPath + "/bbs/bbsSearchView",
				gridSrc : rosten.webPath + "/bbs/bbsGrid?companyId=" + companyId + "&userId=" + userid + "&type=new"
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "mybbsManage":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/bbsAction/bbsView",
				searchSrc:rosten.webPath + "/bbs/bbsSearchView",
				gridSrc : rosten.webPath + "/bbs/bbsGrid?companyId=" + companyId + "&userId=" + userid + "&type=person"
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		case "bbsConfigManage":
			rosten.kernel.setHref(rosten.webPath + "/bbs/bbsConfig", oString);
            break;
		}
	}
	connect.connect("show_naviEntity", show_bbsNaviEntity);
});
