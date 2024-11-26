package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.Data_Utilis;
import Utilities.LogsUtilis;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import static DriverFactory.DriverFactory.*;
import static Utilities.Data_Utilis.getPropertyValue;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC01_Login {
    
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");

    public TC01_Login() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("Browser") !=null ? System.getProperty("Browser") : getPropertyValue("environment","Browser");
       LogsUtilis.info(System.getProperty("Browser"));
        setupDriver(browser);
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the Home Page ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @Test
    public void validLoginTC() throws IOException{
        new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(getDriver()).assertLoginTC(getPropertyValue("environment","HOME_URL")));
    }
    @AfterMethod
    public void quite() throws IOException {
    quiteDriver();
    }
}
