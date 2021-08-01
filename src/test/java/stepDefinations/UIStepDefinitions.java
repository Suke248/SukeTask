package stepDefinations;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import libraries.APIEndPoints;
import pageObjects.ProductsPage;
import pageObjects.LoginPage;

import static io.restassured.RestAssured.given;

public class UIStepDefinitions {
    ProductsPage productsPage = new ProductsPage();
    LoginPage loginPage = new LoginPage();

    @When("I Login in to the site using user {string} and password {string}")
    public void iLoginInToTheSiteUsingUserAndPassword(String user, String password) throws Exception {
        loginPage.openBrowser();
        assert loginPage.navigatetoLoginURL();
        loginPage.login(user, password);
        productsPage.verifyHomeScreenDisplayed();
    }

    @And("I sort the products as {string}")
    public void iSortTheProductsAs(String sort) {
         productsPage.sortProducts(sort);
    }

    @And("I add {string} items to the cart")
    public void iAddItemsToTheCart(String items) {
         productsPage.addItemsToCart(Integer.parseInt(items));
    }

    @And("I visit shopping cart")
    public void iVisitShoppingCart() throws Exception {
        productsPage.visitShoppingCartBadge();
    }

    @Then("I verify the selected items are added in the cart")
    public void iVerifyTheSelectedItemsAreAddedInTheCart() {
        productsPage.verifySelectedItemsWithCartItems();
    }

    @And("I continue shopping")
    public void iContinueShopping() throws Exception {
         productsPage.selectContinueShoppingButton();
    }

    @When("I remove {string} item")
    public void iRemoveItem(String items) {
        productsPage.removeItems(Integer.parseInt(items));
    }

    @When("I click checkout button")
    public void iClickCheckoutButton() throws Exception {
         productsPage.clickCheckOutButton();
    }

    @And("I enter checkout information")
    public void iEnterCheckoutInformation() {
        productsPage.enterCheckoutInformation();
    }

    @And("I click continue button")
    public void iClickContinueButton() throws Exception {
        productsPage.clickContinueButton();
    }

    @Then("checkout page is displayed")
    public void checkoutPageIsDisplayed() throws Exception {
        productsPage.verifyCheckOutOverviewPage();
    }

    @And("I verify total price")
    public void iVerifyTotalPrice() throws Exception {
         productsPage.verifyTotalPrice();
    }

    @And("I finish checkout")
    public void iFinishCheckout() throws Exception {
         productsPage.clickFinishButton();
    }

    @Then("order confirmation message is displayed")
    public void orderConfirmationMessageIsDisplayed() throws Exception {
        productsPage.verifyOrderConfirmation();
    }

}
