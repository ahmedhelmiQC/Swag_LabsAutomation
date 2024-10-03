package Pages;

import Utilities.LogsUtilis;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {

    private final By subTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By buttonFinish = By.id("finish");
    private final WebDriver driver;

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }
    public float getSubtotal(){
        LogsUtilis.info("Sub Total Price :"+Utility.getText(driver,subTotal).replace("Item total: $",""));
        return  Float.parseFloat(Utility.getText(driver,subTotal).replace("Item total: $",""));
    }
    public float getTax(){
        LogsUtilis.info("Tax :" + Utility.getText(driver,tax).replace("Tax: $",""));
        return Float.parseFloat(Utility.getText(driver,tax).replace("Tax: $",""));
    }
    public float getTotal(){
        LogsUtilis.info("Actual Total Price :" + Utility.getText(driver,total).replace("Total: $","") );
        return Float.parseFloat(Utility.getText(driver,total).replace("Total: $",""));
    }
    public String calculateTotalPrice(){
        LogsUtilis.info("Total Price : "+ getSubtotal()+getTax());
        return String.valueOf(getSubtotal()+getTax());
    }
    public boolean comparingPrices(){
        return calculateTotalPrice().equals(String.valueOf(getTotal()));
    }
    public P06_FinishOrderPage clickingFinishButton(){
        Utility.clickingOnElement(driver,buttonFinish);
        return new P06_FinishOrderPage(driver);
    }
}

