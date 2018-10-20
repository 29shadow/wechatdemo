package com.gtzhou.wechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;

/**
 * 微信接入验证工具
 * @author Administrator
 *
 */
public class SignUtil {
	
	private static String token = "yingzi";
	
	/**
	 * 微信接入验证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		if(StringUtils.isEmpty(signature)){
			return false;
		}
		if(StringUtils.isEmpty(timestamp)){
			return false;
		}
		if(StringUtils.isEmpty(nonce)){
			return false;
		}
		
		//将三个参数进行字典序排序
        String[] tmpArr = {token,timestamp,nonce}; 
        Arrays.sort(tmpArr);
        
        //接成一个字符串进行sha1加密 
        StringBuffer bf = new StringBuffer();
        Arrays.asList(tmpArr).forEach(str -> bf.append(str));
        
        String pwd = sha1(bf.toString());
       /* if(StringUtils.trim(pwd).equals(signature)){
        	return true;
        }else{
        	return false;
        }*/
        return pwd!=null ? StringUtils.trim(pwd).equals(signature):false;
	}
	
	/**
	 * sha1加密
	 * @param str
	 * @return
	 */
	public static String sha1(String str){
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
	
	/**
	 * 将字节转换为十六进制字符串 
	 * @param bytes
	 * @return
	 */
	public static String bytes2Hex(byte[] bytes){
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
