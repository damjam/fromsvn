<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>

<%@ include file="/pages/common/meta.jsp"%>
<%@ include file="/pages/common/sys.jsp"%>

<html>
	<head>
		
		<title>黄金账户查询</title>
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/validate.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/paginater.js" />
		<script type="text/javascript">
			$(function(){
				$("#btnQry").live("click",function(){
					if($("#startDate").val()=="" || $("#endDate").val()==""){
						alert("查询的开始日期或结束日期都不能为空！");
						return false;
					}else if(parseInt($("#startDate").val(),10)>parseInt($("#endDate").val(),10)){
						alert("查询的开始日期不能大于结束日期！");
						return false;
					}else{
						$("#dataForm").submit();
						return true;
					}
				});
			});
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/investQueryAction.do?action=queryGoldAcct" styleClass="validate-tip" styleId="dataForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}A2036(查汇总信息),2034查交易流水</caption>
						<tr>
							<td class="formlabel">
								定投账号
							</td>
							<td>
								<html:select property="acctNo">
									<html:options collection="payChnls" labelProperty="investAcctNo" property="investAcctNo"/>
								</html:select>
							</td>
							<td class="formlabel">
								日期
							</td>
							<td>
								<html:text property="startDate" styleId="startDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'%Date'});" style="width:70px;"/>
								&nbsp;
								-
								&nbsp;
								<html:text property="endDate" styleId="endDate" onfocus="WdatePicker({dateFmt:'yyyyMMdd',maxDate:'%Date'});" style="width:70px;"/>
							</td>
							<td colspan="4"></td>
						</tr>
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" onclick="FormUtils.reset('dataForm', ['startDate', 'endDate']);"/>&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			<!-- 数据列表区 -->
				<div class="tablebox">	
				<table class="detail_grid" width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td width="15%">累计积存重量(2039)</td>
							<td width="18%">${resMap.totalDeposit}</td>
							<td width="15%">累计赎回重量(2039)</td>
							<td width="18%">${resMap.totalTakeout}</td>
							<td width="15%">当前黄金账户余额(2044)</td>
							<td width="18%">${resMap.currentMoney}</td>
						</tr>
						<tr>	
							<td>当前账户实际定投金额</td>
							<td>${resMap.goldRealBal}</td>
							<td>当前账户定投均价</td>
							<td>${resMap.avgPrice}</td>
							<td>上日定投价格(2054,开始日期，结束日期输入上日日期)</td>
							<td>${resMap.lastDayPrice}</td>
						</tr>
						<tr>
							<td>黄金账户账面价值</td>
							<td>${resMap.goldVirtualBal}</td>
							<td>账面盈亏</td>
							<td colspan="3">${resMap.accountSurplus}</td>
						</tr>
					</table>
					<br/>
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td>变动时间</td>
						 	<td>变动类型</td>
						    <td>变化值</td>
						    <td>当前重量</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>{element.exch_time.value}</td>
								<td>{element.change_type.value}</td>
								<td>{element.exch_weight.value}</td>
								<td></td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
