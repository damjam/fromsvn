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
		<title>�޸Ľ�ɫ</title>
		
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
					FormUtils.reset("roleInfoForm");
				});
				
				$('#btnReturn').click(function(){
					var url="/roleInfoAction.do?action=listRoleInfo";;
					gotoUrl(url);
				});
				$('#btnSubmit').click(function(){
					if(!FormUtils.hasSelected('limitIds')){
						alert('��ѡ��Ȩ�޵�');
						return;
					}
					FormUtils.submitFirstTokenForm();
				});
				$("#limitGroupId").change(function(){
					var limitGroupId = $('#limitGroupId').val();
					if(limitGroupId == ''||limitGroupId==null){return;}
					$('#limitTree').show().html(LOAD_IMAGE).load(CONTEXT_PATH + '/roleInfoAction.do?action=loadTree&limitGroupId='+limitGroupId, function() {
						SysStyle.setDataGridStyle();
					});
				});
			});
		 
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<html:form action="roleInfoAction.do?action=updateRoleInfo" styleId="roleInfoForm" method="post" styleClass="validate">
		<html:hidden property="roleId"/>
		<html:hidden property="limitGroupId"/>
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
						    <td class="formlabel nes" align="right">��ɫ����</td>
						    <td>
						    	<html:text property="roleName"  styleClass="{required:true}" maxlength="40"/>
						    	<span class="field_tipinfo">����Ϊ��</span>
						    </td>
					   </tr>
					    <tr>
						    <td align="right">��ע</td>
						    <td>
						    	<html:text property="remark"  maxlength="100"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes" align="right">Ȩ����</td>
						     <td>
						     	${limitGroupName}
						     </td>
					   </tr>
					    <tr>
					    	<td class="formlabel nes">Ȩ�޽ڵ�</td>
					    	<td id="limitTree">
					 		<logic:present name="list">
								<p style="margin-top:10px; margin-left: 20px;">
									<a href="javascript:openAll(this)" style="color: red">չ��</a>
								</p>
								<div id="systree" style="margin-top: 10px;"></div>
								<script type="text/javascript">		
									d = new dTree('d','dtree/images/system/menu/');
									d.config.folderLinks = false;
									d.config.useCookies = false;
									d.config.check = true;
									<logic:iterate id="element" name="list">
										d.add('${element.code}','${element.parentCode}','${element.name}', null, null, null, 'limitIds', '${element.code}');
									</logic:iterate>
									
									document.getElementById('systree').innerHTML = d;
									
									//�����Ѿ�ѡ�е�ֵ
									<logic:present name="rolePrivilegeList">
										var selectStr = '';
										<logic:iterate id="element" name="rolePrivilegeList">
											selectStr = selectStr + ",{menudm:'${element.id.limitId}'}";
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
						     
						     <td colspan="2" align="center">
								 <input type="button" id="btnSubmit" value="�ύ"/>&nbsp;
								 <input type="button" id="btnClear" value="���"/>&nbsp;
								 <input type="button" id="btnReturn" value="����"/>&nbsp;
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
