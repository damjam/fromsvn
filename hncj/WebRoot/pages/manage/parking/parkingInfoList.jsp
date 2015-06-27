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
					var url="/parkingInfo.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('/parkingInfo.do?action=delete&id='+id);
				}
			}
			function updateInfo(id){
				var url="/parkingInfo.do?action=toEdit&id="+id;
				gotoUrl(url);  
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<s:form action="parkingInfo.do?action=list" id="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								车位产权人
							</td>
							<td>
								<s:textfield name="ownerName" id="ownerName" maxlength="10"/>
							</td>
							<td class="formlabel">
								产权人电话
							</td>
							<td>
								<s:textfield name="ownerCel" id="ownerCel" maxlength="10"/>
							</td>
							<td class="formlabel">
								车位使用人
							</td>
							<td>
								<s:textfield name="endUser" id="endUser" maxlength="10"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								使用人电话
							</td>
							<td>
								<s:textfield name="endUserCel" id="endUserCel" maxlength="10"/>
							</td>
							<td class="formlabel">
								状态
							</td>
							<td>
								<s:select name="state" id="state" list="#request.parkingStates" listKey="value" listValue="name" headerKey="" headerValue="---全部---"></s:select>
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
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td>车位号</td>
						    <td>车位产权人</td>
						    <td>产权人电话</td>
						    <td>车位使用人</td>
						    <td>使用人电话</td>
						    <td>车位状态</td>
						    <td>创建时间</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.sn}</td>
								<td>${element.ownerName}</td>
								<td>${element.ownerCel}</td>
								<td>${element.endUser}</td>
								<td>${element.endUserCel}</td>
								<td><f:state className="ParkingState" value="${element.state }" /></td> 
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td class="redlink">
							    	<a href="javascript:updateInfo('${element.id}')" >修改</a>
							    	<a href="javascript:delInfo('${element.id}')" >删除</a>
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
