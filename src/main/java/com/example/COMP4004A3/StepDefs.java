package com.example.COMP4004A3;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.*;


public class StepDefs {

    WebDriver driver;
    @Before
    public void browserDriverInit(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Given("Open browser and application")
    public void openBrowserAndApp(){
        driver.manage().window().maximize();
        driver.get("http://localhost:8080/");
    }

    @Given("Page is displayed")
    public void verifyIndexPage(){
        String actual = driver.findElement(By.id("greetingHeader")).getText();
        assertEquals("Greetings", actual);
    }

    @When("User enters {string} in field {string}")
    public void userEntersName(String input, String field){
        driver.findElement(By.id(field)).sendKeys(input);
    }

    @When("User submits form {string}")
    public void userSubmitsForm(String button){
        driver.findElement(By.id(button)).click();
    }

    @Then("Server should respond with {string}")
    public void verifyServerResponse(String response){
        String actual = driver.findElement(By.className("messages")).getText();
        assertTrue(actual.contains(response));
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
