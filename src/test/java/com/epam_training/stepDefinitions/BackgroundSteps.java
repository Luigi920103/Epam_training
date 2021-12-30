package com.epam_training.stepDefinitions;

import com.epam_training.hooks.BaseTests;
import com.epam_training.pages.HomePage;
import com.epam_training.pages.LoginPage;
import com.epam_training.pages.ShopPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import static com.epam_training.hooks.BaseTests.goTo;
import static com.epam_training.properties.ReaderOfProperties.properties;

public class BackgroundSteps {

    //Page Objects
    public static HomePage home;
    public static LoginPage loginPage;
    public static ShopPage shopPage;

    @Given("the user navigates to the base page")
    public void openingTheBasePage() {
        goTo(properties.getProperty("baseUrl"));
        home = BaseTests.loadingHomePage();
    }

    @And("the user selects My Account button")
    public void selectingMyAccountButton() {
        loginPage = home.selectMyAccountButton();
    }

    @And("the user selects Shop button")
    public void selectingShopButton() {
        shopPage = home.selectShopButton();
    }

}
