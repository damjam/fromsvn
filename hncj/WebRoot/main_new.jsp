<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%response.setHeader("Cache-Control", "no-cache");%>
<%@ include file="/pages/common/taglibs.jsp" %>
<html lang="zh-cn"> 
	<head>
		<%@ include file="/pages/common/meta.jsp" %>
		<%@ include file="/pages/common/sys.jsp" %>
		<%@ include file="/pages/common/taglibs.jsp" %>
		<title>${comInfo.sp}</title>
		<link href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css">
	    <link href="http://cdn.bootcss.com/jqueryui/1.11.3/jquery-ui.css" rel="stylesheet">
		<f:css href="/macadmin/css/style.css"/>
		<f:css href="/macadmin/css/widgets.css"/>
		<f:css href="/macadmin/css/jquery.onoff.css"/>
		<f:css href="/macadmin/css/jquery.cleditor.css"/>
		<f:css href="/macadmin/css/jquery.dataTables.css"/>
		
		<f:css href="/macadmin/css/bootstrap-datetimepicker.min.css"/>
		<f:css href="/macadmin/css/fullcalendar.css"/>
		<f:css href="/macadmin/css/prettyPhoto.css"/>
		<script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.js"></script>
		<f:js src="/js/jquery.js"/>
		<script src="http://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
		<script src="http://cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.js"></script>
		
		<f:js src="/macadmin/js/jquery.onoff.min.js"/>
		<f:js src="/macadmin/js/jquery.cleditor.min.js"/>
		<f:js src="/macadmin/js/bootstrap-datetimepicker.min.js"/>
		<f:js src="/macadmin/js/moment.min.js"/>
		<f:js src="/macadmin/js/fullcalendar.min.js"/>
		<f:js src="/macadmin/js/jquery.prettyPhoto.js"/>
		<f:js src="/macadmin/js/jquery.slimscroll.min.js"/>
		<f:js src="/macadmin/js/jquery.dataTables.min.js"/>
		<!-- Noty -->
		<f:js src="/macadmin/js/jquery.noty.js"/>
		<f:js src="/macadmin/js/themes/default.js"/>
		<f:js src="/macadmin/js/layouts/bottom.js"/>
		<f:js src="/macadmin/js/layouts/topRight.js"/>
		<f:js src="/macadmin/js/layouts/top.js"/>
		
		<f:js src="/macadmin/js/custom.js"/>
		
		<script src="http://cdn.bootcss.com/jquery.nicescroll/3.6.8/jquery.nicescroll.min.js"></script>
		<f:js src="/js/biz/main.js"/>
		<f:js src="/js/common.js"/>
		
<style type="text/css">
.sidebar-closed > #sidebar > ul {
    display: none;
}

.sidebar-closed #main-content {
    margin-left: 0px;
}

.sidebar-closed #sidebar {
    margin-left: -230px;
}
body{
	border-top: 3px solid #eee;
	background:#f7f7f7;  
	padding-top: 37px;
}
</style>
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
		lastClick.style.background = "";
		obj.style.background = "#eee";
		lastClick = obj;
	}else{
		lastClick = obj;
		lastClick.style.background = "#eee";
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
	
	$("aside.left-panel:not(.collapsed)").niceScroll({
        cursorcolor: '#8e909a',
        //cursorborder: '0px solid #fff',
        cursoropacitymax: '0.5',
        cursorborderradius: '0px'
   });
});


function toggleMenu(){
	if ($('#sidebar').is(":visible") === true) {
        $('#main-content').css({
            'margin-left': '0px'
        });
        
        $('#sidebar').hide();
        $('aside.left-panel').addClass('collapsed');
        //$('#nav').css('display','none');
    } else {
        $('#main-content').css({
            'margin-left': '180px'
        });
        $('#sidebar').show();
        $('aside.left-panel').removeClass('collapsed');
        
        //$("#content").removeClass("sidebar-closed");
    }
}



</script>
<style type="text/css">
	.jqstooltip { 
		position: absolute;
		left: 0px;top: 0px;
		visibility: hidden;
		background: rgb(0, 0, 0) transparent;
		background-color: rgba(0,0,0,0.6);
		filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000);
		-ms-filter: "progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";
		color: white;font: 10px arial, san serif;text-align: left;
		white-space: nowrap;
		padding: 5px;border: 1px solid white;z-index: 10000;
		}
		.jqsfield { color: white;font: 10px arial, san serif;text-align: left;}
		.mainwrap{
			position: relative;
			margin-left: 180px;
			margin-right: 0px;
			width: auto;
			background:#eee url("../img/main-back.png") repeat;
			/*border-left: 1px solid #ccc;*/
			z-index: 50;
			min-height: 600px;
		}
		.mainwrap iframe {
		    position: absolute;
		    height: 100%;
		    width: 100%;
		    left: 0;
		    top: 0;
		}
		.main_bt {
		    float: left;
		    margin: 0px;
		    padding: 180px 10px 0px 0px;
		    _padding-right: 5px;
		}
		aside.left-panel {
	    
	    width: 180px;
	    position: fixed;
	    height: 100%;
	    top: 0px;
	    left: 0px;
	}
</style>
	</head>
	
   <body>
   <div class="navbar navbar-fixed-top bs-docs-nav" role="banner">
    <div class="conjtainer">
      <!-- Menu button for smallar screens -->
      <div class="navbar-header">
		  <button class="navbar-toggle btn-navbar" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
			<span>Menu</span>
		  </button>
		</div>
      
      

      <!-- Navigation starts -->
      <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">         

        <ul class="nav navbar-nav">  
         
          <!-- Upload to server link. Class "dropdown-big" creates big dropdown -->
		  <li class="dropdown dropdown-big">
           <a href="javascript:void(0)"> <i class="fa fa-bars menu-toggle" onclick="toggleMenu()" aria-hidden="true" style="padding-left: 10px;"></i>
          </a>
          </li>
          <li class="dropdown dropdown-big" >
           <span style="font-size:16px;font-weight:bold;line-height:50px;padding-left:10px;">${comInfo.sp}</span>
          </li>
        </ul>

        <!-- Search form -->
        
        <!-- Links -->
        <ul class="nav navbar-nav pull-right">
          <li class="dropdown pull-right">            
            <a data-toggle="dropdown" class="dropdown-toggle" href="index.html#">
              <i class="fa fa-user"></i> ${SESSION_USER.loginId} <b class="caret"></b>              
            </a>
            
            <!-- Dropdown menu -->
            <ul class="dropdown-menu">
              <li><a href="index.html#"><i class="fa fa-user"></i>个人信息</a></li>
              <li><a href="index.html#"><i class="fa fa-cogs"></i>设置</a></li>
              <li><a href="login.do?action=logOff"><i class="fa fa-sign-out"></i>退出</a></li>
            </ul>
          </li>
          
        </ul>
      </nav>

    </div>
  </div>
		
		

        
   <!-- mac main start -->     
        <div class="content" style="margin-top:10px;" id="content">

  	<!-- Sidebar -->
    <div class="sidebar" id="sidebar">
        <div class="sidebar-dropdown"><a href="index.html#">Navigation</a></div>

        <!--- Sidebar navigation -->
        <!-- If the main navigation has sub navigation, then add the class "has_sub" to "li" of main navigation. -->
        <aside class="left-panel" style="overflow:hidden;outline: none;padding-top: 50px;">
        <ul id="nav" class="nicescroll left-panel" style="overflow:hidden;outline: none">
          <!-- Main menu with font awesome icon -->
          <li class="open"><a href="pageHome_new.jsp" target="main_area">待办事项</a>
            <!-- Sub menu markup 
            <ul>
              <li><a href="#">Submenu #1</a></li>
              <li><a href="#">Submenu #2</a></li>
              <li><a href="#">Submenu #3</a></li>
            </ul>-->
          </li>
          
          <s:iterator id="menu1" value="userMenu.children">
          	<li class="has_sub">
          		<a href="javascript:void(0);"> ${menu1.name}  <span class="pull-right"><i class="fa fa-chevron-right" style="line-height: 28px;height: auto;"></i></span></a>
            <ul>
            	<s:iterator id="menu2" value="children">
             	  	<li><a class="" href="${CONTEXT_PATH}${menu2.entry}" onclick="changeColor(this)" target="main_area">${menu2.name }</a></li>
             	</s:iterator>
            </ul>
          	</li>
          </s:iterator>
        </ul>
        </aside>
    </div>
  	  	<!-- mainwrap -->
  	<div class="mainwrap" id="main-content">
      <iframe name="main_area" id="main_area" src="pageHome_new.jsp" style="border: 0;font: "></iframe>
    </div>

   <!-- mainwrap ends -->
   <div class="clearfix"></div>

</div>
<!-- Content ends -->

<!-- Footer starts -->
<!-- 
<footer>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
            <p class="copy">Copyright @ 2016</p>
      </div>
    </div>
  </div>
</footer>
 --> 

	</body>
</html>
