package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by dmytro.bezzubikov on 4/29/2015.
 */
public class UserGridPage extends BasePage
{
    public UserGridPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(xpath = "//a[contains(text(),'Add expense')]")
    private WebElement addExpenseList;

    @FindBy(xpath = "//span[@class='ng-binding other-travel-expenses']")
    private WebElement otherTravelExpenses;

    @FindBy(xpath = "//span[@class='ng-binding ng-scope airfare']")
    private WebElement airTravelItem;

    @FindBy(xpath = "//span[@class='ng-binding ng-scope car-rental']")
    private WebElement carRentalItem;

    @FindBy(xpath = "//span[@class='ng-binding ng-scope hotel-stay']")
    private WebElement hotelItem;

    @FindBy(xpath = "//li/span[contains(text(),'Baggage Fee')]")
    private WebElement baggageFeeItem;

    @FindBy(xpath = "//a[text()='Delete']")
    private WebElement buttonDelete;

    @FindBy(xpath = "//span[text()='OK']")
    private WebElement buttonOK;

    @FindBy(xpath = "//div[@class='progressBar']")
    private WebElement progressBar;

    @FindBy(xpath = "//a[text()='Submitted']")
    private WebElement submittedTab;

    @FindBy(xpath = "//a[text()='Unsubmitted']")
    private WebElement unsubmittedTab;

    public void openAddExpenseList()
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(progressBar)));
        addExpenseList.click();
    }
    public BaseForm openBaggageFeeForm()
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(otherTravelExpenses));
        otherTravelExpenses.click();
        return openForm(baggageFeeItem);
    }

    public BaseForm openAirTravelForm()
    {
        openAddExpenseList();
        return openForm(airTravelItem);
    }
    public BaseForm openCarRentalForm()
    {
        return openForm(carRentalItem);
    }

    public BaseForm openHotelForm()
    {
        return openForm(hotelItem);
    }

    public BaseForm openForm(WebElement item)
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.not(ExpectedConditions.visibilityOf(progressBar)));
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(item));
        item.click();
        BaseForm form = new BaseForm(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,EXPLICIT_TIMEOUT), form);
        return form;
    }

    public boolean isRecordPresentByMerchantCityAmount(String merchant, String city, String amount) {
        try {
            (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(.,'" + merchant.toUpperCase() + "') and contains(.,'" + city.toUpperCase() + "')]/../../div[@class='amount']/span[contains(.,'" + amount + "')]")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isRecordPresentByMerchantAmount(String merchant, String amount) {
        try {
            (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(.,'" + merchant.toUpperCase() + "')]/../../div[@class='amount']/span[contains(.,'" + amount + "')]")));
                return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public UserGridPage selectRecordMerchantCityAmount(boolean expected, String merchant, String city, String amount)
    {
        WebElement checkbox= driver.findElement(By.xpath("//span[contains(.,'" + merchant.toUpperCase() +
                "') and contains(.,'" + city.toUpperCase() + "')]/../../div[@class='amount']/span[contains(.,'" +
                amount + "')]/../..//div/input"));
        try {
            if (!checkbox.getAttribute("checked").toString().equals("checked")) {
                if(!expected) checkbox.click();
            }
        } catch (NullPointerException e) {
            if (expected) checkbox.click();
        }
        return this;
    }

    public UserGridPage selectRecordMerchantAmount(boolean expected, String merchant, String amount)
    {
        WebElement checkbox= driver.findElement(By.xpath("//span[contains(.,'" + merchant.toUpperCase() +
                "')]/../../div[@class='amount']/span[contains(.,'" +
                amount + "')]/../..//div/input"));
        try {
            if (!checkbox.getAttribute("checked").toString().equals("checked")) {
                if(!expected) checkbox.click();
            }
        } catch (NullPointerException e) {
            if (expected) checkbox.click();
        }
        return this;
    }

    public void openRecordMerchantCityAmount(String merchant, String city, String amount)
    {
        driver.findElement(By.xpath("//span[contains(.,'" + merchant.toUpperCase() + "') and contains(.,'" + city.toUpperCase() + "')]/../../div[@class='amount']/span[contains(.,'" + amount + "')]")).click();
    }

    public void openRecordMerchantAmount(String merchant, String amount)
    {
        driver.findElement(By.xpath("//span[contains(.,'" + merchant.toUpperCase() + "') ]/../../div[@class='amount']/span[contains(.,'" + amount + "')]")).click();
    }

    public UserGridPage clickDelete()
    {
        buttonDelete.click();
        return this;
    }

    public UserGridPage selectSubmittedTab()
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(submittedTab));
        submittedTab.click();
        return this;
    }

    public UserGridPage selectUnsubmittedTab()
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(unsubmittedTab));
        unsubmittedTab.click();
        return this;
    }

    public void confirmAlert()
    {
        buttonOK.click();
    }
}
