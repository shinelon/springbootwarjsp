package com.sample.wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.wechat.api.SnsAPI;
import com.sample.wechat.bean.SnsToken;
import com.sample.wechat.bean.user.User;
import com.sample.wechat.config.WechatConfig;
import com.sample.wechat.ticketmanager.TicketManager;
import com.sample.wechat.tokenmanager.TokenManager;
import com.sample.wechat.utils.JsonUtil;
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
		String redirectUrl=null;
		try {
			 redirectUrl=URLEncoder.encode("http://wxkfkf.tunnel.qydev.com/redirect", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		parm.put("redirectUrl",redirectUrl);
		request.setAttribute("parm", parm);
				
		return "apidemo";
	}
	@RequestMapping("/redirect")
	private String apiredirect(HttpServletRequest request){
		String code=(String) request.getParameter("code");
		String state=(String)request.getParameter("state");
		logger.info("code:"+code);
		logger.info("state:"+state);
		Map<String, String> parm=new HashMap<>();
		parm.put("code", code);
		parm.put("state",state);
		request.setAttribute("parm",parm);
		return "oauthpage";
	}
	@RequestMapping("/snstoken")
	@ResponseBody
	private String snsToken(HttpServletRequest request){
		String code=(String) request.getParameter("code");
		SnsToken snsToken=SnsAPI.oauth2AccessToken(config.getAppid(), config.getSecret(), code);
		logger.info("token:"+snsToken.getAccess_token());
		return JsonUtil.toJSONString(snsToken);
	}
	
	@RequestMapping("/getuserinfo")
	@ResponseBody
	private String getUserInfo(HttpServletRequest request){
		String code=(String) request.getParameter("code");
		SnsToken snsToken=SnsAPI.oauth2AccessToken(config.getAppid(), config.getSecret(), code);
		User user=SnsAPI.userinfo(snsToken.getAccess_token(), snsToken.getOpenid(), "zh_CN");
		String userInfo=JsonUtil.toJSONString(user);
		logger.info("user:"+userInfo);
		return userInfo;
	}
}
