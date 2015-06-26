// 验证字符串长度
function validateLen(str, display, len) {
	if (str.length != len) {
	   display.innerHTML = "<font color='red'>长度必须为" + len + "</font>";
	   return false;
	} else {
	   return true;
	}
}

// 验证字符串是否为数字
function validateNumber(str, display) {
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (str.search(re) == -1) {
		display.innerHTML = "<font color='red'>必须输入数字</font>";
		return false;
	} else {
		return true;
	}
}

//验证是否为T+N(N为数字)的形式
function validateTPlusN(str, display) {
	var regu = "T\\u002B[0-9]{1}";
	var re = new RegExp(regu);
	if (str.search(re) == -1) {
		display.innerHTML = "<font color='red'>必须输入T+N,N为数字</font>";
		return false;
	} else {
		return true;
	}
}

//验证输入是否为指定字符
function validateSpecialCharacter(c,validStr,display){
	var valid=validStr.split(";");
	var input=c.split(",");
	var result=true;
	for(i=0;i<input.length;i++){
		var temp=false;
		for(j=0;j<valid.length;j++){
		   if(input[i]==valid[j]){
			  temp=true;
			  break;
		   }
		}
		if(!temp){
			result=false;
			break;
		}
	}
	var desc="";
	for(i=0;i<valid.length;i++){
		desc=desc+valid[i];
		if(i==(valid.length-1)){
			desc+=" ";
		}else{
			desc+=";";
		}
	}
	if(result==false){
		display.innerHTML = "<font color='red'>必须输入下列字符"+desc+"之一</font>";
	}
	return result;
}

//验证是否为空
function isStrEmpty(str,display){
	if(str==null||str==""){
		display.innerHTML = "<font color='red'>不能为空</font>";
		return false;
	}else{
		return true;
	}
}