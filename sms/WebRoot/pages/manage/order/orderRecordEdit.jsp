<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<f:css href="/js/plugin/jquery-ui.min.css"/>
		<f:js src="/js/jquery.min.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>
		<f:js src="/js/plugin/jquery-ui.js"/>	
			
		<script type="text/javascript">
			
		 	function save(){
		 		var flag = true;
		 		$('input[type="text"]').each(function(){
		 			var val = this.value;
		 			if($.trim(val) == ''){
		 				flag = false;
		 				return false;
		 			}
		 		});
		 		if(!flag){
		 			alert('请填写完整后再保存!');
		 			return false;
		 		}
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	
		 	function setBrandAutoComplete(model){
		 		$(model).autocomplete({
					delay : 500,
					minLength: 0,
					source: function(request, response) {
						var keyword = $(brand).val();
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
						//$('#rangeType').val(ui.item.rangeType);
				    }
				});
		 	}
		 	$().ready(function(){
		 		//setBrandAutoComplete($('#brand'));
		 		modelAuto();
		 		//setAmount();
		 	});
		 	function modelAuto(){
		 		$('input[name=carModels]').each(function(i,e){
		 			$(e).autocomplete({
						delay : 500,
						minLength: 0,
						source: function(request, response) {
							var keyword = $(e).val();
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
							//$('#rangeType').val(ui.item.rangeType);
					    }
					});
		 		});
		 	}
		 	function addRow(){
		 		var flag = true;
		 		$('.product input').each(function(){
		 			var val = this.value;
		 			if($.trim(val) == ''){
		 				flag = false;
		 				return false;
		 			}
		 		});
		 		if(!flag){
		 			alert('请完善产品明细信息!');
		 			return false;
		 		}
		 		var rowHtml = '<tr class="product">';
		 		rowHtml += '<td align="center"><input type="text" name="carModels" style="width: 120px;" autocomplete="off"/>&nbsp;</td>';
		 		rowHtml += '<td align="center"><select name="productNames" style="width: 60px;"><option value="座垫">座垫</option><option value="脚垫">脚垫</option><option value="后备箱垫">后备箱垫</option></td>';
		 		rowHtml += '<td align="center"><input type="text" name="materials" style="width: 120px;"/>&nbsp;</td>';
		 		rowHtml += '<td align="center"><input type="text" name="colors" style="width: 60px;"/></td>';
		 		rowHtml += '<td align="center"><input type="text" name="prices" style="width: 50px;" onblur="setAmount(this)"/>&nbsp;</td>';
		 		rowHtml += '<td align="center"><input type="text" name="nums" style="width:30px;" maxlength="4" value="1" onblur="setAmount(this)"/></td>';
		 		rowHtml += '<td align="center"><input type="text" name="amounts" style="width:50px;" maxlength="8" readonly="readonly" class="amount"/></td>';
		 		rowHtml += '<td align="center"><input type="button" value="删除" onclick="deleteThisRow(this)"/></td>';
		 		rowHtml += '</tr>';
		 		$('#dtTable').append(rowHtml);
		 		SysStyle.setFormGridStyle();
		 		SysStyle.setButtonStyle();
		 		modelAuto();
		 	}
		 	function deleteThisRow(row){
		 		$(row).parent().parent().remove();
		 		setTotalAmt();
		 	}
		 	function setAmount(e){
 				var price = $(e).parent().parent().find('input[name^="prices"]');
		 		var priceVal = price.val();
		 		if(isNaN(priceVal) || priceVal == ''){
		 			return;
		 		}
		 		var num = $(e).parent().parent().find('input[name^="nums"]');
		 		var numVal = num.val();
		 		if(isNaN(numVal) || numVal == ''){
		 			return;
		 		}
		 		var amountVal = parseFloat(priceVal)*parseInt(numVal);
		 		var amount = $(e).parent().parent().find('input[name^="amounts"]');
		 		amount.val(amountVal);
		 		setTotalAmt();
		 	}
		 	function setTotalAmt(){
		 		var totalAmt = 0;
		 		$('input[name="amounts"]').each(function(){
		 			var amount = this.value;
		 			if(isNaN(amount) || amount == ''){
		 				return true;
		 			}
		 			totalAmt += parseFloat(amount);
		 		});
		 		$('#amount').val(totalAmt);
		 	}
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doEdit" id="dataForm" method="post">
	<s:hidden name="id"/>
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
						    <td class="formlabel nes">客户姓名</td>
						    <td>
						    	<s:textfield name="clientName" id="clientName" maxlength="10" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">联系电话</td>
						    <td>
						    	<s:textfield name="clientTel" id="clientTel" maxlength="25" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">收货地址</td>
						    <td>
						    	<s:textfield name="address" id="address" maxlength="20" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">订单金额</td>
						    <td>
						    	<s:textfield name="amount" id="amount" maxlength="20" class="{required:true}" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
				  </table>
				  </div>
				  <div  class="contentb" style="margin: auto;text-align: center;" >
				  <table class="form_grid" id="dtTable" style="word-wrap:break-all; width: 90%;text-align: center;">
						<caption></caption>
						  <tr bgcolor="#f2f2f7">
						  <!-- 
						  	<td align="center" >汽车品牌</td> -->
						  	<td align="center" >汽车品牌型号</td>
						  	<td align="center" >产品名称</td>
						  	<td align="center" >材质</td>
						  	<td align="center" >颜色</td>
						  	<td align="center" >价格</td>
						  	<td align="center" >数量</td>
						  	<td align="center" >金额</td>
						  	<td align="center" ><input type="button"  onclick="addRow()"  value="新增一行" id="addRowBtn"/></td>
						  </tr>
						  <!-- 
						  <tr class="product">
							   <td align="center">
							  	    <input type="text" name="carModels" class="model" style="width: 120px;" autocomplete="off"/>&nbsp;
							   </td>   
							   <td align="center">
							  	    <input type="text" name="productNames" style="width: 120px;"/>&nbsp;
							   </td>
							   <td align="center">
							  	    <input type="text" name="materials" style="width: 120px;"/>&nbsp;
							   </td>
							   <td align="center">
									<select name="colors" style="width: 40px;">
										<option value="">红</option>
										<option value="">绿</option>
										<option value="">蓝</option>
									</select>							  	    
							   </td>
							   <td align="center">
							  	    <input type="text" name="prices" style="width: 50px;" class="num" onblur="setAmount(this)"/>&nbsp;
							   </td>
							   <td align="center">
							  	    <input type="text" name="nums" style="width:30px;" maxlength="4" value="1" onblur="setAmount(this)"/>
							   </td>
							   <td align="center">
							  	    <input type="text" name="amounts" style="width:50px;" maxlength="4" readonly="readonly" class="amount"/>
							   </td>
							   <td align="center"><input type="button" value="删除" onclick="deleteThisRow(this)"/></td>
						  </tr>
						   -->
						  <c:forEach items="${list}" var="element">
						  	  <tr class="product">
						  	  	<td>
						  	  		${element.carModel}
							  	    <input type="hidden" name="carModels" value="${element.carModel }"/>&nbsp;
							   </td>   
							   <td align="center">
							   		${element.productName }
							  	    <input type="hidden" name="productNames" value="${element.productName }"/>&nbsp;
							   </td>
							   <td align="center">
							   		${element.material }
							  	    <input type="hidden" name="materials" value="${element.material }"/>&nbsp;
							   </td>
							   <td align="center">
							   		<!-- 
									<select name="colors" style="width: 40px;">
										<option value="">红</option>
										<option value="">绿</option>
										<option value="">蓝</option>
									</select>
									 -->
									${element.color}
									<input type="hidden" name="colors" value="${element.color}">
							   </td>
							   <td align="center">
							   	    <fmt:formatNumber value="${element.price}" pattern="##0.00"/>
							  	    <input type="hidden" name="prices" value="${element.price }"/>&nbsp;
							   </td>
							   <td align="center">
							   		${element.num}
							  	    <input type="hidden" name="nums" value="${element.num}"/>
							   </td>
							   <td align="center">
							   		<fmt:formatNumber value="${element.amount}" pattern="##0.00"/>
							  	    <input type="hidden" name="amounts" value="${element.amount}"/>
							   </td>
							   <td align="center"><input type="button" value="删除" onclick="deleteThisRow(this)"/></td>
						  	  </tr>
						  </c:forEach>
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
