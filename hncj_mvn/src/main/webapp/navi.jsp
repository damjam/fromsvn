<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<title></title>
		<link rel="stylesheet" href="css/login.css" type="text/css" media="screen, projection" />
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
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
			var tip = '手机号/证件号码';
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
	            <div class="toptext"></div>
	            <div style="float:right;"><img src="images/top_right.jpg" width="8" height="39" /></div>
	        </div>
	        <div class="topshadow"></div>
		</div>
		
		
	    <!--登录表单区域-->
	    <div class="middle">
	        <div class="middlebox">
		    	<div class="mainleft">
			        
		        </div>
		        <div class="mainright" style="font-size:14px;font-weight:bold; padding-left:5px;">
		        	<form action="login.do?action=login" method="post" id="logForm">
		        		<input type="hidden" name="tag" value="${tag}"/>
		        		<div class="formbox">
				            <div class="formleft"></div>
				            <div class="formright" style="font-weight: normal">
				           		<f:msg/>
				           	</div>
				        </div>
				        <div class="formbox">
				            <div class="formleft"></div>
				            <div class="formright" style="font-weight: normal">
				           	 选择登录机构</div>
				        </div>
				        <div class="formbox">
				            <div class="formleft"></div>
				            <div class="formright">
				            	<a class="redlink" href="${CONTEXT_PATH}">${comInfo.sp}</a>
				            </div>
				        </div>
				        <!--  
				        <div class="formbox">
				            <div class="formleft"></div>
				            <div class="formright">
				            	<a class="redlink" href="${CONTEXT_PATH}/dg">东莞银行</a>
				            </div>
				        </div>
				        <div class="formbox">
				            <div class="formleft"></div>
				            <div class="formright">
				            	<a class="redlink" href="${CONTEXT_PATH}/gz">广州银行</a>
				            </div>
				        </div>
				        -->
						<!-- 
				        <div class="formbox">
							<div class="formleft"></div>
				        	<div class="formright" style="_padding-top:20px">
				        		<img src="images/down.gif" width="14" height="15" /><a style="text-decoration:underline" href="#">下载USB驱动</a>
				        		<img style="padding-left:30px" src="images/down.gif" width="14" height="15" /><a style="text-decoration:underline;" href="SignOcx.rar">下载银联网络安全插件</a>
				        	</div>
				        </div> -->
					</form>
		        </div>
			</div>
	    </div>
	    
	    <jsp:include flush="true" page="/footer.jsp"></jsp:include>
	</body>
</html>
