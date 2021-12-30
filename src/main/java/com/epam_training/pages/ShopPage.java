package com.epam_training.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

public class ShopPage extends BasePage {

    @FindBy(xpath = "//span[@class='onsale'] //parent::*/following::span[@class='price']/ins")
    private List<WebElement> prices;

    @FindBy(xpath = "//span[@class='onsale'] //parent::*/following::span[@class='price']/del")
    private List<WebElement> oldPrices;

    //Labels or messages
    @FindBy(css = "#wpmenucartli > a > span.cartcontents")
    private WebElement productsCart;

    @FindBy(css = "#wpmenucartli > a > span.amount")
    private WebElement priceCart;

    //Local variables
    private double price;
    private boolean isThePriceOnTheScreen;
    private String buttonLocatorAfter, buttonLocatorBefore, priceLocator, priceToTake;
    private int randomOption;
    private WebElement elementToClick;

    private int accumulatedInTheCart = 0;

    JavascriptExecutor js = (JavascriptExecutor) getDriver();

    public ShopPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    public List<WebElement> checkPrices() {
        return prices;
    }

    public List<WebElement> checkOldPrices() {
        return oldPrices;
    }

    /**
     * Valida que el elemento tenga un precio y que este sea mayor a cero
     *
     * @param object lista de elementos que contendran los precios a ser validados
     * @return
     */
    public boolean validatePrice(List<WebElement> object) {
        List<WebElement> results = object;
        isThePriceOnTheScreen = true;
        for (WebElement priceToBeValidated : results) {
            if (priceToBeValidated.getText().equals("")) {
                isThePriceOnTheScreen = false;
                break;
            } else {
                price = Integer.parseInt(priceToBeValidated.getText().substring(1, priceToBeValidated.getText().indexOf(".")));
                if (price < 0) {
                    isThePriceOnTheScreen = false;
                    break;
                }
            }
        }
        return isThePriceOnTheScreen;
    }

    /**
     * Este metodo selecciona el numero de productos deseado deacuerdo a el valor del parametro
     * la seleccion de cada uno de ellos la hace de forma aleatoria entre las priemras 7 opciones de la
     * pagina, y a su vez invoca el metodo addingUpProducts que sera el encargado de realizar la sumatoria
     * de precios de los productos seleccionados para realizar la validacion final
     *
     * @param numberOfProducts Numero expresado como string que determinara el numero de productos a agregar
     *                         al carrito de compras
     */
    public void selectProduct(String numberOfProducts) {
        for (int i = 0; i < Integer.parseInt(numberOfProducts); i++) {
            randomOption = ((int) Math.floor(Math.random() * 7 + 1));
            setElementOnCatalog(Integer.toString(randomOption));
            try {
                elementToClick = getDriver().findElement(By.cssSelector(buttonLocatorBefore));
                elementToClick.click();
                addingUpProducts(priceLocator);

            } catch (NoSuchElementException e) {
                click(By.cssSelector(buttonLocatorAfter));
                addingUpProducts(priceLocator);
            }
            validationClickableElement(By.cssSelector(buttonLocatorAfter));
            js.executeScript("window.scrollBy(0,-2000)");
        }
    }

    /**
     * Este metodo selecciona el numero de productos deseado deacuerdo a el valor del parametro
     * la seleccion de cada uno de ellos la hace de forma ordenada entre los opciones presentes de la
     * pagina, y a su vez invoca el metodo addingUpProducts que sera el encargado de realizar la sumatoria
     * de precios de los productos seleccionados para realizar la validacion final
     *
     * @param numberOfProducts Numero expresado como string que determinara el numero de productos a agregar
     *                         al carrito de compras
     */
    public void selectingTheBooks(String numberOfProducts) {
        for (int a = 1; a <= (Integer.parseInt(numberOfProducts)); a++) {
            setElementOnCatalog(Integer.toString(a));
            elementToClick = getDriver().findElement(By.cssSelector(buttonLocatorBefore));
            elementToClick.click();
            addingUpProducts(priceLocator);
            js.executeScript("window.scrollBy(0,-2000)");
            validationClickableElement(By.cssSelector(buttonLocatorAfter));
        }
    }

    /**
     * Este metodo nos ayudara a setear de forma rapida ciertos locators para la localizacion
     * de elementos que hagan parte de un arreglo o conjunto de elementos con las mismas caracteristicas
     * en este caso especifico para los elementos mostrados en la pagina shop
     *
     * @param selection numero pasado como String que nos indicara la posicion
     *                  dentro de la matriz
     */
    public void setElementOnCatalog(String selection) {
        buttonLocatorBefore = ".products li:nth-child(" + selection + ") a[class='button product_type_simple add_to_cart_button ajax_add_to_cart']";
        buttonLocatorAfter = ".products li:nth-child(" + selection + ") a[class='button product_type_simple add_to_cart_button ajax_add_to_cart added']";
        priceLocator = ".products li:nth-child(" + selection + ")  span[class='price']";
    }

    /**
     * Metodo que nos permitira seleccionar la canasta y nos regresara el page
     * object correspondiente para continuar interactuando con los elementos
     * de esa pagina
     *
     * @return
     */
    public BasketPage openingBasket() {
        productsCart.click();
        return new BasketPage(getDriver());
    }

    /**
     * Metodo cuya funcion es realizar la sumatoria de precios de forma independiente a la pagina para asi
     * contrastar los resultados con los mostrados por el carrito en la misma pagina
     *
     * @param locator Recive un locatos de tipo By Css como cadena de texto
     */
    public void addingUpProducts(String locator) {
        priceToTake = getDriver().findElement(By.cssSelector(locator)).getText();
        if (priceToTake.contains("\n")) {
            setAccumulatedInTheCart(getAccumulatedInTheCart() + Integer.parseInt(priceToTake.substring(priceToTake.indexOf("\n") + 2, priceToTake.lastIndexOf("."))));
        } else {
            setAccumulatedInTheCart(getAccumulatedInTheCart() + Integer.parseInt(priceToTake.substring(1, priceToTake.lastIndexOf("."))));
        }
    }

    /**
     * Metodo que retorna el numero de productos seleccionados deacuerdo a la pagina para su posterios
     * validacion con el numero de productos deseado desde la prueba
     *
     * @return string con el numero de productis
     */
    public String productsOnCart() {
        return productsCart.getText().substring(0, productsCart.getText().indexOf(" "));
    }

    /**
     * Metodo que returna el total calculado por la aplicacion como cadena de texto
     *
     * @return entero como cadena de texto sin caracteres de moneda o puntos decimales
     */
    public String priceOnCart() {
        System.out.println("Acumulado auto <" + getAccumulatedInTheCart() + ">");
        System.out.println("Acumulado app <" + priceCart.getText().substring(1, priceCart.getText().indexOf(".")) + ">");
        return (priceCart.getText().substring(1, priceCart.getText().indexOf(".")).replace(",", ""));
    }

    //getters and setters
    public int getAccumulatedInTheCart() {
        return accumulatedInTheCart;
    }

    public void setAccumulatedInTheCart(int accumulatedInTheCart) {
        this.accumulatedInTheCart = accumulatedInTheCart;
    }
}
