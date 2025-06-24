package com.nse.pages;

import com.nse.utils.ConfigReader;
import com.nse.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IndicesPage extends BasePage {
    TopHeaderMenuPage topHeaderMenu;
    ConfigReader config = new ConfigReader();

    // Locators
    private By indicesTitle = By.xpath("*//h1[@id='indicies_title']");
    private By indicesTable = By.xpath("*//table[@id='liveindexTable']/tbody");

    // Dynamic String xpath expressions
    private String xStrIndexName = "*//table[@id='liveindexTable']/tbody/tr/td/a[text()='%s']";

    public IndicesPage(WebDriver driver) {
        super(driver);
        this.topHeaderMenu = new TopHeaderMenuPage(driver);
    }

    // Actions
    public void waitForIndicesPageLoad() {
        waitForPageLoad(indicesTable);
    }

    public String getIndicesPageTitle() {
        return getElement(indicesTitle).getText().trim();
    }

    public StocksPage openStocksPageForIndex(String index) {
        waitForIndicesPageLoad();
        By locator = By.xpath(String.format(xStrIndexName, index));
        actionClick(locator);
        switchToNewWindow(config.getStocksPageUrl());
        return getInstance(StocksPage.class);
    }
}
