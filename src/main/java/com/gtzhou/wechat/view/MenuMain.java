package com.gtzhou.wechat.view;

import com.alibaba.fastjson.JSONArray;
import com.gtzhou.wechat.utils.HttpUtils;

import net.sf.json.JSONObject;

public class MenuMain {
	
	public static void main(String[] args) {
		ClickButton cbt=new ClickButton();
        cbt.setKey("image");
        cbt.setName("�ظ�ͼƬ");
        cbt.setType("click");

        ViewButton vbt=new ViewButton();
        vbt.setUrl("http://jocker10.e2.luyouxia.net:30179");
        vbt.setName("sell");
        vbt.setType("view");

        JSONArray sub_button=new JSONArray();
        sub_button.add(cbt);
        sub_button.add(vbt);

        JSONObject buttonOne=new JSONObject();
        buttonOne.put("name", "�˵�");
        buttonOne.put("sub_button", sub_button);

        JSONArray button=new JSONArray();
        button.add(vbt);
        button.add(buttonOne);
        button.add(cbt);

        JSONObject menujson=new JSONObject();
        menujson.put("button", button);
        System.out.println(menujson);
        //����Ϊ����ӿڵ� url   +�ź������ token������Ͳ�������� token ��ȡ�ķ�������
        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+"13_A8Br6J7YzeZCRoGo91gZelpI8A_iIJPx392n2Ow8rFWcv0hrvtBkcMN62Mb3vV7i1lsIoxxLY0ls8TDob9cevZgJN4BoM70Oy9uMqdLHMtabJPH8QR-ekOrQwtdmvzEBAi3Ws3nfnlon2D3AKYUaADARFB";

        try{
            String rs=HttpUtils.sendPostBuffer(url, menujson.toString());
            System.out.println(rs);
        }catch(Exception e){
            System.out.println("�������");
        }
	}

}
