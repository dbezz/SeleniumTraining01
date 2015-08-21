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
public class BaggageExpenseTest extends BaseTest
{
    private StartPage startPage;
    private UserGridPage userGridPage;
    private BaseForm form;

    private Expense expense = new Expense();
    private Expense expenseUpd = new Expense();
    @Test
    public void BaggageExpenseTestCRUD()
    {
        setBaggageExpenseData();
        startPage = PageFactory.initElements(driver, StartPage.class);
        if (!startPage.addExpenseDisplayed())
        {
            userGridPage = startPage.selectUserRole();
        } else
            userGridPage= PageFactory.initElements(driver, UserGridPage.class);

        userGridPage.openAddExpenseList();
        form = userGridPage.openBaggageFeeForm();
        form.
                inputMerchant(expense.getMerchant()).
                inputCity(expense.getCity()).
                inputDescription(expense.getDescription()).
                setTransactionDate(expense.getTransactionDate()).
                inputPersonalAmount(expense.getPersonalAmount()).
                inputBusinessAmount(expense.getBusinessAmount()).
                openPurposesSelector().
                selectItem(expense.getPurpose());
        form.saveForm();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expense.getMerchant(), expense.getAmount()),
                "Record with merchant:" + expense.getMerchant() + " and total amount " + expense.getAmount() + " is added");

        userGridPage.openRecordMerchantAmount(expense.getAmount(), expense.getAmount());

        verifyBaggageForm(expense);
        
        form.
                inputMerchant(expenseUpd.getMerchant()).
                inputCity(expenseUpd.getCity()).
                inputDescription(expenseUpd.getDescription()).
                setTransactionDate(expenseUpd.getTransactionDate()).
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

        verifyBaggageForm(expenseUpd);

        form.clickSubmit();
        userGridPage.selectSubmittedTab();
        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");
        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());

        verifyBaggageForm(expenseUpd);

        form.clickRevert();
        userGridPage.selectUnsubmittedTab();

        softAssert.assertTrue(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is present");

        userGridPage.openRecordMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount());
        verifyBaggageForm(expenseUpd);
        form.clickCancel();

        userGridPage.selectRecordMerchantAmount(true, expenseUpd.getMerchant(), expenseUpd.getAmount());
        userGridPage.clickDelete();
        userGridPage.confirmAlert();

        softAssert.assertFalse(userGridPage.isRecordPresentByMerchantAmount(expenseUpd.getMerchant(), expenseUpd.getAmount()),
                "Record with merchant:" + expenseUpd.getMerchant() + " and total amount " + expenseUpd.getAmount() + " is not present");

        softAssert.assertAll();
    }
    private void setBaggageExpenseData()
    {
        expense.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expense.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expense.setAmount(df.format(Float.parseFloat(expense.getPersonalAmount()) + Float.parseFloat(expense.getBusinessAmount())));
        expense.setTransactionDate(dateFormat.format(cal.getTime()));
        

        expense.setMerchant("American Airlines");
        expense.setCity("Arlington");
        expense.setDescription("Description");
        expense.setPurpose("Onsite with Prospect");

        expenseUpd.setPersonalAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expenseUpd.setBusinessAmount(df.format(Float.valueOf(Utils.Utils.randInt(100, 749)) / 100));
        expenseUpd.setAmount(df.format(Float.parseFloat(expenseUpd.getPersonalAmount()) + Float.parseFloat(expenseUpd.getBusinessAmount())));
        cal.add(Calendar.DATE, -1);
        expenseUpd.setTransactionDate(dateFormat.format(cal.getTime()));

        expenseUpd.setMerchant("Cathay");
        expenseUpd.setCity("City of Industry");
        expenseUpd.setDescription("Description updated");
        expenseUpd.setPurpose("Conference");
    }

    private void verifyBaggageForm(Expense expense)
    {
        softAssert.assertTrue(form.getMerchant().contains(expense.getMerchant()));
        softAssert.assertTrue(form.getCity().contains(expense.getCity()));
        softAssert.assertEquals(form.getTransactionDate(), expense.getTransactionDate());
        softAssert.assertEquals(form.getDescription(), expense.getDescription());
        softAssert.assertEquals(form.getPersonalAmount(), expense.getPersonalAmount());
        softAssert.assertEquals(form.getBusinessAmount(), expense.getBusinessAmount());
        softAssert.assertTrue(form.getPurpose().contains(expense.getPurpose()));
    }
  
}
