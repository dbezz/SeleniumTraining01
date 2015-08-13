package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by dmytro.bezzubikov on 4/29/2015.
 */
public class BasePage
{
    protected WebDriver driver;
    public static final int EXPLICIT_TIMEOUT = 30;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isElementDisplayed(WebElement element)
    {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }



    public void setText(WebElement element, String text)
    {
        element.clear();
        element.sendKeys(text);
    }
}
