package com.gtzhou.wechat.start;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * ��Ŀ������ʼ�� servlet
 * @author Administrator
 *
 */
public class InterfaceUrlIntiServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		InterfaceUrlInti.init();
	}

}
