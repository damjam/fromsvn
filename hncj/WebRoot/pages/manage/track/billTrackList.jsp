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
					var url="/billTrack.do?action=toAdd";
					gotoUrl(url);   
				});
			});
			
			function delInfo(id){
				if(window.confirm("确认删除?")){
					gotoUrl('/billTrack.do?action=delete&id='+id);
				}
			}
			function discard(id){
				if(window.confirm("确认不再显示该记录?")){
					var url="/billTrack.do?action=discard&id="+id;
					gotoUrl(url);
				}
			}
			function sendNotice(id,cel){
				if(cel == ''){
					//alert('没有可发送的手机号码');
					//return;
				}
				if(window.confirm("将发送短信至"+cel+"，确认操作?")){
					var url="/billTrack.do?action=sendNotice&id="+id;
					gotoUrl(url);  
				}
			}
			function generate(){
				gotoUrl("/billTrack.do?action=generateAll");
				$('#queryForm').find(':button, :submit, :reset').attr('disabled', true);
			}
		</script> 
	</head>
	<body>
		<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
		<f:msg styleClass="msg" />
		<html:form action="/billTrack.do?action=list" styleId="queryForm">
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
								业主姓名
							</td>
							<td>
								<html:text property="ownerName" styleId="ownerName" maxlength="10"/>
							</td>
							<td class="formlabel">
								联系电话
							</td>
							<td>
								<html:text property="ownerCel" styleId="ownerCel" maxlength="10"/>
							</td>
						</tr>
						<tr>
							<td class="formlabel">
								到期天数
							</td>
							<td>
								<html:select property="leftDays" style="width:166px">
									<html:option value="">---全部---</html:option>
									<html:options collection="remainDays" property="value" labelProperty="name" />
								</html:select>
							</td>
							<td class="formlabel">
								账单类型
							</td>
							<td>
								<html:select property="billType" style="width:166px">
									<html:option value="">---全部---</html:option>
									<html:options collection="billTypes" property="value" labelProperty="name" />
								</html:select>
							</td>	
						</tr>	
						<tr>
						    <td></td>
							<td colspan="5">
								<input type="button" value="查询" id="btnQry"/>&nbsp;
								<input type="button" value="重置" id="btnClear" />&nbsp;
								<c:if test="${sessionScope.SESSION_USER.userType eq 'superUser'}">
									<input type="button" value="生成账单提醒" id="" onclick="generate()"/>&nbsp;
								</c:if>
								<!-- 
								<input type="button" value="新增" id="btnAdd"/> -->
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
						 	<td>房屋编号</td>
						 	<td>业主姓名</td>
						    <td>账单类型</td>
						    <td>账单号</td>
						    <td>到期时间</td>
						    <td>剩余天数</td>
						    <td>欠费天数</td>
						    <td>联系电话</td>
						    <td>已通知次数</td>
						    <td>操作</td>
						 </tr>
					</thead>
					<f:showDataGrid name="list" msg=" " styleClass="data_grid">
						<logic:iterate id="element" name="list">
							<tr align="center">
								<td>${element.houseSn}</td>
								<td>${element.ownerName}</td>
								<td><f:type className="BillType" value="${element.billType}" /> </td>
								<td>${element.billId}</td>
								<td>${element.expireDate}</td>
								<td>${element.leftDays}</td>
								<td>${element.overDays}</td>
								<td>${element.ownerCel}</td>
								<td>${element.noticeTimes}</td> 
							    <td class="redlink">
							    	<a href="javascript:discard('${element.id}')" >不再提醒</a>
							    	<a href="javascript:sendNotice('${element.id}','${element.ownerCel}')" >发送通知</a>
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
