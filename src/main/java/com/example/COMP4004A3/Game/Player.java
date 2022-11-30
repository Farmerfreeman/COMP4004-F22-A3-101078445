package com.example.COMP4004A3.Game;

import java.util.ArrayList;
import java.util.List;

public class Player {

    public Player(){
        this.name = "Temp";
        this.cards = null;
        this.score = 0;
    }

    public Player(String name){
        this.name = name;
    }

    private String name;

    private List<Card> cards = new ArrayList<>();

    private int score;
}
