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
				$('#btnAdd').click(function(){
					gotoUrl('/depositBill.do?action=toAdd');
				});
				
			});
			function charge(id){
				if(window.confirm("确认收费?")){
					gotoUrl('/icDeposit.do?action=charge&id='+id);
				}
			}
			function openReport(id){
				window.open(CONTEXT_PATH+'/reportAction.do?action=icDeposit&id='+id);
			}
			function delRecord(id){
				if(!window.confirm("确认删除?")){
					return;
				}
				gotoUrl('/icDeposit.do?action=deleteIcDeposit&id='+id);
			}
			
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/depositBill.do?action=list" styleId="queryForm">
			<!-- 查询功能区 -->
			<div class="userbox">
				<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
				<div class="contentb">
					<table border="0" cellspacing="3" cellpadding="0" class="form_grid">
						<caption>${ACT.name}</caption>
						<tr>
							<td class="formlabel">
								房屋编号
							</td>
							<td>
								<html:text property="houseSn" styleId="houseSn" maxlength="10"/>
							</td>
							<td class="formlabel">
								充值日期
							</td>
							<td>
								<html:text property="startChargeDate" styleId="startChargeDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>&nbsp;-
								<html:text property="endChargeDate" styleId="endChargeDate" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyyMMdd'})"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel nes">状态</td>
						    <td>
						    	<html:select property="state" styleId="state">
						    		<html:option value="">---全部---</html:option>
						    		<html:options collection="billStates" property="value" labelProperty="name" />
						    	</html:select>
						    </td>
							<td class="formlabel">
								账单号
							</td>
							<td>
								<html:text property="id" styleId="id" maxlength="20"/>
							</td>
							<td class="formlabel">
								年份
							</td>
							<td>
								<html:text property="year" styleId="year" style="width:70px;" onclick="WdatePicker({dateFmt:'yyyy'})"/>
							</td>
						</tr>	
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<input type="button" value="新增" id="btnAdd"/>
							</td>
						</tr>
					</table>
				</div>
				<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
			</div>
			
			<div class="tablebox" id="listDiv" style="display: block; margin: -10px 0 -30px 0;">
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin:0 0 10px 0">
					<caption>汇总信息</caption>
					<thead>
						<tr class="titlebg">
							<td align="center">电费充值笔数</td>
							<td align="center">电费充值金额（元）</td>
							<td align="center">燃气费充值笔数</td>
							<td align="center">燃气充值金额（元）</td>
							<td align="center">水费充值笔数</td>
							<td align="center">水费充值金额（元）</td>
							<td align="center">总笔数</td>
							<td align="center">总金额（元）</td>
						</tr>
					</thead>
					<tr>
						<td align="center">${sumInfo.elecCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="elecAmt" format="##0.00"/></td>
						<td align="center">${sumInfo.gasCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="gasAmt" format="##0.00"/></td>
						<td align="center">${sumInfo.waterCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="waterAmt" format="##0.00"/></td>
						<td align="center">${sumInfo.totatCnt}</td>
						<td align="center"><bean:write name="sumInfo" property="totalAmt" format="##0.00"/></td>
					</tr>
				</table>
			</div>
			<!-- 数据列表区 -->
			<div class="tablebox">			
				<table class="data_grid" width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						 <tr align="center" class="titlebg">
						 	<td >账单号</td>
						 	<td >房屋编号</td>
						 	<td >付款人</td>
						    <td >付款时间</td>
						    <td >金额</td>
						    <td >IC卡类型</td>
						    <td >收款人</td>
						    <td >状态</td>
						    <td >备注</td>
						    <td>操作</td>
						 </tr>
					</thead>
					
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.id}</td>
								<td>${element.houseSn}</td>
								<td>${element.payerName}</td>
								<td width="120"><bean:write name="element" property="chargeDate" format="yyyy-MM-dd HH:mm:ss"/></td>
								<td><bean:write name="element" property="amount" format="##0.00"/></td>
								<td><f:type className="IcCardType" value="${element.icCardType}" /> </td>
								<td>${element.chargeUser}</td>
								<td>
							    	<f:state className="BillState" value="${element.state}" />
							    </td>
								<td>${element.remark}</td>
								<td class="redlink">
							    	<logic:equal value="00" name="element" property="state">
							    		<a href="javascript:charge('${element.id}')">收费</a>
							    		<a href="javascript:delRecord('${element.id}')">删除</a>
							    	</logic:equal>
							    	<logic:equal value="01" name="element" property="state">
							    		<a href="javascript:openReport('${element.id}')">打印</a>
							    	</logic:equal>
							    </td>
						    </tr>
						</logic:iterate>
					</f:showDataGrid>
				</table>
				<f:paginate/>			
			</div> 
		</html:form>
	</body>
</html>
