package com.example.COMP4004A3.Game;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

public class discardPile {

    private List<Card> cards;

    public discardPile(){
        cards = new LinkedList<>();
    }


    public void addCard(Card c){
        cards.add(c);
    }

    public List<Card> getCards() {
        return cards;
    }
}
