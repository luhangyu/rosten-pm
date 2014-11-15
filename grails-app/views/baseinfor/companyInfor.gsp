<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>公司信息</title>
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
				companyInfor_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/baseinfor/companyInforSave",content,function(data){
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
		data-dojo-props='actionBarSrc:"${createLink(controller:'baseinforAction',action:'companyInforForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${companyInfor?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"公司信息",toggleable:false,moreText:"",height:"240px",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>公司名称：</div></td>
					    <td >
					    	<input id="companyName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"companyName",
									value:"${companyInfor?.companyName}"
			                '/>
					    </td>
					    <td><div align="right">公司简称：</div></td>
					    <td >
					    	<input id="companyAbbreviation" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"companyAbbreviation",trim:true,
									value:"${companyInfor?.companyAbbreviation}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">公司地址：</div></td>
					    <td colspan="3">
					    	<input id="companyAddress" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"companyAddress",trim:true,style:"width:560px",
									value:"${companyInfor?.companyAddress}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">公司电话：</div></td>
					    <td >
					    	<input id="companyPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"companyPhone",trim:true,
									value:"${companyInfor?.companyPhone}"
			                '/>
					    </td>
					    <td><div align="right">公司传真：</div></td>
					    <td >
					    	<input id="companyFax" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"companyFax",trim:true,
									value:"${companyInfor?.companyFax}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">公司邮箱：</div></td>
					    <td >
					    	<input id="companyMail" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"companyMail",trim:true,
									value:"${companyInfor?.companyMail}"
			                '/>
					    </td>
					    <td><div align="right">公司网址：</div></td>
					    <td >
					    	<input id="companyWebsite" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"companyWebsite",trim:true,
									value:"${companyInfor?.companyWebsite}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td><div align="right">备注：</div></td>
					    <td  colspan=3>
					    	<textarea id="description" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"description","class":"input",
                               		style:{width:"560px"},rows:"5",
                               		trim:true,value:"${companyInfor?.description}"
                           '>
    						</textarea>
					    </td>
					</tr>
					
				</table>
			</div>
			
		</form>
	</div>
</div>
</body>