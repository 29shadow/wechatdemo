package com.gtzhou.wechat.quartz;

import org.apache.log4j.Logger;

import com.gtzhou.wechat.common.WeChatTask;

/**
 * ��ʱ�����ȡtoken
 * @author Administrator
 *
 */
public class QuartzJob {
	
	private static Logger logger = Logger.getLogger(QuartzJob.class);
	
	/**
	 * ����ִ�л�ȡ token
	 */
	public void workForToken() {
        try {
            WeChatTask timer = new WeChatTask();
            timer.getToken_getTicket();
        } catch (Exception e) {
            logger.error(e, e);
        }
    }

}
