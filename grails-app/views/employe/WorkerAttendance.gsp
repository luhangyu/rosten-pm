<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考勤记录</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
<body>
	<div style="text-Align:center">
		<input  data-dojo-type="dijit/form/ValidationTextBox" id="itemId"  data-dojo-props='name:"itemId",style:{display:"none"}' />
        <div class="rosten_form1" style="width:700px;text-align:left">
        	<input id="ProjectPlanId" data-dojo-type="dijit/form/ValidationTextBox"  data-dojo-props='name:"ProjectPlanId",style:{display:"none"},value:""' />
          
            <fieldset class="fieldset-form">
                <legend class="tableHeader">基本信息</legend>
                <table class="tableData" align="center">
                    <tbody>
					<tr>
					    <td width="100"><div align="right"><span style="color:red">*&nbsp;</span>姓名：</div></td>
					    <td width="250">
					    	<input id="attendUserName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"attendUserName",
									value:"${workerAttendance?.attendUserName}"
			                '/>
			      
		         	<g:hiddenField name="attendUserNameId" value="${attendUserNameId}" />
					<button data-dojo-type="dijit.form.Button" data-dojo-props='onClick:function(){selectPerson()}'>选择</button>
           			
					    </td>
					
						<td width="100"><div align="right"><span style="color:red">*&nbsp;</span>部门：</div></td>
					    <td width="250">
					    	<input id="attendDepart" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"attendDepart",
				               		trim:true,required:true,
									value:"${workerAttendance?.attendDepart}"
				          	'/>				          	
			           	</td>
					
					</tr>
					</tbody>
				</table>
					
					
					<table border="0" width="500" align="center">
						<tr>
							<td style="width:16%">出勤(天)</td>
							<g:if test="${isShowField}">
							<td style="width:16%">事假(天)</td>
							<td style="width:16%">病假(天)</td>
							<td style="width:16%">旷工(天)</td>
							<td style="width:16%">迟到(天)</td>
							<td style="width:16%">早退(天)</td>
							</g:if>
						</tr>
					
						<tr>
							<td><input style="width:40px" id="workNumber" onChange='attend_onChange(this)'  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"workNumber",value:"${workerAttendance?.workNumber}"
			                '/>
			                </td>
			                <g:if test="${isShowField}">
							<td><input style="width:40px" id="affairsNumber" onChange='attend_onChange(this)'  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"affairsNumber",value:"${workerAttendance?.affairsNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="illNumber" onChange='attend_onChange(this)'  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"illNumber",value:"${workerAttendance?.illNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="awayNumber" onChange='attend_onChange(this)'  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"awayNumber",value:"${workerAttendance?.awayNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="lateNumber" onChange='attend_onChange(this)'  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"lateNumber",value:"${workerAttendance?.lateNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="earlyAwayNumber" onChange='attend_onChange(this)'  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"earlyAwayNumber",value:"${workerAttendance?.earlyAwayNumber}"
			                '/>
			                </td>
			                </g:if>
						</tr>

                </table>
                	
                	
                	<table style="display:none" border="0"  class="tableData" align="center">
                	<tr>
						<td width="100"><div align="right">备注：</div></td>
					    <td colspan=3>
					    	<textarea id="attendRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"attendRemark","class":"input",
                               		style:{width:"500px"},rows:"2",
                               		trim:true,value:"${workerAttendance?.attendRemark}"
                           '>
                           </textarea>
					    </td>
					</tr>
                	 </table>	
                			
							<tr>
							<td colspan=4>
								<div style="text-align:center;margin-top:10px">
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){workerAttendance_Submit()}'>确定</button>
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){rosten.hideRostenShowDialog()}'>取消</button>
								</div>
							</td>
						</tr>
				
				
            </fieldset>
		</div>
	</div>
</body>
</html>
