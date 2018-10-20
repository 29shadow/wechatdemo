package com.gtzhou.sp.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ��֤΢�Žӿ�ƾ֤
 * @author Administrator
 *
 */

public class WechatServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	//������Token
	private String Token; 
	
	//����ַ���
	private String echostr;
	
	@Override
	public void init() throws ServletException {
		Token = "yingzi";
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		connect(req,resp);
	}
	

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	private void connect(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		if(!checkSignature(req,resp)){
			System.out.println("����������ʧ�ܣ�");
			return;
		}
		String echostr=getEchostr();  
		if(!isEmpty(echostr)){
			resp.getWriter().print(echostr);//����໥��֤  
		}
	}
	
	/**
	 * ΢�Ž�����֤
	 */
	private boolean checkSignature(HttpServletRequest req, HttpServletResponse resp) {

		//΢�ż���ǩ��
		String signature = req.getParameter("signature") ;	
		//ʱ���
		String timestamp = req.getParameter("timestamp");
		//�����
		String nonce = req.getParameter("nonce");
		//����ַ���
		String echostr = req.getParameter("echostr");  
		
		if(isEmpty(signature)){
			return false;
		}
		if(isEmpty(timestamp)){
			return false;
		}
		if(isEmpty(nonce)){
			return false;
		}
		//���������������ֵ�������
        String[] tmpArr = {Token,timestamp,nonce}; 
        Arrays.sort(tmpArr);
        
        //�ӳ�һ���ַ�������sha1���� 
        StringBuffer bf = new StringBuffer();
        Arrays.asList(tmpArr).stream().forEach(str -> bf.append(str));
        String pwd = sha1(bf.toString());
        if(trim(pwd).equals(signature)){
        	this.echostr = echostr;
        	return true;
        }else{
        	return false;
        }
        
	}
	
	public boolean isEmpty(String str){
		return null==str || "".equals(str) ? true:false;
	}
	
	public String trim(String str){  
        return null !=str  ? str.trim() : str;  
    } 
	
	
	public String getEchostr(){  
        return echostr;  
    } 
	/**
	 * sha1����
	 * @param str
	 * @return
	 */
	public String sha1(String str){
		byte[] bytes = str.getBytes();
		MessageDigest md = null;
		String strDes = null;  
        try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(bytes);
			strDes = bytes2Hex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");  
			return null;
		}
        return strDes;
	}
	
	public String bytes2Hex(byte[] bytes){
		 String des = "";  
	        String tmp = null;  
	        for (int i = 0; i < bytes.length; i++) {  
	            tmp = (Integer.toHexString(bytes[i] & 0xFF));  
	            if (tmp.length() == 1) {  
	                des += "0";  
	            }  
	            des += tmp;  
	        }  
	        return des; 
	}
}
