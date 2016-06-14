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
				$('#btnExport').click(function(){
					gotoUrl('${uri}?action=export');
				});
				$('#btnOpen').click(function(){
					gotoUrl('${uri}?action=toOpen');
				});
				$('#btnImport').click(function() {
					gotoUrl('${uri}?action=toImport');
				});
				
			});
			function deposit(id){
				gotoUrl('${uri}?action=toDeposit&id='+id);
			}
			function withdraw(id){
				gotoUrl('${uri}?action=toWithdraw&id='+id);
			}
			function cancelAcct(id){
				if(window.confirm("确认销户?")){
					gotoUrl('${uri}?action=cancel&id='+id);
				}
			}
			function detail(id){
				window.open('${uri}?action=detail&acctNo='+id);
			}
		</script> 
	</head>
	<body>
		<f:msg styleClass="msg" />
		<form action="${uri}?action=list" id="queryForm" method="post">
		<div class="userbox">
			<!-- 查询功能区 -->
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								业主姓名
							</td>
							<td>
								<s:textfield name="ownerName" id="ownerName" maxlength="10"/>
							</td>
							<td class="formlabel">
								房屋编号
							</td>
							<td>
								<s:textfield name="houseSn" id="houseSn" maxlength="10"/>
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
								<input type="button" value="开户并充值" id="btnOpen" />&nbsp;
								<input type="button" value="导出" id="btnExport"/>&nbsp;
								<input type="button" value="导入" id="btnImport"/>&nbsp;
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
						 	<c:if test="${sessionScope.isHQ == true}">
						 		<td>机构</td>
						 	</c:if>
						 	<td >房屋编号</td>
						 	<td >业主姓名</td>
						 	<td >账户余额</td>
						    <!-- 
						    <td >欠费次数</td>
						    <td >累计欠费天数</td> -->
						    <td >创建时间</td>
						    <td >状态</td>
						    <td >操作</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<c:if test="${sessionScope.isHQ == true}">
							 		<td>${element.branchName}</td>
							 	</c:if>
								<td>${element.houseSn}</td>
								<td>${element.ownerName}</td>
								<td><fmt:formatNumber value="${element.balance}" pattern="##0.00"/></td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><f:state className="OwnerState" value="${element.state}"/> </td>
							    <td class="redlink">
							    	<c:if test="${element.state eq '00'}">
							    		<a href="javascript:deposit('${element.id}')" >充值</a>
							    		<a href="javascript:withdraw('${element.id}')" >提现</a>
							    		<!-- 
							    		<a href="javascript:cancelAcct('${element.id}')" >销户</a> -->
							    	</c:if>
							    	<a href="javascript:detail('${element.id}')" >查看交易记录</a>
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
