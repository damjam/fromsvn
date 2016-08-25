<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>
		<f:js src="/layer/layer.js"/>			
		<script type="text/javascript">
			
		 	function save(){
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	$().ready(function(){
		 		$(".popup-search").click(function(){
		 			var bindCode = '';
		 			var bindName = $(this).attr('data-search-for');
	 				var toUrl=CONTEXT_PATH+'/employee.do?action=queryPopup&bindCode='+bindCode+'&bindName='+bindName;
					layer.open({
						title:'员工',
					    type: 2,
					    area: ['720px', '530px'],
					    fix: false, //不固定
					    maxmin: true,
					    content: toUrl
					});
		 		});
		 	});
		 	
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<div class="userbox">
	<form action="${uri}?action=doAdd" id="dataForm" method="post" class="validate">
		<div class="widget">
			<table class="form_grid">
				<caption class="widget-head">${ACT.name}</caption>
				  <tr>
					    <td class="formlabel nes">物料名称</td>
					    <td>
					    	<s:textfield name="item" id="item" maxlength="10" class="{required:true}"/>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				  </tr>
				  <tr class="houseSn">
					    <td class="formlabel">品牌</td>
					    <td>
					    	<s:textfield name="brand" id="brand" maxlength="10"/>
					    	<span class="field_tipinfo"></span>
					    </td>
				   </tr>
				  <tr>
					    <td class="formlabel">型号</td>
					    <td>
					    	<s:textfield name="modal" id="modal" maxlength="10"/>
					    	<span class="field_tipinfo"></span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel">价值</td>
					    <td>
					    	<s:textfield name="price"  id="price" class="{num:true}" maxlength="11"/>
					    	<span class="field_tipinfo">请输入数字</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">状态</td>
					    <td>
					    	<s:select name="state" id="state" list="#request.materialStates" listKey="value" listValue="name" style="166px;"></s:select>
					    	<span class="field_tipinfo"></span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel">来源</td>
					    <td>
					    	<s:textfield name="source" id="source" maxlength="25"/>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel">购置日期</td>
					    <td>
					    	<s:textfield name="buyDate"  id="buyDate" maxlength="8" readonly="true" onfocus="WdatePicker();"/>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel">购置经手人</td>
					    <td>
					    	<s:textfield name="buyer"  id="buyer" maxlength="10"/>
					    	<a href="javascript:void(0)" data-search-for="buyer" class="popup-search"><i class="fa fa-search"></i></a>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel nes">责任人</td>
					    <td>
					    	<s:textfield name="manager"  id="manager" maxlength="10" class="{required:true}"/>
					    	<a href="javascript:void(0)" data-search-for="manager" class="popup-search"><i class="fa fa-search"></i></a>
					    	<span class="field_tipinfo">不能为空</span>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel">最后经手人</td>
					    <td>
					    	<s:textfield name="lastUser"  id="lastUser" maxlength="10" class="{required:true}"/>
					    	<a href="javascript:void(0)" data-search-for="lastUser" class="popup-search"><i class="fa fa-search"></i></a>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel">存放位置</td>
					    <td>
					    	<s:textfield name="place" id="place" maxlength="10"/>
					    </td>
				   </tr>
				   <tr>
					    <td class="formlabel">备注</td>
					    <td>
					    	<s:textfield name="remark" id="remark" maxlength="25"/>
					    </td>
				   </tr>
			  </table>
			  <div class="btnbox">
				 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
				 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
			</div>
		</div>
	</form>
	</div>	
</body>
</html>
