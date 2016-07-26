<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<html lang="zh-cn">
<head>
<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>
<title></title>

<f:css href="/css/page.css" />
<!-- 
<f:css href="/js/plugin/jquery-ui.min.css" /> -->
<link href="http://cdn.bootcss.com/jqueryui/1.12.0-rc.2/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
<f:js src="/js/jquery.min.js" />
<f:js src="/js/plugin/jquery.metadata.js"/>
<f:js src="/js/plugin/jquery.validate.js"/>
<f:js src="/js/sys.js" />
<f:js src="/js/common.js" />
<f:js src="/js/datePicker/WdatePicker.js" defer="defer" />
<!-- 
<f:js src="/js/plugin/jquery-ui.js" /> -->
<script src="http://cdn.bootcss.com/jqueryui/1.12.0-rc.2/jquery-ui.js"></script>

<f:js src="/layer/layer.js"/>
<script type="text/javascript">
	function save() {
		var flag = true;
		$('.product input[type="text"]').each(function() {
			var val = this.value;
			if ($.trim(val) == '') {
				flag = false;
				return false;
			}
		});
		if (!flag) {
			alert('请填写完整后再保存!');
			return false;
		}
		FormUtils.submitFirstTokenForm();
	}

	function setBrandAutoComplete(model) {
		$(model)
				.autocomplete(
						{
							delay : 500,
							minLength : 0,
							source : function(request, response) {
								var keyword = $(brand).val();
								//var word = $('#search-content').val();
								//word = encodeURI(word, "utf-8");
								//alert(keyword);
								$
										.ajax({
											contentType : "application/x-www-form-urlencoded; charset=utf-8",
											type : "post",
											url : CONTEXT_PATH
													+ "/carBrand.do?action=loadByKeyword&keyword="
													+ keyword,
											dataType : "json",
											data : {
												top : 10,
												key : request.term
											},
											success : function(data) {
												response($.each(data.list,
														function(item) {
															return item;
														}));
											}
										});
							},
							select : function(event, ui) {
								//$('#rangeType').val(ui.item.rangeType);
							}
						});
	}
	function setClientInfo(){
		var clientName = $('#clientName').val();
		if(clientName == ''){
			return;
		}
		$.post(CONTEXT_PATH + '/merchantInfo.do?action=getMerchantInfo&mrname='+clientName, function(data) {
			if (data != null) {
				var jsonObj = eval('(' + data + ')');
				var status = jsonObj.status;
				if(status == '1'){
					var contact = jsonObj.manager;
					var clientTel = jsonObj.mobile;
					var address = jsonObj.addr;
					$('#contact').val(contact);
					$('#clientTel').val(clientTel);
					$('#address').val(address);
				}else{
					
				}
			}
		});
	}
	function callback(){
		setClientInfo();
	}
	$().ready(function() {
		//setBrandAutoComplete($('#brand'));
		
		modelAuto();
		//setAmount();
		$('.clientName').blur(function(){
			setClientInfo();
		});
		
		$("#clientName").autocomplete({
			delay : 500,
			minLength: 0,
			 source: function(request, response) {
				var keyword = $('#clientName').val();
				//var word = $('#search-content').val();
				//word = encodeURI(word, "utf-8");
				//alert(keyword);
				if(keyword == ''){
					return;
				}
                $.ajax({
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    type:"post",  
                    url: CONTEXT_PATH+"/merchantInfo.do?action=loadByKeyword&keyword="+keyword,
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
				
		    }
		});
		$('.popup-search').click(function(){
			var bindCode = 'clientName';
			var bindName = 'clientName';
			var toUrl = CONTEXT_PATH
					+ '/merchantInfo.do?action=queryPopUpMerchantInfo&bindCode='
					+ bindCode + '&bindName=' + bindName;
			
			layer.open({
				title : '客户信息',
				type : 2,
				area : [ '960px', '640px' ],
				fix : false, //不固定
				maxmin : true,
				content : toUrl
			});
		});
		
	});
	
	function modelAuto() {
		$('input[name=carModels]')
				.each(
						function(i, e) {
							$(e)
									.autocomplete(
											{
												delay : 500,
												minLength : 0,
												source : function(request,
														response) {
													var keyword = $(e).val();
													//var word = $('#search-content').val();
													//word = encodeURI(word, "utf-8");
													//alert(keyword);
													$
															.ajax({
																contentType : "application/x-www-form-urlencoded; charset=utf-8",
																type : "post",
																url : CONTEXT_PATH
																		+ "/carModel.do?action=loadByKeyword&keyword="
																		+ keyword,
																dataType : "json",
																data : {
																	top : 10,
																	key : request.term
																},
																success : function(
																		data) {
																	response($
																			.each(
																					data.list,
																					function(
																							item) {

																						return item;
																					}));
																}
															});
												},
												select : function(event, ui) {
													//$('#rangeType').val(ui.item.rangeType);
												}
											});
						});
	}
	function addRow() {
		var flag = true;
		$('.product input').each(function() {
			var val = this.value;
			if ($.trim(val) == '') {
				flag = false;
				return false;
			}
		});
		if (!flag) {
			alert('请完善产品明细信息!');
			return false;
		}
		var rowHtml = '<tr class="product">';
		rowHtml += '<td align="center"><input type="text" name="carModels" style="width: 120px;" autocomplete="off"/>&nbsp;</td>';
		rowHtml += '<td align="center"><select name="productNames" style="width: 60px;"><option value="座垫">座垫</option><option value="脚垫">脚垫</option><option value="平板后箱垫">平板后箱垫</option><option value="全包围后箱垫">全包围后箱垫</option><option value="脚垫+丝圈">脚垫+丝圈</option></td>';
		rowHtml += '<td align="center"><input type="text" name="materials" class="material" style="width: 120px;"/>&nbsp;</td>';
		rowHtml += '<td align="center"><input type="text" name="colors" class="color" style="width: 60px;"></td>';
		rowHtml += '<td align="center"><input type="text" name="prices" style="width: 50px;" onblur="setAmount(this)"/>&nbsp;</td>';
		rowHtml += '<td align="center"><input type="text" name="nums" style="width:30px;" maxlength="4" value="1" onblur="setAmount(this)"/></td>';
		rowHtml += '<td align="center"><input type="text" name="amounts" style="width:50px;" maxlength="8" readonly="readonly" class="amount"/></td>';
		rowHtml += '<td align="center"><input type="button" value="删除" onclick="deleteThisRow(this)"/></td>';
		rowHtml += '</tr>';
		$('#dtTable').append(rowHtml);
		SysStyle.setFormGridStyle();
		SysStyle.setButtonStyle();
		modelAuto();
		addMaterialAuto();
		addColorAuto();
	}
	function deleteThisRow(row) {
		$(row).parent().parent().remove();
		setTotalAmt();
	}
	function setAmount(e) {
		var price = $(e).parent().parent().find('input[name^="prices"]');
		var priceVal = price.val();
		if (isNaN(priceVal) || priceVal == '') {
			return;
		}
		var num = $(e).parent().parent().find('input[name^="nums"]');
		var numVal = num.val();
		if (isNaN(numVal) || numVal == '') {
			return;
		}
		var amountVal = parseFloat(priceVal) * parseInt(numVal);
		var amount = $(e).parent().parent().find('input[name^="amounts"]');
		amount.val(amountVal);
		setTotalAmt();
	}
	function setTotalAmt() {
		var totalAmt = 0;
		$('input[name="amounts"]').each(function() {
			var amount = this.value;
			if (isNaN(amount) || amount == '') {
				return true;
			}
			totalAmt += parseFloat(amount);
		});
		$('#amount').val(totalAmt);
		$('#payAmt').val(totalAmt);
	}
	
	
</script>
</head>
<body>

	<f:msg styleClass="msg" />
	<form action="${uri}?action=doAdd" id="dataForm" method="post" class="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b> <b class="b2"></b> <b class="b3"></b> <b
					class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel nes">客户名称</td>
							<td><s:textfield name="clientName" id="clientName"
									maxlength="25" class="{required:true} clientName" title="可输入首字母快速检索"/> 
								<a href="javascript:void(0)" class="popup-search" style="text-decoration: none;">
									<i class="fa fa-search"></i>
								</a>
								<span class="field_tipinfo">不能为空</span></td>
						</tr>
						<tr>
							<td class="formlabel">联系人</td>
							<td><s:textfield name="contact" id="contact"
									maxlength="25" />
							</td>
						</tr>
						<tr>
							<td class="formlabel nes">联系电话</td>
							<td><s:textfield name="clientTel" id="clientTel"
									maxlength="25" class="{required:true}" /> <span
								class="field_tipinfo">不能为空</span></td>
						</tr>
						<tr>
							<td class="formlabel nes">收货地址</td>
							<td><s:textfield name="address" id="address" maxlength="50"
									class="{required:true}" style="width:360px;"/> <span class="field_tipinfo">不能为空</span>
							</td>
						</tr>
						<tr>
							<td class="formlabel nes">订单金额</td>
							<td><s:textfield name="amount" id="amount" maxlength="20"
									class="{required:true}" readonly="true" /> <span
								class="field_tipinfo">不能为空</span></td>
						</tr>
						<tr>
							<td class="formlabel nes">实收金额</td>
							<td><s:textfield name="payAmt" id="payAmt" maxlength="20"
									class="{required:true,num:true}" /> <span
								class="field_tipinfo">不能为空</span></td>
						</tr>
						<tr>
							<td class="formlabel nes">订货日期</td>
							<td><s:textfield name="orderDate" id="orderDate"
									maxlength="8" class="{required:true}"
									onfocus="WdatePicker({dateFmt:'yyyyMMdd'})" /> <span
								class="field_tipinfo">不能为空</span></td>
						</tr>
						<tr>
							<td class="formlabel">备注</td>
							<td><s:textfield name="remark" id="remark" maxlength="50"/> 
							</td>
						</tr>
					</table>
				</div>
				<div class="contentb" style="margin: auto; text-align: center;">
					<table class="form_grid" id="dtTable"
						style="word-wrap: break-all; width: 90%; text-align: center;">
						<caption></caption>
						<tr bgcolor="#f2f2f7">
							<!-- 
						  	<td align="center" >汽车品牌</td> -->
							<td align="center">汽车品牌型号</td>
							<td align="center">产品名称</td>
							<td align="center">材质</td>
							<td align="center">颜色</td>
							<td align="center">价格</td>
							<td align="center">数量</td>
							<td align="center">金额</td>
							<td align="center"><input type="button" onclick="addRow()"
								value="新增一行" id="addRowBtn" /></td>
						</tr>
						<tr class="product">
							<!-- 
							   <td align="center">
							  	    <input type="text" class="brand" name="brand" id="brand" style="width: 120px;" autocomplete="off" onblur="modelAuto(this)"/>&nbsp;
							   </td> -->
							<td align="center"><input type="text" name="carModels"
								class="model" style="width: 120px;" autocomplete="off" />&nbsp;
							</td>
							<td align="center"><select name="productNames"
								style="width: 60px;">
									<option value="脚垫">脚垫</option>
									<option value="座垫">座垫</option>
									<option value="全包围后箱垫">全包围后箱垫</option>
									<option value="平板后箱垫">平板后箱垫</option>
									<option value="脚垫+丝圈">脚垫+丝圈</option>
							</select></td>
							<td align="center"><input type="text" name="materials"
								style="width: 120px;" class="material"/>&nbsp;</td>
							<td align="center"><input type="text" name="colors"
								style="width: 60px;" class="color"/>&nbsp;</td>
							<td align="center"><input type="text" name="prices"
								style="width: 50px;" class="num" onblur="setAmount(this)" />&nbsp;
							</td>
							<td align="center"><input type="text" name="nums"
								style="width: 30px;" maxlength="4" value="1"
								onblur="setAmount(this)" /></td>
							<td align="center"><input type="text" name="amounts"
								style="width: 50px;" maxlength="4" readonly="readonly"
								class="amount" /></td>
							<td align="center"><input type="button" value="删除"
								onclick="deleteThisRow(this)" /></td>
						</tr>

					</table>
					<div class="btnbox">
						<input type="button" id="btnSumit" value="保存" onclick="save()" />
						<input type="button" id="btnReturn" value="取消"
							onclick="gotoUrl('${uri}?action=list')" />
					</div>
				</div>
				<b class="b4"></b> <b class="b3"></b> <b class="b2"></b> <b
					class="b1"></b>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		function addMaterialAuto(){
			$('.material').each(function(i, e) {
				$(e).autocomplete({
					delay : 10,
					minLength : 0,
					autoFocus:true,
					source : function(request,
							response) {
						var keyword = $(e).val();
						var productName = $(e).parent().parent().find("select[name='productNames']").val();
						//var word = $('#search-content').val();
						//word = encodeURI(word, "utf-8");
						//alert(keyword);
						$.ajax({
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							type : "post",
							url : CONTEXT_PATH
									+ "/orderRecord.do?action=loadMaterials&keyword="
									+ keyword+'&productName='+productName,
							dataType : "json",
							data : {
								top : 10,
								key : request.term
							},
							success : function(data) {
								response($.each(data.list, function(item) {
									return item;
								}));
							}
						});
					},
					select : function(event, ui) {
						//$('#rangeType').val(ui.item.rangeType);
					}
				});
			});
		}
		function addColorAuto(){
			$('.color').each(function(i, e) {
				$(e).autocomplete({
					delay : 10,
					minLength : 0,
					autoFocus:true,
					source : function(request,
							response) {
						var keyword = $(e).val();
						var productName = $(e).parent().parent().find("select[name='productNames']").val();
						//var word = $('#search-content').val();
						//word = encodeURI(word, "utf-8");
						//alert(keyword);
						$.ajax({
							contentType : "application/x-www-form-urlencoded; charset=utf-8",
							type : "post",
							url : CONTEXT_PATH
									+ "/orderRecord.do?action=loadColors&keyword="
									+ keyword+'&productName='+productName,
							dataType : "json",
							data : {
								top : 10,
								key : request.term
							},
							success : function(data) {
								response($.each(data.list, function(item) {
									return item;
								}));
							}
						});
					},
					select : function(event, ui) {
						//$('#rangeType').val(ui.item.rangeType);
					}
				});
			});
		}
		$().ready(function(){
			addMaterialAuto();
			addColorAuto();
		});
	</script>
</body>
</html>
