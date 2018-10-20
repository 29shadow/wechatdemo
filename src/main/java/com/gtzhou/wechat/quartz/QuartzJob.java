package com.gtzhou.wechat.quartz;

import org.apache.log4j.Logger;

import com.gtzhou.wechat.common.WeChatTask;

/**
 * 定时任务获取token
 * @author Administrator
 *
 */
public class QuartzJob {
	
	private static Logger logger = Logger.getLogger(QuartzJob.class);
	
	/**
	 * 任务执行获取 token
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
