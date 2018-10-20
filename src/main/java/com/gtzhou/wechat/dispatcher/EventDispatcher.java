package com.gtzhou.wechat.dispatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gtzhou.wechat.common.GetUseInfo;
import com.gtzhou.wechat.controller.WechatController;
import com.gtzhou.wechat.message.resp.Article;
import com.gtzhou.wechat.message.resp.Image;
import com.gtzhou.wechat.message.resp.ImageMessage;
import com.gtzhou.wechat.message.resp.NewsMessage;
import com.gtzhou.wechat.utils.HttpPostUploadUtil;
import com.gtzhou.wechat.utils.MessageUtil;

import net.sf.json.JSONObject;

/**
 * 事件消息分发
 * @author Administrator
 *
 */
public class EventDispatcher {
	private static Logger logger = Logger.getLogger(EventDispatcher.class);
	
	public static String processEvent(Map<String, String> map) {
		
		String openid = map.get("FromUserName"); // 用户 openid
		String mpid = map.get("ToUserName"); // 公众号原始 ID
		/*ImageMessage imgmsg = new ImageMessage();
		imgmsg.setToUserName(openid);
		imgmsg.setFromUserName(mpid);
		imgmsg.setCreateTime(new Date().getTime());
		imgmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);*/
		
		NewsMessage newmsg=new NewsMessage();
	    newmsg.setToUserName(openid);
	    newmsg.setFromUserName(mpid);
	    newmsg.setCreateTime(new Date().getTime());
	    newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //关注事件
        	System.out.println("==============这是关注事件！");
        	try {
            /*Image img = new Image();
            HttpPostUploadUtil util=new HttpPostUploadUtil();
            String filepath="D:\\test.png";  
            Map<String, String> textMap = new HashMap<String, String>();  
            textMap.put("name", "testname");  
            Map<String, String> fileMap = new HashMap<String, String>();  
            fileMap.put("userfile", filepath); 
            String mediaidrs = util.formUpload(textMap, fileMap);
            System.out.println(mediaidrs);
            String mediaid=JSONObject.fromObject(mediaidrs).getString("media_id");
            img.setMediaId(mediaid);
            imgmsg.setImage(img);
        	return MessageUtil.imageMessageToXml(imgmsg);*/
        	
        	 Map<String, String> userinfo=GetUseInfo.Openid_userinfo(openid);
             Article article=new Article();
             article.setDescription("欢迎来到gtzhou的个人博客：菜鸟程序员成长之路！"); //图文消息的描述
             article.setPicUrl(userinfo.get("headimgurl")); //图文消息图片地址
             article.setTitle("尊敬的："+userinfo.get("nickname")+",你好！");  //图文消息标题
             article.setUrl("http://www.cuiyongzhi.com");  //图文 url 链接
             List<Article> list=new ArrayList<Article>();
             list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里 list 中加入多个 Article 即可！
             newmsg.setArticleCount(list.size());
             newmsg.setArticles(list);
             return MessageUtil.newsMessageToXml(newmsg);
        	
	    	 } catch (Exception e) {
	             // TODO Auto-generated catch block
	             System.out.println("====代码有问题额☺！");
	             logger.error(e,e);
	         }
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
            System.out.println("==============这是取消关注事件！");
        }

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
            System.out.println("==============这是自定义菜单点击事件！");
        }

        
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
            System.out.println("==============这是扫描二维码事件！");
        }
        
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
            System.out.println("==============这是位置上报事件！");
        }
        
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单 View 事件
            System.out.println("==============这是自定义菜单 View 事件！");
        }

        return null;
    }

}
