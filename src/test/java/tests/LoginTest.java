package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.Map;

public class LoginTest {
    WebDriver driver;
    LoginPage loginPage;

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
        loginPage = new LoginPage(driver);
    }

    @Test
    public void validLoginTest() {
        loginPage.login("standard_user", "secret_sauce");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Login failed - not on inventory page");
    }

    @Test
    public void invalidLoginTest() {
        loginPage.login("wrong_user", "wrong_pass");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username and password do not match"));
    }

    @Test
    public void lockedOutUserTest() {
        loginPage.login("locked_out_user", "secret_sauce");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("locked out"));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}