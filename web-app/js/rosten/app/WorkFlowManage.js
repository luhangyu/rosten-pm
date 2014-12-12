/**
 * @author rosten
 */
define([ "dojo/_base/connect", "dijit/registry", "dojo/has", "rosten/app/Application","rosten/kernel/behavior" ], function(
		connect, registry,has) {
	
	flowBusiness_deleteFlow = function(){
   	 var unids = rosten.getGridUnid("multi");
        if (unids == "")
            return;
        rosten.readNoTime(rosten.webPath + "/modeler/flowBusinessDeleteFlow1/" + unids, {}, function(data){
       	 if (data.result == "true" || data.result == true) {
                rosten.alert("成功!");
                rosten.kernel.refreshGrid();
            } else {
                rosten.alert("失败!");
            }
        });
        
   };
   flowBusiness_addFlow = function(){
   	 var unids = rosten.getGridUnid("multi");
        if (unids == "")
            return;
        
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        var id = "sys_relationFlowDialog";
        var initValue = [];
        initValue.push(rosten.getGridSelectedValue("relationFlowName"));
        
        rosten.selectDialog("关联流程选择", id, rosten.webPath + "/modeler/flowSelect?companyId="+companyId, false, initValue);
        rosten[id].callback = function(data) {
       	 var content = {flowId:data[0].id,flowName:data[0].name};
            rosten.read(rosten.webPath + "/modeler/flowBusinessAddFlow1/" + unids, content, function(_data){
           	 if (_data.result == "true" || _data.result == true) {
                    rosten.alert("成功!");
                    rosten.kernel.refreshGrid();
                } else {
                    rosten.alert("失败!");
                }
            });
        };
        
   };
   
	flowBusiness_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:flowBusiness_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    flowBusiness_onMessageOpen = function(rowIndex){
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("flowBusiness", rosten.webPath + "/modeler/flowBusinessShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
		rosten.kernel.getGrid().clearSelected();
    };
    add_flowBusiness = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("flowBusiness", rosten.webPath + "/modeler/flowBusinessAdd1?companyId=" + companyId + "&userid=" + userid);
    };
    read_flowBusiness = function() {
        change_flowBusiness();
    };
    change_flowBusiness = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;

        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("flowBusiness", rosten.webPath + "/modeler/flowBusinessShow/" + unid + "?userid=" + userid + "&companyId=" + companyId);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_flowBusiness = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten._readNoTime(rosten.webPath + "/modeler/flowBusinessDelete", content, function(data){
            	if (data.result == "true" || data.result == true) {
                    rosten.alert("成功!");
                    rosten.kernel.refreshGrid();
                } else {
                    rosten.alert("失败!");
                }
            });
        };
    };
	
	//增加已部署流程功能
	read_flow = function() {
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
        var url = rosten.webPath + "/modeler/flowExport?id=" + unid + "&type=image";
    	window.open(url,false);
    };
    delete_flow = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/modeler/flowDelete", content, function(data){
            	if (data.result == "true" || data.result == true) {
                    rosten.alert("成功删除!");
                    rosten.kernel.refreshGrid();
                } else {
                    rosten.alert("删除失败!");
                }
            });
        };
    };
    export_flow = function(){
    	var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
    	var url = rosten.webPath + "/modeler/flowExport?id=" + unid;
    	window.open(url,false);
    };
    start_flow = function(){
    	var unid = rosten.getGridUnid("multi");
        if (unid == "")
            return;
        rosten.readSync(rosten.webPath + "/modeler/flowUpdateState",{id:unid,status:"active"},function(data){
			if(data.result=="true" || data.result == true ){
				rosten.alert("重启成功！").queryDlgClose= function(){
					rosten.kernel.refreshGrid();
				};
			}else{
				rosten.alert("重启失败!");
			}
		});
    };
    stop_flow = function(){
    	var unid = rosten.getGridUnid("multi");
        if (unid == "")
            return;
        rosten.readSync(rosten.webPath + "/modeler/flowUpdateState",{id:unid,status:"suspend"},function(data){
			if(data.result=="true" || data.result == true ){
				rosten.alert("挂起成功！").queryDlgClose= function(){
					rosten.kernel.refreshGrid();
				};
			}else{
				rosten.alert("挂起失败!");
			}
		});
    };
	
	//-----------增加流程引擎部分------------------------------------------------
    import_modeler = function(){
    	var companyId = rosten.kernel.getUserInforByKey("companyid");
    	rosten.kernel.createRostenShowDialog(rosten.webPath + "/modeler/modelUpload?companyId=" + companyId, {
            onLoadFunction : function() {
            }
        });
    };
    upload_modeler = function(){
		var companyId = rosten.kernel.getUserInforByKey("companyid");
    	var content = {
    		companyId:companyId	
    	};
    	rosten.readSync(rosten.webPath + "/modeler/uploadModel",content,function(data){
			if(data.result=="true"|| data.result == true){
				rosten.alert("导入成功！").queryDlgClose= function(){
					rosten.kernel.hideRostenShowDialog();
					rosten.kernel.refreshGrid();
				};
			}else{
				rosten.alert("导入失败!");
			}
		},null,"file_form");
    };
    deploy_modeler = function(){
    	var companyId = rosten.kernel.getUserInforByKey("companyid");
    	var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
    	rosten.readSync(rosten.webPath + "/modeler/deploy/" + unid,{companyId:companyId},function(data){
			if(data.result=="true" || data.result == true ){
				rosten.alert("部署成功！").queryDlgClose= function(){
					rosten.kernel.refreshGrid();
				};
			}else{
				rosten.alert("部署失败!");
			}
		});
    };
    export_modeler = function(){
    	var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
    	var url = rosten.webPath + "/modeler/export/" + unid;
    	window.open(url,false);
    };
    add_modeler = function() {
    	if (has("ie")) {
    		rosten.alert("此功能不支持IE浏览器！");
    		return;
    	}
        rosten.kernel.createRostenShowDialog(rosten.webPath + "/modeler/add", {
            onLoadFunction : function() {

            }
        });
    };
    create_modeler = function(){
    	var workFlowName = registry.byId("workFlowName");
		if(!workFlowName.isValid()){
			rosten.alert("流程名称不正确！").queryDlgClose = function(){
				workFlowName.focus();
			};
			return;
		}
		var workFlowKey = registry.byId("workFlowKey");
		if(!workFlowKey.isValid()){
			rosten.alert("流程idkey不正确！").queryDlgClose = function(){
				workFlowKey.focus();
			};
			return;
		}
		var content = {name:workFlowName.attr("value"),key:workFlowKey.attr("value"),description:registry.byId("description").attr("value"),companyId:rosten.kernel.getUserInforByKey("companyid")};
		
		rosten.readSync(rosten.webPath + "/modeler/create",content,function(data){
			if(data.result=="true"){
				rosten.alert("保存成功！").queryDlgClose= function(){
					rosten.kernel.hideRostenShowDialog();
					rosten.openNewWindow("modeler", rosten.webPath + "/modeler/web/editor.html?id=" + data.modelId);	
				};
			}else{
				rosten.alert("保存失败!");
			}
		});
    };
    modeler_formatTopic = function(value,rowIndex){
    	return "<a href=\"javascript:modeler_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
    };
    modeler_onMessageOpen = function(rowIndex){
    	if (has("ie")) {
    		rosten.alert("此功能不支持IE浏览器！");
    		return;
    	}
    	var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("modeler", rosten.webPath + "/modeler/web/editor.html?id=" + unid);
		rosten.kernel.getGrid().clearSelected();
    };
    save_modeler = function(){
    	var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        
    };
    read_modeler = function() {
        change_modeler();
    };
    change_modeler = function() {
    	if (has("ie")) {
    		rosten.alert("此功能不支持IE浏览器！");
    		return;
    	}
        var unid = rosten.getGridUnid("single");
        if (unid == "")
            return;
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("modeler", rosten.webPath + "/modeler/web/editor.html?id=" + unid);
        rosten.kernel.getGrid().clearSelected();
    };
    delete_modeler = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
            var unids = rosten.getGridUnid("multi");
            if (unids == "")
                return;
            var content = {};
            content.id = unids;
            rosten.read(rosten.webPath + "/modeler/modelerDelete", content, function(data){
            	if (data.result == "true" || data.result == true) {
                    rosten.alert("成功删除!");
                    rosten.kernel.refreshGrid();
                } else {
                    rosten.alert("删除失败!");
                }
            });
        };
    };
    
    //-------------------------------------------------------------------------
    
	/*
	 * 此功能默认必须存在
	 */
	show_workFlowNaviEntity = function(oString) {
		
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		
		switch (oString) {
		case "flowDefinedManage":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/modelerAction/flowDefinedView",
				gridSrc : rosten.webPath + "/modeler/flowDefinedGrid?userid=" + userid + "&companyId=" + companyId
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "workFlowManage":
            var naviJson = {
                identifier : oString,
                actionBarSrc : rosten.webPath + "/modelerAction/molelerView",
                gridSrc : rosten.webPath + "/modeler/modelerGrid?userid=" + userid + "&companyId=" + companyId
            };
            rosten.kernel.addRightContent(naviJson);
            
            var rostenGrid = rosten.kernel.getGrid();
            rostenGrid.onRowDblClick = read_modeler;
            break;
		case "flowBusinessManage":
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/modelerAction/flowBusinessView",
				gridSrc : rosten.webPath + "/modeler/flowBusinessGrid?userid=" + userid + "&companyId=" + companyId
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		}
	}
	connect.connect("show_naviEntity", show_workFlowNaviEntity);
});
