<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<title></title>
		<link rel="stylesheet" href="css/login.css" type="text/css" media="screen, projection" />
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/usbkey.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		<f:css href="/css/page.css"/>
		<script type="text/javascript">
			$(function() {
				$('.forminput,.formbt').each(function() {
					$(this)
						.focus(function() {$(this).addClass('sffocus');})
						.blur(function() {$(this).removeClass('sffocus');});
				});
			});
			
			//��½
			function log(){
				if(isEmpty() || $('#password').val() == ''){
					$('#errorMsg').html('�û��������벻��Ϊ��');
					return false;
				}
				if($('#verifyCode').val().trim().length != 4){
					$('#errorMsg').html('������4λ��֤��');
					return false;
				}
				var form = $('#logForm');	
				var DOMForm = form[0];
				
				//������� form.password.value
				DOMForm.password.value = hex_md5(DOMForm.password.value);
				DOMForm.submit();			
				return false;
			}
			
			function toRegister(){
				gotoUrl('/custInfoAction.do?action=toRegister');
			}
			$(function(){
				$("#flushImg").click(function(){
					$("#randomImg").attr("src",$("#randomImg").attr("src")+"?time="+new Date());
				});
				$('#randomImg').click(function(){
					$('#randomImg').attr("src",$("#randomImg").attr("src")+"?time="+new Date());
				});
				$('#randomImg').mouseover(function(){
					this.style.cursor='hand';
				});
			});
			function findPwd(){
				gotoUrl('/custInfoAction.do?action=toResetPwd');
			}
			$().ready(function(){
				bindEvent();
				var code = $('#code').val();
				if(code == tip){
					$('#code').css('color', 'gray');
				}
			});
			function isEmpty(){
				var code = $('#code').val();
				if(code == tip || code == ''){
					$('#code').css('color', 'gray');
					return true;
				}
				return false;
			}
			//var tip = '�ֻ���/֤������';
			var tip = '';
			function bindEvent(){
				$('#code').blur(function(){
					var code = $('#code').val();
					if(code == ''){
						$('#code').val(tip);
						$('#code').css('color', 'gray');
					}
				});
				$('#code').focus(function(){
					var code = $('#code').val();
					if(code == tip){
						$('#code').val('');
						$('#code').css('color', 'black');
					}
				});
			}
		</script>
		<%
			com.ylink.cim.admin.view.CookieDealer.dealAllCookieInCurrentReq(new String[]{"code", "fromBranch"}, new String[]{"code", "fromBranch"}, request);
		%>
	</head>

	<body class="fullwidth">
	    <!--��½��ͷ��-->
	    <div class="top">
	        <div class="topbg">
	            <div style="float:left;"><img src="images/top_left.jpg" width="8" height="39" /></div>
	            <div class="toptext">
	            	<!-- 
	            	 <c:choose>
		            	<c:when test="${tag eq 'gz'}">�������лƽ�Ͷ����ϵͳ</c:when>
		            	<c:when test="${tag eq 'dg'}">��ݸ���лƽ�Ͷ����ϵͳ</c:when>
		            	<c:otherwise> ���ڽ��ڵ��ӽ������� �ͻ���Ϣ����ϵͳ</c:otherwise>
		            </c:choose> -->
		            	�����������ҵ����ϵͳ
	            </div>
	            <div style="float:right;"><img src="images/top_right.jpg" width="8" height="39" /></div>
	        </div>
	        <div class="topshadow"></div>
		</div>
		
		
	    <!--��½������-->
	    <div class="middle">
	        <div class="middlebox">
		    	<div class="mainleft">
			        <div class="logo" style="padding-top: 30px;"><img title="�����������ҵ����ϵͳ" src="images/logo64.jpg" /></div>
					
			        <div class="gonggao">
			        </div>
		        </div>
	        
		        <div class="mainright">
		        	<form action="login.do?action=login" method="post" id="logForm">
		        		<input type="hidden" name="tag" value="${tag}"/>
				        <div class="formbox">
				            <div class="formleft"><img title="�û���½" src="images/user.gif" /></div>
				            <div class="formright"><span style="font-size:14px;font-weight:bold; padding-left:5px;">�û���½</span>
				            <span class="redfont" id="errorMsg">${msg}</span></div>
				        </div>
				        <div class="formbox">
				            <div class="formleft">�˺�</div>
				            <div class="formright"><input class="forminput" id="code" name="code" type="text" value="${code}" maxlength="20"/></div>
				        </div>
				        
				        <div class="formbox">
				            <div class="formleft">����</div>
				            <div class="formright"><input class="forminput" id="password" name="password" type="password" value=""/>
				            </div>
				        </div>
				        <div class="formbox">
				            <div class="formleft">��֤��</div>
				            <div class="formright"><input class="forminput" id="verifyCode" name="verifyCode" type="text" style="width: 40px;" size="4" maxlength="4"/>
				            	<img src="${CONTEXT_PATH}/servlet/verifyCode" id="randomImg" align="middle" />
				            		<a href="javascript:void(0)" id="flushImg"><font color="#c94600">��һ��</font></a>
				            		
				            </div>
				        </div>
				        <div class="formbox">
				            <div class="formleft"></div>
				            <div class="formright"><label><input onfocus="this.blur()" name="remenber" type="checkbox" value="1" checked="checked" />�ڴ˵����ϼ�ס�û���</label>
				            	<a href="javascript:void(0)" onclick="javascript:toRegister()"><font style="text-decoration:underline"></font></a> 
				            	<a href="javascript:void(0)" onclick="javascript:findPwd()"><font style="text-decoration:underline"></font></a>
				            	</span></div>
				        		
				        </div>
				        <div class="formbox">
				            <div class="formleft"></div>
				            <div class="formbtbox">
				            <input type="image" src="images/enter.gif" title="�����¼ϵͳ" onclick="return log();"/>
				           </div>
				        </div>
						<!-- 
				        <div class="formbox">
							<div class="formleft"></div>
				        	<div class="formright" style="_padding-top:20px">
				        		<img src="images/down.gif" width="14" height="15" /><a style="text-decoration:underline" href="#">����USB����</a>
				        		<img style="padding-left:30px" src="images/down.gif" width="14" height="15" /><a style="text-decoration:underline;" href="SignOcx.rar">�������簲ȫ���</a>
				        	</div>
				        </div> -->
					</form>
		        </div>
			</div>
	    </div>
	    
	    <!--��½�ڵײ�-->
	    <div class="footer">
	        <div class="footshadow"></div>
	        <div class="foottext">
	            <div class="textleft">����ʹ��IE7.0�汾�������������FireFox�����+1024�ֱ��ʻ����Ѳ�������</div>
	            <div class="textright">��Ȩ����&copy;<a href="http://www.szgold.com.cn/" title="���ʹٷ���վ" target="_blank"></a> ȫ���ͷ����ߣ�400-189-6699</div>
	        </div>
	    </div>
	</body>
</html>
