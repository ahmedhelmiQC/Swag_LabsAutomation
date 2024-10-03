package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static Utilities.Utility.findWebElement;

public class P06_FinishOrderPage {

    private final By thanksMessage = By.tagName("h2");
    private final By homePage = By.id("back-to-products");
    private final WebDriver driver;
    public P06_FinishOrderPage(WebDriver driver) {
        this.driver = driver;
    }
    public boolean checkVisibilityOfThanksMessage(){
        return findWebElement(driver,thanksMessage).isDisplayed();
    }

   public P02_LandingPage goToHmePage(){
        Utility.clickingOnElement(driver,homePage);
        return new P02_LandingPage(driver);
   }
}
