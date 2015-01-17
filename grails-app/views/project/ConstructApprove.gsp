<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>项目施工方案</title>
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
				constructApprove_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/project/constructApproveSave",content,function(data){
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
		data-dojo-props='actionBarSrc:"${createLink(controller:'projectAction',action:'constructApproveForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false,tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${constructApprove?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"施工方案",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>所属项目名称：</div></td>
					    <td colspan=3>
					    	<input id="projectBelong" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectBelong",style:{width:"550px"},
									value:"${constructApprove?.projectBelong?.projName}"
			                '/>
			                <g:if test="${!onlyShow }">
					         	<g:hiddenField id="projectBelongId" data-dojo-type="dijit/form/ValidationTextBox" name="projectBelongId" value="${constructApprove?.projectBelong?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("ProjectBelong");	
									}'>选择</button>
			           		</g:if>
					    </td>
					</tr>
					<tr>
						<td width="120"><div align="right">方案名称：</div></td>
					    <td colspan=3>
					    	<input id="approveName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"approveName",style:{width:"550px"},
									value:"${constructApprove?.approveName}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td width="120"><div align="right">方案要点：</div></td>
					    <td colspan=3>
					    	<textarea id="approvePoint" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"approvePoint","class":"input",
                               		style:{width:"550px"},rows:"3",
                               		trim:true,value:"${constructApprove?.approvePoint}"
                           '>
                           </textarea>
					    </td>
					</tr>
					<tr>
					    <td width="120"><div align="right">方案备注：</div></td>
					    <td colspan=3>
					    	<textarea id="approveRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"approveRemark","class":"input",
                               		style:{width:"550px"},rows:"2",
                               		trim:true,value:"${constructApprove?.approveRemark}"
                           '>
                           </textarea>
					    </td>
					</tr>
					
					</table>
					<div style="clear:both;"></div>
			</div>
					
            <div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"附件信息",toggleable:false,moreText:"",
				href:"${createLink(controller:'share',action:'getFileUploadNew',id:constructApprove?.id,params:[uploadPath:'constructApprove',isShowFile:isShowFile])}"'>
			</div> 
		</form>

	</div>

</div>
</body>