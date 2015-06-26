<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/common.js"/>
		<f:css href="/css/page.css" />
		
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>

		<script type="text/javascript">$(function(){
				$('#selPrivilege').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("请选择需要的权限，有且仅有一条");
						return false;
					}
					
					window.returnValue=selValue;
					window.close();
					
					
				});
				
				$('#clearPrivilege').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				
				$('#winClose').click(function(){
					window.close();
				});
				
				
				$('#btnClear').click(function(){
					FormUtils.reset("privilegeFrm")	;
				});
			});
			
	    </script>
	</head>

	<body>
		<form id="privilegeFrm" action="privilegeAction.do?action=queryPopUpPrivilege" method="post" >
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
					<div class="contentb">
						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td colspan="4" align="left">
									<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">权限选择</span>
								</td>
							</tr>
							<tr>
								<td height="30" align="right" nowrap="nowrap">
									权限编号
								</td>
								<td  height="30">
									<input type="text"  style="width: 260px;" name="limitId" id="limitId" value="${privilege.limitId }"/>
								</td>
								<td height="30" align="right">
									权限名称
								</td>
								<td height="30">
									<input  style="width: 260px;" name="limitName" type="text" id="limitName" value="${privilege.limitName }"/>
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="查询" /> 
									<input id="btnClear" style="margin-left: 30px;" type="button" value="清除" />
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

			<!-- 数据列表区 -->
			<div class="tablebox">
				<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th align="center" nowrap="nowrap" class="titlebg">
								请选择
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								权限编号
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								权限名称
							</th>
						</tr>
					</thead>
					
					
					<tbody>
						<c:if test="${empty privilegeList}">
							<tr>
								<td colspan="10">
									没有数据
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty privilegeList}">
							<c:forEach items="${privilegeList}" var="privilege">
								<tr class="shortcut">
									<td align="center" nowrap="nowrap">
										&nbsp;<input name="privilege" type="radio"" value="${privilege.limitId}$${privilege.limitName}"/>
									</td>
								
									<td align="center" nowrap="nowrap">
										&nbsp;${privilege.limitId}
									</td>
									<td align="center" nowrap="nowrap">
										&nbsp;${privilege.limitName}
									</td>
								</tr>
							</c:forEach>

						</c:if>
					</tbody>
				</table>
				<f:paginate />
			</div>
		</form>

		<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td height="30" colspan="4" align="center">
					<input type="submit" value="确定" id="selPrivilege"/>
					<input style="margin-left: 30px;" type="submit" value="清除" id="clearPrivilege"/>
					<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
				</td>
			</tr>
		</table>
		<br />
		<br />
		<br />
	</body>
</html>