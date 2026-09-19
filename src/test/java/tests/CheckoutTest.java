package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Map;

public class CheckoutTest {
    WebDriver driver;
    CartPage cartPage;
    CheckoutPage checkoutPage;

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

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void completeCheckoutFlowSuccessfully() {
        // Add item, go to cart, proceed to checkout
        cartPage.addFirstItemToCart();
        cartPage.goToCart();
        checkoutPage.clickCheckout();

        // Fill shipping details
        checkoutPage.fillShippingInfo("Rahul", "Sharma", "440001");

        // Finish order
        checkoutPage.finishOrder();

        // Verify confirmation message
        String confirmation = checkoutPage.getConfirmationMessage();
        Assert.assertEquals(confirmation, "Thank you for your order!");
    }

    @Test
    public void checkoutFailsWithMissingFirstName() {
        cartPage.addFirstItemToCart();
        cartPage.goToCart();
        checkoutPage.clickCheckout();

        // Leave first name blank, only fill last name and zip
        checkoutPage.fillShippingInfo("", "Sharma", "440001");

        String error = checkoutPage.getErrorMessage();
        Assert.assertTrue(error.contains("First Name is required"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}