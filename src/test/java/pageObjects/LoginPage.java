package pageObjects;

import io.github.bonigarcia.wdm.WebDriverManager;
import libraries.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.TestBase;

public class LoginPage extends TestBase {

    private By userNameEdit;
    private By passwordEdit;
    private By loginButton;

    public LoginPage() {
        InitElements();
    }

    private void InitElements() {
        userNameEdit = By.id("user-name");
        passwordEdit = By.id("password");
        loginButton = By.id("login-button");
    }

    public void openBrowser() {
        try {
            logger.info("Browser selected#" + browser);
            logger.info("Operating System#" + System.getProperty("os.name"));
            test = reports.createTest("UI Feature");
            if (browser.equalsIgnoreCase("chrome")) {
                LaunchChromeBrowser();
            }
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
        } catch (Exception e) {
            logger.error("Browser failed to opened");
        }
    }

    protected void LaunchChromeBrowser() {
        try {
            System.setProperty("webdriver.chrome.silentOutput", "true");
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.addArguments("--disable-dev-shm-usage");
            chromeOptions.setExperimentalOption("useAutomationExtension", false);
            driver = new ChromeDriver(chromeOptions);
            logger.info("Chrome Browser started");
        } catch (Exception e) {
            logger.info(e);
        }
    }

    public boolean navigatetoLoginURL() {
        try {
            String url= ConfigReader.getConfigValue("UIBaseURL");
            driver.get(url);
            logger.info("Loading:\t url");
            test.info("Navigated to URL: "+url);
        } catch (Exception e) {
            logger.error("Unable to navigate to URL");
        }
        return driver.getCurrentUrl().length() > 0;
    }

    public void login(String user, String password) throws Exception {
        assert sendKeys(userNameEdit, user);
        assert sendKeys(passwordEdit, password);
        assert click(loginButton);
        test.info("Logged in with username: "+user+" and password: "+password);
    }


}
