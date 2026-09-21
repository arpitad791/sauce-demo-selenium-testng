package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;

public class CartTest extends BaseTest {
    CartPage cartPage;

    @BeforeMethod
    public void loginAndInitCart() {
        // Log in first since cart page requires authentication
        new LoginPage(driver).login("standard_user", "secret_sauce");
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
}
