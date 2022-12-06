package com.example.COMP4004A3.cucumber.config.selenium;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class ConnectionTest extends SeleniumTest {
    @Test
    public void canConnect(){
        this.indexPage.connect();
        assertThat(this.indexPage.hasText("Connection Opened"), is(true));
        assertThat(this.indexPage.hasText("You are now connected with id"), is(true));
    }


}
