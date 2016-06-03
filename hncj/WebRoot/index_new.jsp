<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<title></title>
		<link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
		<f:css href="/macadmin/css/style.css"/>
		
		<style type="text/css">
		html, body{
			height: 100%;
		}
		body{
		padding-top: 37px;
		}
		.footer {
		    position: absolute;
		    bottom: 0;
		    width: 100%;
		    height: 100px;
		    
		    text-align: center;
		}
		.footshadow {
		  /*  background: url(images/bottom_shadow.jpg) repeat-x;*/
		    height: 56px;
		    width: 100%;
		}
		.foottext {
   /* background: url(images/bottom_bg.jpg) repeat-x;*/
    height: 44px;
    line-height: 44px;
    vertical-align: middle;
    width: 100%;
}
.textleft {
    float: left;
    padding-left: 15px;
}
.textright {
    float: right;
    padding-right: 15px;
}
.footer {
    position: absolute;
    bottom: 0;
    width: 100%;
    text-align: left;
    padding:0;
}	
		</style>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/usbkey.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/md5.js"/>
		<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<f:js src="/layer/layer.js"/>
		<script type="text/javascript">
			$(function() {
				if(navigator.userAgent.indexOf("Chrome") == -1){
					layer.confirm('您正在使用的浏览器可能无法正确显示系统内容，建议使用最新版chrome浏览器，是否现在下载安装？', {
					  title:'提示',
					  btn: ['立即下载','以后再说'] //按钮
					}, function(index){
					   window.open("http://softdl.360tpcdn.com/Chrome/Chromestable_49.0.2623.75.exe");
					   layer.close(index);
					   layer.confirm('接下来你要？',{
						   title:'提示',
						   btn:['关闭此页','继续访问']
					   },function(index){
						   top.close();
					   }),function(index){
						   
					   }
					}, function(){
					   
					}); 
				}
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
					//$('#errorMsg').html('请输入4位验证码');
					//return false;
				}
				var form = $('#logForm');	
				var DOMForm = form[0];
				
				//密码加密 form.password.value
				DOMForm.password.value = hex_md5(DOMForm.password.value);
				FormUtils.submitFirstTokenForm();
				
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

<body>
<header style="margin-top:-40px;padding:0 10px;line-height:40px;">
	${comInfo.sp}
</header>	
<div class="admin-form">
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <!-- Widget starts -->
            <div class="widget worange">
              <!-- Widget head -->
              <div class="widget-head">
                <i class="icon-lock"></i>登录
              </div>

              <div class="widget-content">
                <div class="padd">
                  <!-- Login form -->

		        	<form class="form-horizontal" action="login.do?action=login" method="post" id="logForm">
		        		<input type="hidden" name="tag" value="${tag}"/>
				        <div class="form-group">
	                      <label class="control-label col-lg-3" for=""></label>
	                      <div class="col-lg-9" id="errorMsg" style="color: red;">
	                        ${msg}
	                      </div>
	                    </div>
				        <div class="form-group">
	                      <label class="control-label col-lg-3" for="code">用户名</label>
	                      <div class="col-lg-9">
	                        <input type="text" class="form-control" style="width:80%" id="code" name="code" value="super" placeholder="用户名">
	                      </div>
	                    </div>
						<div class="form-group">
	                      <label class="control-label col-lg-3" for="password">密码</label>
	                      <div class="col-lg-9">
	                        <input type="password" class="form-control" style="width:80%" id="password" name="password" value="111111" placeholder="密码">
	                      </div>
	                    </div>
						<div class="form-group">
	                      <label class="control-label col-lg-3" for="verifyCode">验证码</label>
	                      <div class="col-lg-9">
	                      	<img src="${CONTEXT_PATH}/servlet/verifyCode" id="randomImg" align="middle" />
	                        <input type="text" class="form-control" id="verifyCode" name="verifyCode" placeholder="验证码" style="width: 80px;display: inline" size="8" maxlength="4" >
	                      </div>
	                    </div>
				        <div class="form-group">
							<div class="col-lg-9 col-lg-offset-2">
		                      <div class="checkbox">
		                        <label>
		                          <input onfocus="this.blur()" name="remenber" type="checkbox" value="1" checked="checked" />在此电脑上记住用户名
		                        </label>
								</div>
							</div>
						</div>
				       
				        <div class="col-lg-9 col-lg-offset-2">
							<button type="submit" class="btn btn-info btn-sm" title="点击登录系统" onclick="return log();">登录</button>
							<button type="reset" class="btn btn-default btn-sm">重置</button>
						</div>
                    	<br />
				        
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
			      <div class="widget-foot">
                  	 <a href="#">忘记密码?</a>
                  </div>
			    </div>
			  </div> 
			 </div>
			</div>
		</div>    
	    <!--登录口底部-->
	    <!-- 
	    <jsp:include page="/footer.jsp"></jsp:include> -->
	    <!-- 
	    <footer class="footer" style="height: 45px;">
		  <div class="textleft" style="padding-left: 10px;height:40px; line-height: 40px">
		  	${comInfo.tip}&nbsp;<a href="http://softdl.360tpcdn.com/Chrome/Chromestable_49.0.2623.75.exe" target="_blank" style="color: red;">点此下载</a>
		  </div>
		  <div class="textright" style="padding-right: 10px;height:40px; line-height: 40px">
		  	<a href="${comInfo.groupWeb}" title="访问官方网站" target="_blank">昌建地产集团</a>
			电话：${comInfo.tel}
		  </div>
		</footer> -->
	</body>
</html>
