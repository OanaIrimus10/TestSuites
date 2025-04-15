package com.practicetestautomation.tests.login;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginClass {
    private WebDriver driver;
    private Logger logger;


    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        logger= Logger.getLogger(LoginClass.class.getName());
        logger.setLevel(Level.INFO);
        logger.info("Running tests in " + browser);
        switch (browser.toLowerCase()) {
        case "chrome":
            driver=new ChromeDriver();
            break;
        case  "firefox":
            driver =new FirefoxDriver();
            break;
        default:
            logger.warning("Configuration for " + browser+ "is missing , so tests will run in Chrome ");
            driver =new ChromeDriver();
            break;
    }

    //Open page
    driver.get("https://practicetestautomation.com/practice-test-login/");
    driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
    driver.quit();
    logger.info("Browser is closed");
    }

    @Test (groups={"positive", "regression", "smoke"})
    public void testLoginFunctionality(){
        logger.info("Starting testLoginFunctionality");
        //Type username student into Username field
        WebElement userNameInput=driver.findElement(By.id("username"));
        logger.info("Type username");
        userNameInput.sendKeys("student");

        //Type password Password123 into Password field
        WebElement passwordInput=driver.findElement(By.id("password"));
        logger.info("Type password");
        passwordInput.sendKeys("Password123");

        //facem scroll
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,350)", "");

        //Push Submit button
        //WebElement submitButton= driver.findElement(By.id("submit"));
        WebElement submitButton= driver.findElement(By.id("submit"));
        logger.info("Click submit button");
        submitButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        //Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        logger.info("Verify login functionality");

        String expectedUrl="https://practicetestautomation.com/logged-in-successfully/";
        String actualUrl= driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);

        //Verify new page contains expected text ('Congratulations' or 'successfully logged in')

        String expectedMessage="Congratulations student. You successfully logged in!";
        String pageSource= driver.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedMessage));

        //Verify button Log out is displayed on the new page
        WebElement logOutButton=driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logOutButton.isDisplayed());


    }

    @Parameters({"username", "password", "expectedErrorMessage"})
    @Test (groups={"negative", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage){
        logger.info("Starting negativeLoginTest");

//        Type username incorrectUser into Username field
        WebElement userNameInput=driver.findElement(By.id("username"));
        logger.info("Type username"+ username);
        userNameInput.sendKeys(username);

//        Type password Password123 into Password field
        WebElement passwordInput=driver.findElement(By.id("password"));
        logger.info("Type password");
        passwordInput.sendKeys(password);

//        Push Submit button
        WebElement submitButton= driver.findElement(By.id("submit"));
        logger.info("Click submit button");
        submitButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        Verify error message is displayed
        WebElement errorMessage= driver.findElement(By.id("error"));
        Assert.assertTrue(errorMessage.isDisplayed());

        logger.info("Verify the error message"+ expectedErrorMessage);
        //Verify error message text is Your username is invalid!
        String actualErrorMessage= errorMessage.getText();
        Assert.assertEquals(actualErrorMessage,expectedErrorMessage);

    }

}
