package com.epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static com.epam_training.properties.ReaderOfProperties.properties;

public class OrderDetailsPage extends BasePage {

    //labels
    @FindBy(css = ".wc-bacs-bank-details-heading")
    private WebElement bankDetails;

    @FindBy(css = "#page-35 > div > div.woocommerce > h2:nth-child(7)")
    private WebElement orderDetails;

    public OrderDetailsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    /**
     * Metodo que nos ayuda a validar la existencia de el label en pantalla
     * y si el texto corresponde a lo esperado
     *
     * @return booleano que servira para la validacion del assert en los steps
     */
    public boolean bankDetailsOnScreen() {
        if (validationElement(bankDetails)) {
            if (bankDetails.getText().equals(properties.getProperty("OurBankDetails")))
                return true;
            return false;
        }
        return false;
    }

    /**
     * Metodo que nos ayuda a validar la existencia de el label en pantalla
     * y si el texto corresponde a lo esperado
     *
     * @return booleano que servira para la validacion del assert en los steps
     */
    public boolean orderDetailsOnScreen() {
        if (validationElement(orderDetails)) {
            if (orderDetails.getText().equals(properties.getProperty("OrderDetails")))
                return true;
            return false;
        }
        return false;
    }
}
