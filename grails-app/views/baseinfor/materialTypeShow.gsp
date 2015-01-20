<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>材料类型</title>
</head>
<body>
<div style="width:700px;height:500px">
	<g:set var="dataurl" scope="page"> ${createLink(controller:'baseinfor',action:'matTypeTreeDataStore',params:[companyId:company?.id])}</g:set>
	<div data-dojo-id="treeDataStore" data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-props='url:"${dataurl}"'></div>

	<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props='style:"height:100%;padding:0;"'>
		
		<div id="objTreePane" data-dojo-type="dojox/layout/ContentPane" data-dojo-props="region:'leading',splitter:true,style:'width:160px'">
			<div id="obj_tree" data-dojo-type="dijit/Tree" data-dojo-props='store:treeDataStore, query:{parentId:null},
				showRoot:true,label: "材料类型",
				autoExpand:false, onLoad:function(){dialog_treeOnLoad()}'>
				<script type="dojo/method" data-dojo-event="onClick" data-dojo-args="item">
					var id = "all";
					if(item && !item.root){
						id=item.id;
					}
					dialog_showTreeRightPane(id);
				</script>
			</div>
		</div>
		<div id="objEditPane" data-dojo-type="dojox/layout/ContentPane" 
			data-dojo-props="style:'padding:0px',renderStyles:true,region:'center'">
		</div>
		<div data-dojo-type="dojox/layout/ContentPane" data-dojo-props="region:'bottom',style:{textAlign:'center'}">
		
			<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){dialog_submit()}'>确定</button>
			<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){rosten.hideCommonShowDialog("barGoodsNameSelect")}'>取消</button>
		</div>
	</div>
</div>
</body>