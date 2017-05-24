package com.sample.wechat.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.IdleConnectionEvictor;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {
	
	public static final Logger logger = LoggerFactory.getLogger(HttpClientConfig.class);

	@Bean
	public PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager pm = new PoolingHttpClientConnectionManager();
		pm.setMaxTotal(100);
		pm.setDefaultMaxPerRoute(100);
		pm.setDefaultSocketConfig(SocketConfig.copy(SocketConfig.DEFAULT)
		        .setSoTimeout(5000)
		        .build());
		return pm;
	}

	@Bean
	public HttpClientBuilder getHttpClientBuilder(PoolingHttpClientConnectionManager pm) {
		return HttpClients.custom().setConnectionManager(pm)
				.setConnectionManagerShared(true).setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(5000)
						.setConnectTimeout(5000).setConnectionRequestTimeout(3000).build())
				.setRetryHandler(new HttpRequestRetryHandler() {
					@Override
					public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
						return false;
					}
				});
	}

	@Bean
	public CloseableHttpClient getCloseableHttpClient(HttpClientBuilder httpClientBuilder) {
		return httpClientBuilder.build();
	}

	@Bean(destroyMethod = "shutdown")
	public IdleConnectionEvictor getIdleConnectionEvictor(PoolingHttpClientConnectionManager pm) {
		IdleConnectionEvictor idleConnectionEvictor = new IdleConnectionEvictor(pm, 60, TimeUnit.SECONDS, 60,
				TimeUnit.SECONDS);
		idleConnectionEvictor.start();
		logger.info("HttpClient IdleConnectionEvictor is run:{}", idleConnectionEvictor.isRunning());
		return idleConnectionEvictor;
	}

}
