//将指定样式的td改为可编辑的
function resetTds(tableId,td_styleId){
    var numId = $("#"+tableId+" td[styleId='"+td_styleId+"']"); 
    numId.click(function(){ //可编辑td的鼠标点击事件
        var tdIns = $(this); //td对象
        if ( tdIns.children("input").length>0 ){ return false; } //若已经有输入框了，则不做任何反应      

        var inputIns = $("<input type='text'/>"); //需要插入的输入框
        inputIns.width(tdIns.width()); //设置input与td宽度一致 
        
        var beforeValue = tdIns.html();
        if(!checkDigst(beforeValue)){//预防一些格式不对的数据。
        	beforeValue='';
        }
        inputIns.val(beforeValue); //将本来单元格td内容copy到插入的文本框input中 
        tdIns.html(""); //删除原来单元格td内容 
        inputIns.appendTo(tdIns).focus().select(); //将需要插入的输入框代码插入dom节点中 

		/**
        inputIns.mouseout( //输入框鼠标离开事件
			function(event){ 
				if(checkDigst($(this).val())==false){
					alert('请填入数值！');
					this.focus().select();
				}
				tdIns.html($(this).val());
			}
        ); 
        **/
        inputIns.blur( //失去焦点事件
			function(event){ 
				if($(this).val().length==0){
					alert('请填入非空数值！');
					this.focus().select();
					return;
				}
				if(isDigst152($(this).val())==false){
					alert('请填入数值！');
					this.focus().select();
					return;
				}
				tdIns.html($(this).val());
			}
        );
    }); 
}
/**
	检查是否是数值
*/
function checkDigst(value) {
		return /^\d*(\.\d+)?$/.test(value);
};

/**
  测试是否是15.2型的数字
*/
function isDigst152(str){
   if(str.indexOf(".")==-1){
      if(str.length>13){
         alert(str+"  整数部分不得超过"+13+"位");
         return false;
      }else if(!/^(\d{1,13})$/.test(str)){     
  	     alert(str+" 不是数字.");
         return false;
      }
   }else if(!/^(\d{1,13})(\.\d{0,2})$/.test(str)){     
      alert(str+"整数部分不得超过"+13+"位，小数部分不得超过"+2+"位");
      return false;
   }
   return true; 	
}