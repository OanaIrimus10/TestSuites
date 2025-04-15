package com.practicetestautomation.tests.exception;

import com.google.common.base.Verify;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ExceptionsClass {
    private WebDriver driver;
    private Logger logger;


    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser){
        logger= Logger.getLogger(ExceptionsClass.class.getName());
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
    driver.get("https://practicetestautomation.com/practice-test-exceptions");
    driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
    driver.quit();
    logger.info("Browser is closed");
    }

    @Test
    public void noSuchElementExcetionTest() {
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));

        //click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        WebElement row2InputField= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        //Verify Row 2 input field is displayed
        Assert.assertTrue(row2InputField.isDisplayed(), "Row 2 input field is not displayed!");
    }

    @Test
    public void timeoutExceptionTest() {

        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(6));

        //click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        //Wait for 3 seconds for the second input field to be displayed
        WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        //Verify second input field is displayed
        Assert.assertTrue(row2InputField.isDisplayed(), "Row 2 input field is not displayed!");

    }

    @Test
    public void ElementNotInteractableExceptionTest(){

        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(6));

        //click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        //Wait for for the second input field to be displayed
        WebElement row2InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));

        //Type text into the second input field
        row2InputField.sendKeys("Cheese");

        //Verify second input field is displayed
        Assert.assertTrue(row2InputField.isDisplayed(), "Row 2 input field is not displayed!");

        //Push Save button using locator By.name(“Save”)
        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
        saveButton.click();

        //Verify text saved
        WebElement successMessage= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage=successMessage.getText();
        String expectedMessage="Row 2 was saved";

        Assert.assertEquals(actualMessage, expectedMessage, "Message is not expected");
    }

    @Test
    public void InvalidElementStateExceptionTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Clear input field
        WebElement editButton= driver.findElement(By.id("edit_btn"));
        editButton.click();

        WebElement row1InputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']/input")));
        row1InputField.clear();
        row1InputField.sendKeys("Spaghetti");

        //click save button
        WebElement saveButton= driver.findElement(By.xpath("//div[@id='row1']/button[@name='Save']"));
        saveButton.click();

        //Verify text changed
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = successMessage.getText();
        String expectedMessage = "Row 1 was saved";

        Assert.assertEquals(actualMessage, expectedMessage, "Message is not expected");

    }

    @Test
    public void StaleElementReferenceExceptionTest(){
        WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));

        //Find the instructions text element

        //Push add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        //Verify instruction text element is no longer displayed
        Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("add_btn"))), "Instructions text is still displayed");

    }
}