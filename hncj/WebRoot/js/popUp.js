
popUp={
	
	
	//����ҵ�����ͶԻ���busiNo��tempName ���ؿؼ���id��params url������ַ���
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
	},
	popUpMerchantInfo:function(merchantNo, merchantName, params){
		var toUrl="/merchantInfo.do?action=queryPopUpMerchantInfo";
		if(params&&params!=null){
			toUrl=toUrl+"&"+params;
		}
		retVal = openContextDialog(toUrl);
		if(retVal){
			resultVal=retVal.split("$");
			$('#'+merchantNo).val(resultVal[0]);
			$('#'+merchantName).val(resultVal[1]);
		}
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