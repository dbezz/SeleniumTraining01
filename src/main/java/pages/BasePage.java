package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by dmytro.bezzubikov on 4/29/2015.
 */
public class BasePage
{
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isElementDisplayed(WebElement element)
    {
        return  element.isDisplayed();
    }

    public void setText(WebElement element, String text)
    {
        element.sendKeys(text);
    }
}
