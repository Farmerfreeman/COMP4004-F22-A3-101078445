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

    private List<Card> discardPile;

    private List<Card> stockPile;

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
    }

    public boolean registerPlayer(final WebSocketSession session){
        if (players.size() == 4){
            return false;
        }
        return this.players.putIfAbsent(session.getId(), new Player(session)) == null;
    }

    public Collection<Player> getPlayers(){
        return this.players.values().stream().collect(Collectors.toList());
    }



}
