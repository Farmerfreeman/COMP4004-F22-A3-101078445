package com.example.COMP4004A3.Game;

import org.apache.commons.io.LineIterator;

import java.nio.channels.Pipe;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.shuffle;

public class stockPile {

    private List<Card> cards;

    public stockPile(){
        this.initPile();
    }

    private void initPile(){
        this.cards = new LinkedList<>();

        for (final Suit suit : Suit.values()){
            for (int i = 1; i <= 13; i++){
                this.cards.add(new Card(suit, i));
            }
        }

        shuffle(this.cards);
    }


    public List<Card> getCards() {
        return cards;
    }
}
