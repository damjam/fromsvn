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
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery-ui.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/layer/layer.js"/>
		<script type="text/javascript">
			layer.config({
			    extend: 'extend/layer.ext.js'
			});
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
				$(".model").autocomplete({
					delay : 500,
					minLength: 0,
					 source: function(request, response) {
						var keyword = $('.model').val();
						//var word = $('#search-content').val();
						//word = encodeURI(word, "utf-8");
						//alert(keyword);
	                    $.ajax({
	                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
	                        type:"post",  
	                        url: CONTEXT_PATH+"/carModel.do?action=loadByKeyword&keyword="+keyword,
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
			function instock(id){
				layer.prompt({formType:0,title:'请输入数量'},function(val){
					var url="${uri}?action=instock&id="+id+"&inoutNum="+val;
					gotoUrl(url);
				});
			}
			function outstock(id) {
				/*
				layer.prompt({formType:0,title:'请输入数量'},function(val){
					var url="${uri}?action=outstock&id="+id+"&inoutNum="+val;
					gotoUrl(url);
				});
				*/
				var url = "${uri}?action=toOutstock&id="+id;
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
								货架
							</td>
							<td>
								<s:textfield name="shelf" id="shelf" maxlength="10"/>
							</td>
							<td class="formlabel">
								汽车品牌型号
							</td>
							<td>
								<s:textfield name="carModel" id="carModel" class="model" maxlength="10"/>
							</td>
							<td class="formlabel">商品类型</td>
						    <td>
						    	<s:select list="#{'脚垫':'脚垫','平板后箱垫':'平板后箱垫','全包围后箱垫':'全包围后箱垫','座垫':'座垫'}" headerKey="" headerValue="--全部--" listKey="key" listValue="value" name="productType" id="productType" />
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
						 	<td >名称</td>
						    <td >汽车品牌型号</td>
						    <td >存放位置</td>
						    <td >产品类型</td>
						    <td >库存数量</td>
						    <td >备注</td>
						    <!-- 
						    <td >车型</td> -->
						    <td >操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>${element.product}</td>
								<td>${element.carModel}</td>
								<td>${element.shelf}</td>
								<td>${element.productType}</td>
								<td>${element.num}</td>
								<td>${element.remark}</td>
							    <td class="redlink">
							    	<a href="javascript:instock('${element.id}')" ><i class="fa fa-plus-circle"></i></a>&nbsp;
							    	<c:if test="${element.num > 0}">
							    		<a href="javascript:outstock('${element.id}')" ><i class="fa fa-minus-circle"></i></a>
							    	</c:if>
							    	<c:if test="${element.num == 0}">
										<a href="javascript:delInfo('${element.id}')" >删除</a>&nbsp;    	
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
