/**
 * @author rosten
 */
define(["dojo/dom",
        "dojo/_base/kernel",
        "dijit/registry",
        "rosten/app/Application",
        "rosten/kernel/behavior",
        "dojox/validate/web"], function(dom,kernel,registry,validate) {
	/*
	 * 考勤
	 */
	//增加考勤清单ITEM~~~~~~~~
	workerAttendance_addItem = function(){
		var attendType = registry.byId("attendType").get("value");
		
		rosten.createRostenShowDialog(rosten.webPath + "/employe/workerAttendanceAdd?attendtype="+attendType, {
            onLoadFunction : function() {

	            }
        });
	};
	workerAttendance_Submit = function(){
		var itemId = registry.byId("itemId").get("value");
		var attendType=registry.byId("attendType").get("value");		
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
				if(attendType=="员工考勤"){					
					store.setValue(items[i],"affairsNumber",registry.byId("affairsNumber").get("value"));
					store.setValue(items[i],"illNumber",registry.byId("illNumber").get("value"));				
					store.setValue(items[i],"lateNumber",registry.byId("lateNumber").get("value"));
					store.setValue(items[i],"awayNumber",registry.byId("awayNumber").get("value"));
					store.setValue(items[i],"earlyAwayNumber",registry.byId("earlyAwayNumber").get("value"));
				}
			}else{
				var randId = Math.random();				
				if(attendType == "员工考勤"){
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
							lateNumber:registry.byId("lateNumber").get("value"),
							awayNumber:registry.byId("awayNumber").get("value"),
							earlyAwayNumber:registry.byId("earlyAwayNumber").get("value"),							
					};
				}else{
					var content ={
							id:randId,
							workerAttendanceId:randId,
							rowIndex:items.length+1,							
							itemId:registry.byId("id").get("value"),
							attendUserName:registry.byId("attendUserName").get("value"),
							attendDepart:registry.byId("attendDepart").get("value"),
							attendRemark:registry.byId("attendRemark").get("value"),
							workNumber:registry.byId("workNumber").get("value"),						
//							livingRmb:registry.byId("livingRmb").get("value"),
//							shouldRmb:registry.byId("shouldRmb").get("value"),
//							deductRmb:registry.byId("deductRmb").get("value"),
//							bonusRmb:registry.byId("bonusRmb").get("value"),
//							finalRmb:registry.byId("finalRmb").get("value"),
							
					};
				}
				
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
            	
            	var lateNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"lateNumber");
            	var awayNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"awayNumber");
            	var earlyAwayNumber = rosten.getGridItemValue(workerAttendanceGrid,rowIndex,"earlyAwayNumber");
            	
            	//alert(constructDate);
            	registry.byId("itemId").set("value",id);
            	registry.byId("attendUserName").set("value",attendUserName);
            	registry.byId("attendDepart").set("value",attendDepart);
            	registry.byId("attendRemark").set("displayedValue",attendRemark);
            	registry.byId("workNumber").set("value",workNumber);
            	registry.byId("affairsNumber").set("value",affairsNumber);
            	registry.byId("illNumber").set("value",illNumber);
            	
            	registry.byId("lateNumber").set("value",lateNumber);
            	registry.byId("awayNumber").set("value",awayNumber);
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
	convertString_ToInt = function(a){
		(typeof(a)!="undefined" && a!="")?"":a=0;
		a=parseFloat(a);
		//(a>=0 && a<=1)?"":alert("填写不规范，必须在0-1之间，请返回修改！");
		if(a>=0 && a<=1){
			return true;
		}else{
			alert("填写不规范，必须在0-1之间，请返回修改！");
			return false
		}
	}	
	attend_onChange=function(obj){
		//convertToInt(obj.value)?"":(registry.byId("workNumber").set("value",0));
		convertString_ToInt(obj.value)?"":(obj.set("value",0));
	}
	
	
	
	
	
	/*
	 * 工资
	 */
	//增加工资明细ITEM~~~~~~~~
	workerSalary_addItem = function(){
		rosten.createRostenShowDialog(rosten.webPath + "/employe/workerSalaryAdd", {
            onLoadFunction : function() {

	            }
        });
	};
	workerSalary_Submit = function(){
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
				store.setValue(items[i],"workerName",registry.byId("workerName").get("value"));
				store.setValue(items[i],"workerDepart",registry.byId("workerDepart").get("value"));
				store.setValue(items[i],"salaryType",registry.byId("salaryType").get("value"));
				store.setValue(items[i],"wrkSlyRemark",registry.byId("wrkSlyRemark").get("value"));
				
				store.setValue(items[i],"salaryRmb",registry.byId("salaryRmb").get("value"));											
				store.setValue(items[i],"livingRmb",registry.byId("livingRmb").get("value"));
				store.setValue(items[i],"shouldRmb",registry.byId("shouldRmb").get("value"));				
				store.setValue(items[i],"deductRmb",registry.byId("deductRmb").get("value"));
				store.setValue(items[i],"bonusRmb",registry.byId("bonusRmb").get("value"));
				store.setValue(items[i],"finalRmb",registry.byId("finalRmb").get("value"));
				
			}else{
				var randId = Math.random();				
				var content ={
							id:randId,
							workerSalaryId:randId,
							rowIndex:items.length+1,
							//BargainId:registry.byId("BargainId").get("value"),
							itemId:registry.byId("id").get("value"),
							workerName:registry.byId("workerName").get("value"),
							workerDepart:registry.byId("workerDepart").get("value"),
							salaryType:registry.byId("salaryType").get("value"),
							wrkSlyRemark:registry.byId("wrkSlyRemark").get("value"),
							
							salaryRmb:registry.byId("salaryRmb").get("value"),						
							livingRmb:registry.byId("livingRmb").get("value"),
							shouldRmb:registry.byId("shouldRmb").get("value"),
							deductRmb:registry.byId("deductRmb").get("value"),
							bonusRmb:registry.byId("bonusRmb").get("value"),
							finalRmb:registry.byId("finalRmb").get("value"),							
					};
				store.newItem(content);
			}
				
				
			
		}
		
		var store = workerSalaryGrid.getStore();
		store.fetch({
			query:{id:"*"},onComplete:gotAll,queryOptions:{deep:true}
		});
		rosten.hideRostenShowDialog();
	};
	
	workerSalary_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:workerSalary_onMessageOpen(" + rowIndex + ");\">" + value+ "</a>";
	};
	workerSalary_onMessageOpen = function(rowIndex){
		//打开systemCodeItem信息
    	rosten.createRostenShowDialog(rosten.webPath + "/employe/workerSalaryShow", {
            onLoadFunction : function() {
	            
            	var id = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"id");			            
            	var workerName = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"workerName");
            	var workerDepart = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"workerDepart");
            	var salaryType = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"salaryType");
            	
            	var wrkSlyRemark = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"wrkSlyRemark");
            	var salaryRmb = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"salaryRmb");
            	var livingRmb = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"livingRmb");
            	var shouldRmb = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"shouldRmb");            	
            	var deductRmb = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"deductRmb");
            	var bonusRmb = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"bonusRmb");
            	var finalRmb = rosten.getGridItemValue(workerSalaryGrid,rowIndex,"finalRmb");
            	
            	//alert(constructDate);
            	registry.byId("itemId").set("value",id);
            	registry.byId("workerName").set("value",workerName);
            	registry.byId("workerDepart").set("value",workerDepart);
            	registry.byId("salaryType").set("value",salaryType);
            	registry.byId("wrkSlyRemark").set("value",wrkSlyRemark);
            	
            	registry.byId("salaryRmb").set("value",salaryRmb);
            	registry.byId("livingRmb").set("value",livingRmb);
            	registry.byId("shouldRmb").set("value",shouldRmb);           	
            	registry.byId("deductRmb").set("value",deductRmb);
            	registry.byId("bonusRmb").set("value",bonusRmb);
            	registry.byId("finalRmb").set("value",finalRmb);
	        }
        });
    };

    workerSalary_action = function(value,rowIndex){
    	return "<a href=\"javascript:workerSalary_onDelete(" + rowIndex + ");\">" + "删除" + "</a>";
	};
	workerSalary_onDelete = function(rowIndex){
		//删除item信息
		var store = workerSalaryGrid.getStore();
	    var item = rosten.getGridItem(workerSalaryGrid,rowIndex);
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
	
	
	
	
	
	
	
	
	
	
});
