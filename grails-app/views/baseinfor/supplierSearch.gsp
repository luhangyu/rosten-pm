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
            <th width="8%">供应商名称</th>
            <td width="18%">
            	<input id="s_suppName" data-dojo-type="dijit/form/ValidationTextBox" 
                	data-dojo-props='trim:true
               '/>
            </td>
            <th width="8%">供应商类型</th>
            <td width="18%">
               <div id="s_suppType" data-dojo-type="dijit/form/ComboBox"
	                data-dojo-props='trim:true,value:""
	            '>
					<g:each in="${suppliers}" var="item">
						<option value="${item.code}">${item.name}</option>
					</g:each>
	            </div>
            </td>
            <td>
            	<div class="btn">
                	<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){supplier_search()}'>查询</button>
                	<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){supplier_resetSearch()}'>重置条件</button>
              	</div>
           </td>
          </tr>
          
        </tbody>
      </table>
    </div>
	
</body>
</html>
