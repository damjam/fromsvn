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
		 		$('#reporterType').change(function(){
		 			var reporterType = $(this).val();
	 				if(reporterType == '00'){//业主
	 					$('.houseSn').show();
	 					$('#houseSn').attr('disabled', false);
	 				}else{
	 					$('.houseSn').hide();
	 					$('#houseSn').attr('disabled', true);
	 				}
		 		});
		 		$('#reporter').click(function(){
		 			var bindCode = '';
		 			var bindName = 'reporter';
		 			var reporterType = $('#reporterType').val();
		 			if(reporterType == '01'){//员工
		 				var toUrl=CONTEXT_PATH+'/employee.do?action=queryPopup&bindCode='+bindCode+'&bindName='+bindName;
						layer.open({
							title:'员工',
						    type: 2,
						    area: ['720px', '530px'],
						    fix: false, //不固定
						    maxmin: true,
						    content: toUrl
						});
		 			}
		 		});
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
		 	function getHouseInfo(){
				if($('#houseSn').val() == ''){
					return;
				}
				var params = $('#houseSn').serialize();
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/commonServiceBill.do?action=getHouseInfo',
				     async:true,
				     dataType: "json",
				     data:params,
				     contentType: "application/x-www-form-urlencoded; charset=utf-8",
					 success:function(data) {
				    	 if(data.error){
				    		 alert(data.error);
				    		 $('#houseSn').val('');
				    		 $('#houseSn').focus();
				    		 return;
				    	 }
				    	 var ownerName = data.ownerName;
				    	 $('#reporter').val(ownerName);
				    	 
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" id="dataForm" method="post" class="validate">
		<div class="userbox">
			<div>
				<b class="b1"></b>
				<b class="b2"></b>
				<b class="b3"></b>
				<b class="b4"></b>
				<div class="contentb">
					<table class="form_grid">
					  <caption>${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">报事报修人类型</td>
						    <td>
						    	<s:select name="reporterType" id="reporterType" list="#request.reporterTypes" listKey="value" listValue="name" style="166px;"></s:select>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					  </tr>
					  <tr class="houseSn">
						    <td class="formlabel nes">房屋编号</td>
						    <td>
						    	<s:textfield name="houseSn" id="houseSn" maxlength="10" onblur="getHouseInfo()" class="{required:true}"/>
						    	<span class="field_tipinfo">格式如:6-1203</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">姓名</td>
						    <td>
						    	<s:textfield name="reporter" id="reporter" class="{required:true}" maxlength="10"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">联系电话</td>
						    <td>
						    	<s:textfield name="reporterTel"  id="reporterTel" class="{digit:true}" maxlength="11"/>
						    	<span class="field_tipinfo">请输入正确的手机号</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">详情</td>
						    <td>
						    	<s:textfield name="item"  id="item" class="required:true}" maxlength="25"/>
						    	<span class="field_tipinfo">请输入详情</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">重要程度</td>
						    <td>
						    	<s:select name="impIndex" id="impIndex" list="#request.impIndexTypes" listKey="value" listValue="name" style="166px;"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">紧急程度</td>
						    <td>
						    	<s:select name="emgIndex" id="emgIndex" list="#request.emgIndexTypes" listKey="value" listValue="name" style="166px;"></s:select>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">指定责任人</td>
						    <td>
						    	<s:textfield id="processor" name="processor" maxlength="10"/>
						    	<a href="javascript:void(0)" data-search-for="processor" class="popup-search"><i class="fa fa-search"></i></a>
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
