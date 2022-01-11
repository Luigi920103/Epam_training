package com.epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static com.epam_training.properties.ReaderOfProperties.properties;

public class BasketPage extends BasePage {

    //buttons
    @FindBy(css = ".wc-proceed-to-checkout a")
    private WebElement checkoutButton;

    //labels
    @FindBy(css = ".cart-subtotal span[class='woocommerce-Price-amount amount']")
    private WebElement subtotal;

    @FindBy(css = ".order-total span[class='woocommerce-Price-amount amount']")
    private WebElement total;

    @FindBy(css = "td[data-title='Roaming Tax'] span[class='woocommerce-Price-amount amount']")
    private WebElement tax;

    private double subTotalVariable, totalVariable, taxVariable;

    public BasketPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    public boolean subtotalLowerThanTotal() {
        subTotalVariable = Double.parseDouble((subtotal.getText().substring(1)).replace(",", ""));
        totalVariable = Double.parseDouble((total.getText().substring(1)).replace(",", ""));
        return subTotalVariable < totalVariable;
    }

    public boolean taxValidation(String condition) {
        subTotalVariable = Double.parseDouble((subtotal.getText().substring(1)).replace(",", ""));
        taxVariable = Double.parseDouble((tax.getText().substring(1)).replace(",", ""));
        System.out.println(subTotalVariable + " tax" + taxVariable);
        if (condition.equals("indian")) {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<< " + subTotalVariable * Double.parseDouble(properties.getProperty("indianTax")));
            return (subTotalVariable * Double.parseDouble(properties.getProperty("indianTax"))) == taxVariable;
        } else {
            return (subTotalVariable * Double.parseDouble(properties.getProperty("abroadTax"))) == taxVariable;
        }
    }

    public CheckoutPage openingCheckoutPage() {
        checkoutButton.click();
        return new CheckoutPage(getDriver());
    }
}
