package com.nse;

import com.nse.pages.EquityPage;
import com.nse.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EquityPageTest extends BaseTest {
    @Test(priority = 1, description = "Verify that the Equity Page loads successfully", dataProvider = "getDataForEquityPageLoad")
    public void verifyEquityPageLoadsSuccessfullyForGivenSymbol(String index, String symbol, String companyName) {
        EquityPage equityPage = page.getInstance(HomePage.class).goToIndicesPage().openStocksPageForIndex(index).openEquityPageFor(symbol);
        equityPage.waitForEquityPageLoadFor(symbol);
        Assert.assertEquals(companyName, equityPage.getEquityTitle());
    }

    @Test(priority = 2, description = "Verify that the Stock Information displays as expected", dataProvider = "getDataForStockInformationDisplay")
    public void verifyStockInformationDisplay(String index, String symbol, String companyName, String priceLow, String priceHigh) {
        EquityPage equityPage = page.getInstance(HomePage.class).goToIndicesPage().openStocksPageForIndex(index).openEquityPageFor(symbol);
        equityPage.waitForEquityPageLoadFor(symbol);

        // Capture screenshot before validations
        takeScreenshotOnDemand();
        Assert.assertEquals(companyName, equityPage.getEquityTitle());
        Assert.assertTrue(equityPage.isPriceInformationTableDisplayed(), "Price Information table is not displayed");

        equityPage.printAllPriceInformation();

        Assert.assertEquals(equityPage.get52WeekLowPriceValue(), priceLow, "52 Week Low price does not match with expected price");
        Assert.assertEquals(equityPage.get52WeekHighPriceValue(), priceHigh, "52 Week High price does not match with expected price");
    }

    @DataProvider(name = "getDataForEquityPageLoad")
    public Object[][] getDataForEquityPageLoad() {
        return new Object[][]{
                {"NIFTY 50", "TATAMOTORS", "TATA MOTORS LIMITED"},
                {"NIFTY 50", "RELIANCE", "RELIANCE INDUSTRIES LIMITED"}
        };
    }

    @DataProvider(name = "getDataForStockInformationDisplay")
    public Object[][] getDataForStockInformationDisplay() {
        return new Object[][]{
                {"NIFTY 50", "TATAMOTORS", "TATA MOTORS LIMITED", "535.75", "1,179.00"},
                {"NIFTY 50", "RELIANCE", "RELIANCE INDUSTRIES LIMITED", "1,114.85", "1,608.80"}
        };
    }
}
