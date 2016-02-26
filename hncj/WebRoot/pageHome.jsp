<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<link rel="stylesheet" href="css/page.css" type="text/css"
			media="screen, projection" />
		<f:css href="/css/page.css"/>
		<f:js src="/js/common.js" />
		<f:js src="/js/jquery.js" />
		
		<f:js src="/js/common.js"/>
		<script type="text/javascript">
			function seeUserMsgInfo(){
				gotoUrl("/msgInfo.do?action=queryUserMsgInfo");
			}
			$().ready(function(){
				var readOne = '${readOne}';
				parent.setMsgTip(readOne);
			});
			function hasRead(link, msgId){
				gotoUrl('/noticeMsgAction.do?action=hasRead&msgId='+msgId);
				/*
				link.innerHtml('已读');
				$.ajax({
				 type:'POST',
			     url:CONTEXT_PATH + '/noticeMsgAction.do?action=hasRead',
			     async:false,
			     data:{msgId:msgId},
			     dataType: 'json',
				 success:function(data) {
			    	 if(data.result == true){
			    		 
			    	 }else{
			    		 //alert(data.msg);
			    	 }
				 },
				 error:function(data){   
                     alert("发送失败"+data.msg);
                 }       
			 });*/
			}
		</script>
		<style type="text/css">
			.myclass td{border:0px; height:25px; line-height:25px; vertical-align:middle; padding-left:3px;hite-space:nowrap;word-break:keep-all;}
		</style>
	</head>

	<body>
		<div class="location">
			您当前所在位置：<span class="redlink"><a href="javascript: void(0);">首页</a></span>
		</div>
		
		<!-- 登录成功提示区 -->
		<div class="okbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<div style="height:30px; padding:15px 15px 25px 15px; overflow:hidden;">
					<table style="width: 100%">
						<tr>
							<td width="52" align="center" valign="top">
								<img src="images/icon14.gif" width="37" height="41" />
							</td>
							<td>
								<p style="font-size:14px; font-weight:bold;">
									系统登录提示
								</p>
								<p style=" text-indent:2em; line-height:20px;">
									尊敬的用户，您已经成功登录，请通过左侧导航菜单进行相应功能操作！
									如果您在使用过程中有疑问，请拨打
									<span class="redfont">24小时客服热线：<strong></strong>
									</span>
								</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		<c:if test="${msgCnt > 0}">
		<div class="okbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">
				<div style="padding: 15px 15px 25px 15px; overflow: hidden;">
					<table style="width:100%;">
						<tr>
							<td width="52" align="center" valign="top" >
								<img src="images/icon14.gif" width="37" height="41" />
							</td>
							<td>
								<p style="font-size: 14px; font-weight: bold;">
									系统消息
								</p>
								<p style="text-indent: 2em; line-height: 20px;" align="center">
									
									<span class="redfont">&nbsp; </span>
								</p>
							</td>
						</tr>
					</table>
					<table class="data_grid myclass" style="border-collapse:collapse; padding-left: 20px;">
						<f:showDataGrid name="list" msg=" " styleClass="data_grid">
							<c:forEach items="list" var="element">
								<tr align="center">
									<td width="150">
										${element.subject}
									</td>
									<td align="left" nowrap="nowrap" class="bluelinku">
										${element.content}
									</td>
									<td align="right" nowrap="nowrap" class="bluelinku">
										<a href="javascript:void(0)" onclick="hasRead(this, ${element.id})">标记为已读</a>
									</td>
								</tr>
							</c:forEach>
						</f:showDataGrid>
					</table>
				</div>
			 </div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>
		</c:if>
		<!-- 角色流程帮助区 -->
		<div class="processtitle">
			<div class="processtitleimg">
				<img src="images/icon15.gif" width="37" height="41" />
			</div>
			<div class="processtitletx">
				常用操作流程：
				<span style="color:#0459a7"></span>
			</div>
		</div>

		<div class="processbox">
			<b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
			<div class="contentb">

				<div
					style="height:130px; padding:15px 15px 25px 15px; overflow:hidden;">
					<table style="width: 100%">
						<tr>
							<td valign="middle">
								<strong>步骤一</strong>：
								<br />
								<br />
								鼠标点击左侧功能操作中的“xxxx”菜单。
							</td>
							<td>
								<img src="images/arrow.gif" width="51" height="124" />
							</td>
							<td valign="middle">
								<strong>步骤二</strong>：
								<br />
								<br />
								在打开的窗口中点击“浏览”按钮xxxx。
							</td>
							<td>
								<img src="images/arrow.gif" width="51" height="124" />
							</td>
							<td valign="middle">
								<strong>步骤三</strong>：
								<br />
								<br />
								
							</td>
							<td>
								<img src="images/arrow.gif" width="51" height="124" />
							</td>
							<td valign="middle">
								<strong>步骤四</strong>：
								<br />
								<br />
								在弹出的提示窗口中点击“返回”按钮完成操作！
							</td>
						</tr>
					</table>
				</div>
			</div>
			<b class="b4"></b><b class="b3"></b><b class="b2"></b><b class="b1"></b>
		</div>

<!-- 
		<div class="processbz">
			注：如需观看更加具体的操作过程，请
			<span class="redlink"><a href="javascript: return;">点击此处</a>
			</span>下载Flash动画帮助教程！
		</div>
 -->
		<!--版权区域-->
		<jsp:include flush="true" page="/pages/layout/copyright.jsp"></jsp:include>
	</body>
</html>
