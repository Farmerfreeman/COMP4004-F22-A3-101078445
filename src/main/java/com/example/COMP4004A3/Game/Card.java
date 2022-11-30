package com.example.COMP4004A3.Game;

public class Card {

    public Card(){
        this.suit = Suit.SPADE;
        this.rank = 1;
    }

    public Card(Suit suit, int rank){
        this.suit = suit;
        this.rank = rank;
    }


    private Suit suit;

    //1-13, 1 is ACE, 11 is JACK, 12 is QUEEN, 13 is KING
    private int rank;
}
