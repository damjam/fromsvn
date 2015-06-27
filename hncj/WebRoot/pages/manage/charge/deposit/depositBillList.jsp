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
					gotoUrl('/depositBill.do?action=toAdd');
				});
				
			});
			function charge(id){
				if(window.confirm("确认收费?")){
					gotoUrl('/depositBill.do?action=charge&id='+id);
				}
			}
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=depositBill&id='+id);
			}
			function delRecord(id){
				if(!window.confirm("确认删除?")){
					return;
				}
				gotoUrl('/depositBill.do?action=deleteBill&id='+id);
			}
			function refund(id){
				if(!window.confirm("确认退款?")){
					return;
				}
				gotoUrl('/depositBill.do?action=refund&id='+id);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<form action="depositBill.do?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								房屋编号
							</td>
							<td>
								<s:textfield name="houseSn" id="houseSn" maxlength="10"/>
							</td>
							<td class="formlabel">
								收款日期
							</td>
							<td>
								<s:textfield name="startDepositDate" id="startDepositDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endDepositDate" id="endDepositDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								退款日期
							</td>
							<td>
								<s:textfield name="startRefundDate" id="startRefundDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endRefundDate" id="endRefundDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">状态</td>
						    <td>
						    	<s:select name="state" id="state" list="#request.billStates" listKey="value" listValue="name" headerKey="" headerValue="---全部---"></s:select>
						    </td>
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
								<s:textfield name="year" id="year" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyy'})"/>
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
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:0 0 10px 0">
					<caption>汇总信息</caption>
					<thead>
						<tr class="titlebg">
							<td align="center">已缴已退款笔数</td>
							<td align="center">已缴已退款金额（元）</td>
							<td align="center">已缴未退款笔数</td>
							<td align="center">已缴未退款金额（元）</td>
							<td align="center">总笔数</td>
							<td align="center">总金额（元）</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.refundCnt}</td>
						<td align="center">
							<fmt:formatNumber value="${sumInfo.refundAmt }" pattern="##0.00"/>
						</td>
						<td align="center">${sumInfo.paidCnt}</td>
						<td align="center"><fmt:formatNumber value="${sumInfo.paidAmt }" pattern="##0.00"/></td>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center"><fmt:formatNumber value="${sumInfo.totalAmt }" pattern="##0.00"/></td>
					</tr>
				</table>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >账单号</td>
						 	<td >房屋编号</td>
						 	<td >付款人</td>
						    <td >付款时间</td>
						    <td >金额</td>
						    <td >用途</td>
						    <td >收款人</td>
						    <td >退款时间</td>
						    <!-- 
						    <td >退款金额</td> -->
						    <td >退款人</td>
						    <td >状态</td>
						    <td >备注</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.id}</td>
								<td>${element.houseSn}</td>
								<td>${element.payerName}</td>
								<td width="120">
									<fmt:formatDate value="${element.depositDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td><fmt:formatNumber value="${element.amount }" pattern="##0.00"/></td>
								<td>${element.purpose}</td>
								<td>${element.depositUser}</td>
								<td width="120">
									<fmt:formatDate value="${element.refundDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<!-- 
								<td><bean:write name="element" property="refundAmount" format="##0.00"/></td>
								 --><td>${element.refundUser}</td>
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
							    		<a href="javascript:refund('${element.id}')">退款</a>
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
