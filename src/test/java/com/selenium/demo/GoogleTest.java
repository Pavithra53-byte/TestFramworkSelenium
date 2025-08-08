package com.selenium.demo;

import com.selenium.demo.utils.DriverFactory;
import com.selenium.demo.utils.Constants;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GoogleTest {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = DriverFactory.getDriver();
    }

    @Test
    public void openGoogleHomePage() {
        driver.get(Constants.BASE_URL);
        assert driver.getTitle().contains("Google");
    }

    @AfterClass
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
