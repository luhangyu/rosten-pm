<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>合同基本信息</title>
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
				Bargain_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					
					
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/bargain/BargainSave",content,function(data){
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
						var corpName = registry.byId("BargainVendorCropName").get("value");
						rosten.selectBaseSelect("单位选择","${createLink(controller:'baseinfor',action:'getBankInforSelect',params:[companyId:company?.id])}",false,"BargainVendorCropName","BargainVendorCropId",corpName);
						break;
					}
				}
			
		});
    </script>
</head>
<body>
<div class="rosten_action">
	<div data-dojo-type="rosten/widget/ActionBar" data-dojo-id="rosten_actionBar" 
		data-dojo-props='actionBarSrc:"${createLink(controller:'bargainAction',action:'BargainForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${Bargain?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"承包合同信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>合同名称：</div></td>
					    <td >
					    	<input id="BargainName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainName",
									value:"${Bargain?.BargainName}"
			                '/>
					    </td>
						    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>合同类型：</div></td>
						    <td width="250">
						    	<select id="BargainType" data-dojo-type="dijit/form/FilteringSelect" 
					                data-dojo-props='name:"BargainType",${fieldAcl.isReadOnly("BargainType")},
					                trim:true,required:true,missingMessage:"请选择类别！",invalidMessage:"请选择类别！",
					      			value:"${Bargain?.BargainType}"
					            '>
								<option value="承包合同">承包合同</option>
								<option value="分包合同">分包合同</option>
					    	</select>
				           </td>
					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>合同号：</div></td>
					    <td >
					    	<input id="BargainNo" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainNo",
									value:"${Bargain?.BargainNo}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>合同金额：</div></td>
					    <td >
					    	<input id="BargainMoney" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainMoney",
									value:"${Bargain?.BargainMoney}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>制表人：</div></td>
					    <td >
					    	<input id="BargainMaker" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainMaker",
									value:"${Bargain?.BargainMaker}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>签订日期：</div></td>
					    <td>
						    <input id="BargainSignDate" data-dojo-type="dijit/form/DateTextBox" 
		               		data-dojo-props='name:"BargainSignDate",${fieldAcl.isReadOnly("BargainSignDate")},
		               		trim:true,required:true,
							value:"${Bargain?.getFormatteBargainSignDate()}"
		          			'/>
			            </td>
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>付款情况：</div></td>
					    <td colspan=3>
					    	<textarea id="BargainPayMemo" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"BargainPayMemo","class":"input",
                               		style:{width:"550px"},rows:"3",
                               		trim:true,value:"${Bargain?.BargainPayMemo}"
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
					    	<input id="BargainVendor" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainVendor",
									value:"${Bargain?.BargainVendor}"
			                '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>甲方单位名称：</div></td>
					    <td width="250">
					    	<input id="BargainVendorCropName" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"BargainVendorCropName",
				               		trim:true,required:true,
									value:"${Bargain?.BargainVendorCropName}"
				          	'/>
				          	<g:if test="${!onlyShow }">
					         	<g:hiddenField data-dojo-type="dijit/form/ValidationTextBox" name="BargainVendorCropId" value="${departId}" />
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
					    	<input id="BargainVendorBoss" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainVendorBoss",
									value:"${Bargain?.BargainVendorBoss}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>法人职务：</div></td>
					    <td >
					    	<input id="BargainVendorDuty" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainVendorDuty",
									value:"${Bargain?.BargainVendorDuty}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>电话：</div></td>
					    <td >
					    	<input id="BargainVendorPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainVendorPhone",
									value:"${Bargain?.BargainVendorPhone}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>邮编：</div></td>
					    <td >
					    	<input id="BargainVendorPost" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainVendorPost",
									value:"${Bargain?.BargainVendorPost}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>地址：</div></td>
					    <td colspan=3>
					    	<input id="BargainVendorAddress" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainVendorAddress",style:{width:"550px"},
									value:"${Bargain?.BargainVendorAddress}"
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
					    	<input id="BargainPurchaser" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainPurchaser",
									value:"${Bargain?.BargainPurchaser}"
			                '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>乙方单位名称：</div></td>
					    <td width="250">
					    	<input id="BargainPurchaserCropName" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"BargainPurchaserCropName",
				               		trim:true,required:true,
									value:"${BargainPurchaserCropName}"
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
					    	<input id="BargainPurchaserBoss" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainPurchaserBoss",
									value:"${Bargain?.BargainPurchaserBoss}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>法人职务：</div></td>
					    <td >
					    	<input id="BargainPurchaserDuty" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainPurchaserDuty",
									value:"${Bargain?.BargainPurchaserDuty}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>电话：</div></td>
					    <td >
					    	<input id="BargainPurchaserPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainPurchaserPhone",
									value:"${Bargain?.BargainPurchaserPhone}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>邮编：</div></td>
					    <td >
					    	<input id="BargainPurchaserPost" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainPurchaserPost",
									value:"${Bargain?.BargainPurchaserPost}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>地址：</div></td>
					    <td colspan=3>
					    	<input id="BargainPurchaserAddress" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"BargainPurchaserAddress",style:{width:"550px"},
									value:"${Bargain?.BargainPurchaserAddress}"
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
