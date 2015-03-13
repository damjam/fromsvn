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
				
			});
			function deposit(id){
				gotoUrl('/account.do?action=toDeposit&id='+id);
			}
			function withdraw(id){
				gotoUrl('/account.do?action=toWithdraw&id='+id);
			}
			function cancelAcct(id){
				if(window.confirm("ȷ������?")){
					gotoUrl('/account.do?action=cancel&id='+id);
				}
				
			}
			function detail(id){
				window.open(CONTEXT_PATH+'/account.do?action=detail&acctNo='+id);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/account.do?action=list" styleId="queryForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								ҵ������
							</td>
							<td>
								<html:text property="ownerName" styleId="ownerName" maxlength="10"/>
							</td>
							<td class="formlabel">
								���ݱ��
							</td>
							<td>
								<html:text property="houseSn" styleId="houseSn" maxlength="10"/>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="��ѯ" id="btnQry"/>&nbsp;
								<input type="button" value="����" id="btnClear" />&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- �����б��� -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >���ݱ��</td>
						 	<td >ҵ������</td>
						 	<td >�˻����</td>
						    <!-- 
						    <td >Ƿ�Ѵ���</td>
						    <td >�ۼ�Ƿ������</td> -->
						    <td >����ʱ��</td>
						    <td >״̬</td>
						    <td >����</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.ownerName}</td>
								<td><bean:write name="element" property="balance" format="##0.00"/></td>
								<td><bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td><f:state className="OwnerState" value="${element.state}"/> </td>
							    <td class="redlink">
							    	<logic:equal value="00" name="element" property="state">
							    		<a href="javascript:deposit('${element.id}')" >��ֵ</a>
							    		<a href="javascript:withdraw('${element.id}')" >����</a>
							    		<!-- 
							    		<a href="javascript:cancelAcct('${element.id}')" >����</a> -->
							    	</logic:equal>
							    	<a href="javascript:detail('${element.id}')" >�鿴���׼�¼</a>
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
