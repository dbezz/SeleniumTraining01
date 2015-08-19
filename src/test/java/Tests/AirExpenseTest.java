package Tests;

import Entities.AirExpense;
import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import pages.BaseForm;
import pages.StartPage;
import pages.UserGridPage;

import java.util.Calendar;



/**
 * Created by dmytro.bezzubikov on 4/21/2015.
 */
public class AirExpenseTest extends BaseTest
{
    private StartPage startPage;
    private UserGridPage userGridPage;
    private BaseForm airTravelForm;
    
    private AirExpense airExpense= new AirExpense();
    private AirExpense airExpenseUpd = new AirExpense();

    @Test
    public void AirTravelTestCRUD()
    {
        setAirExpenseData();
        startPage = PageFactory.initElements(driver, StartPage.class);

        if (!startPage.addExpenseDisplayed())
        {
            userGridPage = startPage.selectUserRole();
        } else
            userGridPage= PageFactory.initElements(driver, UserGridPage.class);

        airTravelForm = userGridPage.openAirTravelForm();
        airTravelForm.
                inputMerchant(airExpense.getMerchant()).
                inputTicketNumber(airExpense.getTicketNumber()).
                selectBooking(airExpense.getBooking()).
                setTransactionDate(airExpense.getTransactionDate()).
                setTravelDate(airExpense.getTravelDate()).
                setCarrier(airExpense.getCarrier()).
                inputFlight(airExpense.getFlightNo()).
                setFrom(airExpense.getFromLocation()).
                setTo(airExpense.getToLocation()).
                inputPersonalAmount(airExpense.getPersonalAmount()).
                inputBusinessAmount(airExpense.getBusinessAmount()).
                openPurposesSelector().
                selectItem(airExpense.getPurpose());
        airTravelForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(airExpense.getMerchant(), airExpense.getAmount()),
                "Record with merchant:" + airExpense.getMerchant() + " and total amount " + airExpense.getAmount() + " is added");

        userGridPage.openRecordMerchantAmount(airExpense.getAmount(), airExpense.getAmount());

        verifyAirTravelForm(airExpense);
        
        airTravelForm.
                inputMerchant(airExpenseUpd.getMerchant()).
                inputTicketNumber(airExpenseUpd.getTicketNumber()).
                selectBooking(airExpenseUpd.getBooking()).
                setTransactionDate(airExpenseUpd.getTransactionDate()).
                setTravelDate(airExpenseUpd.getTravelDate()).
                setCarrier(airExpenseUpd.getCarrier()).
                inputFlight(airExpenseUpd.getFlightNo()).
                setFrom(airExpenseUpd.getFromLocation()).
                setTo(airExpenseUpd.getToLocation()).
                inputPersonalAmount(airExpenseUpd.getPersonalAmount()).
                inputBusinessAmount(airExpenseUpd.getBusinessAmount()).
                openPurposesSelector().
                unselectItem(airExpense.getPurpose()).
                selectItem(airExpenseUpd.getPurpose());
        airTravelForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(airExpenseUpd.getMerchant(), airExpenseUpd.getAmount()),
                "Record with merchant:" + airExpenseUpd.getMerchant() + " and total amount " + airExpenseUpd.getAmount() + " is present");
        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(airExpense.getMerchant(), airExpense.getAmount()),
                "Record with merchant:" + airExpense.getMerchant() + " and total amount " + airExpense.getAmount() + " is not present");

        userGridPage.openRecordMerchantAmount(airExpenseUpd.getMerchant(), airExpenseUpd.getAmount());

        verifyAirTravelForm(airExpenseUpd);
        airTravelForm.inputAirBookedOutsideReason(airExpenseUpd.getOutReason());

        airTravelForm.clickSubmit();
        userGridPage.selectSubmittedTab();
        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(airExpenseUpd.getMerchant(), airExpenseUpd.getAmount()),
                "Record with merchant:" + airExpenseUpd.getMerchant() + " and total amount " + airExpenseUpd.getAmount() + " is present");
        userGridPage.openRecordMerchantAmount(airExpenseUpd.getMerchant(), airExpenseUpd.getAmount());

        verifySubmittedAirTravelForm(airExpenseUpd);
        verifyAirTravelFormOutReason(airExpenseUpd);

        airTravelForm.clickRevert();
        userGridPage.selectUnsubmittedTab();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(airExpenseUpd.getMerchant(), airExpenseUpd.getAmount()),
                "Record with merchant:" + airExpenseUpd.getMerchant() + " and total amount " + airExpenseUpd.getAmount() + " is present");

        userGridPage.openRecordMerchantAmount(airExpenseUpd.getMerchant(), airExpenseUpd.getAmount());
        verifyAirTravelForm(airExpenseUpd);
        airTravelForm.clickCancel();

        userGridPage.selectRecordMerchantAmount(true, airExpenseUpd.getMerchant(), airExpenseUpd.getAmount());
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(airExpenseUpd.getMerchant(), airExpenseUpd.getAmount()),
                "Record with merchant:" + airExpenseUpd.getMerchant() + " and total amount " + airExpenseUpd.getAmount() + " is not present");

        softAssert.assertAll();
    }

    private void setAirExpenseData()
    {
        airExpense.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        airExpense.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        airExpense.setAmount(df.format(Float.parseFloat(airExpense.getPersonalAmount()) + Float.parseFloat(airExpense.getBusinessAmount())));
        airExpense.setTransactionDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        airExpense.setTravelDate(dateFormat.format(cal.getTime()));

        airExpense.setMerchant("Pacific Wings");
        airExpense.setTicketNumber("12345");
        airExpense.setBooking("Balboa travel");
        airExpense.setCarrier("Delta");
        airExpense.setFlightNo("123");
        airExpense.setFromLocation("Detroit Metro Wayne");
        airExpense.setToLocation("Sacramento Mather");
        airExpense.setPurpose("Onsite with Prospect");

        airExpense.setCarrierExp("DL");
        airExpense.setFromLocationExp("DTW");
        airExpense.setToLocationExp("MHR");
        if (!airExpense.getBooking().equals("Balboa travel"))
            airExpense.setOutReason("Reason");
       
        airExpenseUpd.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        airExpenseUpd.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        airExpenseUpd.setAmount(df.format(Float.parseFloat(airExpenseUpd.getPersonalAmount()) + Float.parseFloat(airExpenseUpd.getBusinessAmount())));
        airExpenseUpd.setTransactionDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        airExpenseUpd.setTravelDate(dateFormat.format(cal.getTime()));

        airExpenseUpd.setMerchant("Aeroflot");
        airExpenseUpd.setTicketNumber("54321");
        airExpenseUpd.setBooking("Other");
        airExpenseUpd.setCarrier("Air Berlin");
        airExpenseUpd.setFlightNo("321");
        airExpenseUpd.setFromLocation("Winnipeg Intl");
        airExpenseUpd.setToLocation("Seronera");
        airExpenseUpd.setPurpose("Conference");

        airExpenseUpd.setCarrierExp("AB");
        airExpenseUpd.setFromLocationExp("YWG");
        airExpenseUpd.setToLocationExp("SEU");
        if (!airExpenseUpd.getBooking().equals("Balboa travel"))
            airExpenseUpd.setOutReason("Reason");
    }

    private void verifyAirTravelForm(AirExpense expense)
    {
        softAssert.assertEquals(airTravelForm.getMerchant(), expense.getMerchant());
        softAssert.assertEquals(airTravelForm.getTicketNumber(), expense.getTicketNumber());
        softAssert.assertEquals(airTravelForm.getBooking(), expense.getBooking());
        softAssert.assertEquals(airTravelForm.getTransactionDate(), expense.getTransactionDate());
        softAssert.assertEquals(airTravelForm.getTravelDate(), expense.getTravelDate());
        softAssert.assertEquals(airTravelForm.getCarrier(), expense.getCarrierExp());
        softAssert.assertEquals(airTravelForm.getFlight(), expense.getFlightNo());
        softAssert.assertEquals(airTravelForm.getFrom(), expense.getFromLocationExp());
        softAssert.assertEquals(airTravelForm.getTo(), expense.getToLocationExp());
        softAssert.assertEquals(airTravelForm.getPersonalAmount(), expense.getPersonalAmount());
        softAssert.assertEquals(airTravelForm.getBusinessAmount(), expense.getBusinessAmount());
        softAssert.assertTrue(airTravelForm.getPurpose().contains(expense.getPurpose()));
    }

    private void verifySubmittedAirTravelForm(AirExpense expense)
    {
        softAssert.assertEquals(airTravelForm.getMerchant(), expense.getMerchant());
        softAssert.assertEquals(airTravelForm.getTicketNumber(), expense.getTicketNumber());
        softAssert.assertEquals(airTravelForm.getSubmittedBooking(), expense.getBooking());
        softAssert.assertEquals(airTravelForm.getTransactionDate(), expense.getTransactionDate());
        softAssert.assertEquals(airTravelForm.getTravelDate(), expense.getTravelDate());
        softAssert.assertEquals(airTravelForm.getCarrier(), expense.getCarrierExp());
        softAssert.assertEquals(airTravelForm.getFlight(), expense.getFlightNo());
        softAssert.assertEquals(airTravelForm.getFrom(), expense.getFromLocationExp());
        softAssert.assertEquals(airTravelForm.getTo(), expense.getToLocationExp());
        softAssert.assertEquals(airTravelForm.getPersonalAmount(), expense.getPersonalAmount());
        softAssert.assertEquals(airTravelForm.getBusinessAmount(), expense.getBusinessAmount());

        softAssert.assertTrue(airTravelForm.getPurpose().contains(expense.getPurpose()));
    }

    private void verifyAirTravelFormOutReason(AirExpense expense)
    {
        if (!(expense.getOutReason()==null))
        {
            softAssert.assertEquals(airTravelForm.getOutReason(),(expense.getOutReason()));
        }
    }

}
