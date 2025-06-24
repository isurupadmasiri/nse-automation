package com.nse.pages;

import com.nse.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class BasePage extends Page {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void waitForPageLoad(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            closeNavigationDropdownIfVisible();
            waitForLoad();
        } catch (Exception e) {
            Log.error("Error loading the page");
        }
    }

    @Override
    public WebElement getElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (Exception e) {
            Log.error("Something went wrong while capturing the element" + locator.toString());
            Log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void waitForElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            Log.error("Maximum time reached for element to be visible: " + locator.toString());
        }
    }

    public void closeNavigationDropdownIfVisible() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0);
        actions.build().perform();
    }

    public void switchToNewWindow(String expectedUrl) {
        String currentWindow = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getCurrentUrl().contains(expectedUrl)) {
                Log.info("Switched to page" + driver.getCurrentUrl());
                return;
            }
        }
        driver.switchTo().window(currentWindow);
    }

    public void waitForLoad() {
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public void actionClick(By locator) {
        Actions action = new Actions(driver);
        action.moveToElement(getElement(locator)).click().perform();
    }
}
