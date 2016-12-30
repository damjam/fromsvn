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
				
				$('#btnAdd').click(function(){
					var url="${uri}?action=toAdd";
					gotoUrl(url);   
				});
			});
			function query(){
				$('#queryForm').submit();
			}
			function delInfo(id){
				layer.confirm("确认删除?", {
					  btn: ['确定','取消'] //按钮
				}, function(){
					gotoUrl('${uri}?action=delete&id='+id);
				});
			}
			function updateInfo(id){
				var url="${uri}?action=toEdit&id="+id;
				gotoUrl(url);  
			}
			function cancel(id){
				layer.confirm('确认取消订单?', {
					  btn: ['确定','取消'] //按钮
				}, function(){
					var url="${uri}?action=cancel&id="+id;
					gotoUrl(url);  
				});
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
				layer.confirm('确认收款?', {
					  btn: ['确定','取消'] //按钮
				}, function(){
					var url="${uri}?action=pay&id="+id;
					$('#queryForm').attr('action', url);
					$('#queryForm').submit();
				});
			}
			
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								客户名称
							</td>
							<td>
								<s:textfield name="clientName" id="clientName" maxlength="10"/>
							</td>
							<td class="formlabel">
								联系电话
							</td>
							<td>
								<s:textfield name="clientTel" id="clientTel" maxlength="10"/>
							</td>
							<td class="formlabel">
								订购日期
							</td>
							<td>
								<s:textfield name="beginOrderDate" id="beginOrderDate" maxlength="8" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endOrderDate" id="endOrderDate" maxlength="8" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								订单状态
							</td>
							<td>
								<s:select list="#request.orderStates" name="state" listKey="value" listValue="name" headerKey="" headerValue="---请选择---"/>
							</td>
							<td class="formlabel">
								付款状态
							</td>
							<td>
								<s:select list="#request.payStates" name="payState" listKey="value" listValue="name" headerKey="" headerValue="---请选择---"/>
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
			<div class="tablebox" id="listDiv" style="display: block; margin: -10px 0 -30px 0;">
			<!-- 汇总信息 -->
				<table class="data_grid" style="margin:0 0 10px 0;width: 100%;border: 0;">
					<caption>汇总信息</caption>
					<thead>
						<tr class="titlebg">
							<td align="center">笔数</td>
							<td align="center">合计金额（元）</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center"><fmt:formatNumber value="${sumInfo.totalAmt }" pattern="##0.00"/></td>
					</tr>
				</table>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >订单号</td>
						 	<td >客户名称</td>
						 	<td >联系人</td>
						    <td >联系电话</td>
						    <td >送货地址</td>
						    <td >订货日期</td>
						    <td >订单金额</td>
						    <td >实付金额</td>
						    <td >订单状态</td>
						    <td >付款状态</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.id}</td>
								<td>${element.clientName}</td>
								<td>${element.contact}</td>
								<td>${element.clientTel}</td>
								<td>${element.address}</td>
								<td>${element.orderDate }</td>
								<td><fmt:formatNumber value="${element.amount}" pattern="##0.00"/></td>
							    <td><fmt:formatNumber value="${element.payAmt}" pattern="##0.00"/></td>
							    <td ondblclick="edit()"><f:state className="OrderState" value="${element.state}"/> </td>
							    <td><f:state className="PayState" value="${element.payState}"/> </td>
							    <td class="redlink">
							    	<c:if test="${element.state eq '00' }">
							    	<!-- 
							    		<a href="javascript:changeState('${element.id}','01')" >发货</a>
							    		 -->
							    		<a href="javascript:cancel('${element.id}')" >取消</a>
							    		<a href="javascript:updateInfo('${element.id}')">修改</a>
							    	</c:if>
							    	<!-- 
							    	<c:if test="${element.state eq '01' }">
							    		<a href="javascript:changeState('${element.id}','01')" >确认收货</a>
							    		<a href="javascript:changeState('${element.id}','04')" >退货</a>
							    	</c:if> -->
							    	<c:if test="${element.payState eq '00' && element.state eq '02'}">
							    		<a href="javascript:pay('${element.id}')" >确认收款</a>
							    	</c:if>
							    	<c:if test="${element.state ne '09'}">
							    		<a href="javascript:detail('${element.id}')" >查看明细</a>
							    		<a href="javascript:openReport('${element.id}')">打印</a>
							    	</c:if>
							    	
							    	<c:if test="${element.state eq '09' }">
							    		<a href="javascript:delInfo('${element.id}')" >删除</a>
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
