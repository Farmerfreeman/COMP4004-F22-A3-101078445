package com.example.COMP4004A3.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;

@Component
public class SocketHandler extends TextWebSocketHandler {

    @Autowired
    private Game game;

    private boolean acceptingConnections;

    @PostConstruct
    public void init(){this.acceptingConnections = true;}
}
