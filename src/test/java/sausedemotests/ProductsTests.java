package sausedemotests;

import core.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static sausedemotests.Constants.*;

public class ProductsTests extends BaseTest {


    @BeforeEach
    public void beforeTest() {
        loginPage.navigate();
        loginPage.fillLoginForm(STANDARD_USER, SECRET_SAUCE);
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart() {
        addToCart(SAUCE_LABS_BACKPACK);
        addToCart(SAUCE_LABS_BIKE_LIGHT);

        driver.findElement(By.className(SHOPPING_CART_LINK)).click();

        List<WebElement> cartItems = driver.findElements(By.className(INVENTORY_ITEM_NAME));
        boolean isBackpackAdded = false;
        boolean isBikeLightAdded = false;

        for (WebElement item : cartItems) {
            if (item.getText().equals(SAUCE_LABS_BACKPACK)) {
                isBackpackAdded = true;
            }
            if (item.getText().equals(SAUCE_LABS_BIKE_LIGHT)) {
                isBikeLightAdded = true;
            }
        }

        assertTrue(isBackpackAdded, BACKPACK_NOT_ADDED_TO_THE_CART);
        assertTrue(isBikeLightAdded, BIKE_LIGHT_NOT_ADDED_TO_THE_CART);
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation() {
        addToCart(SAUCE_LABS_BACKPACK);
        addToCart(SAUCE_LABS_BIKE_LIGHT);

        driver.findElement(By.className(SHOPPING_CART_LINK)).click();
        proceedToCheckout();

        fillShippingDetails(JOHN, DOE, NUMBER);
        driver.findElement(By.id("continue")).click();

        String summaryText = driver.findElement(By.className(TITLE)).getText();
        assertTrue(summaryText.contains(CHECKOUT_OVERVIEW), FAILED_TO_NAVIGATE_TO_CHECKOUT_OVERVIEW_PAGE);
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm() {
        addToCart(SAUCE_LABS_BACKPACK);
        addToCart(SAUCE_LABS_BIKE_LIGHT);

        driver.findElement(By.className(SHOPPING_CART_LINK)).click();
        proceedToCheckout();

        fillShippingDetails(JOHN, DOE, NUMBER);
        driver.findElement(By.id("continue")).click();

        driver.findElement(By.id("finish")).click();

        driver.findElement(By.className(SHOPPING_CART_LINK)).click();
        List<WebElement> cartItems = driver.findElements(By.className(INVENTORY_ITEM_NAME));
        assertEquals(0, cartItems.size(), CART_IS_NOT_EMPTY_AFTER_COMPLETING_THE_ORDER);
    }
}
