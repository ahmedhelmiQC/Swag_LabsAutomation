package Pages;

import Utilities.LogsUtilis;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {

    private final By subTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By finishButton = By.id("finish");
    private final WebDriver driver;

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }
    public float getSubTotal(){
        LogsUtilis.info("SubTotal Price : " + Utility.getText(driver,subTotal).replace("Item total: $",""));
        return Float.parseFloat(Utility.getText(driver,subTotal).replace("Item total: $",""));
    }
    public float getTax(){
        LogsUtilis.info("Tax : "+ Utility.getText(driver,tax).replace("Tax: $",""));
        return Float.parseFloat(Utility.getText(driver,tax).replace("Tax: $",""));
    }
    public float actualTotalPrice(){
        LogsUtilis.info("Actual Total Price : "+ Utility.getText(driver,total).replace("Total: $",""));
        return Float.parseFloat(Utility.getText(driver,total).replace("Total: $",""));
    }
    public String calculatingTotalPrice(){
        LogsUtilis.info("Total Price : "+ getSubTotal()+ getTax());
    return String.valueOf(getSubTotal()+ getTax());
    }
    public boolean comparingPrice(){
        return calculatingTotalPrice().equals(String.valueOf(actualTotalPrice()));
    }
    public P06_FinishPage clickOnFinishButton(){
        Utility.clickingOnElement(driver,finishButton);
        return new P06_FinishPage(driver);
    }


}
