package com.sample.wechat.localhttpclient;

public abstract class LocalResponseHandler{
	
	protected String uriId;
	
	protected long startTime = System.currentTimeMillis();

	public String getUriId() {
		return uriId;
	}

	public void setUriId(String uriId) {
		this.uriId = uriId;
	}
}
