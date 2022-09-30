package com.example.websocket.stomp.demo.stomp;

import java.time.LocalDateTime;

public class WebSocketMsg<T> {
	/**
     * 发送方
     */
    private String from;

    /**
     * 接收方
     */
    private String to;

    /**
     * 时间
     */
    private LocalDateTime time = LocalDateTime.now();

    /**
     * 平台来源
     */
    private String platform;
    /**
     * 主题通道
     */
    private String topicChannel;

    /**
     * 信息业务对象
     */
    private T data;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getTopicChannel() {
		return topicChannel;
	}

	public void setTopicChannel(String topicChannel) {
		this.topicChannel = topicChannel;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
