package com.gtzhou.wechat.utils;

public class StringUtils {
	
	public static boolean isEmpty(String str){
		return null==str || "".equals(str) ? true:false;
	}
	
	public static String trim(String str){  
        return null !=str  ? str.trim() : str;  
    } 

}
