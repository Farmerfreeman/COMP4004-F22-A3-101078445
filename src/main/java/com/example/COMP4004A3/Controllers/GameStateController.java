package com.example.COMP4004A3.Controllers;

import com.example.COMP4004A3.Models.ClientState;
import com.example.COMP4004A3.Models.GameState;
import nonapi.io.github.classgraph.json.JSONDeserializer;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameStateController {

    @MessageMapping("/game")
    @SendTo("/topic/game")
    public GameState gameState(String message) throws Exception{
        ClientState clientState = JSONDeserializer.deserializeObject(ClientState.class, message);
        GameState gState = new GameState(clientState.getPlayerTurn(), clientState.getPlayers(), clientState.getStockPile(),
                clientState.getDiscardPile(), clientState.gameOver, clientState.roundOver);
        System.out.println("Server received client state: " + clientState);
        return gState;
    }
}
