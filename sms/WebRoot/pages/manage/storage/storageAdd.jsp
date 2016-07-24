<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
		<f:css href="/css/page.css"/>
		<f:js src="/js/jquery.js"/>
		<script src="http://cdn.bootcss.com/jqueryui/1.12.0-rc.2/jquery-ui.js"></script>	
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/layer/layer.js"/>
		<!-- 
		<f:js src="/js/plugin/jquery.validate.js"/>		
		 -->
		<script type="text/javascript">
			
		 	function save(){
		 		if($('#name').val() == ''){
		 			layer.msg('型号不能为空',{icon:7,shade:0.2,time:1000});
		 			return;
		 		}
		 		if($('#brand').val() == ''){
		 			layer.msg('品牌不能为空',{icon:7,shade:0.2,time:1000});
		 			return;
		 		}
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	
		 	$(function() {
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
		 	
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" id="dataForm" method="post">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
					  <caption>${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes">商品类型</td>
						    <td>
						    	<s:select list="#{'脚垫':'脚垫','平板后箱垫':'平板后箱垫','全包围后箱垫':'全包围后箱垫','座垫':'座垫'}" listKey="key" listValue="value" name="productType" id="productType" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">汽车型号</td>
						    <td>
						    	<s:textfield name="carModel" id="carModel" maxlength="10" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">产品名称</td>
						    <td>
						    	<s:textfield name="product" id="product" maxlength="20" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <!-- 
					   <tr>
						    <td class="formlabel nes">颜色</td>
						    <td>
						    	<s:textfield name="color" id="color" maxlength="10" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr> -->
					    <tr>
						    <td class="formlabel nes">存放位置</td>
						    <td>
						    	<s:textfield name="shelf" id="shelf" maxlength="10" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">数量</td>
						    <td>
						    	<s:textfield name="num" id="num" maxlength="10" class="{required:true,digit:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">备注</td>
						    <td>
						    	<s:textfield name="remark" id="remark" maxlength="25"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
				</div>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</form>	
</body>
</html>
