package com.gtzhou.wechat.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.gtzhou.wechat.handler.ChatHandler;

@Controller
public class WebSocketController {
	
	@Autowired
	ChatHandler handler;
	
	@RequestMapping("/login/{userId}")
    public @ResponseBody String login(HttpSession session, @PathVariable("userId") Integer userId) {
        System.out.println("登录接口,userId="+userId);
        session.setAttribute("userId", userId);
        System.out.println(session.getAttribute("userId"));

        return "success";
    }
	
	@MessageMapping("/webSocket/sendMsg")
	@SendTo("/topic/subscribeTest")
    public @ResponseBody String sendMessage() {
        boolean hasSend = handler.sendMessageToUser(4, new TextMessage("发送一条小xi"));
        System.out.println(hasSend);
        return "message";
    }

}
