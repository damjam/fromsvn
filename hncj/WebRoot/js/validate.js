// ��֤�ַ�������
function validateLen(str, display, len) {
	if (str.length != len) {
	   display.innerHTML = "<font color='red'>���ȱ���Ϊ" + len + "</font>";
	   return false;
	} else {
	   return true;
	}
}

// ��֤�ַ����Ƿ�Ϊ����
function validateNumber(str, display) {
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (str.search(re) == -1) {
		display.innerHTML = "<font color='red'>������������</font>";
		return false;
	} else {
		return true;
	}
}

//��֤�Ƿ�ΪT+N(NΪ����)����ʽ
function validateTPlusN(str, display) {
	var regu = "T\\u002B[0-9]{1}";
	var re = new RegExp(regu);
	if (str.search(re) == -1) {
		display.innerHTML = "<font color='red'>��������T+N,NΪ����</font>";
		return false;
	} else {
		return true;
	}
}

//��֤�����Ƿ�Ϊָ���ַ�
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
		display.innerHTML = "<font color='red'>�������������ַ�"+desc+"֮һ</font>";
	}
	return result;
}

//��֤�Ƿ�Ϊ��
function isStrEmpty(str,display){
	if(str==null||str==""){
		display.innerHTML = "<font color='red'>����Ϊ��</font>";
		return false;
	}else{
		return true;
	}
}