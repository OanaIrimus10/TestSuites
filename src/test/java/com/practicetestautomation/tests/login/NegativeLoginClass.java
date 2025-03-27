package com.practicetestautomation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeLoginClass {
    @Test
    public void incorrectUserNameTest(){
//        Open page
        WebDriver driver=new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
        driver.manage().window().maximize();

//        Type username incorrectUser into Username field
        WebElement userNameInput=driver.findElement(By.id("username"));
        userNameInput.sendKeys("incorrectUser");

//        Type password Password123 into Password field
        WebElement passwordInput=driver.findElement(By.id("password"));
        passwordInput.sendKeys("Password123");

//        Push Submit button
        WebElement submitButton= driver.findElement(By.id("submit"));
        submitButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        Verify error message is displayed
        WebElement errorMessage= driver.findElement(By.id("error"));
        Assert.assertTrue(errorMessage.isDisplayed());

//        Verify error message text is Your username is invalid!
        String expectedErrorMessage="Your username is invalid!";
        String actualErrorMessage= errorMessage.getText();
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);

        driver.quit();

    }

    @Test
    public void incorrectPasswordTest(){
//        Open page
        WebDriver driver=new FirefoxDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
        driver.manage().window().maximize();

//        Type username student into Username field
        WebElement userNameInput=driver.findElement(By.id("username"));
        userNameInput.sendKeys("student");

//        Type password incorrectPassword into Password field
        WebElement passwordInput=driver.findElement(By.id("password"));
        passwordInput.sendKeys("incorrectPassword");

//        Push Submit button
        WebElement submitButton= driver.findElement(By.id("submit"));
        submitButton.click();

//        Verify error message is displayed
        WebElement passwordErrorMessage= driver.findElement(By.id("error"));
        Assert.assertTrue(passwordErrorMessage.isDisplayed());

//        Verify error message text is Your password is invalid!
        String actualErrorMessage="Your password is invalid!";
        String expectedErrorMessage=passwordErrorMessage.getText();
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);

        driver.quit();
    }
}


