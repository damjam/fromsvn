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
		<f:css href="/css/page.css" />
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<script type="text/javascript">$(function(){
				$('#selBankAddress').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("请选择需要的地区编号");
						return false;
					}
					
					window.returnValue=selValue;
					window.close();
					
					
				});
				
				$('#clearBankAddress').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				
				$('#winClose').click(function(){
					window.close();
				});
				
			});
			
	    </script>
	</head>

	<body>
		<form id="query" action="addressInfo.do?action=queryPopUpAddressInfo" method="post" >
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
					<div class="contentb">
						<table width="100%">
							<tr>
								<td align="left">
									<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">地区码选择</span>
								</td>
							</tr>
						</table>

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td height="30" align="right" nowrap="nowrap">
									地区编号
								</td>
								<td  height="30">
									<input type="text"  style="width: 260px;" name="addrNo" id="addrNo" value="${addressInfo.addrNo}"/>
								</td>
								<td height="30" align="right">
									地区名称
								</td>
								<td height="30">
									<input  style="width: 260px;" name="addrName" type="text" id="addrName" value="${addressInfo.addrName}"/>
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
									<input onclick="clearData();" style="margin-left: 30px;" type="button" value="清除" />
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
								地区编号
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								地区名称
							</th>
						</tr>
					</thead>
					
					
					<tbody>
						<c:if test="${empty addressInfoList}">
							<tr>
								<td colspan="10">
									没有数据
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty addressInfoList}">
							<c:forEach items="${addressInfoList}" var="addressInfo">
								<tr class="shortcut">
									<td align="center" nowrap="nowrap">
										<input name="addrName" type="radio" value="${addressInfo.addrId}$${addressInfo.addrName}"/>
									</td>
								
									<td align="center" nowrap="nowrap">
										&nbsp;${addressInfo.addrNo}
									</td>
									<td align="center" nowrap="nowrap">
										&nbsp;${addressInfo.addrName}
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
					<input type="submit" value="确定" id="selBankAddress"/>
					<input style="margin-left: 30px;" type="submit" value="清除" id="clearBankAddress"/>
					<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
				</td>
			</tr>
		</table>
		<br />
		<br />
		<br />
	</body>
</html>