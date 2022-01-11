package com.epam_training.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import java.util.List;

import static java.lang.Math.round;

public class ShopPage extends BasePage {

    @FindBy(css = ".woocommerce-LoopProduct-link .price")
    private List<WebElement> prices;

    @FindBy(css = ".woocommerce-LoopProduct-link")
    private List<WebElement> results;

    @FindBy(xpath = "//span[@class='onsale'] //parent::*/following::span[@class='price']/ins")
    private List<WebElement> newPrices;

    @FindBy(xpath = "//span[@class='onsale'] //parent::*/following::span[@class='price']/del")
    private List<WebElement> oldPrices;

    //scrolls
    @FindBy(css = ".price_slider span:nth-child(3)")
    private WebElement rightLimit;

    @FindBy(css = ".price_slider span:nth-child(2)")
    private WebElement leftLimit;

    //drop list
    @FindBy(css = "select[name='orderby']")
    private WebElement orderByList;


    //buttons
    @FindBy(css = "button[type='submit']")
    private WebElement filter;

    //Labels or messages
    @FindBy(css = "#wpmenucartli > a > span.cartcontents")
    private WebElement productsCart;

    @FindBy(css = "#wpmenucartli > a > span.amount")
    private WebElement priceCart;

    @FindBy(css = ".price_label .from")
    private WebElement lowerPriceOnFilter;

    @FindBy(css = ".price_label .to")
    private WebElement higherPriceOnFilter;

    //Local variables
    private double price;
    private boolean isThePriceOnTheScreen;
    private boolean IsThePriceOnTheRange;
    private String buttonLocatorAfter, buttonLocatorBefore, priceLocator, priceToTake;
    private int randomOption;
    private int currentLowerPriceOnFilter, tempCurrentValue, tempDesiredValue;
    private int currentHigherPriceOnFilter;
    private WebElement elementToClick;
    private ReadMorePage readMorePage;

    private boolean stockValidation;

    Actions act = new Actions(getDriver());

    private int accumulatedInTheCart = 0;

    JavascriptExecutor js = (JavascriptExecutor) getDriver();

    public ShopPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
    }

    public List<WebElement> checkPrices() {
        return prices;
    }

    public List<WebElement> checkResults() {
        return results;
    }

    public List<WebElement> checkNewPrices() {
        return newPrices;
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

    public boolean validatePriceRange(List<WebElement> object, int lowerLimit, int higherLimit) {
        List<WebElement> results = object;
        IsThePriceOnTheRange = true;
        for (WebElement priceToBeValidated : results) {
            price = Integer.parseInt(priceToBeValidated.getText().substring(1, priceToBeValidated.getText().indexOf(".")));
            if (price < lowerLimit && price > higherLimit) {
                IsThePriceOnTheRange = false;
                break;
            }
        }
        return IsThePriceOnTheRange;
    }

    public boolean priceResultsInRange(int lowerLimit, int higherLimit) {
        if (
                validatePriceRange(newPrices, lowerLimit, higherLimit) &&
                        validatePriceRange(prices, lowerLimit, higherLimit)
        ) {
            return true;
        } else {
            return false;
        }
    }


    public void validateStock(List<WebElement> clickableObject) {
        List<WebElement> results = clickableObject;
        boolean flag = false;
        setStockValidation(true);
        String selectLinkOpeningNewTab = Keys.chord(Keys.CONTROL, Keys.RETURN);
        for (WebElement priceToBeValidated : results) {
            if (flag == false) {
                readMorePage = openReadMore(priceToBeValidated, selectLinkOpeningNewTab);
                switchToTab(1);
                setStockValidation(readMorePage.stockValidation());
                getDriver().close();
                flag = true;
                switchToTab(0);
            } else {
                priceToBeValidated.sendKeys(selectLinkOpeningNewTab);
                switchToTab(1);
                setStockValidation(readMorePage.stockValidation());
                getDriver().close();
                switchToTab(0);
            }
            if (isStockValidation() == false) {
                break;
            }
        }
    }

    ReadMorePage openReadMore(WebElement object, CharSequence command) {
        object.sendKeys(command);
        return new ReadMorePage(getDriver());
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
        return (priceCart.getText().substring(1, priceCart.getText().indexOf(".")).replace(",", ""));
    }

    public void setFilterByPrice(int lowerPrice, int higherPrice) {
        setLocalPrices();
        if (getCurrentLowerPriceOnFilter() != lowerPrice) {
            firstApproximationOnFilter(round(lowerPrice - getCurrentLowerPriceOnFilter()) / 2, leftLimit);
            ApproximationOnFilter(getCurrentLowerPriceOnFilter(), lowerPrice, leftLimit);
        }

        if (getCurrentHigherPriceOnFilter() != higherPrice) {
            firstApproximationOnFilter(round(higherPrice - getCurrentHigherPriceOnFilter()) / 2, rightLimit);
            ApproximationOnFilter(getCurrentHigherPriceOnFilter(), higherPrice, rightLimit);
        }
        filter.click();
    }

    public void ApproximationOnFilter(int currentValue, int desiredValue, WebElement object) {

        while (true) {
            if (rightLimit == object)
                tempCurrentValue = getCurrentHigherPriceOnFilter();
            if (leftLimit == object)
                tempCurrentValue = getCurrentLowerPriceOnFilter();

            if (tempCurrentValue < desiredValue)
                act.dragAndDropBy(object, 2, 0).build().perform();
            if (tempCurrentValue > desiredValue)
                act.dragAndDropBy(object, -3, 0).build().perform();
            if (tempCurrentValue == desiredValue)
                break;
            setLocalPrices();
        }
    }

    public void orderResultsBy(String desiredOrder) {
        orderByList.click();
        Select options = new Select(orderByList);
        options.selectByVisibleText(desiredOrder);
    }


    public void firstApproximationOnFilter(int offset, WebElement object) {
        act.dragAndDropBy(object, offset, 0).build().perform();
        setLocalPrices();
    }

    public void setLocalPrices() {
        setCurrentLowerPriceOnFilter(cleanPrice(lowerPriceOnFilter.getText()));
        setCurrentHigherPriceOnFilter(cleanPrice(higherPriceOnFilter.getText()));
    }

    public int cleanPrice(String number) {
        return Integer.parseInt(number.substring(1));
    }

    //getters and setters
    public int getAccumulatedInTheCart() {
        return accumulatedInTheCart;
    }

    public void setAccumulatedInTheCart(int accumulatedInTheCart) {
        this.accumulatedInTheCart = accumulatedInTheCart;
    }

    public boolean isStockValidation() {
        return stockValidation;
    }

    public void setStockValidation(boolean stockValidation) {
        this.stockValidation = stockValidation;
    }

    public int getCurrentLowerPriceOnFilter() {
        return currentLowerPriceOnFilter;
    }

    public void setCurrentLowerPriceOnFilter(int currentLowerPriceOnFilter) {
        this.currentLowerPriceOnFilter = currentLowerPriceOnFilter;
    }

    public int getCurrentHigherPriceOnFilter() {
        return currentHigherPriceOnFilter;
    }

    public void setCurrentHigherPriceOnFilter(int currentHigherPriceOnFilter) {
        this.currentHigherPriceOnFilter = currentHigherPriceOnFilter;
    }

    public boolean isThePriceOnTheRange() {
        return IsThePriceOnTheRange;
    }

    public void setThePriceOnTheRange(boolean thePriceOnTheRange) {
        IsThePriceOnTheRange = thePriceOnTheRange;
    }
}
