package com.example.COMP4004A3.Models;

import com.example.COMP4004A3.Game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

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

    public ClientState(){
        playerTurn = 1;
        players = new ArrayList<Player>();
        stockPile = new ArrayList<Card>();
        discardPile = new ArrayList<Card>();
        gameOver = false;
        gameOver = false;
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
