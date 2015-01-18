<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
	
</style>
<script type="text/javascript">	

</script>
</head>
<body>
	<div class="searchtab">
      <table width="100%" border="0">
        
        <tbody>
          <tr>
            <th width="8%">材料名称</th>
            <td width="12%">
            	<input id="s_materialInforName" data-dojo-type="dijit/form/ValidationTextBox" 
                	data-dojo-props='trim:true
               '/>
            </td>
            <th width="8%">材料类型</th>
            <td width="12%">
               <div id="s_materialType" data-dojo-type="dijit/form/ComboBox"
	                data-dojo-props='trim:true,value:"${mateTypeName }" <g:if test="${mateTypeName }">,readOnly:true</g:if>
	            '>
					<g:each in="${mateTypeList}" var="item">
						<option value="${item.matTypeName}">${item.matTypeName}</option>
					</g:each>
	            </div>
            </td>
           	<th width="8%">&nbsp;</th>
            <td width="12%">&nbsp;
            	
            </td>
            <td width="40%">
            	<div class="btn">
                	<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){materialInfor_search()}'>查询</button>
                	<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){materialInfor_resetSearch()}'>重置条件</button>
              	</div>
           </td>
          </tr>
          
        </tbody>
      </table>
    </div>
	
</body>
</html>
