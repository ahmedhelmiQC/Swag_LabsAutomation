package Pages;

import Utilities.LogsUtilis;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class P02_LandingPage {
    static float totalPrice = 0;
    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;
    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.='Remove']");
        private final WebDriver driver;

    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }


    public By getNumberOfSelectedProductsOnCart() {
        return numberOfProductsOnCartIcon;
    }

    public P02_LandingPage addAllProductsToCart() {
        allProducts = driver.findElements(addToCartButtonForAllProducts); //6 1,2,3,4,5,6
        LogsUtilis.info("number of all products: " + allProducts.size());
        for (int i = 1; i <= allProducts.size(); i++) {
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + i + "]"); //dynamic Locator
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);
        }
        return this;
    }

    public String getNumberOfProductsOnCartIcon() {
        try {
            LogsUtilis.info("number of products on cart: " + Utility.getText(driver, numberOfProductsOnCartIcon));
            return Utility.getText(driver,numberOfProductsOnCartIcon); //exc >> no such element
        } catch (Exception e) {
            LogsUtilis.error(e.getMessage());
            return "0";
        }
    }

    public String getNumberOfSelectedProducts() {
        try {
            selectedProducts = driver.findElements(numberOfSelectedProducts);
            LogsUtilis.info("selected products: " + (selectedProducts.size()));
            return String.valueOf(selectedProducts.size());
        } catch (Exception e) {
            LogsUtilis.error(e.getMessage());
            return "0";
        }
    }


        public boolean comparingNumberOfSelectedProductsWithCart() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());
    }

}
