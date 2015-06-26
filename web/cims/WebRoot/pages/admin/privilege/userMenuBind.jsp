<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>�޸Ľ�ɫ��Ϣ</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="/userInfoAction.do?action=menuBind" styleId="userInfoForm" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>�û���ݲ˵�</caption>
					  <tr>
						    <td class="formlabel">��½��</td>
						    <td>${SESSION_USER.loginId}</td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">�û�����</td>
						    <td>${SESSION_USER.userName}</td>
					   </tr>
					   
					    <tr>
							<td class="formlabel nes">��ݲ˵�</td>
							<td>
								<logic:present name="avaibleList">
								<div id="systree" style="margin-top: 10px;"></div>
								<script type="text/javascript">		
									var d = new dTree('d','dtree/images/system/menu/');
									d.config.folderLinks = false;
									d.config.useCookies = false;
									d.config.check = true;
									<logic:iterate id="element" name="avaibleList">
										d.add('${element.code}','${element.parentCode}','${element.name}', null, null, null, 'privilege', '${element.code}');
									</logic:iterate>
									document.getElementById('systree').innerHTML = d;
									
									//�����Ѿ�ѡ�е�ֵ
									<logic:present name="selectList">
										var selectStr = '';
										<logic:iterate id="element" name="selectList">
											selectStr = selectStr + ",{menudm:'${element.code}'}";
										</logic:iterate>
										if(selectStr.length>0){
											selectStr = selectStr.substring(1);
										}
										//alert(selectStr);
										var funcs = eval("("+"{funcs:["+selectStr+"]}"+")");
										var node = '';
										for(var n=0; n<funcs.funcs.length;n++){
											node = d.co(funcs.funcs[n].menudm);
											if(node){
											   node.checked = true;
											}else{
												//alert(funcs.funcs[n].menudm+'��Ӧ��Ȩ�޵���Ȩ�����в����ڣ�');
											}
										}
									</logic:present>
								</script>
								</logic:present>
							</td>
						</tr>					   
					   <tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type=submit alt=" �� �� " value=" �� �� "/>
								<%@ include file="/pages/layout/goback.jsp" %>
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
	</html:form>	
	<!--��Ȩ����-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
