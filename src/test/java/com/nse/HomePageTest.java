package com.nse;

import com.nse.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {
    @Test(description = "Verify that the NSE home page loads successfully")
    public void verifyHomePageLoadsSuccessfully() {
        HomePage homePage = page.getInstance(HomePage.class);
        homePage.waitForHomePageLoad();
        Assert.assertTrue(homePage.isNSELogoDisplayed(), "NSE Logo is not displayed");
        Assert.assertTrue(homePage.isSearchBarVisible(), "Search bar is not displayed");
    }
}
