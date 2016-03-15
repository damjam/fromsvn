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
				
			});
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=depositDetailBill&id='+id);
			}
			function addDeposit(){
				gotoUrl('/accountJournal.do?action=toDeposit');
			}
			function addWithdraw(){
				gotoUrl('/accountJournal.do?action=toWithdraw');
			}
			function addReverse(){
				gotoUrl('/accountJournal.do?action=toReverse');
			}
			function change(){
				$('#gatherPeriod').val('');
				var gatherWay = $('#gatherWay').val();
				if(gatherWay == 'D'){
					$('#td1').html('汇总日期');
					$('#gatherPeriodD').show();
					$('#gatherPeriodM').hide();
					$('#gatherPeriodY').hide();
				}else if(gatherWay == 'M'){
					$('#td1').html('汇总月份');
					$('#gatherPeriodD').hide();
					$('#gatherPeriodM').show();
					$('#gatherPeriodY').hide();
				}else if(gatherWay == 'Y'){
					$('#td1').html('汇总年份');
					$('#gatherPeriodD').hide();
					$('#gatherPeriodM').hide();
					$('#gatherPeriodY').show();
				}
			}
			$().ready(function(){
				$('#gatherPeriodM').hide();
				$('#gatherPeriodY').hide();
			});
			function query(){
				var gatherWay = $('#gatherWay').val();
				var gatherPeriod = $('#gatherPeriod'+gatherWay).val();
				if(gatherPeriod == ''){
					alert('汇总日期不能为空');
					$('#gatherPeriod'+gatherWay).focus();
					return;
				}
				$('#frame0').attr('src', CONTEXT_PATH+"/reportAction.do?action=gather&gatherWay="+gatherWay+"&gatherPeriod="+gatherPeriod);
			}
		</script> 
	</head>
	<body>
		
		<f:msg styleClass="msg" />
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								汇总周期
							</td>
							<td>
								<select name="gatherWay" id="gatherWay" onchange="change()">
									<option value="D">按天</option>
									<option value="M">按月</option>
									<option value="Y">按年</option>
								</select>
							</td>
							<td class="formlabel nes" id="td1">
								汇总日期
							</td>
							<td id="td2">
								<input type="text" name="gatherPeriod" id="gatherPeriodD" onclick="WdatePicker({dateFmt:'yyyyMMdd'})" value="${today}"/>
								<input type="text" name="gatherPeriod" id="gatherPeriodM" onclick="WdatePicker({dateFmt:'yyyyMM'})"/>
								<input type="text" name="gatherPeriod" id="gatherPeriodY" onclick="WdatePicker({dateFmt:'yyyy'})"/>
							</td>
							<c:if test="${sessionScope.isHQ == true}">
								<td class="formlabel">机构</td>
								<td>
									<s:select name="branchNo" list="branches" listKey="key" listValue="value" headerKey="" headerValue="---全部---"></s:select>
								</td>
							</c:if>	
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry" onclick="query();"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox" style="height:100%">			
				<iframe id="frame0" width="100%"  height="100%" ></iframe>
			</div> 
	</body>
</html>
