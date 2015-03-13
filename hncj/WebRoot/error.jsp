<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f" %>
    
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<title></title>
		<link rel="stylesheet" href="css/page.css" type="text/css" media="screen, projection"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/common.js"/>
		<script type="text/javascript"><!--//--><![CDATA[//><!--//--><!]]>
			function forward()
			{
				var url = '${url}';
				if(url != ''){
					gotoUrl(url);
				} else {
					toHomePage();
				}
				//window.history.back();	
			}
		</script>
	</head>

	<body class="fullwidth">
	    <div class="location">您当前所在位置： <span class="redlink"><a href="javascript: return;">首页</a></span> > 系统提示 > 操作失败</div>

		<!-- 操作结果提示区 -->
		<div class="userbox"><div>
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">		
		<table width="100%" border="0" cellspacing="5" cellpadding="0">
		  <tr>
		    <td width=""></td>
		    <td width="120" rowspan="3" align="center" valign="middle"><img src="images/error.gif" width="158" height="141" /></td>
		    <td width="180" height="30"><span style="font-size:14px; font-weight:bold; padding-bottom:10px;">操作失败信息</span></td>
		    <td width=""></td>
		  </tr>
		  <tr>
		  	<td colspan="2"></td>
		    <td height="60" colspan="2" valign="middle">${msg}</td>
		  </tr>
		  <tr>
		  	<td></td>
		    <td height="30" colspan="4">
		    	<input type="submit" value=" 返 回 " class="inp_L3" onclick="forward();" onmouseover="this.className='inp_L4'" onmouseout="this.className='inp_L3'" id="input_btn3"  name="escape"/>    </td>
		    </tr>
		  </table>   
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		</div>
		
		</div>
		
		<!--版权区域-->
		<div class="bottom">
		    <div style="float:left; padding-left:5px; color:#767676;">建议使用IE7.0版本以上浏览器或者FireFox浏览器+1024分辨率获得最佳操作体验</div><div style="float:right; padding-right:5px;color:#767676;">版权所有&copy;<a href="http://www.szgold.com.cn/" title="访问银联网络官方网站" target="_blank" style="color:#767676;">广州银联网络支付有限公司</a> 全国客服热线：4008-333-222</div>
		</div>
	</body>
</html>
