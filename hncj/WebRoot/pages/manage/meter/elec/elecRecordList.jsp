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
				$('#btnCheck').click(function(){
					
					if(window.confirm("生成账单后记录将不可删除，确认操作?")){
						$(':button').attr('disabled', true);
						gotoUrl('${uri}?action=checkAll');
						
					}
				});
				$('#btnImp').click(function(){
					gotoUrl('${uri}?action=toImport');
				});
				$('#btnAdd').click(function(){
					gotoUrl('${uri}?action=toAdd');
				});
			});
			function check(id){
				if(window.confirm("生成账单后记录将不可删除，确认操作?")){
					gotoUrl('${uri}?action=check&id='+id);
				}
			}
			function delRecord(id){
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
		<form action="${uri}?action=list" id="queryForm" method="post">
			<!-- 查询功能区 -->
			<div class="userbox">
				<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
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
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="全部生成账单" id="btnCheck"/>&nbsp;
								<input type="button" value="导入电表记录" id="btnImp"/>&nbsp;
								<input type="button" value="新增电表记录" id="btnAdd"/>&nbsp;
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
						 	<td >房屋编号</td>
						 	<td >用电月份</td>
						    <td >上期读数</td>
						    <td >上期抄表日期</td>
						    <td >本期读数</td>
						    <td >本期抄表日期</td>
						    <td >实际用量</td>
						    <td >创建时间</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.recordMonth}</td>
								<td>${element.prenum}</td>
								<td>${element.preRecordDate}</td>
								<td>${element.curnum}</td>
								<td>${element.curRecordDate}</td>
								<td>${element.num}</td>
								<td><fmt:formatDate value="${element.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							    <td class="redlink">
							    	<c:if test="${element.state eq '00'}">
							    		<c:if test="${element.num > 0}">
							    			<a href="javascript:check(${element.id})" title="生成账单后将不可删除">生成账单</a>
							    		</c:if>
							    		<a href="javascript:delRecord(${element.id})">删除</a>
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
