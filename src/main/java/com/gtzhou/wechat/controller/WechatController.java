package com.gtzhou.wechat.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gtzhou.wechat.common.JSSDK_Config;
import com.gtzhou.wechat.dispatcher.EventDispatcher;
import com.gtzhou.wechat.dispatcher.MsgDispatcher;
import com.gtzhou.wechat.message.Message;
import com.gtzhou.wechat.utils.MessageUtil;
import com.gtzhou.wechat.utils.SignUtil;
import com.gtzhou.wechat.utils.StringUtils;

@Controller
@RequestMapping("/wechat")
public class WechatController {
	
	private static Logger logger = Logger.getLogger(WechatController.class);
	
	/**
	 * 接受微信get请求，返回验证参数
	 * @param request
	 * @param response
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 */
	@RequestMapping(value = "security", method = RequestMethod.GET)
	public void doGet(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="signature") String signature,
			@RequestParam(value="timestamp") String timestamp,
			@RequestParam(value="nonce") String nonce,
			@RequestParam(value="echostr") String echostr,
			@RequestParam(value="shopId") String shopId){
		//微信接入验证
		try {
			System.out.println("-----"+shopId);
			if(!SignUtil.checkSignature(signature, timestamp, nonce)){
				System.out.println("服务器接入失败！");
				return;
			}
			
			//返回验证参数
			if(!StringUtils.isEmpty(echostr)){
				PrintWriter out = response.getWriter();
				out.print(echostr);//完成相互认证  
				out.close();
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		
	}
	
	/**
	 * 用于接收微信服务端消息
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "security", method = RequestMethod.POST)
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset-utf-8");
    	response.setCharacterEncoding("UTF-8");
		System.out.println("post方法");
		try {
			PrintWriter out = response.getWriter(); 
			Map<String, String> map=MessageUtil.parseXml(request);
			String msgtype=map.get("MsgType");
			if(MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgtype)){
				//进入事件处理
				out.print(EventDispatcher.processEvent(map)); 
            }else{
            	out.print(MsgDispatcher.processMessage(map)); //进入消息处理
            }
			
            out.close(); 
		} catch (Exception e) {
			logger.error(e,e);
		}
	}
	
	@RequestMapping("/jssdk")
	@ResponseBody
    public Message JSSDK_config(
            @RequestParam(value = "url", required = true) String url) {
        try {
            System.out.println(url);
            Map<String, String> configMap = JSSDK_Config.jsSDK_Sign(url);
            return Message.success(configMap);
        } catch (Exception e) {
            return Message.error();
        }

    }
	
	@RequestMapping("/testjs")
	public String testJS(){
		return "jssdkconfig";
	}


}
