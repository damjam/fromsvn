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
					gotoUrl('/generalBill.do?action=toAdd');
				});
				
			});
			function charge(id){
				if(window.confirm("确认收费?")){
					gotoUrl('/generalBill.do?action=charge&id='+id);
				}
			}
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=generalBill&id='+id);
			}
			function delRecord(id){
				if(!window.confirm("确认删除?")){
					return;
				}
				gotoUrl('/generalBill.do?action=deleteBill&id='+id);
			}
			function refund(id){
				if(!window.confirm("确认退款?")){
					return;
				}
				gotoUrl('/generalBill.do?action=refund&id='+id);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<form action="generalBill.do?action=list" id="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								收款日期
							</td>
							<td>
								<s:textfield name="startChargeDate" id="startChargeDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endChargeDate" id="endChargeDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								关键字
							</td>
							<td>
								<s:textfield name="keyword" id="keyword" maxlength="20"/>
							</td>
							<td class="formlabel nes">状态</td>
						    <td>
						    	<s:select name="state" id="state" list="#request.billStates" listKey="value" listValue="name" headerKey="" headerValue="---全部---"></s:select>
						    </td>
					    </tr>
						<tr>
							<td class="formlabel">
								账单号
							</td>
							<td>
								<s:textfield name="id" id="id" maxlength="20"/>
							</td>
							<td class="formlabel">
								年份
							</td>
							<td>
								<s:textfield name="year" id="year" onclick="WdatePicker({dateFmt:'yyyy'})"/>
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
			<!-- 汇总信息 -->
			 
			<div class="tablebox" id="listDiv" style="display: block; margin: -10px 0 -30px 0;">
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:0 0 10px 0">
					<caption>汇总信息</caption>
					<thead>
						<tr class="titlebg">
							<td align="center">总笔数</td>
							<td align="center">总金额（元）</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center">
							<fmt:formatNumber value="${sumInfo.totalAmt}" pattern="##0.00"/>
						</td>
					</tr>
				</table>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >账单号</td>
						    <td >收费项目</td>
						    <td >单价</td>
						    <td >数量</td>
						    <td >总额</td>
						    <td >实收金额</td>
						    <td >付款人</td>
						    <td >付款时间</td>
						    <td >收款人</td>
						    <td >状态</td>
						    <td >备注</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.id}</td>
								<td><f:type className="TradeType" value="${element.tradeType}"/> </td>
								<td><fmt:formatNumber value="${element.unitPrice}" pattern="##0.00"/></td>
								<td>${element.num}</td>
								<td><fmt:formatNumber value="${element.totalAmt}" pattern="##0.00"/></td>
								<td><fmt:formatNumber value="${element.paidAmt}" pattern="##0.00"/></td>
								<td>${element.payerName}</td>
								<td><fmt:formatDate value="${element.chargeDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${element.chargeUser}</td>
								<td>
							    	<f:state className="BillState" value="${element.state}" />
							    </td>
								<td>${element.remark}</td>
								<td class="redlink">
							    	<c:if test="${element.state eq '00'}">
							    		<a href="javascript:charge('${element.id}')">收费</a>
							    		<a href="javascript:delRecord('${element.id}')">删除</a>
							    	</c:if>
							    	<c:if test="${element.state eq '01'}">
							    		<a href="javascript:openReport('${element.id}')">打印</a>
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
