/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry","rosten/util/general","rosten/app/Application", "rosten/kernel/behavior"], function(
		connect, registry,General) {
	
	var general = new General();
	
	//往来单位搜索
	contactCorp_search = function(){
		var content = {};
		
		var s_contactCorpName = registry.byId("s_contactCorpName");
		if(s_contactCorpName.get("value")!=""){
			content.contactCorpName = s_contactCorpName.get("value");
		}
		
		var s_contactCorpType = registry.byId("s_contactCorpType");
		if(s_contactCorpType.get("value")!=""){
			content.contactCorpType = s_contactCorpType.get("value");
		}
		
		switch(rosten.kernel.navigationEntity) {
		default:
			rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
			break;
		}
	};
	contactCorp_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		default:
			registry.byId("s_contactCorpType").set("value","");
			registry.byId("s_contactCorpName").set("value","");
			
			rosten.kernel.refreshGrid();
			break;
		}	
	};
    
	//材料信息搜索
	materialInfor_search = function(){
	    var content = {};
        
        var s_materialInforName = registry.byId("s_materialInforName");
        if(s_materialInforName.get("value")!=""){
            content.materialInforName = s_materialInforName.get("value");
        }
        
        var s_materialType = registry.byId("s_materialType");
        if(s_materialType.get("value")!=""){
            content.materialType = s_materialType.get("value");
        }
        
        metInfor_rostenGrid.refresh(null,content);
	};
	materialInfor_resetSearch = function(){
        switch(rosten.kernel.navigationEntity) {
        default:
            registry.byId("s_materialInforName").set("value","");
            registry.byId("s_materialType").set("value","");
            metInfor_rostenGrid.refresh();
            break;
        }   
    };
	//供应商搜索
	supplier_search = function(){
		var content = {};
		
		var s_suppName = registry.byId("s_suppName");
		if(s_suppName.get("value")!=""){
			content.suppName = s_suppName.get("value");
		}
		
		var s_suppType = registry.byId("s_suppType");
		if(s_suppType.get("value")!=""){
			content.suppType = s_suppType.get("value");
		}
		
		
		switch(rosten.kernel.navigationEntity) {
		default:
			rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
			break;
		}
	};
	//供应商-搜索重置
	supplier_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		default:
			registry.byId("s_suppName").set("value","");
			registry.byId("s_suppType").set("value","");
			rosten.kernel.refreshGrid();
			break;
		}	
	};
	
	//公司信息子块
	formatIsDefault = function(value,rowIndex){
	    if(value){
            var _values = "<img style=\"margin-left:4px\" src=\"" + rosten.webPath + "/images/rosten/actionbar/ok.png" + "\" />";
            return _values;
        }else{
            return "";
        }
	};
	companyInfor_setDefault = function(){
    	var unids = rosten.getGridUnid("single");
        if (unids == "")
            return;
        var content = {companyIsDef:"true"};
        content.id = unids;
        rosten.read(rosten.webPath + "/baseinfor/companyInforSetDefault", content, rosten.commonCallback);
    };
    
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
	bankInfor_setDefault = function(){
    	var unids = rosten.getGridUnid("single");
        if (unids == "")
            return;
        var content = {accountIsDef:"true"};
        content.id = unids;
        rosten.read(rosten.webPath + "/baseinfor/bankInforSetDefault", content, rosten.commonCallback);
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
        var unid = _getGridItemValue(metInfor_rostenGrid,rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("materialInfo", rosten.webPath + "/baseinfor/materialInfoShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_materialInfo = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        var type=rosten.variable.currentTreeId;
        rosten.openNewWindow("materialInfo", rosten.webPath + "/baseinfor/materialInfoAdd?companyId=" + companyId + "&userid=" + userid + "&type=" + type);
    };
	change_materialInfo = function() {
		var unid = rosten._getGridUnid(metInfor_rostenGrid,"single");
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
			var unids = rosten._getGridUnid(metInfor_rostenGrid,"multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/materialInfoDelete", content,function(data){
			    if(data.result=="true" || data.result==true){
			        rosten.alert("成功删除！").queryDlgClose = function(){
			            metInfor_rostenGrid.refresh();
			        };
			    }else{
			        rosten.alert("删除失败！");
			    }
			});
		};
	};
	fresh_materialInfo = function(){
		metInfor_rostenGrid.refresh();
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
	materialType_save = function(){
		var formWidget = registry.byId("rosten_form");
		if(!formWidget.validate()){
			rosten.alert("请正确填写相关信息！");
			return;
		}
		
		var content = {};
		rosten.readNoTime(rosten.webPath + "/baseinfor/matTypeSave", content,function(data){
			if(data.result=="true" || data.result == true){
				rosten.alert("保存成功！").queryDlgClose= function(){
					rosten.kernel.hideRostenShowDialog();
					refreshObjTree();
				};
			}else if(data.result=="exist"){
				rosten.alert("当前类型已经存在，请更换名称！");
			}else{
				rosten.alert("保存失败!");
			}
		},function(error){
			rosten.alert("系统错误，请通知管理员！");
		},"rosten_form");
		
	};
	//-------------------------------------------3
	
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
	
	//------------------------------------------4
	//材料单位
	materialUnit_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:materialUnit_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	materialUnit_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("materialUnit", rosten.webPath + "/baseinfor/materialUnitShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
	};
	add_materialUnit = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("materialUnit", rosten.webPath + "/baseinfor/materialUnitAdd?companyId=" + companyId + "&userid=" + userid);
    };
	change_materialUnit = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("materialUnit", rosten.webPath + "/baseinfor/materialUnitShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
	};
	read_materialUnit = function() {
		change_materialUnit();
	};
	delete_materialUnit = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/baseinfor/materialUnitDelete", content,rosten.deleteCallback);
		};
	};
	_getGridItemValue=function(rostenGrid,index,name){
        var grid = rostenGrid.getGrid();
        var item = grid.getItem(index);
        var store = rostenGrid.getStore();
        return store.getValue(item, name);
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
				searchSrc:rosten.webPath + "/baseinfor/contactCorpSearchView",
				gridSrc : rosten.webPath + "/baseinfor/contactCorpGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
			
		case "supplier": //供应商
			var naviJson = {
				identifier : oString,				
				actionBarSrc : rosten.webPath + "/baseinforAction/supplierView?userId=" + userid,
				searchSrc:rosten.webPath + "/baseinfor/supplierSearchView",
				gridSrc : rosten.webPath + "/baseinfor/supplierGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;	
		case "materialInfo":
			var companyId = rosten.kernel.getUserInforByKey("companyid");
            rosten.kernel.setHref(rosten.webPath + "/baseinfor/materialType?companyId=" + companyId, oString);
            break;
		case "materialUnit":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/baseinforAction/materialUnitView?userId=" + userid,
				gridSrc : rosten.webPath + "/baseinfor/materialUnitGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;		
			
		}
	};
	connect.connect("show_naviEntity", show_baseinforNaviEntity);
});
