package com.example.COMP4004A3.cucumber.config.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

public class ConnectionTest extends SeleniumTest {
    @Test
    public void canConnect(){
        this.indexPage.connect();
        assertThat(this.indexPage.hasText("Connection Opened"), is(true));
        assertThat(this.indexPage.hasText("You are now connected with id"), is(true));
    }

    @Test
    public void canMultiplePlayersConnect(){


        final WebDriver player1 = this.quickConnectSecondUser();

        this.delay(1);

        final WebDriver player2 = this.quickConnectSecondUser();

        this.delay(1);

        final WebDriver player3 = this.quickConnectSecondUser();

        this.delay(1);

        final WebDriver player4 = this.quickConnectSecondUser();

        this.delay(1);

        String consoleText = "";

        WebElement console = player1.findElement(By.id("console"));
        List<WebElement> children = console.findElements(By.xpath("./child::*"));

        for (WebElement c : children){
            consoleText += c.getText();
        }

        assertTrue(consoleText.contains("The game has started! Please wait your turn"));
    }
}
