package com.example.COMP4004A3.Game;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

public class StockPile {

    private List<Card> cards;

    public StockPile(){
        this.init();
    }

    private void init(){
        this.cards = new LinkedList<>();

        for (final Suit suit : Suit.values()){
            for (int i = 1; i <= 13; i++){
                this.cards.add(new Card(suit, i));
            }
        }

        shuffle(this.cards);
    }
    public Card drawCard(){
        if (this.cards.size() >= 1){
            return this.cards.remove(0);
        }
        else return null;
    }

    public List<Card> getCards() {
        return cards;
    }
}
