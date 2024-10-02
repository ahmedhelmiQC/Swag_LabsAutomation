package Tests;

import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
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

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.Data_Utilis.getPropertyValue;
import static Utilities.Utility.VerifyURL;


@Listeners ({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_LandingPage {
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");

    public TC02_LandingPage() throws FileNotFoundException {
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
    public void checkingNumberOfSelectedProductsWithOnCartTC() {
            new P01_LoginPage(getDriver()).enterUserName(UserName)
                    .enterPassword(Password).clickOnLoginButton()
                    .addAllProductsToCart();
      Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }
    @Test
    public void addingRandomProductsToCartTC(){
        new P01_LoginPage(getDriver()).
                enterUserName(UserName)
                .enterPassword(Password)
                .clickOnLoginButton()
                .addRandomProducts(5,6);
        Assert.assertTrue(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());
    }

    @Test
    public void clickOnCartButtonTC () throws IOException {
        new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton()
                .clickOnCartIcon();
        Assert.assertTrue(new P02_LandingPage(getDriver()).
                verifyCartPageURL(getPropertyValue("environment","CartPage_URL")));
    }
    @Test
    public void getprice1(){
        new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton();
    }
        @Test
        public void clickOnContuneButtonTC () throws IOException {
            new P01_LoginPage(getDriver()).enterUserName(UserName)
                    .enterPassword(Password).clickOnLoginButton()
                    .addRandomProducts(5,6)
                    .clickOnCartIcon().clickOnCheckOnButton();
            Assert.assertTrue(VerifyURL(getDriver(),getPropertyValue("environment","Checkout_URL")));
        }

    @AfterMethod
    public void quite(){
        getDriver().quit();
    }
}
