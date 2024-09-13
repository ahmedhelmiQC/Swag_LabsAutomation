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
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static DriverFactory.DriverFactory.getDriver;
import static DriverFactory.DriverFactory.setupDriver;
import static Utilities.Data_Utilis.getPropertyValue;


    @Listeners ({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC02_LandingPage {
    private final String UserName= Data_Utilis.getJsonData("validLogin","username");
    private final String Password= Data_Utilis.getJsonData("validLogin","password");

    public TC02_LandingPage() throws FileNotFoundException {
    }

    @BeforeMethod
    public void setup() throws IOException {
        setupDriver(getPropertyValue("environment","Browser"));
        LogsUtilis.info("Edge Driver is Opened");
        getDriver().get(getPropertyValue("environment","BASE_URL"));
        LogsUtilis.info("page is redirected to the URL");
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    @Test
    public void checkingNumberOfSelectedProductsWithOnCartTC() {
        new P01_LoginPage(getDriver()).enterUserName(UserName)
                .enterPassword(Password).clickOnLoginButton().addAllProductsToCart();
        Assert.assertFalse(new P02_LandingPage(getDriver()).comparingNumberOfSelectedProductsWithCart());

    }
    public static int generateRandomNumber(int upperBound){  // 0 >> upper-1 > 5
        return new Random().nextInt(upperBound)+1;
    }
    //// Set >> unique 1,2,3,4,5  > condition
    public static Set<Integer> generateRandomNumber(int numberOfProductNeeded, int totalNumberOfProduct){
    Set<Integer>generatedNumbers = new HashSet<>();
        while (generatedNumbers.size()>numberOfProductNeeded){
            int randomNumber = generateRandomNumber(totalNumberOfProduct);
            generatedNumbers.add(randomNumber);
            }
        return generatedNumbers;
    }

    @AfterMethod
    public void quite(){
        getDriver().quit();
    }
}
