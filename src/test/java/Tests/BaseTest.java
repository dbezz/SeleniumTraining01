package Tests;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dmytro.bezzubikov on 4/23/2015.
 */
public class BaseTest {
    protected static WebDriver driver;
    protected static SoftAssert softAssert = new SoftAssert();

    static {

        Logger.getLogger("").setLevel(Level.ALL);
        for (Handler h : Logger.getLogger("").getHandlers()) {
            Logger.getLogger("").removeHandler(h);
        }
        SLF4JBridgeHandler.install();
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        StatusPrinter.print((LoggerContext) LoggerFactory.getILoggerFactory());
    }

    protected Logger log = Logger.getLogger("TEST");

    protected DecimalFormat df = new DecimalFormat("#.00");
    protected DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    protected Calendar cal = Calendar.getInstance();

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
        } else if (browser.equals("ff")) {
            FirefoxBinary binary = new FirefoxBinary(new File("C:/Program Files (x86)/Mozilla Firefox 24/firefox.exe"));
            FirefoxProfile profile = new FirefoxProfile(new File("C:/Users/dmytro.bezzubikov/AppData/Roaming/Mozilla/Firefox/Profiles/a7ui5dm7.Selenium"));
            driver = new FirefoxDriver(binary,profile);
        } else if (browser.equals("ie")) {
            driver = new InternetExplorerDriver();
        } else {
            throw new RuntimeException("Please create a driver for " + browser);
        }
        driver.manage().window().maximize();
        ((RemoteWebDriver) driver).setLogLevel(Level.INFO);
    }

    @BeforeMethod
    public void logMethodStart(Method m) {
        log.info("Start test method " + m.getName());
    }

    @Parameters({"url","browser"})
    @BeforeMethod
    public void loginToApplication (String url, String browser) throws InterruptedException {
        if (browser.equals("ie")) {
            try {
                Runtime.getRuntime().exec("D:\\projects\\AutoIt\\ABCAuthIE10.exe");
            } catch (IOException e) {
                e.printStackTrace();
            }
            driver.get(url);
            driver.get("javascript:document.getElementById('overridelink').click();");
        }
        else {
            try {
                Runtime.getRuntime().exec("D:\\projects\\AutoIt\\ABCAuth10.exe");
            } catch (IOException e) {
                e.printStackTrace();
            }
            driver.get(url);
         }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }

    @AfterMethod(alwaysRun = true)
    public void logMethodEnd(ITestResult result) throws Exception {
        if (result.isSuccess()) {
            log.info("PASSED: Test method " + result.getMethod().getMethodName());
        } else {
            log.warning("FAILED: Test method " + result.getMethod().getMethodName());
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = "c:\\tmp\\screenshot" + result.getMethod().getMethodName() + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".png";
            FileUtils.copyFile(screenshot, new File(fileName));
            log.info("Screenshot: " + fileName);
        }
    }
}
