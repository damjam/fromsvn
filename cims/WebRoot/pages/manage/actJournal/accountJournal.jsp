<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<html>
	<head>
		
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
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/accountJournal.do?action=list" styleId="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								日期
							</td>
							<td>
								<html:text property="startCreateDate" styleId="startCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<html:text property="endCreateDate" styleId="endCreateDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
							<td class="formlabel">
								出入账类型
							</td>
							<td>
								<html:select property="inoutType" styleId="inoutType">
						    		<html:option value="">---全部---</html:option>
						    		<html:options collection="inoutTypes" property="value" labelProperty="name" />
						    	</html:select>
							</td>
							<td class="formlabel">
								交易类型
							</td>
							<td>
								<html:select property="tradeType" styleId="tradeType">
						    		<html:option value="">---全部---</html:option>
						    		<html:options collection="tradeTypes" property="value" labelProperty="name" />
						    	</html:select>
							</td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="收入" onclick="addDeposit()"/>&nbsp;
								<input type="button" value="支出" onclick="addWithdraw()"/>&nbsp;
								<input type="button" value="冲正" onclick="addReverse()"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<div class="tablebox" id="listDiv" style="display: block; margin: -10px 0 -30px 0;">
			<!-- 汇总信息 -->
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:0 0 10px 0">
					<caption>汇总信息</caption>
					<thead>
						<tr class="titlebg">
							<td align="center">收入笔数</td>
							<td align="center">收入金额（元）</td>
							<td align="center">支出笔数</td>
							<td align="center">支出金额（元）</td>
							<td align="center">总笔数</td>
							<td align="center">净收入（元）</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.inCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="inAmt" format="##0.00"/></td>
						<td align="center">${sumInfo.outCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="outAmt" format="##0.00"/></td>
						<td align="center">${sumInfo.totalCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="netAmt" format="##0.00"/></td>
					</tr>
				</table>
			</div>
			
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >流水号</td>
						 	<td >收支类型</td>
						 	<td >交易类型</td>
						 	<td >交易金额</td>
						    <td >交易时间</td>
						    <td >关联账单号</td>
						    <td >当前余额</td>
						    <td >备注</td>
						    <td >经手人</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.id}</td>
								<td><f:type className="InoutType" value="${element.inoutType}"/></td>
								<td><f:type className="TradeType" value="${element.tradeType}"/></td>
								<td align="right"><bean:write name="element" property="amount" format="##0.00"/></td>
								<td><bean:write name="element" property="createDate" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td>${element.billId}</td>
								<td align="right"><bean:write name="element" property="balance" format="##0.00"/></td>
						    	<td>${element.remark}</td>
						    	<td>${element.createUser}</td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
