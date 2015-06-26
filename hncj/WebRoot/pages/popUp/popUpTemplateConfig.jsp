<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<base  target="_self"/>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>

		<f:js src="/js/paginater.js" />
		<f:css href="/css/page.css" />
		<f:css href="/css/base.css"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<style type="text/css">
			body{
				width: 96%;
				margin-left: 10px;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				$('#selTemplateConfig').click(function(){
					var i=0;
					var selValue="";
					$(':radio').each(function(){
						if($(this).attr('checked')){
							selValue=$(this).val();
							i++;
						}
						
					});
					
					if(i==0 || i>1){
						alert("请选择需要的角色，有且仅有一条");
						return false;
					}
					
					window.returnValue=selValue;
					window.close();
					
					
					});
				
					$('#clearTemplateConfig').click(function(){
						var selValue="";
						window.returnValue="$";
						window.close();
					});
					
					$('#winClose').click(function(){
						window.close();
					});
					
					$('#btnClear').click(function(){
						$("form :text").val('');
					});
				
				$('#busiType').val($('#hidBusiType').val());
				$('#tempType').val($('#hidTempType').val());
			});
	    </script> 
	  <title>选择文件模板</title>
	</head>
	<body>
	<!-- 查询功能区 -->
	<form id="query" action="templateConfig.do?action=queryPopUpTemplateConfig"
		method="post">
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
							<td colspan="4" align="left"  nowrap="nowrap">
								<span style="font-size: 14px; font-weight: bold; padding-bottom: 10px;">文件模板配置查询</span>
							</td>
						</tr>
						<tr>
							<td height="30" align="right"  nowrap="nowrap">
								模板编号
							</td>
							<td height="30"  nowrap="nowrap">
								<input type="text" class="userbox_bt" style="width: 100px;" name="tempId" id="tempId"  maxlength="6" value ="${templateConfig.tempId}"/>
							</td>
							<td height="30" align="right"  nowrap="nowrap">
								模板名称
							</td>
							<td height="30"  nowrap="nowrap">
								<input type="text"  name="tempName" id="tempName"  maxlength="6" value ="${templateConfig.tempName}"/>
							</td>
						</tr>
						<tr>
							<td height="30" align="right"  nowrap="nowrap">
								机构名称
							</td>
							<td height="30"  nowrap="nowrap">
								<input type="text" style="width: 100px;" name="branchName" id="branchName"  maxlength="6" value ="${templateConfig.branchName}"/>
							</td>
							<td height="30" align="right"  nowrap="nowrap">
								商户名称
							</td>
							<td height="30"  nowrap="nowrap">
								<input type="text"  name="merchantName" id="merchantName"  maxlength="6" value ="${templateConfig.merchantName}"/>
							</td>
						</tr>
						<tr>
							<td height="30" align="right">
								业务类型
							</td>
							<td height="30"  nowrap="nowrap">
								<select  id="busiType" name="busiType" >
									<option value='' >---请选择---</option>
									<option value='0' >代付</option>
									<option value='1' >代收</option>
								</select>
								<input id="hidBusiType" value="${templateConfig.busiType}" type="hidden"/>
							</td>
							<td height="30" align="right"  nowrap="nowrap">
								模板类型
							</td>
							<td height="30"  nowrap="nowrap">
							<select   id="tempType" name="tempType" >
								<option value='' >---请选择---</option>
								<option value='0' >交易文件</option>
								<option value='1' >清算文件</option>
								<option value='2' >回盘文件</option>
							</select>
							<input id="hidTempType" value="${templateConfig.tempType}" type="hidden" />
						  </td>
						</tr>
						<tr></tr>
						<tr>
							<td height="30" align="right">
								&nbsp;
							</td>
							<td height="30" colspan="3">
								<input type="submit" value="查询" />
								<input id="btnClear" style="margin-left: 30px;" type="button" value="清除" />
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

		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class='data_grid' width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th align="left" nowrap="nowrap" class="titlebg">
							请选择
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							模板编号
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							模板名称
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							所属机构
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							机构名称
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							所属商户
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							商户名称
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							业务类型
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							模板类型
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							是否默认
						</th>
						<th align="left" nowrap="nowrap" class="titlebg">
							文件类型
						</th>
					</tr>
				</thead>
				
				
				<tbody>
					<c:if test="${empty templateConfigList}">
						<tr>
							<td colspan="10">
								没有数据
							</td>
						</tr>
					</c:if>
					<c:if test="${not empty templateConfigList}">
						<c:forEach items="${templateConfigList}" var="templateConfig">
							<tr class="shortcut">
								<td align="center" nowrap="nowrap">
									<input name="templateConfig" type="radio"" value="${templateConfig.tempId}$${templateConfig.tempName}"/>
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.tempId}
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.tempName}
								</td>
								
								<td align="left" nowrap="nowrap">
									${templateConfig.branchNo}
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.branchName}
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.merchantNo}
								</td>
								<td align="left" nowrap="nowrap">
									${templateConfig.merchantName}
								</td>
								<td align="left" nowrap="nowrap">
									<c:choose>
										<c:when test="${templateConfig.busiType eq '0'}">
											代付
										</c:when>
										<c:when test="${templateConfig.busiType eq '1'}">
											代收
										</c:when>
									</c:choose>
								</td>
								<td align="left" nowrap="nowrap">
									<c:choose>
										<c:when test="${templateConfig.tempType eq '0'}">
											交易文件
										</c:when>
										<c:when test="${templateConfig.tempType eq '1'}">
											清算文件
										</c:when>
										<c:when test="${templateConfig.tempType eq '2'}">
											回盘文件
										</c:when>
									</c:choose>
								</td>
								<td align="left" nowrap="nowrap">
									<c:choose>
										<c:when test="${templateConfig.ifDefault eq 'Y'}">
											是
										</c:when>
										<c:when test="${templateConfig.ifDefault eq 'N'}">
											否
										</c:when>
									</c:choose>
								</td>
								<td align="left" nowrap="nowrap">
									<c:choose>
										<c:when test="${templateConfig.fileType eq '0'}">
											TXT
										</c:when>
										<c:when test="${templateConfig.fileType eq '1'}">
											XML
										</c:when>
										<c:when test="${templateConfig.fileType eq '2'}">
											EXCEL
										</c:when>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
				</c:if>
				</tbody>
			</table>
			<f:paginate />
		</div>
	</form>
	<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr></tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td height="30" colspan="4" align="center">
				<input type="submit" value="确定" id="selTemplateConfig"/>
				<input style="margin-left: 30px;" type="submit" value="清除" id="clearTemplateConfig"/>
				<input style="margin-left: 30px;" type="button" value="关闭" id="winClose"/>
			</td>
		</tr>
	</table>
	<br />
	<br />
	<br />
	</body>
</html>