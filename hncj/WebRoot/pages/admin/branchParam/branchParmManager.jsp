<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
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

		<style type="text/css">
			html { overflow-y: scroll; }
		</style>
		
		<script type="text/javascript">
			function clearData(){
				FormUtils.reset("query");
			}
		
		   function addbranchParm(){
			   gotoUrl('/branchParmManage.do?action=toAdd');
		   		//window.location.href="pages/admin/branchRunManager/branchParmAdd.jsp";
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
			function reloadbranchParm() {
				$.post(CONTEXT_PATH + '/branchParmManage.do?action=reload', function(data) {
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
			   $.post(CONTEXT_PATH + '/branchParmManage.do?action=backupData', function(data) {
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
		</script>
</head>

<body>
	 
	<jsp:include flush="true" page="/pages/layout/location.jsp"></jsp:include>
	<f:msg/>
<!-- 查询功能区 -->
	<form id="query" action="branchParmManage.do?action=query" method="post" >
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
		    <td class="formlabel">参数代码</td>
		    <td><input type="text" name="code" id="code"  /></td>
		    <td class="formlabel">参数名称</td>
		    <td><input name="parname" type="text" id="parname" /></td>
		    <td class="formlabel" align="left">&nbsp;</td>
		    <td></td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		    <td colspan="5">
		      <input type="submit" value="查询" id="input_btn2"   />&nbsp;&nbsp;
		      <input onclick="clearData();"   type="button" value="重置"  />&nbsp;&nbsp;
		   	  <input onclick="addbranchParm();"  type="button" value="新增"   />&nbsp;&nbsp;
			  <input onclick="reloadbranchParm();"  type="button" value="重新加载"   />&nbsp;&nbsp;
			  <!-- 
			  <input onclick="backup1();"  type="button" value="备份数据库"   /> -->
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
				    <th align="center" nowrap="nowrap" class="titlebg">参数代码</th>
				    <th align="center" nowrap="nowrap" class="titlebg">参数名称</th>
				    <th align="center" nowrap="nowrap" class="titlebg">参数值</th>
				    <th align="center" nowrap="nowrap" class="titlebg">备注</th>
				    <th align="center" nowrap="nowrap" class="titlebg">操作</th>
				  </tr>
				 </thead>
				 <tbody>
				  <logic:notEmpty name="branchParmList">
				  	<logic:iterate name="branchParmList" id="branchPram">
					  <tr align="center">
					    <td nowrap="nowrap"><bean:write name="branchPram" property="code" /></td>
					    <td nowrap="nowrap"><bean:write name="branchPram" property="parname" /></td>
					    <td nowrap="nowrap"><bean:write name="branchPram" property="parvalue" /></td>
						<td nowrap="nowrap"><bean:write name="branchPram" property="remark" /></td>
					    <td nowrap="nowrap">
						    <span class="redlink">
						   		 <a href="branchParmManage.do?action=update&code=${branchPram.code}">修改</a>
						   		 <!-- 
						   		 <a onclick="return confirm('你确认要删除吗?');" href="branchParmManage.do?action=delete&code=${branchPram.code }" >删除</a>
						   		 -->
						    </span>
					    </td>
					  </tr>
					 </logic:iterate>
					</logic:notEmpty>
				</tbody>
			</table>
			
			<f:paginate />	
		
		</div>
	</form>
	
	<!--版权区域-->
 	<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
</body>
</html>