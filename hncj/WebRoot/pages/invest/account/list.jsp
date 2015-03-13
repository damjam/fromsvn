<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<html>
	<head>
		
		<title></title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.blockUI.js"/>
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			var aipNo = "";
			function cancelAcct(acctNo, state){
				if(state == '1'){
					//如果已解约，直接执行销户
					gotoUrl("/investAcctAction.do?action=cancelInvestAcct&acctNo="+acctNo+"&state="+state);
				}else if(state == '6'){
					//结果返回时,根据状态进行解约或销户
					aipNo = acctNo;
					window.open('${CONTEXT_PATH}/investAcctAction.do?action=cancelInvestAcct&acctNo='+acctNo+"&state="+state,'','');
		 		 	$.blockUI({ message: $('#block'), css: { width: '275px' } }); 
					
				}
				gotoUrl("/investAcctAction.do?action=cancelInvestAcct&acctNo="+acctNo);
			}
			
			function cancelSign(acctNo, state){
				aipNo = acctNo;
				//if(confirm("在变更改银行卡号前需对原卡号解约，是否继续?")){
				window.open('${CONTEXT_PATH}/investAcctAction.do?action=cancelSign&acctNo='+acctNo+"&state="+state,'','');
		 		$.blockUI({ message: $('#block'), css: { width: '275px' } });
				//}
			}
			
			function modify(acctNo, state){
				aipNo = acctNo;
				if(state == '1'){
					if(window.confirm("更改银行账户前需对当前账户解约，是否继续？")){
						window.open('${CONTEXT_PATH}/investAcctAction.do?action=cancelInvestAcct&acctNo='+acctNo+"&state="+state,'','');
		 		 		$.blockUI({ message: $('#block'), css: { width: '275px' } }); 
					}
				}else if(state == '6'){
					gotoUrl("/investAcctAction.do?action=toUpdateInvestAcct&acctNo="+acctNo);
				}
			}
			
			function toAdd(){
				gotoUrl("/investAcctAction.do?action=toAddSign");
			}
			
			function checkCancelSign(){
				alert(aipNo);
				gotoUrl("/investAcctAction.do?action=checkCancel&acctNo="+aipNo);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/investAcctAction.do?action=list" styleId="dataForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								定投账号
							</td>
							<td>
								<html:select property="acctNo">
									<html:option value="">---全部---</html:option>
									<html:options collection="signContracts" labelProperty="investAcctNo" property="investAcctNo"/>
								</html:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="submit" value="查询"/>&nbsp;
								<input type="button" value="重置" onclick="FormUtils.reset(this.form)"/>&nbsp;
								<input type="button" value="新增" onclick="toAdd()"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						    <td>定投账号</td>
						    <td>签约行别</td>
						    <td>卡号</td>
						    <td>签约时间</td>
						    <td>状态</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>
									${element.aipNo}
								</td>
							    <td>
							   		无(当前接口不支持显示行别)
							    </td>
								<td>
									${element.accountNo}
								</td>
							    <td>
							   	 	${element.createTime}
							    </td>
							    <td>
							    	${element.acctState}
							    </td>
							    <td align="center">
							       <span class="redlink">
<%-- 							       <a href="javascript:void(0)" onclick="cancel('${element.aip_no.value}',' ${element.account_no.value}');">解约</a> --%>
<%-- 							 	   		<c:if test="${element.is_sign.value eq '0'}"> --%>
<%-- 							       			<a href="javascript:void(0)" onclick="cancel('${element.aip_no.value}');">签约</a> --%>
<%-- 							 	   		</c:if> --%>
							 	   		<c:if test="${element.state eq '1'}">
							 	   			<a href="javascript:void(0)" onclick="modify('${element.aipNo}', '${element.state}');">更改银行账户绑定 </a>
							       			<a href="javascript:void(0)" onclick="cancelAcct('${element.aipNo}', '${element.state}');">销户</a>
							       			<a href="javascript:void(0)" onclick="cancelSign('${element.aipNo}', '${element.state}');">解约</a>
							 	   		</c:if>
							 	   		<c:if test="${element.state eq '6'}">
							 	   			<a href="javascript:void(0)" onclick="modify('${element.aipNo}', '${element.state}');">更改银行账户绑定 </a>
							 	   			<a href="javascript:void(0)" onclick="cancelAcct('${element.aipNo}', '${element.state}');">销户</a>
							 	   			<a href="javascript:void(0)" onclick="addSign('${element.aipNo}', '${element.state}');">签约</a>
							 	   		</c:if>
							 	   </span>
							  </td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
			</div> 
		</html:form>
		<div id="block" style="display:none; cursor: default;padding: 20px;"> 
	        <input type="button" value="解约成功" onclick="checkCancelSign()"/>&nbsp;&nbsp; 
	        <input type="button" value="解约失败" onclick="checkCancelSign()"/> 
		</div>
	</body>
</html>
