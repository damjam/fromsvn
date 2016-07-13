try {document.execCommand("BackgroundImageCache", false, true);}catch (e){}

String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 锟斤拷模态锟皆伙拷锟斤拷.
 * @param isRefresh 锟角凤拷刷锟斤拷
 * @param refreshHandle 刷锟铰达拷锟斤拷id
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
	
	// 默锟斤拷锟斤拷锟斤拷刷锟斤拷
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
 * 锟斤拷锟斤拷页锟斤拷元锟斤拷锟斤拷式.
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
 * 系统锟斤拷式.
 */
SysStyle = {
	/**
	 * 锟斤拷锟斤拷锟斤拷锟捷憋拷锟酵凤拷锟斤拷锟�, 锟斤拷偶锟斤拷锟斤拷色, 锟斤拷锟斤拷贫锟斤拷锟缴�.
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
						//if($("#[id^='sel']")[0]){
							$("#selInfo")[0].click();
						//}
					});
					$tr.mouseover(function(){
						$(this).attr("title", "双锟斤拷锟缴匡拷锟斤拷选锟斤拷");
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
	 * 锟斤拷锟矫憋拷锟斤拷锟斤拷锟绞�.
	 */
	setFormGridStyle: function() {
		// 锟斤拷锟矫憋拷锟斤拷锟斤拷锟绞�.
		/*
		$('table.form_grid').find('td').each(function(i) {
			// form锟斤拷锟轿拷锟斤拷锟�, 锟斤拷一锟斤拷为锟斤拷签, 锟节讹拷锟斤拷为录锟斤拷锟�.
			if (i % 2 == 0) {
				$(this).addClass('form-label-td');
			}
		});
		*/
		
		// 锟斤拷锟矫憋拷锟斤拷锟斤拷锟斤拷锟绞�.
		$('table.form_grid :text, table.form_grid :password')
			.addClass('userbox_bt')
			.focus(function() {$(this).addClass('sffocus');})
			.blur(function() {$(this).removeClass('sffocus');});
	},
	
	/**
	 * 锟斤拷锟斤拷锟斤拷细锟斤拷息锟斤拷锟斤拷锟绞�.
	 */
	setDetailGridStyle: function() {
		$('table.detail_grid td:even').addClass('headcell').attr('width', '100px');
		$('table.detail_grid td:odd').addClass('valuecell');
	},
	
	/**
	 * 锟斤拷锟矫帮拷钮锟斤拷式.
	 */
	setButtonStyle: function() {
		$btn = $(':button, :submit, :reset')
		$btn.addClass('inp_L3');
		
		$btn.mouseover(function() {$(this).addClass('inp_L4'); $(this).removeClass('inp_L3')});
		$btn.mouseout(function() {$(this).addClass('inp_L3'); $(this).removeClass('inp_L4')});
	},
	
	/**
	 * 锟斤拷锟矫诧拷询锟斤拷锟斤拷锟绞�.
	 */
	setSearchGridStyle: function() {
		$('table.search_grid :text').addClass('form-text');
		$('table.search_grid :password').addClass('form-text');
	},
	
	/**
	 * 锟斤拷锟斤拷锟斤拷权锟斤拷锟斤拷锟斤拷.
	 */
	setNoPrivilegeStyle: function() {
		$('span.no-privilege a').removeAttr('href').removeAttr('onclick');
	},
	
	/**
	 * 锟斤拷锟矫凤拷页锟斤拷锟斤拷式.
	 */
	setPageNavStyle: function() {
		$btn = $(':button.pagenavbtn')
		$btn.addClass('inp_L1');
		
		$btn.mouseover(function() {$(this).addClass('inp_L2'); $(this).removeClass('inp_L1')});
		$btn.mouseout(function() {$(this).addClass('inp_L1'); $(this).removeClass('inp_L2')});
	},
	
	/**
	 * 锟斤拷颖锟斤拷锟街�.
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
 * 锟斤拷锟斤拷证锟斤拷锟斤拷锟斤拷示.
 */
JError = {
	/**
	 * 锟斤拷示锟斤拷锟斤拷锟斤拷息
	 * @param {} errorMap
	 * @param {} errorList
	 */
	showErrors: function(errorMap, errorList) {
		for ( var i = 0; errorList[i]; i++ ) {
			var error = errorList[i];
			var tipObj = JError.getTipElement(error.element)
			var errObj = JError.getErrElement(error.element)

			// 没锟斤拷锟斤拷示锟斤拷息, 锟剿筹拷.
			if (tipObj == null) {
				return;
			}

			// 锟叫达拷锟斤拷锟斤拷息, 锟斤拷示锟斤拷锟斤拷锟斤拷息, 锟斤拷锟斤拷锟斤拷示锟斤拷息.
			if (errObj != null) {
				tipObj.hide();
				errObj.show();
			}
			
			// 没锟叫达拷锟斤拷锟斤拷息, 锟斤拷锟矫达拷锟斤拷锟斤拷式锟斤拷示锟斤拷示锟斤拷息.
			else {
				tipObj.addClass('error_tipinfo');
				tipObj.show();
			}
		}
	},
	
	/**
	 * 锟斤拷锟斤拷锟斤拷锟斤拷锟较�
	 */
	clearError: function(element) {
		var tipObj = JError.getTipElement(element);
		var errObj = JError.getErrElement(element);
		
		// 锟叫达拷锟斤拷锟斤拷息锟斤拷锟斤拷, 锟斤拷锟斤拷锟截达拷锟斤拷锟斤拷息, 锟斤拷示锟斤拷示锟斤拷息.
		if (errObj) {
			errObj.hide();
			tipObj.show();
		}
		
		// 去锟斤拷锟斤拷示锟斤拷息锟侥达拷锟斤拷锟斤拷式.
		if (tipObj != null) {
			tipObj.removeClass('error_tipinfo');
		}
	},
	
	/**
	 * 取锟斤拷锟斤拷示锟斤拷息锟斤拷锟斤拷
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
		
		// 锟斤拷锟斤拷锟斤拷一锟斤拷.
		obj = obj.next();
		return obj.hasClass('field_tipinfo') ? obj : null;
	},
		
	/**
	 * 取锟矫达拷锟斤拷锟斤拷息锟斤拷锟斤拷, 一锟斤拷为录锟斤拷锟斤拷锟斤拷牡诙锟斤拷锟�, text->infospan->errspan
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
	 * 锟斤拷示锟斤拷锟斤拷锟斤拷息
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
 * 锟斤拷锟斤拷页锟斤拷.
 * @param boxid 锟斤拷锟斤拷id
 * @param url 锟斤拷址 锟斤拷锟斤拷CONTEXT_PATH
 * @param param 锟斤拷锟斤拷锟叫憋拷,json锟斤拷式.
 */
function $jload(boxid, url, param) {
	var box = $('#' + boxid);
	if (box.length == 0) { 
		return;
	}
	
	// LOAD_IMAGE 位锟斤拷common.js 锟斤拷.
	box.show().html(LOAD_IMAGE).load(CONTEXT_PATH + url, param, function() {SysStyle.setDataGridStyle();});
}

/**
 * 双签锟斤拷证
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
 * 取锟斤拷页锟斤拷.
 */
function getMainFrame() {
	var page = window;
	var depth = 0;

	while (page.document.body.className.indexOf('top_frame') < 0) {
		page = page.parent;
		depth += 1;
		
		// 锟斤拷锟街伙拷锟斤拷锟�5锟斤拷.
		if (depth > 5) {
			return null;
		}
	}
	
	return page;
}

/**
 * 锟斤拷锟斤拷锟斤拷锟斤拷取, main.js.
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
 * 锟叫伙拷div锟斤拷锟斤拷示
 */

function togglediv(divobj)
{
	if (divobj.style.display=='block' || divobj.style.display=='')		
		divobj.style.display='none';
	else
		divobj.style.display='block';
}		
/**
 * Frame锟斤拷锟截猴拷亩锟斤拷锟�
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
 * 锟斤拷锟斤拷模态锟斤拷锟斤拷,锟斤拷卟锟斤拷锟斤拷锟斤拷锟绞笔癸拷锟侥拷锟街�
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
	// LOAD_IMAGE 位锟斤拷common.js 锟斤拷.
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
 * 锟截闭碉拷锟斤拷锟斤拷锟节诧拷锟斤拷锟斤拷锟斤拷锟�
 * @param {Object} dialogId
 */
function closeDetail(dialogId){
	$('#'+dialogId).html('');
	$('#' + dialogId).dialog('close');
}

/**
 * 锟斤拷锟斤拷锟斤拷
 * @param {Object} link
 */
function closeAll(link){
	d.closeAll();
	$("p:first").html('<a href="javascript:openAll(this)" style="color: red">展锟斤拷</a>');
}
/**
 * 展锟斤拷锟斤拷
 * @param {Object} link
 */
function openAll(link){
	d.openAll();
	$("p:first").html('<a href="javascript:closeAll(this)" style="color: red">锟斤拷锟斤拷</a>');
}
