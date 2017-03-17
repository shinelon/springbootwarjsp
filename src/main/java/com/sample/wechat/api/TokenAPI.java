package com.sample.wechat.api;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import com.sample.wechat.bean.Token;
import com.sample.wechat.localhttpclient.LocalHttpClient;

/**
 * ACCESS_TOKEN API
 * @author LiYi
 *
 */
public class TokenAPI extends BaseAPI{

	/**
	 * 获取access_token
	 * @param appid appid
	 * @param secret secret
	 * @return Token
	 */
	public static Token token(String appid,String secret){
		HttpUriRequest httpUriRequest = RequestBuilder.get()
				.setUri(BASE_URI + "/cgi-bin/token")
				.addParameter("grant_type","client_credential")
				.addParameter("appid", appid)
				.addParameter("secret", secret)
				.build();
		return LocalHttpClient.executeJsonResult(httpUriRequest,Token.class);
	}

}
