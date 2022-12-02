package com.example.COMP4004A3;

import com.example.COMP4004A3.Game.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication(scanBasePackages = "com.example.COMP4004A3")
public class Comp4004A3Application extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(Comp4004A3Application.class, args);
	}
}