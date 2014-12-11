<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="layout" content="rosten" />
    <title>材料信息</title>
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
				materialInfo_save = function(object){
					var formWidget = registry.byId("rosten_form");
					if(!formWidget.validate()){
						rosten.alert("请正确填写相关信息！");
						return;
					}
					var content = {};
					//增加对多次单击的次数----2014-9-4
					var buttonWidget = object.target;
					rosten.toggleAction(buttonWidget,true);

					rosten.readSync(rosten.webPath + "/baseinfor/materialInfoSave",content,function(data){
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
		data-dojo-props='actionBarSrc:"${createLink(controller:'baseinforAction',action:'materialInfoForm',id:vacate?.id,params:[userid:user?.id])}"'>
	</div>
</div>

<div data-dojo-type="dijit/layout/TabContainer" data-dojo-props='persist:false, tabStrip:true,style:{width:"800px",margin:"0 auto"}' >
	<div data-dojo-type="dijit/layout/ContentPane" title="基本信息" data-dojo-props=''>
		<form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" onsubmit="return false;" class="rosten_form" style="padding:0px">
			<input  data-dojo-type="dijit/form/ValidationTextBox" id="id"  data-dojo-props='name:"id",style:{display:"none"},value:"${materialInfo?.id }"' />
        	<input  data-dojo-type="dijit/form/ValidationTextBox" id="companyId" data-dojo-props='name:"companyId",style:{display:"none"},value:"${company?.id }"' />
        	
			<div data-dojo-type="rosten/widget/TitlePane" data-dojo-props='title:"材料信息",toggleable:false,moreText:"",marginBottom:"2px"'>
				<table border="0" width="740" align="left">

					<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>材料名称：</div></td>
					    <td >
					    	<input id="materialInfoName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"materialInfoName",
									value:"${materialInfo?.materialInfoName}"
			                '/>
					    </td>
					    <td><div align="right">材料类型：</div></td>
					    <td >
					    	<input id="materialInfoType" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoType",trim:true,
									value:"${materialInfo?.materialInfoType}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">材料小类：</div></td>
					    <td>
					    	<input id="materialInfoSonType" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoSonType",trim:true,
									value:"${materialInfo?.materialInfoSonType}"
			                '/>
					    </td>
					     <td><div align="right">换算数量：</div></td>
					    <td>
					    	<input id="materialInfoQuantity" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoQuantity",trim:true,
									value:"${materialInfo?.materialInfoQuantity}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">采购材料单位：</div></td>
					    <td >
					    	<input id="materialInfoPurchaseUnit" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoPurchaseUnit",trim:true,
									value:"${materialInfo?.materialInfoPurchaseUnit}"
			                '/>
					    </td>
					    <td><div align="right">领用材料单位：</div></td>
					    <td >
					    	<input id="materialInfoGetUnit" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoGetUnit",trim:true,
									value:"${materialInfo?.materialInfoGetUnit}"
			                '/>
					    </td>
					</tr>

					<tr>
					    <td><div align="right">材料品牌：</div></td>
					    <td >
					    	<input id="materialInfoBrand" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoBrand",trim:true,
									value:"${materialInfo?.materialInfoBrand}"
			                '/>
					    </td>
					    <td><div align="right">材料规格：</div></td>
					    <td >
					    	<input id="materialInfoSpecification" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoSpecification",trim:true,
									value:"${materialInfo?.materialInfoSpecification}"
			                '/>
					    </td>
					</tr>
						<tr>
					    <td><div align="right">进价：</div></td>
					    <td >
					    	<input id="materialInfoRefPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoRefPrice",trim:true,
									value:"${materialInfo?.materialInfoRefPrice}"
			                '/>
					    </td>
					    <td><div align="right">批发价：</div></td>
					    <td >
					    	<input id="materialInfoWPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoWPrice",trim:true,
									value:"${materialInfo?.materialInfoWPrice}"
			                '/>
					    </td>
					</tr>
						<tr>
					    <td><div align="right">零售价：</div></td>
					    <td >
					    	<input id="materialInfoRPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoRPrice",trim:true,
									value:"${materialInfo?.materialInfoRPrice}"
			                '/>
					    </td>
					    <td><div align="right">底线价：</div></td>
					    <td >
					    	<input id="materialInfoLowestPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"materialInfoLowestPrice",trim:true,
									value:"${materialInfo?.materialInfoLowestPrice}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td><div align="right">备注：</div></td>
					    <td  colspan=3>
					    	<textarea id="materialInfoRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"materialInfoRemark","class":"input",
                               		style:{width:"560px"},rows:"5",
                               		trim:true,value:"${materialInfo?.materialInfoRemark}"
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