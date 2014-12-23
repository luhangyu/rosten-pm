<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>材料类型</title>
	<script type="text/javascript">
		require([
				"dojo/_base/kernel",
				"dijit/registry",
				"dojo/_base/connect",
				"dijit/Menu",
				"dijit/MenuItem",
				"dojo/data/ItemFileReadStore",
				"dijit/Tree",
				"dijit/tree/ForestStoreModel",
				"dijit/layout/BorderContainer",
				"dojox/layout/ContentPane",
				"dijit/form/SimpleTextarea",
				"dijit/form/Button"
			], function(kernel,registry,connect,Menu, MenuItem,ItemFileReadStore,Tree,ForestStoreModel){
			
			var obj_treenode;
			treeOnLoad = function(){
				var menu = registry.byId("obj_tree_menu");
				var tree = registry.byId("obj_tree");
				menu.bindDomNode(tree.domNode);

				connect.connect(menu,"_openMyself",tree,function(e){
					obj_treenode = registry.getEnclosingWidget(e.target);
				});
			}
			kernel.addOnLoad(function(){
				if(registry.byId("obj_tree_menu")) return;
				var menu = new Menu({
					id: 'obj_tree_menu',
					selector: ".dijitTreeNode"
				});
				menu.addChild(new MenuItem({
					label: "新建类型",
					disabled:false,
					iconClass:'docCreateIcon',
					onClick:function() {createSubObj(obj_treenode)}
				}));
				menu.addChild(new MenuItem({
					label: "编辑类型",
					iconClass:"docOpenIcon",
					disabled:false,
					onClick:function(){editSubObj(obj_treenode)}
				}));
				menu.addChild(new MenuItem({
					label: "删除类型",
					iconClass:"docDeleteIcon",
					disabled:false,
					onClick:function(){deleteSubObj(obj_treenode)}
				}));
				
			});
			createSubObj = function(selectedTreeNode){
				var w = registry.byId("objEditPane");
				var href = "${createLink(controller:'baseinfor',action:'matTypeCreate')}";
				href = href + "?companyId=${company?.id}";
				if(!obj_treenode.item.root){
					href = href + "&parentId="+obj_treenode.item.id;
				}
				w.attr("href",href);
			}
			editSubObj = function(selectedTreeNode){
				if(!selectedTreeNode.item.root){
					var w = registry.byId("objEditPane");
					var tree = registry.byId("obj_tree");
					
					if(tree.model==null) var store = tree.store;
					else store = tree.model.store;
					
					var href = "${createLink(controller:'baseinfor',action:'matTypeShow')}";
					var href = href+"/"+selectedTreeNode.item.id;
					w.attr("href",href);
				}
			}
			deleteSubObj = function(selectedTreeNode){
				var w = registry.byId("objEditPane");
				var tree = registry.byId("obj_tree");
				if(tree.model==null) var store = tree.store;
				else store = tree.model.store;
							
				rosten.confirm("您是否将删除所选中的节点？").callback = function(){
					var href = "${createLink(controller:'baseinfor',action:'matTypeDelete')}";
					if(!selectedTreeNode.item.root){
						href = href + "/"+selectedTreeNode.item.id;
						w.attr("href",href);
					}
				}
			}
			refreshObjTree = function(){
				var tree = registry.byId("obj_tree");
				if(tree){
					var store = new ItemFileReadStore({url:"${createLink(controller:'baseinfor',action:'matTypeTreeDataStore',params:[companyId:company?.id])}"});
					tree.destroy();
					var div = document.createElement("div");
					var treeModel = new ForestStoreModel({ 
				    	store: store, // the data store that this model connects to 
				    	query: {parentId:null}, // filter multiple top level items 
				    	rootLabel: "材料类型", 
				    	childrenAttrs: ["children"] // children attributes used in data store. 
					}); 
					var tree = new Tree({
						id:"obj_tree",
						model: treeModel,
						onClick:function(item){
							if(item && !item.root){
								var w = registry.byId("objEditPane");
								var href = "${createLink(controller:'baseinfor',action:'matTypeShow')}";
								var href = href+"/"+item.id;
								w.attr("href",href);
							}
						},
						onLoad:treeOnLoad,
						autoExpand:true,
						showRoot:true,
						openOnClick:false,openOnDblClick:true},div);
					var p = registry.byId("objTreePane");
					p.domNode.appendChild(tree.domNode);
				}
			}
			getItem = function(){
				var tree = registry.byId('obj_tree');
				if(tree.selectedItem){
					if(tree.selectedItem != tree.model.root){
						console.log(tree.selectedItem.name);
					}else{
						console.log("root 节点....");
					}
				}else{
					rosten.alert("请选择节点");
				}
				
			};
		});
	</script>
</head>
<body>
	<g:set var="dataurl" scope="page"> ${createLink(controller:'baseinfor',action:'matTypeTreeDataStore',params:[companyId:company?.id])}</g:set>
	<div data-dojo-id="treeDataStore" data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-props='url:"${dataurl}"'></div>

	<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props='style:"height:100%;padding:0"'>
		
		<div id="objTreePane" data-dojo-type="dojox/layout/ContentPane" data-dojo-props="region:'leading',splitter:true,style:'width:260px'">
			<div id="obj_tree" data-dojo-type="dijit.Tree" data-dojo-props='store:treeDataStore, query:{parentId:null},
				showRoot:true,label: "材料类型",
				autoExpand:true, onLoad:function(){treeOnLoad()}'>
				<script type="dojo/method" data-dojo-event="onClick" data-dojo-args="item">
					if(item && !item.root){
						var w = dijit.byId("objEditPane");
						var href = "${createLink(controller:'baseinfor',action:'matTypeShow')}";
						var href = href+"/"+item.id;
						w.attr("href",href);
					}
				</script>
			</div>
		</div>
		<div id="objEditPane" data-dojo-type="dojox/layout/ContentPane" 
			data-dojo-props="style:'padding:0px',renderStyles:true,region:'center'">
		</div>
	</div>
</body>