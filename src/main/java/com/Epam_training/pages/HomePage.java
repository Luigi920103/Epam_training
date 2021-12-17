package com.Epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage extends BasePage {
    //Buttons
    @FindBy(css ="#menu-item-50")
    private WebElement myAccountButton;

    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,30),this);
    }
//Todo Mapear los otros botones del menu principal de la pagina del home para completar el metodo

    /**
     * Metodo que selecciona el correpondiente boton del menu de la pagina
     * principal
     * @param button recibe un string que debe corresponde al nombre del boton
     *               que se desea seleccioanr este nombre solo se usara para el
     *               switch case dado que internamente los locators estan definidos
     *               por id o css
     * @return Retorna un objeto de tipo login page que permitira interactuar
     * con esta nueva pagina y sus elementos
     */
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
