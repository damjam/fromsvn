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
						alert("请选择需要的银行地区编号");
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
	 	<title>银行地区码选择</title>
	 	
	</head>

	<body>

		<form id="query" action="${CONTEXT_PATH}/bankAddress.do?action=queryPopUpBankAddress" method="post" >
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
					<div class="contentb">
						<table width="100%">
							<tr>
								<td align="center">
									<span
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">银行地区码选择</span>
								</td>
							</tr>
						</table>

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr class="shortcut">
								<td  height="30" align="right">
									银行地区码
								</td>
								<td width="270" height="30">
									<input type="text" class="userbox_bt"  name="addrNo" id="addrNo"  value="${bankAddress.addrNo }"/>
								</td>
								<td  height="30" align="right">
									银行地区名称
								</td>
								<td height="30">
									<input class="userbox_bt"  name="addrName" type="text" id="addrName"  value="${bankAddress.addrName }" />
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
									<input style="margin-left: 30px;" type="reset" value="清除" />
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
		

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr></tr>
				<tr></tr>
				<tr></tr>
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
					<c:if test="${empty bankAddressList}">
						<tr>
							<td colspan="10">
								没有数据
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty bankAddressList}">
						<c:forEach items="${bankAddressList}" var="bankAddress">
							<tr>
								<td align="center" nowrap="nowrap">
									<input name="bankAddress" type="radio""" value="${bankAddress.addrNo}$${bankAddress.addrName}"/>
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${bankAddress.addrNo}
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${bankAddress.addrName}
								</td>
							</tr>
						</c:forEach>

					</c:if>
				</tbody>
			</table>
			<f:paginate />
			<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr></tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="30" colspan="3" align="center">
						<input type="submit" value="确定" id="selBankAddress"/>
						<input style="margin-left: 30px;" type="submit" value="清除" id="clearBankAddress"/>
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