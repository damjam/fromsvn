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
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>	
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#queryForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("queryForm");
				});
				
			});
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=depositDetailBill&id='+id);
			}
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=detail" id="queryForm" method="post">
			<s:hidden name="acctNo" id="acctNo"/>
			<!-- 查询功能区 -->
			<div class="userbox">
				<div class="widget">
					<div class="widget-head">
                  <div class="pull-left">${ACT.name}</div>
                </div>
					<table class="form_grid">
						<tr>
							<td class="formlabel">
								日期
							</td>
							<td>
								<s:textfield name="startCreateDate" id="startCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endCreateDate" id="endCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								变动类型
							</td>
							<td>
								<s:select list="#request.changeTypes" id="type" headerKey="" headerValue="---全部---" listKey="value" listValue="name"></s:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
							</td>
						</tr>
					</table>
				</div>	
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >流水号</td>
						 	<td >出入账类型</td>
						 	<td >交易类型</td>
						 	<td >交易金额</td>
						 	<td >交易时间</td>
						    <td >关联账单号</td>
						    <td >当前余额</td>
						    <td >备注</td>
						    <td >操作</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach var="element" items="${list}">
							<tr align="center">
								<td>${element.id}</td>
								<td><f:type className="InoutType" value="${element.inoutType}"/></td>
								<td><f:type className="AccountChangeType" value="${element.type}"/></td>
								<td>
									<fmt:formatNumber pattern="##0.00" value="${element.amount }"></fmt:formatNumber>
								</td>
								<td>
									<fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>${element.billId}</td>
								<td>
									<fmt:formatNumber value="${element.balance }" pattern="##0.00"></fmt:formatNumber>
								</td>
						    	<td>${element.remark}</td>
						    	<td class="redlink">
						    		<c:if test="${element.type eq '01'}">
						    			<a href="javascript:openReport(${element.id})">打印</a>
						    		</c:if>
						    	</td>
						    </tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</form>
	</body>
</html>
