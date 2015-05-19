package pz01_seleniumTrainig;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.*;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dmytro.bezzubikov on 4/23/2015.
 */
public class BaseTest {
    protected static WebDriver driver;
    static {

        Logger.getLogger("").setLevel(Level.ALL);
        for (Handler h:Logger.getLogger("").getHandlers())
        {
            Logger.getLogger("").removeHandler(h);
        }
        SLF4JBridgeHandler.install();
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        StatusPrinter.print((LoggerContext) LoggerFactory.getILoggerFactory());
    }
    protected Logger log = Logger.getLogger("TEST");

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

            FirefoxBinary binary = new FirefoxBinary(new File("C:/Program Files (x86)/Mozilla Firefox 24/firefox.exe" ));
            FirefoxProfile profile = new FirefoxProfile(new File("C:/Users/dmytro.bezzubikov/AppData/Roaming/Mozilla/Firefox/Profiles/a7ui5dm7.Selenium"));
            driver = new FirefoxDriver(binary, profile);

        } else if (browser.equals("ie")) {
            driver = new InternetExplorerDriver();
        } else {
            throw new RuntimeException("Please create a driver for " + browser);
        }
        driver.manage().window().maximize();
        ((RemoteWebDriver)driver).setLogLevel(Level.INFO);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }
}
