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
				$('#btnCheck').click(function(){
					
					if(window.confirm("生成账单后记录将不可删除，确认操作?")){
						$(':button').attr('disabled', true);
						gotoUrl('/waterRecord.do?action=checkAll');
						
					}
				});
				$('#btnImp').click(function(){
					gotoUrl('/waterRecord.do?action=toImport');
				});
			});
			function check(id){
				if(window.confirm("生成账单后记录将不可删除，确认操作?")){
					gotoUrl('/waterRecord.do?action=check&id='+id);
				}
			}
			function delRecord(id){
				if(window.confirm("确认删除?")){
					gotoUrl('/waterRecord.do?action=delete&id='+id);
				}
			}
			
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/waterRecord.do?action=list" styleId="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								创建日期
							</td>
							<td>
								<html:text property="startCreateDate" styleId="startCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<html:text property="endCreateDate" styleId="endCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								房屋编号
							</td>
							<td>
								<html:text property="houseSn" styleId="houseSn" maxlength="10"/>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="全部生成账单" id="btnCheck"/>&nbsp;
								<input type="button" value="导入水表记录" id="btnImp"/>&nbsp;
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
						 	<td >用水月份</td>
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
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.recordMonth}</td>
								<td>${element.prenum}</td>
								<td>${element.preRecordDate}</td>
								<td>${element.curnum}</td>
								<td>${element.curRecordDate}</td>
								<td>${element.num}</td>
								<td><bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td class="redlink">
							    	<logic:equal value="00" name="element" property="state">
							    		<c:if test="${element.num > 0}">
							    			<a href="javascript:check(${element.id})" title="生成账单后将不可删除">生成账单</a>
							    		</c:if>
							    		<a href="javascript:delRecord(${element.id})">删除</a>
							    	</logic:equal>
							    </td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
