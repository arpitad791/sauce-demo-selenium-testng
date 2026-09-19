package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators - these are the HTML element IDs from saucedemo.com
    By usernameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginButton = By.id("login-button");
    By errorMessage = By.cssSelector("[data-test='error']");
    By inventoryContainer = By.className("inventory_list");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(inventoryContainer),
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ));
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}