package com.example.COMP4004A3.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static java.util.Collections.shuffle;

public class DiscardPile {

    private Stack<Card> cards;

    public DiscardPile(){
        cards = new Stack<>();
    }


    public void addCard(Card c){
        cards.push(c);
    }

    public Card getTopCard(){return cards.pop();}

    public List<Card> getCards() {
        return cards;
    }
}
