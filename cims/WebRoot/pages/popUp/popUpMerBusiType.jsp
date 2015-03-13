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
		<title>ѡ���̻�ҵ������</title>
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<f:css href="/css/page.css" />
		
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<f:js src="/js/popUp.js" />
		<f:js src="/js/fee/fee.js"/>
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				$('#selTranNo').val($('#hidTranNo').val());
				
				$('#selTranNo').change(function(){
					$('#merBusiTypeOper').attr("action",CONTEXT_PATH+"/popUpMerBusiOperAction.do?action=queryPopUpMerBusiTypeOper");
					$('#merBusiTypeOper').submit();
				});
				
				$('#winClose').click(function(){
					window.close();				
				});
				
				$('#saveMerBusiType').click(function(){
					
					var merchantNo=$('#merchantNo').val();
					if(merchantNo="" ||merchantNo.length==0){
						alert("��ѡ���̻�!");
						return false;
					}
					
	
					//�ж��Ƿ�ѡ��������
					var tranNo=$('#selTranNo').val();
					if(tranNo="" ||tranNo.length==0){
						alert("��ѡ��������!");
						return false;
					}
					
					var i=0;
					
					$(':checkbox').each(function(){
						if($(this).attr('checked')){
							i++;
						}
					});
					
					if(i==0){
						alert("��ѡ��ҵ������!")
						return false;
					}
				
					$('#merBusiTypeOper').attr("action",CONTEXT_PATH+"/popUpMerBusiOperAction.do?action=save");
					$('#merBusiTypeOper').submit();
				});
			});
			
			$(function(){
			
				//�޸ĵ�ʱ����
				var ssMerchantNo=$('#merchantNo').val();
				if(null!=ssMerchantNo && ssMerchantNo.length>0){
					$('#merchantName').val(ssMerchantNo);
					$('#merchantNo').val(ssMerchantNo)
				}
			});
			
    	</script>
	</head>

	<body>
		<form id="merBusiTypeOper"  action="${CONTEXT_PATH}/popUpMerBusiOperAction.do?action=queryPopUpMerBusiTypeOper" method="post"  class="validate"  >
			<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
			<div class="contentb">
			<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
				<tr>
					<td colspan="4" align="left">
						<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">�̻�ҵ������ѡ��</span>
						<br/>
					</td>
				</tr>
				<tr><td colspan="4" align="center"><f:msg /></td></tr>
				<tr >
					<td height="30" align="right" nowrap="nowrap" class="formlabel nes">
						�̻����
					</td>
					<td  height="30" class="form" >
						<input type="text" value="${param.merchantName}" id="merchantName" name="merchantName" class="{required:true}"/>
						<img align="left" src="<%=request.getContextPath()+"/images/search.jpeg" %>" alt="����" onclick="popUp.popUpMerchantInfo('merchantNo','merchantName')"/>
						<span class="field_tipinfo"></span>
						<span class="error_tipinfo">��ѡ���̻�</span>							
						<input type="hidden" value="${popUpMerBusiTypeOperForm.merchantNo}" id="merchantNo" name="merchantNo"/>	
						<input type="hidden" id="ssMerchantNo" name="ssMerchantNo" value="{param.merchantNo}"/>	
					</td>
					<td height="30" align="right" nowrap="nowrap" class="formlabel nes">
						��������
					</td>
					<td  height="30" colspan="3">
						<select name="tranNo" id="selTranNo"  class="{required:true}">
							<option value="">---��ѡ��---</option>
							<c:forEach items="${tranList}" var="tran">
								<option value="${tran.tranNo}">${tran.tranName}</option>
							</c:forEach>
						</select>
							<span class="field_tipinfo"></span>
						<span class="error_tipinfo">��ѡ��������</span>	
						<input type="hidden" value="${popUpMerBusiTypeOperForm.tranNo}" id="hidTranNo"/>	
					</td>
				</tr>
				<tr></tr>
				<tr></tr>
				<tr>
					<td height="30" align="right">
						&nbsp;
					</td>
					<td height="30" colspan="3">
						<input style="margin-left: 30px;" type="button"" value="����" id="saveMerBusiType"/>
						<input style="margin-left: 30px;" type="button" value="�ر�" id="winClose"/>
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
							ҵ����
						</th>
						<th align="center" nowrap="nowrap" class="titlebg">
							ҵ������
						</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty merchantBusiTypeOperList}">
						<tr>
							<td colspan="10">
								û������
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty merchantBusiTypeOperList}">
						<c:forEach items="${merchantBusiTypeOperList}" var="mer">
							<tr>
								<td align="center" nowrap="nowrap">
									&nbsp;<input name="busiType" type="checkbox"" value="${mer.busiNo}"/>
								</td>
							
								<td align="center" nowrap="nowrap">
									&nbsp;${mer.busiNo}
								</td>
								<td align="center" nowrap="nowrap">
									&nbsp;${mer.busiName}
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
			
		</tr>
	</table>
	<br />
	<br />
	<br />
	</body>
</html>