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
					var url="/houseInfo.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id){
				if(window.confirm("ȷ��ɾ��?")){
					gotoUrl('/houseInfo.do?action=delete&id='+id);
				}
			}
			function generate(){
				gotoUrl('/houseInfo.do?action=generateHouseSn');
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/houseInfo.do?action=list" styleId="queryForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								¥��
							</td>
							<td>
								<html:select property="buildingNo" style="width:166px">
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="buildingNos" property="key" labelProperty="value" />
								</html:select>
							</td>
							<td class="formlabel">
								��Ԫ
							</td>
							<td>
								<html:select property="unitNo" style="width:166px">
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="unitNos" property="key" labelProperty="value" />
								</html:select>
							</td>
							<td class="formlabel">
								¥��
							</td>
							<td>
								<html:select property="floor" style="width:166px">
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="floors" property="key" labelProperty="value" />
								</html:select>
							</td>
						</tr>
						<tr>
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
								<input type="button" value="����" id="btnAdd"/>
								<c:if test="${sessionScope.SESSION_USER.userType eq 'superUser'}">
									<input type="button" value="����������" id="" onclick="generate()"/>&nbsp;
								</c:if>
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
						    <td >¥��</td>
						    <td >��Ԫ</td>
						    <td >¥��</td>
						    <td >����</td>
						    <td >���(ƽ��)</td>
						    <td >��������</td>
						    <td >����ʱ��</td>
						    <td >����</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.buildingNo}</td>
								<td>${element.unitNo}</td>
								<td>${element.floor}</td>
								<td>${element.houseDesc}</td>
								<td><bean:write name="element" property="area" format="##0.00"/></td>
								<td>${element.deliveryDate}</td>
								<td><bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td class="redlink">
							    	<a href="javascript:delInfo('${element.houseSn}')" >ɾ��</a>
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
