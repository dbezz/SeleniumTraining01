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
public class CarRentalExpenseTest extends BaseTest
{
    private StartPage startPage;
    private UserGridPage userGridPage;
    private BaseForm carRentalForm;

    @Test
    public void CarRentalTestCRUD()
    {
        DecimalFormat df = new DecimalFormat("#.00");

        String personalAmount = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000)) / 100);
        String businessAmount = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000))/100);
        Float amountNum=Float.parseFloat(personalAmount)+Float.parseFloat(businessAmount);
        String amount = df.format(amountNum);

        String personalAmountUpd = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000)) / 100);
        String businessAmountUpd = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000))/100);
        amountNum=Float.parseFloat(personalAmountUpd)+Float.parseFloat(businessAmountUpd);
        String amountUpd = df.format(amountNum);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();

        String transactionDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String dateUpd = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String dropOffDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String pickUpDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String travelDate = dateFormat.format(cal.getTime());
        String dropOffDateUpd = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String pickUpDateUpd = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String travelDateUpd = dateFormat.format(cal.getTime());

        String merchant = "Hertz";
        String pickUpLocation = "Arlanda";
        String dropOffLocation = "Telluride";
        String booking = "Balboa travel";
        String expenseType = "GPS";
        String purpose = "Onsite with Prospect";

        String pickUpLocationExp = "ARN";
        String dropOffLocationExp = "TEX";

        String merchantUpd = "Europcar";
        String pickUpLocationUpd = "Offut";
        String dropOffLocationUpd = "Geneina";
        String bookingUpd = "Other";
        String expenseTypeUpd = "Fuel";
        String purposeUpd = "Conference";

        String pickUpLocationUpdExp = "OFF";
        String dropOffLocationUpdExp = "EGN";

        startPage = PageFactory.initElements(driver, StartPage.class);

        if (!startPage.addExpenseDisplayed())
        {
            userGridPage = startPage.selectUserRole();
        } else
            userGridPage= PageFactory.initElements(driver, UserGridPage.class);

        userGridPage.openAddExpenseList();

        carRentalForm = userGridPage.openCarRentalForm();

        carRentalForm.
                inputMerchant(merchant).
                setPickUpDate(pickUpDate).
                inputPickUpLocation(pickUpLocation).
                setdropOffDate(dropOffDate).
                inputDropOffLocation(dropOffLocation).
                selectBooking(booking).
                setTransactionDate(transactionDate).
                setTravelDate(travelDate).
                selectExpenseType(expenseType).
                inputPersonalAmount(personalAmount).
                inputBusinessAmount(businessAmount).
                openPurposesSelector().
                selectItem(purpose);

        carRentalForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(merchant, amount),
                "Record with merchant:" + merchant + " and total amount " + amount + " is added");

        userGridPage.openRecordMerchantAmount(merchant, amount);
        carRentalForm.initPage(carRentalForm);

        softAssert.assertEquals(carRentalForm.getMerchant(), merchant, "Merchant: " + merchant);
        softAssert.assertEquals(carRentalForm.getPickUpDate(), pickUpDate, "Pick up date: " + pickUpDate);
        softAssert.assertEquals(carRentalForm.getPickUpLocation(), pickUpLocationExp, "Pick up location: " + pickUpLocationExp);
        softAssert.assertEquals(carRentalForm.getDropOffDate(), dropOffDate, "Drop off date: " + dropOffDate);
        softAssert.assertEquals(carRentalForm.getDropOffLocation(), dropOffLocationExp, "Drop off location: " + dropOffLocationExp);
        softAssert.assertEquals(carRentalForm.getBooking(), booking, "Booking: " + booking);
        softAssert.assertEquals(carRentalForm.getTransactionDate(), transactionDate, "Transaction date: " + transactionDate);
        softAssert.assertEquals(carRentalForm.getTravelDate(), travelDate, "Travel date: " + travelDate);
        softAssert.assertEquals(carRentalForm.getExpenseType(), expenseType, "Expense type: " + expenseType);
        softAssert.assertEquals(carRentalForm.getPersonalAmount(), personalAmount, "Personal amount: " + personalAmount);
        softAssert.assertEquals(carRentalForm.getBusinessAmount(), businessAmount, "Business amount: " + businessAmount);
        softAssert.assertTrue(carRentalForm.getPurpose().contains(purpose), "Purpose: " + purpose);

        carRentalForm.
                inputMerchant(merchantUpd).
                setPickUpDate(pickUpDateUpd).
                inputPickUpLocation(pickUpLocationUpd).
                setdropOffDate(dropOffDateUpd).
                inputDropOffLocation(dropOffLocationUpd).
                selectBooking(bookingUpd).
                setTransactionDate(dateUpd).
                setTravelDate(travelDateUpd).
                selectExpenseType(expenseTypeUpd).
                inputPersonalAmount(personalAmountUpd).
                inputBusinessAmount(businessAmountUpd).
                openPurposesSelector().
                unselectItem(purpose).
                selectItem(purposeUpd);
        carRentalForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(merchantUpd, amountUpd),
                "Record with merchant:" + merchantUpd + " and total amount " + amountUpd + " is present");
        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(merchant, amount),
                "Record with merchant:" + merchant + " and total amount " + amount + " is not present");

        userGridPage.openRecordMerchantAmount(merchantUpd, amountUpd);
        carRentalForm.initPage(carRentalForm);

        softAssert.assertEquals(carRentalForm.getMerchant(), merchantUpd, "Updated merchant: " + merchantUpd);
        softAssert.assertEquals(carRentalForm.getPickUpDate(), pickUpDateUpd, "Updated pick up date: " + pickUpDateUpd);
        softAssert.assertEquals(carRentalForm.getPickUpLocation(), pickUpLocationUpdExp, "Updated pick up location: " + pickUpLocationUpdExp);
        softAssert.assertEquals(carRentalForm.getDropOffDate(), dropOffDateUpd, "Updated drop off date: " + dropOffDateUpd);
        softAssert.assertEquals(carRentalForm.getDropOffLocation(), dropOffLocationUpdExp, "Updated drop off location: " + dropOffLocationUpdExp);
        softAssert.assertEquals(carRentalForm.getBooking(), bookingUpd, "Updated booking: " + bookingUpd);
        softAssert.assertEquals(carRentalForm.getTransactionDate(), dateUpd, "Updated transaction date: " + dateUpd);
        softAssert.assertEquals(carRentalForm.getTravelDate(), travelDateUpd, "Updated travel date: " + travelDateUpd);
        softAssert.assertEquals(carRentalForm.getExpenseType(), expenseTypeUpd, "Updated expense type: " + expenseTypeUpd);
        softAssert.assertEquals(carRentalForm.getPersonalAmount(), personalAmountUpd, "Updated personal amount: " + personalAmountUpd);
        softAssert.assertEquals(carRentalForm.getBusinessAmount(), businessAmountUpd, "Updated business amount: " + businessAmountUpd);
        softAssert.assertTrue(carRentalForm.getPurpose().contains(purposeUpd), "Updated purpose: " + purposeUpd);

        carRentalForm.clickCancel();
        userGridPage.selectRecordMerchantAmount(true, merchantUpd, amountUpd);
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(merchantUpd, amountUpd),
                "Record with merchant:" + merchantUpd + " and total amount " + amountUpd + " is not present");

        softAssert.assertAll();
    }
}
