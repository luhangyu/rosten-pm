define(function(){
var _1=function(){
var _2={};
var ua=navigator.userAgent.toLowerCase();
var s;
(s=ua.match(/rv:([\d.]+)\) like gecko/))?_2.ie=s[1]:(s=ua.match(/msie ([\d.]+)/))?_2.ie=s[1]:(s=ua.match(/firefox\/([\d.]+)/))?_2.firefox=s[1]:(s=ua.match(/chrome\/([\d.]+)/))?_2.chrome=s[1]:(s=ua.match(/opera.([\d.]+)/))?_2.opera=s[1]:(s=ua.match(/version\/([\d.]+).*safari/))?_2.safari=s[1]:0;
return _2.ie;
};
var _3={};
var _4="WebOffice1";
if(!_1()){
_4="Control";
}
var _5=document.getElementById(_4);
weboffice_close=function(){
try{
_5.Close();
}
catch(e){
}
};
weboffice_newDoc=function(_6){
try{
var _7="doc";
if(_6&&_6!=""){
_5.LoadOriginalFile(_6,_7);
}else{
_5.LoadOriginalFile("",_7);
}
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_uploadDoc=function(_8,_9){
try{
_5.OptionFlag=128;
_5.HttpInit();
_5.SetTrackRevisions(0);
for(var o in _8){
_5.HttpAddPostString(_8[o].name,_8[o].value);
}
_5.HttpAddPostCurrFile("wordFile","");
return _5.HttpPost(_9);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_docOpen=function(){
try{
_5.LoadOriginalFile("open","doc");
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_docSave=function(){
try{
_5.Save();
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_saveAsTo=function(){
try{
_5.ShowDialog(84);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_showPrintDialog=function(){
try{
_5.PrintDoc(1);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_print=function(){
try{
_5.PrintDoc(0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_notPrint=function(){
try{
_5.SetSecurity(1);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_okPrint=function(){
try{
_5.SetSecurity(1+32768);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_notSave=function(){
try{
_5.SetSecurity(2);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_okSave=function(){
try{
_5.SetSecurity(2+32768);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_notCopy=function(){
try{
_5.SetSecurity(4);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_okCopy=function(){
try{
_5.SetSecurity(4+32768);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_fullScreen=function(){
try{
_5.FullScreen=true;
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_unProtect=function(_a){
try{
_5.ProtectDoc(0,1,_a);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_protectFull=function(_b){
try{
_5.ProtectDoc(1,1,_b);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_addSeal=function(){
try{
var _c=_5.GetDocumentObject().Application.CommandBars("电子印章");
if(_c){
_c.Controls("盖章").Execute();
}
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_protectRevision=function(){
try{
_5.SetTrackRevisions(1);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_unShowRevisions=function(){
try{
_5.ShowRevisions(0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_uhowRevisions=function(){
try{
_5.ShowRevisions(1);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_exitRevisions=function(){
try{
_5.SetTrackRevisions(0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_acceptAllRevisions=function(){
try{
_5.SetTrackRevisions(4);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_unAcceptAllRevisions=function(){
try{
var _d=_5.GetRevCount();
var _e;
for(var i=1;i<=_d;i++){
_e=_5.GetRevInfo(i,0);
_5.AcceptRevision(_e,1);
}
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_getRevAllInfo=function(){
var _f;
_f=_5.GetRevCount();
var _10=0;
var _11;
for(var i=1;i<=_f;i++){
_10=_5.GetRevInfo(i,2);
if("1"==_10){
_10="插入";
}else{
if("2"==_10){
_10="删除";
}else{
_10="未知操作";
}
}
_11=new String(_5.GetRevInfo(i,1));
_11=parseFloat(_11);
dateObj=new Date(_11);
alert(dateObj.getYear()+"年"+dateObj.getMonth()+1+"月"+dateObj.getDate()+"日"+dateObj.getHours()+"时"+dateObj.getMinutes()+"分"+dateObj.getSeconds()+"秒");
alert("用户:"+_5.GetRevInfo(i,0)+"\r\n操作:"+_10+"\r\n内容:"+_5.GetRevInfo(i,3));
}
};
weboffice_setUserName=function(_12){
try{
_5.SetCurrUserName(_12);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_addBookmark=function(_13){
if(_13==undefined){
_5.SetFieldValue("rosten","加入书签{rosten}","::ADDMARK::");
}else{
_5.SetFieldValue(_13,"加入书签{"+_13+"}","::ADDMARK::");
}
};
weboffice_taohong=function(_14,_15){
if(_14==undefined){
_5.SetFieldValue("rosten","<a href='www.baidu.com' >Rosten信息技术有限公司</a>","");
}else{
_5.SetFieldValue(_14,_15,"");
}
};
weboffice_setMarkValue=function(_16,_17){
_5.SetFieldValue(_16,_17,"");
};
weboffice_fillBookMarks=function(_18){
try{
if(_18==undefined){
_5.BookMarkOpt("/system/wordTemplate",2);
}else{
_5.BookMarkOpt(_18,2);
}
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_linkRed=function(){
window.open("mark.html","newwindow","height=768, width=1024, top=0, left=0, toolbar=yes,resizable=yes, menubar=yes,location=yes, status=yes");
};
weboffice_toolBar_newDoc=function(_19){
try{
var _1a=_5.HideMenuItem(0);
if((_1a&1)&&!_19){
_5.HideMenuItem(1);
}else{
_5.HideMenuItem(1+32768);
}
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_toolBar_open=function(_1b){
try{
var _1c=_5.HideMenuItem(0);
if((_1c&2)&&!_1b){
_5.HideMenuItem(2);
}else{
_5.HideMenuItem(2+32768);
}
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_toolBar_save=function(_1d){
try{
var _1e=_5.HideMenuItem(0);
if((_1e&4)&&!_1d){
_5.HideMenuItem(4);
}else{
_5.HideMenuItem(4+32768);
}
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_toolBar=function(_1f){
try{
_5.ShowToolBar=_1f;
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_hideFileMenu=function(){
try{
_5.SetToolBarButton2("Menu Bar",1,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_showFileMenu=function(){
try{
_5.SetToolBarButton2("Menu Bar",1,4);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_hideEditMenu=function(){
try{
_5.SetToolBarButton2("Menu Bar",2,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_showEditMenu=function(){
try{
_5.SetToolBarButton2("Menu Bar",2,4);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_hideNewItem=function(){
try{
_5.SetToolBarButton2("Standard",1,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_showNewItem=function(){
try{
_5.SetToolBarButton2("Standard",1,4);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_hideOpenItem=function(){
try{
_5.SetToolBarButton2("Standard",2,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_showOpenItem=function(){
try{
_5.SetToolBarButton2("Standard",2,4);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_hideSaveItem=function(){
try{
_5.SetToolBarButton2("Standard",1,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_showSaveItem=function(){
try{
_5.SetToolBarButton2("Standard",1,4);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_beginMenu=function(){
try{
_5.HideMenuAction(1,1048576);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_insertMenu=function(){
try{
_5.HideMenuAction(1,2097152);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_pageMenu=function(){
try{
_5.HideMenuAction(1,4194304);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_adducMenu=function(){
try{
_5.HideMenuAction(1,8388608);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_emailMenu=function(){
try{
_5.HideMenuAction(1,16777216);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_checkMenu=function(){
try{
_5.HideMenuAction(1,33554432);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_viewMenu=function(){
try{
_5.HideMenuAction(1,67108864);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_empolderMenu=function(){
try{
_5.HideMenuAction(1,134217728);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_loadMenu=function(){
try{
_5.HideMenuAction(1,268435456);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_allHideMenu=function(){
try{
_5.HideMenuAction(1,1048576+2097152+4194304+8388608+16777216+33554432+67108864+134217728+268435456);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_nullityCopy=function(){
try{
_5.HideMenuAction(1,8192);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_nullityAffix=function(){
try{
_5.HideMenuAction(1,4096);
_5.HideMenuAction(5,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_affixCopy=function(){
try{
_5.HideMenuAction(6,0);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_hideSeal=function(){
var obj;
try{
obj=new Object(_5.GetDocumentObject());
if(obj!=null){
obj.Application.CommandBars("电子印章").Visible=!obj.CommandBars("电子印章").Visible;
}
delete obj;
}
catch(e){
alert("隐藏显示印章工具栏出错");
}
};
weboffice_write2=function(){
var _20;
try{
_20=new Object(_5.GetDocumentObject());
if(_20!=null){
_20.Application.CommandBars("电子印章").Controls("盖章").Execute();
}
delete _20;
}
catch(e){
alert("盖章出错");
}
};
weboffice_notMenu=function(){
try{
_5.SetToolBarButton2("Menu Bar",1,8);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_okMenu=function(){
try{
_5.SetToolBarButton2("Menu Bar",1,11);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_notOfter=function(){
try{
_5.SetToolBarButton2("Standard",1,8);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_okOfter=function(){
try{
_5.SetToolBarButton2("Standard",1,11);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_notFormat=function(){
try{
_5.SetToolBarButton2("Formatting",1,8);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_okFormat=function(){
try{
_5.SetToolBarButton2("Formatting",1,11);
}
catch(e){
alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);
}
};
weboffice_hideAll=function(_21,_22,_23,_24){
_5.HideMenuArea(_21,_22,_23,_24);
};
weboffice_notifyCtrlReady=function(_25){
_5.SetWindowText("Rosten恒传技术",0);
_5.OptionFlag|=128;
weboffice_newDoc(_25);
_5.ShowToolBar=false;
_5.HideMenuArea("hideall","","","");
rosten.variable.wordMenu=false;
};
weboffice_saveBinaryFileFromBase64=function(){
var _26=_5.GetTempFilePath();
var v=_5.GetFileBase64("",0);
_5.SaveBinaryFileFromBase64(_26,v);
};
weboffice_setCustomToolBtn=function(_27,_28){
_5.SetCustomToolBtn(_27,_28);
};
weboffice_notifyToolBarClick=function(_29){
};
return _3;
});

