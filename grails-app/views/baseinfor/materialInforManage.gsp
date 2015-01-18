<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>列表</title>
    <script type="text/javascript">
    </script>
</head>
<body>
	<div data-dojo-type="rosten/widget/ActionBar" id="rosten_actionBar" 
		data-dojo-props='actionBarSrc:"${createLink(controller:'baseinforAction',action:'materialInfoManageView')}"'></div>
	
	<div data-dojo-type="rosten/widget/RostenContentPane"
		data-dojo-props='src:"${createLink(controller:'baseinfor',action:'materialInforSearchView')}",style:{padding:"1px"}'></div>
	
	<div data-dojo-type="rosten/widget/RostenGrid" id="metInfor_rostenGrid" data-dojo-id="metInfor_rostenGrid"
		data-dojo-props='url:"${createLink(controller:'baseinfor',action:'materialInfoGrid',params:[searchId:searchId])}",showRowSelector:"new"'></div>
</body>
</html>