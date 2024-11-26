package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Utilities.Data_Utilis;
import Utilities.LogsUtilis;
import Utilities.Utility;
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
public class TC03_CartTest {
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");

    private Set<Cookie> cookes;
    public TC03_CartTest() throws FileNotFoundException {
    }
    @BeforeClass
    public void Login() throws IOException {
        String browser = System.getProperty("Browser") !=null ? System.getProperty("Browser") : getPropertyValue("environment","Browser");
        LogsUtilis.info(System.getProperty("Browser"));
        setupDriver(browser);
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

        String browser = System.getProperty("Browser") !=null ? System.getProperty("Browser") : getPropertyValue("environment","Browser");
        LogsUtilis.info(System.getProperty("Browser"));
        setupDriver(browser);
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the Home Page ");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @Test
    public void comparingPriceTC() throws IOException{
       String totalPrice = new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton().
                addRandomProducts(2,6)
                .getTotalPriceOfSelectedProducts();
        new P02_LandingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(new P03_CartPage(getDriver()).comparingPrice(totalPrice));
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
