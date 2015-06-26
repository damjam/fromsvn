<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
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
					var url="/chargeItem.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('/chargeItem.do?action=delete&id='+id);
				}
			}
			function updateInfo(id){
				var url="/chargeItem.do?action=toEdit&id="+id;
				gotoUrl(url);  
			}
			$().ready(function(){
				var height = document.body.scrollHeight;
				parent.adjustHeight(height, 0);
			});
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="chargeItem.do?action=list" styleId="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								计费项类型
							</td>
							<td>
								<html:select property="item">
									<html:option value="">---全部---</html:option>
						    		<html:options collection="chargeTypes" property="value" labelProperty="name" />
								</html:select>
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
						 	<td >计费名称</td>
						 	<td >计费类型</td>
						    <td >计费方式</td>
						    <td >计费详情</td>
						    <td >创建时间</td>
						    <td >备注</td>
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${ element.itemName}</td>
								<td><f:type className="ChargeType" value="${ element.item}" /> </td>
								<td><f:type className="ChargeWay" value="${ element.way}" /> </td>
								<td>${element.ruleDesc}</td>
								<td><bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
							    <td>${element.remark }</td>
							    <td class="redlink">
							    	<a href="javascript:updateInfo('${element.id}')" >修改</a>
							    	<a href="javascript:delInfo('${element.id}')" >删除</a>
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
