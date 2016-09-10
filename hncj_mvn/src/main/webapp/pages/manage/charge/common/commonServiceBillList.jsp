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
		<f:js src="/js/datePicker/WdatePicker.js"/>
		<script type="text/javascript">
			$(function(){
				$('#btnQry').click(function(){
					$('#queryForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("queryForm");
				});
				$('#btnAdd').click(function(){
					gotoUrl('${uri}?action=toAdd');
				});
				$('#btnImport').click(function(){
					gotoUrl('${uri}?action=toImport');
				});
				$('#btnExport').click(function(){
					gotoUrl('${uri}?action=export');
				});
			});
			function charge(id){
				if(window.confirm("确认收费?")){
					gotoUrl('${uri}?action=charge&id='+id);
				}
			}
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=commonServiceBill&id='+id);
			}
			function delRecord(id){
				if(!window.confirm("确认删除?")){
					return;
				}
				gotoUrl('${uri}?action=deleteBill&id='+id);
			}
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<div class="widget">
					<div class="widget-head">
	                    <div class="pull-left">${ACT.name}</div>
	                </div>
					<table class="form_grid">
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
								<s:textfield name="startChargeDate" id="startChargeDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<s:textfield name="endChargeDate" id="endChargeDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
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
								年份
							</td>
							<td>
								<s:textfield name="year" id="year" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyy'})"/>
							</td>
							<c:if test="${sessionScope.isHQ == true}">
								<td class="formlabel">机构</td>
								<td>
									<s:select name="branchNo" list="branches" listKey="key" listValue="value" headerKey="" headerValue="---全部---"></s:select>
								</td>
							</c:if>	
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="新增" id="btnAdd"/>&nbsp;
								<input type="button" value="导出" id="btnExport"/>&nbsp;
								<input type="button" value="导入" id="btnImport"/>
								
							</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="tablebox" id="listDiv">
			<!-- 汇总信息 -->
				<table class="data_grid">
					<caption>汇总信息</caption>
					<thead>
						<tr class="titlebg">
							<td align="center">应缴笔数</td>
							<td align="center">应缴金额（元）</td>
							<td align="center">已缴笔数</td>
							<td align="center">已缴金额（元）</td>
							<td align="center">未缴笔数</td>
							<td align="center">未缴金额（元）</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center">
							<fmt:formatNumber value="${sumInfo.totalAmt }" pattern="##0.00"></fmt:formatNumber>
						</td>
						<td align="center">${sumInfo.paidCnt}</td>
						<td align="center"><fmt:formatNumber value="${sumInfo.paidAmt }" pattern="##0.00"></fmt:formatNumber></td>
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
							<c:if test="${sessionScope.isHQ == true}">
								<td>机构</td>
							</c:if>	
						 	<td>账单号</td>
						 	<td>编号</td>
						 	<td>面积</td>
						 	<td>姓名</td>
						    <td>起止日期</td>
						    <td>物业费单价</td>
						    <td>公共能源费单价</td>
						    <td>合计金额</td>
						    <td>实收金额</td>
						    <td>缴费时间</td>
						    <td>收款人</td>
						    <td>状态</td>
						    <td>备注</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<c:if test="${sessionScope.isHQ == true}">
							 		<td>${element.branchName}</td>
							 	</c:if>
								<td>${element.id}</td>
								<td>${element.houseSn}</td>
								<td><fmt:formatNumber value="${element.area }" pattern="##0.00"/></td>
								<td>${element.ownerName}</td>
								<td>${element.startDate}-${element.endDate}</td>
								<td><fmt:formatNumber value="${element.servicePrice}" pattern="##0.00"/></td>
								<td><fmt:formatNumber value="${element.lightPrice}" pattern="##0.00"/></td>
								<td><fmt:formatNumber value="${element.totalAmount}" pattern="##0.00"/></td>
								<td><fmt:formatNumber value="${element.paidAmount}" pattern="##0.00"/></td>
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
