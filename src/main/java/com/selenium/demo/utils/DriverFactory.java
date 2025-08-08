package com.selenium.demo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    // Get WebDriver instance (Singleton)
    public static WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    // Initialize WebDriver
    private static void initializeDriver() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER_PATH);
        driver = new ChromeDriver();

        // Set timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        driver.manage().window().maximize();
    }

    // Close and quit WebDriver
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
