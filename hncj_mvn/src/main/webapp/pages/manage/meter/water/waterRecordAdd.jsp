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
		<f:js src="/dtree/wtree.js"/>
		<script src="https://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
		<f:js src="/js/plugin/jquery.metadata.js"/>
		<f:js src="/js/plugin/jquery.validate.js"/>		
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
			function checkNum(){
				var prenum = $('#prenum').val();
				var curnum = $('#curnum').val();
				if(prenum != '' && curnum != ''){
					if(parseInt(curnum, 10) < parseInt(prenum, 10)){
						alert('本期读数不能小于上期读数');
						return;
					}
					$('#num').val(curnum-prenum);
				}
			}
			function getPreRecord(){
				var houseSn = $('#houseSn').val();
				if(houseSn == ''){
					return;
				}
				$.post(CONTEXT_PATH + '/${uri}?action=getPreRecord&houseSn='+houseSn, function(data) {
					if (data != null) {
						var jsonObj = eval('(' + data + ')');
						$('#prenum').val(jsonObj.prenum);
						var preRecordDate = jsonObj.preRecordDate;
						if(preRecordDate != ''){
							$('#preRecordDate').val(jsonObj.preRecordDate);
						}
					}
					else {
						$('#prenum').val(0);
					}
				});
			}
			/*
			function getRecordDate(){
				var recordMonth = $('#recordMonth').val();
				if(recordMonth == '') {
					return;
				}
				$.post(CONTEXT_PATH + '/${uri}?action=getRecordDate&recordMonth='+recordMonth, function(data) {
					if (data != null) {
						var jsonObj = eval('(' + data + ')');
						var preRecordDate = jsonObj.preRecordDate;
						var curRecordDate = jsonObj.curRecordDate;
						if(preRecordDate != ''){
							$('#preRecordDate').val(jsonObj.preRecordDate);
						}
						if(curRecordDate != ''){
							$('#curRecordDate').val(jsonObj.curRecordDate);
						}
					}
				});
			}*/
		</script>
	</head>
<body>

<f:msg styleClass="msg"/>
	<form action="${uri}?action=doAdd" method="post" class="validate">
		<div class="userbox">
			<div class="widget">
				<table class="form_grid">
					<caption class="widget-head">${ACT.name}</caption>
					  <tr>
						    <td class="formlabel nes">房屋编号</td>
						    <td>
						    	<s:textfield name="houseSn"  id="houseSn" class="{required:true}" maxlength="10" onblur="getPreRecord()"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">用水月份</td>
						    <td>
						    	<s:textfield name="recordMonth" id="recordMonth" class="{required:true}" />
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					  <tr>
						    <td class="formlabel nes">上期抄表日期</td>
						    <td>
						    	<s:textfield name="preRecordDate" id="preRecordDate" class="{required:true,digit:true}" maxlength="8" onfocus="WdatePicker();" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <!--  -->
					   <tr>
						    <td class="formlabel nes">上期读数</td>
						    <td>
						    <s:textfield name="prenum"  id="prenum" class="{required:true,digit:true}" maxlength="8"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr> 
					   <tr>
						    <td class="formlabel nes">本期抄表日期</td>
						    <td>
						    <s:textfield name="curRecordDate"  id="curRecordDate" class="{required:true}" maxlength="8" readonly="true" onfocus="WdatePicker();"/>
						    	<span class="field_tipinfo">不能为空</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">本期读数</td>
						    <td>
						    <s:textfield name="curnum"  id="curnum" class="{required:true,digit:true}" maxlength="8" onblur="checkNum()"/>
						    	<span class="field_tipinfo">请输入正确的数字</span>
						    </td>
					   </tr>
					   <tr>
						    <td class="formlabel nes">实际用量</td>
						    <td>
						    <s:textfield name="num"  id="num" class="{required:true}" maxlength="25" readonly="true"/>
						    	<span class="field_tipinfo">不能为空</span>
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
