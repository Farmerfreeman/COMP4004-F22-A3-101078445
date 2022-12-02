package com.example.COMP4004A3;

import com.example.COMP4004A3.Game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.swing.*;

@EnableWebSocket
@SpringBootApplication(scanBasePackages = "com.example.COMP4004A3")
public class Comp4004A3Application extends SpringBootServletInitializer implements WebSocketConfigurer {

	@Autowired
	private SocketHandler socketHandler;
	@Override
	public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry){
		registry.addHandler(this.socketHandler, "/game").withSockJS();
	}

	public static void main(String[] args) {

		SpringApplication.run(Comp4004A3Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder){
		return builder.sources(Comp4004A3Application.class);
	}
}