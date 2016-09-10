/**
 * usbkey 工具类.
 * @author aps-mhc
 */
USBKey = {
	/**
	 * 检查开关, Y:检查,N:不检查
	 */
	checkSwitch: 'Y',
	 
	/**
	 * ocx控件名称.
	 */
	ocxName: 'SignOcx.cab',
	
	/**
	 * ocx控件ID
	 */
	ocxId: 'signOcx',

	
	keyBoxId: '__signinfo',
	rnBoxId: '__randomNo',
	
	/**
	 * 获取ocx 控件, 如果不存在则创建, 添加到body最后.
	 */
	getOcx: function() {
		var id = this.ocxId;
		if ($('#' + id).length == 0) {
			var ocxhtml = '<OBJECT id="' + id + '" style="display:none" '+' codebase="'+CONTEXT_PATH+"/"+this.ocxName+'#version=1,0,0,0" classid="clsid:3C3E1438-A8C4-4D13-A35B-00F30AA64E23" VIEWASTEXT></OBJECT>';
			$('body').append(ocxhtml);
		}
		
		return $('#' + id).get(0);
	},
	
	/**
	 * 签名.
	 * @return true/false
	 */
	signMsg: function(randomBox, keyBox) {
		if (this.checkSwitch != 'Y') {
			return true;
		}

		var ocx = this.getOcx();

		if (!this.isInstalled(true)) {
			return false;
		}
		var randomNo = Math.round(Math.random()*10000);
		var signinfo = signOcx.Signature("",randomNo);

	    $('#' + randomBox).val(randomNo);
	    $('#' + keyBox).val(signinfo);
		if(signinfo==""){
			alert("签名失败。请联系管理员。")
			return false;
		}

		return true;
	},
	/**
	 * 签名.
	 * @return true/false
	 */
	signFile: function(fileName, keyBox) {
		if (this.checkSwitch != 'Y') {
			return true;
		}

		var ocx = this.getOcx();

		if (!this.isInstalled(true)) {
			return false;
		}
		
		
		var signinfo = ocx.Signfile("",fileName);
	    $('#' + keyBox).val(signinfo); 
		if(signinfo==""){
			alert("签名失败。请联系管理员。")
			return false;
		}

		return true;
	},
	
	/**
	 * 签名. 在form中生成randomNo和key两个隐藏域, 并设置值.
	 */
	signFormMsg: function(formId) {
		if (this.checkSwitch != 'Y') {
			return true;
		}
		
		var $f = $('#' + formId);
		this.createKeyBox($f, this.keyBoxId, this.rnBoxId);
		
		return this.signMsg(this.rnBoxId, this.keyBoxId);
	},
	/**
	 * 签名. 在form中生成randomNo和key两个隐藏域, 并设置值.
	 */
	signFileMsg: function(formId,fileId) {
		if (this.checkSwitch != 'Y') {
			return true;
		}
		
		var $f = $('#' + formId);
		var $fileid = $('#' + fileId);
		this.createKeyBox($f, this.keyBoxId, this.rnBoxId);
		return this.signFile($fileid[0].value, this.keyBoxId);
	},
	
	/**
	 * 创建key和randomNo的隐藏域.
	 */
	createKeyBox: function($f, keyBoxId, rnBoxId) {
		var $key = $f.find('#' + keyBoxId);
		var $rn = $f.find('#' + rnBoxId);
		
		if ($key.length == 0) {
			$f.append('<input type="hidden" id="' + keyBoxId + '" name="' + keyBoxId + '" />');
		}
		
		if ($rn.length == 0) {
			$f.append('<input type="hidden" id="' + rnBoxId + '" name="' + rnBoxId + '" />');
		}
	},
	
	/**
	 * 检查控件是否已安装.
	 * @param prom 是否提示.
	 */
	isInstalled: function(prom) {
  		if(typeof(signOcx) == "object"){
			if( (signOcx.object != null) ){
				return true;
			}
		}
		if (prom) {
			alert('未安装USBKEY控件');
		}
		return false;
		
	},
	
	/**
	 * 验证当前登录用户key 是否正确.
	 * @param successFn 验证成功回调函数 无参数
	 * @param failureFn 验证失败回调函数 无参数
	 */
	validateKey: function(successFn, failureFn) {
		// 不检查时直接执行fn, 并退出.
		if (this.checkSwitch != 'Y') {
			successFn();
			return;
		}
		
		if (!this.isInstalled(true)) {
			return false;
		}
		
		var ocx = this.getOcx();
		var rn = Math.round(Math.random() * 10000);
		var sign = ocx.Signature("",rn);
		if (sign == "") {
			alert('请确认是否已经插入USBKEY');
			return false;
		}
		
		// 取key参数.
		var param = {'__signinfo':sign, '__randomNo':rn};
		
		$.get(CONTEXT_PATH + '/checkUser.do?action=checkKey', param, function(data) {
			if (data != 'success') {
				alert('验证失败');
				
				if (failureFn) {
					failureFn();
				}
			}
			else {
				successFn();
			}
		});
	}
}