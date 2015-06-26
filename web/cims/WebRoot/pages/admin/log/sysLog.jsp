<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/common.js"/>
		<f:css href="/css/page.css"/>


		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">
		</script>
</head>

<body>
	<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
	<!-- ��ѯ������ -->
	<html:form action="sysLog.do?action=querySysLog" method="post" >
		<div class="userbox">
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">
		<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
		  <caption>ϵͳ��־��ѯ</caption>
		  <tr>
		    <td class="formlabel">ģ����</td>
		    <td><input name="limitId" type="text" id="limitId" value ="${param.limitId}" /></td>
		    <td class="formlabel">��־����</td>
		    <td>
				<html:select property="logClass">
					<html:option value="">---��ѡ��---</html:option>
					<html:options collection="logClassTypes" labelProperty="name" property="value"/>
				</html:select>
			</td>
		  </tr>
		   <tr>
		    <td></td>
		    <td colspan="5">
	   			 <input type="submit" value="��ѯ"/>&nbsp;&nbsp;
	   			 <input type="button" value="����" onclick="FormUtils.reset('query')"/>
		    </td>
		  </tr>
		  </table>
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		</div>
	</html:form>
		<!-- �����б��� -->
		<div class="tablebox">
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				  <tr align="center">
				    <th class="titlebg">ģ����</th>
				    <th class="titlebg">ģ������</th>
				    <th class="titlebg">��־����</th>
				    <th class="titlebg">����ʱ��</th>
				    <th class="titlebg">����</tr></th>
				    <!-- 
				    <th class="titlebg">����</th> -->
				  </tr>
				 </thead>
				 <tbody>
			  	<f:showDataGrid name="list" msg=" " styleClass="data_grid">
				<logic:iterate id="sysLog" name="list">
				  <tr align="center">
				    <td>${sysLog.limitId}</td>
				    <td>${sysLog.limitName}</td>
				    <td><f:type className="LogClassType" value="${sysLog.logClass}"/></td>
				    <td>
						<fmt:formatDate value="${sysLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>   
					</td>
					 <td title="${sysLog.content}">${sysLog.contentAbbr}</td>
				  </tr>
				 </logic:iterate>
				 </f:showDataGrid>
				</tbody>
			</table>
			<f:paginate />	
		</div>

	<!--��Ȩ����-->
 	<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
</body>
</html>