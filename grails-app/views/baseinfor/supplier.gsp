<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>供应商</title>
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
				supplier_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/baseinfor/supplierSave",content,function(data){
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

			
		});
    </script>
</head>
<body>
<div class="rosten_action">
	<div data-dojo-type="rosten/widget/ActionBar" data-dojo-id="rosten_actionBar" 
		data-dojo-props='actionBarSrc:"${createLink(controller:'baseinforAction',action:'supplierForm',id:supplier?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${supplier?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"材料信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td width="120px"><div align="right"><span style="color:red">*&nbsp;</span>供应商名称：</div></td>
					   <td width="250px">
					    	<input id="supplierName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"supplierName",
									value:"${supplier?.supplierName}"
			                '/>
					    </td>
					    <td width="120px"><div align="right">税务登记号：</div></td>
					    <td width="250px">
					    	<input id="supplierTax" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierTax",trim:true,
									value:"${supplier?.supplierTax}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">法人：</div></td>
					    <td>
					    	<input id="supplierLealPerson" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierLealPerson",trim:true,
									value:"${supplier?.supplierLealPerson}"
			                '/>
					    </td>
					     <td><div align="right">法人职务：</div></td>
					    <td>
					    	<input id="supplierLealPersonDuty" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierLealPersonDuty",trim:true,
									value:"${supplier?.supplierLealPersonDuty}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">联系电话：</div></td>
					    <td >
					    	<input id="supplierPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierPhone",trim:true,
									value:"${supplier?.supplierPhone}"
			                '/>
					    </td>
					    <td><div align="right">传真：</div></td>
					    <td >
					    	<input id="supplierFax" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierFax",trim:true,
									value:"${supplier?.supplierFax}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">地址：</div></td>
					    <td colspan="3">
					    	<input id="supplierAddress" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierAddress",trim:true,style:"width:550px",
									value:"${supplier?.supplierAddress}"
			                '/>
					    </td>
					</tr>
						<tr>
					     <td><div align="right">邮编：</div></td>
					    <td >
					    	<input id="supplierPost" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierPost",trim:true,
									value:"${supplier?.supplierPost}"
			                '/>
					    </td>
					</tr>
						<tr>
					    <td><div align="right">网址：</div></td>
					    <td >
					    	<input id="supplierWebSite" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierWebSite",trim:true,
									value:"${supplier?.supplierWebSite}"
			                '/>
					    </td>
					    <td><div align="right">电子邮箱：</div></td>
					    <td >
					    	<input id="supplierEMail" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierEMail",trim:true,
									value:"${supplier?.supplierEMail}"
			                '/>
					    </td>
					</tr>
						<tr>
					    <td><div align="right">供应商评分：</div></td>
					    <td >
					    	<input id="supplierGrade" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierGrade",trim:true,
									value:"${supplier?.supplierGrade}"
			                '/>
					    </td>
					    <td><div align="right">助记码：</div></td>
					    <td >
					    	<input id="supplierCode" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierCode",trim:true,
									value:"${supplier?.supplierCode}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td><div align="right">备注：</div></td>
					    <td  colspan=3>
					    	<textarea id="supplierRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"supplierRemark","class":"input",
                               		style:{width:"550px"},rows:"2",
                               		trim:true,value:"${supplier?.supplierRemark}"
                           '>
    						</textarea>
					    </td>
					</tr>
					
				</table>
				<div style="clear:both;"></div>
			</div>
			
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"汇款账号信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td width="120px"><div align="right"><span style="color:red">*&nbsp;</span>户主：</div></td>
					   <td width="250px">
					    	<input id="supplierBankAccountMaster" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"supplierBankAccountMaster",
									value:"${supplier?.supplierBankAccountMaster}"
			                '/>
					    </td>
					    <td width="120px"><div align="right">开户行：</div></td>
					    <td width="250px">
					    	<input id="supplierBankAccountBank" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"supplierBankAccountBank",trim:true,
									value:"${supplier?.supplierBankAccountBank}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td width="120px"><div align="right"><span style="color:red">*&nbsp;</span>账号：</div></td>
					   <td width="250px">
					    	<input id="supplierBankAccountNo" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"supplierBankAccountNo",
									value:"${supplier?.supplierBankAccountNo}"
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