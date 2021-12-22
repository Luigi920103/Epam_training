package com.Epam_training.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LoginPage extends BasePage {

    //Textbox
    @FindBy(css ="#username")
    private WebElement userTextBox;

    @FindBy(css ="#password")
    private WebElement passwordTextBox;

    //Buttons
    @FindBy(css ="p:nth-child(3) > input.woocommerce-Button.button")
    private WebElement submitButton;

    //Labels or messages
    @FindBy(css =".woocommerce-MyAccount-content")
    private WebElement welcomeMessage;

    @FindBy(css =".woocommerce-error")
    private WebElement errorMessage;


    public LoginPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,30),this);
    }

    /**
     * Metodo que diligencia los campos de user y password y el
     * correspondiente boton de submit
     * @param user recibe un String como parametro este se puede enviar con ""
     * @param password recibe un String como parametro este se puede enviar con ""
     */
    public void signIn(String user, String password){
        userTextBox.sendKeys(user);
        passwordTextBox.sendKeys(password);
        submitButton.click();
    }

    /**
     * Metodo que lee y retorna el mensajede bienvenida una vez logeados
     * @return
     */
    public String getWelcomeMessage(){
        return welcomeMessage.getText();
    }

    /**
     * Metodo que retorna el mensaje de error mostrado por la pagina una vez
     * ingresadas credenciales invalidas
     * @return
     */
    public String getErrorMessage(){
        return errorMessage.getText();
    }

    /**
     * Metodo para validar que el campo password tenga la propiedad type=password
     * @return boolean
     */
    public boolean validatePasswordElement() {
        return passwordTextBox.getAttribute("type").equals("password");
    }
}