<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>银行账户信息</title>
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
				bankInfor_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}

					//是否默认银行账户字段
					var accountIsDefVal
					if (registry.byId("is1").get("value")){
						accountIsDefVal=registry.byId("is1").get("value");
				 	}else{
				 		accountIsDefVal=registry.byId("is2").get("value");
					}

					
					var content = {};
					content.accountIsDef=accountIsDefVal;
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/baseinfor/bankInforSave",content,function(data){
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
		data-dojo-props='actionBarSrc:"${createLink(controller:'baseinforAction',action:'bankInforForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${bankInfor?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"账户信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>账号名称：</div></td>
					    <td >
					    	<input id="accountName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"accountName",
									value:"${bankInfor?.accountName}"
			                '/>
					    </td>
					    <td><div align="right">开户行：</div></td>
					    <td >
					    	<input id="accountBank" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"accountBank",trim:true,
									value:"${bankInfor?.accountBank}"
			                '/>
					    </td>
					</tr>
					
					<tr>
					    <td><div align="right">户主：</div></td>
					    <td >
					    	<input id="accountMaster" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"accountMaster",trim:true,
									value:"${bankInfor?.accountMaster}"
			                '/>
					    </td>
					   			 
			                <td><div align="right">默认账户：</div></td>
							<td>
		                        	<input id="is1" data-dojo-type="dijit/form/RadioButton"
	                             		data-dojo-props='name:"accountIsDef",
	                             			type:"radio",
	                             			${fieldAcl.isReadOnly("accountIsDef")},
	                             			<g:if test="${bankInfor?.accountIsDef || bankInfor?.accountIsDef==true }">checked:true,</g:if>
	              							value:"true"
	                                '/>
									<label for="is1">是</label>
									
	                                <input id="is2" data-dojo-type="dijit/form/RadioButton"
	                             		data-dojo-props='name:"accountIsDef",
	                             			type:"radio",
	                             			${fieldAcl.isReadOnly("accountIsDef")},
	                             			<g:if test="${!bankInfor?.accountIsDef && bankInfor?.accountIsDef==false }">checked:true,</g:if>
	              							value:"false"
	                                '/>
									<label for="is2">否</label>									
		                 	</td>
					   
					</tr>

					    <td><div align="right">备注：</div></td>
					    <td  colspan=3>
					    	<textarea id="accountRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"accountRemark","class":"input",
                               		style:{width:"560px"},rows:"3",
                               		trim:true,value:"${bankInfor?.accountRemark}"
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