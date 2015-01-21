<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>工资明细</title>
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
					    	<input id="workerName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"workerName",
									value:"${workerSalary?.workerName}"
			                '/>
			      
			         	<g:hiddenField name="attendUserNameId" value="${attendUserNameId}" />
						<button data-dojo-type="dijit.form.Button" data-dojo-props='onClick:function(){selectPerson()}'>选择</button>
           			
					    </td>
					
						<td width="100"><div align="right"><span style="color:red">*&nbsp;</span>部门：</div></td>
					    <td width="250">
					    	<input id="workerDepart" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"workerDepart",
				               		trim:true,required:true,
									value:"${workerSalary?.workerDepart}"
				          	'/>				          	
			           	</td>
					</tr>
					<tr>
					    <td width="100"><div align="right"><span style="color:red">*&nbsp;</span>工资类型：</div></td>
					    <td width="250">
							<select id="salaryType" data-dojo-type="dijit/form/FilteringSelect" 
					                data-dojo-props='name:"salaryType",
					                trim:true,required:true,
					      			value:"${workerSalary?.salaryType}",
					            '>
								<option value="月工资">月工资</option>
								<option value="日工资">日工资</option>
								
					    	</select>
					    </td>
					
						<td width="100"><div align="right"><span style="color:red">*&nbsp;</span>备注：</div></td>
					    <td width="250">
					    	<input id="wrkSlyRemark" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='name:"wrkSlyRemark",trim:true,
									value:"${workerSalary?.wrkSlyRemark}"
				          	'/>				          	
			           	</td>
					</tr>
					
					</tbody>
				</table>
					
					
					<table border="0" width="500" align="center">
						<tr>
							<td style="width:16%">工资(元)</td>
							<td style="width:16%">生活费(元)</td>
							<td style="width:16%">应发(元)</td>
							<td style="width:16%">扣除(元)</td>
							<td style="width:16%">奖励(元)</td>
							<td style="width:16%">实发(元)</td>
							
						</tr>
					
						<tr>
							<td><input style="width:40px" id="salaryRmb"  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"salaryRmb",value:"${workerSalary?.salaryRmb}"
			                '/>
			                </td>
							<td><input style="width:40px" id="livingRmb"  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"livingRmb",value:"${workerSalary?.livingRmb}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="shouldRmb"  data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"shouldRmb",value:"${workerSalary?.shouldRmb}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="deductRmb" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"deductRmb",value:"${workerSalary?.deductRmb}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="bonusRmb"   data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bonusRmb",value:"${workerSalary?.bonusRmb}"
			                '/>
			                </td>
			                <td><input style="width:40px" id="finalRmb"   data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"finalRmb",value:"${workerSalary?.finalRmb}"
			                '/>
			                </td>
						</tr>

                </table>
                			
						<tr>
							<td colspan=4>
								<div style="text-align:center;margin-top:10px">
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){workerSalary_Submit()}'>确定</button>
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){rosten.hideRostenShowDialog()}'>取消</button>
								</div>
							</td>
						</tr>
				
				
            </fieldset>
		</div>
	</div>
</body>
</html>
