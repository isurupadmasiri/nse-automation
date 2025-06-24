package com.nse.listeners;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.nse.reporting.ExtentReporter;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;

public class TestListeners implements ITestListener {
    ExtentReports extentReport;
    ExtentTest extentTest;

    @Override
    public void onStart(ITestContext context) {
        extentReport = ExtentReporter.generateReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        extentTest = extentReport.createTest(testName);
        extentTest.log(Status.INFO, "Test: " + testName + " has started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.PASS, "Test: " + testName + " has successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();

        // Capture screenshot
        WebDriver driver = null;
        Object testClass = result.getInstance();
        try {
            Field driverField = testClass.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            driver = (WebDriver) driverField.get(testClass);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        File srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Copy screenshot from source to destination folder
        DateUtils.toCalendar(new Date());
        String destScreenshotPath = System.getProperty("user.dir") + "\\test-output" + "\\screenshots\\" + testName + System.currentTimeMillis() + ".png";
        try {
            FileHandler.copy(srcScreenshot, new File(destScreenshotPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Attach the screenshot to extent report
        extentTest.addScreenCaptureFromPath(destScreenshotPath);
        extentTest.log(Status.INFO, result.getThrowable());
        extentTest.log(Status.FAIL, "Test: " + testName + " has failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.INFO, result.getThrowable());
        extentTest.log(Status.SKIP, "Test: " + testName + " has skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();
    }
}
