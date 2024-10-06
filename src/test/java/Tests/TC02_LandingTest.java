package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Utilities.Data_Utilis;
import Utilities.LogsUtilis;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.Data_Utilis.getPropertyValue;
import static Utilities.Utility.*;


@Listeners ({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_LandingTest {
    private Set<Cookie> cookies;
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");

    public TC02_LandingTest() throws FileNotFoundException {
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
        cookies =getAllCookies(getDriver());
        quite();
    }
    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtilis.info("EdgeDriver is opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("Page is redirect to the Home Page ");
        restoreSession(getDriver(),cookies);
        getDriver().get(getPropertyValue("environment","HOME_URL"));

        getDriver().navigate().refresh();
    }
    @Test
    public void checkingNumberOfSelectedProductsWithOnCartTC() {
            new P02_LandingPage(getDriver()).addAllProductsToCart();
      Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }
    @Test
    public void addingRandomProductsToCartTC(){
        new P02_LandingPage(getDriver()).addRandomProducts(5,6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void clickOnCartButtonTC () throws IOException {
        new P02_LandingPage(getDriver()).clickOnCartIcon();
        Assert.assertTrue(new P02_LandingPage(getDriver()).
                verifyCartPageURL(getPropertyValue("environment","CartPage_URL")));
    }
//    @Test
//    public void getprice1(){
//        new P01_LoginPage(getDriver()).enterUserName(UserName)
//                .enterPassword(Password).clickOnLoginButton();
//    }
        @Test
        public void clickOnCheckOnButtonTC () throws IOException {
            new P02_LandingPage(getDriver()).addRandomProducts(5,6)
                    .clickOnCartIcon().clickOnCheckOnButton();
            Assert.assertTrue(VerifyURL(getDriver(),getPropertyValue("environment","Checkout_URL")));
        }

    @AfterMethod
    public void quite(){
        getDriver().quit();
    }
    @AfterClass
    public void deletesession(){
        cookies.clear();
    }
}
