

import com.example.COMP4004A3.Game.Card;
import com.example.COMP4004A3.Game.stockPile;
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


}
