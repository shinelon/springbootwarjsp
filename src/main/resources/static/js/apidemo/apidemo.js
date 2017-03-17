wx.ready(function() {
	$('#checkJsApi').click(function() {
		wx.checkJsApi({
			jsApiList : [ 'getNetworkType', 'previewImage' ],
			success : function(res) {
				alert(JSON.stringify(res));
			}
		});
	});
});

wx.error(function(res) {
	alert(res.errMsg);
});