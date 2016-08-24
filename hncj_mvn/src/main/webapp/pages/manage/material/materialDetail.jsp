<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/layer/layer.js"/>
		<script type="text/javascript">
			
		</script> 
	</head>
	<body>
		<f:msg styleClass="msg"/>
		<div class="userbox">
			<table class="detail_grid" border="1">
				<caption></caption>
				<tr>
					<td width="20%">名称</td>
					<td width="30%">${item}</td>
					<td width="20%">品牌</td>
					<td width="30%">${brand}</td>
				</tr>
				<tr>
					<td>型号</td>
					<td>${modal}</td>
					<td>责任人</td>
					<td>${manager}</td>
				</tr>
				<tr>
					<td>状态</td>
					<td><f:state className="MaterialState" value="${state}"/></td>
					<td>存放位置</td>
					<td>${place}</td>
				</tr>
				<tr>
					<td>购置时间</td>
					<td>${buyDate}</td>
					<td>购置经手人</td>
					<td>${buyer}</td>
				</tr>
				<tr>
					<td>购置来源</td>
					<td>${source}</td>
					<td>购买价格</td>
					<td><fmt:formatNumber pattern="##0.00" value="${price }"/></td>
				</tr>
				<tr>
					<td>备注</td>
					<td colspan="3">${remark}</td>
				</tr>
			</table>
		</div>
		<!-- 
		<img src="/upload/${poster}" id="img1" style="display:none"/> -->
		<!-- 
		<p align="center">
			<input type="button" onclick="history.go(-1);" class="inp_L3" value="返回"/>
		</p> -->
	</body>
</html>
