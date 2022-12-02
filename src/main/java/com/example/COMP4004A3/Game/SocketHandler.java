package com.example.COMP4004A3.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.w3c.dom.Text;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class SocketHandler extends TextWebSocketHandler {

    @Autowired
    private Game game = new Game();

    private boolean acceptingConnections;

    @PostConstruct
    public void init(){this.acceptingConnections = true;}

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception{
        if (this.game.registerPlayer(session)){
            this.sendMessage(session, new TextMessage("SERVER: Welcome!"));
        } else{
            this.sendMessage(session, new TextMessage("Not accepting new players."));
            //TODO: Disconnect player session if server is not accepting new players.
        }
    }

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message){
        System.out.println("Rcd message : " + message);
        broadcastMessage(new TextMessage("Got your message! " + message));
    }

    private void sendMessage(final WebSocketSession user, final TextMessage message){
        try{
            user.sendMessage(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void broadcastMessage(TextMessage message){
        this.game.getPlayers().stream()
                .map(Player::getSession)
                .forEach(session -> {
                    try{
                        session.sendMessage(message);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
    }
}
