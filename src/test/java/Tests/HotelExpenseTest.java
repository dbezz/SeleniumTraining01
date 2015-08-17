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
public class HotelExpenseTest extends BaseTest
{
    private StartPage startPage;
    private UserGridPage userGridPage;
    private BaseForm hotelExpenseForm;

    @Test
    public void HotelExpenseTestCRUD()
    {
        DecimalFormat df = new DecimalFormat("#.00");

        String personalAmount = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000)) / 100);
        String businessAmount = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000))/100);
        Float amountNum=Float.parseFloat(personalAmount)+Float.parseFloat(businessAmount);
        String amount = df.format(amountNum);

        String personalAmountUpd = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000)) / 100);
        String businessAmountUpd = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000)) / 100);
        amountNum=Float.parseFloat(personalAmountUpd)+Float.parseFloat(businessAmountUpd);
        String amountUpd = df.format(amountNum);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();

        String transactionDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String transactionDateUpd = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String travelDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String travelDateUpd = dateFormat.format(cal.getTime());

        String merchant = "Holiday Inn Aber";
        String expenseType = "Food";
        String note = "Description";
        String travelProgramBooking = "Other";
        String purpose = "Onsite with Member";

        String merchantUpd = "Hyatt Grand Aspen";
        String expenseTypeUpd = "Hotel Night";
        String noteUpd = "DescriptionUpd";
        String purposeUpd = "Conference";
        String travelProgramBookingUpd = "Other";


        startPage = PageFactory.initElements(driver, StartPage.class);

        if (!startPage.addExpenseDisplayed())
        {
            userGridPage = startPage.selectUserRole();
        } else
            userGridPage= PageFactory.initElements(driver, UserGridPage.class);

        userGridPage.openAddExpenseList();

        hotelExpenseForm = userGridPage.openHotelForm();

        hotelExpenseForm.
                inputMerchant(merchant).
                setTransactionDate(transactionDate).
                setTravelDate(travelDate).
                selectExpenseType(expenseType).
                inputNote(note).
                inputPersonalAmount(personalAmount).
                inputBusinessAmount(businessAmount).
                openPurposesSelector().
                selectItem(purpose).
                closePurposesSelector().
                openTravelProgramSelector().
                selectTravelProgramBookingItem(travelProgramBooking);
        hotelExpenseForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(merchant, amount),
                "Record with merchant:" + merchant + " and total amount " + amount + " is added");

        userGridPage.openRecordMerchantAmount(merchant, amount);
        hotelExpenseForm.initPage(hotelExpenseForm);

        softAssert.assertTrue(hotelExpenseForm.getMerchant().contains(merchant), "Merchant: " + merchant);
        softAssert.assertEquals(hotelExpenseForm.getTransactionDate(), transactionDate, "Transaction date: " + transactionDate);
        softAssert.assertEquals(hotelExpenseForm.getNote(), note, "Description: " + note);
        softAssert.assertEquals(hotelExpenseForm.getTravelDate(), travelDate, "Travel date: " + travelDate);
        softAssert.assertEquals(hotelExpenseForm.getExpenseType(), expenseType, "Expense type: " + expenseType);
        softAssert.assertEquals(hotelExpenseForm.getPersonalAmount(), personalAmount, "Personal amount: " + personalAmount);
        softAssert.assertEquals(hotelExpenseForm.getBusinessAmount(), businessAmount, "Business amount: " + businessAmount);
        softAssert.assertTrue(hotelExpenseForm.getPurpose().contains(purpose), "Purpose: " + purpose);
        softAssert.assertTrue(hotelExpenseForm.getTravelProgramBooking().contains(travelProgramBooking), "Travel program booking: " + travelProgramBooking);

        hotelExpenseForm.
                inputMerchant(merchantUpd).
                setTransactionDate(transactionDateUpd).
                setTravelDate(travelDateUpd).
                selectExpenseType(expenseTypeUpd).
                inputNote(noteUpd).
                inputPersonalAmount(personalAmountUpd).
                inputBusinessAmount(businessAmountUpd).
                openPurposesSelector().
                unselectItem(purpose).
                selectItem(purposeUpd).
                closePurposesSelector().
                openTravelProgramSelector().
                selectTravelProgramBookingItem(travelProgramBooking);
        hotelExpenseForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(merchantUpd, amountUpd),
                "Record with merchant:" + merchantUpd + " and total amount " + amountUpd + " is present");
        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(merchant, amount),
                "Record with merchant:" + merchant + " and total amount " + amount + " is not present");

        userGridPage.openRecordMerchantAmount(merchantUpd, amountUpd);
        hotelExpenseForm.initPage(hotelExpenseForm);

        softAssert.assertTrue(hotelExpenseForm.getMerchant().contains(merchantUpd), "Updated merchant: " + merchantUpd);
        softAssert.assertEquals(hotelExpenseForm.getTransactionDate(), transactionDateUpd, "Updated transaction date: " + transactionDateUpd);
        softAssert.assertEquals(hotelExpenseForm.getNote(), noteUpd, "Updated description: " + noteUpd);
        softAssert.assertEquals(hotelExpenseForm.getTravelDate(), travelDateUpd, "Updated travel date: " + travelDateUpd);
        softAssert.assertEquals(hotelExpenseForm.getExpenseType(), expenseTypeUpd, "Updated expense type: " + expenseTypeUpd);
        softAssert.assertEquals(hotelExpenseForm.getPersonalAmount(), personalAmountUpd, "Updated personal amount: " + personalAmountUpd);
        softAssert.assertEquals(hotelExpenseForm.getBusinessAmount(), businessAmountUpd, "Updated business amount: " + businessAmountUpd);
        softAssert.assertTrue(hotelExpenseForm.getPurpose().contains(purposeUpd), "Updated purpose: " + purposeUpd);
        softAssert.assertTrue(hotelExpenseForm.getTravelProgramBooking().contains(travelProgramBookingUpd), "Updated travel program booking: " + travelProgramBookingUpd);

        hotelExpenseForm.clickCancel();
        userGridPage.selectRecordMerchantAmount(true, merchantUpd, amountUpd);
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(merchantUpd, amountUpd),
                "Record with merchant:" + merchantUpd + " and total amount " + amountUpd + " is not present");

        softAssert.assertAll();
    }
}
