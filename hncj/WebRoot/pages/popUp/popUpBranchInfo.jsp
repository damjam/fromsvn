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
				$('#selBranchInfo').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("请选择需要的机构");
						return false;
					}
					window.returnValue=selValue;
					window.close();
					
				});
				
				$('#clearBranchInfo').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				
				$('#btnClear').click(function(){
					$(':text').val('');
				});
				
				$('#winClose').click(function(){
					window.close();
				});
				$('table.data_grid').each(function() {
				var $t = $(this);
				$trs = $t.find('tbody tr');
				$trs.each(function(){
					$tr = $(this);
					$tr.click(function(){
						removeClassAll($trs);
						$(this).addClass("click");
						var radio = $(this).find("td [type='radio']:eq(0)");
						radio.attr("checked", true);
					});
					$tr.dblclick(function(){
						$('#selBranchInfo').click();
					});
					$tr.mouseover(function(){
						$(this).attr("title", "双击可快速选定");
					});
				});
			});
			var removeClassAll = function($trs){
				$trs.each(function(){
					var $tr = $(this);
					$tr.removeClass("click");
					$tr.removeClass("on");
				});
			};
			});
			
	 	</script>
	 	<title>选择机构</title>
	 	
	</head>

	<body>

		<form id="branchInfo" action="${CONTEXT_PATH}/branchAction.do?action=queryPopUpBranchInfo" method="post" >
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
								<td align="left">
									<span
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px; padding-left: 20px;">机构选择</span>
								</td>
							</tr>
						</table>

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr class="shortcut">
								<td  height="30" align="right">
									机构编号
								</td>
								<td width="270" height="30">
									<input name="branchNo"  type="text" id="branchNo"  value="${branchInfoOperForm.branchNo}"/>
								</td>
								<td  height="30" align="right">
									机构名称
								</td>
								<td height="30">
									<input  name="branchName" type="text" id="branchName"  value="${branchInfoOperForm.branchName}" />
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td  height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="查询" />
									<input id='btnClear' style="margin-left: 30px;" type="button" value="清除" />
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
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<thead>
					<tr>
						<th align="center" nowrap="nowrap" class="titlebg">
							请选择
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							机构编号
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							机构名称
						</th>
					</tr>
				</thead>
				
				
				<tbody>
					<c:if test="${empty branchInfoList}">
						<tr>
							<td colspan="10">
								没有数据
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty branchInfoList}">
						<c:forEach items="${branchInfoList}" var="branchInfo">
							<tr>
								<td align="center" nowrap="nowrap">
									<input name="branchInfo" type="radio"" value="${branchInfo.branchNo}$${branchInfo.branchName}"/>
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${branchInfo.branchNo}
								</td>
								<td align="left" nowrap="nowrap">
									&nbsp;${branchInfo.branchName}
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
					<td height="30" colspan="3" align="center">
						<input type="button" value="确定" id="selBranchInfo"/>
						<input style="margin-left: 30px;" type="submit" value="清除" id="clearBranchInfo"/>
						<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
					</td>
				</tr>
			</table>
			<br />
			<br />
			<br />
		</div>
	</body>
</html>