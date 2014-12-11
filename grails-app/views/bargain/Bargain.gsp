<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>合同信息</title>
    <style type="text/css">
    	.rosten .dsj_form table tr{
    		height:30px;
    	}
    	body{
			overflow:auto;
		}
		.rosten .rostenTitleGrid .dijitTitlePaneContentInner{
			padding:1px;
		
		}
		
		.divHide{
			display:none;
		}
    </style>
	<script type="text/javascript">
	require(["dojo/parser",
		 		"dojo/_base/kernel",
		 		"dijit/registry",		 		
		 		"dojo/dom",
		 		"dojo/_base/lang",		 		
		 		"dijit/layout/TabContainer",
		 		"dijit/layout/ContentPane",
		 		"dijit/form/ValidationTextBox",
		 		"dijit/form/RadioButton",
		 		"dijit/form/DateTextBox",
		 		"dijit/form/SimpleTextarea",
		 		"dijit/form/NumberTextBox",
		 		"dijit/form/Button",
		 		"dijit/form/Form",
		     	"rosten/widget/ActionBar",
		     	"rosten/widget/TitlePane",
		     	"rosten/app/Application",
		     	"rosten/kernel/behavior"],
			function(parser,kernel,registry,dom,lang){
				kernel.addOnLoad(function(){
					rosten.init({webpath:"${request.getContextPath()}",dojogridcss : true});
					rosten.cssinit();
				});
				bargain_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};

					content.bargainGoodsValues = rosten.getGridDataCollect(bargainGoodsGrid,[
					                                                     					"bargainGoodsName",
					                                                     					"bargainGoodsCorp",
																							"bargainGoodsUnit",
																							"bargainGoodsNum",
																							"bargainGoodsPrice",
																							"bargainGoodsDiscount",
																							"bargainGoodsRemark",
					]
							);

					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/bargain/bargainSave",content,function(data){
						if(data.result=="true" || data.result == true){
							rosten.alert("保存成功！").queryDlgClose= function(){
								page_quit();
							};
						}else{
							rosten.alert("保存失败!");
						}
						rosten.toggleAction(buttonWidget,false);
					},function(error){
						rosten.alert("系统错误，请通知管理员！");
						rosten.toggleAction(buttonWidget,false);
					},"rosten_form");
					
				};



				//增加清单ITEM~~~~~~~~
				bargainGoods_addItem = function(){
					rosten.createRostenShowDialog(rosten.webPath + "/bargain/bargainGoodsAdd", {
			            onLoadFunction : function() {

				            }
			        });
				};
				bargainGoods_Submit = function(){
					//var chenkids = ["ExpenseReimHappenDate","ExpenseReimItemType","ExpenseReimItemMoney"];
					//if(!rosten.checkData(chenkids)) return;					
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
							dojo.query(".rostenTitleGrid").style("display","block");
						}else{
							dojo.query(".rostenTitleGrid").style("display","none");
					}
					
					};
				
				//ITEM~~~~~~~

				
				page_quit = function(){
					rosten.pagequit();
				};
				showSelectDialog = function(type){
					switch(type){
					case "BargainVendorCorpName":
						var corpName = registry.byId("BargainVendorCorpName").get("value");
						rosten.selectBaseSelect("单位选择","${createLink(controller:'baseinfor',action:'getContactCorpSelect',params:[companyId:company?.id])}",false,"BargainVendorCorpName","bargainVendorCorpNameId",corpName);
						break;

					case "BargainPurchaserCorpName":
						var corpName = registry.byId("BargainPurchaserCorpName").get("value");
						rosten.selectBaseSelect("单位选择","${createLink(controller:'baseinfor',action:'getContactCorpSelect',params:[companyId:company?.id])}",false,"BargainPurchaserCorpName","bargainPurchaserCorpNameId",corpName);
						break;	
					}
					
				}
			
		});
    </script>
</head>
<body>
<div class="rosten_action">
	<div data-dojo-type="rosten/widget/ActionBar" data-dojo-id="rosten_actionBar" 
		data-dojo-props='actionBarSrc:"${createLink(controller:'bargainAction',action:'bargainForm',id:bargain?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='doLayout:false,persist:false,tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props='doLayout:false'>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"}',value:"${bargain?.id}"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id}"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"合同信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>合同名称：</div></td>
					    <td >
					    	<input id="bargainName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainName",
									value:"${bargain?.bargainName}"
			                '/>
					    </td>
						    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>合同类型：</div></td>
						    <td width="250">
						    	<select id="bargainType"  onChange='bargainType_onChange()'   data-dojo-type="dijit/form/FilteringSelect" 
					                data-dojo-props='name:"bargainType",${fieldAcl.isReadOnly("bargainType")},
					                trim:true,required:true,missingMessage:"请选择类别！",invalidMessage:"请选择类别！",
					      			value:"${bargain?.bargainType}",
					            '>
								<option value="总包合同">总包合同</option>
								<option value="分包合同">分包合同</option>
								<option value="采购合同">采购合同</option>
								<option value="销售合同">销售合同</option>
					    	</select>
				           </td>
					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>合同号：</div></td>
					    <td >
					    	<input id="bargainNo" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainNo",
									value:"${bargain?.bargainNo}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>合同金额：</div></td>
					    <td >
					    	<input id="bargainMoney" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainMoney",
									value:"${bargain?.bargainMoney}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>制表人：</div></td>
					    <td >
					    	<input id="bargainMaker" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainMaker",
									value:"${bargain?.bargainMaker}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>签订日期：</div></td>
					    <td>
						    <input id="bargainSigningDate" data-dojo-type="dijit/form/DateTextBox" 
		               		data-dojo-props='name:"bargainSigningDate",${fieldAcl.isReadOnly("bargainSigningDate")},
		               		trim:true,required:true,
							value:"${bargain?.getFormatteBargainSignDate()}"
		          			'/>
			            </td>
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>付款情况：</div></td>
					    <td colspan=3>
					    	<textarea id="bargainPayMemo" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"bargainPayMemo","class":"input",
                               		style:{width:"550px"},rows:"2",
                               		trim:true,value:"${bargain?.bargainPayMemo}"
                           '>
                           </textarea>
					    </td>
					</tr>
					</table>
					<div style="clear:both;"></div>

			</div>

		
		
		
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"合同甲方信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>甲方：</div></td>
					    <td width="250">
					    	<input id="bargainVendor" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainVendor",
									value:"${bargain?.bargainVendor}"
			                '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>甲方单位名称：</div></td>
					    <td width="250">
					    	<input id="BargainVendorCorpName" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"BargainVendorCorpName",
				               		trim:true,required:true,
									value:"${bargain?.getBargainVendorName()}"
				          	'/>
				          	<g:if test="${!onlyShow }">
					         	<g:hiddenField data-dojo-type="dijit/form/ValidationTextBox" name="bargainVendorCorpNameId" value="${bargain?.BargainVendorCorpName?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("BargainVendorCorpName");	
									}'>选择</button>
			           		</g:if>
			           	</td>
					</tr>
										<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>法人：</div></td>
					    <td >
					    	<input data-dojo-type="dijit/form/ValidationTextBox" data-dojo-props='trim:true,required:true,
									value:"${BargainVendorCorpName?.contactCorpLealPerson}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>法人职务：</div></td>
					    <td >
					    	<input data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,
									value:"${BargainVendorCorpName?.contactCorpLealPersonDuty}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>电话：</div></td>
					    <td >
					    	<input  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,
									value:"${BargainVendorCorpName?.contactCorpPhone}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>邮编：</div></td>
					    <td >
					    	<input  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,
									value:"${BargainVendorCorpName?.contactCorpPost}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>地址：</div></td>
					 	 <td colspan=3>
					    	<input  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,style:{width:"550px"},
									value:"${BargainVendorCorpName?.contactCorpAddress}"
			                '/>
					    </td>
					   
					</tr>


				</table>
				<div style="clear:both;"></div>
			</div>


			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"合同乙方信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>乙方：</div></td>
					    <td width="250">
					    	<input id="bargainPurchaser" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainPurchaser",
									value:"${bargain?.bargainPurchaser}"
			                '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>乙方单位名称：</div></td>
					    <td width="250">
					    	<input id="BargainPurchaserCorpName" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"BargainPurchaserCorpName",
				               		trim:true,required:true,
									value:"${bargain?.getBargainPurchaserName()}"
				          	'/>
				          <g:if test="${!onlyShow }">
					         	<g:hiddenField data-dojo-type="dijit/form/ValidationTextBox" name="bargainPurchaserCorpNameId" value="${Bargain?.BargainPurchaserCorpName?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("BargainPurchaserCorpName");	
									}'>选择</button>
			           		</g:if>
			           	</td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>法人：</div></td>
					    <td >
					    	<input  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true, 
									value:"${bargain?.BargainPurchaserCorpName?.contactCorpLealPerson}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>法人职务：</div></td>
					    <td >
					    	<input   data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true, 
									value:"${BargainPurchaserCorpName?.contactCorpLealPersonDuty}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>电话：</div></td>
					    <td >
					    	<input  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true, 
									value:"${BargainPurchaserCorpName?.contactCorpPhone}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>邮编：</div></td>
					    <td >
					    	<input   data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true, 
									value:"${BargainPurchaserCorpName?.contactCorpPost}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>地址：</div></td>
					    <td colspan=3>
					    	<input  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,style:{width:"550px"},
									value:"${BargainPurchaserCorpName?.contactCorpAddress}"
			                '/>
					    </td>
					   
					</tr>


				</table>
				<div style="clear:both;"></div>
			</div>
		
		
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='"class":"rostenTitleGrid",style:{display:"none"},title:"采购货物明细",toggleable:false,_moreClick:bargainGoods_addItem,moreText:"<span style=\"color:#108ac6\">增加</span>",marginBottom:"2px"'>
            	<div data-dojo-type="rosten/widget/RostenGrid" id="bargainGoodsGrid" data-dojo-id="bargainGoodsGrid"
					data-dojo-props='imgSrc:"${resource(dir:'images/rosten/share',file:'wait.gif')}",showPageControl:false,url:"${createLink(controller:'bargain',action:'bargainGoodsGrid',id:bargain?.id)}"'></div>             	
            	
            </div>
            
           
            
		</form>

	</div>
</div>
<div style="clear:both;height:50px"></div>
</body>
