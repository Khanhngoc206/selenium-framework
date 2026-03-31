package framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import framework.base.BasePage;

public class CheckoutPage extends BasePage {

    private final By checkoutFormBy = By.id("checkout_info_container");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(checkoutFormBy);
    }
}