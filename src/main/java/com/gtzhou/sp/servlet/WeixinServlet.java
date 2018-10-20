package com.gtzhou.sp.servlet;
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.util.Arrays;  
import java.util.logging.Logger;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import net.sf.json.JSONObject;  
import net.sf.json.xml.XMLSerializer;  
public class WeixinServlet extends HttpServlet{  
	  
    private Logger log =Logger.getLogger(this.getClass().getName());  
    private static final long serialVersionUID = 1L;  
    private   String Token;  
    private   String echostr;  
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
         connect(request,response);  
    }  
  
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset-utf-8");
    	response.setCharacterEncoding("UTF-8");
        message(request,response);  
    }  
  
       
      
    /** 
     *@author gtzhou
     *@return   
     *@exception  
     *@param 
     *  
     * <p>����������Ч��֤</p> 
     */  
    private void connect(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{  
        log.info("RemoteAddr: "+ request.getRemoteAddr());  
        log.info("QueryString: "+ request.getQueryString());  
         if(!accessing(request, response)){  
             log.info("����������ʧ��.......");  
             return ;  
         }  
        String echostr=getEchostr();  
        if(echostr!=null && !"".equals(echostr)){  
                log.info("������������Ч..........");  
                response.getWriter().print(echostr);//����໥��֤  
        }  
    }  
    /** 
     * @author haibing.xiao 
     * Date 2013-05-29 
     * @return boolean 
     * @exception ServletException, IOException 
     * @param 
     * 
     *<p>��������΢�Ź���ƽ̨����֤</p>  
     */  
    private boolean accessing(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {  
        String signature = request.getParameter("signature");  
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");  
        String echostr = request.getParameter("echostr");  
        if( isEmpty(signature)){  
            return false;  
        }  
        if(isEmpty(timestamp)){  
            return false;  
        }  
        if(isEmpty(nonce)){  
            return false;  
        }  
        if(isEmpty(echostr)){  
            return false;  
        }  
        
        String[] ArrTmp = { Token, timestamp, nonce };  
        Arrays.sort(ArrTmp);  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < ArrTmp.length; i++) {  
            sb.append(ArrTmp[i]);  
        }  
        String pwd = Encrypt(sb.toString());  
           
        log.info("signature:"+signature+"timestamp:"+timestamp+"nonce:"+nonce+"pwd:"+pwd+"echostr:"+echostr);  
          
        if(trim(pwd).equals(trim(signature))){  
            this.echostr =echostr;  
            return true;  
        }else{  
            return false;  
        }  
    }  
    private String Encrypt(String strSrc) {  
        MessageDigest md = null;  
        String strDes = null;  
  
        byte[] bt = strSrc.getBytes();  
        try {  
            md = MessageDigest.getInstance("SHA-1");  
            md.update(bt);  
            strDes = bytes2Hex(md.digest()); //to HexString  
        } catch (NoSuchAlgorithmException e) {  
            System.out.println("Invalid algorithm.");  
            return null;  
        }  
        return strDes;  
    }  
  
    public String bytes2Hex(byte[] bts) {  
        String des = "";  
        String tmp = null;  
        for (int i = 0; i < bts.length; i++) {  
            tmp = (Integer.toHexString(bts[i] & 0xFF));  
            if (tmp.length() == 1) {  
                des += "0";  
            }  
            des += tmp;  
        }  
        return des;  
    }  
      
       
    public String getEchostr(){  
        return echostr;  
    }  
    /** 
     *@author haibing.xiao 
     *@return   
     *@exception ServletException, IOException 
     *@param 
     *  
     * <p>XML��װ���</p> 
     */  
     private void message(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{  
           
            InputStream is = request.getInputStream();  
            // ȡHTTP����������  
            int size = request.getContentLength();  
            // ���ڻ���ÿ�ζ�ȡ������  
            byte[] buffer = new byte[size];  
            // ���ڴ�Ž��������  
            byte[] xmldataByte = new byte[size];  
            int count = 0;  
            int rbyte = 0;  
            // ѭ����ȡ  
            while (count < size) {   
                // ÿ��ʵ�ʶ�ȡ���ȴ���rbyte��  
                rbyte = is.read(buffer);   
                for(int i=0;i<rbyte;i++) {  
                    xmldataByte[count + i] = buffer[i];  
                }  
                count += rbyte;  
            }  
            is.close();  
            String requestStr = new String(xmldataByte, "UTF-8");  
              
              
            try{  
                manageMessage(requestStr,request,response);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
              
        }  
   
       
    /** 
     * @author haibing.xiao 
     * @return  
     * @exception ServletException, IOException 
     * @param 
     *  
     * <p>ҵ��ת�����</p> 
     *  
     */  
      private void  manageMessage(String requestStr,HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{  
              
            String responseStr;  
             
            try {  
                 XMLSerializer xmlSerializer=new XMLSerializer();  
                 JSONObject jsonObject =(JSONObject) xmlSerializer.read(requestStr);  
//                 String event =jsonObject.getString("Event");  
//                 String msgtype =jsonObject.getString("MsgType");  
//                 if("CLICK".equals(event) && "event".equals(msgtype)){ //�˵�click�¼�  
//                     String eventkey =jsonObject.getString("EventKey");  
//                     if("hytd_001".equals(eventkey)){ // hytd_001 ���Ǻ����ŶӰ�ť�ı�־ֵ  
//                         jsonObject.put("Content", "��ӭʹ�ú����ŶӲ˵�click��ť.");  
//                     }  
//                      
//                 }  
//                 responseStr =creatRevertText(jsonObject);//����XML
                 responseStr =creatRevertPic(jsonObject);
                 log.info("responseStr:"+responseStr);  
//                 OutputStream os =response.getOutputStream();  
//                 os.write(responseStr.getBytes("UTF-8"));  
                 PrintWriter out = response.getWriter(); 
                 out.print(responseStr); 
                 out.close(); 
            }   catch (Exception e) {  
                e.printStackTrace();  
            }  
              
    }  
//    private String creatRevertText(JSONObject jsonObject){  
//            StringBuffer revert =new StringBuffer();  
//            revert.append("<xml>");  
//            revert.append("<ToUserName><![CDATA["+jsonObject.get("FromUserName")+"]]></ToUserName>");  
//            revert.append("<FromUserName><![CDATA["+jsonObject.get("ToUserName")+"]]></FromUserName>");  
//            revert.append("<CreateTime>"+jsonObject.get("CreateTime")+"</CreateTime>");  
//            revert.append("<MsgType><![CDATA[text]]></MsgType>");  
//            revert.append("<Content><![CDATA["+jsonObject.get("Content")+"]]></Content>");  
////            revert.append("<FuncFlag>0</FuncFlag>"); 
//            revert.append("<MsgId>1234567890123456</MsgId>");
//            revert.append("</xml>");  
//            return revert.toString();  
//        } 
      
      private String creatRevertPic(JSONObject jsonObject){  
          StringBuffer revert =new StringBuffer();  
          revert.append("<xml>");  
          revert.append("<ToUserName><![CDATA["+jsonObject.get("FromUserName")+"]]></ToUserName>");  
          revert.append("<FromUserName><![CDATA["+jsonObject.get("ToUserName")+"]]></FromUserName>");  
          revert.append("<CreateTime>"+jsonObject.get("CreateTime")+"</CreateTime>");  
          revert.append("<MsgType><![CDATA[image]]></MsgType>");  
          revert.append("<MediaId><![CDATA["+jsonObject.get("MediaId")+"]]></MediaId>");  
//          revert.append("<FuncFlag>0</FuncFlag>"); 
          revert.append("</xml>");  
          return revert.toString();  
      }  
    @Override  
    public void init() throws ServletException {  
        Token="yingzi";  
    }  
       
    private boolean isEmpty(String str){  
        return null ==str || "".equals(str) ? true :false;  
    }  
    private String trim(String str){  
        return null !=str  ? str.trim() : str;  
    }  
      
}  