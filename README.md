# SauceDemo Test Automation Suite

Automated regression testing suite for [SauceDemo](https://www.saucedemo.com), built using Selenium WebDriver and TestNG, following the Page Object Model (POM) design pattern.

## Tech Stack
- Java 17
- Selenium WebDriver 4.21
- TestNG 7.10
- Maven
- WebDriverManager (automatic browser driver management)

## Test Coverage
- **Login:** valid login, invalid credentials, locked-out user
- **Cart:** add single/multiple items, badge count verification, remove items
- **Checkout:** end-to-end purchase flow, form validation (missing required fields)

## Design Pattern
Uses Page Object Model — each page (`LoginPage`, `CartPage`, `CheckoutPage`) encapsulates its own locators and actions, keeping test logic separate from page interaction. Explicit waits (`WebDriverWait`) handle synchronization on dynamic page transitions.

## How to Run
1. Clone this repo
2. Open in IntelliJ IDEA (or any Java IDE)
3. Run `mvn clean install` to download dependencies
4. Right-click the `tests` package → Run All

## Results
9/9 tests passing.
