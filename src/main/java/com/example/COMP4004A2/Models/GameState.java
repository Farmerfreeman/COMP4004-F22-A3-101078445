package com.example.COMP4004A2.Models;

import com.example.COMP4004A2.Game.*;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private int playerTurn;

    private List<Player> players;

    private List<Card> stockPile;

    private List<Card> discardPile;

    boolean gameOver;

    boolean roundOver;

    public GameState(int playerTurn, List<Player> players, List<Card> stockPile, List<Card> discardPile, boolean gameOver, boolean roundOver){
        this.playerTurn = playerTurn;
        this.gameOver = gameOver;
        this.players = players;
        this.stockPile = stockPile;
        this.discardPile = discardPile;
        this.roundOver = roundOver;
    }
}
