<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<title>新增提醒消息</title>
		
		<f:css href="/css/page.css"/>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>		
		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		<script type="text/javascript">
			
		 	function save(){
		 		
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	
		 	//变动机构
		 	function changeBranch(){
		 		var branchNo = '${BRANCH_TYPE.value}';
		 		//只有总部才进行机构选择
		 		if(branchNo != '0000'){
		 			return;
		 		}
		 		var params = 'branchNo='+branchNo;
		 		$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/pushMngAction.do?action=getBusiType',
				     async:true,
				     dataType: "json",
				     data:params,
					 success:function(data) {
				    	var jsonObj = data;
				    	$('#busiType').empty();
				    	$("#busiType").append("<option value=''>不限</option>");
				    	for(var i in jsonObj){
							$("#busiType").append("<option value='"+i+"'>"+jsonObj[i]+"</option>");
						}
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
		 	}
		 	
		 	$().ready(function(){
		 		changeBranch();
		 	});
			function inputTextNum()
		 	{
		 	   var content=$("#content").val();
		 	   var len=content.length;
// 		 	  for ( var i = 0; i < content.length; i++) {//汉字len要加1
// 					if (content.charCodeAt(i) > 127) {
// 						len++;
// 					}
// 				}
		 	  	$("#txtNumLen").html(len);
		 	}
		</script>
	</head>
<body>
<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
<f:msg styleClass="msg"/>
	<s:form action="noticeMsgAction.do?action=doAdd" id="noticeMsgForm" method="post" styleClass="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid" width="100%" border="0" cellspacing="3" cellpadding="0">
					  <caption>${ACT.name}</caption>
					     <tr>
						    <td class="formlabel nes">客户类型</td>
						    <td>
						    	<html:select property="custType"  id="custType">
									<html:option value="">不限</html:option>
									<html:options collection="custTypes" labelProperty="name" property="value"/>
								</html:select>
						    </td>
					   </tr>
					      <c:if test="${BRANCH_TYPE.value eq '0000'}">
					   <tr>
						    <td class="formlabel nes">机构类型</td>
						    <td>
						    	<html:select property="branchNo"  id="branchNo" onchange="changeBranch();">
									<html:option value="">不限</html:option>
									<html:options collection="branchTypes" labelProperty="value" property="key"/>
								</html:select>
						    </td>
					   </tr>
					  </c:if> 
					      <tr>
						    <td class="formlabel nes">业务类型</td>
						    <td>
						    	<html:select property="busiType"  id="busiType">
									<html:option value="">不限</html:option>
									<html:options collection="busiTypes" labelProperty="value" property="key"/>
								</html:select>
						    </td>
					   </tr>
					  
					  <tr>
						    <td class="formlabel nes">主题</td>
						    <td>
						    	<s:textfield name="subject"  id="subject" styleClass="{required:true}" maxlength="25"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">内容</td>
						    <td>
						    <div id="txtNum">当前输入的字数为：<span id="txtNumLen" style="color: red;">0</span></div>
						    	<html:textarea property="content"  id="content" onkeyup="inputTextNum();" onblur="inputTextNum();" styleClass="{required:true,maxlength:500}" rows="5" cols="50"/>
						    	<span class="field_tipinfo">不能为空,500字以内</span>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="提交" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('/noticeMsgAction.do?action=list')"/>
				</div>
				</div>
				<b class="b4"></b>
				<b class="b3"></b>
				<b class="b2"></b>
				<b class="b1"></b>	
			</div>
		</div>	
	</s:form>	
	<!--版权区域-->
	<div class="bottom">
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</div>
</body>
</html>
