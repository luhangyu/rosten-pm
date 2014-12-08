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
        	<input id="expenseReimburseId" data-dojo-type="dijit/form/ValidationTextBox"  data-dojo-props='name:"expenseReimburseId",style:{display:"none"},value:""' />
            <fieldset class="fieldset-form">
                <legend class="tableHeader">信息添加</legend>
                <table class="tableData">
                    <tbody>
						<tr>
                            <td width="120">
                                <div align="right"><span style="color:red">*&nbsp;</span>发生日期：</div>
                            </td>
					    	 <td width="250">
						    	<input id="ExpenseReimHappenDate" data-dojo-type="dijit/form/DateTextBox" 
				                	data-dojo-props='name:"ExpenseReimHappenDate",
				                	trim:true,required:true,missingMessage:"请正确填写发布时间！",invalidMessage:"请正确填写发布时间！",
				                	value:"${ExpenseReimbursementItem?.ExpenseReimHappenDate()}"
				               '/>
					    	</td>
					    	
                            <td width="120">
                                <div align="right"><span style="color:red">*&nbsp;</span>费用类型：</div>
                            </td>
                             <td width="250">
						    	<select id="ExpenseReimItemType" data-dojo-type="dijit/form/FilteringSelect" 
					                data-dojo-props='name:"ExpenseReimItemType",
					                trim:true,required:true,missingMessage:"请选择类别！",invalidMessage:"请选择类别！",
					      			value:"${ExpenseReimbursementItem?.ExpenseReimItemType}"
					            '>
								<option>汽车</option>
								<option>旅店</option>
					    	</select>
				           </td>
				          </tr>
				          <tr>
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>报销金额：</div>
                            <td width="250">
					    	<input id="ExpenseReimItemMoney" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"ExpenseReimItemMoney",
									value:"${ExpenseReimbursementItem?.ExpenseReimItemMoney}"
			                '/>
					    	</td>
                          
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>票据张数：</div>
                            <td width="250">
					    	<input id="ExpenseReimItemPaperNum" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"ExpenseReimItemPaperNum",
									value:"${ExpenseReimbursementItem?.ExpenseReimItemPaperNum}"
			                '/>
					    	</td>
					    </tr>
						<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>备注：</div></td>
					    <td colspan=3>
					    	<textarea id="ExpenseReimItemRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"ExpenseReimItemRemark","class":"input",
                               		style:{width:"550px"},rows:"2",
                               		trim:true,value:"${ExpenseReimbursementItem?.ExpenseReimItemRemark}"
                           '>
                           </textarea>
					    </td>
					</tr>
						<tr>
							<td colspan=4>
								<div style="text-align:center;margin-top:10px">
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){expenseReimbursementItem_Submit()}'>确定</button>
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
