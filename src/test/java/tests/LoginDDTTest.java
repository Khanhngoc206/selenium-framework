package tests;

import framework.base.BaseTest;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import framework.utils.ExcelDataProvider;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDDTTest extends BaseTest implements ITest {

    private final ThreadLocal<String> testName = new ThreadLocal<>();

    @Override
    public String getTestName() {
        return testName.get();
    }

    private void setTestName(String name) {
        testName.set(name);
    }

    @DataProvider(name = "smokeLoginData")
    public Object[][] smokeLoginData() {
        return ExcelDataProvider.getSmokeCases();
    }

    @DataProvider(name = "negativeAndBoundaryData")
    public Object[][] negativeAndBoundaryData() {
        return ExcelDataProvider.getNegativeAndBoundaryCases();
    }

    @DataProvider(name = "regressionLoginData")
    public Object[][] regressionLoginData() {
        return ExcelDataProvider.getRegressionCases();
    }

    @Test(groups = {"smoke"}, dataProvider = "smokeLoginData")
    public void testLoginSmokeCases(String username,
                                    String password,
                                    String expectedUrl,
                                    String description) {

        setTestName(description);

        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(username, password);

        Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded");
        Assert.assertTrue(
                getDriver().getCurrentUrl().contains(expectedUrl),
                "Current URL should contain expected URL fragment"
        );
    }

    @Test(groups = {"regression"}, dataProvider = "regressionLoginData")
    public void testLoginRegressionCases(String caseType,
                                         String username,
                                         String password,
                                         String expectedResult,
                                         String description) {

        setTestName(description);

        LoginPage loginPage = new LoginPage(getDriver());

        if ("SMOKE".equalsIgnoreCase(caseType)) {
            InventoryPage inventoryPage = loginPage.login(username, password);

            Assert.assertTrue(inventoryPage.isLoaded(), "Inventory page should be loaded");
            Assert.assertTrue(
                    getDriver().getCurrentUrl().contains(expectedResult),
                    "Current URL should contain expected URL fragment"
            );
        } else {
            loginPage.loginExpectingFailure(username, password);

            Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
            Assert.assertTrue(
                    loginPage.getErrorMessage().toLowerCase().contains(expectedResult.toLowerCase()),
                    "Actual error message should contain expected text"
            );
        }
    }
}