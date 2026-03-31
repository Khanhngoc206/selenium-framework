package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    private final By cartItemsBy = By.cssSelector(".cart_item");
    private final By removeButtonsBy = By.cssSelector(".cart_item button");
    private final By checkoutButtonBy = By.id("checkout");
    private final By itemNamesBy = By.cssSelector(".inventory_item_name");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Lấy số lượng item trong giỏ.
     * Nếu không có item nào thì trả về 0.
     *
     * @return số lượng item
     */
    public int getItemCount() {
        return getElements(cartItemsBy).size();
    }

    /**
     * Xóa item đầu tiên trong cart nếu có.
     *
     * @return CartPage
     */
    public CartPage removeFirstItem() {
        List<WebElement> removeButtons = getElements(removeButtonsBy);
        if (!removeButtons.isEmpty()) {
            waitAndClick(removeButtons.get(0));
        }
        return this;
    }

    /**
     * Đi tới trang checkout.
     *
     * @return CheckoutPage
     */
    public CheckoutPage goToCheckout() {
        WebElement checkoutButton = driver.findElement(checkoutButtonBy);
        waitAndClick(checkoutButton);
        waitForPageLoad();
        return new CheckoutPage(driver);
    }

    /**
     * Lấy danh sách tên item trong giỏ.
     *
     * @return List tên item, có thể rỗng
     */
    public List<String> getItemNames() {
        List<WebElement> elements = getElements(itemNamesBy);
        List<String> names = new ArrayList<>();

        for (WebElement element : elements) {
            names.add(element.getText().trim());
        }
        return names;
    }
}