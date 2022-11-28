package com.example.COMP4004A2.Models;

import com.example.COMP4004A2.Game.*;

import java.util.ArrayList;
import java.util.List;

public class ClientState {
    private int playerTurn;

    private List<Player> players;

    private List<Card> stockPile;

    private List<Card> discardPile;

    public boolean gameOver;

    public boolean roundOver;

    public ClientState(int playerTurn, List<Player> players, List<Card> stockPile, List<Card> discardPile, boolean gameOver, boolean roundOver){
        this.playerTurn = playerTurn;
        this.gameOver = gameOver;
        this.players = players;
        this.stockPile = stockPile;
        this.discardPile = discardPile;
        this.roundOver = roundOver;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getStockPile() {
        return stockPile;
    }

    public List<Card> getDiscardPile() {
        return discardPile;
    }
}
