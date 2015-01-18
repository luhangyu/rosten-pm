/**
 * @author rosten
 */
define(["dojo/dom",
        "dojo/_base/kernel",
        "dijit/registry",
        "rosten/app/Application",
        "rosten/kernel/behavior"], function(dom,kernel,registry) {
	
	//增加清单ITEM~~~~~~~~
	constructLog_addItem = function(){
		var planId = registry.byId("id").get("value");
		rosten.createRostenShowDialog(rosten.webPath + "/project/constructLogAdd?planId="+planId, {
            onLoadFunction : function() {

	            }
        });
	};
	constructLog_Submit = function(){
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
//				store.setValue(items[i],"projPlanName",registry.byId("projPlanName").get("value"));
//				store.setValue(items[i],"s_constructPart",registry.byId("s_constructPart").get("value"));
				store.setValue(items[i],"getFormatteConstructDate",registry.byId("constructDate").get("displayedValue"));
				store.setValue(items[i],"consDoneQutt",registry.byId("consDoneQutt").get("value"));
				store.setValue(items[i],"consDoneRate",registry.byId("consDoneRate").get("value"));
				store.setValue(items[i],"logMaker",registry.byId("logMaker").get("value"));
				store.setValue(items[i],"conslogRemark",registry.byId("conslogRemark").get("value"));
			}else{
				var randId = Math.random();
				var content ={
						id:randId,
						constructLogId:randId,
						rowIndex:items.length+1,
						//BargainId:registry.byId("BargainId").get("value"),
						itemId:registry.byId("id").get("value"),
//						getProjPlanName:registry.byId("projPlanName").get("value"),
//						getConstructPart:registry.byId("s_constructPart").get("value"),
						getFormatteConstructDate:registry.byId("constructDate").get("displayedValue"),
						consDoneQutt:registry.byId("consDoneQutt").get("value"),
						consDoneRate:registry.byId("consDoneRate").get("value"),
						logMaker:registry.byId("logMaker").get("value"),
						conslogRemark:registry.byId("conslogRemark").get("value"),
				};
				store.newItem(content);
			}
		}
		
		var store = constructLogGrid.getStore();
		store.fetch({
			query:{id:"*"},onComplete:gotAll,queryOptions:{deep:true}
		});
		rosten.hideRostenShowDialog();
	};
	constructLog_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:constructLog_onMessageOpen(" + rowIndex + ");\">" + value+ "</a>";
	};
	constructLog_onMessageOpen = function(rowIndex){
		//打开systemCodeItem信息
    	rosten.createRostenShowDialog(rosten.webPath + "/project/constructLogShow", {
            onLoadFunction : function() {
	            
            	var id = rosten.getGridItemValue(constructLogGrid,rowIndex,"id");			            
//            	var getProjPlanName = rosten.getGridItemValue(constructLogGrid,rowIndex,"getProjPlanName");
//            	var getConstructPart = rosten.getGridItemValue(constructLogGrid,rowIndex,"getConstructPart");
            	var constructDate = rosten.getGridItemValue(constructLogGrid,rowIndex,"getFormatteConstructDate");
            	var consDoneQutt = rosten.getGridItemValue(constructLogGrid,rowIndex,"consDoneQutt");
            	var consDoneRate = rosten.getGridItemValue(constructLogGrid,rowIndex,"consDoneRate");
            	var logMaker = rosten.getGridItemValue(constructLogGrid,rowIndex,"logMaker");
            	var conslogRemark = rosten.getGridItemValue(constructLogGrid,rowIndex,"conslogRemark");
            	//alert(constructDate);
            	registry.byId("itemId").set("value",id);
//            	registry.byId("projPlanName").set("value",getProjPlanName);
//            	registry.byId("s_constructPart").set("value",getConstructPart);
            	registry.byId("constructDate").set("displayedValue",constructDate);
            	registry.byId("consDoneQutt").set("value",consDoneQutt);
            	registry.byId("consDoneRate").set("value",consDoneRate);
            	registry.byId("logMaker").set("value",logMaker);
            	registry.byId("conslogRemark").set("value",conslogRemark);
            
	        }
        });
    };

    constructLog_action = function(value,rowIndex){
    	return "<a href=\"javascript:constructLog_onDelete(" + rowIndex + ");\">" + "删除" + "</a>";
	};
	constructLog_onDelete = function(rowIndex){
		//删除item信息
		var store = constructLogGrid.getStore();
	    var item = rosten.getGridItem(constructLogGrid,rowIndex);
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
	
	
	bargainType_onChange=function(){
			constructLogTitlePane.resize();

	};
});
