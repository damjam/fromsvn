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
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#queryForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("queryForm");
				});
				
				$('#btnSum').click(function(){
					var url="/dailyOrder.do?action=exeDailySum";
					gotoUrl(url);   
				});
			});
			function query(){
				$('#queryForm').submit();
			}
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			function updateInfo(id){
				var url="${uri}?action=toEdit&id="+id;
				gotoUrl(url);  
			}
			function cancel(id){
				if(confirm('确认取消订单?')){
					var url="${uri}?action=cancel&id="+id;
					gotoUrl(url);  
				}
			}
			function detail(id){
				var url = CONTEXT_PATH+'/orderDetail.do?action=detailList&orderId='+id;
				layer.open({
					title:'订单明细',
				    type: 2,
				    area: ['800px', '360px'],
				    fix: false, //不固定
				    maxmin: true,
				    content: url
				}); 
			}
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=orderRecord&id='+id);
			}
			function pay(id){
				var url="${uri}?action=pay&id="+id;
				if(confirm('确认操作?')){
					gotoUrl(url);  
				}
			}
			
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=dailySum" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								订购日期
							</td>
							<td>
								<s:textfield name="beginDate" id="beginDate" maxlength="8" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endDate" id="endDate" maxlength="8" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="重新统计" id="btnSum"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >订购日期</td>
						 	<td >订单数量</td>
						    <td >订单总额</td>
						    <td >已付款</td>
						    <td >未付款</td>
						    <td >脚垫</td>
						    <td >座垫</td>
						    <td >全包围后备箱垫</td>
						    <td >平板后备箱垫</td>
						    <td >脚垫+丝圈</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.orderDate}</td>
								<td>${element.totalCnt}</td>
								<td><fmt:formatNumber value="${element.totalAmt}" pattern="##0.00"/></td>
								<td>${element.paidCnt}/<fmt:formatNumber value="${element.paidAmt}" pattern="##0.00"/></td>
							    <td>${element.unpaidCnt}/<fmt:formatNumber value="${element.unpaidAmt}" pattern="##0.00"/></td>
								<td>${element.footpadCnt}/<fmt:formatNumber value="${element.footpadAmt}" pattern="##0.00"/></td>
								<td>${element.seatpadCnt}/<fmt:formatNumber value="${element.seatpadAmt}" pattern="##0.00"/></td>
								<td>${element.wrapTrunkCnt}/<fmt:formatNumber value="${element.wrapTrunkAmt}" pattern="##0.00"/></td>
								<td>${element.flatTrunkCnt}/<fmt:formatNumber value="${element.flatTrunkAmt}" pattern="##0.00"/></td>
								<td>${element.silkFootpadCnt}/<fmt:formatNumber value="${element.silkFootpadAmt}" pattern="##0.00"/></td>
						    </tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</form>
	</body>
</html>
