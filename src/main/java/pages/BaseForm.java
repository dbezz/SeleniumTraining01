package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Created by dmytro.bezzubikov on 7/27/2015.
 */
public class BaseForm extends BasePage
{
    public BaseForm(WebDriver driver)
    {
        super(driver);
    }

    public BaseForm initPage(BaseForm page)
    {
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, EXPLICIT_TIMEOUT), page);
        return this;
    }

    @FindBy(xpath = "//div[starts-with(@class,'booking')]//span[@class='arrow']")
    private WebElement openBookingsSelectArrow;

    @FindBy(xpath = "//div[starts-with(@class,'booking')]/div/strong")
    private WebElement submittedBooking;

    @FindBy(xpath = "//div[starts-with(@class,'booking')]//em")
    private WebElement selectedBooking;

    @FindBy(xpath = "//div[@class='type-col']//div/span")
    private WebElement selectedExpenseType;

    @FindBy(xpath = "//td//div[@name='purpose']//em")
    private WebElement selectedPurpose;

    @FindBy(xpath = "//td//div[@name='booking']//em")
    private WebElement selectedTravelProgramBooking;

    @FindBy(xpath = "//li//input[@name='ticketNo']")
    private WebElement ticketNoInput;

    @FindBy(xpath = "//li//input[@name='merchantInput']")
    private WebElement merchantInput;

    @FindBy(xpath = "//li//input[@name='ct']")
    private WebElement cityInput;

    @FindBy(xpath = "//input[@name='pickUpDate']")
    private WebElement pickUpDate;

    @FindBy(xpath = "//input[@name='dropOffDate']")
    private WebElement dropOffDate;

    @FindBy(xpath = "//input[@name='pickUpLocation']")
    private WebElement pickUpLocation;

    @FindBy(xpath = "//input[@name='dropOffLocation']")
    private WebElement dropOffLocation;

    @FindBy(xpath = "//div[@class='from-col']//input")
    private WebElement fromInput;

    @FindBy(xpath = "//div[@class='from-col']//input/preceding-sibling::span")
    private WebElement fromSpan;

    @FindBy(xpath = "//div[@class='to-col']//input")
    private WebElement toInput;

    @FindBy(xpath = "//div[@class='to-col']//input/preceding-sibling::span")
    private WebElement toSpan;

    @FindBy(name = "dateInput")
    private WebElement transactionDateInput;

    @FindBy(xpath = "//div[@class='description-col']/input")
    private WebElement descriptionInput;

    @FindBy(xpath = "//div[@class='note-col']/input")
    private WebElement noteInput;

    @FindBy(xpath = "//div[@class='description-col']/input/preceding-sibling::span")
    private WebElement descriptionSpan;

    @FindBy(xpath = "//div[@class='note-col']/input/preceding-sibling::span")
    private WebElement noteSpan;

    @FindBy(xpath = "//div[@class='carrier-col']//input")
    private WebElement carrierInput;

    @FindBy(xpath = "//div[@class='carrier-col']//input/preceding-sibling::span")
    private WebElement carrierSpan;

    @FindBy(xpath = "//div[@class='flight-col']//input")
    private WebElement flightInput;

    @FindBy(xpath = "//div[@class='flight-col']//input/preceding-sibling::span")
    private WebElement flightSpan;

    @FindBy(xpath = "//div[@class='date-col']//div/input")
    private WebElement travelDateInput;

    @FindBy(xpath = "//div[@class='date-col']//div/input/../preceding-sibling::span")
    private WebElement travelDateSpan;

    @FindBy(xpath = "//div[starts-with(@class,'personal-amt-col')]//input")
    private WebElement personalAmountInput;

    @FindBy(xpath = "//div[starts-with(@class,'personal-amt-col')]//input/preceding-sibling::span")
    private WebElement personalAmountSpan;

    @FindBy(xpath = "//div[starts-with(@class,'business-amt-col')]//input")
    private WebElement businessAmountInput;

    @FindBy(xpath = "//div[starts-with(@class,'business-amt-col')]//input/preceding-sibling::span")
    private WebElement businessAmountSpan;

    @FindBy(xpath = "//p[text()='Purpose:']/../following-sibling::td//span")
    private WebElement purposesSelectOpenArrow;

    @FindBy(xpath = "//p[text()='Travel Program Booking:']/../following-sibling::td//span")
    private WebElement travelProgramBookingOpenArrow;

    @FindBy(xpath = "//div[@class='select-drop']")
    private WebElement activeSelect;

    @FindBy(xpath = "//div[@class='select-drop up']")
    private WebElement purposeSelect;

    @FindBy(xpath = "//div[@class='select-drop']")
    private WebElement expenseSelect;

    @FindBy(xpath = "//div[@class='type-col']//span[@class='arrow']")
    private WebElement expenseTypeSelectOpenArrow;

    @FindBy(xpath = "//textarea[contains(@name,'eason')]")
    private WebElement outsideReasonInput;


    @FindBy(xpath = "//div[@class='modal-bottombar']//button/span[text()='Save']")
    private WebElement buttonSave;

    @FindBy(xpath = "//div[@class='modal-bottombar']//button/span[text()='Submit']")
    private WebElement buttonSubmit;

    @FindBy(xpath = "//div[@class='modal-bottombar']//button/span[text()='Cancel']")
    private WebElement buttonCancel;

    @FindBy(xpath = "//div[@class='modal-bottombar']//button/span[text()='Revert']")
    private WebElement buttonRevert;

    @FindBy(xpath = "//span[contains(@ng-show,'serverActionInProgress')]/em")
    private WebElement actionInProgress;

    public BaseForm inputTicketNumber(String name)
    {
        setText(ticketNoInput, name);
        return this;
    }

    public BaseForm setPickUpDate(String name)
    {
        setText(pickUpDate, name);
        return this;
    }

    public BaseForm setdropOffDate(String name)
    {
        setText(dropOffDate, name);
        return this;
    }

    public BaseForm selectBooking(String name)
    {
        openBookingsSelectArrow.click();
        driver.findElement(By.xpath("//span[text()='" + name + "']")).click();
        return this;
    }
    public BaseForm inputMerchant(String name)
    {
        return inputWithSearch(merchantInput,name);
    }

    public BaseForm inputPickUpLocation(String name)
    {
        return inputWithSearch(pickUpLocation,name);
    }

    public BaseForm inputDropOffLocation(String name)
    {
        return inputWithSearch(dropOffLocation,name);
    }

    public BaseForm inputWithSearch(WebElement element,String name) {
        setText(element, name);
        element.sendKeys(Keys.BACK_SPACE);
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,'" + name + "')]")));
        element.sendKeys(name.substring(name.length()-1));
        element.sendKeys(Keys.BACK_SPACE);
        element.sendKeys(Keys.ENTER);
        return this;
    }
    public BaseForm inputCity(String name)
    {
        return inputWithSearch(cityInput,name);
    }
    public BaseForm setCarrier(String name)
    {
        return inputWithSearch(carrierInput,name);
    }
    public BaseForm setFrom(String name)
    {
        return inputWithSearch(fromInput,name);
    }
    public BaseForm setTo(String name)
    {
        return inputWithSearch(toInput,name);
    }
    public BaseForm setTravelDate(String name)
    {
        try {
            setText(travelDateInput, name);

        } catch (InvalidElementStateException e)
        {
            travelDateSpan.click();
            setText(travelDateInput, name);
        }
        return this;
    }
    public BaseForm inputFlight(String name)
    {
        try {
            setText(flightInput, name);

        } catch (InvalidElementStateException e)
        {
            flightSpan.click();
            setText(flightInput, name);
        }
        return this;
    }
    public BaseForm inputDescription(String name)
    {
        try {
            setText(descriptionInput, name);

        } catch (InvalidElementStateException e)
        {
            descriptionSpan.click();
            setText(descriptionInput, name);
        }
        return this;
    }
    public BaseForm inputNote(String name)
    {
        try {
            setText(noteInput, name);

        } catch (InvalidElementStateException e)
        {
            noteSpan.click();
            setText(noteInput, name);
        }
        return this;
    }
    public BaseForm inputPersonalAmount(String name)
    {
        try {
            setText(personalAmountInput, name);

        } catch (InvalidElementStateException e)
        {
            personalAmountSpan.click();
            setText(personalAmountInput, name);
        }
        return this;
    }
    public BaseForm inputBusinessAmount(String name)
    {
        try {
            setText(businessAmountInput, name);

        } catch (InvalidElementStateException e)
        {
            businessAmountSpan.click();
            setText(businessAmountInput, name);
        }
        return this;
    }

    public BaseForm inputOutsideReason(String reason)
    {
        if (!(reason==null))
        {
            if (outsideReasonInput.isDisplayed())
            {
                outsideReasonInput.sendKeys(Keys.CONTROL+"a");
                outsideReasonInput.sendKeys(Keys.DELETE);
                outsideReasonInput.sendKeys(reason);
                merchantInput.sendKeys("");
            }
            else
            {
                //log some warning - "a rule  must have been turned off"
            }
        }
        return this;
    }

    public String getMerchant()
    {
        return merchantInput.getAttribute("value");
    }
    public String getPickUpDate()
    {
        return pickUpDate.getAttribute("value");
    }
    public String getDropOffDate()
    {
        return dropOffDate.getAttribute("value");
    }
    public String getPickUpLocation()
    {
        return pickUpLocation.getAttribute("value");
    }
    public String getDropOffLocation()
    {
        return dropOffLocation.getAttribute("value");
    }
    public String getTicketNumber()
    {
        return ticketNoInput.getAttribute("value");
    }

    public String getBooking()
    {
        if (selectedBooking.isDisplayed())
        {
            return selectedBooking.getText();
        }
        return submittedBooking.getText();
    }

    public String getSubmittedBooking()
    {
        return submittedBooking.getText();
    }

    public String getExpenseType()
    {
        return selectedExpenseType.getText();
    }

    public String getPurpose()
    {
        return selectedPurpose.getText();
    }

    public String getOutReason()
    {
        return outsideReasonInput.getAttribute("value");
    }

    public String getTravelProgramBooking()
    {
        return selectedTravelProgramBooking.getText();
    }

    public String getTravelDate()
    {
        return travelDateSpan.getText();
    }

    public String getCarrier()
    {
        return carrierSpan.getText();
    }

    public String getFlight()
    {
        return flightSpan.getText();
    }

    public String getFrom()
    {
        return fromSpan.getText();
    }

    public String getTo()
    {
        return toSpan.getText();
    }

    public String getCity()
    {
        return cityInput.getAttribute("value");
    }
    public String getDescription()
    {
        return descriptionInput.getAttribute("value");
    }
    public String getNote()
    {
        return noteInput.getAttribute("value");
    }
    public String getPersonalAmount()
    {
        return personalAmountInput.getAttribute("value");
    }
    public String getBusinessAmount()
    {
        return businessAmountInput.getAttribute("value");
    }
    public BaseForm openPurposesSelector()
    {
        openSelector(purposesSelectOpenArrow);
        return this;
    }
    public BaseForm closePurposesSelector()
    {
        closeSelector(purposesSelectOpenArrow);
        return this;
    }
    public BaseForm openTravelProgramSelector()
    {
        openSelector(travelProgramBookingOpenArrow);
        return this;
    }
    public BaseForm closeTravelProgramSelector()
    {
        closeSelector(travelProgramBookingOpenArrow);
        return this;
    }
    public void openSelector(WebElement element)
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        try
        {
            if (activeSelect.isDisplayed())
                return;
        }
        catch(NoSuchElementException e)
        {
            element.click();
        }

    }
    public void closeSelector(WebElement element)
    {
        try
        {
            if (activeSelect.isDisplayed())
                element.click();
        }
        catch(NoSuchElementException e)
        {
            return;
        }
    }
    public BaseForm clickItem(String item)
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.elementToBeClickable(activeSelect.findElement(By.xpath("./div/ul//div/div[contains(.,'" + item + "')]/preceding-sibling::div/div"))));
        activeSelect.findElement(By.xpath("./div/ul//div/div[contains(.,'" + item + "')]/preceding-sibling::div/div")).click();
        return this;
    }
    public BaseForm clickTravelProgramBookingItem(String item)
    {
        activeSelect.findElement(By.xpath(".//span[contains(.,'"+item+"')]")).click();
        return this;
    }
    public BaseForm selectExpenseType(String expenseType)
    {
        expenseTypeSelectOpenArrow.click();
        expenseSelect.findElement(By.xpath(".//div//span[contains(.,'" + expenseType + "')]")).click();
        return this;
    }

    public BaseForm selectItem(String item)
    {
        if(!activeSelect.findElement(By.xpath("./div/ul//div[contains(.,'"+item+"')]/preceding-sibling::div/div")).getAttribute("class").contains("checked"))
        {
            clickItem(item);
        }
        return this;
    }
    public BaseForm selectTravelProgramBookingItem(String item)
    {
        clickTravelProgramBookingItem(item);
        return this;
    }

    public BaseForm unselectItem(String item)
    {
        if(activeSelect.findElement(By.xpath("./div/ul//div/div[contains(.,'"+item+"')]/preceding-sibling::div/div")).getAttribute("class").contains("checked"))
        {
            clickItem(item);
        }
        return this;
    }

    public String getTransactionDate()
    {
        return transactionDateInput.getAttribute("value");
    }
    public BaseForm setTransactionDate(String date)
    {
        setText(transactionDateInput,date);
        return this;
    }
    public void waitStopFormSpinner()
    {
        (new WebDriverWait(driver, EXPLICIT_TIMEOUT)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[contains(@ng-show,'serverActionInProgress')]/em")));
    }
    public void saveForm()
    {
        buttonSave.click();
        waitStopFormSpinner();
    }

    public void clickCancel()
    {
        buttonCancel.click();
        waitStopFormSpinner();
    }

    public void clickSubmit()
    {
        buttonSubmit.click();
        waitStopFormSpinner();
    }

    public void clickRevert()
    {
        buttonRevert.click();
    }
}
