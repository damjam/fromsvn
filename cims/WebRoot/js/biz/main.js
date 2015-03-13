MainPage = {
	/**
	 * 加载模块顶部按钮
	 */
	loadTopButton: function(parentCode, privilegeCode) {
		var param = {parentCode: parentCode, privilegeCode: privilegeCode};
		$('#topbutton').load(CONTEXT_PATH + "/login.do?action=loadUserPrivilegeTopButton", param);
	}
}

// 公共区.
var GLOBAL_MEM = [];