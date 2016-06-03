<!doctype html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<% response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp"%>
		<%@ include file="/pages/common/sys.jsp"%>
		<title></title>
		
		<f:css href="/css/page.css"/>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/jquery.js"/>
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
		 		$('#result').change(function(){
		 			var result = $(this).val();
		 			if(result == '00'){
		 				$('.feedback').show();
		 				$('#feedback').attr('disabled', false);
		 				$('.rate').show();
		 				$('#rate').attr('disabled', false);
		 				$('.processor').hide();
		 				$('#processor').attr('disabled', true);
		 			}else if(result == '01'){//已转移
		 				$('.feedback').show();
		 				$('#feedback').attr('disabled', false);
		 				$('.rate').hide();
		 				$('#rate').attr('disabled', true);
		 				$('.processor').hide();
		 				$('#processor').attr('disabled', true);
		 			}else if(result == '02'){//重新指派责任人
		 				$('.feedback').hide();
		 				$('#feedback').attr('disabled', true);
		 				$('.rate').hide();
		 				$('#rate').attr('disabled', true);
		 				$('.processor').show();
		 				$('#processor').attr('disabled', false);
		 			}
		 		});
		 		$('#processor').click(function(){
		 			var bindCode = '';
		 			var bindName = 'processor';
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
		 		$('#result').change();
		 	});
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAddTrack" id="dataForm" method="post" class="validate">
		<s:hidden name="id"/>
		<div class="userbox">
			<div class="widget">
			<table class="form_grid">
				<caption class="widget-head">${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">处理结果</td>
						    <td>
						    	<s:select name="track.result" id="result" list="#{'00':'已处理','01':'已转移','02':'重新指派责任人'}" listKey="key" listValue="value" style="166px;"></s:select>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr class="processor" style="display: none;">
					   		<td class="formlabel nes">责任人</td>
						    <td>
						    	<s:textfield name="track.processor" id="processor" maxlength="10" class="{required:true}"/>
						    	<a><i class="fa fa-institution"></i></a>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr class="feedback">
						    <td class="formlabel nes">处理详情</td>
						    <td>
						    	<s:textfield name="track.feedback" id="feedback" maxlength="25" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr class="rate">
						    <td class="formlabel nes">评价</td>
						    <td>
						    	<s:select name="rate" id="rate" list="#request.rateTypes" listKey="value" listValue="name" style="166px;"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">备注</td>
						    <td>
						    	<s:textfield name="track.remark" id="remark" maxlength="25"/>
						    </td>
					   </tr>
				  </table>
				  <div class="btnbox">
					 <input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 <input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
				</div>
			</div>
		</div>	
	</form>	
</body>
</html>
