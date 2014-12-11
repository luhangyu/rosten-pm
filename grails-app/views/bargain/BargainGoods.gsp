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
					    	<input id="bargainGoodsName" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainGoodsName",
									value:"${BargainGoods?.bargainGoodsName}"
			                '/>
					    	</td>
                          
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>生产厂家：</div>
                            <td width="250">
					    	<input id="bargainGoodsCorp" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainGoodsCorp",
									value:"${BargainGoods?.bargainGoodsCorp}"
			                '/>
					    	</td>
					    </tr>
				          <tr>
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>单位：</div>
                            <td width="250">
					    	<input id="bargainGoodsUnit" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainGoodsUnit",
									value:"${BargainGoods?.bargainGoodsUnit}"
			                '/>
					    	</td>
                          
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>数量：</div>
                            <td width="250">
					    	<input id="bargainGoodsNum" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainGoodsNum",
									value:"${BargainGoods?.bargainGoodsNum}"
			                '/>
					    	</td>
					    </tr>
					     <tr>
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>市场价：</div>
                            <td width="250">
					    	<input id="bargainGoodsPrice" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainGoodsPrice",
									value:"${BargainGoods?.bargainGoodsPrice}"
			                '/>
					    	</td>
                          
                            <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>折扣：</div>
                            <td width="250">
					    	<input id="bargainGoodsDiscount" data-dojo-type="dijit/form/ValidationTextBox" 
			                 	data-dojo-props='trim:true,required:true,name:"bargainGoodsDiscount",
									value:"${BargainGoods?.bargainGoodsDiscount}"
			                '/>
					    	</td>
					    </tr>
					     
						<tr>
					    <td width="120"><div align="right"><span style="color:red">*&nbsp;</span>备注：</div></td>
					    <td colspan=3>
					    	<textarea id="bargainGoodsRemark" data-dojo-type="dijit/form/SimpleTextarea" 
    							data-dojo-props='name:"bargainGoodsRemark","class":"input",
                               		style:{width:"550px"},rows:"2",
                               		trim:true,value:"${BargainGoods?.bargainGoodsRemark}"
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
