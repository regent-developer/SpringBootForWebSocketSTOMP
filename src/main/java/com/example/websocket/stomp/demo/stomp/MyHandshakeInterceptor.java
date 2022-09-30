package com.example.websocket.stomp.demo.stomp;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.apache.commons.lang3.StringUtils;

public class MyHandshakeInterceptor implements HandshakeInterceptor {
	/**
     * websocket握手之前
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    	System.out.println("--websocket的http连接握手之前--");
        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
        WebSocketUser user = null;
        //获取token认证
        String token = req.getServletRequest().getParameter("token");
        //解析token获取用户信息
        //鉴权，我的方法是，前端把token传过来，解析token，判断正确与否，return true表示通过，false请求不通过。
        //TODO 鉴权设置用户
        if (StringUtils.isNotBlank(token)) {
            user = new WebSocketUser(token);
        }

        //如果token认证失败user为null，返回false拒绝握手
        if (user == null) {
            return false;
        }
        System.out.println("user信息：" + user.getName());
        
        //保存认证用户
        attributes.put("user", user);
        return true;
    }

    /**
     * websocket握手之后
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
    	System.out.println("--websocket的http连接握手之后--");
    }
}
