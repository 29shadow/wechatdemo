package com.gtzhou.config.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.gtzhou.wechat.handler.ChatHandler;
import com.gtzhou.wechat.interceptors.WebSocketInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketStompConfig implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatHandler(), "/service/chat").addInterceptors(new WebSocketInterceptor());
	}
	
	
	@Bean
	private ChatHandler chatHandler(){
		return new ChatHandler();
	}
	

}
