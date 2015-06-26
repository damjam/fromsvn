
popUp={
	
	
	//弹出业务类型对话框，busiNo，tempName 返回控件的id，params url后跟的字符传
	popUpTradeRouteTemplate:function(tempId,tempName,params){
		var toUrl="/routeTemplate.do?action=queryPopUpTradeRouteTemplate";
		if(params && params!=""){
			toUrl+="&"+params;
		}
		var retval=openContextDialog(toUrl);
		if(retval){
			var ss=retval.split("$");
	
			if(null!=tempId && ""!=tempId){
				$('#'+tempId).val(ss[0]);
			}
			
			if(null!=tempName && ""!=tempName){
				$('#'+tempName).val(ss[1]);
			}
		}
	},
	
	popUpPrivilege:function(limitId,limitName,params){
		var toUrl="/privilegeAction.do?action=queryPopUpPrivilege";
		if(params&&params!=null){
			toUrl=toUrl+"&"+params;
		}
		
		var retVal=openContextDialog(toUrl);
		if(retVal){
			resultVal=retVal.split("$");
			$('#'+limitId).val(resultVal[0]);
			$('#'+limitName).val(resultVal[1]);
		}
	},
	
	popUpRoleInfo:function(userId){
		var toUrl="/roleInfoAction.do?action=queryPopUpRoleInfo&userId="+userId;
		openContextDialog(toUrl);
	},
	popUpStoreBranch:function(branchId, branchName, params){
		var toUrl="/investGoldAction.do?action=queryStoreBranch";
		if(params&&params!=null){
			toUrl=toUrl+"&"+params;
		}
		retVal = openContextDialog(toUrl);
		if(retVal){
			resultVal=retVal.split("$");
			$('#'+branchId).val(resultVal[0]);
			$('#'+branchName).val(resultVal[1]);
		}
	},
	popUpgoldVariety:function(goldType, goldTypeName, params){
		var toUrl="/investGoldAction.do?action=queryGoldVariety";
		if(params&&params!=null){
			toUrl=toUrl+"&"+params;
		}
		retVal = openContextDialog(toUrl);
		if(retVal){
			resultVal=retVal.split("$");
			$('#'+goldType).val(resultVal[0]);
			$('#'+goldTypeName).val(resultVal[1]);
		}
	}
}