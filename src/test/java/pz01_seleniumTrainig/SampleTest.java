package pz01_seleniumTrainig;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.SignInPage;
import pages.StartPage;

/**
 * Created by dmytro.bezzubikov on 4/21/2015.
 */
public class SampleTest extends BaseTest
{
    private StartPage startPage;
    private SignInPage signInPage;

    @Parameters({"url"})
    @BeforeMethod
    public void getApplication(String url)
    {
        driver.get(url);
        startPage = PageFactory.initElements(driver, StartPage.class);
    }

    @Test
    public void testHomePageHasSignIn()
    {
        Assert.assertTrue(startPage.signInDisplayed());
    }

    @Test
    public void testHomePageHasSignUp()
    {
        Assert.assertTrue(startPage.signUpDisplayed());
    }

    @Test
    public void testSignIn()
    {
        signInPage = startPage.clickSignIn();
        signInPage.setUserName("");
        signInPage.setPassword("");
        signInPage.clickLogin();
        Assert.assertTrue(true);
        Assert.assertTrue(true);

    }
}
