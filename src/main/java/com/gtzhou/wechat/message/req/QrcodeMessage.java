package com.gtzhou.wechat.message.req;

/**
 * 二维码请求
 * @author gtzhou
 * @date 2018年8月26日
 */
public class QrcodeMessage extends BaseMessage{

	private int ExpireSeconds;
	private String ActionName;
	private String ActionInfo;
	private String SceneId;
	private String SceneStr;
	public int getExpireSeconds() {
		return ExpireSeconds;
	}
	public void setExpireSeconds(int expireSeconds) {
		ExpireSeconds = expireSeconds;
	}
	public String getActionName() {
		return ActionName;
	}
	public void setActionName(String actionName) {
		ActionName = actionName;
	}
	public String getActionInfo() {
		return ActionInfo;
	}
	public void setActionInfo(String actionInfo) {
		ActionInfo = actionInfo;
	}
	public String getSceneId() {
		return SceneId;
	}
	public void setSceneId(String sceneId) {
		SceneId = sceneId;
	}
	public String getSceneStr() {
		return SceneStr;
	}
	public void setSceneStr(String sceneStr) {
		SceneStr = sceneStr;
	}
	
	
	
}
