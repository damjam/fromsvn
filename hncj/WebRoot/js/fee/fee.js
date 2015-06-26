//打开一个窗口
function openwindow(url){
	var feathers = "dialogTop=100px;dialogLeft=100px;dialogHeight=500px;dialogWidth=800px;scroll=yes;status=no;resizable=yes";
	window.showModalDialog(url, window, feathers);
}

//打开一个窗口:附带参数
function openwindowWithConditon(url,condtion){
	var feathers = "dialogTop=100px;dialogLeft=100px;dialogHeight=500px;dialogWidth=800px;scroll=yes;status=no;resizable=yes";
	var realUrl = url+"&static_condition="+condtion;
	window.showModalDialog(realUrl, window, feathers);
}

/**
*打开一个窗口:附带参数+ 数据过滤条件
*url:请求地址
*condtion：静态条件
*dataPri：数据条件：dataPri=type1:propName1;type2:propName2;
*/
function openwindowWithConditonAndPriv(url,condtion,dataPri){
	var feathers = "dialogTop=100px;dialogLeft=100px;dialogHeight=500px;dialogWidth=800px;scroll=yes;status=no;resizable=yes";
	var realUrl = url+"&static_condition="+condtion+"&dataPri="+dataPri;
	window.showModalDialog(realUrl, window, feathers);
}

//--------以下是计费参数设置的相关的函数---可编辑的td的样式固定为为edit-------------------------------------------------------------
//获取单选框的值,默认为false
function getRadioValue(radioName){
	var item = $("[name='"+radioName+"']:checked");//$(":radio:checked"); 
	var len=item.length; 
	if(len>0){ 
		var str = $(":radio:checked").val();
		var value = eval(str);
  		return value;
	} 
	return false;
}

//加载详细参数设置页面
function loadConfigDiv(){
	var pageName = getConfigPageName();//该方法需要具体的页面实现
	var configParam = getConfigParamFromPage();//该方法需要具体的页面实现
	$('#param_config_div').load(pageName,{},function(){initConfigParam(configParam);});
}

//初始化分区信息.sectionStr:初始值,不带参数名；tableName 分区对应的tableid
function initSection(sectionStr,tableName){
	var parentTable =$("#"+tableName);
	var	values = sectionStr.split(';');
	for(var i=0;i<values.length;i++){
		var strs = values[i].split(',');
		if(strs.length===2){
			var newLine ="<tr><td styleId='edit' width='100' align='center'>"+strs[0]
					+"</td><td styleId='edit' width='100' align='center'>"+strs[1]
					+"</td><td width='50' align='center'><input type='button' onClick='removeLine(this)' value='删除'/></td></tr>";
			parentTable.append(newLine);
		}
	}
	resetTds(tableName,"edit");
}
//获取分区设置的参数值 tableName：分区对应的table的名字
function getSectionValue(tableName){
	var resultStr="";
	$("#"+tableName+" tr:gt(0)").each(function(){
                var limitSetction = $(this).find("td").eq(0).html();//分段上限
                var fee = $(this).find("td").eq(1).html();//金额
                if(limitSetction.length>0 && fee.length>0){
                	resultStr = resultStr+";"+limitSetction+","+fee;
                }
            });
     return resultStr;
}
//删除初始化分区表格的一行
function removeLine(obj){
	$(obj).parent().parent().remove();
}

//增加行
function add_Tr(tableId){
	var parentTable =$("#"+tableId);
	var newLine ="<tr><td styleId='edit' width='100' align='center'>&nbsp;</td><td styleId='edit' width='100' align='center'>&nbsp;</td><td width='50' align='center'><input type='button' onClick='removeLine(this)' value='删除'/></td></tr>";
	parentTable.append(newLine);
	resetTds(tableId,"edit");
}

//--自动填充所有的输入框--------------------------------------------------------
//str：参数值；keys：单值参数数组；sectionKeys多值参数数组；tables：多值参数对应的table
function initAll(str,keys,sectionKeys,tables){
	//将所有的参数放入到Map中
	var params = str.split("&");
	var map = new Map(); 
	$.each(params,function(n,value) { 
		 var ss = value.split('=');
		 if(ss.length==2){map.put(ss[0],ss[1]);}
    }); 
    
    //遍历所有的单值参数
    $.each(keys,function(n,key) { 
    	 if(map.get(key)!=null){
    	 	fillInput(key,map.get(key));
    	 }
    }); 
    
    //遍历所有的多值参数
    $.each(sectionKeys,function(n,section) { 
    	 var valueOfSection = map.get(section);
    	 if(valueOfSection!=null){
    	 	initSection(valueOfSection,tables[n]);
    	 }
    });     
}
//将值填入相应的输入框
function fillInput(key,value){
	$("#"+key).val(value);
}



//-----自定义的map--------------------------------------
function struct(key, value) {   
   this.key = key;   
   this.value = value;   
 }   
    
 function put(key, value){   
   for (var i = 0; i < this.arr.length; i++) {   
     if ( this.arr[i].key === key ) {   
       this.arr[i].value = value;   
       return;   
     }   
   }   
   this.arr[this.arr.length] = new struct(key, value);   
 }   
    
 function get(key) {   
   for (var i = 0; i < this.arr.length; i++) {   
     if ( this.arr[i].key === key ) {   
       return this.arr[i].value;   
     }   
   }   
   return null;   
 }   
    
 function remove(key) {   
   var v;   
   for (var i = 0; i < this.arr.length; i++) {   
     v = this.arr.pop();   
     if ( v.key === key ) {   
       continue;   
     }   
     this.arr.unshift(v);   
   }   
 }   
    
 function size() {   
   return this.arr.length;   
 }   
    
 function isEmpty() {   
   return this.arr.length <= 0;   
 }   
    
 function Map() {   
   this.arr = new Array();   
   this.get = get;   
   this.put = put;   
   this.remove = remove;   
   this.size = size;   
   this.isEmpty = isEmpty;   
 }  
