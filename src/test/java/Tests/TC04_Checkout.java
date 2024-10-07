package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
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
import static Utilities.Utility.restoreSession;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC04_Checkout {
    private Set<Cookie> cookies;
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");
    private final String FirstName = Data_Utilis.getJsonData("information","fName")+"-"+ Utility.getTimestamp();
    private final String LastName = Data_Utilis.getJsonData("information","lName")+"-"+Utility.getTimestamp();
    private final String ZipCode = new Faker().number().digits(5);
    public TC04_Checkout() throws FileNotFoundException {
    }
    @BeforeClass
    public void Login() throws IOException {
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the Home Page ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton();
        cookies =Utility.getAllCookies(getDriver());
        quiteDriver();
    }
    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("browser") !=null ? System.getProperty("browser") : getPropertyValue("environment","Browser");
        LogsUtilis.info(System.getProperty("Browser"));
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the BASE Page ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        restoreSession(getDriver(),cookies);
        LogsUtilis.info("Page is redirect to the Home Page ");
        getDriver().get(getPropertyValue("environment","HOME_URL"));

        getDriver().navigate().refresh();
    }
    @Test
    public void checkoutStepOneTC () throws IOException {
        new P02_LandingPage(getDriver()).addRandomProducts(3,6)
                .clickOnCartIcon().clickOnCheckOnButton()
                .fillingInformationForm(FirstName,LastName,ZipCode).clickOnContinueButton();
        LogsUtilis.info(FirstName + " "+ LastName + " " + ZipCode);
        Assert.assertTrue(Utility.VerifyURL(getDriver(),getPropertyValue("environment","FinishOrder_URL")));
    }
    @AfterMethod
    public void quite() throws IOException {
        quiteDriver();
    }
    @AfterClass
    public void deleteSession(){
        cookies.clear();
    }
}
