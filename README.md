# SpringBoot建立WebSocket连接(STOMP)主动推送消息

## STOMP协议介绍
STOMP，Streaming Text Orientated Message Protocol，是流文本定向消息协议，是一种为MOM(Message Oriented Middleware，面向消息的中间件)设计的简单文本协议。
它提供了一个可互操作的连接格式，允许STOMP客户端与任意STOMP消息代理(Broker)进行交互，类似于OpenWire(一种二进制协议)。  

STOMP协议工作于TCP协议之上，使用了下列命令：

SEND 发送  
SUBSCRIBE 订阅  
UNSUBSCRIBE 退订  
BEGIN 开始  
COMMIT 提交  
ABORT 取消  
ACK 确认  
DISCONNECT 断开  

  
* 发送消息
```
SEND 
destination:/app/xxx 
content-type:application/json 
content-length:44 
 
{"xxx":"abcd"}
```

* 订阅消息
```
SUBSCRIBE 
id:xx-1 
destination:/app/xxx
```

* 广播
```
MESSAGE 
message-id:xxx-1 
subscription:xx-1 
destination:/topic/xxx 
{"message":"xxx"}
```

## springboot使用STOMP消息步骤

* 添加pom文件依赖
* java方式配置websocket stomp
* 消息实体类
* 编写控制层
* 编写客户端
