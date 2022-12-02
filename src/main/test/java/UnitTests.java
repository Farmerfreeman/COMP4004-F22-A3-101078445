

import com.example.COMP4004A3.Game.Card;
import com.example.COMP4004A3.Game.discardPile;
import com.example.COMP4004A3.Game.stockPile;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Dictionary;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    @Test
    @DisplayName("Test StockPile init: Pile has 52 Cards")
    void initStockPile(){
        stockPile p = new stockPile();
        assertEquals(52, p.getCards().size());
    }

    @Test
    @DisplayName("Draw a card from stockpile")
    void drawFromStock(){
        stockPile p = new stockPile();
        Card c = p.drawCard();
        assertTrue(c.getClass() == Card.class);
    }

    @Test
    @DisplayName("Draw a card from stockpile when none are left")
    void drawFromEmptyStock(){
        stockPile p = new stockPile();
        Card c = new Card();
        for (int i = 0; i<=52; i++){
            c = p.drawCard();
        }

        assertEquals(null, c);
    }

    @Test
    @DisplayName("Add a card to the Discard Pile")
    void discardCard(){
        discardPile p = new discardPile();
        Card c = new Card();
        p.addCard(c);

        assertEquals(c, p.getCards().get(0));
    }
}
