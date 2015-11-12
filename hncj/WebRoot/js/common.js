var ZERO = 0.0001;
var LOAD_IMAGE = "<img src='" + CONTEXT_PATH
		+ "/images/ajax_loading.gif' style='margin-left:20px;'/>";
var SAVE_IMAGE = "<img src='" + CONTEXT_PATH + "/images/ajax_saving.gif'/>";

var _tracer = null;
var _times = 0;
var _MAX_TIMES = 10; // 系统参数
var _INTERVAL = 2000; // 系统参数

var _INVALID_CHAR = "'<>/&";

/**
 * 是否含有无效字符.
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
 * 定时执行查询结果
 * 
 */
function startTracker(func) {
	_tracer = setInterval(func, _INTERVAL);
	
	return _tracer;
}

/**
 * 显示成功的图片
 *
 */
function showRightImg(targetId){
	$('#' + targetId).html('<img src="' + CONTEXT_PATH + '/images/pages/right.gif" height="12" width="16" vspace="4"/>');
}

/**
 * 显示错误的图片
 *
 */
function showFaultImg(targetId){
	$('#' + targetId).html('<img src="' + CONTEXT_PATH + '/images/pages/fault.gif"  height="22" width="17" vspace="0"/>');
}

/**
 * 得到当前页面验证结果
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
 * 表单token.
 */
YToken = {
	/**
	 * 生成随机数.
	 */
	callId: function() {
		var random = Math.floor(Math.random() * 10001);
	  	return (random + "_" + new Date().getTime()).toString();
	},
	
	/**
	 * 在表单内部创建随机数token.
	 */
	create: function(jqueryForm) {
		if (jqueryForm.find('input[name="TOKEN-KEY"]').length == 0) {
			jqueryForm.append('<input type="hidden" name="TOKEN-KEY">');
		}
		
		jqueryForm.find('input[name="TOKEN-KEY"]').val(YToken.callId());
	},
	
	/**
	 * 获取表单内部随机数token.
	 */
	get: function(jqueryForm) {
		return jqueryForm.find('input[name="TOKEN-KEY"]').val();
	},
	
	/**
	 * 清空表单内部随机数token.
	 */
	clear: function(jqueryForm) {
		jqueryForm.find('input[name="TOKEN-KEY"]').val('');
	}
}

/**
 * 页面是否有返回结果
 */
function hasResult(){
	var result = $('input[name="result"]').val();
	if (result == null || result == "" || result == undefined){
		return false;
	}
	return true;
}

/**
 * 得到返回消息
 */
function getMessage(){
	return $('input[name="message"]').val();
}

/**
 * 得到返回结果
 */
function getResult(){
	return $('input[name="result"]').val();
}

/**
 * 显示TIP
 * @param result 成功 OR 失败
 * @param message 返回结果
 * @param isHide 成功的信息是否隐藏
 * @param isReset 是否重设页面上的表单
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
 * 隐藏TIP
 */
function hideTip(){
	$('#tip').slideUp("slow");
}

/**
 * 完成验证, 根据是否有效显示提示信息.
 * @tgt jquery对象.
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
	 * 全选全清复选框
	 */
	selectAll: function(target, checkboxName) {
		$(':checkbox[name="' + checkboxName + '"]').each(function() {
			$(this).attr('checked', target.checked);
		});
	},
	
	/**
	 * 获取复选框选中的值. 以","分隔.
	 */
	getCheckedValues: function(checkboxName) {
		return $(':checkbox[name="' + checkboxName + '"][checked]').map(function(){
	  		return $(this).val();
		}).get().join(",");
	},
	
	/**
	 * 获取复选框选中的值. 数组形式.
	 */
	getCheckedArrayValues: function(checkboxName) {
		return $(':checkbox[name="' + checkboxName + '"][checked]').map(function(){
	  		return $(this).val();
		}).get();
	},
	
	/**
	 * 是否有选中复选框
	 */
	hasSelected: function(checkboxName) {
		return $(':checkbox[name="' + checkboxName + '"][checked]').length > 0;
	},
	
	/**
	 * 选中复选框的个数.
	 */
	getSelectedCount: function(checkboxName) {
		return $(':checkbox[name="' + checkboxName + '"][checked]').length;
	},
	
	/**
	 * 获取隐藏域的值. 以","分隔.
	 */
	getHiddenTextValuesStr: function(textName) {
		return $('input:hidden[name="' + textName + '"]').map(function(){return $(this).val();}).get().join(",");
	},
	
	/**
	 * 是否有选中单选框
	 */
	hasRadio: function(radioboxName) {
		return $(':radio[name="' + radioboxName + '"][checked]').length > 0;
	},
	
	/**
	 * 是否有选中单选框
	 */
	getRadioedValue: function(radioboxName) {
		return $(':radio[name="' + radioboxName + '"][checked]').val();
	},
	
	/**
	 * 表单重置
	 */
	reset: function(param, ignores) {
		//清除表单中的数据，可以传form的id,form对象,当前对象,也可什么都不传
		var form;
		if(!param){
			 var event = window.event; // 事件     
        	 var target = event.target || event.srcElement; // 获得事件源 
       		 form = target.form;
		}
		if(typeof param == 'object'){
			if(param.tagName.toUpperCase()=='FORM'){
				form = param;
			}else{
				form = param.form;
			}
		}
		this.clearData($(form).find('input:text'), ignores);
		this.clearData($(form).find('input:password'));
		this.clearData($(form).find('select'));
		this.clearData($(form).find('textarea'));
	},
	
	/**
	 * 清空对象
	 * @param obj Jquery对象
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
					//过滤无需重置的元素
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
	 * 提交第一个加入令牌的form.
	 */
	submitFirstTokenForm: function() {
		this.submitTokenForm(0);
	},
	
	/**
	 * 提交第一个加入令牌的form.
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
 * 判断数组元素是否相同.
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
	window.location.href= CONTEXT_PATH + url;
}

function isDisplay(domId) {
	var display = $('#' + domId).css('display');
	
	return (display == 'block' || display == 'inline' || display == '');
}
/**
 * 把普通数字转换为金额类型的数字
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
 * 金额格式化
 * 
 * @param 
 *            money 金额
 * @param 
 *            t 大写金额现实的位置的ID
 * @param t1 隐藏INPUT内容,用于页面往后传递,如果没有传空字符串
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
			$(money).parent().find('span.error_tipinfo').text('请输入正确格式的金额').show();
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
				+ ','//不要逗号
				+ z.substring(z.length - (4 * i + 3));
			}
			result = z + "." + x;

		} else {

			num = num.replace(/,/g, '');
			
			for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
				num = num.substring(0, num.length - (4 * i + 3)) + ','
						+ num.substring(num.length - (4 * i + 3));
			
			//不要金额逗号
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
 * 金额小写转汉字大写js方法
 * 
 * @author jcj
 * @param {}
 *            数字金额
 * @return {}中文金额
 */
function arabia_to_chinese(num) {

	
	for (i = num.length - 1; i >= 0; i--) {
		num = num.replace(",", "")// 替换tomoney()中的“,”
		num = num.replace(" ", "")// 替换tomoney()中的空格
	}
	num = num.replace("￥", "")// 替换掉可能出现的￥字符

	// ---字符处理完毕，开始转换，转换采用前后两部分分别转换---/
	part = num.split(".");
	newchar = "";
	// 小数点前进行转化
	for (i = part[0].length - 1; i >= 0; i--) {
		if (part[0].length > 14) {
			//alert("请输入正确的金额");
			return "";
		}// 若数量超过拾亿单位，提示
		tmpnewchar = "";
		perchar = part[0].charAt(i);
		switch (perchar) {
			case "0" :
				tmpnewchar = "零" + tmpnewchar;
				break;
			case "1" :
				tmpnewchar = "壹" + tmpnewchar;
				break;
			case "2" :
				tmpnewchar = "贰" + tmpnewchar;
				break;

			case "3" :
				tmpnewchar = "叁" + tmpnewchar;
				break;
			case "4" :
				tmpnewchar = "肆" + tmpnewchar;
				break;
			case "5" :
				tmpnewchar = "伍" + tmpnewchar;
				break;
			case "6" :
				tmpnewchar = "陆" + tmpnewchar;
				break;
			case "7" :
				tmpnewchar = "柒" + tmpnewchar;
				break;
			case "8" :
				tmpnewchar = "捌" + tmpnewchar;
				break;
			case "9" :
				tmpnewchar = "玖" + tmpnewchar;
				break;
		}
		switch (part[0].length - i - 1) {
			case 0 :
				tmpnewchar = tmpnewchar + "元";
				break;
			case 1 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "拾";
				break;
			case 2 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "佰";
				break;
			case 3 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "仟";
				break;
			case 4 :
				tmpnewchar = tmpnewchar + "万";
				break;
			case 5 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "拾";
				break;
			case 6 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "佰";
				break;
			case 7 :
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "仟";
				break;
			case 8 :
				tmpnewchar = tmpnewchar + "亿";
				break;
			case 9 :
				tmpnewchar = tmpnewchar + "拾";
				break;
			case 10 :
				tmpnewchar = tmpnewchar + "佰";
				break;
			case 11 :
				tmpnewchar = tmpnewchar + "仟";
				break;
			case 12 :
				tmpnewchar = tmpnewchar + "万";
				break;
		}
		newchar = tmpnewchar + newchar;
		
	}
	
	// 小数点之后进行转化
	if (num.indexOf(".") != -1) {
		if (part[1].length > 2) {
			part[1] = part[1].substr(0, 2)
		}
		for (i = 0; i < part[1].length; i++) {
			tmpnewchar = ""
			perchar = part[1].charAt(i)
			switch (perchar) {
				case "0" :
					tmpnewchar = "零" + tmpnewchar;
					break;
				case "1" :
					tmpnewchar = "壹" + tmpnewchar;
					break;
				case "2" :
					tmpnewchar = "贰" + tmpnewchar;
					break;
				case "3" :
					tmpnewchar = "叁" + tmpnewchar;
					break;
				case "4" :
					tmpnewchar = "肆" + tmpnewchar;
					break;
				case "5" :
					tmpnewchar = "伍" + tmpnewchar;
					break;
				case "6" :
					tmpnewchar = "陆" + tmpnewchar;
					break;
				case "7" :
					tmpnewchar = "柒" + tmpnewchar;
					break;
				case "8" :
					tmpnewchar = "捌" + tmpnewchar;
					break;
				case "9" :
					tmpnewchar = "玖" + tmpnewchar;
					break;
			}
			if (i == 0)
				tmpnewchar = tmpnewchar + "角";
			if (i == 1)
				tmpnewchar = tmpnewchar + "分";
			newchar = newchar + tmpnewchar;
		}
	}
	
	// 替换所有无用汉字
	while (newchar.search("零零") != -1)
		newchar = newchar.replace("零零", "零");
	newchar = newchar.replace("零亿", "亿");
	newchar = newchar.replace("亿万", "亿");
	newchar = newchar.replace("零万", "万");
	newchar = newchar.replace("零元", "元");
	newchar = newchar.replace("零角", "");
	newchar = newchar.replace("零分", "");
	
	if (newchar.charAt(newchar.length - 1) == "元"
			|| newchar.charAt(newchar.length - 1) == "角")
		newchar = newchar + "整"
	if(newchar.substr(0,1)=='元'){
		newchar.substring(1,newchar.length-1);
	}
	if(newchar=='元整'){
		newchar='零元整';
	}
	
	if(newchar.substr(0,1)=='元'){
		newchar=newchar.substr(1,newchar.length-1);
	}
	if(newchar=='元整'){
		newchar='零元整';
	}
	return newchar;
	
}
function formtMoney(amount,t) {
	//var val = a.value;
	
	var val = $('#'+t).text();
	var len = val.length;
	//如果输入数据以小数点结尾，将其去掉
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
 * 取得选中的checkbox对象
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
	var desc = "总笔数:"+k+", "+"总金额:"+formatToCurrency(amount)+".\n";
	desc += "确认提交?";
	return desc;
}
/**
 * 取时间，精确到分，默认为00:00
 */
function setStartTime(){
	WdatePicker({dateFmt:'yyyy-MM-dd HH:mm', startDate:'%y-%M-%d 00:00:00'});
}
/**
 * 取时间，精确到分，默认为23:59
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
