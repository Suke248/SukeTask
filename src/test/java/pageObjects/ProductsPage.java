package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.TestBase;
import java.util.ArrayList;
import java.util.List;

public class ProductsPage extends TestBase {

    private By productsLabel;
    private By sortDropdown;
    private By addToCartButton;
    private By itemName;
    private By itemPrice;
    private By shoppingCartButton;
    private By YourCartLabel;
    private By removeItem;
    private By continueShoppingButton;
    private By addedItem;
    private By checkOutButton;
    private  By checkoutLabel;
    private By continueButton;
    private By firstNameEdit;
    private By lastNameEdit;
    private By zipCodeEdit;
    private By checkoutOverviewLabel;
    private By finishButton;
    private By itemTotal;
    private By completeHeaderLabel;
    private By completeTextLabel;

    public ProductsPage() {
        InitElements();
    }

    private void InitElements() {
        productsLabel = By.xpath("//span[text()='Products']");
        YourCartLabel = By.xpath("//span[text()='Your Cart']");
        sortDropdown = By.xpath("//select[@class='product_sort_container']");
        addToCartButton = By.xpath("//button[text()='Add to cart']");
        itemName = By.xpath("//div[@class='inventory_item_name']");
        itemPrice = By.xpath("//div[@class='inventory_item_price']");
        shoppingCartButton = By.xpath("//span[@class='shopping_cart_badge']");
        removeItem = By.xpath("//button[@class='btn btn_secondary btn_small cart_button']");
        continueShoppingButton = By.id("continue-shopping");
        addedItem  = By.xpath("//button[@class='btn btn_secondary btn_small btn_inventory']");
        checkOutButton = By.id("checkout");
        checkoutLabel = By.xpath("//span[text()='Checkout: Your Information']");
        continueButton = By.id("continue");
        firstNameEdit = By.id("first-name");
        lastNameEdit = By.id("last-name");
        zipCodeEdit = By.id("postal-code");
        checkoutOverviewLabel = By.xpath("//span[text()='Checkout: Overview']");
        finishButton = By.id("finish");
        itemTotal = By.xpath("//div[@class='summary_subtotal_label']");
        completeHeaderLabel = By.xpath("//h2[@class='complete-header']");
        completeTextLabel = By.xpath("//div[@class='complete-text']");
    }

    public void verifyHomeScreenDisplayed() throws Exception {
        waitForElementToDisplay(productsLabel, 20);
        if(isElementCurrentlyDisplayed(productsLabel)){
            test.pass("Login successfull and Products page is displayed");
        }else{
            test.pass("Login not successfull ");
            Assert.fail();
        }
    }

    public void sortProducts(String sort) {
        selectByVisibleText(sortDropdown, sort);
        test.info("Products sorted on: "+sort);
    }

    public void addItemsToCart(int numberItems) {
        List<WebElement> addToCart = driver.findElements(addToCartButton);
        List<WebElement> itemNames = driver.findElements(itemName);
        List<WebElement> itemPrices = driver.findElements(itemPrice);
        List<String> addedItemNames = new ArrayList<>();
        List<String> addedItemPrices = new ArrayList<>();
        for(int i=0;i<numberItems;i++){
            click(addToCart.get(i));
            test.info("Added item to the cart: "+itemNames.get(i).getText());
        }

        List<WebElement> addedItems = driver.findElements(addedItem);

        for(int i=0;i<addedItems.size();i++) {
            addedItemNames.add(itemNames.get(i).getText());
            addedItemPrices.add(itemPrices.get(i).getText());
        }
        worldList.put("addedItemName",addedItemNames);
        worldList.put("addedItemPrice",addedItemPrices);
        test.info("Added Item Names: "+worldList.get("addedItemName"));
        test.info("Added Item Prices: "+worldList.get("addedItemPrice"));
    }

    public void visitShoppingCartBadge() throws Exception {
        click(shoppingCartButton);
        waitForElementToDisplay(YourCartLabel, 10);
        if(isElementCurrentlyDisplayed(YourCartLabel)){
            test.pass("Shopping Cart is displayed");
        }else{
            test.fail("Shopping Cart is not displayed");
            Assert.fail();
        }
        verifyItemsInCart();
    }

    public void verifyItemsInCart() {
        List<WebElement> itemNames = driver.findElements(itemName);
        List<WebElement> itemPrices = driver.findElements(itemPrice);
        List<String> addedItemNames = new ArrayList<>();
        List<String> addedItemPrices = new ArrayList<>();
        for(int i=0;i<itemNames.size();i++){
            addedItemNames.add(itemNames.get(i).getText());
            addedItemPrices.add(itemPrices.get(i).getText());
        }
        worldList.put("CartItemName",addedItemNames);
        worldList.put("CartItemPrice",addedItemPrices);

        test.info("Cart Item Names: "+ worldList.get("CartItemName"));
        test.info("Cart Item Names: "+ worldList.get("CartItemPrice"));
    }

    public void verifySelectedItemsWithCartItems() {
        if (worldList.get("addedItemName").containsAll(worldList.get("CartItemName"))) {
            test.pass("Cart items match with selected items");
        } else {
            test.fail("Cart items match with selected items");
            Assert.fail("Cart items does not match with selected items");
        }

        if (worldList.get("addedItemName").containsAll(worldList.get("CartItemName"))) {
            test.pass("Cart item prices match with selected item price");
        } else {
            test.fail("Cart Item prices does not match with selected item prices");
            Assert.fail("Cart Item prices does not match with selected item prices");
        }
    }

    public void removeItems(int items) {
        List<WebElement> removeItems = driver.findElements(removeItem);
        List<WebElement> removeItemNames = driver.findElements(itemName);
        for(int i=0;i<items;i++){
            test.info("Removed item from the cart: "+removeItemNames.get(i).getText());
            click(removeItems.get(i));
        }
    }

    public void selectContinueShoppingButton() throws Exception {
        click(continueShoppingButton);
    }

    public void clickCheckOutButton() throws Exception {
        click(checkOutButton);
        waitForElementToDisplay(checkoutLabel, 10);
        if(isElementCurrentlyDisplayed(checkoutLabel)){
            test.pass("Checkout page is displayed");
        }else{
            test.fail("Checkout is not displayed");
            Assert.fail();
        }
    }

    public void clickContinueButton() throws Exception {
        click(continueButton);
    }

    public void enterCheckoutInformation() {
        sendKeys(firstNameEdit,"first Name");
        sendKeys(lastNameEdit,"last name");
        sendKeys(zipCodeEdit,"05302");
    }

    public void verifyCheckOutOverviewPage() throws Exception {
        waitForElementToDisplay(checkoutOverviewLabel, 10);
        if(isElementCurrentlyDisplayed(checkoutOverviewLabel)){
            test.pass("Checkout overview page is displayed");
        }else{
            test.fail("Checkout overview page is not displayed");
            Assert.fail();
        }
    }

    public void clickFinishButton() throws Exception {
        click(finishButton);
    }

    public void verifyTotalPrice() throws Exception {
        double totalPrice=0;
        List<WebElement> itemPrices = driver.findElements(itemPrice);
        for(int i=0;i<itemPrices.size();i++){
            String itemPrice = itemPrices.get(i).getText().replace("$","").trim();
            totalPrice = totalPrice+ Double.parseDouble(itemPrice);
        }
        test.info("Actual Total Price: "+totalPrice);
        double expTotalPrice = Double.parseDouble(getTextFromElement(itemTotal).split(":")[1].replace("$","").trim());
        test.info("Expected Total Price: "+expTotalPrice);
        if(totalPrice==expTotalPrice){
            test.pass("Total Price is as expected");
        }else{
            test.pass("Total Price is not as expected");
        }
    }

    public void verifyOrderConfirmation() throws Exception {
        waitForElementToDisplay(completeHeaderLabel, 5);
        test.info("Complete order Header is displayed as: "+getTextFromElement(completeHeaderLabel));
        test.info("Complete order Text is displayed as: "+getTextFromElement(completeTextLabel));
    }
}
