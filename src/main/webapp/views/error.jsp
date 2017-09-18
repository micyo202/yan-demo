<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/global.jsp"%>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="${pageContext.request.contextPath}/resources/plugins/countUp.js"></script>
<title>错误页面</title>
<style type="text/css">
body, html {
	height: 100%;
	font-family: 'Microsoft YaHei', 'Arial';
	font-size: 13px;
	font-weight: 400;
	background: #4B96B4;
	/* Old browsers */
	background: -moz-linear-gradient(-45deg, #4B96B4 0%, #57B9BD 100%);
	/* FF3.6+ */
	background: -webkit-gradient(linear, left top, right bottom, color-stop(0%, #4B96B4),
		color-stop(100%, #57B9BD));
	/* Chrome,Safari4+ */
	background: -webkit-linear-gradient(-45deg, #4B96B4 0%, #57B9BD 100%);
	/* Chrome10+,Safari5.1+ */
	background: -o-linear-gradient(-45deg, #4B96B4 0%, #57B9BD 100%);
	/* Opera 11.10+ */
	background: -ms-linear-gradient(-45deg, #4B96B4 0%, #57B9BD 100%);
	/* IE10+ */
	background: linear-gradient(-45deg, #4B96B4 0%, #57B9BD 100%);
	/* W3C */
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#4B96B4,
		endColorstr=#57B9BD, GradientType=1);
	/* IE6-9 fallback on horizontal gradient */
	color: #fff;
}

#error-link {
	font-size: 150px;
	padding: 10px;
}
</style>
<script type="text/javascript">
	var errorCode = '${errorCode}';
	var mainApp = {
		main_fun : function() {
			var count = new countUp("error-link", 0, errorCode, 0, 5);
			window.onload = function() {
				count.start();
			}
		},
		initialization : function() {
			mainApp.main_fun();
		}
	}
	$(function() {
		mainApp.main_fun();
	});
	window.onload = function() {
		setTimeout("location.reload()", 1);
	}
</script>
</head>
<body>
	<div class="container">
		<div style="padding-top: 60px; text-align: center;">
			<div class="col-md-6 col-md-offset-3 text-center">
				<h1>对不起，您的操作有误！</h1>
				<h5>未能监测到的错误操作，请检查原因</h5>
				<span id="error-link"></span>
				<h2>! 请联系系统管理员 !</h2>
			</div>
		</div>

		<div style="text-align: center;">
			<div class="col-md-8 col-md-offset-2">
				<h3>
					<i class="fa fa-lightbulb-o fa-5x"></i>
				</h3>
			</div>
		</div>
	</div>
</body>
</html>