package com.example.websocket.stomp.demo.stomp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web/socket/stomp")
public class WebSocketStompTestController {
	@Autowired
    private WebSocketTestService webSocketService;

    /**
     * 发送信息 stomp
     *
     * @param webSocketMsgVo 信息对象vo
     * @return 统一出参
     */
    @PostMapping("/sendStompMsg")
    @MessageMapping("/sendStompMsg")
    public Object sendStompMsg(@RequestBody WebSocketMsg webSocketMsg) {
    	System.out.println("--发送信息--");
        return webSocketService.sendStompMsg(webSocketMsg);
    }
}
