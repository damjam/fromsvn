var ZERO = 0.0001;
var LOAD_IMAGE = "<img src='" + CONTEXT_PATH
		+ "/images/ajax_loading.gif' style='margin-left:20px;'/>";
var SAVE_IMAGE = "<img src='" + CONTEXT_PATH + "/images/ajax_saving.gif'/>";

var _tracer = null;
var _times = 0;
var _MAX_TIMES = 10; // ϵͳ����
var _INTERVAL = 2000; // ϵͳ����

var _INVALID_CHAR = "'<>/&";

/**
 * �Ƿ�����Ч�ַ�.
 */
function containsInvalid(s) {
	if (s == null) {
		return false;
	}

	for (var i = 0; i < s.length; i++) {
		if (_INVALID_CHAR.indexOf(s.charAt(i)) > -1) {
			return true;
		}
	}

	return false;
}

/**
 * ��ʱִ�в�ѯ���
 * 
 */
function startTracker(func) {
	_tracer = setInterval(func, _INTERVAL);
	
	return _tracer;
}

/**
 * ��ʾ�ɹ���ͼƬ
 *
 */
function showRightImg(targetId){
	$('#' + targetId).html('<img src="' + CONTEXT_PATH + '/images/pages/right.gif" height="12" width="16" vspace="4"/>');
}

/**
 * ��ʾ�����ͼƬ
 *
 */
function showFaultImg(targetId){
	$('#' + targetId).html('<img src="' + CONTEXT_PATH + '/images/pages/fault.gif"  height="22" width="17" vspace="0"/>');
}

/**
 * �õ���ǰҳ����֤���
 * 
 */
function isValidate(){
	var bValidate = true;
	$('div[id^="resultImg_"]').each(function(){
		if ($(this).html().indexOf('fault.gif') != -1){
			bValidate = false;
		}
	});
	return bValidate;
}

/**
 * ��token.
 */
YToken = {
	/**
	 * ���������.
	 */
	callId: function() {
		var random = Math.floor(Math.random() * 10001);
	  	return (random + "_" + new Date().getTime()).toString();
	},
	
	/**
	 * �ڱ��ڲ����������token.
	 */
	create: function(jqueryForm) {
		if (jqueryForm.find('input[name="TOKEN-KEY"]').length == 0) {
			jqueryForm.append('<input type="hidden" name="TOKEN-KEY">');
		}
		
		jqueryForm.find('input[name="TOKEN-KEY"]').val(YToken.callId());
	},
	
	/**
	 * ��ȡ���ڲ������token.
	 */
	get: function(jqueryForm) {
		return jqueryForm.find('input[name="TOKEN-KEY"]').val();
	},
	
	/**
	 * ��ձ��ڲ������token.
	 */
	clear: function(jqueryForm) {
		jqueryForm.find('input[name="TOKEN-KEY"]').val('');
	}
}

/**
 * ҳ���Ƿ��з��ؽ��
 */
function hasResult(){
	var result = $('input[name="result"]').val();
	if (result == null || result == "" || result == undefined){
		return false;
	}
	return true;
}

/**
 * �õ�������Ϣ
 */
function getMessage(){
	return $('input[name="message"]').val();
}

/**
 * �õ����ؽ��
 */
function getResult(){
	return $('input[name="result"]').val();
}

/**
 * ��ʾTIP
 * @param result �ɹ� OR ʧ��
 * @param message ���ؽ��
 * @param isHide �ɹ�����Ϣ�Ƿ�����
 * @param isReset �Ƿ�����ҳ���ϵı�
 */
function showTip(result, message, isHide, isReset){
	if (isHide == undefined || isHide == null) {
		isHide = true;
	}
	if (isReset == undefined || isReset == null) {
		isReset = true;
	}
	if (result == "true"){
		$('#tip').slideDown("slow").html('<img src="' + CONTEXT_PATH + '/images/pages/success.gif" align="absmiddle">&nbsp;' + message);
		if (isReset){
			reset();
		}
		if (isHide){
			setTimeout(hideTip, 3000);
		}
	} else if (result == "false") {
		$('#tip').slideDown("slow").html('<img src="' + CONTEXT_PATH + '/images/pages/fault.gif" align="absmiddle">&nbsp;' + message);
	} else if (result == "loading") {
		$('#tip').slideDown("slow").html('<img src="' + CONTEXT_PATH + '/images/pages/loading.gif" align="absmiddle">&nbsp;' + message);
	}
}

/**
 * ����TIP
 */
function hideTip(){
	$('#tip').slideUp("slow");
}

/**
 * �����֤, �����Ƿ���Ч��ʾ��ʾ��Ϣ.
 * @tgt jquery����.
 */
function finishValidate(tgt, isValid) {
	var tipSpan = tgt.find('+ span');

	if (tipSpan.length == 0) {
		return;
	}
	
	if (isValid) {
		tipSpan.addClass('field-tipinfo');
		tipSpan.removeClass('error-input');
	}
	else {
		tipSpan.addClass('error-input');
		tipSpan.removeClass('field-tipinfo');
	}
}

FormUtils = {
	/**
	 * ȫѡȫ�帴ѡ��
	 */
	selectAll: function(target, checkboxName) {
		$("input[type='checkbox'][name='"+checkboxName+"']:checked").each(function() {
			$(this).attr('checked', target.checked);
		});
	},
	
	/**
	 * ��ȡ��ѡ��ѡ�е�ֵ. ��","�ָ�.
	 */
	getCheckedValues: function(checkboxName) {
		return $("input[type='checkbox'][name='"+checkboxName+"']:checked").map(function(){
	  		return $(this).val();
		}).get().join(",");
	},
	
	/**
	 * ��ȡ��ѡ��ѡ�е�ֵ. ������ʽ.
	 */
	getCheckedArrayValues: function(checkboxName) {
		return $("input[type='checkbox'][name='"+checkboxName+"']:checked").map(function(){
	  		return $(this).val();
		}).get();
	},
	
	/**
	 * �Ƿ���ѡ�и�ѡ��
	 */
	hasSelected: function(checkboxName) {
		return $("input[type='checkbox'][name='"+checkboxName+"']:checked").length > 0;
	},
	
	/**
	 * ѡ�и�ѡ��ĸ���.
	 */
	getSelectedCount: function(checkboxName) {
		return $("input[type='checkbox'][name='"+checkboxName+"']:checked").length;
	},
	
	/**
	 * ��ȡ�������ֵ. ��","�ָ�.
	 */
	getHiddenTextValuesStr: function(textName) {
		return $('input:hidden[name="' + textName + '"]').map(function(){return $(this).val();}).get().join(",");
	},
	
	/**
	 * �Ƿ���ѡ�е�ѡ��
	 */
	hasRadio: function(radioboxName) {
		return $('input[name="' + radioboxName + '"]:checked').length > 0;
	},
	
	/**
	 * �Ƿ���ѡ�е�ѡ��
	 */
	getRadioedValue: function(radioboxName) {
		return $('input[name="' + radioboxName + '"]:checked').val();
	},
	
	/**
	 * ������
	 */
	reset: function(param, ignores) {
		//������е����ݣ����Դ�form��id,form����,��ǰ����,Ҳ��ʲô������
		var form;
		if(!param){
			 var event = window.event; // �¼�     
        	 var target = event.target || event.srcElement; // ����¼�Դ 
       		 form = target.form;
		}
		if(typeof param == 'object'){
			if(param.tagName.toUpperCase()=='FORM'){
				form = param;
			}else{
				form = param.form;
			}
		}else if(typeof param == 'string'){
			form = $('#'+param);
		}
		this.clearData($(form).find('input:text'), ignores);
		this.clearData($(form).find('input:password'));
		this.clearData($(form).find('select'));
		this.clearData($(form).find('textarea'));
	},
	
	/**
	 * ��ն���
	 * @param obj Jquery����
	 */
	clearData: function(obj, ignores) {
		if (obj != null && obj != undefined && obj.length > 0){
			obj.each(function(i){
				var id = $(this).attr('id');
				if(id == ''){
					id = $(this).attr('name');
				}
				var flag = true;
				if(id){
					if(id.indexOf('pageSize') != -1 || id.indexOf('goPageIndex') != -1){
						flag = false;
					}
					//�����������õ�Ԫ��
					if(ignores && ignores.length > 0){
						for(var i=0; i<ignores.length; i++){
							if(id == ignores[i]){
								if(id == 'startDate'){
									$(this).val(defStartDate);
								}else if(id == 'endDate'){
									$(this).val(defEndDate);
								}
								flag = false;
								break;
							}
						}
					}
				}
				if(flag){
					$(this).val('');
				}
			});
		}
	},
	
	/**
	 * �ύ��һ���������Ƶ�form.
	 */
	submitFirstTokenForm: function() {
		this.submitTokenForm(0);
	},
	
	/**
	 * �ύ��һ���������Ƶ�form.
	 */
	submitTokenForm: function(formIndex) {
		var form = $('form:eq(' + formIndex + ')');
		if ((form.hasClass('validate') && form.valid()) || !form.hasClass('validate')) {
			form.find(':button, :submit, :reset').attr('disabled', true);
			YToken.create(form);
			form.submit();
		}
	}
};

/**
 * �ж�����Ԫ���Ƿ���ͬ.
 */
function equalArray(array1, array2) {
	if (!$.isArray(array1) || !$.isArray(array2)) {
		return false;
	}

	if (array1.length != array2.length) {
		return false;
	}

	var eq = true;
	
	$.each(array1, function(i, n) {
		if ($.inArray(n, array2) < 0) {
			eq = false;
			return false;
		}
	});
	
	return eq;
}

function gotoUrl(url) {
	if(url.indexOf(CONTEXT_PATH) != -1){
		window.location.href= url;
	}else{
		var prefix = url.slice(0,1);
		if(prefix != '/'){
			window.location.href= CONTEXT_PATH +"/"+ url;
		}else{
			window.location.href= CONTEXT_PATH + url;
		}
	}
}

function isDisplay(domId) {
	var display = $('#' + domId).css('display');
	
	return (display == 'block' || display == 'inline' || display == '');
}
/**
 * ����ͨ����ת��Ϊ������͵�����
 * @param {Object} money
 * @return {TypeName} 
 */

function formatToCurrency(money) {
	var num = money;
	var result;
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num)) {
		result = "";
	} else {
		var st = num.indexOf(".")
		if (st != -1) {
			num = num.replace(/,/g, '');
			var x;
			if (num.length >= st + 3) {
				x = num.substring(st + 1, st + 3)
			} else {
				x = num.substring(st + 1, num.length)
			}
			z = num.substring(0, st);
			for (var i = 0; i < Math.floor((z.length - (1 + i)) / 3); i++) {
				z = z.substring(0, z.length - (4 * i + 3)) + ','
						+ z.substring(z.length - (4 * i + 3));
			}
			result = z + "." + x;

		} else {

			num = num.replace(/,/g, '');
			for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
				num = num.substring(0, num.length - (4 * i + 3)) + ','
						+ num.substring(num.length - (4 * i + 3));
			result = num+".00";
		}

	}
	return result;
}

/**
 * ����ʽ��
 * 
 * @param 
 *            money ���
 * @param 
 *            t ��д�����ʵ��λ�õ�ID
 * @param t1 ����INPUT����,����ҳ�����󴫵�,���û�д����ַ���
 */
function formatCurrency(money, t, t1) {
	var num = money.value;
	var result;
	num = num.toString().replace(/\$|\,/g, '');
	if (isNaN(num)) {
		result='';
		$('#'+t).html('');
		$('#'+t1).html('');
	} else {
		if(num.length>=2){
			if(num.substring(0,2)=='00'){
				num='';
			}
		}
		if(num.length>1 && num.substr(0,1)=='0'&&num.substr(1,1)!='.'){
			num=num.substr(1,num.length-1);
		}
		var amount = new Number(num);
		if(amount>=10000000000000){
			money.select();
			money.focus();
			$(money).parent().find('span.error_tipinfo').text('��������ȷ��ʽ�Ľ��').show();
			$(money).parent().find('span.field_tipinfo').hide();
			$('#'+t).html('');
			$('#'+t1).html('');
			return;
		}else{
			$(money).parent().find('span.error_tipinfo').hide();
		}
		var st = num.indexOf(".")
		if (st != -1) {
			num = num.replace(/,/g, '');
			var x;
			if (num.length >= st + 3) {
				x = num.substring(st + 1, st + 3)
			} else {
				x = num.substring(st + 1, num.length)
			}
			z = num.substring(0, st);
			for (var i = 0; i < Math.floor((z.length - (1 + i)) / 3); i++) {
				z = z.substring(0, z.length - (4 * i + 3)) 
				+ ','//��Ҫ����
				+ z.substring(z.length - (4 * i + 3));
			}
			result = z + "." + x;

		} else {

			num = num.replace(/,/g, '');
			
			for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
				num = num.substring(0, num.length - (4 * i + 3)) + ','
						+ num.substring(num.length - (4 * i + 3));
			
			//��Ҫ����
			/*for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
				num = num.substring(0, num.length - (4 * i + 3)) + ''
						+ num.substring(num.length - (4 * i + 3));*/
			result = num;
		}
		$('#'+t1).text(result);
		result = result.replace(/,/g, '');
	}
	if (result != money.value) {
		money.value = result;
	}
	
	var sda=arabia_to_chinese(result);
	var ida="#"+t;
	
	//$(ida).val(sda);
	$(ida).text(sda);
}
/**
 * ���Сдת���ִ�дjs����
 * 
 * @author jcj
 * @param {}
 *            ���ֽ��
 * @return {}���Ľ��
 */
function arabia_to_chinese(num) {

	
	for (i = num.length - 1; i >= 0; i--) {
		num = num.replace(",", "")// �滻tomoney()�еġ�,��
		num = num.replace(" ", "")// �滻tomoney()�еĿո�
	}
	num = num.replace("��", "")// �滻�����ܳ��ֵģ��ַ�

	// ---�ַ�������ϣ���ʼת����ת������ǰ�������ֱַ�ת��---/
	part = num.split(".");
	newchar = "";
	// С����ǰ����ת��
	for (i = part[0].length - 1; i >= 0; i--) {
		if (part[0].length > 14) {
			//alert("��������ȷ�Ľ��");
			return "";
		}// ����������ʰ�ڵ�λ����ʾ
		tmpnewchar = "";
		perchar = part[0].charAt(i);
		switch (perchar) {
			case "0" :
				tmpnewchar = "��" + tmpnewchar;
				break;
			case "1" :
				tmpnewchar = "Ҽ" + tmpnewchar;
				break;
			case "2" :
				tmpnewchar = "��" + tmpnewchar;
				break;

			case "3" :
				tmpnewchar = "��" + tmpnewchar;
				break;
			case "4" :
				tmpnewchar = "��" + tmpnewchar;
				break;
			case "5" :
				tmpnewchar = "��" + tmpnewchar;
				break;
			case "6" :
				tmpnewchar = "½" + tmpnewchar;
				break;
			case "7" :
				tmpnewchar = "��" + tmpnewchar;
				break;
			case "8" :
				tmpnewchar = "��" + tmpnewchar;
				break;
			case "9" :
				tmpnewchar = "��" + tmpnewchar;
				break;
		}
		switch (part[0].length - i - 1) {
			case 0 :
				tmpnewchar = tmpnewchar + "Ԫ";
				break;
			case 1 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "ʰ";
				break;
			case 2 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "��";
				break;
			case 3 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "Ǫ";
				break;
			case 4 :
				tmpnewchar = tmpnewchar + "��";
				break;
			case 5 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "ʰ";
				break;
			case 6 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "��";
				break;
			case 7 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "Ǫ";
				break;
			case 8 :
				tmpnewchar = tmpnewchar + "��";
				break;
			case 9 :
				tmpnewchar = tmpnewchar + "ʰ";
				break;
			case 10 :
				tmpnewchar = tmpnewchar + "��";
				break;
			case 11 :
				tmpnewchar = tmpnewchar + "Ǫ";
				break;
			case 12 :
				tmpnewchar = tmpnewchar + "��";
				break;
		}
		newchar = tmpnewchar + newchar;
		
	}
	
	// С����֮�����ת��
	if (num.indexOf(".") != -1) {
		if (part[1].length > 2) {
			part[1] = part[1].substr(0, 2)
		}
		for (i = 0; i < part[1].length; i++) {
			tmpnewchar = ""
			perchar = part[1].charAt(i)
			switch (perchar) {
				case "0" :
					tmpnewchar = "��" + tmpnewchar;
					break;
				case "1" :
					tmpnewchar = "Ҽ" + tmpnewchar;
					break;
				case "2" :
					tmpnewchar = "��" + tmpnewchar;
					break;
				case "3" :
					tmpnewchar = "��" + tmpnewchar;
					break;
				case "4" :
					tmpnewchar = "��" + tmpnewchar;
					break;
				case "5" :
					tmpnewchar = "��" + tmpnewchar;
					break;
				case "6" :
					tmpnewchar = "½" + tmpnewchar;
					break;
				case "7" :
					tmpnewchar = "��" + tmpnewchar;
					break;
				case "8" :
					tmpnewchar = "��" + tmpnewchar;
					break;
				case "9" :
					tmpnewchar = "��" + tmpnewchar;
					break;
			}
			if (i == 0)
				tmpnewchar = tmpnewchar + "��";
			if (i == 1)
				tmpnewchar = tmpnewchar + "��";
			newchar = newchar + tmpnewchar;
		}
	}
	
	// �滻�������ú���
	while (newchar.search("����") != -1)
		newchar = newchar.replace("����", "��");
	newchar = newchar.replace("����", "��");
	newchar = newchar.replace("����", "��");
	newchar = newchar.replace("����", "��");
	newchar = newchar.replace("��Ԫ", "Ԫ");
	newchar = newchar.replace("���", "");
	newchar = newchar.replace("���", "");
	
	if (newchar.charAt(newchar.length - 1) == "Ԫ"
			|| newchar.charAt(newchar.length - 1) == "��")
		newchar = newchar + "��"
	if(newchar.substr(0,1)=='Ԫ'){
		newchar.substring(1,newchar.length-1);
	}
	if(newchar=='Ԫ��'){
		newchar='��Ԫ��';
	}
	
	if(newchar.substr(0,1)=='Ԫ'){
		newchar=newchar.substr(1,newchar.length-1);
	}
	if(newchar=='Ԫ��'){
		newchar='��Ԫ��';
	}
	return newchar;
	
}
function formtMoney(amount,t) {
	//var val = a.value;
	
	var val = $('#'+t).text();
	var len = val.length;
	//�������������С�����β������ȥ��
	if(val.substr(len-1,1)=='.'){
		val = val.substr(0,len-1);
		var amountVal = $(amount).val();
		$(amount).val(amountVal.substr(0,amountVal.length-1));
	}
	var result;
	if (val == "") {

	} else {
		
		var	num = val.replace(/\$|\,/g, '');
		if(num>=10000000000000){
			return;
		}
		var st = val.indexOf(".")

		if (st == -1) {
			result = val + ".00";
		} else {
			var l = val.length - (st + 1)
			if (l == 0) {
				result = val + ".00";
			} else if (l == 1) {
				result = val + "0";
			} else {
				result = val;
			}
		}
		$('#'+t).text(result);
	}
}
/*
function formtMoney(a) {
	var val = a.value;
	var result;
	if (val == "") {

	} else {
		
		var	num = val.replace(/\$|\,/g, '');
		if(num>=10000000000000){
			return;
		}
		var st = val.indexOf(".")

		if (st == -1) {
			result = val + ".00";
		} else {
			var l = val.length - (st + 1)
			if (l == 0) {
				result = val + ".00";
			} else if (l == 1) {
				result = val + "0";
			} else {
				result = val;
			}
		}
		
		if (result != a.value) {
			a.value = result
		}
	}
}*/
/**
 * ȡ��ѡ�е�checkbox����
 * @param {Object} name
 * @return {TypeName} 
 */
function getCheckedbox(name){
	var cbs = document.getElementsByName(name);
	var boxArray = [];
	if(cbs == null || cbs == undefined){
		return;
	}
	for(var i=0; i<cbs.length; i++){
		if(cbs[i].checked){
			boxArray.push(cbs[i]);
		}
	}
	return boxArray;
}
/**
 * 
 * @param {Object} cbName
 * @param {Object} tdIndex
 */
function getConfirmInfo(cbName, tdIndex){
	var boxArray = getCheckedbox(cbName);
	var k = 0;
	var amount = 0.00;
	for(var i=0; i<boxArray.length; i++){
		k++;
		var bill = boxArray[i].parentNode.parentNode.childNodes[tdIndex].innerHTML;
		var fbill = bill.replace(/,/g, "");
		amount += parseFloat(fbill);
	}
	var desc = "�ܱ���:"+k+", "+"�ܽ��:"+formatToCurrency(amount)+".\n";
	desc += "ȷ���ύ?";
	return desc;
}
/**
 * ȡʱ�䣬��ȷ���֣�Ĭ��Ϊ00:00
 */
function setStartTime(){
	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', startDate:'%y-%M-%d 00:00:00'});
}
/**
 * ȡʱ�䣬��ȷ���֣�Ĭ��Ϊ23:59
 */
function setEndTime(){
	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', startDate:'%y-%M-%d 23:59:59'});
}

function setStartTimeS(){
	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', startDate:'%y-%M-%d 00:00:00'});
}

function setEndTimeS(){
	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', startDate:'%y-%M-%d 23:59:59'});
}
function backToList(url, strParam, updateSymbol){
	var str = "";
	url = CONTEXT_PATH+url;
	var formStr = "<form method='post' action='"+url+"'>";
	var jsonParam = eval('('+strParam+')');
	for (var i in jsonParam){
		if(!i || !jsonParam[i]){
			continue;
		}
		if(jsonParam.constructor==Array){
			for(var j=0; j<jsonParam[i].length; i++){
				formStr += "<input type='hidden' name='"+i+"'";
				formStr += "' value='"+jsonParam[i][j]+"'/>";
			}
		}else{
			formStr += "<input type='hidden' name='"+i+"'";
			formStr += "' value='"+jsonParam[i]+"'/>";
		}
	}
	if(updateSymbol){
		formStr += "<input type='hidden' name='updateSymbol' value='Y'/>";
	}
	formStr += "</form>";
	$('body').append(formStr);
	$("form:last").submit(); 
}
