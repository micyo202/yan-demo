<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>用户登录</title>
<style>
#container {
	overflow: hidden;
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
}

#background_video {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	object-fit: cover;
	height: 100%;
	width: 100%;
}

#video_cover {
	position: absolute;
	width: 100%;
	height: 100%;
	background: url('resources/images/video_cover.png') no-repeat;
	background-size: cover;
	background-position: center;
}

#video_controls {
	position: absolute;
	left: 50%;
	transform: translate(-50%, 0);
}

@media ( min-width : 768px) {
	#video_controls {
		display: none;
	}
}

/* Demo page specific styles */
#container {
	height: 100%;
}

#overlay {
	position: absolute;
	top: 0;
	right: 0;
	left: 0;
	bottom: 0;
	background: rgba(0, 0, 0, 0.5);
}

</style>
<link href="${pageContext.request.contextPath}/resources/css/login.css" rel="stylesheet">
</head>
<body>
	<div id="container">
		<video id="background_video" loop muted></video>
    	<div id="video_cover"></div>
    	<div id="overlay"></div>
		<div id="login-window">
			<h1 style="color: #fff;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yan Frame</h1>
			<input id="username" type="text" class="username" name="username" placeholder="帐号" required autofocus>
			<input id="password" type="password" class="password" name="password" placeholder="密码" required>
			<div class="checkbox checkbox-primary">
				<input id="remember" class="styled" type="checkbox">
				<label for="remember" style="color: #fff;">记住我</label>
			</div>
			<button id="login-bt" type="button">登录</button>
		</div>
	</div>
	<script type="text/javascript">
		// 初始化值
		/*
		if($.cookie('username') != 'null'){
			$('#username').val($.cookie('username'));
			$('#password').val($.cookie('password'));
			$('#remember').attr('checked', $.cookie('remember'));
		}
		 */

		if ($.cookie('loginCookie') != null && $.cookie('loginCookie') != 'null') {
			var loginCookies = $.cookie('loginCookie').split(',');
			$('#username').val(loginCookies[0]);
			$('#password').val(loginCookies[1]);
			$('#remember').attr('checked', loginCookies[2]);
		}

		// 登录事件
		$('#login-bt').click(function() {
			// 获取checkBox值
			$.post('${pageContext.request.contextPath}/common/login/signin',
					{ 
						username : $('#username').val(),
						password : $('#password').val(),
						remember : $('#remember').is(":checked")
					},
					function(data) {
						if (data.status == 0) {
							$.alert(data.msg);
						} else {
								if (data.remember) {
									var loginCookie = $('#username').val() + ',' + $('#password').val() + ',' + $('#remember').is(":checked");
									$.cookie('loginCookie', loginCookie, { expires : 7 });
								} else {
									$.cookie('loginCookie', null);
								}
								location.href = '${pageContext.request.contextPath}' + data.url;
						}
					});
		});
	</script>
<script src="${pageContext.request.contextPath}/resources/js/bideo.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/login.js"></script>
</body>
</html>