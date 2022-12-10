package com.example.COMP4004A3.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static com.example.COMP4004A3.MessageUtil.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class SocketHandler extends TextWebSocketHandler {

    @Autowired
    private Game game;

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
                this.startGame();
            }
        } else{
            this.sendMessage(session, new TextMessage("Not accepting new players."));
            //TODO: Disconnect player session if server is not accepting new players.
        }
    }

    private void startGame(){
        this.game.dealHands();
        this.updateCards();
        this.updateDiscard();
        this.updateTurns();

        //TODO: Start the game for real.
    }

    private void addCardToClient(Player p, Card c){
        sendMessage(p.getSession(), message(Message.ADD_PLAYER_CARD, c.toHTMLString()).build());
    }

    private void updateCards(){
        Map<Player, List<TextMessage>> messages = game.buildHandMessages();
        messages.forEach((player, message) ->
                message.forEach(toSend -> this.sendMessage(player.getSession(), toSend)));
    }

    private void updateDiscard(){
        TextMessage t = game.buildDiscardUpdateMessage();
        broadCastToAllClients(t);
    }

    private void updateTurns(){
        TextMessage t = game.buildTurnUpdateMessage();
        broadCastToAllClients(t);
    }

    private void updatePoints(){
        Map<Player, List<TextMessage>> messages = game.buildPointMessages();
        messages.forEach((player, message) ->
                message.forEach(toSend -> this.broadCastToAllClients(toSend)));
    }

    @Override
    public void handleTextMessage(final WebSocketSession session, final TextMessage message){
        System.out.println(String.format("Received message from %s : %s", session.getId(), message.getPayload()));
        String[] contents = message.getPayload().split("\\|");
        Player p = game.getPlayerFor(session);

        switch (contents[0]){
            case "PLAYED_CARD":

                Card c = p.getCards().get(Integer.parseInt(contents[1]) - 1);
                game.playCard(p, c);
                broadcastMessage(session, message(Message.PLAYER_PLAYED_CARD, session.getId(), c).build());
                updateDiscard();
                //TODO: This shouldn't update turns forever, sometimes you can play multiple times.
                updateTurns();
                break;
            case "DREW_CARD":
                Card drawCard = game.getStockPile().drawCard();
                p.addCard(drawCard);
                this.addCardToClient(p, drawCard);
                //TODO: This shouldn't update turns forever, sometimes you can draw multiple times.
                updateTurns();
                break;
            case "ROUND_OVER":
                this.game.endRound();
                this.updatePoints();
                this.startGame();

        }

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
