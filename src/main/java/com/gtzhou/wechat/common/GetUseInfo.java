package com.gtzhou.wechat.common;

import java.util.HashMap;
import java.util.Map;

import com.gtzhou.wechat.utils.HttpUtils;

import net.sf.json.JSONObject;

/**
 * ��ȡ΢���û���Ϣ
 * @author Administrator
 *
 */
public class GetUseInfo {
	
	public static Map<String, String> Openid_userinfo(String openid)
            throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token",GlobalConstants.getInterfaceUrl("access_token"));  //��ʱ���л�ȡ���� token
		params.put("openid", openid);  //��Ҫ��ȡ���û��� openid
        params.put("lang", "zh_CN");
        String subscribers = HttpUtils.sendGet(
                GlobalConstants.getInterfaceUrl("OpenidUserinfoUrl"), params);
        System.out.println(subscribers);
        params.clear();
        //���ﷵ�ز���ֻȡ���ǳơ�ͷ�񡢺��Ա�
        params.put("nickname",
                JSONObject.fromObject(subscribers).getString("nickname")); //�ǳ�
        params.put("headimgurl",
                JSONObject.fromObject(subscribers).getString("headimgurl"));  //ͼ��
        params.put("sex", JSONObject.fromObject(subscribers).getString("sex"));  //�Ա�
        return params;
	}

}
