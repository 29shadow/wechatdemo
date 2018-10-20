package com.gtzhou.wechat.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.gtzhou.wechat.utils.HttpUtils;

import net.sf.json.JSONObject;

/**
 * 微信两小时定时任务体,用于获取token
 * @author Administrator
 *
 */
public class WeChatTask {
	
	public void getToken_getTicket() throws Exception {
		
		//获取token执行体
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "client_credential");
        params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
        params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));
        String jstoken = HttpUtils.sendGet(
                GlobalConstants.getInterfaceUrl("tokenUrl"), params);
        String access_token = JSONObject.fromObject(jstoken).getString(
                "access_token"); // 获取到 token 并赋值保存
        GlobalConstants.interfaceUrlProperties.put("access_token", access_token);
                System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token 为=============================="+access_token);
                
        //获取 jsticket 的执行体
        params.clear();
        params.put("access_token", access_token);
        params.put("type", "jsapi");
        String jsticket = HttpUtils.sendGet(
                GlobalConstants.getInterfaceUrl("ticketUrl"), params);
        String jsapi_ticket = JSONObject.fromObject(jsticket).getString(
                "ticket"); 
        GlobalConstants.interfaceUrlProperties
        .put("jsapi_ticket", jsapi_ticket); // 获取到 js-SDK 的 ticket 并赋值保存

        System.out.println("jsapi_ticket================================================" + jsapi_ticket);
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token 为=============================="+access_token);
    }

}
