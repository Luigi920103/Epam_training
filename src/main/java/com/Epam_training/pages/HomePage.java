package com.Epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage extends BasePage {

    @FindBy(css ="#menu-item-50")
    private WebElement myAccountButton;

    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,30),this);
    }

    public LoginPage selectButtonOnMainMenu(String button){

        switch(button) {
            case "My Account":
                myAccountButton.click();
                return new LoginPage(getDriver());
            case "Shop":
                // code block
                //return new LoginPage(getDriver());
            default:
                // code block
                return new LoginPage(getDriver());
        }

    }


}
