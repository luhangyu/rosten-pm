<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>工资发放记录</title>
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
		     	"rosten/app/EmployeApplication"],
			function(parser,kernel,registry,dom,lang){
				kernel.addOnLoad(function(){
					rosten.init({webpath:"${request.getContextPath()}",dojogridcss : true});
					rosten.cssinit();
				});

				
				salary_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					var workerSalaries =["workerName","workerDepart","salaryType","salaryRmb","livingRmb","shouldRmb","deductRmb","bonusRmb","finalRmb","wrkSlyRemark","createdDate"]
					content.workerSalariesValues = rosten.getGridDataCollect(workerSalaryGrid,workerSalaries);
					
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/employe/salarySave",content,function(data){
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
				selectPerson =function(){
					var userName = registry.byId("attendUserName").get("value");
					//application.selectBaseTreeDialog = function(title,url,type,inputName,inputId,showRootName) {
					var dialog = rosten.selectDepart("${createLink(controller:'system',action:'userTreeDataStore',params:[companyId:company?.id])}",false,"attendUserName","attendUserNameId",userName);
					dialog.callback = function(data){
						if(data.length>0){
							var dealId = data[0].id							
							dialog.getStoreDate(dealId,function(item){
							registry.byId("attendUserNameId").attr("value", dialog.chkboxStore.getValue(item, "id"));
							registry.byId("attendUserName").attr("value", dialog.chkboxStore.getValue(item, "name"));
							});
							
						}else{
							registry.byId("attendUserName").attr("value","");
							registry.byId("attendUserNameId").attr("value", "");
						}
					}

						
				};
			
		});
    </script>
</head>
<body>
<div class="rosten_action">
	<div data-dojo-type="rosten/widget/ActionBar" data-dojo-id="rosten_actionBar" 
		data-dojo-props='actionBarSrc:"${createLink(controller:'EmployeAction',action:'salaryForm',id:salary?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='doLayout:false,persist:false,tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="工资信息" data-dojo-props='doLayout:false,'>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${salary?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"发放明细",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>标       题：</div></td>
					    <td colspan=3>
						    <input id="salaryName" data-dojo-type="dijit/form/ValidationTextBox" 
		               		data-dojo-props='name:"salaryName",style:{width:"550px"},
		               		trim:true,required:true,
							value:"${salary?.salaryName}"
		          			'/>
			            </td>
			        </tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>发放日期：</div></td>
					    <td width="250">
						    <input id="wageDay" data-dojo-type="dijit/form/DateTextBox" 
		               		data-dojo-props='name:"wageDay",${fieldAcl.isReadOnly("wageDay")},
		               		trim:true,required:true,
							value:"${salary?.getFormatWageDay()}"
		          			'/>
			            </td>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>填写人：</div></td>
					    <td width="250">
					    	<input id="salaryMaker" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"salaryMaker",
				               		trim:true,required:true,
									value:"${salary?.salaryMaker}"
				          	'/>	
					    </td>
					</tr>
					<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>开始日期：</div></td>
					    <td width="250">
						    <input id="beginDay" data-dojo-type="dijit/form/DateTextBox" 
		               		data-dojo-props='name:"beginDay",${fieldAcl.isReadOnly("beginDay")},
		               		trim:true,required:true,
							value:"${salary?.getFormatBeginDay()}"
		          			'/>
			            </td>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>结束日期：</div></td>
					    <td width="250">
						    <input id="endDay" data-dojo-type="dijit/form/DateTextBox" 
		               		data-dojo-props='name:"endDay",${fieldAcl.isReadOnly("endDay")},
		               		trim:true,required:true,
							value:"${salary?.getFormatEndDay()}"
		          			'/>
			            </td>
					</tr>
					<tr>
					   <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>总金额(元):</div></td>
					    <td width="250">
					    	<input id="salarySum" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"salarySum",
				               		trim:true,required:true,
									value:"${salary?.salarySum}"
				          	'/>	
					    </td>
					</tr>
				</table>
				<div style="clear:both;"></div>
			</div>
								

			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-id="workerSalaryTitlePane" 
				data-dojo-props='"class":"rostenTitleGrid",title:"考勤记录",toggleable:false,_moreClick:workerSalary_addItem,moreText:"<span style=\"color:#108ac6\">增加</span>",marginBottom:"2px"'>
            	<div data-dojo-type="rosten/widget/RostenGrid" id="workerSalaryGrid" data-dojo-id="workerSalaryGrid"
					data-dojo-props='imgSrc:"${resource(dir:'images/rosten/share',file:'wait.gif')}",showPageControl:false,url:"${createLink(controller:'employe',action:'workerSalaryGrid',id:salary?.id)}"'></div>             	           	
            </div>
		</form>
	</div>
</div>
</body>