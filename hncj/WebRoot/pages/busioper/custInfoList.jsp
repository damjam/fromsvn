<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>客户列表</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				
				$('#btnQry').click(function(){
					$('#custInfoActionForm').submit();
				});
				
				$('#btnClear').click(function(){
					FormUtils.reset("custInfoActionForm");
				});
				
				$('#btnAdd').click(function(){
					var url="/custMngAction.do?action=toAdd";
					gotoUrl(url);
				});
			});
			
			function detailedCustInfo(id){
				var url="/custMngAction.do?action=detail&id="+id;
				gotoUrl(url);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<s:form action="/custMngAction.do?action=list" id="custInfoActionForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								手机号
							</td>
							<td>
								<s:textfield name="mobile" id="mobile"  />
							</td>
							<td class="formlabel">
								证件号
							</td>
							<td>
								<s:textfield name="idCard" id="idCard"  />
							</td>
							<td class="formlabel">邮箱</td>
							<td>
								<s:textfield name="email" id="email"  />
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								客户类型
							</td>
							<td>
								<html:select property="custType">
									<html:option value="">---全部---</html:option>
									<html:options collection="custTypes" labelProperty="name" property="value"/>
								</html:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear"/>&nbsp;
								<input type="button" value="新增" id="btnAdd"/>
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
						    <td>手机号</td>
						    <td>真实姓名</td>
						    <td>性别</td>
						    <td>证件类型</td>
						    <td>证件号码</td>
						    <td>客户类型</td>
						    <td>通讯地址</td>
						    <td>邮箱</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.mobile }</td>
							    <td>${element.name }</td>
								<td>
									<f:type className="SexType" value="${element.sex}"/>
								</td>
								<td>
									<f:type className="IdCardType" value="${element.cardType}"/>
								</td>
							    <td>${element.idCard }</td>
							    <td><f:type className="CustType" value="${element.custType}"/></td>
							    <td>${element.addr}</td>
							    <td>${element.email}</td>
							    <td align="center">
							       <span class="redlink">
							       		<a href="javascript:void(0)" onclick="return detailedCustInfo('${element.id}');">查看明细</a>
							 	   </span>
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
