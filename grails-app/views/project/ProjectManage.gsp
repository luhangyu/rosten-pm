<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>项目信息</title>
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
				projectManage_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/project/projectManageSave",content,function(data){
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
		data-dojo-props='actionBarSrc:"${createLink(controller:'projectAction',action:'projectManageForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${projectManage?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"项目信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>项目名称：</div></td>
					    <td width="250">
					    	<input id="projName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projName",
									value:"${projectManage?.projName}"
			                '/>
					    </td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>项目编号：</div></td>
					    <td width="250">
					    	<input id="projectNo" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectNo",
									value:"${projectManage?.projectNo}"
			                '/>
					    </td>
					</tr>
					
					<tr>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>建设单位：</div></td>
					    <td width="250">
					    	<input id="constructionCorp" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"constructionCorp",
				               		trim:true,required:true,
									value:"${constructionCorp}"
				          	'/>
				          	<g:if test="${!onlyShow }">
					         	<g:hiddenField name="allowdepartsId" value="${departId}" />
								<button data-dojo-type="dijit.form.Button" data-dojo-props='onClick:function(){selectDepart("${createLink(controller:'system',action:'departTreeDataStore',params:[companyId:company?.id])}")}'>选择</button>
			           		</g:if>
			           	</td>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>监理单位：</div></td>
					    <td width="250">
					    	<input id="supervisorCorp" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"supervisorCorp",
				               		trim:true,required:true,
									value:"${supervisorCorp}"
				          	'/>
				          	<g:if test="${!onlyShow }">
					         	<g:hiddenField name="allowdepartsId" value="${departId}" />
								<button data-dojo-type="dijit.form.Button" data-dojo-props='onClick:function(){selectDepart("${createLink(controller:'system',action:'departTreeDataStore',params:[companyId:company?.id])}")}'>选择</button>
			           		</g:if>
			           	</td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>建设方代表：</div></td>
					    <td >
					    	<input id="constructionCorpRepresentative" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"constructionCorpRepresentative",
									value:"${projectManage?.constructionCorpRepresentative}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>监理方代表：</div></td>
					    <td >
					    	<input id="supervisorCorpRepresentative" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"supervisorCorpRepresentative",
									value:"${projectManage?.supervisorCorpRepresentative}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>建设方联系电话：</div></td>
					    <td >
					    	<input id="constructionCorpPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"constructionCorpPhone",
									value:"${projectManage?.constructionCorpPhone}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>监理方联系电话：</div></td>
					    <td >
					    	<input id="supervisorCorpPhone" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"supervisorCorpPhone",
									value:"${projectManage?.supervisorCorpPhone}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>项目经理：</div></td>
					    <td width="250">
					    	<input id="projectManager" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectManager",
									value:"${projectManage?.projectManager}"
			                '/>
					    </td>
						 <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>副经理：</div></td>
					    <td width="250">
					    	<input id="projectAssistantManager" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectAssistantManager",
									value:"${projectManage?.projectAssistantManager}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>工地名称：</div></td>
					    <td >
					    	<input id="projectWorkPlace" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectWorkPlace",
									value:"${projectManage?.projectWorkPlace}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>工地地址：</div></td>
					    <td >
					    	<input id="projectWorkPlaceAddress" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectWorkPlaceAddress",
									value:"${projectManage?.projectWorkPlaceAddress}"
			                '/>
					    </td>
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>结构：</div></td>
					    <td >
					    	<input id="projectConstruction" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectConstruction",
									value:"${projectManage?.projectConstruction}"
			                '/>
					    </td>
					    <td ><div align="right"><span style="color:red">*&nbsp;</span>工作量：</div></td>
					    <td >
					    	<input id="projectWorkload" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectWorkload",
									value:"${projectManage?.projectWorkload}"
			                '/>
					    </td>
					</tr>
					<tr>
					   <td><div align="right"><span style="color:red">*&nbsp;</span>开工日期：</div></td>
					    <td>
					    	<input id="projectStartDate" data-dojo-type="dijit/form/DateTextBox" 
			                	data-dojo-props='name:"projectStartDate",${fieldAcl.isReadOnly("projectStartDate")},
			                	trim:true,required:true,missingMessage:"请正确填写考勤时间！",invalidMessage:"请正确填写考勤时间！",
			                	value:"${projectManage?.getFormatteprojectStartDate()}"
			               '/>
			            </td>
			               <td><div align="right"><span style="color:red">*&nbsp;</span>计划竣工日期：</div></td>
					    <td>
					    	<input id="projectEndDate" data-dojo-type="dijit/form/DateTextBox" 
			                	data-dojo-props='name:"projectEndDate",${fieldAcl.isReadOnly("projectEndDate")},
			                	trim:true,required:true,missingMessage:"请正确填写考勤时间！",invalidMessage:"请正确填写考勤时间！",
			                	value:"${projectManage?.getFormatteprojectEndDate()}"
			               '/>
			            </td>
					
					</tr>
					<tr>
						<td ><div align="right"><span style="color:red">*&nbsp;</span>材料预算金额：</div></td>
					    <td >
					    	<input id="projectMaterialMoney" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"projectMaterialMoney",
									value:"${projectManage?.projectMaterialMoney}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>备注：</div></td>
					    <td colspan=3>
					    	<textarea id="projectRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"projectRemark","class":"input",
                               		style:{width:"550px"},rows:"3",
                               		trim:true,value:"${projectManage?.projectRemark}"
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