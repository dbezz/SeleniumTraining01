package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by dmytro.bezzubikov on 4/29/2015.
 */
public class StartPage extends BasePage {
    public StartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "a#sign-up")
    private WebElement signUp;

    @FindBy(xpath = "//div[@id='content']/div/div/a")
    private WebElement signIn;

    public boolean signUpDisplayed() {
        return isElementDisplayed(signUp);
    }

    public boolean signInDisplayed() {
        return isElementDisplayed(signIn);
    }

    public SignInPage clickSignIn() {
        signIn.click();
        SignInPage sigInPage = PageFactory.initElements(driver, SignInPage.class);
        return sigInPage;
    }

    public void clickSignUp() {
        signUp.click();
    }
}
