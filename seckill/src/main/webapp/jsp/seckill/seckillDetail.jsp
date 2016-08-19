<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>秒杀详情页</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <%@ include file="/jsp/common/head.jsp"%>
    <%@ include file="/jsp/common/taglibs.jsp"%>
    <%@ include file="/jsp/common/sys.jsp"%>
  </head>
  <body>
  	
  	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-head">
				<h2> ${seckill.name}</h2>
			</div>
			<div class="panel-body">
				<h3>
				<span class="glyphicon glyphicon-time"></span>
				<!-- 显示倒计时 -->
				<span class="glyphicon" id="seckill-box"></span>
				<span class="">
					<button type="button" class="btn btn-primary" id="killBtn">开始秒杀</button>
				</span>
				</h3>
			</div>
		</div>
	</div>
	
	<!-- 输入电话号码弹出框 -->
	<div class="modal fade" id="phoneModal">
		<div class="modal-dialog">
			<div class="modal-content">	
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>秒杀电话
					</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" class="form-control" id="userPhone"
								name="userPhone" placeholder="请填写手机号">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<span id="message" class="glyphicon"></span>
					<button type="button" class="btn btn-success" data-dismiss="modal" id="userPhoneBtn">
						<span class="glyphicon glyphicon-phone"></span>
						提交
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal 

	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.js"></script>
    <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
    <f:js src="/resources/js/seckill.js"/>
    <script type="text/javascript">
	    $(function() {
			seckill.detail.init({
				seckillId:${seckill.id},
				beginTime:${seckill.beginTime.time},
				endTime:${seckill.endTime.time}
			});
		});
    </script>
  </body>
</html>