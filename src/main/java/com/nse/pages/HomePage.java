package com.nse.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    TopHeaderMenuPage topHeaderMenu;

    // Locators
    private By nseLogo = By.xpath("*//div[@class='main-logo-menu']//a[contains(@class, 'navbar-brand')]//img[contains(@class, 'main_logo')]");
    private By searchBar = By.xpath("*//input[contains(@class, 'rbt-input-main')]");

    public HomePage(WebDriver driver) {
        super(driver);
        this.topHeaderMenu = new TopHeaderMenuPage(driver);
    }

    // Page Actions
    public void waitForHomePageLoad() {
        waitForPageLoad(nseLogo);
        topHeaderMenu.waitForTopNavigationMenu();
    }

    public boolean isNSELogoDisplayed() {
        return getElement(nseLogo).isDisplayed();
    }

    public boolean isSearchBarVisible() {
        return getElement(searchBar).isDisplayed();
    }

    public IndicesPage goToIndicesPage() {
        waitForHomePageLoad();
        topHeaderMenu.goToIndicesPageFromTopMenuItems();
        return getInstance(IndicesPage.class);
    }
}
