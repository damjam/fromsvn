<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<f:js src="/js/common.js" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />
		<f:css href="/js/plugin/jquery-ui.min.css" />
		<f:js src="/js/plugin/jquery-ui.js" />
		<style type="text/css">
body {
	width: 96%;
	margin-left: 10px;
}
</style>
		<script type="text/javascript">
	$(function() {
		$('#selInfo').click(function() {
			if(!FormUtils.hasRadio('merchantInfo')){
				parent.layer.msg('请选择一条记录',{icon:0,shade:0.2,time:1000});
				return;
			}
			var selVal = FormUtils.getRadioedValue('merchantInfo');
			var array = selVal.split('$');
			var code = array[0];
			var name = array[1];
			var bindCodeId = $('#bindCode').val();
			var bindNameId = $('#bindName').val();
			parent.$('#'+bindCodeId).val(code);
			parent.$('#'+bindNameId).val(name);
			parent.callback();
			parent.layer.close(index);
			
		});
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$('#clearInfo').click(function() {
			var bindCodeId = $('#bindCode').val();
			var bindNameId = $('#bindName').val();
			parent.$('#'+bindCodeId).val('');
			parent.$('#'+bindNameId).val('');
		});

		$('#closeIframe').click(function() {
			parent.layer.close(index);
		});

		$('#btnClear').click(function() {
			$('form :text').val('');
		});
		$('table.data_grid').each(function() {
			var $t = $(this);
			$trs = $t.find('tbody tr');
			$trs.each(function() {
				$tr = $(this);
				$tr.click(function() {
					removeClassAll($trs);
					$(this).addClass("click");
					var radio = $(this).find("td [type='radio']:eq(0)");
					radio.attr("checked", true);
				});
				$tr.dblclick(function() {
					$('#selInfo').click();
				});
				$tr.mouseover(function() {
					$(this).attr("title", "双击可快速选定");
				});
			});
		});
		var removeClassAll = function($trs) {
			$trs.each(function() {
				var $tr = $(this);
				$tr.removeClass("click");
				$tr.removeClass("on");
			});
		};
		
		$("#mrname").autocomplete({
			delay : 500,
			minLength: 0,
			 source: function(request, response) {
				var keyword = $('#mrname').val();
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
	});
</script>
	</head>

	<body>
		<form id="merchantInfo"
			action="merchantInfo.do?action=queryPopUpMerchantInfo"
			method="post">
			<input type="hidden" id="bindCode" name="bindCode" value="${bindCode}">
			<input type="hidden" id="bindName" name="bindName" value="${bindName}">
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
					<div class="contentb">

						<table class="form_grid">
							<tr>
								<td colspan="4" align="left">
									<span
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px; padding-left: 10px;">商户选择</span>
								</td>
							</tr>
							<tr>
								<td height="30" align="right" nowrap="nowrap">
									客户名称
								</td>
								<td height="30">
									<s:textfield name="mrname" id="mrname" maxlength="10"/>
								</td>
								<td height="30" align="right" nowrap="nowrap">
									联系电话
								</td>
								<td height="30">
									<s:textfield name="mobile" id="mobile" maxlength="10"/>
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="查询" />
									<input id="btnClear" style="margin-left: 30px;" type="button"
										value="重置" />
								</td>
							</tr>
						</table>
					</div>
					<b class="b4"></b>
					<b class="b3"></b>
					<b class="b2"></b>
					<b class="b1"></b>
				</div>
			</div>

			<!-- 数据列表区 -->
			<div class="tablebox">
				<table class="data_grid" style="width: 100%">
					<thead>
						<tr>
							<th align="center" nowrap="nowrap" class="titlebg">
								请选择
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								商户名称
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								联系人
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								联系电话
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								送货地址
							</th>
						</tr>
					</thead>

					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr class="shortcut">
								<td align="center" nowrap="nowrap" width="30">
									<input name="merchantInfo" type="radio" 
										value="${element.mrname}$${element.mrname}" />
								</td>
								<td align="center" nowrap="nowrap">
									${element.mrname}
								</td>
								<td align="center" nowrap="nowrap">
									${element.manager}
								</td>
								<td align="center" nowrap="nowrap">
									${element.mobile}
								</td>
								<td align="center" nowrap="nowrap">
									${element.addr}
								</td>
							</tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate />
			</div>
		</form>

		<div style="text-align: center;">
			<input type="button" value="确定" id="selInfo" />
			<!-- 
			<input style="margin-left: 30px;" type="submit" value="清除"
				id="clearInfo" /> -->
			<input style="margin-left: 30px;" type="button" value="关闭"
				id="closeIframe" />
		</div>
		
	</body>
</html>