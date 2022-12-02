package com.example.COMP4004A3.Game;

import com.example.COMP4004A3.Models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Game {

    private Map<String, Player> players;

    private List<Card> discardPile;

    private List<Card> stockPile;

    private State state;

    public enum State{
        CONNECTING,
        PLAYING
    }

    public void init(){
        this.players = new HashMap<>();
        this.state = State.CONNECTING;
    }



}
