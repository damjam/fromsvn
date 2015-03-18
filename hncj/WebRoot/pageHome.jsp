<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<link rel="stylesheet" href="css/page.css" type="text/css"
			media="screen, projection" />
		<f:css href="/css/page.css"/>
		<f:js src="/js/common.js" />
		<f:js src="/js/jquery.js" />
		
		<f:js src="/js/common.js"/>
		<script type="text/javascript">
			function seeUserMsgInfo(){
				gotoUrl("/msgInfo.do?action=queryUserMsgInfo");
			}
			$().ready(function(){
				var readOne = '${readOne}';
				parent.setMsgTip(readOne);
			});
			function hasRead(link, msgId){
				gotoUrl('/noticeMsgAction.do?action=hasRead&msgId='+msgId);
				/*
				link.innerHtml('�Ѷ�');
				$.ajax({
				 type:'POST',
			     url:CONTEXT_PATH + '/noticeMsgAction.do?action=hasRead',
			     async:false,
			     data:{msgId:msgId},
			     dataType: 'json',
				 success:function(data) {
			    	 if(data.result == true){
			    		 
			    	 }else{
			    		 //alert(data.msg);
			    	 }
				 },
				 error:function(data){   
                     alert("����ʧ��"+data.msg);
                 }       
			 });*/
			}
		</script>
		<style type="text/css">
			.myclass td{border:0px; height:25px; line-height:25px; vertical-align:middle; padding-left:3px;hite-space:nowrap;word-break:keep-all;}
		</style>
	</head>

	<body>
		<div class="location">
			����ǰ����λ�ã�<span class="redlink"><a href="javascript: void(0);">��ҳ</a>
		</div>
		
		<!-- ��½�ɹ���ʾ�� -->
		<div class="okbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<div style="height:30px; padding:15px 15px 25px 15px; overflow:hidden;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="52" align="center" valign="top">
								<img src="images/icon14.gif" width="37" height="41" />
							</td>
							<td>
								<p style="font-size:14px; font-weight:bold;">
									ϵͳ��¼��ʾ
								</p>
								<p style=" text-indent:2em; line-height:20px;">
									�𾴵��û������Ѿ��ɹ���¼${comInfo.sp}����ͨ����ർ���˵�������Ӧ���ܲ�����
									�������ʹ�ù����������ʣ��벦��
									<span class="redfont">����֧�ֵ绰��18520827190<strong></strong>
									</span>
								</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<c:if test="${msgCnt > 0}">
		<div class="okbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<div style="padding: 15px 15px 25px 15px; overflow: hidden;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="52" align="center" valign="top">
								<img src="images/icon14.gif" width="37" height="41" />
							</td>
							<td>
								<p style="font-size: 14px; font-weight: bold;">
									ϵͳ��Ϣ
								</p>
								<p style="text-indent: 2em; line-height: 20px;" align="center">
									
									<span class="redfont">&nbsp; </span>
								</p>
							</td>
						</tr>
					</table>
					<table class="data_grid myclass" width="100%" border="0" cellspacing="0"
						cellpadding="0" style="border-collapse:collapse; padding-left: 20px;">
						<f:showDataGrid name="list" msg=" " styleClass="data_grid">
							<logic:iterate id="element" name="list" indexId="ind">
								<tr align="center">
									<td width="150">
										${element.subject}
									</td>
									<td align="left" nowrap="nowrap" class="bluelinku">
										${element.content}
									</td>
									<td align="right" nowrap="nowrap" class="bluelinku">
										<a href="javascript:void(0)" onclick="hasRead(this, ${element.id})">���Ϊ�Ѷ�</a>
									</td>
								</tr>
							</logic:iterate>
						</f:showDataGrid>
					</table>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		</c:if>
		<!-- ��ɫ���̰����� -->
		<div class="processtitle">
			<div class="processtitleimg">
				<img src="images/icon15.gif" width="37" height="41" />
			</div>
			<div class="processtitletx">
				���ò������̣�
				<span style="color:#0459a7"></span>
			</div>
		</div>

		<div class="processbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">

				<div
					style="height:130px; padding:15px 15px 25px 15px; overflow:hidden;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td valign="middle">
								<strong>����һ</strong>��
								<br />
								<br />
								�������๦�ܲ����еġ�xxxx���˵���
							</td>
							<td>
								<img src="images/arrow.gif" width="51" height="124" />
							</td>
							<td valign="middle">
								<strong>�����</strong>��
								<br />
								<br />
								�ڴ򿪵Ĵ����������ѯ�������������ѯ����ť���в�ѯ��
							</td>
							<td>
								<img src="images/arrow.gif" width="51" height="124" />
							</td>
							<td valign="middle">
								<strong>������</strong>��
								<br />
								<br />
								�ڱ༭ҳ������Ҫ����/�޸ĵ����ݣ���������桱��ť��
							</td>
							<td>
								<img src="images/arrow.gif" width="51" height="124" />
							</td>
							<td valign="middle">
								<strong>������</strong>��
								<br />
								<br />
								�ڵ�������ʾ�����е�������ء���ť��ɲ�����
							</td>
						</tr>
					</table>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

<!-- 
		<div class="processbz">
			ע������ۿ����Ӿ���Ĳ������̣���
			<span class="redlink"><a href="javascript: return;">����˴�</a>
			</span>����Flash���������̳̣�
		</div>
 -->
		<!--��Ȩ����-->
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>
