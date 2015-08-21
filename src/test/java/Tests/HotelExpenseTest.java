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
public class HotelExpenseTest extends BaseTest
{
    private StartPage startPage;
    private UserGridPage userGridPage;
    private BaseForm form;

    private Expense expense = new Expense();
    private Expense expenseUpd = new Expense();

    @Test
    public void HotelExpenseTestCRUD()
    {
        setHotelExpenseData();
        startPage = PageFactory.initElements(driver, StartPage.class);

        if (!startPage.addExpenseDisplayed())
        {
            userGridPage = startPage.selectUserRole();
        } else
            userGridPage= PageFactory.initElements(driver, UserGridPage.class);

        userGridPage.openAddExpenseList();

        form = userGridPage.openHotelForm();

        form.
                inputMerchant(expense.getMerchant()).
                setTransactionDate(expense.getTransactionDate()).
                setTravelDate(expense.getTravelDate()).
                selectExpenseType(expense.getExpenseType()).
                inputNote(expense.getNote()).
                inputPersonalAmount(expense.getPersonalAmount()).
                inputBusinessAmount(expense.getBusinessAmount()).
                openPurposesSelector().
                selectItem(expense.getPurpose()).
                closePurposesSelector().
                openTravelProgramSelector().
                selectTravelProgramBookingItem(expense.getTravelProgramBooking()).
                inputOutsideReason(expense.getOutReason());

        form.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expense.getMerchant(), expense.getAmount()),
                "Record with merchant:" + expense.getMerchant() + " and total amount " + expense.getAmount() + " is added");

        userGridPage.openRecordMerchantAmount(expense.getAmount(), expense.getAmount());
        
        verifyHotelForm(expense);
        verifyHotelFormOutReason(expense);
                
        form.
                inputMerchant(expenseUpd.getMerchant()).
                setTransactionDate(expenseUpd.getTransactionDate()).
                setTravelDate(expenseUpd.getTravelDate()).
                selectExpenseType(expenseUpd.getExpenseType()).
                inputNote(expenseUpd.getNote()).
                inputPersonalAmount(expenseUpd.getPersonalAmount()).
                inputBusinessAmount(expenseUpd.getBusinessAmount()).
                openPurposesSelector().
                unselectItem(expense.getPurpose()).
                selectItem(expenseUpd.getPurpose()).
                closePurposesSelector().
                openTravelProgramSelector().
                selectTravelProgramBookingItem(expenseUpd.getTravelProgramBooking()).
                inputOutsideReason(expenseUpd.getOutReason());
        form.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");
        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(expense.getMerchant(), expense.getAmount()),
                "Record with merchant:" + expense.getMerchant() + " and total amount " + expense.getAmount() + " is not present");

        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());

        verifyHotelForm(expenseUpd);
        verifyHotelFormOutReason(expenseUpd);

        form.clickSubmit();
        userGridPage.selectSubmittedTab();
        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");
        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());

        verifyHotelForm(expenseUpd);
        verifyHotelFormOutReason(expenseUpd);

        form.clickRevert();
        userGridPage.selectUnsubmittedTab();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");

        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());
        verifyHotelForm(expenseUpd);
        verifyHotelFormOutReason(expenseUpd);
        form.clickCancel();

        userGridPage.selectRecordMerchantAmount(true, expenseUpd.getMerchant(), expenseUpd.getAmount());
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is not present");

        softAssert.assertAll();

    }

    private void setHotelExpenseData() {
        expense.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expense.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expense.setAmount(df.format(Float.parseFloat(expense.getPersonalAmount()) + Float.parseFloat(expense.getBusinessAmount())));

        expenseUpd.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expenseUpd.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expenseUpd.setAmount(df.format(Float.parseFloat(expenseUpd.getPersonalAmount()) + Float.parseFloat(expenseUpd.getBusinessAmount())));

        expense.setTransactionDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expenseUpd.setTransactionDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expense.setTravelDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expenseUpd.setPickUpDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expenseUpd.setTravelDate(dateFormat.format(cal.getTime()));

        expense.setMerchant("Holiday Inn Aber");
        expense.setExpenseType("Food");
        expense.setNote("Note");
        expense.setPurpose("Onsite with Member");
        expense.setTravelProgramBooking("Other");

        if (expense.getTravelProgramBooking().equals("Other"))
        expense.setOutReason("Reason");

        expenseUpd.setMerchant("Hyatt Grand Aspen");
        expenseUpd.setExpenseType("Hotel Night");
        expenseUpd.setNote("NoteUpd");
        expenseUpd.setPurpose("Conference");
        expenseUpd.setTravelProgramBooking("Other");

        if (expenseUpd.getTravelProgramBooking().equals("Other"))
        expenseUpd.setOutReason("ReasonUpd");
   }

    private void verifyHotelForm(Expense expense) {
        softAssert.assertTrue(form.getMerchant().contains(expense.getMerchant()));
        softAssert.assertEquals(form.getTransactionDate(), expense.getTransactionDate());
        softAssert.assertEquals(form.getTravelDate(), expense.getTravelDate());
        softAssert.assertEquals(form.getExpenseType(), expense.getExpenseType());
        softAssert.assertEquals(form.getNote(), expense.getNote());
        softAssert.assertEquals(form.getPersonalAmount(), expense.getPersonalAmount());
        softAssert.assertEquals(form.getBusinessAmount(), expense.getBusinessAmount());
        softAssert.assertTrue(form.getPurpose().contains(expense.getPurpose()));
        softAssert.assertTrue(form.getTravelProgramBooking().contains(expense.getTravelProgramBooking()));
    }

    private void verifyHotelFormOutReason(Expense expense)
    {
        if (!(expense.getOutReason()==null))
        {
            softAssert.assertEquals(form.getOutReason(),(expense.getOutReason()));
        }
    }
}
