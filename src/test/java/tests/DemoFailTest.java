package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.base.BaseTest;

public class DemoFailTest extends BaseTest {

    @Test
    public void testFailToCheckScreenshot() {
        String currentUrl = getDriver().getCurrentUrl();

        // Cố ý sai để test fail
        Assert.assertTrue(currentUrl.contains("inventory"),
                "Co y fail de kiem tra screenshot");
    }
}