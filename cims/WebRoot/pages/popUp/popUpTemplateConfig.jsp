<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>

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
		<base target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />
		<f:css href="/css/base.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				$('#selTemplateConfig').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("��ѡ����Ҫ�Ľ�ɫ�����ҽ���һ��");
						return false;
					}
					
					window.returnValue=selValue;
					window.close();
					
					
					});
				
					$('#clearTemplateConfig').click(function(){
						var selValue="";
						window.returnValue="$";
						window.close();
					});
					
					$('#winClose').click(function(){
						window.close();
					});
					
					$('#btnClear').click(function(){
						$("form :text").val('');
					});
				
				$('#busiType').val($('#hidBusiType').val());
				$('#tempType').val($('#hidTempType').val());
			});
	    </script> 
	  <title>ѡ���ļ�ģ��</title>
	</head>
	<body>
	<!-- ��ѯ������ -->
	<form id="query" action="templateConfig.do?action=queryPopUpTemplateConfig"
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
							<td colspan="4" align="left"  nowrap="nowrap">
								<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">�ļ�ģ�����ò�ѯ</span>
							</td>
						</tr>
						<tr>
							<td height="30" align="right"  nowrap="nowrap">
								ģ����
							</td>
							<td height="30"  nowrap="nowrap">
								<input type="text" class="userbox_bt" style="width: 100px;" name="tempId" id="tempId"  maxlength="6" value ="${templateConfig.tempId}"/>
							</td>
							<td height="30" align="right"  nowrap="nowrap">
								ģ������
							</td>
							<td height="30"  nowrap="nowrap">
								<input type="text"  name="tempName" id="tempName"  maxlength="6" value ="${templateConfig.tempName}"/>
							</td>
						</tr>
						<tr>
							<td height="30" align="right"  nowrap="nowrap">
								��������
							</td>
							<td height="30"  nowrap="nowrap">
								<input type="text" style="width: 100px;" name="branchName" id="branchName"  maxlength="6" value ="${templateConfig.branchName}"/>
							</td>
							<td height="30" align="right"  nowrap="nowrap">
								�̻�����
							</td>
							<td height="30"  nowrap="nowrap">
								<input type="text"  name="merchantName" id="merchantName"  maxlength="6" value ="${templateConfig.merchantName}"/>
							</td>
						</tr>
						<tr>
							<td height="30" align="right">
								ҵ������
							</td>
							<td height="30"  nowrap="nowrap">
								<select  id="busiType" name="busiType" >
									<option value='' >---��ѡ��---</option>
									<option value='0' >����</option>
									<option value='1' >����</option>
								</select>
								<input id="hidBusiType" value="${templateConfig.busiType}" type="hidden"/>
							</td>
							<td height="30" align="right"  nowrap="nowrap">
								ģ������
							</td>
							<td height="30"  nowrap="nowrap">
							<select   id="tempType" name="tempType" >
								<option value='' >---��ѡ��---</option>
								<option value='0' >�����ļ�</option>
								<option value='1' >�����ļ�</option>
								<option value='2' >�����ļ�</option>
							</select>
							<input id="hidTempType" value="${templateConfig.tempType}" type="hidden" />
						  </td>
						</tr>
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
						<th align="left" nowrap="nowrap" class="titlebg">
							��ѡ��
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							ģ����
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							ģ������
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							��������
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							��������
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							�����̻�
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							�̻�����
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							ҵ������
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							ģ������
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							�Ƿ�Ĭ��
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							�ļ�����
						</th>
					</tr>
				</thead>
				
				
				<tbody>
					<c:if test="${empty templateConfigList}">
						<tr>
							<td colspan="10">
								û������
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty templateConfigList}">
						<c:forEach items="${templateConfigList}" var="templateConfig">
							<tr class="shortcut">
								<td align="center" nowrap="nowrap">
									<input name="templateConfig" type="radio"" value="${templateConfig.tempId}$${templateConfig.tempName}"/>
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.tempId}
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.tempName}
								</td>
								
								<td align="left" nowrap="nowrap">
									${templateConfig.branchNo}
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.branchName}
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.merchantNo}
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.merchantName}
								</td>
								<td align="left" nowrap="nowrap">
									<c:choose>
										<c:when test="${templateConfig.busiType eq '0'}">
											����
										</c:when>
										<c:when test="${templateConfig.busiType eq '1'}">
											����
										</c:when>
									</c:choose>
								</td>
								<td align="left" nowrap="nowrap">
									<c:choose>
										<c:when test="${templateConfig.tempType eq '0'}">
											�����ļ�
										</c:when>
										<c:when test="${templateConfig.tempType eq '1'}">
											�����ļ�
										</c:when>
										<c:when test="${templateConfig.tempType eq '2'}">
											�����ļ�
										</c:when>
									</c:choose>
								</td>
								<td align="left" nowrap="nowrap">
									<c:choose>
										<c:when test="${templateConfig.ifDefault eq 'Y'}">
											��
										</c:when>
										<c:when test="${templateConfig.ifDefault eq 'N'}">
											��
										</c:when>
									</c:choose>
								</td>
								<td align="left" nowrap="nowrap">
									<c:choose>
										<c:when test="${templateConfig.fileType eq '0'}">
											TXT
										</c:when>
										<c:when test="${templateConfig.fileType eq '1'}">
											XML
										</c:when>
										<c:when test="${templateConfig.fileType eq '2'}">
											EXCEL
										</c:when>
									</c:choose>
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
				<input type="submit" value="ȷ��" id="selTemplateConfig"/>
				<input style="margin-left: 30px;" type="submit" value="���" id="clearTemplateConfig"/>
				<input style="margin-left: 30px;" type="button" value="�ر�" id="winClose"/>
			</td>
		</tr>
	</table>
	<br />
	<br />
	<br />
	</body>
</html>