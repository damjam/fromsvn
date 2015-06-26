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
				$('#btnCheck').click(function(){
					
					if(window.confirm("�����˵����¼������ɾ����ȷ�ϲ���?")){
						$(':button').attr('disabled', true);
						gotoUrl('/waterRecord.do?action=checkAll');
						
					}
				});
				$('#btnImp').click(function(){
					gotoUrl('/waterRecord.do?action=toImport');
				});
			});
			function check(id){
				if(window.confirm("�����˵����¼������ɾ����ȷ�ϲ���?")){
					gotoUrl('/waterRecord.do?action=check&id='+id);
				}
			}
			function delRecord(id){
				if(window.confirm("ȷ��ɾ��?")){
					gotoUrl('/waterRecord.do?action=delete&id='+id);
				}
			}
			
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/waterRecord.do?action=list" styleId="queryForm">
			<!-- ��ѯ������ -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								��������
							</td>
							<td>
								<html:text property="startCreateDate" styleId="startCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<html:text property="endCreateDate" styleId="endCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
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
								<input type="button" value="ȫ�������˵�" id="btnCheck"/>&nbsp;
								<input type="button" value="����ˮ���¼" id="btnImp"/>&nbsp;
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
						 	<td >��ˮ�·�</td>
						    <td >���ڶ���</td>
						    <td >���ڳ�������</td>
						    <td >���ڶ���</td>
						    <td >���ڳ�������</td>
						    <td >ʵ������</td>
						    <td >����ʱ��</td>
						    <td >����</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.recordMonth}</td>
								<td>${element.prenum}</td>
								<td>${element.preRecordDate}</td>
								<td>${element.curnum}</td>
								<td>${element.curRecordDate}</td>
								<td>${element.num}</td>
								<td><bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td class="redlink">
							    	<logic:equal value="00" name="element" property="state">
							    		<c:if test="${element.num > 0}">
							    			<a href="javascript:check(${element.id})" title="�����˵��󽫲���ɾ��">�����˵�</a>
							    		</c:if>
							    		<a href="javascript:delRecord(${element.id})">ɾ��</a>
							    	</logic:equal>
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
