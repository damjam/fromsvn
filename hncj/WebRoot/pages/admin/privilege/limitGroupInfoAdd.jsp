<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
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
<f:msg />
	<form action="limitGroupInfoAction.do?action=addLimitGroupInfo" id="limitGroupInfoForm" method="post" class="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid"	>
					  <caption>${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes">权限组名称</td>
						    <td>
						    	<s:textfield name="limitGroupName" class="{required:true}" maxlength="200"></s:textfield>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes" align="right">用户类型</td>
						     <td>
						     	<s:select name="userType" list="#request.userTypeCollections" listKey="id.dictValue" listValue="dictName"/>
							</td>
					   </tr>
					    <tr>
					    	<td align="right">权限节点</td>
					 		  <td>
								<div id="systree" style="margin-top:10px;"></div>
								<script type="text/javascript">
									var d = new dTree('d','dtree/images/system/menu/');
									d.config.folderLinks = false;
									d.config.useCookies = false;
									d.config.check = true;										
									<c:forEach items="${list}" var="element">
										d.add('${element.code}','${element.parentCode}','${element.name}', null, null, null, 'limitIds', '${element.code}');
									</c:forEach>
									document.getElementById('systree').innerHTML = d;
								</script>
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
	</form>	
</body>
</html>
