<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title>水费账单</title>
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
				$('#btnPrint').click(function(){
					var startCreateDate = $('#startCreateDate').val();
					var endCreateDate = $('#endCreateDate').val();
					var buildingNo = $('#buildingNo').val();
					if(startCreateDate.trim() == '' || endCreateDate.trim() == ''){
						alert('请选择创建日期');
						return;
					}
					if(buildingNo.trim() == ''){
						alert('请选择楼号');
						return;
					}
					window.open(CONTEXT_PATH+'/reportAction.do?action=waterBillDetail&buildingNo='+buildingNo+"&startCreateDate="+startCreateDate+"&endCreateDate="+endCreateDate);
				});
				
			});
			function charge(id, amount, balance){
				balance = parseFloat(balance); 
				amount = parseFloat(amount);
				
				var flag= false;
				var tip = "确认收费?";
				if(balance > 0){
					if(balance >= amount){
						tip = "业主预存账户现有余额"+balance+"元,水费将从中扣除，确认操作?";
					}else{
						var lackAmt = amount-balance;
						tip = "将从业主预存账户中扣除"+balance+"元,另需补交"+lackAmt+"元，确认操作?";
					}
				}
				
				if(window.confirm(tip)){
					//var url = 
					//gotoUrl('/waterBill.do?action=charge&id='+id);
					$('#queryForm').attr('action', CONTEXT_PATH+'/waterBill.do?action=charge&id='+id);
					$('#queryForm').submit();
					$('#queryForm').attr('action', CONTEXT_PATH+'/waterBill.do?action=list');
				}
			}
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=waterBill&id='+id);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<s:form action="waterBill.do?action=list" id="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid" style="margin: 0;padding: 0;">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								创建日期
							</td>
							<td>
								<s:textfield name="startCreateDate" id="startCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endCreateDate" id="endCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								房屋编号
							</td>
							<td>
								<s:textfield name="houseSn" id="houseSn" maxlength="10"/>
							</td>
							<td class="formlabel">状态</td>
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
								楼号
							</td>
							<td>
								<s:select name="buildingNo" id="buildingNo" list="#request.buildingNos" listKey="key" listValue="value" headerKey="" headerValue="---全部---" style="width:166px;"></s:select>
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
								<input type="button" value="打印欠费账单" id="btnPrint"/>
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
							<td align="center">应缴笔数</td>
							<td align="center">应缴金额（元）</td>
							<td align="center">已缴笔数</td>
							<td align="center">已缴金额（元）</td>
							<td align="center">部分已缴笔数</td>
							<td align="center">部分已缴金额（元）</td>
							<td align="center">未缴笔数</td>
							<td align="center">未缴金额（元）</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center"><fmt:formatNumber value="${sumInfo.totalAmt }" pattern="##0.00"/></td>
						<td align="center">${sumInfo.paidCnt}</td>
						<td align="center"><fmt:formatNumber value="${sumInfo.paidAmt }" pattern="##0.00"/></td>
						<td align="center">${sumInfo.partPaidCnt}</td>
						<td align="center"><fmt:formatNumber value="${sumInfo.partPaidAmt }" pattern="##0.00"/></td>
						<td align="center">${sumInfo.unpayCnt}</td>
						<td align="center"><fmt:formatNumber value="${sumInfo.unpayAmt }" pattern="##0.00"/></td>
					</tr>
				</table>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >账单号</td>
						 	<td >房屋编号</td>
						 	<td >业主姓名</td>
						 	<td >用水月份</td>
						    <td >上期读数</td>
						    <td >本期读数</td>
						    <td >实际用量</td>
						    <td >单价</td>
						    <td >应缴金额</td>
						    <td >已缴金额</td>
						    <td >缴费时间</td>
						    <td >账单状态</td>
						    <td >备注</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.id}</td>
								<td>${element.houseSn}</td>
								<td>${element.ownerName}</td>
								<td>${element.recordMonth}</td>
								<td>${element.prenum}</td>
								<td>${element.curnum}</td>
								<td>${element.num}</td>
								<td><fmt:formatNumber value="${element.price}" pattern="##0.00"/></td>
								<td><fmt:formatNumber value="${element.amount}" pattern="##0.00"/></td>
								<td><fmt:formatNumber value="${element.paidAmt}" pattern="##0.00"/></td>
								<td width="120"><fmt:formatDate value="${element.chargeDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td>
							    	<f:state className="BillState" value="${element.state}" />
							    </td>
							    <td style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" >${element.remark}</td>
							    <td class="redlink">
							    	<c:if test="${element.state eq '00'}">
							    		<a href="javascript:charge('${element.id}', '${element.amount}', '${element.balance}')">收费</a>
							    	</c:if>
							    	<c:if test="${element.state eq '01'}">
							    		<a href="javascript:openReport(${element.id})">打印</a>
							    	</c:if>
							    </td>
						    </tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</s:form>
	</body>
</html>
