

import com.example.COMP4004A3.Game.*;
import org.checkerframework.checker.units.qual.C;
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
        Game g = new Game();
        g.init();

        Player p = new Player("p1");
        Card c = new Card(Suit.SPADE, 8);
        p.addCard(c);
        g.addPlayer(p);

        g.playCard(p, p.getCards().get(0));

        assertEquals(c, g.getDiscardPile().getTopCard());
    }

    @Test
    @DisplayName("CanPlay() function tests if a card can be played on a given discard pile")
    void canPlay(){
        Game g = new Game();
        g.init();

        Player p = new Player("p1");
        Card c = new Card(Suit.SPADE, 8);
        Card c2 = new Card(Suit.SPADE, 11);
        Card c3 = new Card(Suit.HEART, 3);

        g.addPlayer(p);

        p.addCard(c);
        p.addCard(c2);
        p.addCard(c3);

        assertTrue(g.canPlay(c));
        g.playCard(p, c);

        assertTrue(g.canPlay(c2));
        g.playCard(p, c2);

        assertTrue(!g.canPlay(c3));
        g.playCard(p, c3);

        assertTrue(!p.getCards().isEmpty());
    }

    @Test
    @DisplayName("Deals initial hands to all players.")
    void dealHands(){
        Game g = new Game();

        g.init();

        Player p = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        Player p4 = new Player("p4");

        g.addPlayer(p);
        g.addPlayer(p2);
        g.addPlayer(p3);
        g.addPlayer(p4);

        g.dealHands();

        assertEquals(5, p.getCards().size());
        assertEquals(5, p2.getCards().size());
        assertEquals(5, p3.getCards().size());
        assertEquals(5, p4.getCards().size());

    }

    @Test
    @DisplayName("initialize player order and get all four players in the order they were added")
    void initOrdering(){
        Game g = new Game();

        g.init();

        Player p = new Player("p1");
        Player p2 = new Player("p2");
        Player p3 = new Player("p3");
        Player p4 = new Player("p4");

        g.addPlayer(p2);
        g.addPlayer(p4);
        g.addPlayer(p3);
        g.addPlayer(p);

        assertEquals(p2, g.getNextPlayer());
        assertEquals(p4, g.getNextPlayer());
        assertEquals(p3, g.getNextPlayer());
        assertEquals(p, g.getNextPlayer());
        assertEquals(p2, g.getNextPlayer());
    }


}
