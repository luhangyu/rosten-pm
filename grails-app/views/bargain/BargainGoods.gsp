<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购货物明细</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
<body>
	<div style="text-Align:center">
		<input  data-dojo-type="dijit/form/ValidationTextBox" id="itemId"  data-dojo-props='name:"itemId",style:{display:"none"}' />
        <div class="rosten_form" style="width:700px;text-align:left">
        	<input id="BargainId" data-dojo-type="dijit/form/ValidationTextBox"  data-dojo-props='name:"BargainId",style:{display:"none"},value:""' />
          
            <fieldset class="fieldset-form">
                <legend class="tableHeader">货物添加</legend>
                <table class="tableData">
                    <tbody>
						 <tr>
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>货物名称：</div>
                            <td width="250">
			                <input id="barGoodsName" data-dojo-type="dijit/form/ValidationTextBox" 
				               	data-dojo-props='trim:true,readOnly:true,required:true,
									value:"${bargainGoods?.barGoodsName}"
				          	'/>
				          	<g:if test="${!onlyShow}">
					         	<g:hiddenField id="barGoodsNameId" data-dojo-type="dijit/form/ValidationTextBox" name="barGoodsNameId" value="${BargainGoods?.barGoodsName?.id}" />
								<button data-dojo-type="dijit.form.Button" 
									data-dojo-props='onClick:function(){
										showSelectDialog("BarGoodsName");	
									}'>选择</button>
			           		</g:if>		

					    	</td>
                          
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>生产厂家：</div>
                            <td width="250">
					    	<input id="barGoodsCorp" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",readOnly:true,
									value:"${bargainGoods?.barGoodsCorp}"
			                '/>
					    	</td>
					    </tr>
				          <tr>
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>单位：</div>
                            <td width="250">
					    	<input id="barGoodsUnit" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",readOnly:true,
									value:"${bargainGoods?.barGoodsUnit}"
			                '/>
					    	</td>
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>市场价：</div>
                            <td width="250">
					    	<input id="barGoodsPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,placeHolder:"系统自动赋值",readOnly:true,
									value:"${bargainGoods?.barGoodsPrice}"
			                '/>
					    	</td>
                          
					    </tr>
					     <tr>
					    	<td width="120"><div align="right"><span style="color:red">*&nbsp;</span>数量：</div>
                            <td width="250">
					    	<input id="barGoodsNum" onChange='barGoods_onChange()' data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"barGoodsNum",
									value:"${bargainGoods?.barGoodsNum}"
			                '/>
					    	</td>
                          
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>折扣：</div>
                            <td width="250">
					    	<input id="barGoodsDiscount" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"barGoodsDiscount",
									value:"${bargainGoods?.barGoodsDiscount}"
			                '/>
					    	</td>
					    </tr>
					    <tr>
					     <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>总价格：</div>
                            <td width="250">
					    	<input id="barGoodsTPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,placeHolder:"系统自动赋值",readOnly:true,name:"barGoodsTPrice",
									value:"${bargainGoods?.barGoodsTPrice}"
			                '/>
					    </td>
					</tr>
						<tr>
					    <td width="120"><div align="right">备注：</div></td>
					    <td colspan=3>
					    	<textarea id="barGoodsRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"barGoodsRemark","class":"input",
                               		style:{width:"550px"},rows:"2",
                               		trim:true,value:"${bargainGoods?.barGoodsRemark}"
                           '>
                           </textarea>
					    </td>
					</tr>
						<tr>
							<td colspan=4>
								<div style="text-align:center;margin-top:10px">
									<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){bargainGoods_Submit()}'>确定</button>
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
