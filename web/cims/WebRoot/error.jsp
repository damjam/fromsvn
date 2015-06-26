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
	    <div class="location">����ǰ����λ�ã� <span class="redlink"><a href="javascript: return;">��ҳ</a></span> > ϵͳ��ʾ > ����ʧ��</div>

		<!-- ���������ʾ�� -->
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
		    <td width="180" height="30"><span style="font-size:14px; font-weight:bold; padding-bottom:10px;">����ʧ����Ϣ</span></td>
		    <td width=""></td>
		  </tr>
		  <tr>
		  	<td colspan="2"></td>
		    <td height="60" colspan="2" valign="middle">${msg}</td>
		  </tr>
		  <tr>
		  	<td></td>
		    <td height="30" colspan="4">
		    	<input type="submit" value=" �� �� " class="inp_L3" onclick="forward();" onmouseover="this.className='inp_L4'" onmouseout="this.className='inp_L3'" id="input_btn3"  name="escape"/>    </td>
		    </tr>
		  </table>   
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		</div>
		
		</div>
		
		<!--��Ȩ����-->
		<div class="bottom">
		    <div style="float:left; padding-left:5px; color:#767676;">����ʹ��IE7.0�汾�������������FireFox�����+1024�ֱ��ʻ����Ѳ�������</div><div style="float:right; padding-right:5px;color:#767676;">��Ȩ����&copy;<a href="http://www.szgold.com.cn/" title="������������ٷ���վ" target="_blank" style="color:#767676;">������������֧�����޹�˾</a> ȫ���ͷ����ߣ�4008-333-222</div>
		</div>
	</body>
</html>
