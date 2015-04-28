<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#queryForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("queryForm");
				});
				
				$('#btnAdd').click(function(){
					var url="/chargeParam.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function save(){
		 		FormUtils.submitFirstTokenForm();
		 	}
			function updateInfo(id){
				var url="/chargeParam.do?action=toEdit&id="+id;
				gotoUrl(url);  
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/chargeParam.do?action=doAddItem" styleId="queryForm">
			<html:hidden property="id" />
			<!-- �����б��� -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td> </td>
						 	<td >�Ʒ���</td>
						 	<td >�Ʒ�����</td>
						    <td >�Ʒѷ�ʽ</td>
						    <td >�Ʒѹ���</td>
						    <td >����ʱ��</td>
						    <td >��ע</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td><html:multibox property="itemIds" value="${element.id}"/> </td>
								<td>${ element.itemName}</td>
								<td><f:type className="ChargeType" value="${ element.item}" /> </td>
								<td><f:type className="ChargeWay" value="${ element.way}" /> </td>
								<td>${element.ruleDesc}</td>
								<td><bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td>${element.remark }</td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>
				<div class="btnbox clear">
					<input type="button" id="" onclick="save()" value="����"></button>
					<input type="button" id="" onclick="gotoUrl('/chargeParam.do?action=list')" value="����"></button>
				</div>		
			</div> 
		</html:form>
	</body>
</html>
