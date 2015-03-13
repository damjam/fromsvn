<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>新增权限组</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/dtree/wtree.js"/>
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			$(function(){
				$('#btnClear').click(function(){
					FormUtils.reset("limitGroupInfoForm");
				});
				
				$('#btnReturn').click(function(){
					var url="/limitGroupInfoAction.do?action=listLimitGroupInfo";;
					gotoUrl(url);
				});
				$('#btnSubmit').click(function(){
					if(!FormUtils.hasSelected('limitIds')){
						alert('请选择权限点');
						return;
					}
					FormUtils.submitFirstTokenForm();
				});
			});
		 
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="limitGroupInfoAction.do?action=addLimitGroupInfo" styleId="limitGroupInfoForm" method="post" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="40%" border="0" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes">权限组名称</td>
						    <td>
						    	<html:text property="limitGroupName"  styleClass="{required:true}" maxlength="200"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes" align="right">用户类型</td>
						     <td>
						     	<html:select property="userType">
						     		<html:options collection="userTypeCollections" property="id.dictValue" labelProperty="dictName"/>
						     	</html:select>
							</td>
					   </tr>
					    <tr>
					    	<td align="right">权限节点</td>
					 		  <td>
					 		  	 <logic:present name="list">
									<div id="systree" style="margin-top:10px;"></div>
									<script type="text/javascript">
										var d = new dTree('d','dtree/images/system/menu/');
										d.config.folderLinks = false;
										d.config.useCookies = false;
										d.config.check = true;										
										<logic:iterate id="element" name="list">
											d.add('${element.code}','${element.parentCode}','${element.name}', null, null, null, 'limitIds', '${element.code}');
										</logic:iterate>
										document.getElementById('systree').innerHTML = d;
									</script>
								</logic:present>
						 	</td>
					   </tr>
					     <tr>
						     
						     <td colspan="2" align="center">
								 <input type="button" id="btnSubmit" value="提交"/>
								 <input type="button" id="btnClear" value="清除"/>
								 <input type="button" id="btnReturn" value="返回"/>
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
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
