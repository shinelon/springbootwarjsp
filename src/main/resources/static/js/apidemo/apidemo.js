wx.ready(function() {
	$('#checkJsApi').click(function() {
		wx.checkJsApi({
			jsApiList : [ 'getNetworkType', 'previewImage' ],
			success : function(res) {
				alert(JSON.stringify(res));
			}
		});
	});
	
//	$("#OAuth").click(function(){
//	    location.href ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=${parm.appid}&redirect_uri=http%3A%2F%2Fnba.bluewebgame.com%2Foauth_response.php&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
//	});
	
});

wx.error(function(res) {
	alert(res.errMsg);
});