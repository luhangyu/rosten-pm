/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior" ], function(
		connect, registry,General) {
	
	var general = new General();
    
	
	//公司信息子块
	companyInfor_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:companyInfor_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	companyInfor_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("companyInfor", rosten.webPath + "/baseinfor/companyInforShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_companyInfor = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("companyInfor", rosten.webPath + "/baseinfor/companyInforAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_companyInfor = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("companyInfor", rosten.webPath + "/baseinfor/companyInforShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_companyInfor = function() {
		change_companyInfor();
	};
	delete_companyInfor = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/companyInforDelete", content,rosten.deleteCallback);
		};
	};
	
	
	//银行基本
	bankInfor_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:bankInfor_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	bankInfor_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("bankInfor", rosten.webPath + "/baseinfor/bankInforShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_bankInfor = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("bankInfor", rosten.webPath + "/baseinfor/bankInforAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_bankInfor = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("bankInfor", rosten.webPath + "/baseinfor/bankInforShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_bankInfor = function() {
		change_bankInfor();
	};
	delete_bankInfor = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/bankInforDelete", content,
					rosten.deleteCallback);
		};
	};
	
	
	//往来单位信息141126
	contactCorp_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:contactCorp_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	contactCorp_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("contactCorp", rosten.webPath + "/baseinfor/contactCorpShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_contactCorp = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("contactCorp", rosten.webPath + "/baseinfor/contactCorpAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_contactCorp = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("contactCorp", rosten.webPath + "/baseinfor/contactCorpShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_contactCorp = function() {
		change_contactCorp();
	};
	delete_contactCorp = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/contactCorpDelete", content,rosten.deleteCallback);
		};
	};
	
	
	
	//------------------------------------------1
	//供应商141205
	supplier_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:supplier_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	supplier_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("supplier", rosten.webPath + "/baseinfor/supplierShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_supplier = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("supplier", rosten.webPath + "/baseinfor/supplierAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_supplier = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("supplier", rosten.webPath + "/baseinfor/supplierShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_supplier = function() {
		change_supplier();
	};
	delete_supplier = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/supplierDelete", content,rosten.deleteCallback);
		};
	};
	//-------------------------------------------1
	
	
	//------------------------------------------2
	//材料信息
	materialInfo_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:materialInfo_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	materialInfo_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("materialInfo", rosten.webPath + "/baseinfor/materialInfoShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_materialInfo = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("materialInfo", rosten.webPath + "/baseinfor/materialInfoAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_materialInfo = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("materialInfo", rosten.webPath + "/baseinfor/materialInfoShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_materialInfo = function() {
		change_materialInfo();
	};
	delete_materialInfo = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/materialInfoDelete", content,rosten.deleteCallback);
		};
	};
	//-------------------------------------------2
	
	
	
	//------------------------------------------3
	//材料类型（分类）
	materialType_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:materialType_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	materialType_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("materialType", rosten.webPath + "/baseinfor/materialTypeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_materialType = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("materialType", rosten.webPath + "/baseinfor/materialTypeAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_materialType = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("materialType", rosten.webPath + "/baseinfor/materialTypeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_materialType = function() {
		change_materialType();
	};
	delete_materialType = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/materialTypeDelete", content,rosten.deleteCallback);
		};
	};
	//-------------------------------------------3
	
	
	//------------------------------------------4
	//工种
	workerType_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:workerType_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	workerType_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("workerType", rosten.webPath + "/baseinfor/workerTypeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_workerType = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("workerType", rosten.webPath + "/baseinfor/workerTypeAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_workerType = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("workerType", rosten.webPath + "/baseinfor/workerTypeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_workerType = function() {
		change_workerType();
	};
	delete_workerType = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/workerTypeDelete", content,rosten.deleteCallback);
		};
	};
	//-------------------------------------------4
	
	/*
	 * 此功能默认必须存在
	 */
	show_baseinforNaviEntity = function(oString) {
		
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		
		switch (oString) {
		case "companyInfor":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/baseinforAction/companyInforView?userId=" + userid,
				gridSrc : rosten.webPath + "/baseinfor/companyInforGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		
		case "bankInfor":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/baseinforAction/bankInforView?userId=" + userid,
				gridSrc : rosten.webPath + "/baseinfor/bankInforGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		case "contactCorp":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/baseinforAction/contactCorpView?userId=" + userid,
				gridSrc : rosten.webPath + "/baseinfor/contactCorpGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "supplier":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/baseinforAction/supplierView?userId=" + userid,
				gridSrc : rosten.webPath + "/baseinfor/supplierGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;	
		case "materialType":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/baseinforAction/materialTypeView?userId=" + userid,
				gridSrc : rosten.webPath + "/baseinfor/materialTypeGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;	
		case "materialInfo":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/baseinforAction/materialInfoView?userId=" + userid,
				gridSrc : rosten.webPath + "/baseinfor/materialInfoGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;	
		case "workerType":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/baseinforAction/workerTypeView?userId=" + userid,
				gridSrc : rosten.webPath + "/baseinfor/workerTypeGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;		
			
		}
	}
	connect.connect("show_naviEntity", show_baseinforNaviEntity);
});
