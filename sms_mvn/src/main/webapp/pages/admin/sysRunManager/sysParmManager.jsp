<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>

<%@ include file="/pages/common/taglibs.jsp" %>

<html lang="zh-cn">
<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		
		<link rel="stylesheet" href="css/page.css" type="text/css" media="screen, projection" />
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/paginater.js"/>
		<f:js src="/js/common.js" />
		
		<script type="text/javascript">
			function clearData(){
				FormUtils.reset("query");
			}
		
		   function addSysParm(){
			   gotoUrl('/sysParmManage.do?action=toAdd');
		   	   //window.location.href="pages/admin/sysRunManager/sysParmAdd.jsp";
		   }
		   
		   function check(){
		   		var code=document.getElementById("code").value;
				var parname=document.getElementById("parname").value;
				
				if(code.length==0 && parname.length==0){
					alert("请输入至少一个查询条件");
					return false;
				}
				
				return true;
		   }
		   
		   // 重新加载系统参数.
			function reloadSysParm() {
				$.post(CONTEXT_PATH + '/sysParmManage.do?action=reload', function(data) {
					if (data.indexOf('重新加载系统参数') > -1) {
						alert(data);
					}
					else {
						alert('加载失败');
					}
				});
			}
		   function backup1(){
			   if(!window.confirm('确认备份?')){
				   return;
			   }
			   $(':button').attr('disabled', true);
			   $.post(CONTEXT_PATH + '/sysParmManage.do?action=backupData', function(data) {
				   /* 
				   if(data != null){
				    	var jsonObj = eval('(' + data + ')');
					    if (jsonObj.indexOf('') > -1) {
							alert('备份成功');
						}else {
							alert('备份失败');
						}
				    } else {
						alert('备份失败');
					}*/
				});
			   $(':button').attr('disabled', false);
		   }
		   function query1(queryForm){
			   queryForm.submit();
		   }
		</script>
</head>

<body>
	 
	
	<f:msg/>
<!-- 查询功能区 -->
	<form id="query" action="sysParmManage.do?action=query" method="post" >
		<div class="userbox">
		<b class="b1"></b>
		<b class="b2"></b>
		<b class="b3"></b>
		<b class="b4"></b>
		<div class="contentb">
		<table class="form_grid">
		  <caption>${ACT.name}</caption>	
		  <tr>
		    <td class="formlabel">参数代码</td>
		    <td><s:textfield name="code" id="code"  /></td>
		    <td class="formlabel">参数名称</td>
		    <td><s:textfield name="parname" id="parname" /></td>
		    <td class="formlabel" align="left">&nbsp;</td>
		    <td></td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td colspan="5">
		      <input type="button" value="查询" id="input_btn2"   onclick="query1(this.form);"/>&nbsp;&nbsp;
		      <input onclick="clearData();"   type="button" value="重置"  />&nbsp;&nbsp;
		   	  <input onclick="addSysParm();"  type="button" value="新增"   />&nbsp;&nbsp;
			  <input onclick="reloadSysParm();"  type="button" value="重新加载"   />&nbsp;&nbsp;
		    </td>
		  </tr>
		  </table>
		</div>
		<b class="b4"></b>
		<b class="b3"></b>
		<b class="b2"></b>
		<b class="b1"></b>	
		</div>
	
		<!-- 数据列表区 -->
		<div class="tablebox">
			<table class='data_grid'>
				<thead>
				  <tr>
				    <th align="center" nowrap="nowrap" class="titlebg">参数代码</th>
				    <th align="center" nowrap="nowrap" class="titlebg">参数名称</th>
				    <th align="center" nowrap="nowrap" class="titlebg">参数值</th>
				    <th align="center" nowrap="nowrap" class="titlebg">备注</th>
				    <th align="center" nowrap="nowrap" class="titlebg">操作</th>
				  </tr>
				 </thead>
				 <f:showDataGrid name="list" msg=" " styleClass="data_grid">
				  	  <c:forEach items="${list}" var="sysPram">
					  <tr align="center">
					    <td nowrap="nowrap">${sysPram.code}</td>
					    <td nowrap="nowrap">${sysPram.parname}</td>
					    <td nowrap="nowrap">${sysPram.parvalue}</td>
						<td nowrap="nowrap">${sysPram.remark}</td>
					    <td nowrap="nowrap">
						    <span class="redlink">
						   		 <a href="sysParmManage.do?action=update&code=${sysPram.code}">修改</a>
						   		 
						   		 <a onclick="return confirm('你确认要删除吗?');" href="sysParmManage.do?action=delete&code=${sysPram.code }" >删除</a>
						    </span>
					    </td>
					  </tr>
					 </c:forEach>
				</f:showDataGrid>
			</table>
			<f:paginate />	
		</div>
	</form>
	
	<!--版权区域-->
 	<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
</body>
</html>