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


    private final Suit suit;

    //1-13, 1 is ACE, 11 is JACK, 12 is QUEEN, 13 is KING
    private final int rank;

    public final int getRank() {
        return rank;
    }

    public final Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(final Object card){
        return card instanceof Card &&((Card) card).getRank() == this.rank && ((Card) card).getSuit() == this.suit;
    }

    @Override
    public String toString(){
        switch (suit) {
            case HEART:
                return "H" + rank;

            case DIAMOND:
                return "D" + rank;

            case SPADE:
                return "S" + rank;

            case CLUB:
                return "C" + rank;

        }
        return "AS";
    }
}
