var seckill = {
	URL:{
		now:function (){
			return CONTEXT_PATH+'/seckill/time/now';
		},
		exposer:function(seckillId){
			return CONTEXT_PATH+'/seckill/'+seckillId+"/exposer";
		},
		killUrl:function(seckillId, md5){
			return CONTEXT_PATH+'/seckill/'+seckillId+'/'+md5+'/execute';
		}
	},
	handleSeckill: function(seckillId, node){
		//显示秒杀地址
		node.hide().html('<button class="btn btn-parimary btn-lg" id="killBtn"/>');
		$.post(seckill.URL.exposer(seckillId), {}, function(result){
			if(result && result['success']){
				var exposer = result['data'];
				if(exposer['exposed']){
					//开启秒杀
					var md5 = exposer['md5'];
					var killUrl = seckill.URL.killUrl(seckillId, md5);
					console.log('killUrl:'+killUrl);
					$('#killBtn').one('click',function(){
						//只带killBtn
						$(this).addClass('disabled');
						//发送请求执行秒杀
						$.post(killUrl, {}, function(result) {
							if(result && result['success']){
								var seckillResult = result['data'];
								var state = seckillResult['state'];
								var stateInfo = seckillResult['stateInfo'];
								if(state == 1){
									var tip = '<span class="label label-success">'+stateInfo+'</span>';
								}else {
									var tip = '<span class="label label-danger">'+stateInfo+'</span>';
								}
								node.hide.html(tip).show();
							}else{
								console.log(result);
							}
						});
					});
				}else{
					//未开启
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					//重新计时
					seckill.countdown(seckillId, now, start, end);
				}
			}else{
				console.log('result'+result);
			}
		});
	},
	validatePhone: function(phone){
		if(phone && phone.length == 11 && !isNaN(phone)){
			return true;
		}else{
			return false;
		}
	},
	countdown: function(seckillId, nowTime, startTime, endTime){
		var seckillBox = $('#seckill-box');
		if(nowTime > endTime){
			seckillBox.html('秒杀结束');
		}else if(nowTime < startTime){
			var killTime = new Date(startTime+1000);//避免出现页面显示已开始但实际未开始的情况
			seckillBox.countdown(killTime,function(event){
				var format = event.strftime('秒杀倒计时:%D天%H时%M分%S秒');
				seckillBox.html(format);
			}).on('finish.countdown', function(){
				//获取秒杀地址
				seckill.handleSeckill(seckillId, seckillBox);
			});
		}else{
			seckill.handleSeckill(seckillId, seckillBox);
		}
	},
	detail:{
		//页面加载后即执行的操作
		init:function(params) {
			//手机验证和登录
			var userPhone = $.cookie('userPhone');//使用cookie插件
			var beginTime = params['beginTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			if(!seckill.validatePhone(userPhone)){
				var phoneModal = $('#phoneModal');
				phoneModal.modal({
					show:true,
					backdrop:'static',
					keyboard:false
				});
				//
				$('#userPhoneBtn').click(function(){
					var userPhone = $('#userPhone').val();
					if(seckill.validatePhone(userPhone)){
						//写入cookie
						$.cookie('userPhone', userPhone, {expires:7,path:CONTEXT_PATH+'/seckill'});
						window.location.reload();
					}else{
						var tip = '<label class="label label-danger">手机号错误!</label>';
						$('#message').hide().html(tip).show(300);//动态显示
						return false;
					}
				});
			}
			//只需url，不传参数
			var beginTime = params['beginTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			$.get(seckill.URL.now(),{},function(result){
				if(result && result['success']){
					var nowTime = result['data'];
					seckill.countdown(seckillId,nowTime, beginTime, endTime);
				}else{
					//提示错误信息
					console.log('result'+result);
				}
			});
		}
	}
}