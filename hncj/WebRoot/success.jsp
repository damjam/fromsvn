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
	    <div class="location">����ǰ����λ�ã� <span class="redlink"><a href="javascript: return;">��ҳ</a></span> > ϵͳ��ʾ > �����ɹ�</div>

		<!-- ���������ʾ�� -->
		<div class="userbox"><div>
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">		
		<table width="100%" border="0" cellspacing="5" cellpadding="0" align="center">
		  <tr>
		    <td width=""></td>
		    <td width="120" rowspan="3" align="center" valign="middle"><img src="images/succes.jpg" width="158" height="141" /></td>
		    <td width="180" height="30"><span style="font-size:14px; font-weight:bold; padding-bottom:10px;">�����ɹ���Ϣ</span></td>
		    <td width=""></td>
		  </tr>
		  <tr>
		  	<td colspan="2"></td>
		    <td height="60" colspan="2" valign="middle">${msg}</td>
		  </tr>
		  <tr>
		  	<td></td>
		    <td height="30" colspan="4">
		      <input type="submit"  onclick="forward()" value=" ȷ �� " class="inp_L3" onmouseover="this.className='inp_L4'" onmouseout="this.className='inp_L3'" id="input_btn2"  name="ok"/>
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
		
		<!--��Ȩ����-->
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>
