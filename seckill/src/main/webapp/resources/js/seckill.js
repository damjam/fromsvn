var seckill = {
	URL:{
		
	},
	validatePhone: function(userPhone){
		if(!isNaN(userPhone)){
			return true;
		}else{
			return false;
		}
	},
	detail:{
		init:function(params){
			//手机验证和登录
			var userPhone = $.cookie('userPhone');
			var beginTime = params['beginTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			if(!validatePhone(userPhone)){
				var phoneModal = $('#phoneModal');
				phoneModal.modal({
					show:true,
					backdrop:'static',
					keyboard:false
				});
			}
		}
	}
}