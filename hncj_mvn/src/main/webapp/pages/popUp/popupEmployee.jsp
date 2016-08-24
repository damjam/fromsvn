<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<html lang="zh-cn">
<head>
<base target="_self" />
<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>
<f:js src="/js/common.js" />
<f:js src="/js/jquery.js" />
<f:js src="/js/sys.js" />
<f:js src="/js/paginater.js" />
<f:css href="/css/page.css" />
<style type="text/css">
body {
	width: 96%;
	margin-left: 10px;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#selInfo').click(function() {
			if (!FormUtils.hasRadio('radioInfo')) {
				parent.layer.msg('请选择一条记录', {
					icon : 0,
					shade : 0.2,
					time : 1000
				});
				return;
			}
			var selVal = FormUtils.getRadioedValue('radioInfo');
			var array = selVal.split('$');
			var code = array[0];
			var name = array[1];
			var bindCodeId = $('#bindCode').val();
			var bindNameId = $('#bindName').val();
			parent.$('#' + bindCodeId).val(code);
			parent.$('#' + bindNameId).val(name);
			parent.layer.close(index);

		});
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		$('#clearInfo').click(function() {
			var bindCodeId = $('#bindCode').val();
			var bindNameId = $('#bindName').val();
			parent.$('#' + bindCodeId).val('');
			parent.$('#' + bindNameId).val('');
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
	});
</script>
</head>

<body>
	<form id="radionInfo" action="${uri}?action=queryPopup" method="post">
		<input type="hidden" id="bindCode" name="bindCode" value="${bindCode}">
		<input type="hidden" id="bindName" name="bindName" value="${bindName}">
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<tr>
						<td colspan="4" align="left"><span
							style="font-size: 14px; font-weight: bold; padding-bottom: 10px; padding-left: 10px;">商户选择</span>
						</td>
					</tr>
					<tr>
						<td height="30" align="right" nowrap="nowrap">姓名</td>
						<td height="30"><s:textfield id="name" name="name"
								maxlength="10" /></td>
						<td height="30" align="right" nowrap="nowrap">职位</td>
						<td height="30"><s:select list="#request.positionTypes"
								headerKey="" headerValue="---请选择---" listKey="key"
								listValue="value" name="position" /></td>
					</tr>
					<tr></tr>
					<tr></tr>
					<tr>
						<td height="30" align="right">&nbsp;</td>
						<td height="30" colspan="3"><input type="submit" value="查询" />
							<input id="btnClear" style="margin-left: 30px;" type="button"
							value="重置" /></td>
					</tr>
				</table>
			</div>
		</div>
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class="data_grid">
				<thead>
					<tr>
						<th align="center" nowrap="nowrap" class="titlebg">请选择</th>
						<th align="center" nowrap="nowrap" class="titlebg">姓名</th>
						<th align="center" nowrap="nowrap" class="titlebg">性别</th>
						<th align="center" nowrap="nowrap" class="titlebg">职位</th>
						<th align="center" nowrap="nowrap" class="titlebg">电话</th>
					</tr>
				</thead>

				<f:showDataGrid name="list" msg=" " styleClass="data_grid">
					<c:forEach items="${list}" var="element">
						<tr class="shortcut">
							<td align="center" nowrap="nowrap" width="30"><input
								name="radioInfo" type="radio"
								value="${element.id}$${element.name}" /></td>
							<td align="center" nowrap="nowrap">${element.name}</td>
							<td align="center" nowrap="nowrap">${element.positionName}</td>
							<td align="center" nowrap="nowrap"><f:type
									className="SexType" value="${element.gender}" /></td>
							<td align="center" nowrap="nowrap">${element.tel}</td>
						</tr>
					</c:forEach>
				</f:showDataGrid>
			</table>
			<f:paginate />
		</div>
	</form>

	<div style="text-align: center;">
		<input type="button" value="确定" id="selInfo" /> <input
			style="margin-left: 30px;" type="submit" value="清除" id="clearInfo" />
		<input style="margin-left: 30px;" type="button" value="关闭"
			id="closeIframe" />
	</div>

</body>
</html>