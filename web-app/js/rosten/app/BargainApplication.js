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
				store.setValue(items[i],"barGoodsName",registry.byId("barGoodsName").get("value"));
				store.setValue(items[i],"barGoodsCorp",registry.byId("barGoodsCorp").get("value"));
				store.setValue(items[i],"barGoodsUnit",registry.byId("barGoodsUnit").get("value"));
				store.setValue(items[i],"barGoodsNum",registry.byId("barGoodsNum").get("value"));
				store.setValue(items[i],"barGoodsPrice",registry.byId("barGoodsPrice").get("value"));
				store.setValue(items[i],"barGoodsDiscount",registry.byId("barGoodsDiscount").get("value"));
				store.setValue(items[i],"barGoodsRemark",registry.byId("barGoodsRemark").get("value"));
				store.setValue(items[i],"barGoodsTPrice",registry.byId("barGoodsTPrice").get("value"));
				
			}else{
				
				var randId = Math.random();
				var content ={
						id:randId,
						bargainGoodsId:randId,
						rowIndex:items.length+1,
						//BargainId:registry.byId("BargainId").get("value"),
						itemId:registry.byId("id").get("value"),
						barGoodsName:registry.byId("barGoodsName").get("value"),
						barGoodsCorp:registry.byId("barGoodsCorp").get("value"),
						barGoodsUnit:registry.byId("barGoodsUnit").get("value"),
						barGoodsNum:registry.byId("barGoodsNum").get("value"),
						barGoodsPrice:registry.byId("barGoodsPrice").get("value"),
						barGoodsDiscount:registry.byId("barGoodsDiscount").get("value"),
						barGoodsRemark:registry.byId("barGoodsRemark").get("value"),
						
						barGoodsTPrice:registry.byId("barGoodsTPrice").get("value"),
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
            	var barGoodsName = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"barGoodsName");
            	var barGoodsCorp = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"barGoodsCorp");
            	var barGoodsUnit = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"barGoodsUnit");
            	var barGoodsNum = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"barGoodsNum");
            	var barGoodsPrice = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"barGoodsPrice");
            	var barGoodsDiscount = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"barGoodsDiscount");
            	var barGoodsRemark = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"barGoodsRemark");
				alert(barGoodsRemark);
            	var barGoodsTPrice = rosten.getGridItemValue(bargainGoodsGrid,rowIndex,"barGoodsTPrice");
            	
            	registry.byId("itemId").set("value",id);
            	registry.byId("barGoodsName").set("value",barGoodsName);
            	registry.byId("barGoodsCorp").set("value",barGoodsCorp);
            	registry.byId("barGoodsUnit").set("value",barGoodsUnit);
            	registry.byId("bargainGoodsNum").set("value",barGoodsNum);
            	registry.byId("barGoodsPrice").set("value",barGoodsPrice);
            	registry.byId("barGoodsDiscount").set("value",barGoodsDiscount);
            	registry.byId("barGoodsRemark").set("value",barGoodsRemark);
            	
            	registry.byId("barGoodsTPrice").set("value",barGoodsTPrice);
            
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
	
	barGoods_onChange=function(){
		
		var price = registry.byId("barGoodsPrice").get("value");
		var nums = registry.byId("barGoodsNum").get("value");
	
    	var tPrice=price*nums;
    	
    	registry.byId("barGoodsTPrice").set("value",tPrice);
	}
	
});
