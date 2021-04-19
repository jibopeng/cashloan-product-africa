$(function() {
	var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
	var downloadAndroidHost = "http://down.duandai.com/android/";
	var downloadIosHost = "http://down.duandai.com/ios/";
	var rarName = "duandai";
	var rarPostfix = ".apk";
	var cvalue = function(val){
		if(val == null || val == "" || val=="null"){
			val= "fqyd";
		}
		return val;
	};
	var ccode = cvalue($("#channelCode").val());
	
	var reg ='/api/user/wxRegister.htm';//注册页面
	var codeurl = '/api/user/sendSms.htm';//获取验证码
	var signup = '/api/user/validateSmsCode.htm';//判断验证码手否正确
	var checkurl = '/api/user/h5SendSms.htm';//h5获取验证码
	var savePhone = '/api/usr/saveIosPhone.htm';//保存ios手机号码
	var appVersion = '/api/user/appVersion.htm';//获取app版本号
	
	var close = function(e) {
		$(this).parents('.popup').hide();
		return false;
	};
	$('.popup .close').click(close);
	$('.popup a.yes').click(close);
	//$('.popup .overlay').click(close);
	$('.other label i').show();

	//show函数
	var show = function(msg) {
		$('.tips').show().find('h2').text(msg);
	};

	//show_download函数
	var show_download = function(msg,url) {
		$('#tck span').html(msg);
		$('#tck').removeClass('dis-none');
		setTimeout(function(){
			$('#tck').addClass('dis-none');
		},2000)
		window.location.href = url;
	};

	//pop函数
	var pop = function(msg,url) {
		$('#tck span').html(msg);
		$('#tck').removeClass('dis-none');
		setTimeout(function(){
			$('#tck').addClass('dis-none');
		},2000)
		window.location.href = url;
	};

	
	
	//表单验证
	var verifyPhone = function() {
		var reg = /^1(3|4|5|7|8)\d{9}$/;
		var tel = $('input[name=phone]').val();
		if (tel === '') {
			shownew('请输入手机号');
			return false;
		}
		if (!reg.test(tel)) {
			shownew('手机号不正确');
			return false;
		}
		return tel;
	};

	
	var verify = function() {
		var pwd = $('input[name=password]').val();
	    //MD5加密
	    var pwd_md5 = $.md5(pwd);    
	    var re = /(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,16})$/;
	    var code = $('input[name=vcode]').val();
	    var inviteCode = $('input[name=invitationCode]').val();
	    var channelCode = $('input[name=channelCode]').val();
	    var tel = verifyPhone();
	    if (!tel) {
	    	return false;
	    }
	    if (!re.test(pwd)) {
	    	shownew('必须输入6到16位字母与数字组合的密码');
	    	return false;
	    }
	    if (code === '') {
	    	shownew('验证码不能为空');
	    	return false;
	    }
	    if(!$("#check[type='checkbox']").is(':checked')){
	        shownew('请选择同意《短信王服务协议》');
	        return false;
	    }
	    var client = '';
	    if(isiOS){
	    	client = 'h5-ios';
	    } else {
	    	client = 'h5-android';
	    }
	
	    return {
	    	'loginName': tel,
	    	'loginPwd': pwd_md5,
	    	'type': 'register ',
	    	'invitationCode': inviteCode,
	    	'channelCode': channelCode,
	    	'vcode': code,
	    	'client': client
	    };		
	};
	
	//点击获取验证码
	var yes = true;
	$('#btn').click(function() {
		if($(this).attr('fs') == 'true'){
			var tel = verifyPhone();
			/*
			if(tel&&isiOS){
				shownew('ios版即将上线，敬请期待！');
				$.ajax({
	    			url : savePhone,
	    			data : {
	    				phone : tel
	    			},
	    			type : "POST",
	    			dataType : "JSON",
	    		});
	    		return false;
	    	}
	    	*/
			if(tel && yes){
				var downVersion = '';
				$.ajax({
					url: checkurl,
					data: {
	    	    	  code: $('#code').val(),
	    	    	  phone: tel,
	    	    	  type: 'register'
					},
					success: function(data) {
						var time = data.countDown == 0 ? 59 : data.countDown;
						if (data.code == 1000) {
							if(isiOS){
								//$(".hb-main").attr("style","display:block;");
								// shownew('您已经注册，ios版即将上线，敬请期待！');
								//window.location.href = "wnd://splash";
								//跳转AppStore
								//window.location.href = "";
								//跳转页面指引用户下载安装ios
								window.location.href = "/h5/ios_index.jsp";
							}else{
								if(ccode == 'sykj1'){
									window.location.href = "http://android.myapp.com/myapp/detail.htm?apkName=com.duandai.ddloan&ADTAG=mobile";
								} else {
									$.ajax({
										url : appVersion,
										dataType : 'JSON',
										success : function(resData){
											pop("您已经注册请下载登录",downloadAndroidHost+rarName+"_"+resData.version+"_"+ccode+rarPostfix);
										}
									});
								}
							}
						} else if (data.code == 200) {
							shownew(data.msg);
							Time = '59';
							show_Time();
						} else {
							shownew(data.msg);
						}
					}
				})
			}
			return false;
		}
	});

	//点击注册按钮
	$('#btn-reg').click(function(e) {
		var params = verify();
		var downVersion = '';
		if (!params) {
			return false;
		}else{
			$.ajax({
				url: reg,
				data: params,
				dataType: 'json', 
				success: function(data) {
					if (data.code == 200) {
						//注册成功
						if(isiOS){
							// shownew('您已经注册，ios版即将上线，敬请期待！');
							//$(".hb-main").attr("style","display:block;");
							//window.location.href = "wnd://splash";
							//跳转AppStore
							//window.location.href = "";
							//跳转页面指引用户下载安装ios
							window.location.href = "/h5/ios_index.jsp";
						}else{
							if(ccode == 'sykj1'){
								window.location.href = "http://android.myapp.com/myapp/detail.htm?apkName=com.duandai.ddloan&ADTAG=mobile";
							} else {
								$.ajax({
									url : appVersion,
									dataType : 'JSON',
									success : function(resData){
										show_download('恭喜您注册成功,快去下载app登录吧!',downloadAndroidHost+rarName+"_"+resData.version+"_"+ccode+rarPostfix);
									}
								});
							}
						}
					}else {
						//报错
						shownew(data.msg);
					}
				},
				error: function(data){
					shownew("网络连接错误!");
				}
			});
		}
		return false;
	});
  
	var show_Time = function (){ //加时函数
		if(Time == 0){ 
			$('#btn').attr({'fs':'true'})
			$('#btn').text('获取验证码');
		}else{
			$('#btn').text('重新获取('+Time+'秒)');
			Time--;
			setTimeout(show_Time,1000); 
			$('#btn').attr({'fs':'false'})
		}
	};
	
});