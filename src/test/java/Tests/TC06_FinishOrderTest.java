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
import static Utilities.Utility.getAllCookies;
import static Utilities.Utility.restoreSession;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})

public class TC06_FinishOrderTest {
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");
    private final String FirstName = Data_Utilis.getJsonData("information","fName")+"-"+ Utility.getTimestamp();
    private final String LastName = Data_Utilis.getJsonData("information","lName")+"-"+Utility.getTimestamp();
    private final String ZipCode = new Faker().number().digits(5);
    private Set<Cookie> cookies;

    public TC06_FinishOrderTest() throws FileNotFoundException {
    }

    @BeforeClass
    public void Login() throws IOException {
        String browser = System.getProperty("Browser") !=null ? System.getProperty("Browser") : getPropertyValue("environment","Browser");
        LogsUtilis.info(System.getProperty("Browser"));
        setupDriver(browser);
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the Home Page ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton();
        cookies =getAllCookies(getDriver());
        quite();
    }
    @BeforeMethod
    public void setup() throws IOException {
        String browser = System.getProperty("Browser") !=null ? System.getProperty("Browser") : getPropertyValue("environment","Browser");
        LogsUtilis.info(System.getProperty("Browser"));
        setupDriver(browser);
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the Home Page ");
               restoreSession(getDriver(),cookies);
        getDriver().get(getPropertyValue("environment","HOME_URL"));
        getDriver().navigate().refresh();
    }
    @Test
    public void finishOrderTC () throws IOException {
        //ToDo Add AllProducts To Cart Step
        new P02_LandingPage(getDriver()).addAllProductsToCart().clickOnCartIcon();
        // ToDo Go To CheckOn Page Step
        new P03_CartPage(getDriver()).clickOnCheckOnButton();
        //ToDo Filling Information Step
        new P04_CheckoutPage(getDriver()).fillingInformationForm(FirstName,LastName,ZipCode)
                .clickOnContinueButton();
        LogsUtilis.info(FirstName + " "+ LastName + " " + ZipCode);
        // ToDo Go To Overview Page
        new P05_OverviewPage(getDriver()).clickOnFinishButton();

        Assert.assertTrue(new P06_FinishOrderPage(getDriver()).checkVisibilityOfThanksMessage());
    }
    @AfterMethod
    public void quite() throws IOException {
        quiteDriver();
    }
    @AfterClass
    public void deletesession(){
        cookies.clear();
    }
}
