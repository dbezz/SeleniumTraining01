package Tests;

import Entities.CarRentExpense;
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

    private CarRentExpense carRentalExpense= new CarRentExpense();
    private CarRentExpense carRentalExpenseUpd = new CarRentExpense();

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

        carRentalForm = userGridPage.openCarRentalForm();

        carRentalForm.
                inputMerchant(carRentalExpense.getMerchant()).
                setPickUpDate(carRentalExpense.getPickUpDate()).
                inputPickUpLocation(carRentalExpense.getPickUpLocation()).
                setdropOffDate(carRentalExpense.getDropOffDate()).
                inputDropOffLocation(carRentalExpense.getDropOffLocation()).
                selectBooking(carRentalExpense.getBooking()).
                setTransactionDate(carRentalExpense.getTransactionDate()).
                setTravelDate(carRentalExpense.getTravelDate()).
                selectExpenseType(carRentalExpense.getExpenseType()).
                inputPersonalAmount(carRentalExpense.getPersonalAmount()).
                inputBusinessAmount(carRentalExpense.getBusinessAmount()).
                openPurposesSelector().
                selectItem(carRentalExpense.getPurpose());

        carRentalForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(carRentalExpense.getMerchant(), carRentalExpense.getAmount()),
                "Record with merchant:" + carRentalExpense.getMerchant() + " and total amount " + carRentalExpense.getAmount() + " is added");

        userGridPage.openRecordMerchantAmount(carRentalExpense.getMerchant(), carRentalExpense.getAmount());
        carRentalForm.initPage(carRentalForm);

        verifyCarRentalForm(carRentalExpense);

        carRentalForm.
                inputMerchant(carRentalExpenseUpd.getMerchant()).
                setPickUpDate(carRentalExpenseUpd.getPickUpDate()).
                inputPickUpLocation(carRentalExpenseUpd.getPickUpLocation()).
                setdropOffDate(carRentalExpenseUpd.getDropOffDate()).
                inputDropOffLocation(carRentalExpenseUpd.getDropOffLocation()).
                selectBooking(carRentalExpenseUpd.getBooking()).
                setTransactionDate(carRentalExpenseUpd.getTransactionDate()).
                setTravelDate(carRentalExpenseUpd.getTravelDate()).
                selectExpenseType(carRentalExpenseUpd.getExpenseType()).
                inputPersonalAmount(carRentalExpenseUpd.getPersonalAmount()).
                inputBusinessAmount(carRentalExpenseUpd.getBusinessAmount()).
                openPurposesSelector().
                unselectItem(carRentalExpense.getPurpose()).
                selectItem(carRentalExpenseUpd.getPurpose());
        carRentalForm.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(carRentalExpenseUpd.getMerchant(), carRentalExpenseUpd.getAmount()),
                "Record with merchant:" + carRentalExpenseUpd.getMerchant() + " and total amount " + carRentalExpenseUpd.getAmount() + " is present");
        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(carRentalExpense.getMerchant(), carRentalExpense.getAmount()),
                "Record with merchant:" + carRentalExpense.getMerchant() + " and total amount " + carRentalExpense.getAmount() + " is not present");

        userGridPage.openRecordMerchantAmount(carRentalExpenseUpd.getMerchant(), carRentalExpenseUpd.getAmount());
        carRentalForm.initPage(carRentalForm);

        verifyCarRentalForm(carRentalExpenseUpd);
        carRentalForm.inputOutsideReason(carRentalExpenseUpd.getOutReason());

        carRentalForm.clickSubmit();
        userGridPage.selectSubmittedTab();
        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(carRentalExpenseUpd.getMerchant(), carRentalExpenseUpd.getAmount()),
                "Record with merchant:" + carRentalExpenseUpd.getMerchant() + " and total amount " + carRentalExpenseUpd.getAmount() + " is present");
        userGridPage.openRecordMerchantAmount(carRentalExpenseUpd.getMerchant(), carRentalExpenseUpd.getAmount());

        verifyCarRentalForm(carRentalExpenseUpd);
        verifyCarRentalFormOutReason(carRentalExpenseUpd);

        carRentalForm.clickRevert();
        userGridPage.selectUnsubmittedTab();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(carRentalExpenseUpd.getMerchant(), carRentalExpenseUpd.getAmount()),
                "Record with merchant:" + carRentalExpenseUpd.getMerchant() + " and total amount " + carRentalExpenseUpd.getAmount() + " is present");

        userGridPage.openRecordMerchantAmount(carRentalExpenseUpd.getMerchant(), carRentalExpenseUpd.getAmount());
        verifyCarRentalForm(carRentalExpenseUpd);
        carRentalForm.clickCancel();

        userGridPage.selectRecordMerchantAmount(true, carRentalExpenseUpd.getMerchant(), carRentalExpenseUpd.getAmount());
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(carRentalExpenseUpd.getMerchant(), carRentalExpenseUpd.getAmount()),
                "Record with merchant:" + carRentalExpenseUpd.getMerchant() + " and total amount " + carRentalExpenseUpd.getAmount() + " is not present");

        softAssert.assertAll();
    }

    private void setCarRentExpenseData()
    {
        carRentalExpense.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        carRentalExpense.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        carRentalExpense.setAmount(df.format(Float.parseFloat(carRentalExpense.getPersonalAmount()) + Float.parseFloat(carRentalExpense.getBusinessAmount())));

        carRentalExpenseUpd.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        carRentalExpenseUpd.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        carRentalExpenseUpd.setAmount(df.format(Float.parseFloat(carRentalExpenseUpd.getPersonalAmount()) + Float.parseFloat(carRentalExpenseUpd.getBusinessAmount())));

        carRentalExpense.setTransactionDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        carRentalExpenseUpd.setTransactionDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        carRentalExpense.setDropOffDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        carRentalExpense.setPickUpDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        carRentalExpense.setTravelDate(dateFormat.format(cal.getTime()));
        carRentalExpenseUpd.setDropOffDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        carRentalExpenseUpd.setPickUpDate(dateFormat.format(cal.getTime()));
        cal.add(Calendar.DATE, -1);
        carRentalExpenseUpd.setTravelDate(dateFormat.format(cal.getTime()));

        carRentalExpense.setMerchant("Hertz");
        carRentalExpense.setPickUpLocation("Arlanda");
        carRentalExpense.setDropOffLocation("Telluride");
        carRentalExpense.setBooking("Balboa travel");
        carRentalExpense.setExpenseType("GPS");
        carRentalExpense.setPurpose("Onsite with Prospect");

        carRentalExpense.setPickUpLocationExp("ARN");
        carRentalExpense.setDropOffLocationExp("TEX");

        if (!carRentalExpense.getBooking().equals("Balboa travel"))
            carRentalExpense.setOutReason("Reason");

        carRentalExpenseUpd.setMerchant("Europcar");
        carRentalExpenseUpd.setPickUpLocation("Offut");
        carRentalExpenseUpd.setDropOffLocation("Geneina");
        carRentalExpenseUpd.setBooking("Other");
        carRentalExpenseUpd.setExpenseType("Fuel");
        carRentalExpenseUpd.setPurpose("Conference");

        carRentalExpenseUpd.setPickUpLocationExp("OFF");
        carRentalExpenseUpd.setDropOffLocationExp("EGN");

        if (!carRentalExpenseUpd.getBooking().equals("Balboa travel"))
            carRentalExpenseUpd.setOutReason("Reason");
    }
    
    private void verifyCarRentalForm(CarRentExpense expense)
    {
        softAssert.assertEquals(carRentalForm.getMerchant(), expense.getMerchant());
        softAssert.assertEquals(carRentalForm.getPickUpDate(), expense.getPickUpDate());
        softAssert.assertEquals(carRentalForm.getPickUpLocation(), expense.getPickUpLocationExp());
        softAssert.assertEquals(carRentalForm.getDropOffDate(), expense.getDropOffDate());
        softAssert.assertEquals(carRentalForm.getDropOffLocation(), expense.getDropOffLocationExp());
        softAssert.assertEquals(carRentalForm.getBooking(), expense.getBooking());
        softAssert.assertEquals(carRentalForm.getTransactionDate(), expense.getTransactionDate());
        softAssert.assertEquals(carRentalForm.getTravelDate(), expense.getTravelDate());
        softAssert.assertEquals(carRentalForm.getExpenseType(), expense.getExpenseType());
        softAssert.assertEquals(carRentalForm.getPersonalAmount(),  expense.getPersonalAmount());
        softAssert.assertEquals(carRentalForm.getBusinessAmount(),  expense.getBusinessAmount());
        softAssert.assertTrue(carRentalForm.getPurpose().contains(expense.getPurpose()));
    }

    private void verifyCarRentalFormOutReason(CarRentExpense expense)
    {
        if (!(expense.getOutReason()==null))
        {
            softAssert.assertEquals(carRentalForm.getOutReason(),(expense.getOutReason()));
        }
    }
}
