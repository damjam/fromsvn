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
				
			});
			function deposit(id){
				gotoUrl('/account.do?action=toDeposit&id='+id);
			}
			function withdraw(id){
				gotoUrl('/account.do?action=toWithdraw&id='+id);
			}
			function cancelAcct(id){
				if(window.confirm("确认销户?")){
					gotoUrl('/account.do?action=cancel&id='+id);
				}
				
			}
			function detail(id){
				window.open(CONTEXT_PATH+'/account.do?action=detail&acctNo='+id);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<s:form action="account.do?action=list" id="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
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
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
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
		</s:form>
	</body>
</html>
