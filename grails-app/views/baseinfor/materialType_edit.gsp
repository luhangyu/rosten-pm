<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>材料类型</title>
	<script type="text/javascript">
    	require([
		 		"dojo/_base/kernel",
		 		"dijit/registry",
		 		"dojo/dom",
		 		"dojo/_base/xhr",
		 		"dijit/form/Form",
		 		"dijit/form/ValidationTextBox",
		 		"dijit/form/TextBox",
		 		"dijit/form/SimpleTextarea",
		 		"dojox/layout/ContentPane",
		     	"rosten/widget/ActionBar",
		     	"rosten/app/Application"
		    ],
			function(kernel,registry,dom,xhr){
				
    		materialType_save = function(){
    			var formWidget = registry.byId("rosten_form");
				if(!formWidget.validate()){
					rosten.alert("请正确填写相关信息！");
					return;
				}
				
    			var pane = registry.byId("objEditPane");
    			var xhrArgs = {
    	    		form:"rosten_form",
    	        	handleAs: "text",
    	        	load: function(data) {
    	        		pane.set("content",data);
    	        	},
    	        	error: function(error) {
    	            	pane.set("content",error);
    	        	}
    	     	}
    			var deferred = xhr.post(xhrArgs);
    			
    		}
    		kernel.addOnLoad(function(){
    			<g:if test="${flash.refreshTree}">
    				refreshObjTree();
    			</g:if>
    		})
    	});
    </script>
</head>
<body>
<div class="rosten_action">
	<div data-dojo-type="rosten/widget/ActionBar" data-dojo-id="rosten_actionBar" 
		data-dojo-props='actionBarSrc:"${createLink(controller:'baseinforAction',action:'materialTypeForm',id:materialType?.id,params:[userid:user?.id])}"'>
	</div>
</div>
<g:if test="${flash.message}">
	<div class="message">${flash.message}</div>
</g:if>
<g:form id="rosten_form" data-dojo-type="dijit/form/Form" name="rosten_form" url='[controller:"baseinfor",action:"matTypeSave"]' class="rosten_form"  style="width:none;height:none">
	
	<g:hiddenField name="id" value="${materialType?.id}" />
	<g:hiddenField name="parentId" value="${parentId}"/>
	<g:hiddenField name="companyId" value="${companyId}"/>
	
	<fieldset class="fieldset-form">
       	<legend class="tableHeader">材料类型</legend>
		<table class="tableData">
			<tr>
	          	<td width="100">
	              <div align="right" >
	                  <span style="color:red">*&nbsp;</span>材料类型名称：
	              </div>
	          	</td>
	             <td  width="180">
	             	<input id="matTypeName" data-dojo-type="dijit/form/ValidationTextBox" 
	                 	data-dojo-props='trim:true,required:true,name:"matTypeName","class":"input",
							value:"${materialType?.matTypeName}"
	                '/>
	             </td>
   		 	</tr>	
			<tr>
                <td>
                    <div align="right">上级材料类型：</div>
                </td>
                <td>
                	<input id="parentName" data-dojo-type="dijit/form/ValidationTextBox" 
                   		data-dojo-props='name:"parentName",
                   			"class":"input",
                   			trim:true,readOnly:true,disabled:true,
                   			value:"${materialType?.parent?.matTypeName }"
                   	'/>
					<button data-dojo-type="dijit.form.Button" 
						data-dojo-props='onClick:function(){
							rosten.selectBaseTreeDialog(null,"${createLink(controller:'baseinfor',action:'matTypeTreeDataStore',params:[companyId:materialType?.company?.id])}",false,"parentName","parentId");
						}'>选择</button>
                </td>
            </tr>
			<tr>
               <td>
                   <div align="right">显示顺序：</div>
               </td>
               <td>
               	<input id="serialNo" data-dojo-type="dijit/form/ValidationTextBox" 
            		data-dojo-props='name:"serialNo",
            			"class":"input",trim:true,
            			value:"${materialType?.serialNo }"
            	'/>
               </td>
           	</tr>
			<tr>
            	<td>
                    <div align="right" >内容描述：</div>
                </td>

				<td colspan="3">
					<textarea id="matTypeRemark" data-dojo-type="dijit/form/SimpleTextarea"  
						data-dojo-props='name:"matTypeRemark",
				 			"class":"input",trim:true,style:{width:"400px"},
							value:"${materialType?.matTypeRemark }"
			
					'></textarea>
				</td>
            </tr>
		</table>
	</fieldset>
</g:form>

</body>