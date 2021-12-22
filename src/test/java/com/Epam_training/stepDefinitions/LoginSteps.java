package com.Epam_training.stepDefinitions;

import com.Epam_training.hooks.BaseTests;
import com.Epam_training.pages.HomePage;
import com.Epam_training.pages.LoginPage;
import io.cucumber.java.en.*;
import org.junit.Assert;

import static com.Epam_training.hooks.BaseTests.goTo;

public class LoginSteps {
    private HomePage home;
    private LoginPage loginPage;

    @Given("the user navigates to {string}")
    public void the_user_navigates_to(String url) {
        goTo(url);
        home = BaseTests.loadingHomePage();
    }

    @And("the user selects {string} button")
    public void the_user_selects_button(String button) {
        loginPage = home.selectButtonOnMainMenu(button);
    }

    @When("it log in with the credentials {string}, {string}")
    public void it_log_in_with_the_credentials(String user, String password) {
        loginPage.signIn(user, password);
    }

    @Then("it must successfully log in to the web page")
    public void it_must_successfully_log_in_to_the_web_page() {
        Assert.assertTrue("The welcome message has to have the word Hello, however, " +
                        "the message doesn't have that word or it doesn't show it"
                , loginPage.getWelcomeMessage().contains("Hello"));

    }

    @Then("the webpage must show {string}")
    public void the_webpage_must_show(String errorMessage) {
        Assert.assertTrue("The error message has to have the text \"" + errorMessage + "\"" +
                        " and it doesn't have that message or it doesn't show it "
                , loginPage.getErrorMessage().contains(errorMessage));
        System.out.println("Mensaje de error" + errorMessage);
    }

    @Then("password field must has attribute type equal to password")
    public void password_field_must_has_attribute_type_equal_to_password() {
        Assert.assertTrue("The password field has the attribute type equal to password"
                , loginPage.validatePasswordElement());

    }
}
