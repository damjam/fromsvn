
popUp={
	
	
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
	},
	popUpMerchantInfo:function(bindCode, bindName, params){
		var toUrl=CONTEXT_PATH+'/merchantInfo.do?action=queryPopUpMerchantInfo&bindCode='+bindCode+'&bindName='+bindName;
		if(params&&params!=null){
			toUrl=toUrl+"&"+params;
		}
		layer.open({
			title:'商家',
		    type: 2,
		    area: ['720px', '530px'],
		    fix: false, //不固定
		    maxmin: true,
		    content: toUrl
		});
	},
	popUpParkingInfo:function(parkingSn, params){
		var toUrl="/parkingInfo.do?action=queryPopUpParkingInfo";
		if(params&&params!=null){
			toUrl=toUrl+"&"+params;
		}
		retVal = openContextDialog(toUrl);
		if(retVal){
			resultVal=retVal.split("$");
			$('#'+parkingSn).val(resultVal[0]);
		}
	}
}