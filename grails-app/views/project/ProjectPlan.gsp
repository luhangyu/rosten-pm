<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>项目计划</title>
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
		     	"rosten/app/Application",
		     	"rosten/kernel/behavior",
		     	"rosten/app/ProjectApplication"],
			function(parser,kernel,registry,dom,lang){
				kernel.addOnLoad(function(){
					rosten.init({webpath:"${request.getContextPath()}",dojogridcss : true});
					rosten.cssinit();
				});
				projectPlan_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					var constructLogs =["getConstructPart","getFormatteConstructDate","consDoneQutt","consDoneRate","logMaker","conslogRemark"]
					content.constructLogsValues = rosten.getGridDataCollect(constructLogGrid,constructLogs);
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/project/projectPlanSave",content,function(data){
						if(data.result=="true" || data.result == true){
							rosten.alert("保存成功！").queryDlgClose= function(){
								//page_quit();
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
				page_quit = function(){
					rosten.pagequit();
				};


				showSelectDialog = function(type){
					switch(type){
					//所属项目
					case "ProjectBelong":
						var projName = registry.byId("projectBelong").get("value");
						var dialog = rosten.selectBaseDialog("项目选择","${createLink(controller:'project',action:'getProjectSelect',params:[companyId:company?.id])}",false,"projectBelong","projectBelongId",projName);
						dialog.callback = function(data){
							if(data.length>0){
								var dealId = data[0].id
								/*
								 * 特殊字段赋值
								 */
								dialog.getStoreDate(dealId,function(item){
									registry.byId("projectBelongId").attr("value", dialog.chkboxStore.getValue(item, "id"));
									registry.byId("projectBelong").attr("value", dialog.chkboxStore.getValue(item, "name"));
								});
								
							}else{
								registry.byId("projectBelong").attr("value","");
								registry.byId("projectBelongId").attr("value", "");
								
							}
						};

						break;	
						
					case "constCorp":
						var corpName = registry.byId("constCorp").get("value");
						var dialog = rosten.selectBaseDialog("单位选择","${createLink(controller:'baseinfor',action:'getContactCorpSelect',params:[companyId:company?.id])}",false,"supCorp","supCorpId",corpName);
						dialog.callback = function(data){
							if(data.length>0){
								var dealId = data[0].id
								/*
								 * 特殊字段赋值
								 */
								dialog.getStoreDate(dealId,function(item){
									registry.byId("constCorpId").attr("value", dialog.chkboxStore.getValue(item, "id"));
									registry.byId("constCorp").attr("value", dialog.chkboxStore.getValue(item, "name"));
									registry.byId("j_constCorpRer").attr("value",dialog.chkboxStore.getValue(item, "contCorpLeader"));
									registry.byId("j_constCorpPhone").attr("value", dialog.chkboxStore.getValue(item, "contactCorpPhone"));
								});
							}else{
								registry.byId("constCorp").attr("value","");
								registry.byId("constCorpId").attr("value", "");
								registry.byId("j_constCorpRer").attr("value", "");
								registry.byId("j_constCorpPhone").attr("value", "");
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
		data-dojo-props='actionBarSrc:"${createLink(controller:'projectAction',action:'projectPlanForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='doLayout:false,persist:false,tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props='doLayout:false,'>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${projectPlan?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"项目计划",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>所属项目名称：</div></td>
					    <td colspan=3>
					    	<input id="projectBelong" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectBelong",style:{width:"550px"},
									value:"${projectPlan?.projectBelong?.projName}"
			                '/>
			                <g:if test="${!onlyShow }">
					         	<g:hiddenField id="projectBelongId" data-dojo-type="dijit/form/ValidationTextBox" name="projectBelongId" value="${projectPlan?.projectBelong?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("ProjectBelong");	
									}'>选择</button>
			           		</g:if>
					    </td>
					</tr>
					<tr>
						<td width="120"><div align="right">部位：</div></td>
					    <td width="250">
					    	<input id="constructPart" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"constructPart",
									value:"${projectPlan?.constructPart}"
			                '/>
					    </td>
					</tr>
					<tr>

					    <td ><div align="right"><span style="color:red">*&nbsp;</span>工作量：</div></td>
					    <td >
					    	<input id="constructQutt" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"constructQutt",
									value:"${projectPlan?.constructQutt}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>工期：</div></td>
					    <td >
					    	<input id="constructDays" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"constructDays",
									value:"${projectPlan?.constructDays}"
			                '/>
					    </td>
					</tr>
					<tr>
					   <td><div align="right"><span style="color:red">*&nbsp;</span>计划开工日期：</div></td>
					    <td>
					    	<input id="consPlanDate" data-dojo-type="dijit/form/DateTextBox" 
			                	data-dojo-props='name:"consPlanDate",
			                	trim:true,required:true,missingMessage:"请正确填写计划开工日期！",invalidMessage:"请正确填写计划开工日期！",
			                	value:"${projectPlan?.getFormatteConsPlanDate()}"
			               '/>
			            </td>
			               <td><div align="right"><span style="color:red">*&nbsp;</span>实际开工日期：</div></td>
					    <td>
					    	<input id="consTrueDate" data-dojo-type="dijit/form/DateTextBox" 
			                	data-dojo-props='name:"consTrueDate",
			                	trim:true,required:true,missingMessage:"请正确填写实际开工日期！",invalidMessage:"请正确填写实际开工日期！",
			                	value:"${projectPlan?.getFormatteConsTrueDate()}"
			               '/>
			            </td>
					
					</tr>

					
					</table>
					<div style="clear:both;"></div>
			</div>
					

			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-id="constructLogTitlePane" 
				data-dojo-props='"class":"rostenTitleGrid",title:"施工日志",toggleable:false,_moreClick:constructLog_addItem,moreText:"<span style=\"color:#108ac6\">增加</span>",marginBottom:"2px"'>
            	<div data-dojo-type="rosten/widget/RostenGrid" id="constructLogGrid" data-dojo-id="constructLogGrid"
					data-dojo-props='imgSrc:"${resource(dir:'images/rosten/share',file:'wait.gif')}",showPageControl:false,url:"${createLink(controller:'project',action:'constructLogGrid',id:projectPlan?.id)}"'></div>             	           	
            </div>
		</form>

	</div>

</div>
</body>