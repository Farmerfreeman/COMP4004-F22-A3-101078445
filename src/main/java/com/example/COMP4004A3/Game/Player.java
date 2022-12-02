package com.example.COMP4004A3.Game;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final WebSocketSession session;

    public Player(final WebSocketSession session){
        this.session = session;
    }

    private String name;

    private List<Card> cards = new ArrayList<>();

    private int score;

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }
}
