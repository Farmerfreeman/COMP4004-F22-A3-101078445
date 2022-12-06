package com.example.cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class StepDefs {

    List<WebDriver> Drivers = new ArrayList<>();

    @Before
    public void browserDriverInit(){
        WebDriverManager.chromedriver().setup();
        Drivers.add(new ChromeDriver());
        Drivers.add(new ChromeDriver());
        Drivers.add(new ChromeDriver());
        Drivers.add(new ChromeDriver());
    }

    @Given("Open browser and application")
    public void openBrowserAndApp(){
        for (WebDriver d : Drivers) {
            d.manage().window().maximize();
            d.get("http://localhost:8080");

        }
    }

    @Given("Page is displayed")
    public void verifyIndexPage(){
        for (WebDriver d: Drivers){
            String actual = d.findElement(By.id("discardPileText")).getText();
            assertEquals("Discard and Stock Piles", actual);
        }
    }

    @When("User connects")
    public void userConnects(){
        for (WebDriver d: Drivers){
            d.findElement(By.id("connect")).click();
        }
    }


    @Then("Server should respond with {string}")
    public void verifyServerResponse(String response){
        for (WebDriver d : Drivers){
            String consoleText = "";

            WebElement console = d.findElement(By.id("console"));
            List<WebElement> children = console.findElements(By.xpath("./child::*"));

            for (WebElement c : children){
                consoleText += c.getText();
            }

            assertTrue(consoleText.contains(response));
        }
    }

    //Networking Step Defs
    @When("WAIT FOR SERVER")
    public void waitForServer(){
        try{
            Thread.sleep(500);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}
