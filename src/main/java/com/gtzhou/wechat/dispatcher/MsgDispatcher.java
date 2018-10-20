package com.gtzhou.wechat.dispatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gtzhou.wechat.common.GlobalConstants;
import com.gtzhou.wechat.message.resp.Article;
import com.gtzhou.wechat.message.resp.Image;
import com.gtzhou.wechat.message.resp.ImageMessage;
import com.gtzhou.wechat.message.resp.NewsMessage;
import com.gtzhou.wechat.message.resp.TextMessage;
import com.gtzhou.wechat.utils.HttpPostUploadUtil;
import com.gtzhou.wechat.utils.HttpUtils;
import com.gtzhou.wechat.utils.MessageUtil;

/**
 * 消息分发处理
 * @author Administrator
 *
 */
public class MsgDispatcher {
	
	public static String processMessage(Map<String, String> map) throws Exception{
		
		String openid=map.get("FromUserName"); //用户 openid
		String mpid=map.get("ToUserName");   //公众号原始 ID
		
		//普通文本消息回复
		TextMessage txtmsg=new TextMessage();
		txtmsg.setToUserName(openid);
		txtmsg.setFromUserName(mpid);
		txtmsg.setCreateTime(new Date().getTime());
		txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		
		//图文消息回复
		  //对图文消息
		NewsMessage newmsg=new NewsMessage();
		newmsg.setToUserName(openid);
		newmsg.setFromUserName(mpid);
		newmsg.setCreateTime(new Date().getTime());
		newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
        	String content = map.get("Content");
        	if("1".equals(content)){
        		//txtmsg.setContent("你好，你发送的内容是 1！");
        		//获取天气预报
        		Map<String, String> paramsMap = new HashMap<String, String>();
        		paramsMap.put("city", "上海");
        		String result = null;
        		try {
        			result = HttpUtils.sendGet("https://www.sojson.com/open/api/weather/json.shtml",paramsMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
        		System.out.println(result);
        		txtmsg.setContent(result);
        	}else if("2".equals(content)){
        		Image img = new Image();
        		ImageMessage imageMessage = new ImageMessage();
        		Map<String,String> paramsMap = new HashMap<String,String>();
        		imageMessage.setFromUserName(mpid);
        		imageMessage.setToUserName(openid);
        		imageMessage.setCreateTime(new Date().getTime());
        		imageMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);
        		//上传本地文件到微信获取mediaId
        		img.setMediaId(uploaFile());
        		imageMessage.setImage(img);
        		System.out.println(MessageUtil.imageMessageToXml(imageMessage));
        		return MessageUtil.imageMessageToXml(imageMessage);
        	}else if("3".equals(content)){
        		generateQrcode();
        		txtmsg.setContent(generateQrcode());
        	}else if("4".equals(content)){
                txtmsg.setContent("<a href=\"http://www.cuiyongzhi.com\">bo博客</a>");
            }else if("5".equals(content)){
            	sendTemplateMsg(openid);
            }else {
            	txtmsg.setContent("你好，这里是gtzhou个人账号！");
            }
            return MessageUtil.textMessageToXml(txtmsg);
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
        	System.out.println("==============这是图片消息！");
            Article article=new Article();
            article.setDescription("这是图文消息 1"); //图文消息的描述
            article.setPicUrl("http://res.cuiyongzhi.com/2016/03/201603086749_6850.png"); //图文消息图片地址
            article.setTitle("图文消息 1");  //图文消息标题
            article.setUrl("http://www.cuiyongzhi.com");  //图文 url 链接
            List<Article> list=new ArrayList<Article>();
            list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里 list 中加入多个 Article 即可！
            newmsg.setArticleCount(list.size());
            newmsg.setArticles(list);
            return MessageUtil.newsMessageToXml(newmsg);
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
        	txtmsg.setContent("");
            return MessageUtil.textMessageToXml(txtmsg);
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
        	System.out.println("==============这是位置消息！");
            return "";
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
            System.out.println("==============这是视频消息！");
        }

        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
            System.out.println("==============这是语音消息！");
        }

        return null;
    }
	
	public static String uploaFile(){
		Map<String,String> fileMap = new HashMap<>();
		Map<String,String> textMap = new HashMap<>();
		fileMap.put("img", "D:\\test.png");
		HttpPostUploadUtil up = new HttpPostUploadUtil();
		String result = up.formUpload(textMap, fileMap);
		JSONObject obj = JSONObject.parseObject(result);
		System.out.println("image id:"+obj.get("media_id"));
		return (String) obj.get("media_id");
	}
	
	public static String generateQrcode(){
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+GlobalConstants.getInterfaceUrl("access_token");
		Map<String,Object> params = new TreeMap<>();
		params.put("action_name", "QR_LIMIT_SCENE");
	
		Map<String,String> pa = new TreeMap<>();
		pa.put("scene_id", "123");
		Map<String,Map<String,String>> actionInfo = new TreeMap<>();
		actionInfo.put("scene", pa);
		params.put("action_info",actionInfo);
		
		
		ObjectMapper json = new ObjectMapper();
		String pas = "";
		try {
			pas = json.writeValueAsString(params);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String result = "";
		try {
			result = HttpUtils.sendPostBuffer(url, pas);
			//获取ticket
			JSONObject object = JSONObject.parseObject(result);
			String ticket = String.valueOf(object.get("ticket"));
			
			result = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
 			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String sendTemplateMsg(String openId){
		
    	String reqUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+GlobalConstants.getInterfaceUrl("access_token");
		Map<String,Object> params = new TreeMap<>();
		params.put("touser",openId);
		params.put("template_id","U1Xwmqt0SeFB0mBtiznTHnxgdquc3bPvAfXK68sENDI");
		params.put("url", "https://www.baidu.com");
		

		Map<String,String> openid = new TreeMap<>();
		openid.put("value", "你的openid为:"+openId);
		openid.put("color", "#173177");
		
		Map<String,Map<String,String>> data = new TreeMap<>();
		data.put("openid", openid);
		
		params.put("data", data);
		
		ObjectMapper json = new ObjectMapper();
		
		String pas="";
		try {
			pas = json.writeValueAsString(params);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String result = "";
		try {
			result = HttpUtils.sendPostBuffer(reqUrl, pas);
			//获取ticket
			JSONObject object = JSONObject.parseObject(result);
			String ticket = String.valueOf(object.get("ticket"));
			
			result = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
 			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
