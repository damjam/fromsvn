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
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#queryForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("queryForm");
				});
				$('#btnAdd').click(function(){
					gotoUrl('/contact.do?action=toEdit');
				});
				
			});
			function update(id){
				gotoUrl('/contact.do?action=toEdit&id='+id);
			}
			
			function delRecord(id){
				if(!window.confirm("确认删除?")){
					return;
				}
				gotoUrl('/contact.do?action=del&id='+id);
			}
			function refund(id){
				if(!window.confirm("确认退款?")){
					return;
				}
				gotoUrl('/contact.do?action=refund&id='+id);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/contact.do?action=list" styleId="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								姓名
							</td>
							<td>
								<html:text property="contactName" styleId="contactName" maxlength="20"/>
							</td>
							<td class="formlabel">
								电话
							</td>
							<td>
								<html:text property="mobile" styleId="mobile" maxlength="20"/>
							</td>
							<td class="formlabel">
								备注关键字
							</td>
							<td>
								<html:text property="remark" styleId="remark" maxlength="20"/>
							</td>
					    </tr>
						
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="新增" id="btnAdd"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >姓名</td>
						 	<td >性别</td>
						 	<td >行业</td>
						    <td >职位</td>
						    <td >电话</td>
						    <td >工作单位</td>
						    <td>创建时间</td>
						    <td >备注</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.contactName}</td>
								<td><f:type className="SexType" value="${element.sex}" /> </td>
								<td>${element.industry}</td>
								<td>${element.position}</td>
								<td>${element.mobile}</td>
								<td>${element.merchantName}</td>
								<td><bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${element.remark}</td>
								<td class="redlink">
						    		<a href="javascript:update('${element.id}')">修改</a>
						    		<a href="javascript:delRecord('${element.id}')">删除</a>
							    </td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
