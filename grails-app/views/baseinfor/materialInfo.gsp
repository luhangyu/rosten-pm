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
					var parentNode = window.opener;
			        window.close();
			        
			        if(parentNode.metInfor_rostenGrid){
			        	parentNode.metInfor_rostenGrid.refresh();
			    	}
				};
				showSelectDialog = function(type){
					
					//材料类型
					var matInfoTypeId = registry.byId("matInfoTypeId").get("value");
					if(matInfoTypeId==""){
						rosten.alert("请正确填写材料类型！");
						return;
					}
					var url = "${createLink(controller:'baseinfor',action:'getMatUnitSelect',params:[companyId:company?.id])}";
					url += "&matInfoTypeId="+matInfoTypeId;
					
					switch(type){
					case "MatInfoPurUnit":
						var matUnitName = registry.byId("matInfoPurUnit").get("value");
						var dialog = rosten.selectBaseDialog("材料计量单位选择",url,false,"matInfoPurUnit","matInfoPurUnitId",matUnitName);
						dialog.callback = function(data){
							if(data.length>0){
								var dealId = data[0].id
								/*
								 * 特殊字段赋值
								 */
								dialog.getStoreDate(dealId,function(item){
									registry.byId("matInfoPurUnitId").attr("value", dialog.chkboxStore.getValue(item, "id"));
									registry.byId("matInfoPurUnit").attr("value", dialog.chkboxStore.getValue(item, "name"));									
								});
							}else{
								registry.byId("matInfoPurUnit").attr("value","");
								registry.byId("matInfoPurUnitId").attr("value", "");
							}
						};
						break;
						
					case "MatInfoGetUnit":
						var matUnitName = registry.byId("matInfoGetUnit").get("value");
						var dialog = rosten.selectBaseDialog("材料计量单位选择",url,false,"matInfoGetUnit","matInfoGetUnitId",matUnitName);
						dialog.callback = function(data){
							if(data.length>0){
								var dealId = data[0].id
								/*
								 * 特殊字段赋值
								 */
								dialog.getStoreDate(dealId,function(item){
									registry.byId("matInfoGetUnitId").attr("value", dialog.chkboxStore.getValue(item, "id"));
									registry.byId("matInfoGetUnit").attr("value", dialog.chkboxStore.getValue(item, "name"));									
								});
							}else{
								registry.byId("matInfoGetUnit").attr("value","");
								registry.byId("matInfoGetUnitId").attr("value", "");
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
					    	<input id="matInfoName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"matInfoName",
									value:"${materialInfo?.matInfoName}"
			                '/>
					    </td>
						<td><div align="right"><span style="color:red">*&nbsp;</span>材料类型：</div></td>
					    <td>
					    	<input id="matInfoType" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"matInfoType",trim:true,
									value:"${materialInfo?.matInfoType?.matTypeName}"
				          	'/>
				          	<g:if test="${!onlyShow }">
					         	<g:hiddenField id="matInfoTypeId" name="matInfoTypeId" data-dojo-type="dijit/form/ValidationTextBox"  value="${materialInfo?.matInfoType?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										rosten.selectBaseTreeDialog(null,"${createLink(controller:'baseinfor',action:'matTypeTreeDataStore',params:[companyId:company?.id])}",false,"matInfoType","matInfoTypeId");
									}'>选择</button>
								
			           		</g:if>
			           	</td>
					</tr>
					<tr>
					 <td><div align="right"><span style="color:red">*&nbsp;</span>材料小类：</div></td>
					    <td>
			                <select id="matInfoSonType" data-dojo-type="dijit/form/FilteringSelect" 
					                data-dojo-props='name:"matInfoSonType",
					                trim:true,required:true,missingMessage:"请选择类别！",invalidMessage:"请选择类别！",
					      			value:"${materialInfo?.matInfoSonType}"
					            '>
								<option value="大材料">大材料</option>
								<option value="零星材料">零星材料</option>
					    	</select>
					    </td>
					</tr>
					<tr>
					    <td><div align="right">材料品牌：</div></td>
					    <td >
					    	<input id="matInfoBrand" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"matInfoBrand",trim:true,
									value:"${materialInfo?.matInfoBrand}"
			                '/>
					    </td>
					    <td><div align="right">材料规格：</div></td>
					    <td >
					    	<input id="matInfoNorms" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"matInfoNorms",trim:true,
									value:"${materialInfo?.matInfoNorms}"
			                '/>
					    </td>
					</tr>
						<tr>
					    <td><div align="right"><span style="color:red">*&nbsp;</span>大单位：</div></td>
					    <td >
					    	<input id="matInfoPurUnit" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='trim:true,readOnly:true,required:true,
									value:"${materialInfo?.matInfoPurUnit?.matUnitName}"
				          	'/>
				          <g:if test="${!onlyShow}">
					         	<g:hiddenField id="matInfoPurUnitId" data-dojo-type="dijit/form/ValidationTextBox" name="matInfoPurUnitId" value="${materialInfo?.matInfoPurUnit?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("MatInfoPurUnit");	
									}'>选择</button>
			           		</g:if>			                
					    </td>
					    <td><div align="right">小单位：</div></td>
					    <td >
			                <input id="matInfoGetUnit" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='trim:true,readOnly:true,
									value:"${materialInfo?.matInfoGetUnit?.matUnitName}"
				          	'/>
				          <g:if test="${!onlyShow}">
					         	<g:hiddenField id="matInfoGetUnitId" data-dojo-type="dijit/form/ValidationTextBox" name="matInfoGetUnitId" value="${materialInfo?.matInfoGetUnit?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("MatInfoGetUnit");	
									}'>选择</button>
			           		</g:if>			 
					    </td>
					</tr>
					<tr>
					   
					     <td><div align="right">换算数量：</div></td>
					    <td>
					    	<input id="matInfoQuantity" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"matInfoQuantity",trim:true,
									value:"${materialInfo?.matInfoQuantity}"
			                '/>
					    </td>
					</tr>

				

				
						<tr>
					    <td><div align="right">进价(元)：</div></td>
					    <td >
					    	<input id="mInfoRefPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"mInfoRefPrice",trim:true,
									value:"${materialInfo?.mInfoRefPrice}"
			                '/>
					    </td>
					    <td><div align="right">批发价(元)：</div></td>
					    <td >
					    	<input id="mInfoWPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"mInfoWPrice",trim:true,
									value:"${materialInfo?.mInfoWPrice}"
			                '/>
					    </td>
					</tr>
						<tr>
					    <td><div align="right">零售价(元)：</div></td>
					    <td >
					    	<input id="mInfoRPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"mInfoRPrice",trim:true,
									value:"${materialInfo?.mInfoRPrice}"
			                '/>
					    </td>
					    <td><div align="right">底线价(元)：</div></td>
					    <td >
					    	<input id="mInfoLowPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='name:"mInfoLowPrice",trim:true,
									value:"${materialInfo?.mInfoLowPrice}"
			                '/>
					    </td>
					</tr>
					<tr>
					    <td><div align="right">备注：</div></td>
					    <td  colspan=3>
					    	<textarea id="matInfoRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"matInfoRemark","class":"input",
                               		style:{width:"560px"},rows:"5",
                               		trim:true,value:"${materialInfo?.matInfoRemark}"
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