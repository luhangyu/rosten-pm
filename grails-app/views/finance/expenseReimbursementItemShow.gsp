<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>报销子表单</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  
<body>
	<div style="text-Align:center">
		<input  data-dojo-type="dijit/form/ValidationTextBox" id="itemId"  data-dojo-props='name:"itemId",style:{display:"none"}' />
        <div class="rosten_form" style="width:700px;text-align:left">
        	<input id="personInforId" data-dojo-type="dijit/form/ValidationTextBox"  data-dojo-props='name:"personInforId",style:{display:"none"},value:""' />
            <fieldset class="fieldset-form">
                <legend class="tableHeader">学员信息</legend>
                <table class="tableData">
                    <tbody>
						<tr>
                            <td width="100">
                                <div align="right"><span style="color:red">*&nbsp;</span>学员名称：</div>
                            </td>
                           <td  width="250">
                                <input id="itemName" data-dojo-type="dijit/form/ValidationTextBox"
                                	data-dojo-props='name:"itemName",
                                		"class":"input",
                                		trim:true,
                                		required:true,
                                		readOnly:true,
                                		promptMessage:"请正确输入学员名称..."
                                '/>    
                                <button data-dojo-type='dijit/form/Button' 
									data-dojo-props="label:'选择',iconClass:'docAddIcon'">
									<script type="dojo/method" data-dojo-event="onClick">
										addPersonInfor();
									</script>
								</button>                              
                            </td>
                            
                          	<td width="100">
                                <div align="right"><span style="color:red">*&nbsp;</span>部门名称：</div>
                            </td>
                           	<td  width="200">
                                <input id="itemDept" data-dojo-type="dijit/form/ValidationTextBox"
                                	data-dojo-props='name:"itemDept",
                                		"class":"input",
                                		trim:true,
                                		required:true,
                                		readOnly:true,
                                		promptMessage:"请正确输入部门名称"
                                '/>
                            </td>
                            
                        </tr>
                        <tr>
                        	<td>
                                <div align="right"><span style="color:red">*&nbsp;</span>培训费用：</div>
                            </td>
                           	<td>
                                <input id="itemMoney" data-dojo-type="dijit/form/ValidationTextBox"
                                	data-dojo-props='name:"itemMoney",
                                		"class":"input",
                                		trim:true,
                                		required:true,
                                		promptMessage:"请正确输入费用"
                                '/>                                
                            </td>
                        </tr>
						<tr>
                            <td>
                                <div align="right"><span style="color:red">*&nbsp;</span>培训考试结果：</div>
                            </td>
                            <td>
                            	<input id="itemResult" data-dojo-type="dijit/form/ValidationTextBox"
                                	data-dojo-props='name:"itemResult",
                                		"class":"input",
                                		trim:true,
                                		required:true,
                                		promptMessage:"请正确输入条目名称..."
                                '/>
                            </td>
                      
                            <td>
                                <div align="right"><span style="color:red">*&nbsp;</span>证书是否发放：</div>
                            </td>
                            <td>
                            	
                                <input id="itemCert1" data-dojo-type="dijit/form/RadioButton"
					           		data-dojo-props='name:"itemCert",type:"radio",
										value:"是"
				              	'/>
								<label for="itemCert1">是</label>
							
				              	<input id="itemCert2" data-dojo-type="dijit/form/RadioButton"
				           			data-dojo-props='name:"itemCert",type:"radio",
				           			checked:true,
									value:"否"
				              	'/>
								<label for="itemCert2">否</label>
                                
                                
                                
                            </td>
                        </tr>
						<tr>
							<td colspan=4>
								<div style="text-align:center;margin-top:10px">
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){staffItem_Submit()}'>确定</button>
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){rosten.hideRostenShowDialog()}'>取消</button>
								</div>
							</td>
						</tr>
                    </tbody>
                </table>
				
				
            </fieldset>
		</div>
	</div>
</body>
</html>
