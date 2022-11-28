package com.example.COMP4004A2;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ServerMessageController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ServerMessage serverMessage(Message message) throws Exception {

        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new ServerMessage(HtmlUtils.htmlEscape(message.getName()), HtmlUtils.htmlEscape(message.getContent()), time);
    }

}