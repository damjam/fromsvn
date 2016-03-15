<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>新增字典</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/popUp.js"/>
		<script type="text/javascript">
			$(function(){
				
				$('#btnReturn').click(function(){
					gotoUrl("/branchDict.do?action=list");
				});
				
			});
		 
		</script>
	</head>
<body>

<f:msg />
	<form action="${uri}?action=doAdd" method="post" class="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
					  <caption>${ACT.name}</caption>
					  <c:if test="${sessionScope.isHQ == true}">
						   <tr>
							     <td class="formlabel nes">机构</td>
							     <td>
									<s:select name="branchNo" list="#request.branches" listKey="key" listValue="value"/>
									<span class="field_tipinfo">不能为空</span>
								</td>
						   </tr>
					   </c:if>
					   <tr>
						    <td class="formlabel nes">名称</td>
						    <td>
						    	<s:textfield name="dictName" id="dictName"  class="{required:true}" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">值</td>
						    <td>
						    	<s:textfield name="id.dictValue"  id="dictId" class="{required:true,stringNum:true}" maxlength="20"/>
						    	<span class="field_tipinfo">字母或数字</span>
						    </td>
					   </tr>
					   <tr>
						     <td class="formlabel nes">类型</td>
						     <td>
								<s:select name="id.dictType" list="#request.dictTypes" listKey="value" listValue="name"/>
								<span class="field_tipinfo">不能为空</span>
							</td>
					   </tr>
					   <tr>
						     <td class="formlabel nes">显示</td>
						     <td>
								<s:select name="display" list="#{'Y':'是','N':'否'}" listKey="key" listValue="value"/>
								<span class="field_tipinfo">不能为空</span>
							</td>
					   </tr>
					    <tr>
						     <td align="right">备注</td>
						     <td>
								<s:textfield name="remark" maxlength="100"></s:textfield>
								<span class="field_tipinfo"></span>
							</td>
					   </tr>
					     <tr>
						     
						     <td colspan="2" align="center">
								 <input type="submit" id="btnSumit" value="提交"/>&nbsp;
								 <input type="button" id="btnReturn" value="返回"/>
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
	</form>	
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
