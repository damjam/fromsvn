<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>

<%@ include file="/pages/common/taglibs.jsp" %>

<html lang="zh-cn">
	<head>

		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/common.js" />
		<f:css href="/css/page.css" />

		<script type="text/javascript">
			$(function() {
				//定时计划增加
				$('#btnTimerAdd').click(
					function() {
						var url="/timer.do?action=toEdit";
						gotoUrl(url);
					});
		
				//定时计划清除
				$('#btnTimerClear').click(function() {
					FormUtils.reset();
				});
			});
			$().ready(function(){
				var height = document.body.scrollHeight;
				parent.adjustHeight(height, 0);
			});
		</script>
	</head>

	<body>
		<f:msg/>
		<form id="timer" action="timer.do?action=query" method="post">
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					<tr>
						<td class="formlabel">
							beanName
						</td>
						<td>
							<s:textfield name="beanName" maxlength="20"></s:textfield>
						</td>
						<td class="formlabel">
							beanName中文名
						</td>
						<td>
							<s:textfield name="beanNameCh" maxlength="20"></s:textfield>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="5" nowrap="nowrap">
							<input type="submit" value="查询" id="input_btn2" />&nbsp;
							<input type="button" value="重置" id="btnClear"
								onclick="FormUtils.reset('timer');" />&nbsp;
							<input id="btnTimerAdd" type="button" value="新增" />
						</td>
					</tr>
				</table>
			</div>
			<!-- 数据列表区 -->
		</div>
			<div class="tablebox">
				<table class="data_grid">
					<thead>
						<tr align="center" class="titlebg">
							<th align="center" nowrap="nowrap" class="titlebg">
								bean
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								bean中文名
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								参数1
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								参数2
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								触发时间
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								备注
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								操作
							</th>
						</tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<c:forEach items="${list}" var="element">
							<tr align="center">
								<td>
									${element.beanName }
								</td>
								<td>
									${element.beanNameCh }
								</td>
								<td>
									${element.para1}
								</td>
								<td>
									${element.para2}
								</td>
								<td>
									${element.triggertime}
								</td>
								<td>
									${element.remark}
								</td>
								<td>
									<span class="redlink"> <a
										href="timer.do?action=toEdit&id=${element.id}">修改</a> <a
										onclick="return confirm('确认删除?');"
										href="timer.do?action=delete&id=${element.id }">删除</a> </span>
								</td>
							</tr>
						</c:forEach>
					</f:showDataGrid>
				</table>
				<f:paginate />
			</div>
		</form>
	</body>
</html>