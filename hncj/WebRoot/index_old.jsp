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
			
			//登录
			function log(){
				if(isEmpty() || $('#password').val() == ''){
					$('#errorMsg').html('用户名或密码不能为空');
					return false;
				}
				if($('#verifyCode').val().trim().length != 4){
					$('#errorMsg').html('请输入4位验证码');
					return false;
				}
				var form = $('#logForm');	
				var DOMForm = form[0];
				
				//密码加密 form.password.value
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
			//var tip = '手机号/证件号码';
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
	    <!--登录口头部-->
	    <div class="top">
	        <div class="topbg">
	            <div style="float:left;"><img src="images/top_left.jpg" width="8" height="39" /></div>
	            <div class="toptext">
		            	${comInfo.sp}
	            </div>
	            <div style="float:right;"><img src="images/top_right.jpg" width="8" height="39" /></div>
	        </div>
	        <div class="topshadow"></div>
		</div>
		
		
	    <!--登录表单区域-->
	    <div class="middle">
	        <div class="middlebox">
		    	<div class="mainleft">
			        <div class="logo" style="padding-top: 30px;"><img title="${comInfo.sp}" src="images/0318.gif" /></div>
					
			        <div class="gonggao">
			        </div>
		        </div>
	        
		        <div class="mainright">
		        	<form action="login.do?action=login" method="post" id="logForm">
		        		<input type="hidden" name="tag" value="${tag}"/>
				        <div class="formbox">
				            <div class="formleft"><img title="用户登录" src="images/user.gif" /></div>
				            <div class="formright"><span style="font-size:14px;font-weight:bold; padding-left:5px;">用户登录</span>
				            <span class="redfont" id="errorMsg">${msg}</span></div>
				        </div>
				        <div class="formbox">
				            <div class="formleft">账号</div>
				            <div class="formright"><input class="forminput" id="code" name="code" type="text" value="${code}" maxlength="20"/></div>
				        </div>
				        
				        <div class="formbox">
				            <div class="formleft">密码</div>
				            <div class="formright"><input class="forminput" id="password" name="password" type="password" value=""/>
				            </div>
				        </div>
				        <div class="formbox">
				            <div class="formleft">验证码</div>
				            <div class="formright"><input class="forminput" id="verifyCode" name="verifyCode" type="text" style="width: 40px;" size="4" maxlength="4"/>
				            	<img src="${CONTEXT_PATH}/servlet/verifyCode" id="randomImg" align="middle" />
				            		<a href="javascript:void(0)" id="flushImg"><font color="#c94600">换一个</font></a>
				            		
				            </div>
				        </div>
				        <div class="formbox">
				            <div class="formleft"></div>
				            <div class="formright"><label><input onfocus="this.blur()" name="remenber" type="checkbox" value="1" checked="checked" />在此电脑上记住用户名</label>
				            	<a href="javascript:void(0)" onclick="javascript:toRegister()"><font style="text-decoration:underline"></font></a> 
				            	<a href="javascript:void(0)" onclick="javascript:findPwd()"><font style="text-decoration:underline"></font></a>
				            	</span></div>
				        		
				        </div>
				        <div class="formbox">
				            <div class="formleft"></div>
				            <div class="formbtbox">
				            <input type="image" src="images/enter.gif" title="点击登录系统" onclick="return log();"/>
				           </div>
				        </div>
						<!-- 
				        <div class="formbox">
							<div class="formleft"></div>
				        	<div class="formright" style="_padding-top:20px">
				        		<img src="images/down.gif" width="14" height="15" /><a style="text-decoration:underline" href="#">下载USB驱动</a>
				        		<img style="padding-left:30px" src="images/down.gif" width="14" height="15" /><a style="text-decoration:underline;" href="SignOcx.rar">下载网络安全插件</a>
				        	</div>
				        </div> -->
					</form>
		        </div>
			</div>
	    </div>
	    
	    <!--登录口底部-->
	    <jsp:include page="/footer.jsp"></jsp:include>
	</body>
</html>
