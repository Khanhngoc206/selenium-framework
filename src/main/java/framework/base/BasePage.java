package framework.base;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Lớp cha cho tất cả Page Object.
 * Chứa các hàm Selenium dùng chung cho framework.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    /**
     * Khởi tạo BasePage với WebDriver hiện tại.
     *
     * @param driver WebDriver của test hiện tại
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Chờ phần tử click được rồi click.
     *
     * @param element phần tử cần click
     */
    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Chờ phần tử hiển thị rồi clear và nhập dữ liệu.
     *
     * @param element phần tử input
     * @param text nội dung cần nhập
     */
    protected void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Chờ phần tử hiển thị rồi lấy text.
     *
     * @param element phần tử cần lấy text
     * @return text đã trim
     */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element))
                .getText()
                .trim();
    }

    /**
     * Kiểm tra phần tử có hiển thị hay không.
     * Trả về false nếu element không tồn tại hoặc DOM bị render lại.
     *
     * @param locator locator của phần tử
     * @return true nếu hiển thị, false nếu không
     */
    protected boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            return false;
        }
    }

    /**
     * Scroll đến phần tử cần thao tác.
     *
     * @param element phần tử cần scroll tới
     */
    protected void scrollToElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    /**
     * Chờ page load xong hoàn toàn.
     */
    protected void waitForPageLoad() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    /**
     * Chờ phần tử hiển thị rồi lấy attribute.
     *
     * @param element phần tử cần lấy attribute
     * @param attr tên attribute
     * @return giá trị attribute
     */
   /**
 * Lấy danh sách phần tử theo locator.
 * Dùng khi số lượng phần tử có thể bằng 0 (không throw exception).
 *
 * @param locator locator của phần tử
 * @return List<WebElement> (có thể rỗng)
 */
protected java.util.List<WebElement> getElements(By locator) {
    return driver.findElements(locator);
}
}