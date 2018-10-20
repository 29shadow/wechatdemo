package com.gtzhou.wechat.utils;

import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.gtzhou.wechat.message.resp.Article;
import com.gtzhou.wechat.message.resp.Image;
import com.gtzhou.wechat.message.resp.ImageMessage;
import com.gtzhou.wechat.message.resp.MusicMessage;
import com.gtzhou.wechat.message.resp.NewsMessage;
import com.gtzhou.wechat.message.resp.TextMessage;
import com.gtzhou.wechat.message.resp.VideoMessage;
import com.gtzhou.wechat.message.resp.VoiceMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 微信消息接收（post）
 * @author Administrator
 *
 */
public class MessageUtil {

	   /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 返回消息类型：图片
     */
    public static final String RESP_MESSAGE_TYPE_Image = "image";

    /**
     * 返回消息类型：语音
     */
    public static final String RESP_MESSAGE_TYPE_Voice = "voice";

    /**
     * 返回消息类型：视频
     */
    public static final String RESP_MESSAGE_TYPE_Video = "video";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * 请求消息类型：视频
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * 事件类型：VIEW(自定义菜单 URl 视图)
     */
    public static final String EVENT_TYPE_VIEW = "VIEW";

    /**
     * 事件类型：LOCATION(上报地理位置事件)
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";

    /**
     * 事件类型：LOCATION(上报地理位置事件)
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";
    
    /**
     * 解析微信请求 xml
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception { 
    	//解析结果存储在map中
    	Map<String, String> map = new HashMap<String, String>(); 
    	
    	//从 request 中取得输入流   
    	InputStream inputStream = request.getInputStream();
    	
    	// 读取输入流   
        SAXReader reader = new SAXReader();
        Document read = reader.read(inputStream);
        Element root = read.getRootElement();
        List<Element> elements = root.elements();
        elements.stream().forEach(element -> {
        	map.put(element.getName(), element.getText());
        });
        
        inputStream.close();
    	return map;
    }
    
    /**
     * 文本消息对象转换成 xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
    
    /**
     * 图文消息对象转换成 xml
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }
    
    /**
     * 图片消息对象转换成 xml
     * @param imageMessage
     * @return
     */
    public static String imageMessageToXml(ImageMessage imageMessage) {
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }
    
    public static String imageToXml(Image imageMessage) {
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }
    
    /**
     * 语音消息对象转换成 xml
     * @param voiceMessage
     * @return
     */
    public static String voiceMessageToXml(VoiceMessage voiceMessage) {
        xstream.alias("xml", voiceMessage.getClass());
        return xstream.toXML(voiceMessage);
    }
    
    /**
     * 视频消息对象转换成 xml
     * @param videoMessage
     * @return
     */
    public static String videoMessageToXml(VideoMessage videoMessage) {
        xstream.alias("xml", videoMessage.getClass());
        return xstream.toXML(videoMessage);
    }
    
    /**
     * 音乐消息对象转换成 xml
     * @param musicMessage
     * @return
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }
    
    
    private static XStream xstream = new XStream(new XppDriver(){
    	public HierarchicalStreamWriter  createWriter(Writer out) {  
            return new PrettyPrintWriter(out) {  
                // 对所有 xml 节点的转换都增加 CDATA 标记   
                boolean cdata = true;  
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {  
                    super.startNode(name, clazz);  
                }  

                protected void writeText(QuickWriter writer, String text) {  
                    if (cdata) {  
                        writer.write("<![CDATA[");  
                        writer.write(text);  
                        writer.write("]]>");  
                    } else {  
                        writer.write(text);  
                    }  
                }  
            };  
        }  
    });
}
