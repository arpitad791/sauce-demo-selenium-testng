package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class CartPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By addToCartButtons = By.cssSelector("button[data-test^='add-to-cart']");
    By cartBadge = By.className("shopping_cart_badge");
    By cartLink = By.className("shopping_cart_link");
    By removeButtons = By.cssSelector("button[data-test^='remove']");
    By cartItems = By.className("cart_item");
    By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void addFirstItemToCart() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtons));
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        buttons.get(0).click();
    }

    public void addFirstTwoItemsToCart() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtons));
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        buttons.get(0).click();
        // Note: after clicking, this button becomes "Remove", so re-fetch the list
        buttons = driver.findElements(addToCartButtons);
        buttons.get(0).click();
    }

    public String getCartBadgeCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public boolean isCartBadgePresent() {
        return driver.findElements(cartBadge).size() > 0;
    }

    public void goToCart() {
        driver.findElement(cartLink).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(checkoutButton));
    }

    public void removeFirstItemFromCart() {
        int before = getCartItemCount();
        driver.findElement(removeButtons).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(cartItems, before - 1));
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }
}
