package com.nse.pages;

import com.nse.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class StocksPage extends BasePage {
    TopHeaderMenuPage topHeaderMenu;
    ConfigReader config = new ConfigReader();

    // Locators
    private By stocksTitle = By.xpath("*//h1[@id='Market-title']");
    private By stocksTable = By.xpath("*//table[@id='equityStockTable']/tbody");

    // Dynamic String xpath expressions
    private String xStrSymbol = "*//table[@id='equityStockTable']/tbody/tr/td/a[text()='%s']";

    public StocksPage(WebDriver driver) {
        super(driver);
        this.topHeaderMenu = new TopHeaderMenuPage(driver);
    }

    // Actions
    public void waitForStocksPageLoad() {
        waitForPageLoad(stocksTable);
    }

    public String getStocksPageTitle() {
        return getElement(stocksTitle).getText().trim();
    }

    public EquityPage openEquityPageFor(String companyName) {
        waitForStocksPageLoad();
        By locator = By.xpath(String.format(xStrSymbol, companyName));
        actionClick(locator);
        switchToNewWindow(config.getEquityPageUrl());
        return getInstance(EquityPage.class);
    }
}
