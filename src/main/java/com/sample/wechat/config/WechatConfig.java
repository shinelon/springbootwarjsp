package com.sample.wechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {
	private String appid;
	private String secret;
	private String url;

	public String getAppid() {
		return appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
