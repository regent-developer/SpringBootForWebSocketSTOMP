package com.example.websocket.stomp.demo.stomp;

import java.security.Principal;

public class WebSocketUser implements Principal {
	/**
     * 用户信息
     */
    private final String name;

    public WebSocketUser(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }
}
