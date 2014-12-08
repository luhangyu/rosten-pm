<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>报销信息</title>
    <style type="text/css">
    	.rosten .dsj_form table tr{
    		height:30px;
    		
    	}
    	body{
			overflow:auto;
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
					rosten.init({webpath:"${request.getContextPath()}"});
					rosten.cssinit();
				});
				expenseReimbursement_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/finance/expenseReimbursementSave",content,function(data){
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
				page_quit = function(){
					rosten.pagequit();
				};

				//增加清单ITEM
				expenseList_addItem = function(){
					rosten.createRostenShowDialog(rosten.webPath + "/finance/expenseListItemAdd", {
			            onLoadFunction : function() {

				            }
			        });
				};
				expenseReimbursementItem_Submit = function(){
					var chenkids = ["ExpenseReimHappenDate","ExpenseReimItemType","ExpenseReimItemMoney"];
					if(!rosten.checkData(chenkids)) return;
					
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
							store.setValue(items[0],"expenseReimburseId",registry.byId("expenseReimburseId").get("value"));
							store.setValue(items[0],"ExpenseReimHappenDate",registry.byId("ExpenseReimHappenDate").get("value"));
							store.setValue(items[0],"ExpenseReimItemType",registry.byId("ExpenseReimItemType").get("value"));
							store.setValue(items[0],"ExpenseReimItemMoney",registry.byId("ExpenseReimItemMoney").get("value"));
							store.setValue(items[0],"ExpenseReimItemPaperNum",registry.byId("ExpenseReimItemPaperNum").get("value"));
							store.setValue(items[0],"ExpenseReimItemRemark",registry.byId("ExpenseReimItemRemark").get("value"));
						}else{
							var randId = Math.random();
							var content ={
									id:randId,
									staffItemId:randId,
									rowIndex:items.length+1,
									expenseReimburseId:registry.byId("expenseReimburseId").get("value"),
									ExpenseReimHappenDate:registry.byId("ExpenseReimHappenDate").get("value"),
									ExpenseReimItemType:registry.byId("ExpenseReimItemType").get("value"),
									ExpenseReimItemMoney:registry.byId("ExpenseReimItemMoney").get("value"),
									ExpenseReimItemPaperNum:registry.byId("ExpenseReimItemPaperNum").get("value"),
									ExpenseReimItemRemark:registry.byId("ExpenseReimItemRemark").get("value"),
							};
							store.newItem(content);

						}
					}
					
					var store = expenseListGrid.getStore();
					store.fetch({
						query:{id:"*"},onComplete:gotAll,queryOptions:{deep:true}
					});
					rosten.hideRostenShowDialog();
				};
				expenseReimburseItem_formatTopic = function(value,rowIndex){
					return "<a href=\"javascript:expenseReimburseItem_onMessageOpen(" + rowIndex + ");\">" + value+ "</a>";
				};
				expenseReimburseItem_onMessageOpen = function(rowIndex){
					//打开systemCodeItem信息
			    	rosten.createRostenShowDialog(rosten.webPath + "/finance/expenseReimbursementItem", {
			            onLoadFunction : function() {
				            
			            	var itemId = rosten.getGridItemValue(staffListGrid,rowIndex,"id");
			            	var personInforId = rosten.getGridItemValue(staffListGrid,rowIndex,"personInforId");
			            	var itemName = rosten.getGridItemValue(staffListGrid,rowIndex,"getUserName");
			            	var itemDept = rosten.getGridItemValue(staffListGrid,rowIndex,"getUserDepartName");
			            	var itemResult = rosten.getGridItemValue(staffListGrid,rowIndex,"trainResult");
			            	var itemCert = rosten.getGridItemValue(staffListGrid,rowIndex,"trainCert");
			            	var itemMoney = rosten.getGridItemValue(staffListGrid,rowIndex,"userMoney");
							//var itemCert=true;

			            	registry.byId("personInforId").set("value",personInforId);
			            	registry.byId("itemId").set("value",itemId);
			            	registry.byId("itemName").set("value",itemName);
			            	registry.byId("itemDept").set("value",itemDept);
			            	registry.byId("itemResult").set("value",itemResult);
			            	
			            	if(itemCert=="是"){
			            		registry.byId("itemCert1").set("value",itemCert);
					         }else{
					        	registry.byId("itemCert2").set("value","否");
						    }
			            	
			            	
			            	registry.byId("itemMoney").set("value",itemMoney);

				        }
			        });
			    };

			    staffItem_action = function(value,rowIndex){
			    	return "<a href=\"javascript:staffItem_onDelete(" + rowIndex + ");\">" + "删除" + "</a>";
				};
				expenseReimbursementItem_onDelete = function(rowIndex){
					//删除item信息
					var store = staffListGrid.getStore();
				    var item = rosten.getGridItem(staffListGrid,rowIndex);
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
    </script>
</head>
<body>
<div class="rosten_action">
	<div data-dojo-type="rosten/widget/ActionBar" data-dojo-id="rosten_actionBar" 
		data-dojo-props='actionBarSrc:"${createLink(controller:'financeAction',action:'expenseReimbursementForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${expenseReimbursement?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"报销信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>项目名称：</div></td>
					    <td width="250">
					    	<input id="ExpenseReimbursementName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"ExpenseReimbursementName",
									value:"${expenseReimbursement?.ExpenseReimbursementName}"
			                '/>
					    </td>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>项目归属部门：</div></td>
					    <td width="250">
					    	<input id="ExpenseReimbursementBelongDept" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"ExpenseReimbursementBelongDept",
									value:"${expenseReimbursement?.ExpenseReimbursementBelongDept}"
			                '/>
					    </td>
					    </tr>
					    <tr>
						    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>费用类型：</div></td>
						    <td width="250">
						    	<select id="ExpenseReimbursementType" data-dojo-type="dijit/form/FilteringSelect" 
					                data-dojo-props='name:"ExpenseReimbursementType",${fieldAcl.isReadOnly("ExpenseReimbursementType")},
					                trim:true,required:true,missingMessage:"请选择类别！",invalidMessage:"请选择类别！",
					      			value:"${expenseReimbursement?.ExpenseReimbursementType}"
					            '>
								<option>管理费用</option>
								<option>出餐费用</option>
								<option>招待费用</option>
								<option>采购费用</option>
								<option>其它会计科目</option>
					    	</select>
				           </td>
				           
				            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>是否冲抵借款：</div></td>
                            <td width="250">
                                <input id="IsOffset1" data-dojo-type="dijit/form/RadioButton"
					           		data-dojo-props='name:"itemCert",type:"radio",
										value:"是"
				              	'/>
								<label for="IsOffset1">是</label>
				              	<input id="IsOffset2" data-dojo-type="dijit/form/RadioButton"
				           			data-dojo-props='name:"IsOffset",type:"radio",
				           			checked:true,
									value:"否"
				              	'/>
								<label for="IsOffset2">否</label>
                            </td>
				           
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>申请日期：</div></td>
					    <td width="250">
						    	<input id="ExpenseReimbursementDate" data-dojo-type="dijit/form/DateTextBox" 
				                	data-dojo-props='name:"ExpenseReimbursementDate",${fieldAcl.isReadOnly("ExpenseReimbursementDate")},
				                	trim:true,required:true,missingMessage:"请正确填写发布时间！",invalidMessage:"请正确填写发布时间！",
				                	value:"${expenseReimbursement?.getFormatteExpenseReimbursementDate()}"
				               '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>报销人：</div></td>
					    <td width="250">
					    	<input id="ExpenseReimbursementPerson" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"ExpenseReimbursementPerson",
									value:"${expenseReimbursement?.ExpenseReimbursementPerson}"
			                '/>
					    </td>
					</tr>
					
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>票面金额：</div></td>
					    <td width="250">
					    	<input id="ExpenseReimbursementBillMoney" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"ExpenseReimbursementBillMoney",
									value:"${expenseReimbursement?.ExpenseReimbursementBillMoney}"
			                '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>实报金额：</div></td>
					    <td width="250">
					    	<input id="ExpenseReimbursementMoney" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"ExpenseReimbursementMoney",
									value:"${expenseReimbursement?.ExpenseReimbursementMoney}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>票据张数：</div></td>
					    <td >
					    	<input id="ExpenseReimbursementPaperNum" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"ExpenseReimbursementPaperNum",
									value:"${expenseReimbursement?.ExpenseReimbursementPaperNum}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>备注：</div></td>
					    <td colspan=3>
					    	<textarea id="ExpenseReimbursementRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"ExpenseReimbursementRemark","class":"input",
                               		style:{width:"550px"},rows:"3",
                               		trim:true,value:"${expenseReimbursement?.ExpenseReimbursementRemark}"
                           '>
                           </textarea>
					    </td>
					</tr>
					
					</table>
					<div style="clear:both;"></div>
			</div>
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='"class":"rostenTitleGrid",title:"清单",toggleable:false,_moreClick:expenseList_addItem,moreText:"<span style=\"color:#108ac6\">增加</span>",marginBottom:"2px"'>
            	<div data-dojo-type="rosten/widget/RostenGrid" id="expenseListGrid" data-dojo-id="expenseListGrid"
					data-dojo-props='showPageControl:false,url:"${createLink(controller:'finance',action:'expenseListGrid',id:expenseReimbursement?.id)}"'></div>
             	
            </div>
            
           
		</form>
	</div>
</div>


</body>