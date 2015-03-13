/**
 * usbkey ������.
 * @author aps-mhc
 */
USBKey = {
	/**
	 * ��鿪��, Y:���,N:�����
	 */
	checkSwitch: 'Y',
	 
	/**
	 * ocx�ؼ�����.
	 */
	ocxName: 'SignOcx.cab',
	
	/**
	 * ocx�ؼ�ID
	 */
	ocxId: 'signOcx',

	
	keyBoxId: '__signinfo',
	rnBoxId: '__randomNo',
	
	/**
	 * ��ȡocx �ؼ�, ����������򴴽�, ��ӵ�body���.
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
	 * ǩ��.
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
			alert("ǩ��ʧ�ܡ�����ϵ����Ա��")
			return false;
		}

		return true;
	},
	/**
	 * ǩ��.
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
			alert("ǩ��ʧ�ܡ�����ϵ����Ա��")
			return false;
		}

		return true;
	},
	
	/**
	 * ǩ��. ��form������randomNo��key����������, ������ֵ.
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
	 * ǩ��. ��form������randomNo��key����������, ������ֵ.
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
	 * ����key��randomNo��������.
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
	 * ���ؼ��Ƿ��Ѱ�װ.
	 * @param prom �Ƿ���ʾ.
	 */
	isInstalled: function(prom) {
  		if(typeof(signOcx) == "object"){
			if( (signOcx.object != null) ){
				return true;
			}
		}
		if (prom) {
			alert('δ��װUSBKEY�ؼ�');
		}
		return false;
		
	},
	
	/**
	 * ��֤��ǰ��¼�û�key �Ƿ���ȷ.
	 * @param successFn ��֤�ɹ��ص����� �޲���
	 * @param failureFn ��֤ʧ�ܻص����� �޲���
	 */
	validateKey: function(successFn, failureFn) {
		// �����ʱֱ��ִ��fn, ���˳�.
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
			alert('��ȷ���Ƿ��Ѿ�����USBKEY');
			return false;
		}
		
		// ȡkey����.
		var param = {'__signinfo':sign, '__randomNo':rn};
		
		$.get(CONTEXT_PATH + '/checkUser.do?action=checkKey', param, function(data) {
			if (data != 'success') {
				alert('��֤ʧ��');
				
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