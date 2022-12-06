package com.example.COMP4004A3.Game;

public enum Suit {
    HEART("hearts"),
    SPADE("spades"),
    DIAMOND("diams"),
    CLUB("clubs");

    private String html;

    Suit(String html) {this.html = html;}

    public String getHtml(){
        return html;
    }
}
