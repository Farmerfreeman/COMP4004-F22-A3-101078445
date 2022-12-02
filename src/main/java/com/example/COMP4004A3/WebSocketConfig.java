package com.example.COMP4004A3;

import com.example.COMP4004A3.Game.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SocketHandler socketHandler;
    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry){
        registry.addHandler(this.socketHandler, "/game").withSockJS();
    }
}