package com.epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class ReadMorePage extends BasePage {

    //label
    @FindBy(css = "p[class='stock in-stock']")
    private WebElement stockClass;

    //button
    @FindBy(css = "button[type='submit']")
    private WebElement addToBasket;


    public ReadMorePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    /**
     * Acorde al requerimiento si no esta en stock este debe dehabilitar el boton de agregar al carrito
     * y esta funcion valida que si tiene stock el boton este habilitado de lo contrario que lo
     * deshabilite
     * @return booleano que ayudara a realizar la asersion de si cumple o no bajo lo definido
     */
    public boolean stockValidation() {
        boolean stockClassIsVisible = validationElement(stockClass);
        boolean addButtonIsClickable = validationClickableElement(addToBasket);
        if (stockClassIsVisible) {
            if (addButtonIsClickable) {
                return true;
            } else {
                return false;
            }
        } else {
            //Validacion de cuando no esta en stock
            if (addButtonIsClickable) {
                return false;
            } else {
                return true;
            }
        }
    }
}
