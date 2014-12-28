/**
 * @author rosten
 */
define(["dojo/_base/connect", "dijit/registry","rosten/util/general", "rosten/kernel/behavior"], function(
		connect, registry,General) {
	
	staff_search = function(){
		var content = {};
		
		var username = registry.byId("s_username");
		if(username.get("value")!=""){
			content.username = username.get("value");
		}
		
		var chinaName = registry.byId("s_chinaName");
		if(chinaName.get("value")!=""){
			content.chinaName = chinaName.get("value");
		}
		
		var departName = registry.byId("s_departName");
		if(departName.get("value")!=""){
			content.departName = departName.get("value");
		}
		
		var idCard = registry.byId("s_idCard");
		if(idCard.get("value")!=""){
			content.idCard = idCard.get("value");
		}
		
		var sex = registry.byId("s_sex");
		if(sex.get("value")!=""){
			content.sex = sex.get("value");
		}
		
		var politicsStatus = registry.byId("s_politicsStatus");
		if(politicsStatus.get("value")!=""){
			content.politicsStatus = politicsStatus.get("value");
		}
		
		var nativeAddress = registry.byId("s_nativeAddress");
		if(nativeAddress.get("value")!=""){
			content.nativeAddress = nativeAddress.get("value");
		}
		
		var city = registry.byId("s_city");
		if(city.get("value")!=""){
			content.city = city.get("value");
		}
		
		var status = registry.byId("s_status");
		if(status.get("value")!=""){
			content.status = status.get("value");
		}
		
		var workJob = registry.byId("s_workJob");
		if(workJob.get("value")!=""){
			content.workJob = workJob.get("value");
		}
		
		switch(rosten.kernel.navigationEntity) {
		
		case "staffManage":
			dom_rostenGrid.refresh(null,content);
			break;
		default:
			rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
			break;
		}
		
		
	};
	
	staff_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		case "userManage1":
			registry.byId("s_username").set("value","");
			registry.byId("s_chinaName").set("value","");
			registry.byId("s_departName").set("value","");
			registry.byId("s_idCard").set("value","");
			registry.byId("s_sex").set("value","");
			registry.byId("s_politicsStatus").set("value","");
			registry.byId("s_nativeAddress").set("value","");
			registry.byId("s_city").set("value","");
			registry.byId("s_status").set("value","");
			registry.byId("s_workJob").set("value","");
			
			dom_rostenGrid.refresh();
			break;
			
		default:
			registry.byId("s_username").set("value","");
			registry.byId("s_chinaName").set("value","");
			registry.byId("s_departName").set("value","");
			registry.byId("s_idCard").set("value","");
			registry.byId("s_sex").set("value","");
			registry.byId("s_politicsStatus").set("value","");
			registry.byId("s_nativeAddress").set("value","");
			registry.byId("s_city").set("value","");
			registry.byId("s_status").set("value","");
			registry.byId("s_workJob").set("value","");
			rosten.kernel.refreshGrid();
			break;
		}	
	};
	
	//部门调动
	departChange_search = function(){
		var content = {};
		
		var inDepart = registry.byId("s_inDepart");
		if(inDepart.get("value")!=""){
			content.inDepart = inDepart.get("value");
		}
		
		var departName = registry.byId("s_departName");
		if(departName.get("value")!=""){
			content.departName = departName.get("value");
		}
		
		var chinaName = registry.byId("s_chinaName");
		if(chinaName.get("value")!=""){
			content.chinaName = chinaName.get("value");
		}
		
		switch(rosten.kernel.navigationEntity) {
		default:
			rosten.kernel.refreshGrid(rosten.kernel.getGrid().defaultUrl, content);
			break;
		}
	};
	
	departChange_resetSearch = function(){
		switch(rosten.kernel.navigationEntity) {
		default:
			registry.byId("s_inDepart").set("value","");
			registry.byId("s_departName").set("value","");
			registry.byId("s_chinaName").set("value","");
			break;
		}	
		
		rosten.kernel.refreshGrid();
	};
	personInfor_asignAccount = function(){
    	var unid = rosten._getGridUnid(dom_rostenGrid,"single");
        if (unid == "")
            return;
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.kernel.createRostenShowDialog(rosten.webPath + "/staff/asignAccount/"+ unid + "?companyId=" + companyId, {
            onLoadFunction : function() {

            }
        });
    };
    asignAccount_Submit = function(){
    	if(rosten.check_common("username","账号不正确！",true)==false) return false;
		if(rosten.check_common("password","密码不正确！",true)==false) return false;
		if(rosten.check_common("passwordcheck","确认密码不正确！",true)==false) return false;

		var password = registry.byId("password");
		var passwordcheck = registry.byId("passwordcheck");
		if(password.attr("value")!=passwordcheck.attr("value")){
			rosten.alert("密码不一致！").queryDlgClose = function(){
				password.attr("value","");
				passwordcheck.attr("value","");
				password.focus();
			};
			return false;
		}
		
		var content = {};
//		content.userNameFront = registry.byId("userNameFront").attr("value");
        rosten.readSync(rosten.webPath + "/staff/asignAccountSubmit", content, function(data) {
            if (data.result == "true") {
                rosten.kernel.hideRostenShowDialog();
                dom_rostenGrid.refresh();
                rosten.alert("成功!");
            }else if(data.result == "exist"){
            	rosten.alert("当前账号已存在!").queryDlgClose = function(){
            		registry.byId("username").set("value","");
            	}
            } else {
                rosten.alert("失败!");
            }
        },null,"rosten_form");
		
		
    };
    
	/*
	 *系统管理模块中的员工管理模块使用 
	 */
	personInfor_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:personInfor_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	personInfor_onMessageOpen = function(rowIndex){
		var rostenGrid = registry.byId("rosten_rostenGrid");
        var unid = _getGridItemValue(rostenGrid,rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var currentDepartId = rosten.variable.currentDeartId;
		rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userShow/" + unid + "?userid=" + userid + "&companyId=" + companyId + "&currentDepartId=" + currentDepartId);
		rostenGrid.clearSelected();
	};
	
	//2014-12-07增加导出员工账号信息功能
	export_personInfor_account = function(){
		 var companyId = rosten.kernel.getUserInforByKey("companyid");
		 
		 var content = {};
			var query = "";
			var username = registry.byId("s_username");
			if(username.get("value")!=""){
				query += "&username="+username.get("value");
			}
			
			var chinaName = registry.byId("s_chinaName");
			if(chinaName.get("value")!=""){
				query += "&chinaName="+chinaName.get("value");
			}
			
			var departName = registry.byId("s_departName");
			if(departName.get("value")!=""){
				query += "&departName="+departName.get("value");
			}
			
			var idCard = registry.byId("s_idCard");
			if(idCard.get("value")!=""){
				query += "&idCard="+idCard.get("value");
			}
			
			var sex = registry.byId("s_sex");
			if(sex.get("value")!=""){
				query += "&sex="+sex.get("value");
			}
			
			var politicsStatus = registry.byId("s_politicsStatus");
			if(politicsStatus.get("value")!=""){
				query += "&politicsStatus="+politicsStatus.get("value");
			}
		 
		rosten.openNewWindow("exportAccount", rosten.webPath + "/staff/exportPersonAccount?companyId="+companyId+"&type="+ rosten.kernel.navigationEntity+query );
	};
	
	export_personInfor = function(){
		 var companyId = rosten.kernel.getUserInforByKey("companyid");
		 
		 var content = {};
			var query = "";
			var username = registry.byId("s_username");
			if(username.get("value")!=""){
				query += "&username="+username.get("value");
			}
			
			var chinaName = registry.byId("s_chinaName");
			if(chinaName.get("value")!=""){
				query += "&chinaName="+chinaName.get("value");
			}
			
			var departName = registry.byId("s_departName");
			if(departName.get("value")!=""){
				query += "&departName="+departName.get("value");
			}
			
			var idCard = registry.byId("s_idCard");
			if(idCard.get("value")!=""){
				query += "&idCard="+idCard.get("value");
			}
			
			var sex = registry.byId("s_sex");
			if(sex.get("value")!=""){
				query += "&sex="+sex.get("value");
			}
			
			var politicsStatus = registry.byId("s_politicsStatus");
			if(politicsStatus.get("value")!=""){
				query += "&politicsStatus="+politicsStatus.get("value");
			}
		 
		rosten.openNewWindow("export", rosten.webPath + "/staff/exportPerson?companyId="+companyId+"&type="+ rosten.kernel.navigationEntity+query );
	};
	
	import_personInfor = function(){
		 var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.kernel.createRostenShowDialog(rosten.webPath + "/staff/importStaff/"+ companyId, {
            onLoadFunction : function() {

            }
        });
	};
	//打印
	personInfor_print = function(){
		var unids = rosten.getGridUnid("multi");
		if (unids == ""){
			rosten.alert("请勾选要打印的数据！");
			return;
		}
		var content = {};
		content.id = unids;
		rosten.openNewWindow("print", rosten.webPath + "/staff/printPerson/"+unids);
	};
	personInfor_print_rzqd = function(){
		var unids = rosten.getGridUnid("multi");
		if (unids == ""){
			rosten.alert("请勾选要打印的数据！");
			return;
		}
		var content = {};
		content.id = unids;
		rosten.openNewWindow("print", rosten.webPath + "/staff/printPersonRzqd/"+unids);
	};
	personInfor_print_rztzs = function(){
		rosten.alert("尚未开通！");
		return;
		rosten.openNewWindow("print", rosten.webPath + "/staff/printPerson");
	};
	personInfor_rz = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userAdd?companyId=" + companyId + "&userid=" + userid + "&type=staffAdd&flowCode=staffAdd");
        
    };
    personInfor_zz = function() {
    	var unid = rosten.getGridItemValue1(rosten.kernel.getGrid(),"id");
        if (unid == "")
            return;
        var status = rosten.getGridItemValue1(rosten.kernel.getGrid(),"status");
        if(!(status=="试用"||status=="实习")){
        	rosten.alert("只有状态为<试用/实习>的员工才允许转正");
        	return;
        }
        
        var content = {};
        content.id = unid;
        rosten.read(rosten.webPath + "/staff/staffZZ", content, function(data,ioArgs){
        	if (data.result == "true" || data.result == true) {
                rosten.alert("成功!").queryDlgClose = function(){
                    rosten.kernel.refreshGrid();
                };
            } else {
                rosten.alert("失败!");
            }
        });
        
    };
	personInfor_dj = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userAdd?companyId=" + companyId + "&userid=" + userid);
    };
    personInfor_add = function() {
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        var currentDepartId = rosten.variable.currentDeartId;
        rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userAdd?companyId=" + companyId + "&userid=" + userid + "&currentDepartId=" + currentDepartId);
    };
    read_personInfor = function() {
    	change_personInfor();
    };
    change_personInfor = function() {
        var unid = rosten._getGridUnid(dom_rostenGrid,"single");
        if (unid == "")
            return;
        var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        var currentDepartId = rosten.variable.currentDeartId;
        rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userShow/" + unid + "?userid=" + userid + "&companyId=" + companyId + "&currentDepartId=" + currentDepartId);
        dom_rostenGrid.clearSelected();
    };
    delete_personInfor = function() {
        var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
        _1.callback = function() {
        	var unids;
        	if(rosten.kernel.navigationEntity=="staffAdd" || rosten.kernel.navigationEntity=="staffRegi"){
        		unids = rosten.getGridUnid("multi");
        	}else{
        		unids = rosten._getGridUnid(dom_rostenGrid,"multi");
        	}
            
            if (unids == "") return;
            var content = {};
            content.id = unids;
            rosten.readSyncNoTime(rosten.webPath + "/staff/userDelete", content, function(data,ioArgs){
            	if(rosten.kernel.navigationEntity=="staffAdd" || rosten.kernel.navigationEntity=="staffRegi"){
            		delete_callback(data);
            	}else{
            		delete_callback(data,ioArgs,dom_rostenGrid);
            	}
            });
        };
    };
    personInfor_freshGrid = function(){
    	dom_rostenGrid.refresh();
    };
    personInfor_changePassword = function(){
    	var unid = rosten._getGridUnid(dom_rostenGrid,"single","userId");
        if (unid == ""){
        	rosten.alert("请先选择条目或当前不存在账号信息！");
        	return;
        }
    	rosten.kernel.createRostenShowDialog(rosten.webPath + "/system/passwordChangeShow1/"+ unid, {
            onLoadFunction : function() {

            }
        });
    };
	_getGridItemValue=function(rostenGrid,index,name){
    	var grid = rostenGrid.getGrid();
    	var item = grid.getItem(index);
    	var store = rostenGrid.getStore();
    	return store.getValue(item, name);
    };
	//员工聘任--------------------------------------------------------------------------------------------
	engage_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:engage_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	engage_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("engage", rosten.webPath + "/staff/engageShow/" + unid + "?userid=" + userid + "&companyId=" + companyId+ "&flowCode=engage");
		rosten.kernel.getGrid().clearSelected();
	};
	add_engage = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("engage", rosten.webPath + "/staff/engageAdd?companyId=" + companyId + "&userid=" + userid + "&flowCode=engage");
    };
	change_engage = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("engage", rosten.webPath + "/staff/engageShow/" + unid + "?userid=" + userid + "&companyId=" + companyId+ "&flowCode=engage");
	};
	read_engage = function() {
		change_engage();
	};
	delete_engage = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/staff/engageDelete", content,
					rosten.deleteCallback);
		};
	};
	//-------------------------------------------------------------------------------------------------
	
	//员工转正申请----------------------------------------------------------------
	
	officialApply_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:officialApply_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	officialApply_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("officialApply", rosten.webPath + "/staff/officialApplyShow/" + unid + "?userid=" + userid + "&companyId=" + companyId+ "&flowCode=officialApply");
		rosten.kernel.getGrid().clearSelected();
	};
	add_officialApply = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        
        //判断当前用户是否已经转正过或者已经是正式员工
        var content = {userid:userid,companyId:companyId};
        rosten.readNoTime(rosten.webPath + "/staff/officialApplyCheck", content,function(data){
        	if(data.result==true || data.result=="true"){
        		rosten.openNewWindow("officialApply", rosten.webPath + "/staff/officialApplyAdd?companyId=" + companyId + "&userid=" + userid + "&flowCode=officialApply");
        	}else{
        		rosten.alert("当前不需要转正申请，请核查！");
        	}
        });
    };
	change_officialApply = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("officialApply", rosten.webPath + "/staff/officialApplyShow/" + unid + "?userid=" + userid + "&companyId=" + companyId+ "&flowCode=officialApply");
	};
	read_officialApply = function() {
		change_officialApply();
	};
	delete_officialApply = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/staff/officialApplyDelete", content,
					rosten.deleteCallback);
		};
	};
	
	//------------------------------------------------------------------------
	
	
	staffStatusChange_print_tzd = function(){
		//打印通知单
	};
	
	//打印离职交接单
	staffStatusChange_print_qd = function(){
		var unids = rosten.getGridUnid("multi");
		if (unids == ""){
			rosten.alert("请勾选要打印的数据！");
			return;
		}
		var content = {};
		content.id = unids;
		rosten.openNewWindow("print", rosten.webPath + "/staff/printPersonLzjjd/"+unids);
	};
	
	staffStatusChange_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:staffStatusChange_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	staffStatusChange_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var type = rosten.kernel.getGridItemValue(rowIndex,"changeType");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("staffStatusChange", rosten.webPath + "/staff/staffStatusChangeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId + "&type=" + type);
		rosten.kernel.getGrid().clearSelected();
	};
	add_staffStatusChange = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        
        var type="retire";
        if(rosten.kernel.navigationEntity=="staffLeave"){
        	type="leave";
        }
        rosten.openNewWindow("staffStatusChange", rosten.webPath + "/staff/staffStatusChangeAdd?companyId=" + companyId + "&userid=" + userid + "&type=" + type+ "&flowCode=statusChange");
    };
	change_staffStatusChange = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		
		 var type="retire";
	        if(rosten.kernel.navigationEntity=="staffLeave"){
	        	type="leave";
	        }
	        
		rosten.openNewWindow("staffStatusChange", rosten.webPath + "/staff/staffStatusChangeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId+ "&type=" + type+ "&flowCode=statusChange");
	};
	read_staffStatusChange = function() {
		change_staffStatusChange();
	};
	delete_staffStatusChange = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/staff/staffStatusChangeDelete", content,
					rosten.deleteCallback);
		};
	};
	
	staffDepartChange_print_tzd = function(){
		//打印通知单
	};
	
	staffDepartChange_print_qd = function(){
		//打印交接清单
		var unids = rosten.getGridUnid("multi");
		if (unids == ""){
			rosten.alert("请勾选要打印的数据！");
			return;
		}
		var content = {};
		content.id = unids;
		rosten.openNewWindow("print", rosten.webPath + "/staff/printPersonDdjjd/"+unids);
	};
	
	staffDepartChange_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:staffDepartChange_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	staffDepartChange_onMessageOpen = function(rowIndex){
        var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("staffDepartChange", rosten.webPath + "/staff/staffDepartChangeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId+ "&flowCode=staffDepartChange");
		rosten.kernel.getGrid().clearSelected();
	};
	add_staffDepartChange = function() {
		var userid = rosten.kernel.getUserInforByKey("idnumber");
        var companyId = rosten.kernel.getUserInforByKey("companyid");
        rosten.openNewWindow("staffDepartChange", rosten.webPath + "/staff/staffDepartChangeAdd?companyId=" + companyId + "&userid=" + userid + "&flowCode=staffDepartChange");
    };
	change_staffDepartChange = function() {
		var unid = rosten.getGridUnid("single");
		if (unid == "")
			return;
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		rosten.openNewWindow("staffDepartChange", rosten.webPath + "/staff/staffDepartChangeShow/" + unid + "?userid=" + userid + "&companyId=" + companyId+ "&flowCode=staffDepartChange");
	};
	read_staffDepartChange = function() {
		change_staffDepartChange();
	};
	delete_staffDepartChange = function() {
		var _1 = rosten.confirm("删除后将无法恢复，是否继续?");
		_1.callback = function() {
			var unids = rosten.getGridUnid("multi");
			if (unids == "")
				return;
			var content = {};
			content.id = unids;
			rosten.readNoTime(rosten.webPath + "/staff/staffDepartChangeDelete", content,
					rosten.deleteCallback);
		};
	};
	
	personInfor_formatTopic_normal =function(value,rowIndex){
		return "<a href=\"javascript:personInfor_normal_onMessageOpen(" + rowIndex + ");\">" + value + "</a>";
	};
	personInfor_normal_onMessageOpen =function(rowIndex){
		var unid = rosten.kernel.getGridItemValue(rowIndex,"id");
        var userid = rosten.kernel.getUserInforByKey("idnumber");
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		
		var tmpArgs = "&type=" + rosten.kernel.navigationEntity;
		if(rosten.kernel.navigationEntity=="staffAdd"){
			tmpArgs +="&flowCode=" + rosten.kernel.navigationEntity;
		}
		rosten.openNewWindow("personInfor", rosten.webPath + "/staff/userShow/" + unid + "?userid=" + userid + "&companyId=" + companyId + tmpArgs);
		rosten.kernel.getGrid().clearSelected();
	};
	
	staffLeave = function(){
		var userId = registry.byId("userId");
		var userDepartId = registry.byId("userDepartId");
    	if(!userDepartId.isValid()){
    		rosten.alert("请单击<查询验证>按钮进行员工信息验证！");
			return;
    	}
    	
		var commentDialog = rosten.addCommentDialog({});
		commentDialog.title = "离职原因";
		commentDialog.callback = function(_data){
			rosten.readSync(rosten.webPath + "/staff/staffLeave/" + userId.attr("value"),{dataStr:_data.content},function(data){
				if(data.result=="true" || data.result == true){
					rosten.alert("成功！");
				}else{
					rosten.alert("失败!");
				}	
			});
		};
	};
	staffRetire = function(){
		//退休
		var userId = registry.byId("userId");
		var userDepartId = registry.byId("userDepartId");
    	if(!userDepartId.isValid()){
    		rosten.alert("请单击<查询验证>按钮进行员工信息验证！");
			return;
    	}
    	
		var commentDialog = rosten.addCommentDialog({});
		commentDialog.title = "退休原因";
		commentDialog.callback = function(_data){
			rosten.readSync(rosten.webPath + "/staff/staffRetire/" + userId.attr("value"),{dataStr:_data.content},function(data){
				if(data.result=="true" || data.result == true){
					rosten.alert("成功！");
				}else{
					rosten.alert("失败!");
				}	
			});
		};
	};
	staffChangeDepart = function(){
		var userId = registry.byId("userId");
		var userDepartId = registry.byId("userDepartId");
    	if(!userDepartId.isValid()){
    		rosten.alert("请单击<查询验证>按钮进行员工信息验证！");
			return;
    	}

    	var newDepartId = registry.byId("newDepartId");
    	if(!newDepartId.isValid()){
    		rosten.alert("请选择调入部门！").queryDlgClose = function(){
    			registry.byId("newdepartName").focus();
			};
			return;
    	}

		var content ={
			userId:userId.attr("value"),
			userDepartId:userDepartId.attr("value"),
			newDepartId:newDepartId.attr("value")	
		}
		rosten.readSync(rosten.webPath + "/staff/staffChangeDepart", content, function(data) {
			if (data.result == "true" || data.result == true) {
	            rosten.alert("变更成功!").queryDlgClose = function(){
	            	show_staffNaviEntity("staffDepartChange");
				};
	        } else {
	            rosten.alert("变更失败!");
	        }
    	
		});
	}
	
	/*
	 * 此功能默认必须存在
	 */
	show_staffNaviEntity = function(oString) {
		
		var companyId = rosten.kernel.getUserInforByKey("companyid");
		var userid = rosten.kernel.getUserInforByKey("idnumber");
		
		switch (oString) {
		case "serialNoCodeManage":
            rosten.kernel.setHref(rosten.webPath + "/staff/serialNoCodeManage", oString);
            break;
		case "staffManage" :
            //员工管理
            var companyId = rosten.kernel.getUserInforByKey("companyid");
            rosten.kernel.setHref(rosten.webPath + "/staff/depart?companyId=" + companyId, oString);
            break; 
		case "staffAdd":	//员工入职
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/staffAction/staffAddView?userId=" + userid,
				searchSrc:rosten.webPath + "/staff/searchView",
				gridSrc : rosten.webPath + "/staff/staffGrid?companyId=" + companyId + "&userId=" + userid + "&type=" + oString
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "staffRegi":	//员工管理
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/staffAction/staffView?userId=" + userid,
				searchSrc:rosten.webPath + "/staff/searchView",
				gridSrc : rosten.webPath + "/staff/staffGrid?companyId=" + companyId + "&userId=" + userid + "&type=" + oString
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "staffProba":	//员工转正
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/staffAction/officialApplyView?userId=" + userid,
				gridSrc : rosten.webPath + "/staff/officialApplyGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "staffDepartChange":	//员工调动
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/staffAction/staffDepartChangeView?userId=" + userid,
				searchSrc:rosten.webPath + "/staff/departChangeSearchView",
				gridSrc : rosten.webPath + "/staff/staffDepartChangeGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "staffLeave":	//员工离职
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/staffAction/staffStatusChangeView?userId=" + userid +"&type=leave",
				searchSrc:rosten.webPath + "/staff/statusChangeSearchView",
				gridSrc : rosten.webPath + "/staff/staffStatusChangeGrid?companyId=" + companyId + "&userId=" + userid + "&type=leave"
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "staffRetire":	//员工退休
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/staffAction/staffStatusChangeView?userId=" + userid +"&type=retire",
				searchSrc:rosten.webPath + "/staff/statusChangeSearchView",
				gridSrc : rosten.webPath + "/staff/staffStatusChangeGrid?companyId=" + companyId + "&userId=" + userid + "&type=retire"
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "staffEngage":	//员工聘任
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/staffAction/engageView?userId=" + userid,
				gridSrc : rosten.webPath + "/staff/engageGrid?companyId=" + companyId + "&userId=" + userid
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		case "staffSearch":	//员工查询
			var naviJson = {
				identifier : oString,
				actionBarSrc : rosten.webPath + "/staffAction/staffView?userId=" + userid + "&type=" + oString,
				searchSrc:rosten.webPath + "/staff/searchView",
				gridSrc : rosten.webPath + "/staff/staffGrid?companyId=" + companyId + "&userId=" + userid + "&type=" + oString
			};
			rosten.kernel.addRightContent(naviJson);
			break;
		}
	};
	connect.connect("show_naviEntity", show_staffNaviEntity);
});
