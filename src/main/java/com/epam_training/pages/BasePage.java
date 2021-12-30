package com.epam_training.pages;

import com.epam_training.properties.ReaderOfProperties;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.ArrayList;


/**
 * Pagina base de la cual heredaran las otras paginas de tipo pages contiene metodos que pueden ser aprovechados
 * por las paginas que hereden de esta simplificando el codigo y estandarizando ciertas acciones
 */
public class BasePage {
    private WebDriver driver;
    protected WebDriverWait wait;
    ReaderOfProperties readProperty = new ReaderOfProperties();

    /**
     * Constructor de Clase padre el cual contiene el driver y la creacion como tal del elemento wait
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Metodo de clickeo el cual permite esperar hasta que el elemento tenga las condiciones necesarias
     * para interactuar con el recibe como parametro el elemento a ser clickeado
     *
     * @param object elemento de tipo By que permitira la identificacion del elemento
     */
    protected void click(By object) {
        wait.until(ExpectedConditions.presenceOfElementLocated(object));
        wait.until(ExpectedConditions.visibilityOfElementLocated(object));
        wait.until(ExpectedConditions.elementToBeClickable(object));
        driver.findElement(object).click();
    }

    /**
     * Metodo que nos ayudara a identificar la existencia de un elemento en la pagina en la que nos encontremos
     *
     * @param object Elemento de tipo webelement que se desea validar
     */
    protected boolean validationElement(WebElement object) {
        try {
            wait.until(ExpectedConditions.visibilityOf(object));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Metodo que nos ayudara a identificar la existencia de un elemento en la pagina en la que nos encontremos
     * y que este sea clickeable
     *
     * @param object Elemento de tipo By que se desea validar
     */
    protected boolean validationClickableElement(By object) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(object));
            wait.until(ExpectedConditions.elementToBeClickable(object));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected boolean validationClickableElement(WebElement object) {
        try {
            wait.until(ExpectedConditions.visibilityOf(object));
            wait.until(ExpectedConditions.elementToBeClickable(object));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }


    /**
     * Metodo que nos entregara un objeto de tipo select el cual nos permitira identificar las
     * diferentes opciones dentro de un dropbox recibe como parametro el objeto padre del dropbox
     *
     * @param object Obejeto padre a ser validado el cual puede contener N opciones
     */
    public Select getOptions(WebElement object) {
        return new Select(object);
    }

    /**
     * Metodo que nos permitira cambiar entre los diferentes tabs existentes en el navegador
     *
     * @param tab al cual deseamos controlar
     */
    protected void switchToTab(int tab) {
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(tab));
    }

    /**
     * Metodo que entregara el driver al metodo que requiera de el, usado para la creacion
     * de las nuevas pages cuando se quiere hacer cambio de pagina dentro del codigo
     */
    protected WebDriver getDriver() {
        return driver;
    }

}
