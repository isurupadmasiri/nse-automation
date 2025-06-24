package com.nse.pages;

import com.nse.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EquityPage extends BasePage {
    // Locators
    private final By equityTitle = By.xpath("*//h1[@id='quoteName']//span[1]");
    private final By priceInformationTable = By.xpath("*//table[contains(@class, 'card-table')]/thead/tr/th[@id='priceInformationHeading']");
    // Dynamic String xpath expressions
    private final String xStrPriceInformationTable = "*//table[contains(@class, 'card-table')]/thead/tr/th[@id='priceInformationHeading']//ancestor::table//tbody/tr/td";
    TopHeaderMenuPage topHeaderMenu;
    private String xStrSymbolTabNav = "*//div[@id='quoteNav']/a[text()='%s']";
    private String xStrWeekHighPriceValue = xStrPriceInformationTable + "/span[contains(text(), '52 Week High')]//ancestor::tr/td[2]/span";
    private String xStrWeekLowPriceValue = xStrPriceInformationTable + "/span[contains(text(), '52 Week Low')]//ancestor::tr/td[2]/span";
    private String xStrUpperBandValue = xStrPriceInformationTable + "/span[@id='upperbandVal']";
    private String xStrLowerBandValue = xStrPriceInformationTable + "/span[@id='lowerbandVal']";
    private String xStrPriceBandValue = xStrPriceInformationTable + "/span[@id='pricebandVal']";
    private String xStrDailyVolatilityValue = xStrPriceInformationTable + "/span[@id='orderBookTradeDV']";
    private String xStrAnnualizedVolatilityValue = xStrPriceInformationTable + "/span[@id='orderBookTradeAV']";
    private String xStrTickSizeValue = xStrPriceInformationTable + "/span[@id='tickSize']";

    public EquityPage(WebDriver driver) {
        super(driver);
        this.topHeaderMenu = new TopHeaderMenuPage(driver);
    }

    // Actions
    public void waitForEquityPageLoadFor(String symbol) {
        String xpath = String.format(xStrSymbolTabNav, symbol);
        waitForPageLoad(By.xpath(xpath));
    }

    public String getEquityTitle() {
        return getElement(equityTitle).getText().trim();
    }

    public boolean isPriceInformationTableDisplayed() {
        return getElement(priceInformationTable).isDisplayed();
    }

    public String get52WeekHighPriceValue() {
        return getElement(By.xpath(xStrWeekHighPriceValue)).getText();
    }

    public String get52WeekLowPriceValue() {
        return getElement(By.xpath(xStrWeekLowPriceValue)).getText();
    }

    public String getUpperBandValue() {
        return getElement(By.xpath(xStrUpperBandValue)).getText();
    }

    public String getLowerBandValue() {
        return getElement(By.xpath(xStrLowerBandValue)).getText();
    }

    public String getPriceBandValue() {
        return getElement(By.xpath(xStrPriceBandValue)).getText();
    }

    public String getDailyVolatilityValue() {
        return getElement(By.xpath(xStrDailyVolatilityValue)).getText();
    }

    public String getAnnualizedVolatilityValue() {
        return getElement(By.xpath(xStrAnnualizedVolatilityValue)).getText();
    }

    public String getTickSizeValue() {
        return getElement(By.xpath(xStrTickSizeValue)).getText();
    }

    public void printAllPriceInformation() {
        Log.info("\n| Price Information\t\t\t\t\t|\n" + "| --------------------------------- |\n"
                + "| 52 Week High\t\t\t| " + get52WeekHighPriceValue()
                + "\t|\n" + "| 52 Week Low\t\t\t| " + get52WeekLowPriceValue() + "\t|\n"
                + "| Upper Band\t\t\t| " + getUpperBandValue() + " \t|\n"
                + "| Lower Band\t\t\t| " + getLowerBandValue() + " \t|\n"
                + "| Price Band (%)\t\t| " + getPriceBandValue() + " \t|\n"
                + "| Daily Volatility\t\t| " + getDailyVolatilityValue() + " \t\t|\n"
                + "| Annualized Volatility\t| " + getAnnualizedVolatilityValue() + " \t|\n"
                + "| Tick Size\t\t\t\t| " + getTickSizeValue() + " \t\t|");
    }
}
