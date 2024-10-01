package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Utilities.Data_Utilis;
import Utilities.LogsUtilis;
import Utilities.Utility;
import com.github.javafaker.Faker;
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
public class TC04_Checkout {
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");
    private final String FirstName = Data_Utilis.getJsonData("information","fName")+"-"+ Utility.getTimestamp();
    private final String LastName = Data_Utilis.getJsonData("information","lName")+"-"+Utility.getTimestamp();
    private final String ZipCode = new Faker().number().digits(5);
    public TC04_Checkout() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the Home Page ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @Test
    public void checkoutStepOneTC () throws IOException {
        new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton()
                .addRandomProducts(3,6)
                .clickOnCartIcon().clickOnCheckOnButton()
                .fillingInformationForm(FirstName,LastName,ZipCode).clickOnContinueButton();
        LogsUtilis.info(FirstName + " "+ LastName + " " + ZipCode);
        Assert.assertTrue(Utility.VerifyURL(getDriver(),getPropertyValue("environment","Checkout_URL")));
    }
    @AfterMethod
    public void quite() throws IOException {
        quiteDriver();
    }
}