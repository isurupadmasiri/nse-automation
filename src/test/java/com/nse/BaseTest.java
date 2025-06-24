package com.nse;

import com.nse.pages.BasePage;
import com.nse.pages.Page;
import com.nse.utils.ConfigReader;
import com.nse.utils.Log;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BaseTest {
    public Page page;
    WebDriver driver;

    @BeforeMethod
    @Parameters(value = {"browser"})
    public void setup(String browser) {
        Log.info("Loading config file");
        ConfigReader config = new ConfigReader();

        // Initiate driver instance
        Log.info("Starting the " + browser + " browser instance");
        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--disable-extensions");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("media.autoplay.default", 0);
            driver = new FirefoxDriver(options);
        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            options.setCapability("ms:edgeOptions", Map.of(
                    "args", List.of(
                            "--disable-blink-features=AutomationControlled",
                            "--disable-extensions"
                    )
            ));
            driver = new EdgeDriver(options);
        } else {
            Log.error("Invalid browser parameter");
        }

        Log.info("Reading BaseURL from config");
        String url = config.getBaseUrl();

        // Fetching the url
        Log.info("Fetching URL: " + url);
        driver.manage().window().maximize();
        driver.get(url);
        page = new BasePage(driver);
    }

    @AfterMethod
    public void teardown() {
        Log.info("Closing the browser instance");
        driver.quit();
    }

    public void takeScreenshotOnDemand() {
        Log.info("On demand screenshot capture started");
        String testName = Reporter.getCurrentTestResult().getMethod().getMethodName();
        String screenshotName = "on_start_capture_" + testName + System.currentTimeMillis() + ".png";
        Log.info("Capturing screenshot before validations and will be saved in test-output/on-start-screenshots directory with name " + screenshotName);
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String destScreenshotPath = System.getProperty("user.dir") + "\\test-output" + "\\on-start-screenshots\\" + "on_start_capture_" + testName + System.currentTimeMillis() + ".png";
            FileHandler.copy(screenshot, new File(destScreenshotPath));
            Log.info("Screenshot saved in test-output/on-start-screenshots directory with name " + screenshotName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
