package Tests;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pages.BaseForm;
import pages.StartPage;
import pages.UserGridPage;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dmytro.bezzubikov on 4/21/2015.
 */
public class BaggageExpenseTest extends BaseTest
{
    private StartPage startPage;
    private UserGridPage userGridPage;
    private BaseForm baggageFeeForm;

    @Test
    public void BaggageExpenseTestCRUD()
    {
        DecimalFormat df = new DecimalFormat("#.00");

        String personalAmount = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000)) / 100);
        String businessAmount = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000))/100);
        Float amountNum=Float.parseFloat(personalAmount)+Float.parseFloat(businessAmount);
        String amount = df.format(amountNum);

        String personalAmountUpd = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000))/100);
        String businessAmountUpd = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000))/100);
        amountNum=Float.parseFloat(personalAmountUpd)+Float.parseFloat(businessAmountUpd);
        String amountUpd = df.format(amountNum);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();

        String transactionDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String dateUpd = dateFormat.format(cal.getTime());

        String merchant = "American Airlines";
        String city = "Arlington";
        String description = "Description";
        String purpose = "Onsite with Prospect";

        String merchantUpd = "Cathay";
        String cityUpd = "City of Industry";
        String descriptionUpd = "Description updated";
        String purposeUpd = "Conference";

        startPage = PageFactory.initElements(driver, StartPage.class);
        if (!startPage.addExpenseDisplayed())
        {
            userGridPage = startPage.selectUserRole();
        } else
            userGridPage= PageFactory.initElements(driver, UserGridPage.class);

        userGridPage.openAddExpenseList();

        baggageFeeForm = userGridPage.openBaggageFeeForm();
        baggageFeeForm.inputMerchant(merchant).
                inputCity(city).
                inputDescription(description).
                setTransactionDate(transactionDate).
                inputPersonalAmount(personalAmount).
                inputBusinessAmount(businessAmount).
                openPurposesSelector().
                selectItem(purpose);
        baggageFeeForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantCityAmount(merchant, city, amount),
                "Record with merchant:" + merchant + ", city:" + city + " and total amount " + amount + " is added");

        userGridPage.openRecordMerchantCityAmount(merchant, city, amount);
        baggageFeeForm.initPage(baggageFeeForm);

        softAssert.assertEquals(baggageFeeForm.getMerchant(), merchant, "Merchant: " + merchant);
        softAssert.assertTrue(baggageFeeForm.getCity().contains(city), "City: " + city);
        softAssert.assertEquals(baggageFeeForm.getTransactionDate(), transactionDate, "Transaction date: " + transactionDate);
        softAssert.assertEquals(baggageFeeForm.getDescription(), description, "Description:" + description);
        softAssert.assertEquals(baggageFeeForm.getPersonalAmount(), personalAmount, "Personal amount: " + personalAmount);
        softAssert.assertEquals(baggageFeeForm.getBusinessAmount(), businessAmount, "Business amount: " + businessAmount);
        softAssert.assertTrue(baggageFeeForm.getPurpose().contains(purpose), "Purpose: " + purpose);

        baggageFeeForm.
                inputMerchant(merchantUpd).
                inputCity(cityUpd).
                inputDescription(descriptionUpd).
                setTransactionDate(dateUpd).
                inputPersonalAmount(personalAmountUpd).
                inputBusinessAmount(businessAmountUpd).
                openPurposesSelector().
                unselectItem(purpose).
                selectItem(purposeUpd);
        baggageFeeForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantCityAmount(merchantUpd, cityUpd, amountUpd),
                "Record with merchant:" + merchantUpd + ", city:" + cityUpd + " and total amount " + amountUpd + " is present");
        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantCityAmount(merchant, city, amount),
                "Record with merchant:" + merchant + ", city:" + city + " and total amount " + amount + " is not present");

        userGridPage.openRecordMerchantCityAmount(merchantUpd, cityUpd, amountUpd);
        baggageFeeForm.initPage(baggageFeeForm);

        softAssert.assertEquals(baggageFeeForm.getMerchant(), merchantUpd, "Updated merchant: " + merchantUpd);
        softAssert.assertTrue(baggageFeeForm.getCity().contains(cityUpd), "Updated city: " + cityUpd);
        softAssert.assertEquals(baggageFeeForm.getTransactionDate(), dateUpd, "Updated Transaction date: " + transactionDate);
        softAssert.assertEquals(baggageFeeForm.getDescription(), descriptionUpd, "Updated description:" + descriptionUpd);
        softAssert.assertEquals(baggageFeeForm.getPersonalAmount(), personalAmountUpd, "Updated personal amount: "+personalAmountUpd);
        softAssert.assertEquals(baggageFeeForm.getBusinessAmount(), businessAmountUpd, "Updated business amount: " + businessAmountUpd);
        softAssert.assertTrue(baggageFeeForm.getPurpose().contains(purposeUpd), "Updated purpose: " + purposeUpd);

        baggageFeeForm.Cancel();
        userGridPage.selectRecordMerchantCityAmount(true, merchantUpd, cityUpd, amountUpd);
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantCityAmount(merchantUpd, cityUpd, amountUpd),
                "Record with merchant:" + merchantUpd + ", city:" + cityUpd + " and total amount " + amountUpd + " is not present");

        softAssert.assertAll();
    }
}
