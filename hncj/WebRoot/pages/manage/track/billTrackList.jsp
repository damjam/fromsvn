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
					var url="${uri}?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			function discard(id){
				if(window.confirm("确认不再显示该记录?")){
					var url="${uri}?action=discard&id="+id;
					gotoUrl(url);
				}
			}
			function sendNotice(id,cel){
				if(cel == ''){
					//alert('没有可发送的手机号码');
					//return;
				}
				if(window.confirm("将发送短信至"+cel+"，确认操作?")){
					var url="${uri}?action=sendNotice&id="+id;
					gotoUrl(url);  
				}
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
								房屋编号
							</td>
							<td>
								<s:textfield name="houseSn" id="houseSn" maxlength="10"/>
							</td>
							<td class="formlabel">
								业主姓名
							</td>
							<td>
								<s:textfield name="ownerName" id="ownerName" maxlength="10"/>
							</td>
							<td class="formlabel">
								联系电话
							</td>
							<td>
								<s:textfield name="ownerCel" id="ownerCel" maxlength="10"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								到期天数
							</td>
							<td>
								<s:select name="leftDays" id="leftDays" list="#request.remainDays" style="width:166px" listKey="value" listValue="name" headerKey="" headerValue="---全部---"></s:select>
							</td>
							<td class="formlabel">
								账单类型
							</td>
							<td>
								<s:select name="state" id="state" list="#request.billTypes" listKey="value" listValue="name" headerKey="" headerValue="---全部---"></s:select>
							</td>	
						</tr>	
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<!-- 
								<input type="button" value="新增" id="btnAdd"/> -->
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
						 	<td>房屋编号</td>
						 	<td>业主姓名</td>
						    <td>账单类型</td>
						    <td>账单号</td>
						    <td>到期时间</td>
						    <td>剩余天数</td>
						    <td>欠费天数</td>
						    <td>联系电话</td>
						    <td>已通知次数</td>
						    <td>操作</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.ownerName}</td>
								<td><f:type className="BillType" value="${element.billType}" /> </td>
								<td>${element.billId}</td>
								<td>${element.expireDate}</td>
								<td>${element.leftDays}</td>
								<td>${element.overDays}</td>
								<td>${element.ownerCel}</td>
								<td>${element.noticeTimes}</td> 
							    <td class="redlink">
							    	<a href="javascript:discard('${element.id}')" >不再提醒</a>
							    	<a href="javascript:sendNotice('${element.id}','${element.ownerCel}')" >发送通知</a>
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
