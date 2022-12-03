

import com.example.COMP4004A3.Game.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    @Test
    @DisplayName("Test StockPile init: Pile has 52 Cards")
    void initStockPile(){
        StockPile p = new StockPile();
        assertEquals(52, p.getCards().size());
    }

    @Test
    @DisplayName("Draw a card from stockpile")
    void drawFromStock(){
        StockPile p = new StockPile();
        Card c = p.drawCard();
        assertTrue(c.getClass() == Card.class);
    }

    @Test
    @DisplayName("Draw a card from stockpile when none are left")
    void drawFromEmptyStock(){
        StockPile p = new StockPile();
        Card c = new Card();
        for (int i = 0; i<=52; i++){
            c = p.drawCard();
        }

        assertEquals(null, c);
    }

    @Test
    @DisplayName("Add a card to the Discard Pile")
    void discardCard(){
        DiscardPile p = new DiscardPile();
        Card c = new Card();
        p.addCard(c);

        assertEquals(c, p.getCards().get(0));
    }

    @Test
    @DisplayName("Add a player to Game (Only used for testing)")
    void addPlayer(){
        Game g = new Game();
        g.init();

        Player p = new Player("p1");
        g.addPlayer(p);

        assertTrue(g.getPlayers().contains(p));
    }

    @Test
    @DisplayName("Play a Card (Player Hand to Discard Pile)")
    void playCard(){

    }
}
