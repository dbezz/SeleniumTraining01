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
public class AirExpenseTest extends BaseTest
{
    private StartPage startPage;
    private UserGridPage userGridPage;
    private BaseForm airTravelForm;

    @Test
    public void AirTravelTestCRUD()
    {
        DecimalFormat df = new DecimalFormat("#.00");

        String personalAmount = df.format(Float.valueOf(Utils.Utils.randInt(100, 5000))/100);
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
        String travelDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String dateUpd = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -1);
        String travelDateUpd = dateFormat.format(cal.getTime());

        String merchant = "Pacific Wings";
        String ticketNumber = "12345";
        String booking = "Balboa travel";
        String carrier = "Delta";
        String flightNo = "123";
        String fromLocation = "Detroit Metro Wayne";
        String toLocation = "Sacramento Mather";
        String purpose = "Onsite with Prospect";

        String carrierExp = "DL";
        String fromLocationExp = "DTW";
        String toLocationExp = "MHR";

        String merchantUpd = "Aeroflot";
        String ticketNumberUpd = "54321";
        String bookingUpd = "Other";

        String carrierUpd = "Air Berlin";
        String flightNoUpd = "123";
        String fromLocationUpd = "Winnipeg Intl";
        String toLocationUpd = "Seronera";
        String purposeUpd = "Conference";

        String carrierUpdExp = "AB";
        String fromLocationUpdExp = "YWG";
        String toLocationUpdExp = "SEU";

        startPage = PageFactory.initElements(driver, StartPage.class);

        if (!startPage.addExpenseDisplayed())
        {
            userGridPage = startPage.selectUserRole();
        } else
            userGridPage= PageFactory.initElements(driver, UserGridPage.class);

        userGridPage.openAddExpenseList();

        airTravelForm = userGridPage.openAirTravelForm();

        airTravelForm.
                inputMerchant(merchant).
                inputTicketNumber(ticketNumber).
                selectBooking(booking).
                setTransactionDate(transactionDate).
                setTravelDate(travelDate).
                setCarrier(carrier).
                inputFlight(flightNo).
                setFrom(fromLocation).
                setTo(toLocation).
                inputPersonalAmount(personalAmount).
                inputBusinessAmount(businessAmount).
                openPurposesSelector().
                selectItem(purpose);
        airTravelForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(merchant, amount),
                "Record with merchant:" + merchant + " and total amount " + amount + " is added");

        userGridPage.openRecordMerchantAmount(merchant, amount);
        airTravelForm.initPage(airTravelForm);

        softAssert.assertEquals(airTravelForm.getMerchant(), merchant, "Merchant: " + merchant);
        softAssert.assertEquals(airTravelForm.getTicketNumber(), ticketNumber, "Ticket No.: " + ticketNumber);
        softAssert.assertEquals(airTravelForm.getBooking(), booking, "Booking: " + booking);
        softAssert.assertEquals(airTravelForm.getTransactionDate(), transactionDate, "Transaction date: " + transactionDate);
        softAssert.assertEquals(airTravelForm.getTravelDate(), travelDate, "Travel date: " + travelDate);
        softAssert.assertEquals(airTravelForm.getCarrier(), carrierExp, "Carrier: " + carrier);
        softAssert.assertEquals(airTravelForm.getFlight(), flightNo, "Flight: " + flightNo);
        softAssert.assertEquals(airTravelForm.getFrom(), fromLocationExp, "From: " + fromLocation);
        softAssert.assertEquals(airTravelForm.getTo(), toLocationExp, "To: " + toLocation);
        softAssert.assertEquals(airTravelForm.getPersonalAmount(), personalAmount, "Personal amount: " + personalAmount);
        softAssert.assertEquals(airTravelForm.getBusinessAmount(), businessAmount, "Business amount: " + businessAmount);
        softAssert.assertTrue(airTravelForm.getPurpose().contains(purpose), "Purpose: " + purpose);

        airTravelForm.
                inputMerchant(merchantUpd).
                inputTicketNumber(ticketNumberUpd).
                selectBooking(bookingUpd).
                setTransactionDate(dateUpd).
                setTravelDate(travelDateUpd).
                setCarrier(carrierUpd).
                inputFlight(flightNoUpd).
                setFrom(fromLocationUpd).
                setTo(toLocationUpd).
                inputPersonalAmount(personalAmountUpd).
                inputBusinessAmount(businessAmountUpd).
                openPurposesSelector().
                unselectItem(purpose).
                selectItem(purposeUpd);
        airTravelForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(merchantUpd, amountUpd),
                "Record with merchant:" + merchantUpd + " and total amount " + amountUpd + " is present");
        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(merchant, amount),
                "Record with merchant:" + merchant + " and total amount " + amount + " is not present");

        userGridPage.openRecordMerchantAmount(merchantUpd, amountUpd);
        airTravelForm.initPage(airTravelForm);

        softAssert.assertEquals(airTravelForm.getMerchant(), merchantUpd, "Updated merchant: " + merchantUpd);
        softAssert.assertEquals(airTravelForm.getTicketNumber(), ticketNumberUpd, "Updated Ticket No.: " + ticketNumber);
        softAssert.assertEquals(airTravelForm.getBooking(), bookingUpd, "Updated Booking: " + booking);
        softAssert.assertEquals(airTravelForm.getTransactionDate(), dateUpd, "Updated Transaction date: " + transactionDate);
        softAssert.assertEquals(airTravelForm.getTravelDate(), travelDateUpd, "Updated Travel date: " + travelDate);
        softAssert.assertEquals(airTravelForm.getCarrier(), carrierUpdExp, "Updated Carrier: " + carrier);
        softAssert.assertEquals(airTravelForm.getFlight(), flightNoUpd, "Updated Flight: " + flightNo);
        softAssert.assertEquals(airTravelForm.getFrom(), fromLocationUpdExp, "Updated From: " + fromLocation);
        softAssert.assertEquals(airTravelForm.getTo(), toLocationUpdExp, "Updated To: " + toLocation);
        softAssert.assertEquals(airTravelForm.getPersonalAmount(), personalAmountUpd, "Updated personal amount: "+personalAmountUpd);
        softAssert.assertEquals(airTravelForm.getBusinessAmount(), businessAmountUpd, "Updated business amount: " + businessAmountUpd);
        softAssert.assertTrue(airTravelForm.getPurpose().contains(purposeUpd), "Updated purpose: " + purposeUpd);

        airTravelForm.Cancel();
        userGridPage.selectRecordMerchantAmount(true, merchantUpd, amountUpd);
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(merchantUpd, amountUpd),
                "Record with merchant:" + merchantUpd + " and total amount " + amountUpd + " is not present");

        softAssert.assertAll();
    }
}
