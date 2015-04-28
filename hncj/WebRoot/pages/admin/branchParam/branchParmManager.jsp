<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<link rel="stylesheet" href="css/page.css" type="text/css" media="screen, projection" />
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/common.js" />

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">
			function clearData(){
				FormUtils.reset("query");
			}
		
		   function addbranchParm(){
			   gotoUrl('/branchParmManage.do?action=toAdd');
		   		//window.location.href="pages/admin/branchRunManager/branchParmAdd.jsp";
		   }
		   
		   function check(){
		   		var code=document.getElementById("code").value;
				var parname=document.getElementById("parname").value;
				
				if(code.length==0 && parname.length==0){
					alert("����������һ����ѯ����");
					return false;
				}
				
				return true;
		   }
		   
		   // ���¼���ϵͳ����.
			function reloadbranchParm() {
				$.post(CONTEXT_PATH + '/branchParmManage.do?action=reload', function(data) {
					if (data.indexOf('���¼���ϵͳ����') > -1) {
						alert(data);
					}
					else {
						alert('����ʧ��');
					}
				});
			}
		   function backup1(){
			   if(!window.confirm('ȷ�ϱ���?')){
				   return;
			   }
			   $(':button').attr('disabled', true);
			   $.post(CONTEXT_PATH + '/branchParmManage.do?action=backupData', function(data) {
				   /* 
				   if(data != null){
				    	var jsonObj = eval('(' + data + ')');
					    if (jsonObj.indexOf('') > -1) {
							alert('���ݳɹ�');
						}else {
							alert('����ʧ��');
						}
				    } else {
						alert('����ʧ��');
					}*/
				});
			   $(':button').attr('disabled', false);
		   }
		</script>
</head>

<body>
	 
	<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
	<f:msg/>
<!-- ��ѯ������ -->
	<form id="query" action="branchParmManage.do?action=query" method="post" >
		<div class="userbox">
		<div>
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">
		<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
		  <caption>${ACT.name}</caption>	
		  <tr>
		    <td class="formlabel">��������</td>
		    <td><input type="text" name="code" id="code"  /></td>
		    <td class="formlabel">��������</td>
		    <td><input name="parname" type="text" id="parname" /></td>
		    <td class="formlabel" align="left">&nbsp;</td>
		    <td></td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td colspan="5">
		      <input type="submit" value="��ѯ" id="input_btn2"   />&nbsp;&nbsp;
		      <input onclick="clearData();"   type="button" value="����"  />&nbsp;&nbsp;
		   	  <input onclick="addbranchParm();"  type="button" value="����"   />&nbsp;&nbsp;
			  <input onclick="reloadbranchParm();"  type="button" value="���¼���"   />&nbsp;&nbsp;
			  <!-- 
			  <input onclick="backup1();"  type="button" value="�������ݿ�"   /> -->
		    </td>
		  </tr>
		  </table>
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		</div>
		</div>
	
		<!-- �����б��� -->
		<div class="tablebox">
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
				  <tr>
				    <th align="center" nowrap="nowrap" class="titlebg">��������</th>
				    <th align="center" nowrap="nowrap" class="titlebg">��������</th>
				    <th align="center" nowrap="nowrap" class="titlebg">����ֵ</th>
				    <th align="center" nowrap="nowrap" class="titlebg">��ע</th>
				    <th align="center" nowrap="nowrap" class="titlebg">����</th>
				  </tr>
				 </thead>
				 <tbody>
				  <logic:notEmpty name="branchParmList">
				  	<logic:iterate name="branchParmList" id="branchPram">
					  <tr align="center">
					    <td nowrap="nowrap"><bean:write name="branchPram" property="code" /></td>
					    <td nowrap="nowrap"><bean:write name="branchPram" property="parname" /></td>
					    <td nowrap="nowrap"><bean:write name="branchPram" property="parvalue" /></td>
						<td nowrap="nowrap"><bean:write name="branchPram" property="remark" /></td>
					    <td nowrap="nowrap">
						    <span class="redlink">
						   		 <a href="branchParmManage.do?action=update&code=${branchPram.code}">�޸�</a>
						   		 <!-- 
						   		 <a onclick="return confirm('��ȷ��Ҫɾ����?');" href="branchParmManage.do?action=delete&code=${branchPram.code }" >ɾ��</a>
						   		 -->
						    </span>
					    </td>
					  </tr>
					 </logic:iterate>
					</logic:notEmpty>
				</tbody>
			</table>
			
			<f:paginate />	
		
		</div>
	</form>
	
	<!--��Ȩ����-->
 	<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
</body>
</html>