<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>往来单位信息</title>
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
				contactCorp_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/baseinfor/contactCorpSave",content,function(data){
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
		data-dojo-props='actionBarSrc:"${createLink(controller:'baseinforAction',action:'contactCorpForm',id:contactCorp?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${contactCorp?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"往来单位信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>单位名称：</div></td>
					    <td colspan=3>
					    	<input id="contactCorpName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"contactCorpName",style:{width:"550px"},
									value:"${contactCorp?.contactCorpName}"
			                '/>
					    </td>

					   
					</tr>
					<tr>
					 	  <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>类型：</div></td>
						 <td width="250">
						    	<select id="contactCorpType" data-dojo-type="dijit/form/FilteringSelect" 
					                data-dojo-props='name:"contactCorpType",${fieldAcl.isReadOnly("contactCorpType")},
					                trim:true,required:true,missingMessage:"请选择类别！",invalidMessage:"请选择类别！",
					      			value:"${contactCorp?.contactCorpType}"
					            '>
								<g:each in="${contactCropTypeList}" var="item">
				                	<option value="${item.name}">${item.name}</option>
				                </g:each>
					    	</select>
				           </td>
				        <td width="120"><div align="right">法人：</div></td>
					    <td width="250">
					    	<input id="contCorpLeader" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contCorpLeader",
									value:"${contactCorp?.contCorpLeader}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td><div align="right">营业执照：</div></td>
					    <td >
					    	<input id="contCorpLicense" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contCorpLicense",
									value:"${contactCorp?.contCorpLicense}"
			                '/>
					    </td>
					 	<td ><div align="right">税务登记号：</div></td>
					    <td >
					    	<input id="contactCorpTax" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contactCorpTax",
									value:"${contactCorp?.contactCorpTax}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td><div align="right">区域：</div></td>
					    <td >
					    	<input id="contactCorpProv" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contactCorpProv",
									value:"${contactCorp?.contactCorpProv}"
			                '/>
					    </td>
					 	
					</tr>
					<tr>
					<td><div align="right">地址：</div></td>
					    <td colspan=3>
					    	<input id="contactCorpAddr" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contactCorpAddr",style:{width:"550px"},
									value:"${contactCorp?.contactCorpAddr}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td><div align="right">联系人：</div></td>
					    <td >
					    	<input id="contCorpLinkMan" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contCorpLinkMan",
									value:"${contactCorp?.contCorpLinkMan}"
			                '/>
					    </td>
					 	
					</tr>
					<tr>
						<td><div align="right">联系电话：</div></td>
					    <td >
					    	<input id="contactCorpPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contactCorpPhone",
									value:"${contactCorp?.contactCorpPhone}"
			                '/>
					    </td>
					    <td><div align="right">传真：</div></td>
					    <td >
					    	<input id="contactCorpFax" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contactCorpFax",
									value:"${contactCorp?.contactCorpFax}"
			                '/>
					    </td>
					 	
					</tr>
					<tr>

					    <td><div align="right">网址：</div></td>
					    <td >
					    	<input id="contCorpWebSite" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contCorpWebSite",
									value:"${contactCorp?.contCorpWebSite}"
			                '/>
					    </td>
					 	<td><div align="right">电子邮箱：</div></td>
					    <td >
					    	<input id="contCorpEMail" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"contCorpEMail",
									value:"${contactCorp?.contCorpEMail}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td><div align="right">备注：</div></td>
					    <td  colspan=3>
					    	<textarea id="contCorpRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"contCorpRemark","class":"input",
                               		style:{width:"550px"},rows:"2",
                               		trim:true,value:"${contactCorp?.contCorpRemark}"
                           '>
    						</textarea>
					    </td>
					</tr>
				</table>
				<div style="clear:both;"></div>
			</div>
			
		</form>
	</div>
</div>
</body>