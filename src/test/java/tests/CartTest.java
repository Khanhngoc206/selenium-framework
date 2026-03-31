package tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.pages.CartPage;
import framework.pages.CheckoutPage;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;

public class CartTest extends BaseTest {

    @Test
    public void addFirstItemToCartShouldIncreaseBadgeCount() {
        LoginPage loginPage = new LoginPage(getDriver());

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce")
                                               .addFirstItemToCart();

        Assert.assertEquals(inventoryPage.getCartItemCount(), 1);
    }

    @Test
    public void addItemByNameShouldAppearInCart() {
        String productName = "Sauce Labs Backpack";

        LoginPage loginPage = new LoginPage(getDriver());

        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                     .addItemByName(productName)
                                     .goToCart();

        List<String> itemNames = cartPage.getItemNames();

        Assert.assertTrue(itemNames.contains(productName), "Selected product should be in cart");
    }

    @Test
    public void removeFirstItemShouldReduceItemCount() {
        LoginPage loginPage = new LoginPage(getDriver());

        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                     .addFirstItemToCart()
                                     .goToCart();

        int before = cartPage.getItemCount();

        cartPage.removeFirstItem();
        int after = cartPage.getItemCount();

        Assert.assertEquals(before, 1);
        Assert.assertEquals(after, 0);
    }

    @Test
    public void emptyCartShouldReturnZeroItemCount() {
        LoginPage loginPage = new LoginPage(getDriver());

        CartPage cartPage = loginPage.login("standard_user", "secret_sauce")
                                     .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 0);
    }

    @Test
    public void goToCheckoutShouldOpenCheckoutPage() {
        LoginPage loginPage = new LoginPage(getDriver());

        CheckoutPage checkoutPage = loginPage.login("standard_user", "secret_sauce")
                                             .addFirstItemToCart()
                                             .goToCart()
                                             .goToCheckout();

        Assert.assertTrue(checkoutPage.isLoaded(), "Checkout page should be loaded");
    }
}