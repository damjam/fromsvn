try {document.execCommand("BackgroundImageCache", false, true);}catch (e){}

String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 打开模态对话框.
 * @param isRefresh 是否刷新
 * @param refreshHandle 刷新触发id
 */
function openContextDialog(url, isRefresh, refreshHandle, width, height) {
	if (!width) {
		width = 780;
	}
	
	if (!height) {
		height = 500;
	}
	
	var option = 'dialogWidth=' + width + 'px;dialogHeight=' + height + 'px;resizable=yes';
	var rlt = window.showModalDialog(CONTEXT_PATH + url, null, option)
	
	// 默认设置刷新
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
 * 设置页面元素样式.
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
 * 系统样式.
 */
SysStyle = {
	/**
	 * 设置数据表格头背景, 奇偶行颜色, 鼠标移动颜色.
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
						$(this).attr("title", "双击可快速选定");
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
	 * 设置表单表格样式.
	 */
	setFormGridStyle: function() {
		// 设置表单表格样式.
		/*
		$('table.form_grid').find('td').each(function(i) {
			// form表格为两列, 第一列为标签, 第二列为录入框.
			if (i % 2 == 0) {
				$(this).addClass('form-label-td');
			}
		});
		*/
		
		// 设置表单输入框样式.
		$('table.form_grid :text, table.form_grid :password')
			.addClass('userbox_bt')
			.focus(function() {$(this).addClass('sffocus');})
			.blur(function() {$(this).removeClass('sffocus');});
	},
	
	/**
	 * 设置明细信息表格样式.
	 */
	setDetailGridStyle: function() {
		$('table.detail_grid td:even').addClass('headcell').attr('width', '100px');
		$('table.detail_grid td:odd').addClass('valuecell');
	},
	
	/**
	 * 设置按钮样式.
	 */
	setButtonStyle: function() {
		$btn = $(':button, :submit, :reset')
		$btn.addClass('inp_L3');
		
		$btn.mouseover(function() {$(this).addClass('inp_L4'); $(this).removeClass('inp_L3')});
		$btn.mouseout(function() {$(this).addClass('inp_L3'); $(this).removeClass('inp_L4')});
	},
	
	/**
	 * 设置查询表格样式.
	 */
	setSearchGridStyle: function() {
		$('table.search_grid :text').addClass('form-text');
		$('table.search_grid :password').addClass('form-text');
	},
	
	/**
	 * 禁用无权限链接.
	 */
	setNoPrivilegeStyle: function() {
		$('span.no-privilege a').removeAttr('href').removeAttr('onclick');
	},
	
	/**
	 * 设置分页栏样式.
	 */
	setPageNavStyle: function() {
		$btn = $(':button.pagenavbtn')
		$btn.addClass('inp_L1');
		
		$btn.mouseover(function() {$(this).addClass('inp_L2'); $(this).removeClass('inp_L1')});
		$btn.mouseout(function() {$(this).addClass('inp_L1'); $(this).removeClass('inp_L2')});
	},
	
	/**
	 * 添加表单验证.
	 */
	addFormValidate: function() {
		$('form').each(function() {
			var f = $(this);
			if (f.hasClass('validate')) {
				f.validate({
					showErrors: JError.showErrors, 
					clearError: JError.clearError,
					submitHandler: function(form) {
						$(form).append("<input type='hidden' name='YLINK-KEY' value='"+ YToken.callId() +"' />");
						$(form).find(":button, :submit, :reset").attr('disabled', true);
					}
				});
			}
		});
	}
};

/**
 * 表单验证错误提示.
 */
JError = {
	/**
	 * 显示错误信息
	 * @param {} errorMap
	 * @param {} errorList
	 */
	showErrors: function(errorMap, errorList) {
		for ( var i = 0; errorList[i]; i++ ) {
			var error = errorList[i];
			var tipObj = JError.getTipElement(error.element)
			var errObj = JError.getErrElement(error.element)

			// 没有提示信息, 退出.
			if (tipObj == null) {
				return;
			}

			// 有错误信息, 显示错误信息, 隐藏提示信息.
			if (errObj != null) {
				tipObj.hide();
				errObj.show();
			}
			
			// 没有错误信息, 则用错误样式显示提示信息.
			else {
				tipObj.addClass('error_tipinfo');
				tipObj.show();
			}
		}
	},
	
	/**
	 * 清除错误信息
	 */
	clearError: function(element) {
		var tipObj = JError.getTipElement(element);
		var errObj = JError.getErrElement(element);
		
		// 有错误信息对象, 则隐藏错误信息, 显示提示信息.
		if (errObj) {
			errObj.hide();
			tipObj.show();
		}
		
		// 去除提示信息的错误样式.
		if (tipObj != null) {
			tipObj.removeClass('error_tipinfo');
		}
	},
	
	/**
	 * 取得提示信息对象
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
		
		// 查找下一个.
		obj = obj.next();
		return obj.hasClass('field_tipinfo') ? obj : null;
	},
		
	/**
	 * 取得错误信息对象, 一般为录入对象后的第二个, text->infospan->errspan
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
	 * 显示错误信息
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
 * 加载页面.
 * @param boxid 容器id
 * @param url 地址 不含CONTEXT_PATH
 * @param param 参数列表,json形式.
 */
function $jload(boxid, url, param) {
	var box = $('#' + boxid);
	if (box.length == 0) { 
		return;
	}
	
	// LOAD_IMAGE 位于common.js 中.
	box.show().html(LOAD_IMAGE).load(CONTEXT_PATH + url, param, function() {SysStyle.setDataGridStyle();});
}

/**
 * 双签验证
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
 * 取主页面.
 */
function getMainFrame() {
	var page = window;
	var depth = 0;

	while (page.document.body.className.indexOf('top_frame') < 0) {
		page = page.parent;
		depth += 1;
		
		// 最大只查找5级.
		if (depth > 5) {
			return null;
		}
	}
	
	return page;
}

/**
 * 公共区存取, main.js.
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
 * 切换div的显示
 */

function togglediv(divobj)
{
	if (divobj.style.display=='block' || divobj.style.display=='')		
		divobj.style.display='none';
	else
		divobj.style.display='block';
}		
/**
 * Frame加载后的动作
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
 * 弹出模态窗口,宽高参数不传时使用默认值
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
	// LOAD_IMAGE 位于common.js 中.
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
 * 关闭弹出窗口并清除内容
 * @param {Object} dialogId
 */
function closeDetail(dialogId){
	$('#'+dialogId).html('');
	$('#' + dialogId).dialog('close');
}

/**
 * 收起树
 * @param {Object} link
 */
function closeAll(link){
	d.closeAll();
	$("p:first").html('<a href="javascript:openAll(this)" style="color: red">展开</a>');
}
/**
 * 展开树
 * @param {Object} link
 */
function openAll(link){
	d.openAll();
	$("p:first").html('<a href="javascript:closeAll(this)" style="color: red">收起</a>');
}
