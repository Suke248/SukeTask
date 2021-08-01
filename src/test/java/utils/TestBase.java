package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import libraries.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBase {
    public static Map<String, String> world = new HashMap<>();
    public static Map<String, List<String>> worldList = new HashMap<>();
    public RequestSpecification req;
    public ResponseSpecification resSpec;
    public static Response response;
    public Logger logger = LogManager.getLogger(TestBase.class);
    public static WebDriver driver;
    public static int expWait;
    public static String browser;
    public static ExtentReports reports;
    public static ExtentTest test;
    public static ExtentHtmlReporter htmlReporter;
    public static String reportName = null;
    private Scenario scenario;

    public boolean sendKeys(By element, String text) {
        try {
            driver.findElement(element).clear();
            toHighlight(element);
            logger.info(text + " entered in element: " + element);
            driver.findElement(element).sendKeys(text);
        } catch (Exception e) {
            logger.error("Element Not clicked " + e);
        }
        return true;
    }

    public boolean click(By element) throws Exception {
        try {
            logger.info("Clicking object using By element : " + element);
            try {
                waitUntilElementIsClickable(element, expWait);
            } catch (Exception e) {
                e.printStackTrace();
            }
            logger.info("Element is clickable with condition Implict wait");
            WebElement webElement = driver.findElement(element);
            if (webElement.isEnabled() && webElement.isDisplayed()) {
                logger.info("Element is enabled or displayed in page");
                toHighlight(element);
                webElement.click();
                return true;
            } else {
                logger.error("Element is not enabled or displayed for click, will try javascript click next.");
                return clickElementUsingJavaScript(element);
            }
        } catch (ElementNotInteractableException e) {
            logger.error("Element not interactable during click " + e.getMessage());
        } catch (WebDriverException e) {
            e.printStackTrace();
        }
        return clickElementUsingJavaScript(element);
    }

    public boolean click(WebElement webElement) {
        try {
            logger.info("Clicking object using By element : " + webElement);
            try {
                waitUntilElementIsClickable(webElement, expWait);
            } catch (Exception e) {

            }
            logger.info("Element is clickable with condition Implict wait");

            if (webElement.isEnabled() && webElement.isDisplayed()) {
                logger.info("Element is enabled or displayed in page");
                webElement.click();
                return true;
            }
        } catch (WebDriverException e) {
            e.printStackTrace();
            logger.error("WebDriver exception during click " + e.getMessage());
            if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL")) {
                new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.elementToBeClickable(webElement)).click();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean waitUntilElementIsClickable(By locator, long seconds) {
        WebElement element = null;
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            logger.info("Failed to wait for element to be clickable");
            throw e;
        }
        return true;
    }

    public boolean waitUntilElementIsClickable(WebElement element, long seconds) throws Exception {
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.info("Failed to wait for element to be clickable");
            throw e;
        }
        return true;
    }

    public boolean clickElementUsingJavaScript(By locator) throws Exception {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        try {
            try {
                waitUntilElementIsClickable(locator, expWait);
            } catch (Exception e) {
                // do nothing, continue to try and click element
            }
            jse.executeScript("arguments[0].click();", driver.findElement(locator));

            return true;
        } catch (TimeoutException e) {
            throw new Exception("Element " + locator.toString() + " was not found\n" + e.getMessage(), e);
        } catch (WebDriverException e) {
            if (e.getMessage().contains("JavaScript error")) {
                logger.warn("Skipping exception with JavaScript error");
            } else if (!e.getMessage().contains("Missing Template ERR_CONNECT_FAIL")) {
                logger.info("Failed to click: " + locator + " by javascript. Retrying..");
                jse.executeScript("arguments[0].click();",
                        new WebDriverWait(driver, expWait)
                                .until(ExpectedConditions.elementToBeClickable(locator)));
                return true;
            } else
                throw new Exception("Web driver exception clicking element with javascript " + locator.toString() + "\n"
                        + e.getMessage());
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isElementCurrentlyDisplayed(By element) throws Exception {
        boolean isDisplayed = false;
        List<WebElement> elementList = driver.findElements(element);
        if (elementList.size() <= 0) {
            return false;
        } else if (elementList.size() > 1) {
            throw new Exception("Error: Found multiple elements");
        } else {
            WebElement foundElement = elementList.get(0);
            if (foundElement.isDisplayed()) {
                isDisplayed = true;
            }
            return isDisplayed;
        }
    }

    public String getTextFromElement(By element) throws Exception {
        try {
            logger.info("Getting text from element : " + element + "");
            String innerText = new WebDriverWait(driver, expWait)
                    .until(ExpectedConditions.visibilityOfElementLocated(element)).getText().trim();
            logger.info("The Inner Text Of An Element is : " + innerText);
            return innerText;
        } catch (StaleElementReferenceException e) {
            return new WebDriverWait(driver, expWait)
                    .until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(element)))
                    .getText();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getMessage());
            if (e.getMessage().contains("Missing Template ERR_CONNECT_FAIL"))
                return new WebDriverWait(driver, expWait)
                        .until(ExpectedConditions.visibilityOfElementLocated(element)).getText();
            else
                throw new Exception(e);
        }
    }

    public boolean waitForElementToDisplay(By locator, long maxSecondsToWait) {
        for (int i = 0; i < maxSecondsToWait; i++) {
            try {
                Thread.sleep(1000);
                if (isElementCurrentlyDisplayed(locator)) {
                    return true;
                }
            } catch (Exception e) {
            }
        }
        return false;
    }

    public boolean waitForElementToDisplay(WebElement locator, long maxSecondsToWait) {
        for (int i = 0; i < maxSecondsToWait; i++) {
            try {
                Thread.sleep(1000);
                if (locator.isDisplayed()) {
                    return true;
                }
            } catch (Exception e) {
                // do nothing, let it keep looping to wait for object
            }
        }
        return false;
    }

    public void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
        }
    }

    private void toHighlight(By element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style','background:yellow;border: 2px solid red;');", driver.findElement(element));
    }

    protected boolean selectByVisibleText(By element, String str) {
        try {
            waitForElementToDisplay(element, 20);
            WebElement ele = driver.findElement(element);
            Actions action = new Actions(driver);
            action.moveToElement(ele).perform();
            Select select = new Select(ele);
            select.selectByVisibleText(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public RequestSpecification requestSpecification() {
        req = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        return req;
    }

}