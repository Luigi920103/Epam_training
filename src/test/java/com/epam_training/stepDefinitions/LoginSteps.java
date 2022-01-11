package com.epam_training.stepDefinitions;

import com.epam_training.actors.LoginActors;
import io.cucumber.java.en.*;
import org.junit.Assert;

import static com.epam_training.properties.ReaderOfProperties.properties;
import static com.epam_training.stepDefinitions.BackgroundSteps.loginPage;

public class LoginSteps {

    //Actors and Copies or messages
    private LoginActors actor = new LoginActors();
    //Local variables
    private String localUser, localPassword;
    //Text if we want to use the credentials of a correct user
    private String correctUser = "Correct";

    private BackgroundSteps pageObject;

    @When("it log in with the credentials {string}, {string}")
    public void loginWithCredentials(String user, String password) {
        if (user.equals(correctUser))
            localUser = actor.getCorrectUser();
        else localUser = user;
        if (password.equals(correctUser))
            localPassword = actor.getCorrectPassword();
        else localPassword = password;
        loginPage.signIn(localUser, localPassword);
    }

    @When("it log in with the correct credentials")
    public void loginSuccessfully() {
        loginPage.signIn(actor.getCorrectUser(), actor.getCorrectPassword());
    }

    @Then("it must successfully log in to the web page")
    public void validatingLogin() {
        Assert.assertTrue(properties.getProperty("errorWelcomeMessage")
                , loginPage.getWelcomeMessage().contains("Hello"));

    }

    @Then("the webpage must show {string}")
    public void validatingErrorLoginMessage(String errorMessage) {
        Assert.assertTrue(properties.getProperty("errorMessageValidation_A") + errorMessage + "\"" +
                        properties.getProperty("errorMessageValidation_B")
                , loginPage.getErrorMessage().contains(errorMessage));
    }

    @Then("password field must has attribute type equal to password")
    public void validatingPasswordAttributeField() {
        Assert.assertTrue(properties.getProperty("passwordAttribute")
                , loginPage.validatePasswordElement());

    }
}
