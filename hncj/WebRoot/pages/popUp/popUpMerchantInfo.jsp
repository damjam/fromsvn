<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

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
		$('#selMerchantInfo').click(function() {
			var i = 0;
			var selValue = "";
			$(':radio').each(function() {
				if ($(this).attr('checked')) {
					selValue = $(this).val();
					i++;
				}

			});

			if (i == 0 || i > 1) {
				alert("请选择需要的部门，有且仅有一条");
				return false;
			}

			window.returnValue = selValue;
			window.close();

		});

		$('#clearMerchantInfo').click(function() {
			var selValue = "";
			window.returnValue = "$";
			window.close();
		});

		$('#winClose').click(function() {
			window.close();
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
					$('#selMerchantInfo').click();
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
		<form id="merchantInfo"
			action="merchantInfo.do?action=queryPopUpMerchantInfo"
			method="post">
			<div class="userbox">
				<div>
					<b class="b1"></b>
					<b class="b2"></b>
					<b class="b3"></b>
					<b class="b4"></b>
					<div class="contentb">

						<table class="form_grid" width="100%" border="0" cellspacing="3"
							cellpadding="0">
							<tr>
								<td colspan="4" align="left">
									<span
										style="font-size: 14px; font-weight: bold; padding-bottom: 10px; padding-left: 10px;">商户选择</span>
								</td>
							</tr>
							<tr>
								<td height="30" align="right" nowrap="nowrap">
									商户名称
								</td>
								<td height="30">
									<input type="text" name="mrname" id="mrname" style="width: 260px;" value="${merchantInfoActionForm.mrname}"/>
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
				<table class='data_grid' width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<thead>
						<tr>
							<th align="center" nowrap="nowrap" class="titlebg">
								请选择
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								商户名称
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								行业类别
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								经营范围
							</th>
						</tr>
					</thead>

					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr class="shortcut">
								<td align="center" nowrap="nowrap" width="30">
									<input name="merchantInfo" type="radio" 
										value="${element.id}$${element.mrname}" />
								</td>
								<td align="center" nowrap="nowrap">
									${element.mrname}
								</td>
								<td align="center" nowrap="nowrap">
									${element.industry}
								</td>
								<td align="center" nowrap="nowrap">
									${element.busiScope}
								</td>
							</tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate />
			</div>
		</form>

		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center">
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td height="30" colspan="4" align="center">
					<input type="button" value="确定" id="selMerchantInfo" />
					<input style="margin-left: 30px;" type="submit" value="清除"
						id="clearMerchantInfo" />
					<input style="margin-left: 30px;" type="button" value="关闭"
						id="winClose" />
				</td>
			</tr>
		</table>
		<br />
		<br />
		<br />
	</body>
</html>