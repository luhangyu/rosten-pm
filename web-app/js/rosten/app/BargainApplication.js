/**
 * @author rosten
 */
define(["dojo/dom",
        "dojo/_base/kernel",
        "dijit/registry",
        "rosten/app/Application",
        "rosten/kernel/behavior"], function(dom,kernel,registry) {
	
	//增加清单ITEM~~~~~~~~
	bargainGoods_addItem = function(){
		rosten.createRostenShowDialog(rosten.webPath + "/bargain/bargainGoodsAdd", {
            onLoadFunction : function() {

	            }
        });
	};
	bargainGoods_Submit = function(){
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
				store.setValue(items[i],"bargainGoodsName",registry.byId("bargainGoodsName").get("value"));
				store.setValue(items[i],"bargainGoodsCorp",registry.byId("bargainGoodsCorp").get("value"));
				store.setValue(items[i],"bargainGoodsUnit",registry.byId("bargainGoodsUnit").get("value"));
				store.setValue(items[i],"bargainGoodsNum",registry.byId("bargainGoodsNum").get("value"));
				store.setValue(items[i],"bargainGoodsPrice",registry.byId("bargainGoodsPrice").get("value"));
				store.setValue(items[i],"bargainGoodsDiscount",registry.byId("bargainGoodsDiscount").get("value"));
				store.setValue(items[i],"bargainGoodsRemark",registry.byId("bargainGoodsRemark").get("value"));
				
			}else{
				
				var randId = Math.random();
				var content ={
						id:randId,
						bargainGoodsId:randId,
						rowIndex:items.length+1,
						//BargainId:registry.byId("BargainId").get("value"),
						itemId:registry.byId("id").get("value"),
						bargainGoodsName:registry.byId("bargainGoodsName").get("value"),
						bargainGoodsCorp:registry.byId("bargainGoodsCorp").get("value"),
						bargainGoodsUnit:registry.byId("bargainGoodsUnit").get("value"),
						bargainGoodsNum:registry.byId("bargainGoodsNum").get("value"),
						bargainGoodsPrice:registry.byId("bargainGoodsPrice").get("value"),
						bargainGoodsDiscount:registry.byId("bargainGoodsDiscount").get("value"),
						bargainGoodsRemark:registry.byId("bargainGoodsRemark").get("value"),

						
				};
				store.newItem(content);

			}
		}
		
		var store = bargainGoodsGrid.getStore();
		store.fetch({
			query:{id:"*"},onComplete:gotAll,queryOptions:{deep:true}
		});
		rosten.hideRostenShowDialog();
	};
	bargainGoods_formatTopic = function(value,rowIndex){
		return "<a href=\"javascript:bargainGoods_onMessageOpen(" + rowIndex + ");\">" + value+ "</a>";
	};
	bargainGoods_onMessageOpen = function(rowIndex){
		//打开systemCodeItem信息
    	rosten.createRostenShowDialog(rosten.webPath + "/bargain/bargainGoodsShow", {
            onLoadFunction : function() {
	            
            	var id = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"id");			            
            	var bargainGoodsName = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"bargainGoodsName");
            	var bargainGoodsCorp = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"bargainGoodsCorp");
            	var bargainGoodsUnit = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"bargainGoodsUnit");
            	var bargainGoodsNum = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"bargainGoodsNum");
            	var bargainGoodsPrice = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"bargainGoodsPrice");
            	var bargainGoodsDiscount = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"bargainGoodsDiscount");
            	var bargainGoodsRemark = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"bargainGoodsRemark");
				
            	
            	registry.byId("itemId").set("value",id);
            	registry.byId("bargainGoodsName").set("value",bargainGoodsName);
            	registry.byId("bargainGoodsCorp").set("value",bargainGoodsCorp);
            	registry.byId("bargainGoodsUnit").set("value",bargainGoodsUnit);
            	registry.byId("bargainGoodsNum").set("value",bargainGoodsNum);
            	registry.byId("bargainGoodsPrice").set("value",bargainGoodsPrice);
            	registry.byId("bargainGoodsDiscount").set("value",bargainGoodsDiscount);
            	registry.byId("bargainGoodsRemark").set("value",bargainGoodsRemark);

            
	        }
        });
    };

    bargainGoods_action = function(value,rowIndex){
    	return "<a href=\"javascript:bargainGoods_onDelete(" + rowIndex + ");\">" + "删除" + "</a>";
	};
	bargainGoods_onDelete = function(rowIndex){
		//删除item信息
		var store = bargainGoodsGrid.getStore();
	    var item = rosten.getGridItem(bargainGoodsGrid,rowIndex);
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
		var bargainType = registry.byId("bargainType").get("value");
		if(bargainType=="采购合同"){
				kernel.query(".rostenTitleGrid").style("display","block");
				bargainGoodsTitlePane.resize();
			}else{
				kernel.query(".rostenTitleGrid").style("display","none");
		}
	
	};
});
