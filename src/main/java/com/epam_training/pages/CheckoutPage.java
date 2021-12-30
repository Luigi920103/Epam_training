package com.epam_training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import static com.epam_training.properties.ReaderOfProperties.properties;

public class CheckoutPage extends BasePage {

    //buttons
    @FindBy(css = "#place_order")
    private WebElement placeOrder;

    //Text fields
    @FindBy(css = "#billing_first_name")
    private WebElement firstName;

    @FindBy(css = "#billing_last_name")
    private WebElement lastName;

    @FindBy(css = "#billing_company")
    private WebElement company;

    @FindBy(css = "#billing_email")
    private WebElement email;

    @FindBy(css = "#billing_phone")
    private WebElement phone;

    @FindBy(css = "#billing_address_1")
    private WebElement address;

    @FindBy(css = "#billing_address_2")
    private WebElement addressDetail;

    @FindBy(css = "#billing_city")
    private WebElement city;

    @FindBy(css = "#billing_state")
    private WebElement state;

    @FindBy(css = "#billing_postcode")
    private WebElement postCode;

    //labels
    @FindBy(css = ".col-1 div h3")
    private WebElement billingDetails;

    @FindBy(css = "#order_review_heading")
    private WebElement orderDetails;

    @FindBy(css = ".col-2 div h3")
    private WebElement additionalDetails;

    //dropbox
    @FindBy(css = "#s2id_billing_country .select2-choice")
    private WebElement country;

    //radioButtons
    @FindBy(css = "#payment_method_bacs")
    private WebElement directBankTransfer;

    @FindBy(css = "#payment_method_cheque")
    private WebElement checkPayments;

    @FindBy(css = "#payment_method_cod")
    private WebElement cashOnDelivery;

    @FindBy(css = "#payment_method_ppec_paypal")
    private WebElement payPalExpressCheckout;


    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    /**
     * Metodo que nos ayuda a validar la existencia de el label en pantalla
     * y si el texto corresponde a lo esperado
     *
     * @return booleano que servira para la validacion del assert en los steps
     */
    public boolean billingDetailsOnScreen() {
        if (validationElement(billingDetails)) {
            if (billingDetails.getText().equals(properties.getProperty("BillingDetailsLabel")))
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
            if (orderDetails.getText().equals(properties.getProperty("YourOrderLabel")))
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
    public boolean additionalDetailsOnScreen() {
        if (validationElement(additionalDetails)) {
            if (additionalDetails.getText().equals(properties.getProperty("AdditionalInformationLabel")))
                return true;
            return false;
        }
        return false;
    }

    /**
     * Metodo que nos ayudara a diligenciar la informacion de la orden
     * en la pagina de Checkout
     *
     * @param nameOnForm     String
     * @param lastNameOnForm String
     * @param companyOnForm  String
     * @param emailOnForm    String que debe tener "@" y ".com" para ser valido
     * @param phoneOnForm    String que tiene que contener un numero entero sin espacios
     */
    public void fillingFormPersonalData(
            String nameOnForm,
            String lastNameOnForm,
            String companyOnForm,
            String emailOnForm,
            String phoneOnForm
    ) {
        firstName.sendKeys(nameOnForm);
        lastName.sendKeys(lastNameOnForm);
        company.sendKeys(companyOnForm);
        email.sendKeys(emailOnForm);
        phone.sendKeys(phoneOnForm);
    }

    /**
     * Metodo que nos ayudara a diligenciar la informacion de la orden
     * en la pagina de Checkout - informacion acerca de la localizacion
     *
     * @param countryOnForm       String con un nombre valido de pais o que
     *                            exista en el dropbox
     * @param addressOnForm       String
     * @param addressDetailOnForm String
     * @param cityOnForm          String
     * @param stateOnForm         String
     * @param postCodeOnForm      String numerico
     */
    public void fillingFormLocationData(
            String countryOnForm,
            String addressOnForm,
            String addressDetailOnForm,
            String cityOnForm,
            String stateOnForm,
            String postCodeOnForm
    ) {
        selectOptionOnDropBox(country, countryOnForm);
        address.sendKeys(addressOnForm);
        addressDetail.sendKeys(addressDetailOnForm);
        city.sendKeys(cityOnForm);
        state.sendKeys(stateOnForm);
        postCode.sendKeys(postCodeOnForm);
    }

    /**
     * Metodo que nos ayudara a interactuar con los dropbox de esta pagina
     * siempre que las opciones esten anidadas en div's
     *
     * @param object        Elemento que al ser clickeado despliega la lista
     * @param searchingWord String que sera buscado en la lista desplegada
     */
    public void selectOptionOnDropBox(WebElement object, String searchingWord) {
        object.click();
        getDriver().findElement(By.xpath("//div[text()='" + searchingWord + "']")).click();
    }

    /**
     * Metodo que nos ayudara a seleccionar entre los radiobutton de medios de pago
     *
     * @param paymentWay String que puede ser, "Bank","Check","Cash" o "PayPal" solo
     *                   acepta estas opciones y de no marcar o seleccioanr una valida
     *                   por defecto seleccionara "Bank"
     */
    public void selectPaymentWay(String paymentWay) {
        switch (paymentWay) {
            case "Bank":
                directBankTransfer.click();
                break;
            case "Check":
                checkPayments.click();
                break;
            case "Cash":
                cashOnDelivery.click();
                break;
            case "PayPal":
                payPalExpressCheckout.click();
                break;
            default:
                directBankTransfer.click();
        }
    }

    /**
     * Metodo que dara click al boton para generar la orden y nos retornara
     * un pageobject de la siguienta pantalla lo cual nos permitira
     * interactuar con la misma
     *
     * @return retorna un page object de la pagina de order details
     */
    public OrderDetailsPage placeOrder() {
        placeOrder.click();
        return new OrderDetailsPage(getDriver());
    }
}
