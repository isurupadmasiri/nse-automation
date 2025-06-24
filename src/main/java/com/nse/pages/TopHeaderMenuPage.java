package com.nse.pages;

import com.nse.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TopHeaderMenuPage {
    WebDriver driver;

    // Locators
    private By topNavigationMenuItem = By.xpath("*//ul[contains(@class, 'navbar-nav me-auto')]");

    // Dynamic String xpath expressions
    private String strNavigationMenuItem = "*//div[@id='navbarSupportedContent']/ul/li/a[text() = '%s']";
    private String strNavigationSubMenuItem = strNavigationMenuItem + "//parent::li//ul/li/a[text()='%s']";

    public TopHeaderMenuPage(WebDriver driver) {
        this.driver = driver;
        waitForTopNavigationMenu();
    }

    // Page Actions
    private void selectMainMenuItem(String menu) {
        String xpath = String.format(strNavigationMenuItem, menu);
        WebElement mainMenuItem = driver.findElement(By.xpath(xpath));
        Actions action = new Actions(driver);
        action.moveToElement(mainMenuItem).click().perform();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private void selectSubMenuItem(String menu, String subMenu) {
        selectMainMenuItem(menu);
        String xpath = String.format(strNavigationSubMenuItem, menu, subMenu);
        waitForSubMenuDisplay(By.xpath(xpath));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.xpath(xpath))).click().perform();
    }

    private void waitForSubMenuDisplay(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForTopNavigationMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(topNavigationMenuItem));
        } catch (Exception e) {
            Log.error("Error loading the top navigation menu");
        }
    }

    public void goToIndicesPageFromTopMenuItems() {
        selectSubMenuItem("Market Data", "Indices");
    }
}
