package com.nse;

import com.nse.pages.HomePage;
import com.nse.pages.StocksPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class StocksPageTest extends BaseTest {
    public static final String STOCKS_PAGE_TITLE = "Market Watch - Equity/Stock";

    @Test(description = "Verify that the Stocks Page loads successfully", dataProvider = "getDataForStocksPageLoad")
    public void verifyIndicesPageLoadsSuccessfullyForGivenIndex(String index) {
        StocksPage stocksPage = page.getInstance(HomePage.class).goToIndicesPage().openStocksPageForIndex(index);
        stocksPage.waitForStocksPageLoad();
        Assert.assertEquals(stocksPage.getStocksPageTitle(), STOCKS_PAGE_TITLE, "Stocks Page: Incorrect title");
    }

    @DataProvider(name = "getDataForStocksPageLoad")
    public Object[][] getDataForStocksPageLoad() {
        return new Object[][]{
                {"NIFTY 50"}
        };
    }
}
