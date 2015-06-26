<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>�ֵ����ҳ��</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/paginater.js"/>

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">
			 $(function(){
				$("#btnQry").click(function(){
					$("#sysDictFrm").submit();
				});
				
				$("#btnClear").click(function(){
					FormUtils.reset("#sysDictFrm");
				});
				
				$("#btnAdd").click(function(){
					gotoUrl("/sysDictAction.do?action=toAddPage");
				});
				
			 });
			
			 function deletesysDict(dictValue,dictType){
				 var url="/sysDictAction.do?action=deleteSysDict&dictValue="+dictValue+"&dictType="+dictType;
				 gotoUrl(url);
			 }
			 
			 function gotosysDictResource(limitId){
				 var url="/sysDictResourceAction.do?action=listsysDictResource&limitId="+limitId;
				 gotoUrl(url);
			 }
		</script>
	</head>
    
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg"/>

		<html:form action="/sysDictAction.do?action=listSysDict" styleId="sysDictFrm" >
			<div class="userbox">
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" >
						 <caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">����</td>
							<td><html:text property="dictName"></html:text></td>
							<td class="formlabel">����</td>
							<td>
								<html:select property="dictType">
									<html:option value="">---ȫ��---</html:option>
									<html:options labelProperty="name" property="value" collection="dictTypes"/>
								</html:select>
							</td>
						</tr>
						<tr>
							<td>
							</td>	
							<td>
								<input type="button" value="��ѯ"  id="btnQry"/>&nbsp;
								<input type="button" value="���"  id="btnClear"/>&nbsp;
								<input type="button" value="����"  id="btnAdd"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>
			</div>
			
			<!-- �����б��� -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						    <td>ֵ</td>
						    <td>����</td>
						    <td>����</td>
						    <td>��ע</td>
						    <!-- 
						    <td>����</td> -->
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.id.dictValue}</td>
							    <td>${element.dictName}</td>
							     <td><f:type className="SysDictType" value="${element.id.dictType}" /></td>
							     <td>${element.remark}</td>
							     <!-- 
							    <td align="center">
							       <span class="redlink">
							       		<html:link href="javascript:deletesysDict('${element.id.dictValue}','${element.id.dictType}')" styleId="hrefDelete">ɾ��</html:link>
							 	   </span>
							  </td> -->
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div>
		</html:form>
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>