<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<jsp:directive.page import="flink.util.Paginater;" />

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
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
				$('#selBtn').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
					});
					
					if(i==0 || i>1){
						alert("请选择需要的银行行号");
						return false;
					}
					window.returnValue=selValue;
					window.close();
					
				});
				$('#clearBtn').click(function(){
					var selValue="";
					window.returnValue="$";
					window.close();
				});
				$('#winClose').click(function(){
					window.close();
				});
			});
			
	 	</script>
	 	<title>黄金品种选择</title>
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
							品种代码
						</td>
						<td>
							品种名称
						</td>
						<td>
							最小提货数量  
						</td>
						<td>
							提货步长 
						</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="obj">
						<tr class="shortcut" align="center">
							<td>
								<input name="takeBank" type="radio"" value="${obj.variety_id.value}$${obj.variety_name.value}"/>
							</td>
							<td>
								${obj.variety_id.value}
							</td>
							<td>
								${obj.variety_name.value}
							</td>
							<td>
								<bean:write name="obj" property="min_pickup.numVal" format="#"/>
							</td>
							<td>
								<bean:write name="obj" property="pickup_base.numVal" format="#"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div style="text-align: center; margin-top: 20px;">
				<input type="submit" value="确定" id="selBtn"/>
				<input style="margin-left: 30px;" type="submit" value="清除" id="clearBtn"/>
				<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
			</div>		
		</div>
	</body>
</html>