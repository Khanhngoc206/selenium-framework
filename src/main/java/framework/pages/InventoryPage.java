package framework.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import framework.base.BasePage;

public class InventoryPage extends BasePage {

    @FindBy(id = "inventory_container")
    private WebElement inventoryList;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".inventory_item")
    private List<WebElement> productItems;

    @FindBy(css = ".inventory_item button")
    private List<WebElement> addToCartButtons;

    private final By inventoryListBy = By.id("inventory_container");
    private final By cartBadgeBy = By.cssSelector(".shopping_cart_badge");
    private final By productNameBy = By.cssSelector(".inventory_item_name");
    private final By productItemBy = By.cssSelector(".inventory_item");
    private final By addToCartButtonBy = By.cssSelector(".inventory_item button");
    private final By cartLinkBy = By.cssSelector(".shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Kiểm tra trang inventory đã load đúng chưa.
     *
     * @return true nếu inventory list hiển thị
     */
    public boolean isLoaded() {
        return isElementVisible(inventoryListBy);
    }

    /**
     * Add sản phẩm đầu tiên vào cart.
     *
     * @return InventoryPage để hỗ trợ fluent interface
     */
    public InventoryPage addFirstItemToCart() {
        List<WebElement> buttons = getElements(addToCartButtonBy);
        if (!buttons.isEmpty()) {
            waitAndClick(buttons.get(0));
        }
        return this;
    }

    /**
     * Add sản phẩm theo tên.
     *
     * @param name tên sản phẩm
     * @return InventoryPage
     */
    public InventoryPage addItemByName(String name) {
        List<WebElement> items = getElements(productItemBy);

        for (WebElement item : items) {
            WebElement itemName = item.findElement(productNameBy);
            if (itemName.getText().trim().equalsIgnoreCase(name.trim())) {
                WebElement button = item.findElement(By.tagName("button"));
                waitAndClick(button);
                break;
            }
        }
        return this;
    }

    /**
     * Lấy số lượng item hiển thị trên cart badge.
     * Nếu chưa có badge thì trả về 0.
     *
     * @return số lượng item trong badge
     */
    public int getCartItemCount() {
        if (!isElementVisible(cartBadgeBy)) {
            return 0;
        }
        return Integer.parseInt(getText(cartBadge));
    }

    /**
     * Đi tới trang cart.
     *
     * @return CartPage
     */
    public CartPage goToCart() {
        WebElement cartLink = driver.findElement(cartLinkBy);
        waitAndClick(cartLink);
        waitForPageLoad();
        return new CartPage(driver);
    }
}