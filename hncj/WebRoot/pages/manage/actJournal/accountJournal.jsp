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
			function addDeposit(){
				gotoUrl('/accountJournal.do?action=toDeposit');
			}
			function addWithdraw(){
				gotoUrl('/accountJournal.do?action=toWithdraw');
			}
			function addReverse(){
				gotoUrl('/accountJournal.do?action=toReverse');
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<form action="accountJournal.do?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								日期
							</td>
							<td>
								<s:textfield name="startCreateDate" id="startCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endCreateDate" id="endCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								收支类型
							</td>
							<td>
								<s:select name="inoutType" list="#request.inoutTypes" listKey="value" listValue="name" headerKey="" headerValue="---全部---"></s:select>
							</td>
							<td class="formlabel">
								交易类型
							</td>
							<td>
								<s:select name="tradeType" list="#request.tradeTypes" listKey="value" listValue="name" headerKey="" headerValue="---全部---"></s:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="收入" onclick="addDeposit()"/>&nbsp;
								<input type="button" value="支出" onclick="addWithdraw()"/>&nbsp;
								<input type="button" value="冲正" onclick="addReverse()"/>&nbsp;
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
							<td align="center">收入笔数</td>
							<td align="center">收入金额（元）</td>
							<td align="center">支出笔数</td>
							<td align="center">支出金额（元）</td>
							<td align="center">总笔数</td>
							<td align="center">净收入（元）</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.inCnt}</td>
						<td align="center">
							<fmt:formatNumber value="${sumInfo.inAmt }" pattern="##0.00"></fmt:formatNumber>
						</td>
						<td align="center">${sumInfo.outCnt}</td>
						<td align="center">
							<fmt:formatNumber value="${sumInfo.outAmt }" pattern="##0.00"></fmt:formatNumber>
						</td>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center">
							<fmt:formatNumber value="${sumInfo.netAmt }" pattern="##0.00"></fmt:formatNumber>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >流水号</td>
						 	<td >收支类型</td>
						 	<td >交易类型</td>
						 	<td >交易金额</td>
						    <td >交易时间</td>
						    <td >关联账单号</td>
						    <td >当前余额</td>
						    <td >备注</td>
						    <td >经手人</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.id}</td>
								<td><f:type className="InoutType" value="${element.inoutType}"/></td>
								<td><f:type className="TradeType" value="${element.tradeType}"/></td>
								<td align="right">
									<fmt:formatNumber value="${element.amount }" pattern="##0.00"></fmt:formatNumber>
								</td>
								<td>
									<fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>${element.billId}</td>
								<td align="right">
									<fmt:formatNumber value="${element.balance }" pattern="##0.00"></fmt:formatNumber>
						    	</td>
						    	<td>${element.remark}</td>
						    	<td>${element.createUser}</td>
						    </tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</form>
	</body>
</html>
