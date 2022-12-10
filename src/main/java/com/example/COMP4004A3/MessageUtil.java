package com.example.COMP4004A3;

public class MessageUtil {

    public enum Message{
        PLAYER_CONNECTED("|CONNECTED|You are now connected with id %s."),
        OTHER_PLAYER_CONNECTED("|OTHER_CONNECTED|%s has connected to the game.|%s"),
        START_GAME("|START|The game has started! Please wait your turn."),
        ADD_PLAYER_CARD("|ADD_CARD|%s"),
        DEALING_CARDS("|DEALING+CARDS|Updated cards."),
        PLAYER_PLAYED_CARD("|PLAYED_CARD|%s|%s"), //Player, Card
        UPDATE_DISCARD("|DISCARD|%s"),
        UPDATE_STOCK("|STOCK|%s"),
        PLAYER_TURN("|PLAYER_TURN|%s"),
        CANT_PLAY("|CANT_PLAY|You have no playable cards! You'll have to draw up to three cards until you can play."),
        WINNER("|WINNER|%s has won the game!"),
        ROUND_OVER("|ROUND_OVER|That round has concluded."),
        PLAYER_SCORED("|PLAYER_SCORED|%s|%d"), //player, score
        RESET("|RESET|A new round is beginning."),
        GAME_OVER("|GAME_OVER|The game is over, sessions will now be disconnected.");
        private final String content;

        Message(String content){
            this.content = content;
        }

        public String getContent(){
            return this.content;
        }
    }

    public static MessageBuilder message(Message message, final Object... formatArgs){
        return new MessageBuilder(message.getContent())
                .withFormat(formatArgs)
                .withSender("Server");
    }



}
