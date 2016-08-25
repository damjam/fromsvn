<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>修改角色信息</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
	</head>
<body>

<f:msg />
	<form action="/userInfoAction.do?action=menuBind" id="userInfoForm" class="validate">
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					  <tr>
						    <td class="formlabel">登录名</td>
						    <td>${SESSION_USER.loginId}</td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">用户名称</td>
						    <td>${SESSION_USER.userName}</td>
					   </tr>
					   
					    <tr>
							<td class="formlabel nes">快捷菜单</td>
							<td>
								<div id="systree" style="margin-top: 10px;"></div>
								<script type="text/javascript">		
									var d = new dTree('d','dtree/images/system/menu/');
									d.config.folderLinks = false;
									d.config.useCookies = false;
									d.config.check = true;
									<c:forEach var="element" items="avaibleList">
										d.add('${element.code}','${element.parentCode}','${element.name}', null, null, null, 'privilege', '${element.code}');
									</c:forEach>
									document.getElementById('systree').innerHTML = d;
									
									//设置已经选中的值
										var selectStr = '';
										<c:forEach var="element" items="selectList">
											selectStr = selectStr + ",{menudm:'${element.code}'}";
										</c:forEach>
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
												//alert(funcs.funcs[n].menudm+'对应的权限点在权限树中不存在！');
											}
										}
								</script>
							</td>
						</tr>					   
					   <tr>
							<td width="100" height="30" align="right">&nbsp;</td>
							<td height="30" colspan="3">
								<input type=submit alt=" 提 交 " value=" 提 交 "/>
								<%@ include file="/pages/layout/goback.jsp" %>
							</td>
						</tr>
				  </table>
				</div>
		</div>	
	</form>	
</body>
</html>
