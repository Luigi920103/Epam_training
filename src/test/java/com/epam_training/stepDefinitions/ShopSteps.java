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

import static com.epam_training.stepDefinitions.BackgroundSteps.shopPage;

public class ShopSteps {

    private BackgroundSteps pageObject = new BackgroundSteps();

    private BasketPage basketPage;
    private CheckoutPage checkoutPage;
    private OrderDetailsPage orderDetailsPage;

    private String numberOfBooks;
    private ShopActors user = new ShopActors();

    private SoftAssertions softAssert = new SoftAssertions();

    @Then("the user can view the actual price")
    public void validatingPriceOnProduct() {
        Assert.assertTrue("The price has to be superior to 0", shopPage.validatePrice(shopPage.checkPrices()));
    }

    @Then("the user can view old price stricken for the sale written products")
    public void validatingOldPriceOnSaleProducts() {
        Assert.assertTrue("The price has to be superior to 0", shopPage.validatePrice(shopPage.checkOldPrices()));
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
        Assert.assertTrue("The subTotal is lower than the Total", basketPage.subtotalLowerThanTotal());
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
    public void theUserShouldSeeTheOrderDetailsBankDetailsCustomerDetailsAndBillingDetails() {
        softAssert.assertThat(orderDetailsPage.bankDetailsOnScreen()).isEqualTo(true);
        softAssert.assertThat(orderDetailsPage.orderDetailsOnScreen()).isEqualTo(true);
        softAssert.assertAll();
    }

    public String getNumberOfBooks() {
        return numberOfBooks;
    }

    public void setNumberOfBooks(String numberOfBooks) {
        this.numberOfBooks = numberOfBooks;
    }

}
