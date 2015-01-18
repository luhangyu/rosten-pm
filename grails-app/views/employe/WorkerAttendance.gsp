<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>考勤记录</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
<body>
	<div style="text-Align:center">
		<input  data-dojo-type="dijit/form/ValidationTextBox" id="itemId"  data-dojo-props='name:"itemId",style:{display:"none"}' />
        <div class="rosten_form" style="width:700px;text-align:left">
        	<input id="ProjectPlanId" data-dojo-type="dijit/form/ValidationTextBox"  data-dojo-props='name:"ProjectPlanId",style:{display:"none"},value:""' />
          
            <fieldset class="fieldset-form">
                <legend class="tableHeader">基本信息</legend>
                <table class="tableData">
                    <tbody>
					<tr>
					    <td width="100"><div align="right"><span style="color:red">*&nbsp;</span>员工姓名：</div></td>
					    <td width="200">
					    	<input id="attendUserName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"attendUserName",
									value:"${workerAttendance?.attendUserName}"
			                '/>
					    </td>
					
						<td width="100"><div align="right"><span style="color:red">*&nbsp;</span>所属部门：</div></td>
					    <td width="200">
					    	<input id="attendDepart" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"attendDepart",
				               		trim:true,required:true,
									value:"${workerAttendance?.attendDepart}"
				          	'/>				          	
			           	</td>
					
					</tr>
					<tr>
						<td width="120"><div align="right">备注：</div></td>
					    <td colspan=3>
					    	<textarea id="attendRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"attendRemark","class":"input",
                               		style:{width:"500px"},rows:"2",
                               		trim:true,value:"${workerAttendance?.attendRemark}"
                           '>
                           </textarea>
					    </td>
					</tr>
					
					<table border="0" width="450" align="center">
						<tr>
							<td style="width:15%">出勤(天)</td>
							<td style="width:15%">事假(天)</td>
							<td style="width:15%">病假(天)</td>
							<td style="width:15%">旷工(天)</td>
							<td style="width:15%">迟到(天)</td>
							<td style="width:15%">早退(天)</td>
						</tr>
					
						<tr>
							<td><input style="width:40px" id="workNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"workNumber",value:"${workerAttendance?.workNumber}"
			                '/>
			                </td>
							<td><input style="width:40px" id="affairsNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"affairsNumber",value:"${workerAttendance?.affairsNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="illNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"illNumber",value:"${workerAttendance?.illNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="awayNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"awayNumber",value:"${workerAttendance?.awayNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="lateNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"lateNumber",value:"${workerAttendance?.lateNumber}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="earlyAwayNumber" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"earlyAwayNumber",value:"${workerAttendance?.earlyAwayNumber}"
			                '/>
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
				</tbody>
				
            </fieldset>
		</div>
	</div>
</body>
</html>
