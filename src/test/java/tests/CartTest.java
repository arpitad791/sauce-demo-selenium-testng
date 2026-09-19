package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CartPage;
import pages.LoginPage;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Map;

public class CartTest {
    WebDriver driver;
    CartPage cartPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-features=PasswordLeakDetection,PasswordCheck");
        options.addArguments("--incognito");
        options.setExperimentalOption("prefs", Map.of(
                "credentials_enable_service", false,
                "profile.password_manager_enabled", false,
                "profile.password_manager_leak_detection", false
        ));
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");

        // Log in first since cart page requires authentication
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        cartPage = new CartPage(driver);
    }

    @Test
    public void addOneItemShowsBadgeCountOne() {
        cartPage.addFirstItemToCart();
        Assert.assertEquals(cartPage.getCartBadgeCount(), "1");
    }

    @Test
    public void addTwoItemsShowsBadgeCountTwo() {
        cartPage.addFirstTwoItemsToCart();
        Assert.assertEquals(cartPage.getCartBadgeCount(), "2");
    }

    @Test
    public void cartBadgeNotPresentWhenCartEmpty() {
        Assert.assertFalse(cartPage.isCartBadgePresent(), "Badge should not be visible when cart is empty");
    }

    @Test
    public void removingItemUpdatesCart() {
        cartPage.addFirstItemToCart();
        cartPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 1);

        cartPage.removeFirstItemFromCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 0);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}