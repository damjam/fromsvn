$(document).ready(function() {
	// �ַ���С������֤��һ�������ַ�����Ϊ2��
	jQuery.validator.addMethod("stringMinLength", function(value, element, param) {
		var length = value.length;
		for ( var i = 0; i < value.length; i++) {
			if (value.charCodeAt(i) > 127) {
				length++;
			}
		}
		return this.optional(element) || (length >= param);
	}, $.validator.format("���Ȳ���С��{0}!"));
	
	// �ַ���󳤶���֤��һ�������ַ�����Ϊ2��
	jQuery.validator.addMethod("stringMaxLength", function(value, element, param) {
		var length = value.length;
		for ( var i = 0; i < value.length; i++) {
			if (value.charCodeAt(i) > 127) {
				length++;
			}
		}
		return this.optional(element) || (length <= param);
	}, $.validator.format("���Ȳ��ܴ���{0}!"));
	
	// �ַ���֤       
	jQuery.validator.addMethod("stringCheck", function(value, element) {       
		return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);       
	}, "ֻ�ܰ��������֡�Ӣ����ĸ�����ֺ��»���");
	
	// �ַ���֤       
	jQuery.validator.addMethod("stringNum", function(value, element) {       
		return this.optional(element) || /^[A-Za-z0-9]+$/.test(value);       
	}, "ֻ�ܰ���Ӣ����ĸ������");

	// ���֤������֤       
	jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
		return this.optional(element) || /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(value)||/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/.test(value);       
	}, "����ȷ�����������֤����");
	
	// �ֻ�������֤
	jQuery.validator.addMethod("isMobile", function(value, element) {
		var length = value.length;
		var mobile = /0?(13|14|15|18)[0-9]{9}/;
		return this.optional(element) || (length == 11 && mobile.test(value));
	}, "����ȷ��д�����ֻ�����");
	
	// �绰������֤       
	jQuery.validator.addMethod("isTel", function(value, element) {       
		var tel = /^\d{3,4}-?\d{7,9}$/;    //�绰�����ʽ010-12345678   
		return this.optional(element) || (tel.test(value));       
	}, "����ȷ��д���ĵ绰����"); 
	
	// ��ϵ�绰(�ֻ�/�绰�Կ�)��֤   
	jQuery.validator.addMethod("isPhone", function(value,element) {   
		var length = value.length;   
		var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;   
		var tel = /^\d{3,4}-?\d{7,9}$/;   
		return this.optional(element) || (tel.test(value) || mobile.test(value));   
	}, "����ȷ��д������ϵ�绰");
	
	// ����������֤       
	jQuery.validator.addMethod("isZipCode", function(value, element) {       
		var tel = /^[0-9]{6}$/;       
		return this.optional(element) || (tel.test(value));       
	}, "����ȷ��д������������");    
});