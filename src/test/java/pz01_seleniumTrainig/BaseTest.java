package pz01_seleniumTrainig;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by dmytro.bezzubikov on 4/23/2015.
 */
public class BaseTest {
    protected static WebDriver driver;

    @Parameters({"browser", "driverPath"})
    @BeforeMethod
    public void initDriver(String browser, @Optional("") String driverPath) throws Exception {
        System.out.println("Your testing browser is: " + browser);
        browser = browser.toLowerCase();
        if (!driverPath.equals("")) {
            System.setProperty("webdriver.chrome.driver", driverPath);
        }
        if (browser.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--test-type");
            driver = new ChromeDriver(options);
        } else if (browser.equals("yandex")) {
            ChromeOptions options = new ChromeOptions();
            options.setBinary(new File("C:\\Users\\dmytro.bezzubikov\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe"));
            driver = new ChromeDriver(options);
        } else if (browser.equals("firefox")) {
            driver = new FirefoxDriver();

        } else if (browser.equals("ie")) {
            driver = new InternetExplorerDriver();
        } else {
            throw new RuntimeException("Please create a driver for " + browser);
        }
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }
}
