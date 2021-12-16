package com.Epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage {

    @FindBy(css ="#username")
    private WebElement userTextBox;

    @FindBy(css ="#password")
    private WebElement passwordTextBox;

    @FindBy(css ="p:nth-child(3) > input.woocommerce-Button.button")
    private WebElement submitButton;

    @FindBy(css =".woocommerce-MyAccount-content")
    private WebElement welcomeMessage;

    @FindBy(css =".woocommerce-error")
    private WebElement errorMessage;


    public LoginPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,30),this);
    }

    public void signIn(String user, String password){
        userTextBox.sendKeys(user);
        passwordTextBox.sendKeys(password);
        submitButton.click();
    }

    public String getWelcomeMessage(){
        return welcomeMessage.getText();
    }

    public String getErrorMessage(){
        return errorMessage.getText();
    }


}