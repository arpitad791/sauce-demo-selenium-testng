package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;

public class CheckoutTest extends BaseTest {
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void loginAndInitPages() {
        new LoginPage(driver).login("standard_user", "secret_sauce");
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
}
