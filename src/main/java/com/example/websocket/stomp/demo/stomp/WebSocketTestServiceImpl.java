package com.example.websocket.stomp.demo.stomp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class WebSocketTestServiceImpl implements WebSocketTestService {
	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Object sendStompMsg(WebSocketMsg webSocketMsg) {
        String topicChannel = webSocketMsg.getTopicChannel();
        if (StringUtils.isNotBlank(topicChannel)) {
            topicChannel = "/" + topicChannel;
        }
        
        String message = JSON.toJSONString(webSocketMsg);
        String to = webSocketMsg.getTo();
        
        try {
            if (StringUtils.isNotBlank(to)) {
                simpMessagingTemplate.convertAndSend(topicChannel + "/" + to, message);
            } else {
                simpMessagingTemplate.convertAndSend(topicChannel, message);
            }
            return "";
        } catch (Exception e) {
            return "发送失败";
        }

    }
}
