define(["dojo/_base/declare","dojo/_base/lang","dojo/_base/xhr","dojo/query","dojo/dom-style","dojo/_base/kernel","dojo/_base/connect","dijit/registry","dijit/form/CheckBox","dijit/form/TextBox","dijit/form/Button","dojo/data/ItemFileWriteStore","rosten/widget/_Dialog","rosten/util/general"],function(_1,_2,_3,_4,_5,_6,_7,_8,_9,_a,_b,_c,_d,_e){
return _1("rosten.widget.MultiSelectDialog",[_d],{height:"370px",width:"350px",title:"Rosten_选择框",chkboxwidgets:[],defaultvalues:[],datasrc:"",canappend:false,extra_choice:null,single:false,choicelist:[],choicelist_node:null,general:new _e(),chkboxStore:null,constructor:function(_f){
this.chkboxwidgets=[];
if(this.initialized){
var _10=_4(".dijitCheckBox",this._dialog.domNode);
for(var i=0;i<_10.length;i++){
this.chkboxwidgets.push(_8.getEnclosingWidget(_10[i]));
}
}
},buildContent:function(_11){
var _12=document.createElement("fieldset");
var _13="290px";
var h1="255px";
if(this.canappend){
_13="240px";
h1="220px";
}
_5.set(_12,{"border":"1px solid gray","margin":"2px","padding":"3px","fontSize":"12px","height":_13});
_11.appendChild(_12);
var _14=document.createElement("legend");
_5.set(_14,{color:"blue"});
_14.innerHTML="请选择以下选项：";
_12.appendChild(_14);
this.choicelist_node=document.createElement("div");
this.choicelist_node.setAttribute("id","choicelist");
_5.set(this.choicelist_node,{"height":h1,"overflow":"auto"});
_12.appendChild(this.choicelist_node);
if(this.datasrc==""){
this._render(_11);
return;
}
var _15={url:this.datasrc,handleAs:"json",timeout:2000,preventCache:true,sync:true,load:_2.hitch(this,function(_16,_17){
this.choicelist=[];
for(var i=0;i<_16.length;i++){
this.choicelist.push(_16[i].name+"|"+_16[i].id);
}
this._setStoreData(_16);
this._render(_11);
}),error:_2.hitch(this,function(_18,_19){
console.debug(_18);
})};
_3.get(_15);
},_setStoreData:function(_1a){
var _1b={"identifier":"id","label":"name","items":_1a};
this.chkboxStore=new _c({data:_1b});
},getStoreDate:function(_1c,_1d){
if(this.chkboxStore){
this.chkboxStore.fetchItemByIdentity({identity:_1c,onItem:_1d});
}
},_render:function(_1e){
var _1f=this.chkboxwidgets;
var _20=this;
_6.forEach(this.choicelist,function(_21){
if(_21.indexOf("|")!=-1){
var arr=_21.split("|");
var _22="picklist_"+arr[1];
var _23=arr[0];
}else{
var _22="picklist_"+_21;
var _23=_21;
}
if(_8.byId(_22)){
_8.byId(_22).destroy();
}
var _24=document.createElement("div");
_5.set(_24,"height","20px");
var _25={id:_22,name:_23};
var _26=new _9(_25,document.createElement("div"));
if(_20.single==true){
_7.connect(_26,"onClick",_20,"choiceCheck");
}
_24.appendChild(_26.domNode);
var _27=document.createElement("label");
_27.innerHTML=_23;
_27.setAttribute("for",_22);
_24.appendChild(_27);
_20.choicelist_node.appendChild(_24);
_1f.push(_26);
});
for(var i=0;i<this.defaultvalues.length;i++){
var dv=this.defaultvalues[i];
for(var j=0;j<this.chkboxwidgets.length;j++){
var w=this.chkboxwidgets[j];
if(w.attr("name")==dv){
w.attr("value","on");
}
}
}
if(this.canappend){
var _28=document.createElement("fieldset");
_5.set(_28,{"border":"1px solid gray","margin":"2px","padding":"3px","fontSize":"12px","height":"40px"});
_1e.appendChild(_28);
var _29=document.createElement("legend");
_5.set(_29,{color:"blue"});
_29.innerHTML="其他选项：";
_28.appendChild(_29);
var _2a=document.createElement("span");
_2a.innerHTML="新值：";
_28.appendChild(_2a);
var _2b=document.createElement("div");
_5.set(_2b,{"fontSize":"12px",height:"20px",width:"220px"});
_28.appendChild(_2b);
this.extra_choice=new _a({style:{height:"18px",width:"200px"}},_2b);
var w=this.extra_choice;
_7.connect(w,"onKeyPress",this,"onKeyPress");
var _2c=document.createElement("div");
_28.appendChild(_2c);
var _2d=new _b({label:"添加"},_2c);
_7.connect(_2d,"onClick",this,"appendChoice");
}
},initCheckData:function(_2e){
_6.forEach(this.chkboxwidgets,function(w){
var _2f=w.attr("name");
if(this.general.isInArray(_2e,_2f)){
w.attr("value",true);
}
});
},getData:function(){
var _30=[];
_6.forEach(this.chkboxwidgets,function(w){
if(w.attr("value")!=false){
var _31={};
var tmp=w.attr("id");
var _32=w.attr("name");
_31.name=_32;
if(tmp.indexOf("picklist_")!=-1){
_31.id=tmp.split("picklist_")[1];
_30.push(_31);
}else{
_31.id=tmp;
_30.push(_31);
}
}
});
return _30;
},appendChoice:function(){
var _33=this.extra_choice.attr("value");
if(_2.trim(_33)==""){
this.extra_choice.attr("value","");
return;
}
var _34=_4("#choicelist",this._dialog.domNode)[0];
var _35=document.createElement("div");
_5.set(_35,"height","20px");
if(_33.indexOf("|")!=-1){
var arr=_33.split("|");
var _36="picklist_"+arr[1];
var _37=arr[0];
}else{
var _36="picklist_"+_33;
var _37=_33;
}
var _38={id:_36,name:_37};
var _39=new _9(_38,document.createElement("div"));
if(this.single){
_7.connect(_39,"onClick",this,"choiceCheck");
for(var k=0;k<this.chkboxwidgets.length;k++){
this.chkboxwidgets[k].attr("value",false);
}
}
_39.attr("value","on");
_35.appendChild(_39.domNode);
var _3a=document.createElement("label");
_3a.innerHTML=_37;
_3a.setAttribute("for",_36);
_35.appendChild(_3a);
_34.appendChild(_35);
_34.scrollTop=_34.scrollHeight;
this.chkboxwidgets.push(_39);
this.extra_choice.attr("value","");
},onKeyPress:function(e){
if(e.keyCode=="13"){
this.appendChoice();
}
},choiceCheck:function(e){
var w=_8.getEnclosingWidget(e.target);
if(w.attr("value")=="on"){
for(var i=0;i<this.chkboxwidgets.length;i++){
if(this.chkboxwidgets[i]!=w){
if(this.chkboxwidgets[i].attr("value")=="on"){
this.chkboxwidgets[i].attr("value",false);
}
}
}
}
},refresh:function(){
this.contentPane.innerHTML="";
this.chkboxwidgets=[];
if(this.initialized){
var _3b=_4(".dijitCheckBox",this._dialog.domNode);
for(var i=0;i<_3b.length;i++){
this.chkboxwidgets.push(_8.getEnclosingWidget(_3b[i]));
}
}
this.buildContent(this.contentPane);
},simpleRefresh:function(){
this.choicelist_node.innerHTML="";
this.chkboxwidgets=[];
if(this.initialized){
var _3c=_4(".dijitCheckBox",this._dialog.domNode);
for(var i=0;i<_3c.length;i++){
this.chkboxwidgets.push(_8.getEnclosingWidget(_3c[i]));
}
}
this._render(this.contentPane);
}});
});

