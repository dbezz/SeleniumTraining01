package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by dmytro.bezzubikov on 4/29/2015.
 */
public class SignUpPage extends BasePage
{
    public SignUpPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(id = "email-address")
    private WebElement inputEmail;

    @FindBy(id = "password")
    private WebElement inputPassword;

    @FindBy(name = "register")
    private WebElement buttonSignUp;

    public void setEmail(String email)
    {
        setText(inputEmail,email);
    }

    public void setPassword(String password)
    {
        setText(inputPassword,password);
    }

    public void clickSignUp()
    {
        buttonSignUp.click();
    }


}
