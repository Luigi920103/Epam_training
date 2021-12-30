package com.epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class BasketPage extends BasePage {

    //buttons
    @FindBy(css = ".wc-proceed-to-checkout a")
    private WebElement checkoutButton;

    //labels
    @FindBy(css = ".cart-subtotal span[class='woocommerce-Price-amount amount']")
    private WebElement subtotal;

    @FindBy(css = ".order-total span[class='woocommerce-Price-amount amount']")
    private WebElement total;

    private double subTotalVariable, totalVariable;

    public BasketPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    public boolean subtotalLowerThanTotal() {
        subTotalVariable = Double.parseDouble((subtotal.getText().substring(1)).replace(",", ""));
        totalVariable = Double.parseDouble((total.getText().substring(1)).replace(",", ""));
        return subTotalVariable < totalVariable;
    }

    public CheckoutPage openingCheckoutPage() {
        checkoutButton.click();
        return new CheckoutPage(getDriver());
    }
}
