MainPage = {
	/**
	 * ����ģ�鶥����ť
	 */
	loadTopButton: function(parentCode, privilegeCode) {
		var param = {parentCode: parentCode, privilegeCode: privilegeCode};
		$('#topbutton').load(CONTEXT_PATH + "/login.do?action=loadUserPrivilegeTopButton", param);
	}
}

// ������.
var GLOBAL_MEM = [];