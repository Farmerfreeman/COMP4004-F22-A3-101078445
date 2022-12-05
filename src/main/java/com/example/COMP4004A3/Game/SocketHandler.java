package com.example.COMP4004A3.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.example.COMP4004A3.MessageUtil.*;

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
            this.sendMessage(session, message(Message.PLAYER_CONNECTED, session.getId()).build());
            this.broadcastMessage(session, message(Message.OTHER_PLAYER_CONNECTED, session.getId()).build());

            if (this.game.readyToStart()){
                this.acceptingConnections = false;
                this.broadCastToAllClients(message(Message.START_GAME).build());
            }
        } else{
            this.sendMessage(session, new TextMessage("Not accepting new players."));
            //TODO: Disconnect player session if server is not accepting new players.
        }
    }

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message){
        System.out.println(String.format("Received message from %s : %s", session.getId(), message.getPayload()));

    }

    private void sendMessage(final WebSocketSession user, final TextMessage message){
        try{
            user.sendMessage(message);
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    private void broadcastMessage(WebSocketSession sender, TextMessage message){
        this.game.getPlayers().stream()
                .map(Player::getSession)
                .filter(session -> !session.getId().equals(sender.getId()))
                .forEach(session -> {
                    try{
                        session.sendMessage(message);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
    }

    public void broadCastToAllClients(TextMessage message){
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
