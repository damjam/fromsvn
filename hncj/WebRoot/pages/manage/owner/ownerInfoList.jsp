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
				$('#btnImport').click(function(){
					var url="${uri}?action=toImport";
					gotoUrl(url);   
				});
				$('#btnExport').click(function(){
					var url="${uri}?action=export";
					gotoUrl(url);
				});
			});
			function updateInfo(id){
				gotoUrl('${uri}?action=toUpdate&id='+id);
			}
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			function canceInfo(id){
				if(window.confirm("确认销户?")){
					gotoUrl('${uri}?action=cancel&id='+id);
				}
			}
			function openAcct(id){
				if(window.confirm("确认开户?")){
					$('#queryForm').attr('action', CONTEXT_PATH+'${uri}?action=openAcct&id='+id);
					$('#queryForm').submit();
					$('#queryForm').attr('action', CONTEXT_PATH+'${uri}?action=list');
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
								<input type="button" value="新增" id="btnAdd"/>&nbsp;
								<input type="button" value="导出" id="btnExport"/>&nbsp;
								<input type="button" value="导入" id="btnImport"/>&nbsp;
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
						 	<c:if test="${sessionScope.isHQ == true}">
						 		<td>机构</td>
						 	</c:if>
						 	<td >业主姓名</td>
						 	<td >性别</td>
						    <td >证件号码</td>
						    <td >手机号码</td>
						    <td >邮箱</td>
						    <td >房屋编号</td>
						    <td >入住日期</td>
						    <td >重要级别</td>
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
								<td>${element.ownerName}</td>
								<td>${element.gender}</td>
								<td>${element.idCard}</td>
								<td>${element.mobile}</td>
								<td>${element.email}</td>
								<td>${element.houseSn}</td>
								<td>${element.checkinDate}</td>
								<td><f:type className="OwnerGrade" value="${element.grade}"/></td>
								<!-- 
								<td>${element.oweTimes}</td>
								<td>${element.oweDays}</td>
								 -->
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td><f:state className="OwnerState" value="${element.state}"/></td>
							    <td class="redlink">
						    		<c:if test="${element.state eq '00'}">	
						    			<a href="javascript:updateInfo('${element.id}')">修改</a>
							    		<a href="javascript:canceInfo('${element.id}')" >销户</a>
							    		<c:if test="${element.hasAcct ne 'Y'}">
							    			<a href="javascript:openAcct('${element.id}')" >开通账户</a>
							    		</c:if>
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
