package com.gtzhou.wechat.message.req;

/**
 * ��Ƶ��Ϣ
 * @author Administrator
 *
 */
public class VideoMessage extends BaseMessage{
	
	private String MediaId; // ��Ƶ��Ϣý�� id�����Ե��ö�ý���ļ����ؽӿ���ȡ����
    private String ThumbMediaId; // ��Ƶ��Ϣ����ͼ��ý�� id�����Ե��ö�ý���ļ����ؽӿ���ȡ����

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

}
