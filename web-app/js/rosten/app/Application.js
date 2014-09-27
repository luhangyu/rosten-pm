/**
 * @author rosten
 */
define(["dojo/_base/lang",
		"dijit/registry",
		"rosten/widget/MultiSelectDialog",
		"rosten/widget/PickTreeDialog",
		"rosten/widget/DepartUserDialog",
		"rosten/kernel/_kernel"], function(lang,registry,MultiSelectDialog,PickTreeDialog,DepartUserDialog) {
			
	var application = {};
	application.checkData = function(chenkids){
		var flag=true;
		for(var i = 0 ;i<chenkids.length;i++){
			var obj = registry.byId(chenkids[i]);
			if(!obj.isValid()){
				rosten.alert("请正确填写信息！").queryDlgClose = function(){
					obj.focus();
				};
				flag=false;
				break;
			}
		}
		return flag;
	};
    application.cssinitcommon = function() {
        //此功能只添加css文件
        var _rosten = window.opener.rosten;
        var dojocss = _rosten.dojothemecss;
        var rostencss = _rosten.rostenthemecss;

        rosten.addDojoThemeCss(dojocss);
        rosten.addRostenCss(rostencss);
    };
    application.cssinit = function() {
        var _rosten = window.opener.rosten;
        var dojocss = _rosten.dojothemecss;
        var rostencss = _rosten.rostenthemecss;

        rosten.replaceDojoTheme(dojocss, false);
        rosten.replaceRostenTheme(rostencss);
    };
    /*
     * 关闭当前窗口，并刷新父文档视图
     */
    application.pagequit = function() {
        var parentNode = window.opener;
        window.close();
        
    	if(parentNode.rosten.kernel){
    		parentNode.rosten.kernel.refreshGrid();
    	}
    	
    };
    application.selectDialog = function(dialogTitle,id,url,flag,defaultValue,reload){
		/*
		 * dialogTitle:dialog中的titile
		 * id:dialog的id号需唯一
		 * url:url
		 * flag：是否多选，true为多选，默认为false
		 * reload:是否重新载入
		 * defaultValue：对话框中显示的值,为[]数组
		 */
		if (!(rosten[id] && registry.byId(id))) {
			rosten[id] = new MultiSelectDialog({
				title:dialogTitle,
		        id: id,
				single:!flag,
				datasrc:url
			});
			if(defaultValue!=undefined){
				rosten[id].defaultvalues = defaultValue;
			}
			rosten[id].open();
		}else{
			rosten[id].single = !flag;
			if(defaultValue!=undefined){
				rosten[id].defaultvalues = defaultValue;
			}
			rosten[id].open();
			if(reload!=undefined && reload==true){
				rosten[id].datasrc = url;
				rosten[id].refresh();
			}else{
				rosten[id].simpleRefresh();
			}
		}
		
	};
	application.selectFlowUser = function(url,type){
        var id = "sys_flowUserDialog";

        if (rosten[id] && registry.byId(id)) {
        	if (rosten[id].initialized == false) {
        		rosten[id].buildContent(rosten[id].contentPane);
        		rosten[id].buildControl(rosten[id].controlPane);
        		rosten[id].initialized = true;
			}
            rosten[id].refresh();
        } else {
            var args = {
                url : url,
                type:type
            };
            rosten[id] = new DepartUserDialog(args);
            if (rosten[id].initialized == false) {
        		rosten[id].buildContent(rosten[id].contentPane);
        		rosten[id].buildControl(rosten[id].controlPane);
        		rosten[id].initialized = true;
			}
        }
//        var _data = rosten[id].getData();
//        if(_data && _data.length==1){
//            //直接调用
//        	rosten[id].doAction();
//        }else{
//			//显示对话框
//        	rosten[id].open();
//	    }
        return rosten[id];
    };
    application.selectUser = function(url,type,inputName,inputId){
        var id = "sys_userDialog";

        if (rosten[id] && registry.byId(id)) {
            rosten[id].open();
            rosten[id].refresh();
        } else {
            var args = {
                url : url,
                type:type
            };
            rosten[id] = new DepartUserDialog(args);
            rosten[id].open();
        }
        rosten[id].callback = function(data) {
            var _data = [];
            var _data_1 = [];
            for (var k = 0; k < data.length; k++) {
                var item = data[k];
                _data.push(item.name);
                _data_1.push(item.value);
            }
            if( inputName !=undefined){
                registry.byId(inputName).attr("value", _data.join(","));
            }
            if( inputId !=undefined){
                registry.byId(inputId).attr("value",_data_1.join(","));
            }
        }; 
        return rosten[id];
    };
	application.selectDepart = function(url,type,inputName,inputId) {
        var id = "sys_departDialog";

        if (rosten[id] && registry.byId(id)) {
            rosten[id].open();
            rosten[id].refresh();
        } else {
            var args = {
                url : url,
                rootLabel : "部门层级",
                showCheckBox : type,
                folderClass : "departTree"
            };
            rosten[id] = new PickTreeDialog(args);
            rosten[id].open();
        }
        rosten[id].callback = function(data) {
            var _data = [];
            var _data_1 = [];
            for (var k = 0; k < data.length; k++) {
                var item = data[k];
                _data.push(item.name);
                _data_1.push(item.id);

            }
            if( inputName !=undefined){
            	registry.byId(inputName).attr("value", _data.join(","));
            }
            if( inputId !=undefined){
            	registry.byId(inputId).attr("value", _data_1.join(","));
            }
        };
    };
    
    lang.mixin(rosten,application);
    
    return application;
});
