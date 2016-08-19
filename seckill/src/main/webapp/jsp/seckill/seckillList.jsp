<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>秒杀列表页</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <%@ include file="/jsp/common/head.jsp"%>
    <%@ include file="/jsp/common/taglibs.jsp" %>
    <%@ include file="/jsp/common/sys.jsp" %>
  </head>
  <body>
    <div class="container">
    	<div class="panel panel-default">
    		<div class="panel-heading text-center">
    			<h2>秒杀列表</h2>
    		</div>
    		<div class="panel-body">
    			<table class="table table-hover">
    				<thead>
    					<tr>
    						<th>名称</th>
    						<th>库存</th>
    						<th>开始时间</th>
    						<th>结束时间</th>
    						<th>创建时间</th>
    						<th>详情</th>
    					</tr>
    				</thead>
    				<tbody>
    					<c:forEach var="item" items="${list }">
	    					<tr>
	    						<td>${item.name}</td>
	    						<td>${item.number}</td>
	    						<td><fmt:formatDate value="${item.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
	    						<td><fmt:formatDate value="${item.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	    						<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	    						<td><a class="btn btn-info" href="${CONTEXT_PATH}/seckill/${item.id}">详情</a> </td>
	    					</tr>
    					</c:forEach>
    				</tbody>
    			</table>
    		</div>
    	</div>
    </div>


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  </body>
</html>