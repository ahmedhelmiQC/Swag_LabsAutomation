package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_FinishPage {
    private final WebDriver driver;

    private final By thanksMessage = By.tagName("h2");
    private final By backToHomePage = By.id("back-to-products");
    public P06_FinishPage(WebDriver driver) {
        this.driver = driver;
    }


}
