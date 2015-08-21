package Tests;

import Entities.Expense;
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
    private BaseForm form;
    
    private Expense expense = new Expense();
    private Expense expenseUpd = new Expense();

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

        form = userGridPage.openAirTravelForm();
        form.
                inputMerchant(expense.getMerchant()).
                inputTicketNumber(expense.getTicketNumber()).
                selectBooking(expense.getBooking()).
                setTransactionDate(expense.getTransactionDate()).
                setTravelDate(expense.getTravelDate()).
                setCarrier(expense.getCarrier()).
                inputFlight(expense.getFlightNo()).
                setFrom(expense.getFromLocation()).
                setTo(expense.getToLocation()).
                inputPersonalAmount(expense.getPersonalAmount()).
                inputBusinessAmount(expense.getBusinessAmount()).
                openPurposesSelector().
                selectItem(expense.getPurpose());
        form.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expense.getMerchant(), expense.getAmount()),
                "Record with merchant:" + expense.getMerchant() + " and total amount " + expense.getAmount() + " is added");

        userGridPage.openRecordMerchantAmount(expense.getAmount(), expense.getAmount());

        verifyAirTravelForm(expense);
        
        form.
                inputMerchant(expenseUpd.getMerchant()).
                inputTicketNumber(expenseUpd.getTicketNumber()).
                selectBooking(expenseUpd.getBooking()).
                setTransactionDate(expenseUpd.getTransactionDate()).
                setTravelDate(expenseUpd.getTravelDate()).
                setCarrier(expenseUpd.getCarrier()).
                inputFlight(expenseUpd.getFlightNo()).
                setFrom(expenseUpd.getFromLocation()).
                setTo(expenseUpd.getToLocation()).
                inputPersonalAmount(expenseUpd.getPersonalAmount()).
                inputBusinessAmount(expenseUpd.getBusinessAmount()).
                openPurposesSelector().
                unselectItem(expense.getPurpose()).
                selectItem(expenseUpd.getPurpose());
        form.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");
        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(expense.getMerchant(), expense.getAmount()),
                "Record with merchant:" + expense.getMerchant() + " and total amount " + expense.getAmount() + " is not present");

        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());

        verifyAirTravelForm(expenseUpd);
        form.inputOutsideReason(expenseUpd.getOutReason());

        form.clickSubmit();
        userGridPage.selectSubmittedTab();
        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");
        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());

        verifyAirTravelForm(expenseUpd);
        verifyAirTravelFormOutReason(expenseUpd);

        form.clickRevert();
        userGridPage.selectUnsubmittedTab();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");

        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());
        verifyAirTravelForm(expenseUpd);
        form.clickCancel();

        userGridPage.selectRecordMerchantAmount(true, expenseUpd.getMerchant(), expenseUpd.getAmount());
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is not present");

        softAssert.assertAll();
    }

    private void setAirExpenseData()
    {
        expense.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expense.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expense.setAmount(df.format(Float.parseFloat(expense.getPersonalAmount()) + Float.parseFloat(expense.getBusinessAmount())));
        expense.setTransactionDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expense.setTravelDate(dateFormat.format(cal.getTime()));

        expense.setMerchant("Pacific Wings");
        expense.setTicketNumber("12345");
        expense.setBooking("Balboa travel");
        expense.setCarrier("Delta");
        expense.setFlightNo("123");
        expense.setFromLocation("Detroit Metro Wayne");
        expense.setToLocation("Sacramento Mather");
        expense.setPurpose("Onsite with Prospect");

        expense.setCarrierExp("DL");
        expense.setFromLocationExp("DTW");
        expense.setToLocationExp("MHR");
        if (!expense.getBooking().equals("Balboa travel"))
            expense.setOutReason("Reason");
       
        expenseUpd.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expenseUpd.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expenseUpd.setAmount(df.format(Float.parseFloat(expenseUpd.getPersonalAmount()) + Float.parseFloat(expenseUpd.getBusinessAmount())));
        expenseUpd.setTransactionDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expenseUpd.setTravelDate(dateFormat.format(cal.getTime()));

        expenseUpd.setMerchant("Aeroflot");
        expenseUpd.setTicketNumber("54321");
        expenseUpd.setBooking("Other");
        expenseUpd.setCarrier("Air Berlin");
        expenseUpd.setFlightNo("321");
        expenseUpd.setFromLocation("Winnipeg Intl");
        expenseUpd.setToLocation("Seronera");
        expenseUpd.setPurpose("Conference");

        expenseUpd.setCarrierExp("AB");
        expenseUpd.setFromLocationExp("YWG");
        expenseUpd.setToLocationExp("SEU");
        if (!expenseUpd.getBooking().equals("Balboa travel"))
            expenseUpd.setOutReason("Reason");
    }

    private void verifyAirTravelForm(Expense expense)
    {
        softAssert.assertEquals(form.getMerchant(), expense.getMerchant());
        softAssert.assertEquals(form.getTicketNumber(), expense.getTicketNumber());
        softAssert.assertEquals(form.getBooking(), expense.getBooking());
        softAssert.assertEquals(form.getTransactionDate(), expense.getTransactionDate());
        softAssert.assertEquals(form.getTravelDate(), expense.getTravelDate());
        softAssert.assertEquals(form.getCarrier(), expense.getCarrierExp());
        softAssert.assertEquals(form.getFlight(), expense.getFlightNo());
        softAssert.assertEquals(form.getFrom(), expense.getFromLocationExp());
        softAssert.assertEquals(form.getTo(), expense.getToLocationExp());
        softAssert.assertEquals(form.getPersonalAmount(), expense.getPersonalAmount());
        softAssert.assertEquals(form.getBusinessAmount(), expense.getBusinessAmount());
        softAssert.assertTrue(form.getPurpose().contains(expense.getPurpose()));
    }

    private void verifyAirTravelFormOutReason(Expense expense)
    {
        if (!(expense.getOutReason()==null))
        {
            softAssert.assertEquals(form.getOutReason(),(expense.getOutReason()));
        }
    }
}
