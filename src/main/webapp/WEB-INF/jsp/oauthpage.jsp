<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
<link rel="stylesheet" href="/static/css/style.css">
<title>Insert title here</title>
</head>
<body ontouchstart="">
	<div class="wxapi_container">
		oauthJsp
		<hr>
		code:${parm.code} <br> state:${parm.state}
		<div class="wxapi_index_container">
			<ul class="label_box lbox_close wxapi_index_list">

			</ul>
		</div>
		<div class="lbox_close wxapi_form">
			<h3 id="menu-basic">授权</h3>
			<span class="desc">网页授权获取用户信息</span>
			<button class="btn btn_primary" id="OAuthUserInfo"
				onClick="doOAuthUserInfo()">doOAuthUserInfo</button>
		</div>
</body>
<script type="text/javascript">
	function doOAuthUserInfo() {
		console.info("doOAuthAccessToken")
		$.get("getuserinfo?code=${parm.code}", function(data) {
			console.info(data)
		});
	}
</script>
<script type="text/javascript" src="/webjars/jquery/3.1.0/jquery.min.js"></script>
</html>
