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
public class CarRentalExpenseTest extends BaseTest
{
    private StartPage startPage;
    private UserGridPage userGridPage;
    private BaseForm form;

    private Expense expense = new Expense();
    private Expense expenseUpd = new Expense();

    @Test
    public void CarRentalTestCRUD()
    {
        setCarRentExpenseData();
        startPage = PageFactory.initElements(driver, StartPage.class);

        if (!startPage.addExpenseDisplayed())
        {
            userGridPage = startPage.selectUserRole();
        } else
            userGridPage= PageFactory.initElements(driver, UserGridPage.class);

        userGridPage.openAddExpenseList();

        form = userGridPage.openCarRentalForm();

        form.
                inputMerchant(expense.getMerchant()).
                setPickUpDate(expense.getPickUpDate()).
                inputPickUpLocation(expense.getPickUpLocation()).
                setdropOffDate(expense.getDropOffDate()).
                inputDropOffLocation(expense.getDropOffLocation()).
                selectBooking(expense.getBooking()).
                setTransactionDate(expense.getTransactionDate()).
                setTravelDate(expense.getTravelDate()).
                selectExpenseType(expense.getExpenseType()).
                inputPersonalAmount(expense.getPersonalAmount()).
                inputBusinessAmount(expense.getBusinessAmount()).
                openPurposesSelector().
                selectItem(expense.getPurpose());

        form.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expense.getMerchant(), expense.getAmount()),
                "Record with merchant:" + expense.getMerchant() + " and total amount " + expense.getAmount() + " is added");

        userGridPage.openRecordMerchantAmount(expense.getMerchant(), expense.getAmount());
        form.initPage(form);

        verifyCarRentalForm(expense);

        form.
                inputMerchant(expenseUpd.getMerchant()).
                setPickUpDate(expenseUpd.getPickUpDate()).
                inputPickUpLocation(expenseUpd.getPickUpLocation()).
                setdropOffDate(expenseUpd.getDropOffDate()).
                inputDropOffLocation(expenseUpd.getDropOffLocation()).
                selectBooking(expenseUpd.getBooking()).
                setTransactionDate(expenseUpd.getTransactionDate()).
                setTravelDate(expenseUpd.getTravelDate()).
                selectExpenseType(expenseUpd.getExpenseType()).
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
        form.initPage(form);

        verifyCarRentalForm(expenseUpd);
        form.inputOutsideReason(expenseUpd.getOutReason());

        form.clickSubmit();
        userGridPage.selectSubmittedTab();
        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");
        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());

        verifyCarRentalForm(expenseUpd);
        //verifyCarRentalFormOutReason(expenseUpd);

        form.clickRevert();
        userGridPage.selectUnsubmittedTab();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");

        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());
        verifyCarRentalForm(expenseUpd);
        form.clickCancel();

        userGridPage.selectRecordMerchantAmount(true, expenseUpd.getMerchant(), expenseUpd.getAmount());
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is not present");

        softAssert.assertAll();
    }

    private void setCarRentExpenseData()
    {
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
        expense.setDropOffDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expense.setPickUpDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expense.setTravelDate(dateFormat.format(cal.getTime()));
        expenseUpd.setDropOffDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expenseUpd.setPickUpDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        expenseUpd.setTravelDate(dateFormat.format(cal.getTime()));

        expense.setMerchant("Hertz");
        expense.setPickUpLocation("Arlanda");
        expense.setDropOffLocation("Telluride");
        expense.setBooking("Balboa travel");
        expense.setExpenseType("GPS");
        expense.setPurpose("Onsite with Prospect");

        expense.setPickUpLocationExp("ARN");
        expense.setDropOffLocationExp("TEX");

        if (!expense.getBooking().equals("Balboa travel"))
            expense.setOutReason("Reason");

        expenseUpd.setMerchant("Europcar");
        expenseUpd.setPickUpLocation("Offut");
        expenseUpd.setDropOffLocation("Geneina");
        expenseUpd.setBooking("Other");
        expenseUpd.setExpenseType("Fuel");
        expenseUpd.setPurpose("Conference");

        expenseUpd.setPickUpLocationExp("OFF");
        expenseUpd.setDropOffLocationExp("EGN");

        //if (!expenseUpd.getBooking().equals("Balboa travel"))
            //expenseUpd.setOutReason("Reason");
    }
    
    private void verifyCarRentalForm(Expense expense)
    {
        softAssert.assertEquals(form.getMerchant(), expense.getMerchant());
        softAssert.assertEquals(form.getPickUpDate(), expense.getPickUpDate());
        softAssert.assertEquals(form.getPickUpLocation(), expense.getPickUpLocationExp());
        softAssert.assertEquals(form.getDropOffDate(), expense.getDropOffDate());
        softAssert.assertEquals(form.getDropOffLocation(), expense.getDropOffLocationExp());
        softAssert.assertEquals(form.getBooking(), expense.getBooking());
        softAssert.assertEquals(form.getTransactionDate(), expense.getTransactionDate());
        softAssert.assertEquals(form.getTravelDate(), expense.getTravelDate());
        softAssert.assertEquals(form.getExpenseType(), expense.getExpenseType());
        softAssert.assertEquals(form.getPersonalAmount(),  expense.getPersonalAmount());
        softAssert.assertEquals(form.getBusinessAmount(),  expense.getBusinessAmount());
        softAssert.assertTrue(form.getPurpose().contains(expense.getPurpose()));
    }

    private void verifyCarRentalFormOutReason(Expense expense)
    {
        if (!(expense.getOutReason()==null))
        {
            softAssert.assertEquals(form.getOutReason(),(expense.getOutReason()));
        }
    }
}
