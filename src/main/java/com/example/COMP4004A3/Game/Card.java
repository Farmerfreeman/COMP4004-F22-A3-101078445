package com.example.COMP4004A3.Game;

import com.example.COMP4004A3.Helpers;

public class Card {

    public Card(){
        this.suit = Helpers.randomEnum(Suit.class);
        this.rank = Helpers.randomInt(1, 13);
    }

    public Card(Suit suit, int rank){
        this.suit = suit;
        this.rank = rank;
    }


    private Suit suit;

    //1-13, 1 is ACE, 11 is JACK, 12 is QUEEN, 13 is KING
    private int rank;
}
