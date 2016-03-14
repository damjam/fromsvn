<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>修改角色</title>
		
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
					FormUtils.reset("roleInfoForm");
				});
				
				$('#btnReturn').click(function(){
					var url="/roleInfoAction.do?action=listRoleInfo";;
					gotoUrl(url);
				});
				$('#btnSubmit').click(function(){
					if(!FormUtils.hasSelected('limitIds')){
						alert('请选择权限点');
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

<f:msg />
	<form action="roleInfoAction.do?action=updateRoleInfo" id="roleInfoForm" method="post" class="validate">
		<s:hidden name="roleId"/>
		<s:hidden name="limitGroupId"/>
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
					  <caption>${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes" align="right">角色名称</td>
						    <td>
						    	<s:textfield name="roleName" class="{required:true}" maxlength="40"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					    <tr>
						    <td align="right">备注</td>
						    <td>
						    	<s:textfield name="remark"  maxlength="100"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes" align="right">权限组</td>
						     <td>
						     	${limitGroupName}
						     </td>
					   </tr>
					    <tr>
					    	<td class="formlabel nes">权限节点</td>
					    	<td id="limitTree">
								<p style="margin-top:10px; margin-left: 20px;">
									<a href="javascript:openAll(this)" style="color: red">展开</a>
								</p>
								<div id="systree" style="margin-top: 10px;"></div>
								<script type="text/javascript">		
									d = new dTree('d','dtree/images/system/menu/');
									d.config.folderLinks = false;
									d.config.useCookies = false;
									d.config.check = true;
									<c:forEach items="${list}" var="element">
										d.add('${element.code}','${element.parentCode}','${element.name}', null, null, null, 'limitIds', '${element.code}');
									</c:forEach>
									
									document.getElementById('systree').innerHTML = d;
									
									//设置已经选中的值
										var selectStr = '';
										<c:forEach var="element" items="${rolePrivilegeList}">
											selectStr = selectStr + ",{menudm:'${element.id.limitId}'}";
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
						     
						     <td colspan="2" align="center">
								 <input type="button" id="btnSubmit" value="提交"/>&nbsp;
								 <input type="button" id="btnReturn" value="返回"/>&nbsp;
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
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
