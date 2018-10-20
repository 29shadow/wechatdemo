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
 * ΢����Ϣ���գ�post��
 * @author Administrator
 *
 */
public class MessageUtil {

	   /**
     * ������Ϣ���ͣ��ı�
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * ������Ϣ���ͣ�ͼ��
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * ������Ϣ���ͣ�ͼƬ
     */
    public static final String RESP_MESSAGE_TYPE_Image = "image";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String RESP_MESSAGE_TYPE_Voice = "voice";

    /**
     * ������Ϣ���ͣ���Ƶ
     */
    public static final String RESP_MESSAGE_TYPE_Video = "video";

    /**
     * ������Ϣ���ͣ��ı�
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";

    /**
     * ������Ϣ���ͣ�ͼƬ
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";

    /**
     * ������Ϣ���ͣ�����λ��
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

    /**
     * ������Ϣ���ͣ���Ƶ
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

    /**
     * ������Ϣ���ͣ���Ƶ
     */
    public static final String REQ_MESSAGE_TYPE_VIDEO = "video";

    /**
     * ������Ϣ���ͣ�����
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * �¼����ͣ�subscribe(����)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

    /**
     * �¼����ͣ�unsubscribe(ȡ������)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

    /**
     * �¼����ͣ�CLICK(�Զ���˵�����¼�)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";

    /**
     * �¼����ͣ�VIEW(�Զ���˵� URl ��ͼ)
     */
    public static final String EVENT_TYPE_VIEW = "VIEW";

    /**
     * �¼����ͣ�LOCATION(�ϱ�����λ���¼�)
     */
    public static final String EVENT_TYPE_LOCATION = "LOCATION";

    /**
     * �¼����ͣ�LOCATION(�ϱ�����λ���¼�)
     */
    public static final String EVENT_TYPE_SCAN = "SCAN";
    
    /**
     * ����΢������ xml
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) throws Exception { 
    	//��������洢��map��
    	Map<String, String> map = new HashMap<String, String>(); 
    	
    	//�� request ��ȡ��������   
    	InputStream inputStream = request.getInputStream();
    	
    	// ��ȡ������   
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
     * �ı���Ϣ����ת���� xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }
    
    /**
     * ͼ����Ϣ����ת���� xml
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }
    
    /**
     * ͼƬ��Ϣ����ת���� xml
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
     * ������Ϣ����ת���� xml
     * @param voiceMessage
     * @return
     */
    public static String voiceMessageToXml(VoiceMessage voiceMessage) {
        xstream.alias("xml", voiceMessage.getClass());
        return xstream.toXML(voiceMessage);
    }
    
    /**
     * ��Ƶ��Ϣ����ת���� xml
     * @param videoMessage
     * @return
     */
    public static String videoMessageToXml(VideoMessage videoMessage) {
        xstream.alias("xml", videoMessage.getClass());
        return xstream.toXML(videoMessage);
    }
    
    /**
     * ������Ϣ����ת���� xml
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
                // ������ xml �ڵ��ת�������� CDATA ���   
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
