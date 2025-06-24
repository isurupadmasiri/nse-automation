package com.nse.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.nse.utils.ConfigReader;

import java.io.File;

public class ExtentReporter {
    public static ExtentReports generateReports() {
        ExtentReports extentReport = new ExtentReports();
        File extentReportFile = new File(System.getProperty("user.dir") + "\\test-output\\extent-reports\\extent-report.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

        // Set configuration
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setReportName("NSE Automated Test Report");
        sparkReporter.config().setDocumentTitle("NSE Automation Results");
        sparkReporter.config().setTimeStampFormat("MM/dd/yyyy hh:mm:ss");

        extentReport.attachReporter(sparkReporter);

        // Setting test properties to display in report
        ConfigReader config = new ConfigReader();
        extentReport.setSystemInfo("Application URL: ", config.getBaseUrl());
        extentReport.setSystemInfo("Test Default Timeout: ", String.valueOf(config.getTimeout()));
        extentReport.setSystemInfo("Operating System: ", System.getProperty("os.name"));
        extentReport.setSystemInfo("Java Version System: ", System.getProperty("java.version"));
        return extentReport;
    }
}
