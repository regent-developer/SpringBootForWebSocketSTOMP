package com.example.websocket.stomp.demo.stomp;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

public class MyHandshakeHandler extends DefaultHandshakeHandler {
	/**
	 * 生成自定义Principal 
	 */
	@Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
		System.out.println("--websocket的http连接握手之后--");
		
        //设置认证用户
        return (Principal) attributes.get("user");
    }
}
