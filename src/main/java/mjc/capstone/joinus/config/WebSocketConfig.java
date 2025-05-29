package mjc.capstone.joinus.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mjc.capstone.joinus.exception.BusinessException;
import mjc.capstone.joinus.exception.ErrorCode;
import mjc.capstone.joinus.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.*;

import java.security.Principal;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${spring.rabbitmq.username}")
    private String rabbitUser;
    @Value("${spring.rabbitmq.password}")
    private String rabbitPw;
    @Value("${spring.rabbitmq.host}")
    private String rabbitHost;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //메시지 구독 url
        config.enableStompBrokerRelay("/exchange")
                .setClientLogin(rabbitUser)
                .setClientPasscode(rabbitPw)
                .setSystemLogin(rabbitUser)
                .setSystemPasscode(rabbitPw)
                .setRelayHost(rabbitHost)
                .setRelayPort(61613);
        //메시지 발행 url
        config.setPathMatcher(new AntPathMatcher("."));
        config.setApplicationDestinationPrefixes("/pub");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                // STOMP CONNECT 프레임일 때만 처리
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authHeader = accessor.getFirstNativeHeader("Authorization");

                    // Authorization header가 존재하지 않을 시에는
                    if(authHeader == null)
                        throw new BusinessException(ErrorCode.INVALID_INPUT, "토큰이 입력되지 않았습니다");

                    if (authHeader.startsWith("Bearer ")) {
                        String token = authHeader.substring(7);
                        jwtTokenProvider.validateToken(token);
                        // 유효성 검사 후 Authentication 생성
                        Authentication authentication = jwtTokenProvider.getAuthentication(token);
                        // STOMP 세션의 Principal 로 설정
                        accessor.setUser(authentication);
                    }
                }
                return message;
            }
        });
    }
}