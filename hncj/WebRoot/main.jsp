<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8"%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<%@ include file="/pages/common/taglibs.jsp" %>
		<title>${comInfo.sp}</title>
		<f:css href="/css/screen.css" />
		<f:css href="/css/menutree.css" />
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/biz/main.js"/>

		<!--显示隐藏左侧菜单代码-->
		<script>
			var fg = true;
			function menu_xsyc(){
			   if(fg){
			       document.getElementById("menu").style.display="none";
			       document.getElementById("yc").src="images/xs_icon.gif";
			   }else{
			       document.getElementById("menu").style.display="block";
			       document.getElementById("yc").src="images/yc_icon.gif";
			   }
			   fg = !fg;
			}
			
			//退出系统
			function logOut(){
				
			}
			
			
			
//单击目录菜单触发函数：obj：当前单击的菜单；noid：菜单对应的子菜单所在的标签id;level：当前点击菜单的等级
//只有二级和一级菜单点击时候才会触发此函数
function ShowMenu(obj,noid,level){
	changeColor(obj);
	var block = document.getElementById(noid);//菜单对应的展示块
	var beforeOpen = false;//记录选中菜单的显示块原来是否是打开的
	if(block.className == "no"){//打开	
	}else{
		beforeOpen = true;
	}
	
	if(level==2){//点击的是二级菜单		
		var h2 = $(block).parent()[0].getElementsByTagName("h2");//二级子菜单列表
		for(var i=0; i<h2.length;i++){
			h2[i].innerHTML = h2[i].innerHTML.replace("-","+");//修改二级子菜单的前面的展开符号
			//h2[i].style.color = "";
		}
		//obj.style.color = "#FF0000";//二级菜单选中时候的颜色
		
		var ul = $(block).parent()[0].getElementsByTagName("ul");//所在一级菜单下的所有二级子菜单对应的展现体
		for(var i=0; i<ul.length; i++){ul[i].className = "no";}
	}	
	else{//点击的是一级菜单
		//将当前选中的一级菜单修改为红色，其余的都修改为未选中状态
		var h1 = document.getElementById("menu").getElementsByTagName("h1");//所有的一级菜单		
		for(var i=0; i<h1.length;i++){
			h1[i].innerHTML = h1[i].innerHTML.replace("-","+");
			//h1[i].style.color = "";
		}
		//obj.style.color = "#ffffff";//一级菜单选中时候的颜色
		
		var span = document.getElementById("menu").getElementsByTagName("span");//所有一级菜单对应的展现体
		for(var i=0; i<span.length; i++){span[i].className = "no";}
	}
	
	//修改当前点击菜单的样式：开关操作
	//处理当前选中菜单的显示块
	if(beforeOpen){//关闭 原来是打开的显示块
		block.className = "no";
		//obj.style.color = "";	
	}else{//打开  原来是关闭的显示块
		block.className = "";
		obj.innerHTML = obj.innerHTML.replace("+","-");	
	}
}
function changeColor(obj){
	if(window.lastClick != null || window.lastClick != undefined){
		lastClick.style.color = "";
		obj.style.color = "#FF0000";
		lastClick = obj;
	}else{
		lastClick = obj;
		lastClick.style.color = "#FF0000";
	}
}
function setMsgTip(readOne){
	if(readOne == 'Y'){
		var tip = $('#msgTip').text();
		var s = tip.replace(/[^\d]*/ig,"");
		var cnt = parseInt(s)-1;
		if(cnt > 0){
			$('#msgTip').text(cnt+'条未读消息');
		}else{
			$('#msgTip').text('');
		}
	}	
}

$().ready(function(){
	var tip = '${tip}';
	if(tip != ''){
		alert(tip);
		top.location.href='${CONTEXT_PATH}';
	}
});
		</script>
	</head>
	
	<body class="top_frame">
		<!--主框架头部-->
		<div class="top">
			<div class="top_box">
				 <!-- 
				<div class="top_logo" style="background:url(${CONTEXT_PATH}/images/top_right.gif); "></div>
				  -->
				<div class="top_right">
					<div class="toptext">
						<a href="${CONTEXT_PATH}/pageHome.jsp" target="main_area">首页</a><a href="login.do?action=logOff">重新登录</a><a
							href="login.do?action=logOff">退出系统</a>
					</div>
				</div>
			</div>

			<!--头部横向菜单-->
			<div class="top_menu">
				<div class="top_menutime" id="showtime">
					<script>setInterval("document.getElementById('showtime').innerHTML=new Date().toLocaleString()",300);</script>
				</div>
				<div class="top_menutext">
					<div class="top_fenge">
						<img src="images/separated.gif" width="3" height="39" />
					</div>
					
					<!-- 加载快捷菜单 -->
					<div class="top_button" id="topbutton">
						<c:if test="${not empty quickMenus}">
							<c:forEach var="quick" items="${quickMenus}">
								<a href="${CONTEXT_PATH}${quick.entry}" target="main_area" onfocus="this.blur();">${quick.name}</a>
							</c:forEach>
						</c:if>
					</div>
				</div>
				
				<div class="top_menuuser">
					<img src="images/icon0.gif" width="12" height="16" />
					您好！${SESSION_USER.loginId}
					<c:if test="${msgCnt > 0}">
						<a href="${CONTEXT_PATH}/noticeMsgAction.do?action=showNotice" target="main_area" style="color: red" id="msgTip">${msgCnt}条未读消息</a>
					</c:if>
				</div>
			</div>
		</div>

		<!--左侧菜单区-->
		<div class="side" id="menu" style="overflow-x:hidden; padding-top:4px;padding-bottom:20px;">
			
					<s:iterator id="menu1" value="userMenu.children">
					<h1 onclick="javascript:ShowMenu(this,'${menu1.code}',1)"> + ${menu1.name}</h1>
						<span id="${menu1.code}" class="no">
							<s:iterator id="menu2" value="children">
								<s:if test='#menu2.entry == null || "".equals(#menu2.entry)'>
									<h2 onclick="javascript:ShowMenu(this,'${menu2.code}',2)"> + ${menu2.name}</h2>
									<ul id="${menu2.code}" class="no">
										<s:iterator id="menu3" value="children">
											<a href="${CONTEXT_PATH}${menu3.entry}" onfocus="this.blur()" target="main_area" onclick="changeColor(this)"><li>${menu3.name}</li></a>
										</s:iterator>
									</ul>
								</s:if>
								<s:else>
									<h2>&nbsp;&nbsp;&nbsp;
										 <a href="${CONTEXT_PATH}${menu2.entry}" onfocus="this.blur()" target="main_area" onclick="changeColor(this)">${menu2.name}</a>
									</h2>
								</s:else>
							</s:iterator>
						</span>
				</s:iterator>
				<div style="overflow-x:hidden; padding-top:1px;"></div>
		</div>

		<!--显示隐藏按钮-->
		<div class="main_bt">
			<input type="image" id="yc" onclick="menu_xsyc()" src="images/yc_icon.gif" onfocus="this.blur()" title="点击显示/隐藏左侧菜单"
				 style="width: 7;height: 80;border: 0"/>
		</div>

		<!--载入内容区-->
		<div class="main">
			<iframe name="main_area" frameborder="0" src="pageHome.jsp"></iframe>
		</div>
	</body>
</html>
