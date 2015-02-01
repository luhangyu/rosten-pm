<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>正文</title>
    <meta name="layout" content="rosten" />
    
	<script type="text/javascript">
	require(["dojo/parser", "dojo/_base/kernel", "rosten/widget/ActionBar", "rosten/kernel/WebOffice","rosten/app/Application","rosten/kernel/behavior"], 
		function(parser, kernel) {
		kernel.addOnLoad(function() {
			rosten.init({webpath:"${request.getContextPath()}"});
			rosten.cssinit();

			var tmpWord = "${wordTemplate?wordTemplate:""}";
			//tmpWord = "${servletPath}wordTemp/test.doc";
			weboffice_notifyCtrlReady(tmpWord);
		});

		init_wordMark = function(){
			weboffice_setMarkValue("fileNo","${bargain?.bargainNo}");
			
		};
		page_quit = function(){
			weboffice_close();
			var parentWin = window.opener;
			window.close();
			parentWin.location.reload();
		};
		word_save = function(){
			var args =[];
			args.push({name:"filename",value:"${bargain?.bargainNo}.doc"});
			args.push({name:"type",value:"doc"});
			args.push({name:"id",value:"${bargain?.id}"});
			
			var returnValue = weboffice_uploadDoc(args,"${servletPath}bargain/addWordFile1");
			if("ok" == returnValue){
				//rosten.alert("保存成功！");
				page_quit();
			} else if("noReady"== returnValue){
				rosten.alert("word控件尚未准备好，请稍后再保存！");
			}else {
				rosten.alert("保存失败")
			}
		};
		word_menu = function(){
			var webOfficeId = "WebOffice1";
			if(!checkIsIE()){
				webOfficeId = "Control";
			}
			var webObj = document.getElementById(webOfficeId);
			if(rosten.variable.wordMenu == undefined || rosten.variable.wordMenu == true){
				//隐藏
				webObj.HideMenuArea("hideall","","","");
				rosten.variable.wordMenu = false;
			}else{
				//显示
				webObj.HideMenuArea("showmenu","","","");
				rosten.variable.wordMenu = true;
			}
		};
		
	});
    </script>
    <!-- 
    <SCRIPT language=javascript event=NotifyToolBarClick(iIndex) for=WebOffice1>
		weboffice_notifyToolBarClick(iIndex);
	</SCRIPT> -->
</head>
<body class="claro rosten">
	<div class="rosten_action">
		<div data-dojo-type="rosten/widget/ActionBar" data-dojo-props='actionBarSrc:"${createLink(controller:'bargainAction',action:'bargainWord',params:[userid:user?.id])}"'></div>
	</div>
	<div>
		<r:jsLoad dir="js/rosten/kernel" file="loadWebOffice.js" />
	</div>
</body>
</html>