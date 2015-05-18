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
					var url="/billTrack.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id){
				if(window.confirm("ȷ��ɾ��?")){
					gotoUrl('/billTrack.do?action=delete&id='+id);
				}
			}
			function discard(id){
				if(window.confirm("ȷ�ϲ�����ʾ�ü�¼?")){
					var url="/billTrack.do?action=discard&id="+id;
					gotoUrl(url);
				}
			}
			function sendNotice(id,cel){
				if(cel == ''){
					//alert('û�пɷ��͵��ֻ�����');
					//return;
				}
				//if(window.confirm("�����Ͷ�����"+cel+"��ȷ�ϲ���?")){
					var url="/billTrack.do?action=sendNotice&id="+id;
					gotoUrl(url);  
				//}
			}
			function generate(){
				gotoUrl("/billTrack.do?action=generateAll");
				$('#queryForm').find(':button, :submit, :reset').attr('disabled', true);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/billTrack.do?action=list" styleId="queryForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								���ݱ��
							</td>
							<td>
								<html:text property="houseSn" styleId="houseSn" maxlength="10"/>
							</td>
							<td class="formlabel">
								ҵ������
							</td>
							<td>
								<html:text property="ownerName" styleId="ownerName" maxlength="10"/>
							</td>
							<td class="formlabel">
								��ϵ�绰
							</td>
							<td>
								<html:text property="ownerCel" styleId="ownerCel" maxlength="10"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								�˵�����
							</td>
							<td>
								<html:select property="billType" style="width:166px">
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="billTypes" property="value" labelProperty="name" />
								</html:select>
							</td>
							<td class="formlabel">
								��������
							</td>
							<td>
								<html:select property="leftDays" style="width:166px">
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="remainDays" property="value" labelProperty="name" />
								</html:select>
							</td>
							<td class="formlabel">
								��������
							</td>
							<td>
								<html:select property="overDays" style="width:166px">
									<html:option value="">---ȫ��---</html:option>
									<html:options collection="exceedDays" property="value" labelProperty="name" />
								</html:select>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								����ʽ
							</td>
							<td>
								<html:select property="orderType">
									<html:option value="id">Ĭ������</html:option>
									<html:option value="houseSn">���ݱ��</html:option>
								</html:select>
							</td>
						</tr>	
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="��ѯ" id="btnQry"/>&nbsp;
								<input type="button" value="����" id="btnClear" />&nbsp;
								<c:if test="${sessionScope.SESSION_USER.userType eq 'superUser'}">
									<input type="button" value="�����˵�����" id="" onclick="generate()"/>&nbsp;
								</c:if>
								<!-- 
								<input type="button" value="����" id="btnAdd"/> -->
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
						 	<td>���ݱ��</td>
						 	<td>ҵ������</td>
						    <td>�˵�����</td>
						    <td>�˵���</td>
						    <td>����ʱ��</td>
						    <td>ʣ������</td>
						    <td>Ƿ������</td>
						    <td>��ϵ�绰</td>
						    <td>��֪ͨ����</td>
						    <td>����</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.ownerName}</td>
								<td><f:type className="BillType" value="${element.billType}" /> </td>
								<td>${element.billId}</td>
								<td>${element.expireDate}</td>
								<td>${element.leftDays}</td>
								<td>${element.overDays}</td>
								<td>${element.ownerCel}</td>
								<td>${element.noticeTimes}</td> 
							    <td class="redlink">
							    	<a href="javascript:discard('${element.id}')" >��������</a>
							    	 
							    	<a href="javascript:sendNotice('${element.id}','${element.ownerCel}')" >���֪ͨ</a>
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
