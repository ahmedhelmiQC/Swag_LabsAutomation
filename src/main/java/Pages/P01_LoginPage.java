package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P01_LoginPage {
    public final WebDriver driver;
    private final By username = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");

    public P01_LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public P01_LoginPage enterUserName(String usernametext) {
        Utility.sendData(driver, username, usernametext);
        return this;
    }

    public P01_LoginPage enterPassword(String psswordtext) {
        Utility.sendData(driver, password, psswordtext);
        return this;
    }

    public P02_LandingPage clickOnLoginButton() {
        Utility.clickingOnElement(driver, loginButton);
        return new P02_LandingPage(driver);
    }
    public boolean assertLoginTC(String expextedvalue){
        return driver.getCurrentUrl().equals(expextedvalue);
    }
}
