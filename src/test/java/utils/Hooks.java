package utils;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

public class Hooks extends TestBase{

    @Before
    public void InitiateExtentReport(Scenario scenario) {
        test = reports.createTest("Scenario: "+scenario.getName());
    }

    @After
    public void closeExtentReport(Scenario scenario){
        if(scenario.getStatus().toString().equalsIgnoreCase("PASSED")){
            test.info("Test Case completed")   ;
        }else{
            test.info("Test Case Completed")   ;
        }
        reports.flush();
    }

    @AfterStep
    public  void screenshotAfterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            if (driver != null) {
                try {
                    pause(2000);
                    TakesScreenshot ts = (TakesScreenshot) driver;
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "screen1");
                } catch (WebDriverException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}