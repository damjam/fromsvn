function getPrivilegeValue() {
	var selids = d.getCheckedNodes();
	var str = "";
	for (var n = 0; n < selids.length; n++) {
		str += selids[n] + ";";
	}
	$('#privilege').val(str);
	$('#roleForm').attr('action', CONTEXT_PATH+'/roleInfoAction.do?action=add&privilege='+str).submit();
}
function getUpdatePrivilegeValue() {

	var selids = d.getCheckedNodes();
	var str = "";
	for (var n = 0; n < selids.length; n++) {
		str += selids[n] + ";";
	}
	$('#privilege').val(str);
	$('#roleForm').attr('action', CONTEXT_PATH+'/roleInfoAction.do?action=update&privilege='+str).submit();
}