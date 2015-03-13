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
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/jquery.js" />
		<f:js src="/js/sys.js" />
		<f:js src="/js/paginater.js" />
		<f:js src="/js/plugin/jquery.validate.js" />
		<f:js src="/js/plugin/jquery.metadata.js" />
		<f:js src="/js/common.js" />
		<f:css href="/css/page.css" />
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<base target="_self" />
		<script type="text/javascript">
			function busiTypeAdd()    
			{      
				var busiType ;
				busiType = $("#gs").val();
				busiType += "【业务类型】";
				$('#gs').val(busiType);
			}
			function merchantAbbAdd()    
			{      
				var merchantAbb ;
				merchantAbb = $("#gs").val();
				merchantAbb += "【商户简称】";
				$('#gs').val(merchantAbb);
			} 
			function ss()    
			{      
				var gs;
				gs = $("#gs").val();
				
				window.returnValue=gs;
				window.close();
			}
			function clearData(){
				document.getElementById("gs").value="";
			}
	 	</script>
		<title>渠道摘要公式</title>

	</head>

	<body>
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table width="100%">
						<tr>
							<td align="center">
								<span
									style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">渠道摘要公式</span>
							</td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
					</table>

					<table class="form_grid" width="100%" border="0" cellspacing="3"
						cellpadding="0">
						<tr>
							<td height="30" align="center" >
								<input type="button" name="merchantAbb" id="merchantAbb"
									value="商户简称" onclick="merchantAbbAdd();" />
								<input type="button" name="busiType" id="busiType"
									value="业务类型" onclick="busiTypeAdd();" />
							</td>
						</tr>
						
						<tr>
						<td width="100%" align="center">
							<input type="text" id="gs" name="gs" value=""  style="width:90%" maxlength="100"/>
						</td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr>
						<td align="center">例子1：【业务类型】保险【商户简称】QIFA</td>
						</tr>
						<tr>
						<td align="center">例子2：【商户简称】QIFA【业务类型】保险</td>
						</tr>
						<tr>
						<td align="center">例子3：【业务类型】保险</td>
						</tr>
						<tr></tr>
						<tr></tr>
						<tr></tr>
						<tr>
							<td height="30" colspan="3" align="center">
								<input type="submit" value="确定" onclick="ss();" id="selectBtn"/>
									<input onclick="clearData();" style="margin-left: 30px;"
										type="button" value="清除" />
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

	</body>
</html>