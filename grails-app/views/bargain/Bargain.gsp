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
		     	"rosten/app/BargainApplication"],
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
					var bargainGoodsNames =["barGoodsName","barGoodsCorp","barGoodsUnit","barGoodsNum","barGoodsPrice","barGoodsDiscount","barGoodsRemark"]
					content.bargainGoodsValues = rosten.getGridDataCollect(bargainGoodsGrid,bargainGoodsNames);

					//流程相关信息
					<g:if test='${flowCode}'>
						content.flowCode = "${flowCode}";
						content.relationFlow = "${relationFlow}";
					</g:if>

					//添加新增时添加附件功能
					<g:if test="${!bargain?.id}">
						var filenode = dom.byId("fileUpload_show");
						var fileIds = [];

				       	var node=filenode.firstChild;
				       	while(node!=null){
				            node=node.nextSibling;
				            if(node!=null){
				            	fileIds.push(node.getAttribute("id"));
					        }
				        }
						content.attachmentIds = fileIds.join(",");
					</g:if>
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/bargain/bargainSave",content,function(data){
						if(data.result=="true" || data.result == true){
							rosten.alert("保存成功！").queryDlgClose= function(){
								if(window.location.href.indexOf(data.id)==-1){
									window.location.replace(window.location.href + "&id=" + data.id);
								}else{
									window.location.reload();
								}
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
				bargain_addComment = function(){
					//flowCode为是否需要走流程，如需要，则flowCode为业务流程代码
					var commentDialog = rosten.addCommentDialog({type:"bargain"});
					commentDialog.callback = function(_data){
						var content = {dataStr:_data.content,userId:"${user?.id}",status:"${bargain?.status}",flowCode:"${flowCode}"};
						rosten.readSync(rosten.webPath + "/share/addComment/${bargain?.id}",content,function(data){
							if(data.result=="true" || data.result == true){
								rosten.alert("成功！").queryDlgClose= function(){
									var selectWidget = rosten_tabContainer.selectedChildWidget;
									if(selectWidget.get("id")=="flowComment"){
										rosten_tabContainer.selectedChildWidget.refresh();
									}
								};
							}else{
								rosten.alert("失败!");
							}	
						});
					};
				};
				bargain_submit = function(object,conditionObj){
					/*
					 * 从后台获取下一处理人;conditionObj为流程中排他分支使用
					 */
					//增加对多次单击的控制
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);
					
					var content = {};

					//增加对排他分支的控制
					if(conditionObj){
						lang.mixin(content,conditionObj);
					}
					rosten.readSync("${createLink(controller:'share',action:'getSelectFlowUser',params:[userId:user?.id,taskId:bargain?.taskId,drafterUsername:bargain?.drafter?.username])}",content,function(data){
						if(data.dealFlow==false){
							//流程无下一节点
							bargain_deal("submit",null,buttonWidget,conditionObj);
							return;
						}
						var url = "${createLink(controller:'system',action:'userTreeDataStore',params:[companyId:company?.id])}";
						if(data.dealType=="user"){
							//人员处理
							if(data.showDialog==false){
								//单一处理人
								var _data = [];
								_data.push(data.userId + ":" + data.userDepart);
								bargain_deal("submit",_data,buttonWidget,conditionObj);
							}else{
								//多人，多部门处理
								url += "&type=user&user=" + data.user;
								bargain_select(url,buttonWidget,conditionObj);
							}
						}else{
							//群组处理
							url += "&type=group&groupIds=" + data.groupIds;
							if(data.limitDepart){
								url += "&limitDepart="+data.limitDepart;
							}
							bargain_select(encodeURI(url),buttonWidget,conditionObj);
						}

					},function(error){
						rosten.alert("系统错误，请通知管理员！");
						rosten.toggleAction(buttonWidget,false);
					});
				};
				bargain_select = function(url,buttonWidget,conditionObj){
					var rostenShowDialog = rosten.selectFlowUser(url,"single");
		            rostenShowDialog.callback = function(data) {
		            	if(data.length==0){
			            	rosten.alert("请正确选择人员！");
		            		rosten.toggleAction(buttonWidget,false);
			            }else{
			            	var _data = [];
			            	for (var k = 0; k < data.length; k++) {
			            		var item = data[k];
			            		_data.push(item.value + ":" + item.departId);
			            	};
			            	bargain_deal("submit",_data,buttonWidget,conditionObj);
			            }
		            };
					rostenShowDialog.afterLoad = function(){
						var _data = rostenShowDialog.getData();
			            if(_data && _data.length==1){
				            //直接调用
			            	rostenShowDialog.doAction();
				        }else{
							//显示对话框
							rostenShowDialog.open();
					    }
					};
					rostenShowDialog.queryDlgClose = function(){
						rosten.toggleAction(buttonWidget,false);
					};	
				};
				bargain_deal = function(type,readArray,buttonWidget,conditionObj){
					var content = {};
					content.id = "${bargain?.id}";
					content.deal = type;
					if(readArray){
						content.dealUser = readArray.join(",");
					}
					if(conditionObj){
						lang.mixin(content,conditionObj);
					}
					rosten.readSync(rosten.webPath + "/bargain/bargainFlowDeal",content,function(data){
						if(data.result=="true" || data.result == true){
							var ostr = "成功！";
							if(data.nextUserName && data.nextUserName!=""){
								ostr += "下一处理人<" + data.nextUserName +">";
							}
							rosten.alert(ostr).queryDlgClose= function(){
								//刷新待办事项内容
								window.opener.showStartGtask("${user?.id}","${company?.id }");
								
								if(data.refresh=="true" || data.refresh==true){
									window.location.reload();
								}else{
									rosten.pagequit();
								}
							}
						}else{
							rosten.alert("失败!");
							rosten.toggleAction(buttonWidget,false);
						}	
					},function(error){
						rosten.alert("系统错误，请通知管理员！");
						rosten.toggleAction(buttonWidget,false);
					});
				};
				bargain_back = function(object,conditionObj){
					//增加对多次单击的控制
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);
					
					var content = {};
					rosten.readSync("${createLink(controller:'bargain',action:'bargainFlowBack',params:[id:bargain?.id])}",content,function(data){
						if(data.result=="true" || data.result == true){
							rosten.alert("成功！下一处理人<" + data.nextUserName +">").queryDlgClose= function(){
								//刷新待办事项内容
								window.opener.showStartGtask("${user?.id}","${company?.id }");
								
								if(data.refresh=="true" || data.refresh==true){
									window.location.reload();
								}else{
									rosten.pagequit();
								}
							}
						}else{
							rosten.alert("失败!");
							rosten.toggleAction(buttonWidget,false);
						}
						
					},function(error){
						rosten.alert("系统错误，请通知管理员！");
						rosten.toggleAction(buttonWidget,false);
					});
				};
				page_quit = function(){
					rosten.pagequit();
				};
				showSelectDialog = function(type){
					switch(type){
					case "BargainVendorCorpName":
						var corpName = registry.byId("barVendorCorp").get("value");
						var dialog = rosten.selectBaseDialog("单位选择","${createLink(controller:'baseinfor',action:'getContactCorpSelect',params:[companyId:company?.id])}",false,"barVendorCorp","barVendorCorpId",corpName);
						dialog.callback = function(data){
							if(data.length>0){
								var dealId = data[0].id
								/*
								 * 特殊字段赋值
								 */
								dialog.getStoreDate(dealId,function(item){
									registry.byId("barVendorCorpId").attr("value", dialog.chkboxStore.getValue(item, "id"));
									registry.byId("barVendorCorp").attr("value", dialog.chkboxStore.getValue(item, "name"));
									registry.byId("j_contCorpLeader").attr("value",dialog.chkboxStore.getValue(item, "contCorpLeader"));
									registry.byId("j_cCorpLeaderDuty").attr("value", dialog.chkboxStore.getValue(item, "cCorpLeaderDuty"));
									registry.byId("j_contactCorpPhone").attr("value", dialog.chkboxStore.getValue(item, "contactCorpPhone"));
									registry.byId("j_contactCorpPost").attr("value", dialog.chkboxStore.getValue(item, "contactCorpPost"));
									registry.byId("j_contactCorpAddr").attr("value", dialog.chkboxStore.getValue(item, "contactCorpAddr"));
									
								});
								
							}else{
								registry.byId("barVendorCorp").attr("value","");
								registry.byId("barVendorCorpId").attr("value", "");
								registry.byId("j_contCorpLeader").attr("value", "");
								registry.byId("j_cCorpLeaderDuty").attr("value", "");
								registry.byId("j_contactCorpPhone").attr("value", "");
								registry.byId("j_contactCorpPost").attr("value", "");
								registry.byId("j_contactCorpAddr").attr("value", "");
							}
						};
						break;

					case "BargainPurchaserCorpName":
						var corpName = registry.byId("barPurchaserCorp").get("value");
						var dialog = rosten.selectBaseDialog("单位选择","${createLink(controller:'baseinfor',action:'getContactCorpSelect',params:[companyId:company?.id])}",false,"barPurchaserCorp","barPurchaserCorpId",corpName);
						dialog.callback = function(data){
							if(data.length>0){
								var dealId = data[0].id
								/*
								 * 特殊字段赋值
								 */
								dialog.getStoreDate(dealId,function(item){
									registry.byId("barPurchaserCorpId").attr("value", dialog.chkboxStore.getValue(item, "id"));
									registry.byId("barPurchaserCorp").attr("value", dialog.chkboxStore.getValue(item, "name"));
									registry.byId("y_contCorpLeader").attr("value",dialog.chkboxStore.getValue(item, "contCorpLeader"));
									registry.byId("y_cCorpLeaderDuty").attr("value", dialog.chkboxStore.getValue(item, "cCorpLeaderDuty"));
									registry.byId("y_contactCorpPhone").attr("value", dialog.chkboxStore.getValue(item, "contactCorpPhone"));
									registry.byId("y_contactCorpPost").attr("value", dialog.chkboxStore.getValue(item, "contactCorpPost"));
									registry.byId("y_contactCorpAddr").attr("value", dialog.chkboxStore.getValue(item, "contactCorpAddr"));
									
								});
								
							}else{
								registry.byId("barPurchaserCorp").attr("value","");
								registry.byId("barPurchaserCorpId").attr("value", "");
								registry.byId("y_contCorpLeader").attr("value", "");
								registry.byId("y_cCorpLeaderDuty").attr("value", "");
								registry.byId("y_contactCorpPhone").attr("value", "");
								registry.byId("y_contactCorpPost").attr("value", "");
								registry.byId("y_contactCorpAddr").attr("value", "");
							}
						};

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

<div data-dojo-id="rosten_tabContainer" data-dojo-type="dijit/layout/TabContainer" data-dojo-props='doLayout:false,persist:false,tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props='doLayout:false'>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${bargain?.id}"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id}"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"合同信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0"align="left">
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>合同名称：</div></td>
					    <td colspan=3>
					    	<input id="bargainName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainName",style:{width:"550px"},${fieldAcl.isReadOnly("bargainName")},
									value:"${bargain?.bargainName}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>合同类型：</div></td>
					    <td  width="250">
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
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>合同状态：</div></td>
					    <td width="250">
					    	<input id="status" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='disabled:true,trim:true,required:true,name:"status",
									value:"${bargain?.status}"
			                '/>
			           </td>
					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>合同号：</div></td>
					    <td >
					    	<input id="bargainNo" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainNo",${fieldAcl.isReadOnly("bargainNo")},
									value:"${bargain?.bargainNo}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>合同金额：</div></td>
					    <td >
					    	<input id="bargainMoney" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainMoney",${fieldAcl.isReadOnly("bargainMoney")},
									value:"${bargain?.bargainMoney}"
			                '/>&nbsp;元
					    </td>
					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>制表人：</div></td>
					    <td >
					    	<input id="bargainMaker" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainMaker",${fieldAcl.isReadOnly("bargainMaker")},
									value:"${bargain?.bargainMaker}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>签订日期：</div></td>
					    <td>
						    <input id="bargainSignDate" data-dojo-type="dijit/form/DateTextBox" 
		               		data-dojo-props='name:"bargainSignDate",${fieldAcl.isReadOnly("bargainSignDate")},
		               		trim:true,required:true,
							value:"${bargain?.getFormatteBargainSignDate()}"
		          			'/>
			            </td>
					</tr>
					<tr>
					    <td width="120"><div align="right">付款情况：</div></td>
					    <td colspan=3>
					    	<textarea id="bargainPayMemo" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"bargainPayMemo","class":"input",
                               		style:{width:"550px"},rows:"2",${fieldAcl.isReadOnly("bargainPayMemo")},
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
			                 	data-dojo-props='trim:true,required:true,name:"bargainVendor",${fieldAcl.isReadOnly("bargainVendor")},
									value:"${bargain?.bargainVendor}"
			                '/>
					    </td>
						<td width="120"><div align="right">甲方单位名称：</div></td>
					    <td width="250">
					    	<input id="barVendorCorp" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='trim:true,readOnly:true,
									value:"${bargain?.barVendorCorp?.contactCorpName}"
				          	'/>
				          	 <g:if test="${isShowFile }">
					         	<g:hiddenField id="barVendorCorpId" data-dojo-type="dijit/form/ValidationTextBox" name="barVendorCorpId" value="${bargain?.barVendorCorp?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("BargainVendorCorpName");	
									}'>选择</button>
			           		</g:if>
			           	</td>
					</tr>
										<tr>
						<td ><div align="right">法人：</div></td>
					    <td >
					    	<input id="j_contCorpLeader" data-dojo-type="dijit/form/ValidationTextBox" data-dojo-props='trim:true,
					    			placeHolder:"系统自动赋值",
									value:"${bargain?.barVendorCorp?.contCorpLeader}"
			                '/>
					    </td>
					    <td ><div align="right">法人职务：</div></td>
					    <td >
					    	<input id="j_cCorpLeaderDuty" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",
									value:"${bargain?.barVendorCorp?.cCorpLeaderDuty}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right">电话：</div></td>
					    <td >
					    	<input  id="j_contactCorpPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",
									value:"${bargain?.barVendorCorp?.contactCorpPhone}"
			                '/>
					    </td>
					    <td ><div align="right">邮编：</div></td>
					    <td >
					    	<input  id="j_contactCorpPost" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",
									value:"${bargain?.barVendorCorp?.contactCorpPost}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right">地址：</div></td>
					 	 <td colspan=3>
					    	<input  id="j_contactCorpAddr" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",style:{width:"550px"},
									value:"${bargain?.barVendorCorp?.contactCorpAddr}"
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
			                 	data-dojo-props='trim:true,required:true,name:"bargainPurchaser",${fieldAcl.isReadOnly("bargainPurchaser")},
									value:"${bargain?.bargainPurchaser}"
			                '/>
					    </td>
						<td width="120"><div align="right">乙方单位名称：</div></td>
					    <td width="250">
					    	<input id="barPurchaserCorp" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='trim:true,readOnly:true,
									value:"${bargain?.barPurchaserCorp?.contactCorpName}"
				          	'/>
				          <g:if test="${isShowFile }">
					         	<g:hiddenField id="barPurchaserCorpId" data-dojo-type="dijit/form/ValidationTextBox" name="barPurchaserCorpId" value="${Bargain?.barPurchaserCorp?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("BargainPurchaserCorpName");	
									}'>选择</button>
			           		</g:if>
			           	</td>
					</tr>
					<tr>
						<td ><div align="right">法人：</div></td>
					    <td >
					    	<input id="y_contCorpLeader"  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",
									value:"${bargain?.barPurchaserCorp?.contCorpLeader}"
			                '/>
					    </td>
					    <td ><div align="right">法人职务：</div></td>
					    <td >
					    	<input  id="y_cCorpLeaderDuty" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",
									value:"${bargain?.barPurchaserCorp?.cCorpLeaderDuty}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right">电话：</div></td>
					    <td >
					    	<input id="y_contactCorpPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",
									value:"${bargain?.barPurchaserCorp?.contactCorpPhone}"
			                '/>
					    </td>
					    <td ><div align="right">邮编：</div></td>
					    <td >
					    	<input id="y_contactCorpPost"  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",
									value:"${bargain?.barPurchaserCorp?.contactCorpPost}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right">地址：</div></td>
					    <td colspan=3>
					    	<input id="y_contactCorpAddr" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",style:{width:"550px"},
									value:"${bargain?.barPurchaserCorp?.contactCorpAddr}"
			                '/>
					    </td>
					   
					</tr>


				</table>
				<div style="clear:both;"></div>
			</div>
		
		
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-id="bargainGoodsTitlePane" 
				data-dojo-props='"class":"rostenTitleGrid",<g:if test="${bargain?.bargainType!="采购合同"}">style:{display:"none"},</g:if>title:"采购货物明细",toggleable:false,_moreClick:bargainGoods_addItem,moreText:"<span style=\"color:#108ac6\">增加</span>",marginBottom:"2px"'>
            	<div data-dojo-type="rosten/widget/RostenGrid" id="bargainGoodsGrid" data-dojo-id="bargainGoodsGrid"
					data-dojo-props='imgSrc:"${resource(dir:'images/rosten/share',file:'wait.gif')}",showPageControl:false,url:"${createLink(controller:'bargain',action:'bargainGoodsGrid',id:bargain?.id)}"'></div>             	
            	
            </div>
            
           <div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"附件信息",toggleable:false,moreText:"",
				href:"${createLink(controller:'share',action:'getFileUploadNew',id:bargain?.id,params:[uploadPath:'bargain',isShowFile:isShowFile])}"'>
			</div> 
		</form>
	</div>
	<g:if test="${bargain?.id}">
		<div data-dojo-type="dijit/layout/ContentPane" id="flowComment" title="流转意见" data-dojo-props='refreshOnShow:true,style:{width:"740px"},
			href:"${createLink(controller:'share',action:'getCommentLog',id:bargain?.id)}"
		'>	
		</div>
		<div data-dojo-type="dijit/layout/ContentPane" id="flowLog" title="流程跟踪" data-dojo-props='refreshOnShow:true,
			href:"${createLink(controller:'share',action:'getFlowLog',id:bargain?.id,params:[processDefinitionId:bargain?.processDefinitionId,taskId:bargain?.taskId])}"
		'>	
		</div>
	</g:if>
</div>
</body>
