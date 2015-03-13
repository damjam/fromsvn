<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base  target="_self"/> 
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<link rel="stylesheet" href="css/page.css" type="text/css"
			media="screen, projection" />


		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />
		<f:js src="/js/jquery.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/common.js" />
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>

		<script type="text/javascript">
		$(function(){
			$('#selChnlConfig').click(function(){
				var i=0;
				var selValue="";
				$(':radio').each(function(){
					if($(this).attr('checked')){
						selValue=$(this).val();
						i++;
					}
					
				});
				
				if(i==0 || i>1){
					alert("��ѡ����Ҫ���������");
					return false;
				}
				
				window.returnValue=selValue;
				window.close();
				
				
			});
			
			$('#clearBankAddress').click(function(){
				var selValue="";
				window.returnValue="$";
				window.close();
			});
			
			$('#winClose').click(function(){
				window.close();
			});
				
		});
			
		</script>
	</head>

	<body>
		<f:msg styleClass="msg" />
		<!-- ��ѯ������ -->
		<html:form action="channelConfigManage.do?action=queryPopUpChannelConfig" method="post" >
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
									<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">������������ѡ��</span>
								</td>
							</tr>
							<tr>
								<td width="100" height="30" align="right">
									�������
								</td>
								<td width="270" height="30">
									<html:text styleClass="" size="20" property="chnlNo"
										styleId="chnlNo" />
								</td>
								<td width="100" height="30" align="right">
									��������
								</td>
								<td height="30">
									<html:text styleClass="" size="20" property="chnlName" styleId="chnlName" />
									<html:hidden property="chnlPayType" ></html:hidden>
									<html:hidden property="state" value="00"></html:hidden>
									
								</td>
							</tr>
							<tr></tr>
							<tr></tr>
							<tr>
								<td width="100" height="30" align="right">
									&nbsp;
								</td>
								<td height="30" colspan="3">
									<input type="submit" value="��ѯ" id="input_btn2" />
									<input onclick="FormUtils.reset('channelConfigManageActionForm');" style="margin-left: 30px;" type="button" value="���" />
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
				<table class='data_grid' width="100%" border="0" cellspacing="0"
					cellpadding="0">
					<thead>
						<tr>
							
							<th align="center" nowrap="nowrap" class="titlebg">
								��ѡ��
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								�������
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								��������
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								�������ո�����
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								�Ƿ���Ҫ��֤Э��
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								״̬
							</th>
							<th align="center" nowrap="nowrap" class="titlebg">
								����
							</th>
						</tr>
					</thead>
					<tbody>
						<logic:empty name="channelConfigList">
							<tr>
								<td nowrap="nowrap" colspan="6">
									û������
								</td>
							</tr>
						</logic:empty>
						<logic:notEmpty name="channelConfigList">
							<logic:iterate name="channelConfigList" id="channelConfig">
								<tr class="shortcut">
									<td align="center" nowrap="nowrap">
										<input name="addrName" type="radio" value="${channelConfig.chnlNo}$${channelConfig.chnlName}"/>
									</td>
									<td align="center" nowrap="nowrap">
										<bean:write name="channelConfig" property="chnlNo" />
									</td>
									<td align="center" nowrap="nowrap">
										<bean:write name="channelConfig" property="chnlName" />
									</td>
									<td align="center" nowrap="nowrap">
										<c:choose>
											<c:when test="${channelConfig.chnlPayType eq '0'}">����/����</c:when>
											<c:when test="${channelConfig.chnlPayType eq '1'}">����</c:when>
											<c:when test="${channelConfig.chnlPayType eq '2'}">����</c:when>
											<c:otherwise>����</c:otherwise>
										</c:choose>
									</td>
									<td align="center" nowrap="nowrap">
										<c:choose>
											<c:when test="${channelConfig.ifProt eq '0'}">��</c:when>
											<c:when test="${channelConfig.ifProt eq '1'}">��</c:when>
											<c:otherwise>����</c:otherwise>
										</c:choose>
									</td>

									<td align="center" nowrap="nowrap">
										<c:choose>
											<c:when test="${channelConfig.state eq '00'}">��Ч</c:when>
											<c:when test="${channelConfig.state eq '01'}">ע��</c:when>
											<c:otherwise>����</c:otherwise>
										</c:choose>
									</td>
									<td align="center" nowrap="nowrap">
										<span class="redlink"> 
											<html:link
												href="javascript:openContextDialog('/channelConfigManage.do?action=channelConfigDetail&chnlNo=${channelConfig.chnlNo}',false)">��ϸ</html:link>
										</span>
									</td>
								</tr>
							</logic:iterate>
						</logic:notEmpty>
					</tbody>
				</table>

				<!-- ��ҳ -->
				<div class="table_navi">
					<f:paginate />
				</div>

			</div>
		</html:form>
		<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td height="30" colspan="4" align="center">
					<input type="submit" value="ȷ��" id="selChnlConfig"/>
					<input style="margin-left: 30px;" type="submit" value="���" id="clearBankAddress"/>
					<input style="margin-left: 30px;" type="button" value="�ر�" id="winClose"/>
				</td>
			</tr>
		</table>
	</body>
</html>