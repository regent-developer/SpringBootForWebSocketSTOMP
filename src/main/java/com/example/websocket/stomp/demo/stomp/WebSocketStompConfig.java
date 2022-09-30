package com.example.websocket.stomp.demo.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * websocket stomp协议配置类
 *
 */
@Configuration
//此注解开使用STOMP协议来传输基于消息代理的消息，此时可以在@Controller类中使用@MessageMapping
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

	/**
	 * 添加这个Endpoint，这样在网页中就可以通过websocket连接上服务,可配置websocket的服务地址,并且可以指定是否使用socketjs
	 * @param registry
	 */
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /*
         * 1. 将 /stomp/websocketJs路径注册为STOMP的端点，用户连接了这个端点后就可以进行websocket通讯，支持socketJs
         * 2. setAllowedOriginPatterns("*")表示可以跨域
         * 3. addInterceptors添加自定义拦截器
         * 4. addInterceptors添加拦截处理，这里MyPrincipalHandshakeHandler封装的认证用户信息
         * 5. withSockJS()表示支持socktJS访问
         */
        //配置客户端连接地址
        registry.addEndpoint("/stomp/websocketJS")
        		.setAllowedOriginPatterns("*")
        		.addInterceptors(new MyHandshakeInterceptor())
        		.setHandshakeHandler(new MyHandshakeHandler())
        		.withSockJS();
        
        /*
         * 添加多个端点
         * 它的实现类是WebMvcStompEndpointRegistry ，
         * addEndpoint是添加到WebMvcStompWebSocketEndpointRegistration的集合中，
         * 所以可以添加多个端点
         */
        registry.addEndpoint("/stomp/websocket");
    }
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 自定义调度器，用于控制心跳线程
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 线程池线程数，心跳连接开线程
        taskScheduler.setPoolSize(1);
        // 线程名前缀
        taskScheduler.setThreadNamePrefix("websocket-heartbeat-thread-");
        // 初始化
        taskScheduler.initialize();
        // 设置广播节点
        registry.enableSimpleBroker("/ad", "/device", "/pay", "/data", "/warn", "/alone")
        		.setHeartbeatValue(new long[]{10000, 10000})
                .setTaskScheduler(taskScheduler);
        // 客户端向服务端发送消息需有/app前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 指定用户发送（一对一）消息需有 /user前缀
        registry.setUserDestinationPrefix("/user");
    }

	/**
     * 配置发送与接收的消息参数，可以指定消息字节大小，缓存大小，发送超时时间
     *
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        /*
         * 1. setMessageSizeLimit 设置消息缓存的字节数大小（字节）
         * 2. setSendBufferSizeLimit 设置websocket会话时，缓存的大小（字节）
         * 3. setSendTimeLimit 设置消息发送会话超时时间（毫秒）
         */
        registration.setMessageSizeLimit(10240)
                	.setSendBufferSizeLimit(10240)
                	.setSendTimeLimit(10000);
    }

    /**
     * 配置客户端入站通道拦截器
     * 设置输入消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        /*
         * 配置消息线程池
         * 1. corePoolSize 配置核心线程池，当线程数小于此配置时，不管线程中有无空闲的线程，都会产生新线程处理任务
         * 2. maxPoolSize 配置线程池最大数，当线程池数等于此配置时，不会产生新线程
         * 3. keepAliveSeconds 线程池维护线程所允许的空闲时间，单位秒
         */
        registration.taskExecutor()
        			.corePoolSize(10)
        			.maxPoolSize(20)
        			.keepAliveSeconds(60);

        registration.interceptors(new WebSocketUserInterceptor());
    }

    /**
     * 设置输出消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
     *
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor()
        			.corePoolSize(10)
                	.maxPoolSize(20)
                	.keepAliveSeconds(60);
        //registration.interceptors(new WebSocketUserInterceptor());
    }

}
