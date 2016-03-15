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
				gotoUrl('${uri}?action=toDeposit');
			}
			function addWithdraw(){
				gotoUrl('${uri}?action=toWithdraw');
			}
			function addReverse(){
				gotoUrl('${uri}?action=toReverse');
			}
			
			function query(){
				var tradeDate = $('#tradeDate').val();
				if(tradeDate == ''){
					alert('交易日期不能为空');
					$('#tradeDate').focus();
					return;
				}
				$('#frame0').attr('src', CONTEXT_PATH+"/reportAction.do?action=tradeReport&tradeDate="+tradeDate);
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
							
							<td class="formlabel nes" id="td1">
								交易日期
							</td>
							<td id="td2">
								<input type="text" name="tradeDate" id="tradeDate" value="${today}" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
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
