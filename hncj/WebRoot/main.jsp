<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/flink.tld" prefix="f" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>

		<title>${comInfo.sp}</title>
		<f:css href="/css/screen.css" />
		<f:css href="/css/menutree.css" />
		<f:js src="/js/jquery.js"/>
		<f:js src="/js/biz/main.js"/>

		<!--��ʾ�������˵�����-->
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
			
			//�˳�ϵͳ
			function logOut(){
				
			}
			
			
			
//����Ŀ¼�˵�����������obj����ǰ�����Ĳ˵���noid���˵���Ӧ���Ӳ˵����ڵı�ǩid;level����ǰ����˵��ĵȼ�
//ֻ�ж�����һ���˵����ʱ��Żᴥ���˺���
function ShowMenu(obj,noid,level){
	changeColor(obj);
	var block = document.getElementById(noid);//�˵���Ӧ��չʾ��
	var beforeOpen = false;//��¼ѡ�в˵�����ʾ��ԭ���Ƿ��Ǵ򿪵�
	if(block.className == "no"){//��	
	}else{
		beforeOpen = true;
	}
	
	if(level==2){//������Ƕ����˵�		
		var h2 = $(block).parent()[0].getElementsByTagName("h2");//�����Ӳ˵��б�
		for(var i=0; i<h2.length;i++){
			h2[i].innerHTML = h2[i].innerHTML.replace("-","+");//�޸Ķ����Ӳ˵���ǰ���չ������
			//h2[i].style.color = "";
		}
		//obj.style.color = "#FF0000";//�����˵�ѡ��ʱ�����ɫ
		
		var ul = $(block).parent()[0].getElementsByTagName("ul");//����һ���˵��µ����ж����Ӳ˵���Ӧ��չ����
		for(var i=0; i<ul.length; i++){ul[i].className = "no";}
	}	
	else{//�������һ���˵�
		//����ǰѡ�е�һ���˵��޸�Ϊ��ɫ������Ķ��޸�Ϊδѡ��״̬
		var h1 = document.getElementById("menu").getElementsByTagName("h1");//���е�һ���˵�		
		for(var i=0; i<h1.length;i++){
			h1[i].innerHTML = h1[i].innerHTML.replace("-","+");
			//h1[i].style.color = "";
		}
		//obj.style.color = "#ffffff";//һ���˵�ѡ��ʱ�����ɫ
		
		var span = document.getElementById("menu").getElementsByTagName("span");//����һ���˵���Ӧ��չ����
		for(var i=0; i<span.length; i++){span[i].className = "no";}
	}
	
	//�޸ĵ�ǰ����˵�����ʽ�����ز���
	//����ǰѡ�в˵�����ʾ��
	if(beforeOpen){//�ر� ԭ���Ǵ򿪵���ʾ��
		block.className = "no";
		//obj.style.color = "";	
	}else{//��  ԭ���ǹرյ���ʾ��
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
			$('#msgTip').text(cnt+'��δ����Ϣ');
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
		<!--�����ͷ��-->
		<div class="top">
			<div class="top_box">
				 <!-- 
				<div class="top_logo" style="background:url(${CONTEXT_PATH}/images/top_right.gif); "></div>
				  -->
				<div class="top_right">
					<div class="toptext">
						<a href="${CONTEXT_PATH}/pageHome.jsp" target="main_area">��ҳ</a><a href="login.do?action=logOff">���µ�½</a><a
							href="login.do?action=logOff">�˳�ϵͳ</a>
					</div>
				</div>
			</div>

			<!--ͷ������˵�-->
			<div class="top_menu">
				<div class="top_menutime" id="showtime">
					<script>setInterval("document.getElementById('showtime').innerHTML=new Date().toLocaleString()",300);</script>
				</div>
				<div class="top_menutext">
					<div class="top_fenge">
						<img src="images/separated.gif" width="3" height="39" />
					</div>
					
					<!-- ���ؿ�ݲ˵� -->
					<div class="top_button" id="topbutton">
						<logic:notEmpty name="quickMenus">
							<logic:iterate id="quick" name="quickMenus">
								<html:link page="${quick.entry}" onfocus="this.blur()" target="main_area">${quick.name}</html:link>
							</logic:iterate>
						</logic:notEmpty>
					</div>
				</div>
				
				<div class="top_menuuser">
					<img src="images/icon0.gif" width="12" height="16" />
					���ã�${SESSION_USER.loginId}
					<c:if test="${msgCnt > 0}">
						<a href="${CONTEXT_PATH}/noticeMsgAction.do?action=showNotice" target="main_area" style="color: red" id="msgTip">${msgCnt}��δ����Ϣ</a>
					</c:if>
				</div>
			</div>
		</div>

		<!--���˵���-->
		<div class="side" id="menu" style="overflow-x:hidden; padding-top:4px;padding-bottom:20px;">
				<logic:iterate id="menu1" name="USER_MENU" property="children"><!-- һ���Ӳ˵� -->
						<h1 onclick="javascript:ShowMenu(this,'${menu1.code}',1)"> + ${menu1.name}</h1>
						<span id="${menu1.code}" class="no">
							<logic:iterate id="menu2" name="menu1" property="children">	<!-- �����Ӳ˵� -->
								<!-- ���Ӳ˵� -->	
								<logic:empty name="menu2" property="entry">
									<h2 onclick="javascript:ShowMenu(this,'${menu2.code}',2)"> + ${menu2.name}</h2>
									<ul id="${menu2.code}" class="no">
										<logic:iterate id="menu3" name="menu2" property="children">	<!-- �����Ӳ˵� -->
											<html:link page="${menu3.entry}" onfocus="this.blur()" target="main_area" onclick="changeColor(this)"><li>${menu3.name}</li></html:link>
										</logic:iterate>
									</ul>
								</logic:empty>
								<!-- �����Ӳ˵�:�����˵���дΪ�Լ� -->
								<logic:notEmpty name="menu2" property="entry">
									<h2>&nbsp;&nbsp;&nbsp;<html:link page="${menu2.entry}" onfocus="this.blur()" target="main_area" onclick="changeColor(this)">${menu2.name}</html:link>
									</h2>
								</logic:notEmpty>
							</logic:iterate>
						</span>
				</logic:iterate>
				<div style="overflow-x:hidden; padding-top:1px;"></div>
		</div>

		<!--��ʾ���ذ�ť-->
		<div class="main_bt">
			<input type="image" id="yc" onclick="menu_xsyc()" src="images/yc_icon.gif" onfocus="this.blur()" title="�����ʾ/�������˵�"
				width="7" height="80" border="0"/>
		</div>

		<!--����������-->
		<div class="main">
			<iframe name="main_area" frameborder="0" src="pageHome.jsp"></iframe>
		</div>
	</body>
</html>
