package com.sample.wechat.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sample.wechat.config.WechatConfig;
import com.sample.wechat.ticketmanager.TicketManager;
import com.sample.wechat.tokenmanager.TokenManager;

@WebListener
public class TokenAndTicketManagerListener implements ServletContextListener{
//	@Value("${wechat.appid}")
//	private String appid;
//	@Value("${wechat.secret}")
//	private String secret;
	@Autowired
	private WechatConfig config;
	
	private static final Logger logger = LoggerFactory.getLogger(TokenAndTicketManagerListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
	
		WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext())  
        .getAutowireCapableBeanFactory().autowireBean(this);
		logger.info("getAppid:"+config.getAppid());
		logger.info("secret:"+config.getSecret());
		//WEB容器 初始化时调用
		TokenManager.setDaemon(false);
		TokenManager.init(config.getAppid(), config.getSecret());
		TicketManager.init(config.getAppid(),5,60*118);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//WEB容器  关闭时调用
		TicketManager.destroyed();
		TokenManager.destroyed();
	}
}
