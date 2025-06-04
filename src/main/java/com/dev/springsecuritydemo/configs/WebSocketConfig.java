package com.dev.springsecuritydemo.configs;


import lombok.RequiredArgsConstructor;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${url.frontend-1}")
    private String frontend_1;
    @Value("${url.frontend-2}")
    private String frontend_2;
    @Value("${url.test}")
    private String test;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); //Subscribe prefix
        registry.setApplicationDestinationPrefixes("/app");//@MessageMapping gelen mesajlar prefix

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //JS bağlanacağı URL
        registry.addEndpoint("/ws-chat").setAllowedOriginPatterns(String.join(",", frontend_1, frontend_2, test)).withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authorizationHeader = accessor.getFirstNativeHeader("Authorization");

                    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                        try {
                            String token = authorizationHeader.substring(7);
                            String username = jwtService.extractUsername(token);
                            if (username != null) {
                                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                                if (jwtService.isTokenValid(token, userDetails)) {
                                    UsernamePasswordAuthenticationToken authenticationToken =
                                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                    accessor.setUser(authenticationToken);
                                } else {
                                    // Token invalid - could throw an exception or reject connection here
                                    System.out.println("JWT token is invalid");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Exception during JWT processing: " + e.getMessage());
                            // Optionally close connection or ignore auth
                        }
                    } else {
                        System.out.println("No valid Authorization header found for WebSocket connection");
                    }
                }
                return message;
            }
        });
    }

}
