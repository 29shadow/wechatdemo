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
 * 验证微信接口凭证
 * @author Administrator
 *
 */

public class WechatServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	//服务器Token
	private String Token; 
	
	//随机字符串
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
			System.out.println("服务器接入失败！");
			return;
		}
		String echostr=getEchostr();  
		if(!isEmpty(echostr)){
			resp.getWriter().print(echostr);//完成相互认证  
		}
	}
	
	/**
	 * 微信接入验证
	 */
	private boolean checkSignature(HttpServletRequest req, HttpServletResponse resp) {

		//微信加密签名
		String signature = req.getParameter("signature") ;	
		//时间戳
		String timestamp = req.getParameter("timestamp");
		//随机数
		String nonce = req.getParameter("nonce");
		//随机字符串
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
		//将三个参数进行字典序排序
        String[] tmpArr = {Token,timestamp,nonce}; 
        Arrays.sort(tmpArr);
        
        //接成一个字符串进行sha1加密 
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
	 * sha1加密
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
