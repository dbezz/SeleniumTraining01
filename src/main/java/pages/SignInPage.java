package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by dmytro.bezzubikov on 4/29/2015.
 */
public class SignInPage extends BasePage
{
    public SignInPage(WebDriver driver)
    {
        super(driver);
    }

    @FindBy(id = "username")
    private WebElement inputUserName;

    @FindBy(id = "password")
    private WebElement inputPassword;

    @FindBy(id = "login")
    private WebElement buttonLogin;

    public void setUserName(String email)
    {
        setText(inputUserName,email);
    }

    public void setPassword(String password)
    {
        setText(inputPassword, password);
    }

    public void clickLogin()
    {
        buttonLogin.click();
    }


}
