package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;


@Service
@Lazy
public class WebDriverFactory {

    {
        WebDriverManager.chromedriver().setup();
    }
    public WebDriverFactory() {}

    @Bean(destroyMethod = "quit")
    public WebDriver getWebDriver() {
        final DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);

        final WebDriver chromeDriver = new ChromeDriver(new ChromeOptions().merge(capabilities));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        chromeDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
        return chromeDriver;
    }
}
