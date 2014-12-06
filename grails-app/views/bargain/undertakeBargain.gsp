<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>承包合同信息</title>
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
				undertakeBargain_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					
					
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/bargain/undertakeBargainSave",content,function(data){
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
				showSelectDialog = function(type){
					switch(type){
					case "corpName":
						var corpName = registry.byId("undertakeBargainVendorCropName").get("value");
						rosten.selectBaseSelect("单位选择","${createLink(controller:'baseinfor',action:'getBankInforSelect',params:[companyId:company?.id])}",false,"undertakeBargainVendorCropName","undertakeBargainVendorCropId",corpName);
						break;
					}
				}
			
		});
    </script>
</head>
<body>
<div class="rosten_action">
	<div data-dojo-type="rosten/widget/ActionBar" data-dojo-id="rosten_actionBar" 
		data-dojo-props='actionBarSrc:"${createLink(controller:'bargainAction',action:'undertakeBargainForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${undertakeBargain?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"承包合同信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>合同名称：</div></td>
					    <td >
					    	<input id="undertakeBargainName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainName",
									value:"${undertakeBargain?.undertakeBargainName}"
			                '/>
					    </td>
						    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>合同类型：</div></td>
						    <td width="250">
						    	<select id="undertakeBargainType" data-dojo-type="dijit/form/FilteringSelect" 
					                data-dojo-props='name:"undertakeBargainType",${fieldAcl.isReadOnly("undertakeBargainType")},
					                trim:true,required:true,missingMessage:"请选择类别！",invalidMessage:"请选择类别！",
					      			value:"${undertakeBargain?.undertakeBargainType}"
					            '>
								<option value="承包合同">承包合同</option>
								<option value="分包合同">分包合同</option>
					    	</select>
				           </td>
					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>合同号：</div></td>
					    <td >
					    	<input id="undertakeBargainNo" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainNo",
									value:"${undertakeBargain?.undertakeBargainNo}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>合同金额：</div></td>
					    <td >
					    	<input id="undertakeBargainMoney" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainMoney",
									value:"${undertakeBargain?.undertakeBargainMoney}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>制表人：</div></td>
					    <td >
					    	<input id="undertakeBargainMaker" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainMaker",
									value:"${undertakeBargain?.undertakeBargainMaker}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>签订日期：</div></td>
					    <td>
						    <input id="undertakeBargainSignDate" data-dojo-type="dijit/form/DateTextBox" 
		               		data-dojo-props='name:"undertakeBargainSignDate",${fieldAcl.isReadOnly("undertakeBargainSignDate")},
		               		trim:true,required:true,
							value:"${undertakeBargain?.getFormatteundertakeBargainSignDate()}"
		          			'/>
			            </td>
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>付款情况：</div></td>
					    <td colspan=3>
					    	<textarea id="undertakeBargainPayMemo" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"undertakeBargainPayMemo","class":"input",
                               		style:{width:"550px"},rows:"3",
                               		trim:true,value:"${undertakeBargain?.undertakeBargainPayMemo}"
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
					    	<input id="undertakeBargainVendor" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainVendor",
									value:"${undertakeBargain?.undertakeBargainVendor}"
			                '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>甲方单位名称：</div></td>
					    <td width="250">
					    	<input id="undertakeBargainVendorCropName" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"undertakeBargainVendorCropName",
				               		trim:true,required:true,
									value:"${undertakeBargain?.undertakeBargainVendorCropName}"
				          	'/>
				          	<g:if test="${!onlyShow }">
					         	<g:hiddenField data-dojo-type="dijit/form/ValidationTextBox" name="undertakeBargainVendorCropId" value="${departId}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("corpName");	
									}'>选择</button>
			           		</g:if>
			           	</td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>法人：</div></td>
					    <td >
					    	<input id="undertakeBargainVendorBoss" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainVendorBoss",
									value:"${undertakeBargain?.undertakeBargainVendorBoss}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>法人职务：</div></td>
					    <td >
					    	<input id="undertakeBargainVendorDuty" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainVendorDuty",
									value:"${undertakeBargain?.undertakeBargainVendorDuty}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>电话：</div></td>
					    <td >
					    	<input id="undertakeBargainVendorPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainVendorPhone",
									value:"${undertakeBargain?.undertakeBargainVendorPhone}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>邮编：</div></td>
					    <td >
					    	<input id="undertakeBargainVendorPost" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainVendorPost",
									value:"${undertakeBargain?.undertakeBargainVendorPost}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>地址：</div></td>
					    <td colspan=3>
					    	<input id="undertakeBargainVendorAddress" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainVendorAddress",style:{width:"550px"},
									value:"${undertakeBargain?.undertakeBargainVendorAddress}"
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
					    	<input id="undertakeBargainPurchaser" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainPurchaser",
									value:"${undertakeBargain?.undertakeBargainPurchaser}"
			                '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>乙方单位名称：</div></td>
					    <td width="250">
					    	<input id="undertakeBargainPurchaserCropName" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"undertakeBargainPurchaserCropName",
				               		trim:true,required:true,
									value:"${undertakeBargainPurchaserCropName}"
				          	'/>
				          	<g:if test="${!onlyShow }">
					         	<g:hiddenField name="allowdepartsId" value="${departId}" />
								<button data-dojo-type="dijit.form.Button" data-dojo-props='onClick:function(){selectDepart("${createLink(controller:'system',action:'departTreeDataStore',params:[companyId:company?.id])}")}'>选择</button>
			           		</g:if>
			           	</td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>法人：</div></td>
					    <td >
					    	<input id="undertakeBargainPurchaserBoss" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainPurchaserBoss",
									value:"${undertakeBargain?.undertakeBargainPurchaserBoss}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>法人职务：</div></td>
					    <td >
					    	<input id="undertakeBargainPurchaserDuty" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainPurchaserDuty",
									value:"${undertakeBargain?.undertakeBargainPurchaserDuty}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>电话：</div></td>
					    <td >
					    	<input id="undertakeBargainPurchaserPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainPurchaserPhone",
									value:"${undertakeBargain?.undertakeBargainPurchaserPhone}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>邮编：</div></td>
					    <td >
					    	<input id="undertakeBargainPurchaserPost" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainPurchaserPost",
									value:"${undertakeBargain?.undertakeBargainPurchaserPost}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>地址：</div></td>
					    <td colspan=3>
					    	<input id="undertakeBargainPurchaserAddress" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"undertakeBargainPurchaserAddress",style:{width:"550px"},
									value:"${undertakeBargain?.undertakeBargainPurchaserAddress}"
			                '/>
					    </td>
					   
					</tr>


				</table>
				<div style="clear:both;"></div>
			</div>
			
		</form>

	</div>

</div>
</body>