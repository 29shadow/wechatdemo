package com.gtzhou.wechat.interceptors;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class WebSocketInterceptor implements HandshakeInterceptor{

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler, Exception arg3) {
		
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
			Map<String, Object> map) throws Exception {
		if(request instanceof ServletServerHttpRequest){
			ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest)request;
			HttpSession session = serverHttpRequest.getServletRequest().getSession();
			if(session!=null){
				map.put("userId",session.getAttribute("userId"));
			}
		}
		return true;
	}

}
