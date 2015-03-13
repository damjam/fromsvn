<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%
	response.setHeader("Cache-Control", "no-cache");
%>
<jsp:directive.page import="flink.util.Paginater;" />

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>ѡ���̻�</title>
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>		
		<script type="text/javascript">
		$(function(){
			$('#selMerchantInfo').click(function(){
				var i=0;
				var selValue="";
				$(':radio').each(function(){
					if($(this).attr('checked')){
						selValue=$(this).val();
						i++;
					}
					
				});
				
				if(i==0 || i>1){
					alert("��ѡ����Ҫ�Ĳ��ţ����ҽ���һ��");
					return false;
				}
				
				window.returnValue=selValue;
				window.close();
				
				
			});
			
			$('#clearMerchantInfo').click(function(){
				var selValue="";
				window.returnValue="$";
				window.close();
			});
			
			$('#winClose').click(function(){
				window.close();
			});
			
			$('#btnClear').click(function(){
				$('form :text').val('');
			});
			$('table.data_grid').each(function() {
				var $t = $(this);
				$trs = $t.find('tbody tr');
				$trs.each(function(){
					$tr = $(this);
					$tr.click(function(){
						removeClassAll($trs);
						$(this).addClass("click");
						var radio = $(this).find("td [type='radio']:eq(0)");
						radio.attr("checked", true);
					});
					$tr.dblclick(function(){
						$('#selMerchantInfo').click();
					});
					$tr.mouseover(function(){
						$(this).attr("title", "˫���ɿ���ѡ��");
					});
				});
			});
			var removeClassAll = function($trs){
				$trs.each(function(){
					var $tr = $(this);
					$tr.removeClass("click");
					$tr.removeClass("on");
				});
			};
		});
		
	    </script>
	</head>

	<body>
		<form id="merchantInfo" action="merchantAction.do?action=queryPopUpMerchantInfo" method="post" >
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
									<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px; padding-left: 10px;">�̻�ѡ��</span>
								</td>
							</tr>
							<tr>
								<td height="30" align="right" nowrap="nowrap">
									�̻����
								</td>
								<td  height="30">
									<input type="text"  style="width: 260px;" name="merchantNo"  value="${merchantInfoOperForm.merchantNo }"/>
									<input type="hidden"  style="width: 260px;" name="branchNo"  value="${merchantInfoOperForm.branchNo }"/>
								</td>
								<td height="30" align="right">
									�̻�����
								</td>
								<td height="30">
									<input  style="width: 260px;" name="merchantName" type="text"
											value="${merchantInfoOperForm.merchantName }"/>
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="��ѯ" /> 
									<input id="btnClear" style="margin-left: 30px;" type="button" value="���" />
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

			<!-- �����б��� -->
			<div class="tablebox">
				<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th align="center" nowrap="nowrap" class="titlebg">
								��ѡ��
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								�̻����
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								�̻�����
							</th>
						</tr>
					</thead>
					
					
					<tbody>
						<c:if test="${empty merchantInfoList}">
							<tr>
								<td colspan="10">
									û������
								</td>
							</tr>
						</c:if>
						<c:if test="${not empty merchantInfoList}">
							<c:forEach items="${merchantInfoList}" var="merchantInfo">
								<tr class="shortcut">
									<td align="center" nowrap="nowrap">
										<input name="merchantInfo" type="radio"" value="${merchantInfo.merchantNo}$${merchantInfo.merchantName}"/>
									</td>
								
									<td align="center" nowrap="nowrap">
										${merchantInfo.merchantNo}
									</td>
									<td align="center" nowrap="nowrap">
										${merchantInfo.merchantName}
									</td>
								</tr>
							</c:forEach>

						</c:if>
					</tbody>
				</table>
				<f:paginate />
			</div>
		</form>

		<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td height="30" colspan="4" align="center">
					<input type="button" value="ȷ��" id="selMerchantInfo"/>
					<input style="margin-left: 30px;" type="submit" value="���" id="clearMerchantInfo"/>
					<input style="margin-left: 30px;" type="button" value="�ر�" id="winClose"/>
				</td>
			</tr>
		</table>
		<br />
		<br />
		<br />
	</body>
</html>