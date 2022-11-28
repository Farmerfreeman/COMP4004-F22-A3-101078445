package com.example.COMP4004A2.Models;

public class ServerMessage {

    private String content;

    private String from;

    private String time;

    public ServerMessage() {
    }

    public ServerMessage(String from, String content, String time) {
        this.content = content;
        this.time = time;
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from;
    }

    public String getTime() {
        return time;
    }
}