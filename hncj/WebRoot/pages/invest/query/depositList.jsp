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
		<title>积存情况查询</title>
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
					alert("开始日期或结束日期不能为空！");
					return false;
				}else if(parseInt($("#startDate").val(),10)>parseInt($("#endDate").val(),10)){
					alert("开始日期不能大于结束日期！");
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
		<html:form action="/investQueryAction.do?action=queryDepositRecord" styleClass="validate-tip" styleId="dataForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}A2034</caption>
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
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td>积存时间</td>
						 	<td>积存重量</td>
						    <td>积存价格</td>
						    <td>积存手续费</td>
						    <td>积存金额</td>
						    <td>预扣款金额</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.exch_date.value}</td>
								<td>${element.match_amt.value}</td>
								<td>${element.pub_price.value}</td>
								<td>${element.pro_fee.value}</td>
								<td>${element.real_exch_bal.value}</td>
								<td>${element.exch_bal.value}</td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
