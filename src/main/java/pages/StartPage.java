package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by dmytro.bezzubikov on 4/29/2015.
 */
public class StartPage extends BasePage {
    public StartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[@class='current-user']")
    private WebElement currentUser;

    @FindBy(xpath = "//a[text()='Add expense']")
    private WebElement addExpenseLink;

    @FindBy(xpath = "//strong[@class='name ng-binding' and text()='User']")
    private WebElement selectUserRole;

    public boolean addExpenseDisplayed() {
        return isElementDisplayed(addExpenseLink);
    }

    public UserGridPage selectUserRole()
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(currentUser)).click();
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(selectUserRole)).click();
        UserGridPage userGridPage = new UserGridPage(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,EXPLICIT_TIMEOUT), userGridPage);
        return userGridPage;
    }
}
