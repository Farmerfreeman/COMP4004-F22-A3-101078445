package com.example.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features="src/main/test/resources",
                glue= {"com.example.cucumber"})

@SpringBootTest
public class RunCucumberTest {
}
