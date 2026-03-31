package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    private final By errorMessageBy = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Đăng nhập thành công và chuyển sang InventoryPage.
     *
     * @param username username
     * @param password password
     * @return InventoryPage
     */
    public InventoryPage login(String username, String password) {
        waitAndType(usernameField, username);
        waitAndType(passwordField, password);
        waitAndClick(loginButton);
        waitForPageLoad();
        return new InventoryPage(driver);
    }

    /**
     * Đăng nhập với kỳ vọng thất bại, vẫn ở lại LoginPage.
     *
     * @param username username
     * @param password password
     * @return LoginPage
     */
    public LoginPage loginExpectingFailure(String username, String password) {
        waitAndType(usernameField, username);
        waitAndType(passwordField, password);
        waitAndClick(loginButton);
        return this;
    }

    /**
     * Lấy nội dung thông báo lỗi.
     *
     * @return text lỗi
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Kiểm tra thông báo lỗi có hiển thị không.
     *
     * @return true nếu có hiển thị lỗi
     */
    public boolean isErrorDisplayed() {
        return isElementVisible(errorMessageBy);
    }
}