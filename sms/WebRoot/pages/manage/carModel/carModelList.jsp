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
		<f:css href="/js/plugin/jquery-ui.min.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery-ui.js" />
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
				$("#brand").autocomplete({
					delay : 500,
					minLength: 0,
					 source: function(request, response) {
						var keyword = $('#brand').val();
						//var word = $('#search-content').val();
						//word = encodeURI(word, "utf-8");
						//alert(keyword);
	                    $.ajax({
	                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                        type:"post",  
	                        url: CONTEXT_PATH+"/carBrand.do?action=loadByKeyword&keyword="+keyword,
	                        dataType: "json",
	                        data: {
	                            top: 10,
	                            key: request.term
	                        } ,
	                        success: function(data) {
	                             response($.each(data.list, function(item) {
				                    return item;
				                }));
	                        } 
	                    });
	                },
	                select: function (event, ui) {
						//$('#brand').val(ui.item);
				    }
				});
			});
			
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('${uri}?action=delete&id='+id);
				}
			}
			function updateInfo(id){
				var url="${uri}?action=toEdit&id="+id;
				gotoUrl(url);  
			}
			$(function() {
			 	
		 	});
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
								型号
							</td>
							<td>
								<s:textfield name="name" id="name" maxlength="10"/>
							</td>
							<td class="formlabel">
								品牌
							</td>
							<td>
								<s:textfield name="brand" id="brand" maxlength="10"/>
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
				<table class="data_grid">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >型号</td>
						    <td >品牌</td>
						    <!-- 
						    <td >车型</td> -->
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.name}</td>
								<td>${element.brand}</td>
							    <td class="redlink">
							    	<!-- 
							    	<a href="javascript:updateInfo('${element.id}')" >修改</a>
							    	 -->
							    	<a href="javascript:delInfo('${element.id}')" >删除</a>
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
