/**
 * @author rosten
 */
define(["dojo/dom",
        "dojo/_base/kernel",
        "dijit/registry",
        "rosten/app/Application",
        "rosten/kernel/behavior",
        "dojox/validate/web"], function(dom,kernel,registry,validate) {
	
	//增加清单ITEM~~~~~~~~
	workerAttendance_addItem = function(){
		
		rosten.createRostenShowDialog(rosten.webPath + "/employe/workerAttendanceAdd", {
            onLoadFunction : function() {

	            }
        });
	};
	workerAttendance_Submit = function(){
//		var formWidget = registry.byId("rosten_form1");
//		if(!formWidget.validate()){
//			rosten.alert("请正确填写相关信息！");
//			return;
//		}

		
		var itemId = registry.byId("itemId").get("value");
		function gotAll(items,request){
			var node;
			for(var i=0;i < items.length;i++){
				var id = store.getValue(items[i], "id");
				if(id==itemId){
					node = items[i];
					break;
				}
			}
		
			if(node){
				store.setValue(items[i],"itemId",registry.byId("id").get("value"));
				store.setValue(items[i],"attendUserName",registry.byId("attendUserName").get("value"));
				store.setValue(items[i],"attendDepart",registry.byId("attendDepart").get("value"));
				store.setValue(items[i],"attendRemark",registry.byId("attendRemark").get("value"));
				store.setValue(items[i],"workNumber",registry.byId("workNumber").get("value"));
				store.setValue(items[i],"affairsNumber",registry.byId("affairsNumber").get("value"));
				store.setValue(items[i],"illNumber",registry.byId("illNumber").get("value"));
				
				store.setValue(items[i],"awayNumber",registry.byId("awayNumber").get("value"));
				store.setValue(items[i],"lateNumber",registry.byId("lateNumber").get("value"));
				store.setValue(items[i],"earlyAwayNumber",registry.byId("earlyAwayNumber").get("value"));
				
			}else{
				var randId = Math.random();
				var content ={
						id:randId,
						workerAttendanceId:randId,
						rowIndex:items.length+1,
						//BargainId:registry.byId("BargainId").get("value"),
						itemId:registry.byId("id").get("value"),
						attendUserName:registry.byId("attendUserName").get("value"),
						attendDepart:registry.byId("attendDepart").get("value"),
						attendRemark:registry.byId("attendRemark").get("value"),
						workNumber:registry.byId("workNumber").get("value"),						
						affairsNumber:registry.byId("affairsNumber").get("value"),
						illNumber:registry.byId("illNumber").get("value"),
						awayNumber:registry.byId("awayNumber").get("value"),
						lateNumber:registry.byId("lateNumber").get("value"),
						earlyAwayNumber:registry.byId("earlyAwayNumber").get("value"),
				};
				store.newItem(content);
			}
		}
		
		var store = workerAttendanceGrid.getStore();
		store.fetch({
			query:{id:"*"},onComplete:gotAll,queryOptions:{deep:true}
		});
		rosten.hideRostenShowDialog();
	};
	workerAttendance_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:workerAttendance_onMessageOpen(" + rowIndex + ");\">" + value+ "</a>";
	};
	workerAttendance_onMessageOpen = function(rowIndex){
		//打开systemCodeItem信息
    	rosten.createRostenShowDialog(rosten.webPath + "/employe/workerAttendanceShow", {
            onLoadFunction : function() {
	            
            	var id = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"id");			            
            	var attendUserName = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"attendUserName");
            	var attendDepart = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"attendDepart");
            	var attendRemark = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"attendRemark");
            	var workNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"workNumber");
            	var affairsNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"affairsNumber");
            	var illNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"illNumber");
            	
            	var awayNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"awayNumber");
            	var lateNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"lateNumber");
            	var earlyAwayNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"earlyAwayNumber");
            	
            	//alert(constructDate);
            	registry.byId("itemId").set("value",id);
            	registry.byId("attendUserName").set("value",attendUserName);
            	registry.byId("attendDepart").set("value",attendDepart);
            	registry.byId("attendRemark").set("displayedValue",attendRemark);
            	registry.byId("workNumber").set("value",workNumber);
            	registry.byId("affairsNumber").set("value",affairsNumber);
            	registry.byId("illNumber").set("value",illNumber);
            	
            	registry.byId("awayNumber").set("value",awayNumber);
            	registry.byId("lateNumber").set("value",lateNumber);
            	registry.byId("earlyAwayNumber").set("value",earlyAwayNumber);
	        }
        });
    };

    workerAttendance_action = function(value,rowIndex){
    	return "<a href=\"javascript:workerAttendance_onDelete(" + rowIndex + ");\">" + "删除" + "</a>";
	};
	workerAttendance_onDelete = function(rowIndex){
		//删除item信息
		var store = workerAttendanceGrid.getStore();
	    var item = rosten.getGridItem(workerAttendanceGrid,rowIndex);
		store.deleteItem(item);
		//更新store中的rowIndex号
		store.fetch({
			query:{id:"*"},onComplete:function(items){
				for(var i=0;i < items.length;i++){
					var _item = items[i];
					store.setValue(_item,"rowIndex",i+1);
				}
			},queryOptions:{deep:true}
		});
	};
	
	
//	bargainType_onChange=function(){
//			workerAttendanceTitlePane.resize();
//
//	};
});
