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

	<script type="text/javascript">
		$(function(){
			$('#selBusinessClass').click(function(){
				var i=0;
				var selValue="";
				$(':radio').each(function(){
					if($(this).attr('checked')){
						selValue=$(this).val();
						i++;
					}
					
				});
				
				if(i==0 || i>1){
					alert("请选择需要的业务类别");
					return false;
				}
				
				window.returnValue=selValue;
				window.close();
				
				
			});
			
			$('#clearBusinessClass').click(function(){
				var selValue="";
				window.returnValue="$$";
				window.close();
			});
			
			$('#winClose').click(function(){
				window.close();
			});
			
			$('#serviceType').val($('#hidServiceType').val());
			
		});
		
	    </script>
	</head>

	<body>

		<div class="location">
			您当前所在位置：
			<span class="redlink"><a href="javascript: return;">首页</a> </span> > 功能操作 >业务类别管理
		</div>
		<!-- 查询功能区 -->
		<form id="query" action="businessClass.do?action=queryPopUpBusinessClass"
			method="post">
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
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">业务类别选择</span>
								</td>
							</tr>
						</table>

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td width="100" height="30" align="right">
									类别编号
								</td>
								<td width="270" height="30">
									<input type="text"  style="width: 260px;" name="busiClassNo" 
										id="busiClassNo" value="${businessClass.busiClassNo }"/>
								</td>
								<td width="100" height="30" align="right">
									类别名称
								</td>
								<td height="30">
									<input  style="width: 260px;" name="busiClassName" type="text" 
										id="busiClassName" value="${businessClass.busiClassName }"/>
								</td>
							</tr>
							<tr>
								<td width="100" height="30" align="right">
									服务类型
								</td>
								<td width="270" height="30">
									<select name="serviceType"  id="serviceType">
										<option value="">---请选择---</option>
										<option value="1">代收</option>
										<option value="0">代付</option>
									</select>
									<input  type="hidden" id="hidServiceType" value="${businessClass.serviceType }"/>
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td width="100" height="30" align="right">
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

			<!-- 数据列表区 -->
			<div class="tablebox">
				<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th align="center" nowrap="nowrap" class="titlebg">请选择</th>
							<th align="center" nowrap="nowrap" class="titlebg">类别编号</th>
							<th align="center" nowrap="nowrap" class="titlebg">服务类型</th>
							<th align="center" nowrap="nowrap" class="titlebg">类别名称</th>
						</tr>
					</thead>
					
					
					<tbody>
						<c:if test="${empty businessClassList}">
							<tr>
								<td colspan="10">
									没有数据
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty businessClassList}">
							<c:forEach items="${businessClassList}" var="businessClass">
								<tr class="shortcut">
									<td align="center" nowrap="nowrap">
										<input name="businessClass" type="radio"  value="${businessClass.busiClassNo}$${businessClass.busiClassName}$${businessClass.serviceType}"/>
									</td>
									<td align="center" nowrap="nowrap">
										${businessClass.busiClassNo}
									</td>
									<td align="center" nowrap="nowrap">
										<c:if test="${businessClass.serviceType eq '1'}">代收</c:if>
										<c:if test="${businessClass.serviceType eq '0'}">代付</c:if>
									</td>
									<td align="center" nowrap="nowrap">
										${businessClass.busiClassName}
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
					<input type="submit" value="确定" id="selBusinessClass"/>
					<input style="margin-left: 30px;" type="submit" value="清除" id="clearBusinessClass"/>
					<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
				</td>
			</tr>
		</table>
		<br />
		<br />
		<br />
	</body>
</html>