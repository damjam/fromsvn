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
		
		<title>定投计划列表</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				$('#btnAdd').click(function(){
					var url="/investPlanAction.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			function oper(type, planNo,orderNo){
				if(type == 'U'){
					gotoUrl('/investPlanAction.do?action=toUpdate&acctNo='+planNo+"&orderNo="+orderNo);
				}
				if(type == 'P'){
					if(confirm("确定要暂停吗？")){
						gotoUrl('/investPlanAction.do?action=doPause&acctNo='+planNo+"&orderNo="+orderNo);
						return true;
					}else{
					 	return false;
					 }
					}
				if(type == 'R'){
					if(confirm("确定要恢复吗？")){
						gotoUrl('/investPlanAction.do?action=doRecover&acctNo='+planNo+"&orderNo="+orderNo);
						return true;
					}else{
					 	return false;
					 }
				}
				if(type == 'C'){
					if(confirm("取消之后不能再恢复，确定要取消吗?")){
						gotoUrl('/investPlanAction.do?action=doCancel&acctNo='+planNo+"&orderNo="+orderNo);
					 	return true;
					}else{
					 	return false;
					 }
				}
				if(type == 'B'){
// 					gotoUrl('/investPlanAction.do?action=doCancel&planNo='+planNo+"&orderNo="+orderNo);
				alert('功能暂未实现！');
				}
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/investPlanAction.do?action=list" styleClass="validate-tip" styleId="dataForm">
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
									<html:options collection="signContracts" labelProperty="investAcctNo" property="investAcctNo"/>
								</html:select>
							</td>
							<td class="formlabel">
								状态
							</td>
							<td>
								<html:select property="planState">
									<html:option value="">--全部--</html:option>
									<html:options collection="planStates" labelProperty="name" property="value"/>
								</html:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="submit" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" onclick="FormUtils.reset('dataForm');"/>&nbsp;
								<input type="button" value="新增" id="btnAdd"/>
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
						 	<td width="8%">计划号</td>
						    <td>定投模式</td>
<!-- 						    <td>定投类别</td> -->
						    <td>定投周期</td>
						    <td>定投金额/重量</td>
						    <td>支付渠道</td>
						    <td>状态</td>
						    <td>操作</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.order_no.value}</td>
								<td>
									<f:type className="AipMode" value="${element.aip_mode.value}" />
								</td>
<!-- 								<td> -->
<%-- 									${element.aip_type.value} --%>
<!-- 								</td> -->
								<td>
									<f:type className="AipPeriod" value="${element.aip_periods.value}" />
								</td>
							    <td>${element.aip_amount.value}</td>
							    <td>${element.account_no.value}</td>
							    <td>${element.plan_stat.value}</td>
							    <td class="redlink">
							    	<c:if test="${element.plan_stat.value eq '生效'}">
							    		<a href="javascript:void(0)" onclick="oper('U', '${element.aip_no.value}','${element.order_no.value}')">变更</a>
							    		<a href="javascript:void(0)" onclick="oper('P', '${element.aip_no.value}','${element.order_no.value}')">暂停</a>
							    	</c:if>
							    	<c:if test="${element.plan_stat.value eq '暂停'}">
							    		<a href="javascript:void(0)" onclick="oper('R', '${element.aip_no.value}','${element.order_no.value}')">恢复</a>
							    	</c:if>
							    	<c:if test="${element.plan_stat.value eq '生效' or element.plan_stat.value eq '暂停'}">
							    	<!-- 
							    		<a href="javascript:void(0)" onclick="oper('G'), '${element.aip_no.value}','${element.order_no.value}'">实物赎回</a>
							    		<a href="javascript:void(0)" onclick="oper('M'), '${element.aip_no.value}','${element.order_no.value}'">现金赎回</a>
							    	 -->	
							    		<a href="javascript:void(0)" onclick="oper('C', '${element.aip_no.value}','${element.order_no.value}')">取消</a>
							    	</c:if>
							    </td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
