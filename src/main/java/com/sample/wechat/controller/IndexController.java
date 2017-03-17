package com.sample.wechat.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sample.wechat.config.WechatConfig;
import com.sample.wechat.ticketmanager.TicketManager;
import com.sample.wechat.tokenmanager.TokenManager;
import com.sample.wechat.utils.SignUtil;

@Controller
public class IndexController {
	private static final Logger logger=LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private WechatConfig config;
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		String token = TokenManager.getToken(config.getAppid());
		String ticket = TicketManager.getTicket(config.getAppid());
		request.setAttribute("token", token);
		request.setAttribute("ticket", ticket);
		return "index";
	}
	@RequestMapping("/apidemo")
	private String apidemo(HttpServletRequest request){
		String token = TokenManager.getToken(config.getAppid());
		String ticket = TicketManager.getTicket(config.getAppid());
		String url=config.getUrl();
		Map<String, String> parm=SignUtil.doSign(ticket, url+"/apidemo");
		parm.put("appid", config.getAppid());
		logger.info("token:"+token);
		logger.info("Sign:"+parm.toString());
		request.setAttribute("parm", parm);
		return "apidemo";
	}
}
