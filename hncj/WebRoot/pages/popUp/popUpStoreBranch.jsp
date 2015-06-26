<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/common.js"/>
		<f:css href="/css/page.css" />
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<base target="_self"/>
		<script type="text/javascript">
			
			$(function(){
				$('#selButton').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
					});
					
					if(i==0 || i>1){
						alert("请选择需要的记录");
						return false;
					}
					window.returnValue=selValue;
					window.close();
					
				});
				$('#clearButton').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				$('#winClose').click(function(){
					window.close();
				});
			});
			
	 	</script>
	 	<title>提货网点选择</title>
	 	
	</head>

	<body>

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<thead>
					<tr align="center" class="titlebg">
						<td>
							请选择
						</td>
						<td>
							网点号
						</td>
						<td>
							网点名称
						</td>
						<td>
							提货仓库
						</td>
						<td>
							提货联系人
						</td>
						<td>
							仓库地址
						</td>
						<td>
							网点联系人
						</td>
						<td>
							网点地址
						</td>
						<td>
							联系电话
						</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="branch">
						<tr class="shortcut" align="center">
							<td>
								<input name="takeBank" type="radio"" value="${branch.branch_id}$${branch.branch_name}"/>
							</td>
							<td>
								${branch.branch_id}
							</td>
							<td>
								${branch.branch_name}
							</td>
							<td>
								${branch.stor_id}
							</td>
							<td>
								${branch.contacter}
							</td>
							<td>
								${branch.addr}
							</td>
							<td>
								${branch.b_contacter}
							</td>
							<td>
								${branch.b_addr}
							</td>
							<td>
								${branch.tel}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div style="text-align: center; margin-top: 20px;">
				<input type="submit" value="确定" id="selButton"/>
				<input style="margin-left: 30px;" type="submit" value="清除" id="clearButton"/>
				<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
			</div>		
		</div>
	</body>
</html>