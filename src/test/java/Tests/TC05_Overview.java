package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.*;
import Utilities.Data_Utilis;
import Utilities.LogsUtilis;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.*;
import static Utilities.Data_Utilis.getPropertyValue;
@Listeners ({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC05_Overview {
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");
    private final String FirstName = Data_Utilis.getJsonData("information","fName")+"-"+ Utility.getTimestamp();
    private final String LastName = Data_Utilis.getJsonData("information","lName")+"-"+Utility.getTimestamp();
    private final String ZipCode = new Faker().number().digits(5);
    private Set<Cookie> cookes;

    public TC05_Overview() throws FileNotFoundException {
    }

    @BeforeClass
    public void Login() throws IOException {
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the BASE Page ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton();
        cookes = Utility.getAllCookies(getDriver());
        quite();
    }
    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
       // LogsUtilis.info("Page is redirect to the Login Page ");
       Utility.restoreSession(getDriver(),cookes);
        getDriver().get(getPropertyValue("environment","HOME_URL"));
        LogsUtilis.info("Page is redirect to the Home Page ");
       getDriver().navigate().refresh();
    }
    @Test
    public void checkTotalPriceTC () throws IOException {
                      //ToDo Add AllProducts To Cart Step
        new P02_LandingPage(getDriver()).addAllProductsToCart().clickOnCartIcon();
                // ToDo Go To CheckOn Page Step
        new P03_CartPage(getDriver()).clickOnCheckOnButton();
                //ToDo Filling Information Step
        new P04_CheckoutPage(getDriver()).fillingInformationForm(FirstName,LastName,ZipCode)
                .clickOnContinueButton();
        LogsUtilis.info(FirstName + " "+ LastName + " " + ZipCode);
        Assert.assertTrue(new P05_OverviewPage(getDriver()).comparingPrice());
    }
    @AfterMethod
    public void quite() throws IOException {
        quiteDriver();
    }
    @AfterClass
    public void closeSession() throws IOException {
       cookes.clear();
    }
}
