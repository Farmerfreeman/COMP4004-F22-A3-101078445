package com.example.COMP4004A3.Game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;

import com.example.COMP4004A3.MessageUtil;
import com.example.COMP4004A3.MessageBuilder;

import javax.annotation.PostConstruct;

import static com.example.COMP4004A3.MessageUtil.message;

@Component
public class Game {



    private Map<String, Player> players;

    private List<Player> order;

    private int currPlayer;

    private DiscardPile discardPile;

    private StockPile stockPile;

    private final int MAX_PLAYERS = 4;
    private State state;

    public enum State{
        CONNECTING,
        PLAYING
    }

    @PostConstruct
    public void init(){
        this.players = new HashMap<>();
        this.state = State.CONNECTING;
        this.discardPile = new DiscardPile();
        this.stockPile = new StockPile();

        this.order = new ArrayList<>();
        this.currPlayer = 0;

        discardPile.addCard(stockPile.drawCard());
    }

    /**
     *
     * @param p The player to be added
     * Function adds player to players map, only to be used for non-networked unit testing.
     */
    public void addPlayer(Player p){

        players.putIfAbsent(p.getName(), p);
        order.add(p);
    }

    public boolean registerPlayer(final WebSocketSession session){
        if (players.size() == 4){
            return false;
        }
        Player p = new Player(session);
        order.add(p);

        return this.players.putIfAbsent(session.getId(), p) == null;
    }

    public void playCard(Player player, Card card){
        if(!player.getCards().contains(card)){
            System.out.println("Card not played: Player does not posses card.");
            return;
        }
        if(canPlay(card)){
            discardPile.addCard(player.removeCard(card));
        }
        else{
            System.out.println("Card not played: Card can't be played on this discard pile.");
        }

    }

    public void dealHands(){
        this.state = State.PLAYING;
        players.forEach((uid, player) ->{
            for (int i = 0; i < 5; i++){
                Card card = stockPile.drawCard();
                player.getCards().add(card);
            }
        });
    }

    public Map<Player, List<TextMessage>> buildHandMessages(){
        final Map<Player, List<TextMessage>> messages = new HashMap<>();


        for (final Player player : this.getPlayers()) {

            messages.putIfAbsent(player, new ArrayList<>());
            final List<TextMessage> playerMessages = messages.get(player);

            // Step 0, build the message that we're dealing the cards.
            playerMessages.add(message(MessageUtil.Message.DEALING_CARDS).build());

            // Step 1, build the messages to send the player their cards.
            player.getCards()
                    .forEach(card -> {
                        playerMessages.add(message(MessageUtil.Message.ADD_PLAYER_CARD,
                                card.toHTMLString()).build());

                    });

        }
        return messages;
    }

    public Map<Player, List<TextMessage>> buildPointMessages(){
        final Map<Player, List<TextMessage>> messages = new HashMap<>();


        for (final Player player : this.getPlayers()) {

            messages.putIfAbsent(player, new ArrayList<>());
            final List<TextMessage> playerMessages = messages.get(player);


            playerMessages.add(message(MessageUtil.Message.PLAYER_SCORED,
                    player.getSession().getId(), player.getScore()).build());



        }
        return messages;
    }

    public TextMessage buildDiscardUpdateMessage() {
        return(message(MessageUtil.Message.UPDATE_DISCARD,
                discardPile.peekTopCard().toHTMLString()).build());
    }

    public TextMessage buildTurnUpdateMessage(){
        return(message(MessageUtil.Message.PLAYER_TURN,
                this.getNextPlayer().getSession().getId()).build());
    }




    public boolean canPlay(Card c){
        if(c.getRank() == 8) return true;
        if (discardPile.peekTopCard().getSuit().equals(c.getSuit()) ||
                discardPile.peekTopCard().getRank() == c.getRank()){
            return true;
        }
        else return false;
    }

    public boolean readyToStart(){
        return this.players.size() == MAX_PLAYERS;
    }

    public Player getNextPlayer(){
        if (currPlayer == MAX_PLAYERS){ currPlayer = 0;}
        Player p = order.get(currPlayer);
        currPlayer++;
        return p;
    }

    public void endRound(){
        for (Player p : this.getPlayers()){
            p.tallyScore();
            p.setCards(new ArrayList<Card>());
        }

        this.discardPile = new DiscardPile();
        this.stockPile = new StockPile();

        this.currPlayer = 0;

        discardPile.addCard(stockPile.drawCard());

    }




    //Networking Functions

    /**
     * Get the player for the given session.
     *
     * @param session the session.
     * @return the player.
     */
    public Player getPlayerFor(final WebSocketSession session) {
        return this.players.get(session.getId());
    }

    public Collection<Player> getPlayers(){
        return this.players.values().stream().collect(Collectors.toList());
    }

    public DiscardPile getDiscardPile() {
        return discardPile;
    }

    public StockPile getStockPile(){
        return stockPile;
    }
}
