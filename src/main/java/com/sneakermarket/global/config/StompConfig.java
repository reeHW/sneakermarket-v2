package com.sneakermarket.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    /* STOMP를 사용하면 웹 소켓만 사용할 때와 다르게
     * 하나의 연결주소마다 핸들러 클래스를 따로 구현할 필요없이 Controller 방식으로 간편하게 사용할 수 있다.
     * 엔드 포인트를 등록하기 위해 resisterStompEndpoints 메서드를 override 한다.
     * */

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // stomp 접속 주소 url
        registry.addEndpoint("/api/chat/stomp-chatting") // 연결될 엔드 포인트
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .withSockJS(); // SocketJS를 연결한다는 설정
    }

    /**
     * STOMP에서 사용하는 메시지 브로커 설정
     * enableSimpleBroker()를 사용해서 /api/chat/receiver 가 prefix로 붙은 destination의 클라이언트에게 메시지리를 보낼 수 있도록 Simple Broker를 등록한다.
     * setApplicationestinationPrefiexs()를 사용해서 /api/chat/send가 prefix로 붙은 메시지들은 @MessageMapping이 붙은 method로 바운드된다.
     */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * enableSimpleBroker : 내장 메시지 브로커를 사용하기 위한 메소드
         * 파라미터로 지정한 prefix가 붙은 메시지를 발행할 경우, 메시지 브로커가 이를 처리하게 된다.
         * 메시지를 구독하는 요청 url => 즉, 메시지 받을 때
         */
        registry.enableSimpleBroker("/api/chat/receive");

        /**
         * 메시지 핸들러로 라우팅되는 prefix를 파라미터로 지정할 수 있다.
         * 메시지 가공 처리가 필요한 경우, 가공 핸들러로 메시지를 라우팅 되도록 하는 설정
         * 메시지를 발행하는 요청 url => 즉, 메시지 보낼 때
         */
        registry.setApplicationDestinationPrefixes("/api/chat/send"); // 클라이언트로부터 메시지를 받을 api의 prefix를 설정
    }
}
