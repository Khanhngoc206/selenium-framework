package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.base.BaseTest;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginWithValidCredentialsShouldGoToInventoryPage() {
        LoginPage loginPage = new LoginPage(getDriver());

        InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded");
    }

    @Test
    public void loginWithInvalidPasswordShouldShowErrorMessage() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.loginExpectingFailure("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
    }

    @Test
    public void loginWithLockedOutUserShouldShowLockedUserError() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Locked out error should be displayed");
        Assert.assertTrue(
                loginPage.getErrorMessage().toLowerCase().contains("locked out"),
                "Error message should mention locked out"
        );
    }
}