package Pages;

import Utilities.LogsUtilis;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Set;

import static Utilities.Utility.generalWait;

public class P02_LandingPage {
    static float totalPrice = 0;
    private static List<WebElement> allProducts;
    private static List<WebElement> selectedProducts;
    private final By addToCartButtonForAllProducts = By.xpath("//button[@class]");
    private final By numberOfProductsOnCartIcon = By.className("shopping_cart_badge");
    private final By numberOfSelectedProducts = By.xpath("//button[.='Remove']");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By pricesOfSelectedProductLocator = By.xpath("//button[.=\"Remove\"] //preceding-sibling::div[@class='inventory_item_price']");
    private final WebDriver driver;

    public P02_LandingPage(WebDriver driver) {
        this.driver = driver;
    }


//    public By getNumberOfSelectedProductsOnCart() {
//        return numberOfProductsOnCartIcon;
//    }

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
            return Utility.getText(driver, numberOfProductsOnCartIcon); //exc >> no such element
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
    public P02_LandingPage addRandomProducts(int numberOfProductsNeeded, int totalNumberOfProducts) {
        Set<Integer> randomNumbers = Utility.generateRandomNumber(numberOfProductsNeeded, totalNumberOfProducts); //3 > 2,4,1
        for (int random : randomNumbers) {
            LogsUtilis.info("randomNumber " + random);
            By addToCartButtonForAllProducts = By.xpath("(//button[@class])[" + random + "]"); //dynamic Locator
            Utility.clickingOnElement(driver, addToCartButtonForAllProducts);
        }
        return this;
    }
    public boolean comparingNumberOfSelectedProductsWithCart() {
        return getNumberOfProductsOnCartIcon().equals(getNumberOfSelectedProducts());
    }

    public P03_CartPage clickOnCartIcon() {
        Utility.clickingOnElement(driver, cartIcon);
        return new P03_CartPage(driver);
    }

    public boolean verifyCartPageURL(String expectedURL) {
        try {
            LogsUtilis.info("Checkout_URL : "+ generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL)));
            generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getTotalPriceOfSelectedProducts() {
        try {
            List<WebElement> pricesOfSelectedProducts = driver.findElements(pricesOfSelectedProductLocator);
            for (int i = 1; i <= pricesOfSelectedProducts.size(); i++) {
                By elements = By.xpath("(//button[.='Remove']//preceding-sibling::div[@class='inventory_item_price'])[" + i + "]");
                String fullText = Utility.getText(driver, elements);
                totalPrice += Float.parseFloat(fullText.replace("$", ""));
            }
            LogsUtilis.info("Total Price " + totalPrice);
            return String.valueOf(totalPrice);
        } catch (Exception e) {
            LogsUtilis.error(e.getMessage());
            return "0";
        }
    }


}
