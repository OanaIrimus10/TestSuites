package com.practicetestautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SuccessfulLoginPage extends BasePage {
    private By logoutButtonLocator= By.linkText("Log out") ;


    public SuccessfulLoginPage(WebDriver driver){ //driver - paramater
        super(driver);
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public String getPageSource(){
        return driver.getPageSource();
    }

    public boolean isLogoutButtonDisplayed(){
       return isDisplayed(logoutButtonLocator);

    }

}
