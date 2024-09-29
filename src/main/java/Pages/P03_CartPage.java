package Pages;

import Utilities.LogsUtilis;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class P03_CartPage {
    static float totalPrice = 0;
    private final By pricesOfSelectedProductsLocator = By.xpath("//button[.=\"Remove\"] //preceding-sibling::div[@class='inventory_item_price']");
    private final By checkoutButton = By.id("checkout");
    private final WebDriver driver;

    public P03_CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTotalPrice() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductsLocator);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By element = By.xpath("(//button[.='Remove']//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullText = Utility.getText(driver, element);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtilis.info("Total Price " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtilis.error(e.getMessage());
            return "0";
        }
    }

    public boolean comparingPrice(String price) {
        return getTotalPrice().equals(price);
    }
    public P04_CheckoutPage clickOnCheckOnButton(){
         Utility.clickingOnElement(driver , checkoutButton);
        return new P04_CheckoutPage(driver);
    }
}

