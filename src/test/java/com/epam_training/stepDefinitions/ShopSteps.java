package com.epam_training.stepDefinitions;

import com.epam_training.actors.ShopActors;
import com.epam_training.pages.BasketPage;
import com.epam_training.pages.CheckoutPage;
import com.epam_training.pages.OrderDetailsPage;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.assertj.core.api.SoftAssertions;

import static com.epam_training.properties.ReaderOfProperties.properties;
import static com.epam_training.stepDefinitions.BackgroundSteps.shopPage;

public class ShopSteps {

    private BackgroundSteps pageObject = new BackgroundSteps();

    private BasketPage basketPage;
    private CheckoutPage checkoutPage;
    private OrderDetailsPage orderDetailsPage;

    private int localLowerPrice;
    private int localHigherPrice;

    private String numberOfBooks;
    private ShopActors user = new ShopActors();

    private SoftAssertions softAssert = new SoftAssertions();

    @Then("the user can view the current price")
    public void validatingPriceOnProduct() {
        Assert.assertTrue(properties.getProperty("priceHasToBeHigherThanZero"), shopPage.validatePrice(shopPage.checkNewPrices()));
    }

    @Then("the user can view old price stricken for the sale written products")
    public void validatingOldPriceOnSaleProducts() {
        Assert.assertTrue(properties.getProperty("priceHasToBeHigherThanZero"), shopPage.validatePrice(shopPage.checkOldPrices()));
    }

    @When("the user selects {string} random books")
    public void selectingSomeRandomBooks(String numberOfBooks) {
        setNumberOfBooks(numberOfBooks);
        shopPage.selectProduct(numberOfBooks);
    }

    @When("the user selects {string} books")
    @And("the user selects {string} books on the catalog")
    public void selectingSomeBooks(String numberOfBooks) {
        setNumberOfBooks(numberOfBooks);
        shopPage.selectingTheBooks(numberOfBooks);
    }

    @Then("the user can see the same number of books on the basket")
    public void validatingNumberOfBooksSelected() {
        Assert.assertEquals(shopPage.productsOnCart(), getNumberOfBooks());
    }

    @And("the user can see the total of the basket")
    public void validatingFinalPriceOnBasket() {
        Assert.assertEquals(shopPage.priceOnCart(), Integer.toString(shopPage.getAccumulatedInTheCart()));
    }

    @When("the user goes to the basket")
    public void openingTheBasket() {
        basketPage = shopPage.openingBasket();
    }

    @Then("the total price is higher than subtotal")
    public void validatingTotalPriceIsHigherThanSubtotal() {
        Assert.assertTrue(properties.getProperty("subTotalLowerThanTotal"), basketPage.subtotalLowerThanTotal());
    }

    @And("the user proceeds to checkout")
    public void openingTheCheckoutPage() {
        checkoutPage = basketPage.openingCheckoutPage();
    }

    @Then("the user should view Billing Details,Order Details,Additional details and Payment gateway details.")
    public void validatingDetailsOnCheckoutPage() {
        softAssert.assertThat(checkoutPage.billingDetailsOnScreen()).isEqualTo(true);
        softAssert.assertThat(checkoutPage.additionalDetailsOnScreen()).isEqualTo(true);
        softAssert.assertThat(checkoutPage.orderDetailsOnScreen()).isEqualTo(true);
        softAssert.assertAll();
    }

    @And("the user goes to the basket and proceeds to checkout")
    public void openingTheBasketAndCheckoutPages() {
        basketPage = shopPage.openingBasket();
        checkoutPage = basketPage.openingCheckoutPage();
    }

    @And("the user fills his details on the page")
    public void FillingDetailsOnThePage() {
        checkoutPage.fillingFormPersonalData(
                user.getName(),
                user.getLastName(),
                user.getCompany(),
                user.getEmail(),
                user.getPhone()
        );
        checkoutPage.fillingFormLocationData(
                user.getCountry(),
                user.getAddress(),
                user.getAddressDetail(),
                user.getCity(),
                user.getState(),
                user.getPostCode()
        );
        checkoutPage.selectPaymentWay(user.getPaymentWay());
    }

    @When("the user place the order")
    public void placeOrder() {
        orderDetailsPage = checkoutPage.placeOrder();
    }

    @Then("the user should see the order details, bank details,customer details and billing details")
    public void validationsOnPage() {
        softAssert.assertThat(orderDetailsPage.bankDetailsOnScreen()).isEqualTo(true);
        softAssert.assertThat(orderDetailsPage.orderDetailsOnScreen()).isEqualTo(true);
        softAssert.assertAll();
    }

    @Then("the {string} should be correct")
    public void taxValidation(String condition) {
        Assert.assertTrue(properties.getProperty("correctTaxAccordingLocation"), basketPage.taxValidation(condition));
    }

    @When("the user opens the read more on each product")
    public void addButtonAccordingStock() {
        shopPage.validateStock(shopPage.checkResults());
    }

    @Then("it has to have the add button according to its stock")
    public void addButtonAccordingStockAssertion() {
        Assert.assertTrue("The add button has to be active according the stock of the product", shopPage.isStockValidation());
    }

    @When("the user filter by price between {int} to {int}")
    public void filterByPrice(int lowerPrice, int higherPrice) {
        setLocalLowerPrice(lowerPrice);
        setLocalHigherPrice(higherPrice);
        shopPage.setFilterByPrice(lowerPrice, higherPrice);
    }

    @Then("the products show it a price in that range")
    public void validatingTheRangeOfPrices() {
        Assert.assertTrue("Los precios deben estar en el rango esperadpo", shopPage.priceResultsInRange(getLocalLowerPrice(), getLocalHigherPrice()));
    }

    public String getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(String numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }


    public int getLocalLowerPrice() {
        return localLowerPrice;
    }

    public void setLocalLowerPrice(int localLowerPrice) {
        this.localLowerPrice = localLowerPrice;
    }

    public int getLocalHigherPrice() {
        return localHigherPrice;
    }

    public void setLocalHigherPrice(int localHigherPrice) {
        this.localHigherPrice = localHigherPrice;
    }

    @When("the user order the results by {string}")
    public void theUserOrderTheResultsBy(String orderBy) {
        shopPage.orderResultsBy(orderBy);
    }

    @Then("the user has to see the results order by {string}")
    public void theUserHasToSeeTheResultsOrderBy(String arg0) {
        //todo Logic to validate the different orders
        //switch case to distinguish the case
        //todo logic to validate order on prices
    }
}
