package com.epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage extends BasePage {
    //Buttons
    @FindBy(css = "#menu-item-50")
    private WebElement myAccountButton;

    @FindBy(css = "#menu-item-40")
    private WebElement shopButton;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    /**
     * Metodo base que nos permite seleccionar o dar click al boton
     * correspondiente y nos retorna un page object del tipo
     * correspondiente que nos permitira seguir interactuando con
     * la siguiente pagina
     *
     * @return page object de la pagina de login
     */
    public LoginPage selectMyAccountButton() {
        myAccountButton.click();
        return new LoginPage(getDriver());
    }

    /**
     * Metodo base que nos permite seleccionar o dar click al boton
     * correspondiente y nos retorna un page object del tipo
     * correspondiente que nos permitira seguir interactuando con
     * la siguiente pagina
     *
     * @return page object de la pagina de Shop
     */
    public ShopPage selectShopButton() {
        shopButton.click();
        return new ShopPage(getDriver());
    }
}
