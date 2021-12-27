package com.Epam_training.stepDefinitions;

import com.Epam_training.actors.LoginActors;
import com.Epam_training.hooks.BaseTests;
import com.Epam_training.languages.English;
import com.Epam_training.pages.HomePage;
import com.Epam_training.pages.LoginPage;
import io.cucumber.java.en.*;
import org.junit.Assert;

import static com.Epam_training.hooks.BaseTests.goTo;

public class LoginSteps {
    //Page Objects
    private HomePage home;
    private LoginPage loginPage;
    //Actors and Copies or messages
    private English text = new English();
    private LoginActors actor = new LoginActors();
    //Local variables
    private String localUser, localPassword;

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
        if (user.equals("Correct"))
            localUser = actor.getCorrectUser();
        else localUser = user;
        if (password.equals("Correct"))
            localPassword = actor.getCorrectPassword();
        else localPassword = password;
        loginPage.signIn(localUser, localPassword);
    }

    @When("it log in with the correct credentials")
    public void it_log_in_with_the_correct_credentials() {
        loginPage.signIn(actor.getCorrectUser(), actor.getCorrectPassword());
    }

    @Then("it must successfully log in to the web page")
    public void it_must_successfully_log_in_to_the_web_page() {
        Assert.assertTrue(text.getErrorWelcomeMessage()
                , loginPage.getWelcomeMessage().contains("Hello"));

    }

    @Then("the webpage must show {string}")
    public void the_webpage_must_show(String errorMessage) {
        Assert.assertTrue(text.getErrorMessageValidation_A() + errorMessage + "\"" +
                        text.getErrorMessageValidation_B()
                , loginPage.getErrorMessage().contains(errorMessage));
    }

    @Then("password field must has attribute type equal to password")
    public void password_field_must_has_attribute_type_equal_to_password() {
        Assert.assertTrue(text.getPasswordAttribute()
                , loginPage.validatePasswordElement());

    }
}
