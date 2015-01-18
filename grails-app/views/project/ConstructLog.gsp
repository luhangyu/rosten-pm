<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>施工日志</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
<body>
	<div style="text-Align:center">
		<input  data-dojo-type="dijit/form/ValidationTextBox" id="itemId"  data-dojo-props='name:"itemId",style:{display:"none"}' />
        <div class="rosten_form" style="width:700px;text-align:left">
        	<input id="ProjectPlanId" data-dojo-type="dijit/form/ValidationTextBox"  data-dojo-props='name:"ProjectPlanId",style:{display:"none"},value:""' />
          
            <fieldset class="fieldset-form">
                <legend class="tableHeader">日志记录</legend>
                <table class="tableData">
                    <tbody>

				          <tr>
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>施工日期：</div>
                            <td width="250">
					    	<input id="constructDate" data-dojo-type="dijit/form/DateTextBox" 
			                	data-dojo-props='name:"constructDate",
			                	trim:true,required:true,
			                	value:"${constructLog?.getFormatteConstructDate()}"
			               '/>
					    	</td>
					    	<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>填报人：</div>
                            <td width="250">
					    	<input id="logMaker" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,name:"logMaker",
									value:"${constructLog?.logMaker}"
			                '/>
					    </td>
					    </tr>
					     <tr>
					    	<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>完成工程量：</div>
                            <td width="250">
					    	<input id="consDoneQutt" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"consDoneQutt",
									value:"${constructLog?.consDoneQutt}"
			                '/>
					    	</td>
                          
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>完成百分比：</div>
                            <td width="250">
					    	<input id="consDoneRate" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"consDoneRate",
									value:"${constructLog?.consDoneRate}"
			                '/>
					    	</td>
					    </tr>
						<tr>
						    <td width="120"><div align="right">日志备注：</div></td>
						    <td colspan=3>
						    	<textarea id="conslogRemark" data-dojo-type="dijit/form/SimpleTextarea" 
	    							data-dojo-props='name:"conslogRemark","class":"input",
	                               		style:{width:"550px"},rows:"2",
	                               		trim:true,value:"${constructLog?.conslogRemark}"
	                           '>
	                           </textarea>
						    </td>
						</tr>
						<tr>
							<td colspan=4>
								<div style="text-align:center;margin-top:10px">
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){constructLog_Submit()}'>确定</button>
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
