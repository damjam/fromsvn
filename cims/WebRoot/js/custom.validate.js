$(document).ready(function() {
	// 字符最小长度验证（一个中文字符长度为2）
	jQuery.validator.addMethod("stringMinLength", function(value, element, param) {
		var length = value.length;
		for ( var i = 0; i < value.length; i++) {
			if (value.charCodeAt(i) > 127) {
				length++;
			}
		}
		return this.optional(element) || (length >= param);
	}, $.validator.format("长度不能小于{0}!"));
	
	// 字符最大长度验证（一个中文字符长度为2）
	jQuery.validator.addMethod("stringMaxLength", function(value, element, param) {
		var length = value.length;
		for ( var i = 0; i < value.length; i++) {
			if (value.charCodeAt(i) > 127) {
				length++;
			}
		}
		return this.optional(element) || (length <= param);
	}, $.validator.format("长度不能大于{0}!"));
	
	// 字符验证       
	jQuery.validator.addMethod("stringCheck", function(value, element) {       
		return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);       
	}, "只能包括中文字、英文字母、数字和下划线");
	
	// 字符验证       
	jQuery.validator.addMethod("stringNum", function(value, element) {       
		return this.optional(element) || /^[A-Za-z0-9]+$/.test(value);       
	}, "只能包括英文字母、数字");

	// 身份证号码验证       
	jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
		return this.optional(element) || /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(value)||/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/.test(value);       
	}, "请正确输入您的身份证号码");
	
	// 手机号码验证
	jQuery.validator.addMethod("isMobile", function(value, element) {
		var length = value.length;
		var mobile = /0?(13|14|15|18)[0-9]{9}/;
		return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写您的手机号码");
	
	// 电话号码验证       
	jQuery.validator.addMethod("isTel", function(value, element) {       
		var tel = /^\d{3,4}-?\d{7,9}$/;    //电话号码格式010-12345678   
		return this.optional(element) || (tel.test(value));       
	}, "请正确填写您的电话号码"); 
	
	// 联系电话(手机/电话皆可)验证   
	jQuery.validator.addMethod("isPhone", function(value,element) {   
		var length = value.length;   
		var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;   
		var tel = /^\d{3,4}-?\d{7,9}$/;   
		return this.optional(element) || (tel.test(value) || mobile.test(value));   
	}, "请正确填写您的联系电话");
	
	// 邮政编码验证       
	jQuery.validator.addMethod("isZipCode", function(value, element) {       
		var tel = /^[0-9]{6}$/;       
		return this.optional(element) || (tel.test(value));       
	}, "请正确填写您的邮政编码");    
});