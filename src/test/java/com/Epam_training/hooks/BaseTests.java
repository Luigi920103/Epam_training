package com.Epam_training.hooks;

import com.Epam_training.pages.HomePage;
import io.cucumber.java.After;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTests {
    public static WebDriver driver;

    @Before
    public void openNavigator(){
    WebDriverManager.chromedriver().setup();
    driver=(new ChromeDriver());
    cleanNavigator();
        System.out.println("Ejecutando before");
    }

    @After
    public void closeNavigator(){
        driver.quit();
    }

    public void cleanNavigator() {
        String originalHandle = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
    }
    public static void goTo(String url){
        driver.get(url);
    }

    @AfterStep
    public void addScreenshot(Scenario scenario){

        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "image");

    }


    public static HomePage loadingHomePage(){
        return new HomePage(driver);
    }

}