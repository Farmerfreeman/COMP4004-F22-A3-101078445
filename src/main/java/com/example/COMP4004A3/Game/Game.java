package com.example.COMP4004A3.Game;

import com.example.COMP4004A3.Models.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Game {



    private Map<String, Player> players;

    private DiscardPile discardPile;

    private StockPile stockPile;

    private final int MAX_PLAYERS = 4;
    private State state;

    public enum State{
        CONNECTING,
        PLAYING
    }

    public Game(){
        init();
    }
    public void init(){
        this.players = new HashMap<>();
        this.state = State.CONNECTING;
        this.discardPile = new DiscardPile();
        this.stockPile = new StockPile();
    }

    /**
     *
     * @param p The player to be added
     * Function adds player to players map, only to be used for non-networked unit testing.
     */
    public void addPlayer(Player p){
        players.putIfAbsent(p.getName(), p);
    }

    public boolean registerPlayer(final WebSocketSession session){
        if (players.size() == 4){
            return false;
        }
        return this.players.putIfAbsent(session.getId(), new Player(session)) == null;
    }

    public void playCard(Player player, Card card){
        if(!player.getCards().contains(card)){
            System.out.println("Card not played: Player does not posses card.");
            return;
        }
        discardPile.addCard(player.removeCard(card));
    }




    //Networking Functions

    /**
     * Get the player for the given session.
     *
     * @param session the session.
     * @return the player.
     */
    public Player getPlayerFor(final WebSocketSession session) {
        return this.players.get(session.getId());
    }

    public Collection<Player> getPlayers(){
        return this.players.values().stream().collect(Collectors.toList());
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }
}
