try {document.execCommand("BackgroundImageCache", false, true);}catch (e){}

String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * ��ģ̬�Ի���.
 * @param isRefresh �Ƿ�ˢ��
 * @param refreshHandle ˢ�´���id
 */
function openContextDialog(url, isRefresh, refreshHandle, width, height) {
	if (!width) {
		width = 780;
	}
	
	if (!height) {
		height = 500;
	}
	
	var option = 'dialogWidth=' + width + 'px;dialogHeight=' + height + 'px;resizable=yes';
	var rlt = window.showModalDialog(CONTEXT_PATH + url, null, option);
	
	// Ĭ������ˢ��
	if (!isRefresh) {
		isRefresh = false;
	}
	
	var refreshId = refreshHandle ? refreshHandle : '_refresh';
	var _refresh = document.getElementById(refreshId);
	
	if (!_refresh) {
		_refresh = document.getElementById('_refresh_0');
	}

	if (_refresh && isRefresh) {
		setTimeout(function() {_refresh.click();}, 1);
	}

	return rlt;
}

function stopevent(evt){
	var e = evt ? evt : window.event;
	if (window.event) {
		e.cancelBubble=true;
	} else {
		e.stopPropagation();
	}
}

/**
 * ����ҳ��Ԫ����ʽ.
 */
$(function() {
	SysStyle.setDataGridStyle();
	SysStyle.setFormGridStyle();
	SysStyle.setSearchGridStyle();
	SysStyle.setDetailGridStyle();
	
	SysStyle.setPageNavStyle();
	SysStyle.setButtonStyle();
	SysStyle.setNoPrivilegeStyle();
	SysStyle.addFormValidate();
});

/**
 * ϵͳ��ʽ.
 */
SysStyle = {
	/**
	 * �������ݱ��ͷ����, ��ż����ɫ, ����ƶ���ɫ.
	 */
	setDataGridStyle: function() {
		$('table.data_grid').each(function() {
			var $t = $(this);
			var thead = $t.find('thead');
			thead.addClass('bg-1');
			$t.find('tbody tr:odd').addClass('eee');
			$t.find('tbody tr:even').addClass('ddd');
			
			$trs = $t.find('tbody tr');
			$trs.mouseover(function() {if (!$(this).hasClass('click')) {$(this).addClass('on');}});
			$trs.mouseout(function() {if (!$(this).hasClass('click')) {$(this).removeClass('on');}});
			$trs.each(function(){
				var $self = $(this);
				$self.click(function() {
					var trClass = $(this).attr('class');
					if(trClass && trClass.indexOf('shortcut') != -1){
						removeClassAll($trs);
						$(this).addClass("click");
						var radio = $(this).find("td [type='radio']:eq(0)");
						radio.attr("checked", true);
					}else{
						removeSiblinsClass($(this));
						$(this).toggleClass('click');
					}
				});
				
				var trClass = $self.attr('class');
				$tr = $(this);
				if(trClass && trClass.indexOf('shortcut') != -1){
					$tr.dblclick(function(){
						if($("#[id^='sel']")[0]){
							$("#[id^='sel']")[0].click();
						}
					});
					$tr.mouseover(function(){
						$(this).attr("title", "˫���ɿ���ѡ��");
					});
				}
				var removeSiblinsClass = function($self){
					var $trs = $self.siblings();
					$trs.each(function(){
						var $tr = $(this);
						$tr.removeClass("click");
						$tr.removeClass("on");
					});
				}
				var removeClassAll = function($trs){
					$trs.each(function(){
						var $tr = $(this);
						$tr.removeClass("click");
						$tr.removeClass("on");
					});
				};
			});
		});
	},
	

	
	/**
	 * ���ñ������ʽ.
	 */
	setFormGridStyle: function() {
		// ���ñ������ʽ.
		/*
		$('table.form_grid').find('td').each(function(i) {
			// form���Ϊ����, ��һ��Ϊ��ǩ, �ڶ���Ϊ¼���.
			if (i % 2 == 0) {
				$(this).addClass('form-label-td');
			}
		});
		*/
		
		// ���ñ��������ʽ.
		$('table.form_grid :text, table.form_grid :password')
			.addClass('userbox_bt')
			.focus(function() {$(this).addClass('sffocus');})
			.blur(function() {$(this).removeClass('sffocus');});
	},
	
	/**
	 * ������ϸ��Ϣ�����ʽ.
	 */
	setDetailGridStyle: function() {
		$('table.detail_grid td:even').addClass('headcell').attr('width', '100px');
		$('table.detail_grid td:odd').addClass('valuecell');
	},
	
	/**
	 * ���ð�ť��ʽ.
	 */
	setButtonStyle: function() {
		$btn = $(':button, :submit, :reset')
		$btn.addClass('inp_L3');
		
		$btn.mouseover(function() {$(this).addClass('inp_L4'); $(this).removeClass('inp_L3')});
		$btn.mouseout(function() {$(this).addClass('inp_L3'); $(this).removeClass('inp_L4')});
	},
	
	/**
	 * ���ò�ѯ�����ʽ.
	 */
	setSearchGridStyle: function() {
		$('table.search_grid :text').addClass('form-text');
		$('table.search_grid :password').addClass('form-text');
	},
	
	/**
	 * ������Ȩ������.
	 */
	setNoPrivilegeStyle: function() {
		$('span.no-privilege a').removeAttr('href').removeAttr('onclick');
	},
	
	/**
	 * ���÷�ҳ����ʽ.
	 */
	setPageNavStyle: function() {
		$btn = $(':button.pagenavbtn')
		$btn.addClass('inp_L1');
		
		$btn.mouseover(function() {$(this).addClass('inp_L2'); $(this).removeClass('inp_L1')});
		$btn.mouseout(function() {$(this).addClass('inp_L1'); $(this).removeClass('inp_L2')});
	},
	
	/**
	 * ��ӱ���֤.
	 */
	addFormValidate: function() {
		$('form').each(function() {
			var f = $(this);
			if (f.hasClass('validate')) {
				f.validate({
					showErrors: JError.showErrors, 
					clearError: JError.clearError,
					submitHandler: function(form) {
						$(form).append("<input type='hidden' name='TOKEN-KEY' value='"+ YToken.callId() +"' />");
						$(form).find(":button, :submit, :reset").attr('disabled', true);
					}
				});
			}
		});
	}
};

/**
 * ����֤������ʾ.
 */
JError = {
	/**
	 * ��ʾ������Ϣ
	 * @param {} errorMap
	 * @param {} errorList
	 */
	showErrors: function(errorMap, errorList) {
		for ( var i = 0; errorList[i]; i++ ) {
			var error = errorList[i];
			var tipObj = JError.getTipElement(error.element)
			var errObj = JError.getErrElement(error.element)

			// û����ʾ��Ϣ, �˳�.
			if (tipObj == null) {
				return;
			}

			// �д�����Ϣ, ��ʾ������Ϣ, ������ʾ��Ϣ.
			if (errObj != null) {
				tipObj.hide();
				errObj.show();
			}
			
			// û�д�����Ϣ, ���ô�����ʽ��ʾ��ʾ��Ϣ.
			else {
				tipObj.addClass('error_tipinfo');
				tipObj.show();
			}
		}
	},
	
	/**
	 * ���������Ϣ
	 */
	clearError: function(element) {
		var tipObj = JError.getTipElement(element);
		var errObj = JError.getErrElement(element);
		
		// �д�����Ϣ����, �����ش�����Ϣ, ��ʾ��ʾ��Ϣ.
		if (errObj) {
			errObj.hide();
			tipObj.show();
		}
		
		// ȥ����ʾ��Ϣ�Ĵ�����ʽ.
		if (tipObj != null) {
			tipObj.removeClass('error_tipinfo');
		}
	},
	
	/**
	 * ȡ����ʾ��Ϣ����
	 * @param {} element
	 * @return {}
	 */
	getTipElement: function(element) {
		var obj = $(element).next();
		
		if (obj.length == 0) {
			return null;
		}
		
		if (obj.hasClass('field_tipinfo')) {
			return obj;
		}
		
		// ������һ��.
		obj = obj.next();
		return obj.hasClass('field_tipinfo') ? obj : null;
	},
		
	/**
	 * ȡ�ô�����Ϣ����, һ��Ϊ¼������ĵڶ���, text->infospan->errspan
	 * @param {} element
	 * @return {}
	 */
	getErrElement: function(element) {
		var tipObj = JError.getTipElement(element);
		if (tipObj == null) {
			return null;
		}
		
		var errObj = tipObj.next();
		return errObj.hasClass('error_tipinfo') ? errObj : null;
	},

	/**
	 * ��ʾ������Ϣ
	 */
	showError: function(element) {
		var tipe = getTipElement(element);
		
		if (tipe) {
			tipe.removeClass('field_tipinfo').addClass('error_tipinfo');
		}
	},
	
	hasFormError: function() {
		return $('.error_tipinfo').length > 0
	}
}

/**
 * ����ҳ��.
 * @param boxid ����id
 * @param url ��ַ ����CONTEXT_PATH
 * @param param �����б�,json��ʽ.
 */
function $jload(boxid, url, param) {
	var box = $('#' + boxid);
	if (box.length == 0) { 
		return;
	}
	
	// LOAD_IMAGE λ��common.js ��.
	box.show().html(LOAD_IMAGE).load(CONTEXT_PATH + url, param, function() {SysStyle.setDataGridStyle();});
}

/**
 * ˫ǩ��֤
 */
function doubleCheck() {
	var option = "dialogWidth=500px;dialogHeight=300px;resizable=no;scroll=no";
	var rst = window.showModalDialog(CONTEXT_PATH + '/pages/user/doubleCheck.jsp', null, option);
	
	if (!rst) {
		return null;
	}
	
	return rst;
}

/**
 * ȡ��ҳ��.
 */
function getMainFrame() {
	var page = window;
	var depth = 0;

	while (page.document.body.className.indexOf('top_frame') < 0) {
		page = page.parent;
		depth += 1;
		
		// ���ֻ����5��.
		if (depth > 5) {
			return null;
		}
	}
	
	return page;
}

/**
 * ��������ȡ, main.js.
 */
GlobalMem = {
	set: function(key, value) {
		getMainFrame().GLOBAL_MEM[key] = value;
	},
	
	poll: function(key) {
		var mainFrame = getMainFrame();
		var value = mainFrame.GLOBAL_MEM[key];
		delete mainFrame.GLOBAL_MEM[key];
		
		return value;
	}
}

/**
 * �л�div����ʾ
 */

function togglediv(divobj)
{
	if (divobj.style.display=='block' || divobj.style.display=='')		
		divobj.style.display='none';
	else
		divobj.style.display='block';
}		
/**
 * Frame���غ�Ķ���
 */

function FrameOnLoad(obj,frameobj)
{
	var win=obj;
	if (frameobj.location.href=="about:blank")
		return;

	changepage();
	
	if (document.getElementById)
	{       
		if (win && !window.opera)       
		{        
			if (win.contentDocument && win.contentDocument.body.offsetHeight)         
	 			win.height = win.contentDocument.body.offsetHeight;         
	 		else if(win.Document && win.Document.body.scrollHeight)        
	  			win.height = win.Document.body.scrollHeight;      
	   }
	}
	
}	
/**
 * ����ģ̬����,��߲�������ʱʹ��Ĭ��ֵ
 * @param {Object} dialogId
 * @param {Object} url
 * @param {Object} title
 * @param {Object} width
 * @param {Object} height
 * @param {Object} param
 * @return {TypeName} 
 */
function showDetail(dialogId, url, title, width, height, param){
	
	if(!width){
		width = 800;
	}
	if(!height){
		height = 500;
	}
	$('#'+dialogId).dialog({
		bgiframe: true,
		autoOpen: false,
		width: width,
		height: height,
		modal: true,
		title:title,
		close: function() {
			$(this).dialog("destroy");
		}
	});
	var box = $('#' + dialogId);
	if (box.length == 0) { 
		return;
	}
	// LOAD_IMAGE λ��common.js ��.
	box.show().html(LOAD_IMAGE).load(CONTEXT_PATH + url, param, function() {
		SysStyle.setDataGridStyle();
		SysStyle.setFormGridStyle();
		SysStyle.setSearchGridStyle();
		SysStyle.setDetailGridStyle();
		
		SysStyle.setPageNavStyle();
		SysStyle.setButtonStyle();
		SysStyle.setNoPrivilegeStyle();
		SysStyle.addFormValidate();
	});
	$('#' + dialogId).dialog('open');
}
/**
 * �رյ������ڲ��������
 * @param {Object} dialogId
 */
function closeDetail(dialogId){
	$('#'+dialogId).html('');
	$('#' + dialogId).dialog('close');
}

/**
 * ������
 * @param {Object} link
 */
function closeAll(link){
	d.closeAll();
	$("p:first").html('<a href="javascript:openAll(this)" style="color: red">չ��</a>');
}
/**
 * չ����
 * @param {Object} link
 */
function openAll(link){
	d.openAll();
	$("p:first").html('<a href="javascript:closeAll(this)" style="color: red">����</a>');
}
