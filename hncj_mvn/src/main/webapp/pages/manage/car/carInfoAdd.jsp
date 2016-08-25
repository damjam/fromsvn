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
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/dtree/wtree.js"/>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>
		<f:js src="/layer/layer.js"/>
		<f:js src="/js/sys.js"/>
		<f:js src="/js/common.js"/>
		<f:js src="/js/popUp.js"/>
		<f:js src="/js/custom.validate.js"/>
		<f:js src="/js/datePicker/WdatePicker.js" defer="defer"/>		
		<script type="text/javascript">
			
		 	function save(){
		 		FormUtils.submitFirstTokenForm();
		 	}
		 	
		 	
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
			function getOwnerInfo(){
				var houseSn = $('#houseSn').val();
				if(houseSn == ''){
					return;
				}
				var params = $('#houseSn').serialize();
				$.ajax({
					 type:'POST',
				     url:CONTEXT_PATH + '/parkingBill.do?action=getOwnerName',
				     async:true,
				     dataType: "json",
				     data:params,
				     contentType: "application/x-www-form-urlencoded; charset=utf-8",
					 success:function(data) {
				    	 if(data.error){
				    		 alert(data.error);
				    		 $('#houseSn').val('');
				    		 return;
				    	 }
				    	 var ownerName = data.ownerName;
				    	 var ownerCel = data.mobile;
				    	 $('#ownerName').val(ownerName);
				    	 $('#ownerCel').val(ownerCel);
					 },
					 error:function(data){   
	                     alert("连接服务器失败");
	                 }   
				});
			}
			$().ready(function(){
		 		$(".popup-search-parking").click(function(){
		 			var bindCode = '';
		 			var bindName = $(this).attr('data-search-for');
	 				var toUrl=CONTEXT_PATH+'/parkingInfo.do?action=queryPopup&bindCode='+bindCode+'&bindName='+bindName;
					layer.open({
						title:'车位',
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
	<form action="${uri}?action=doAdd" id="carInfoActionForm" method="post" class="validate">
	 <div class="userbox">	
		<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					   <tr>
						    <td class="formlabel nes">车牌号</td>
						    <td>
						    	<s:textfield name="carSn" id="carSn" maxlength="10" class="{required:true,carnum:true}"/>
						    	<span class="field_tipinfo">请输入正确的车牌号</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">品牌</td>
						    <td>
						    	<s:textfield name="brand" id="brand" maxlength="10"/>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">型号</td>
						    <td>
						    	<s:textfield name="model" id="model" maxlength="10"/>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">车主房屋编号</td>
						    <td>
						    	<s:textfield name="houseSn" id="houseSn" maxlength="15" onblur="getOwnerInfo()"/>
						    	<span class="field_tipinfo"></span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车主姓名</td>
						    <td>
						    	<s:textfield name="ownerName" id="ownerName" maxlength="10" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">车主联系电话</td>
						    <td>
						    	<s:textfield name="ownerCel" id="ownerCel" maxlength="20" class="{required:true}"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel">车位编号</td>
						    <td>
						    	<s:textfield name="parkingSn" id="parkingSn" maxlength="10" />
						    	<!-- 
								<a href="javascript:void(0)" class="popup-search-parking">
									<i class="fa fa-search"></i>
								</a> -->	
						    </td>
					   </tr>
					   <tr>
					   		<td></td>
					   		<td>
					   			<input type="button" id="btnSumit" value="保存" onclick="save()"/>
					 			<input type="button" id="btnReturn" value="取消" onclick="gotoUrl('${uri}?action=list')"/>
					   		</td>
					   </tr>
				  </table>
			</div>
		</div>	
	</form>	
</body>
</html>
