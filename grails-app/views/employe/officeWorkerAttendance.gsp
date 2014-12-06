<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>员工考勤</title>
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

				
				officeWorkerAttendance_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					//content.officeAttendanceDate=registry.byId("officeAttendanceDate").get("value");
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/employe/officeWorkerAttendanceSave",content,function(data){
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
		data-dojo-props='actionBarSrc:"${createLink(controller:'EmployeAction',action:'officeWorkerAttendanceForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${officeWorkerAttendance?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"员工考勤信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>员工姓名：</div></td>
					    <td >
					    	<input id="officeAttendanceName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"officeAttendanceName",
									value:"${officeWorkerAttendance?.officeAttendanceName}"
			                '/>
					    </td>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>考勤日期：</div></td>
					    <td>
						    <input id="officeAttendanceDate" data-dojo-type="dijit/form/DateTextBox" 
		               		data-dojo-props='name:"officeAttendanceDate",${fieldAcl.isReadOnly("officeAttendanceDate")},
		               		trim:true,required:true,
							value:"${officeWorkerAttendance?.getFormatteofficeAttendanceDate()}"
		          			'/>
			            </td>

					</tr>
					<tr>
						<td><div align="right"><span style="color:red">*&nbsp;</span>所属部门：</div></td>
					    <td>
					    	<input id="officeAttendanceDepart" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"officeAttendanceDepart",
				               		trim:true,required:true,
									value:"${officeWorkerAttendance?.officeAttendanceDepart}"
				          	'/>				          	
			           	</td>
					
					</tr>
					<tr>
						<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>备注：</div></td>
					    <td colspan=3>
					    	<textarea id="officeWorkerAttendanceRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"officeWorkerAttendanceRemark","class":"input",
                               		style:{width:"550px"},rows:"2",
                               		trim:true,value:"${officeWorkerAttendance?.officeWorkerAttendanceRemark}"
                           '>
                           </textarea>
					    </td>
					</tr>
					
					<table border="0" width="640" align="center">
						<tr>
							<td style="width:15%">出勤</td>
							<td style="width:15%">事假</td>
							<td style="width:15%">病假</td>
							<td style="width:15%">旷工</td>
							<td style="width:15%">迟到</td>
							<td style="width:15%">早退</td>
						</tr>
					
						<tr>
							<td><input style="width:40px" id="workNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"workNumber",value:"${officeWorkerAttendance?.workNumber}"
			                '/>
			                </td>
							<td><input style="width:40px" id="affairsNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"affairsNumber",value:"${officeWorkerAttendance?.affairsNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="illNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"illNumber",value:"${officeWorkerAttendance?.illNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="awayNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"awayNumber",value:"${officeWorkerAttendance?.awayNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="lateNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"lateNumber",value:"${officeWorkerAttendance?.lateNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="earlyAwayNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"earlyAwayNumber",value:"${officeWorkerAttendance?.earlyAwayNumber}"
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