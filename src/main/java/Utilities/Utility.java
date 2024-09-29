package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Utility {
    private static final String SCREENSHOT_PATH = "test_outputs/screenshot/";

                        /// clicking
    public static void clickingOnElement(WebDriver driver , By locator){

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

                    /// send Data
    public static void sendData(WebDriver driver, By locator , String data){
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(data);
    }

                    /// get Text
    public static String getText(WebDriver driver, By locator ){
        new WebDriverWait(driver,Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return  driver.findElement(locator).getText();
    }
                        /// general Wait
    public static WebDriverWait generalWait(WebDriver driver){
        return new WebDriverWait(driver , Duration.ofSeconds(5));
    }

                        /// Scrolling
    public static void scrolling(WebDriver driver , By locator){
        ((JavascriptExecutor)driver).executeScript("arguments[0];",findWebElement(driver,locator));
    }

                        //// selectingFromDropdown
    public static void selectingFromDropdown(WebDriver driver , By locator , String option)
    {
        new Select(findWebElement(driver,locator)).selectByVisibleText(option);
    }
                            //// from By locator  to WebElement
    public static WebElement findWebElement(WebDriver driver, By locator){
        return driver.findElement(locator);
    }

                        ///  Timestamp
    public static String getTimestamp(){
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ssa").format(new Date());
    }
                        /// screenshot //
        public static void takeScreenShot(WebDriver driver , String screenshotName)
    {
        try {
            // capture screenshot using TakeScreenshot
            File screenshotSRC = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOT_PATH + screenshotName + "-"+ getTimestamp()+".png");
            FileUtils.copyFile(screenshotSRC , screenshotFile);

            // attach the screenshot to allure
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotFile.getPath())));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void takeFullScreenshot(WebDriver driver , By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(findWebElement(driver, locator))
                    .save(SCREENSHOT_PATH);
        } catch (Exception e) {
            LogsUtilis.error(e.getMessage());
        }
    }
                //// RandomNumber //////
    public static int generateRandomNumber(int upperBound){  // 0 >> upper-1 > 5
        return new Random().nextInt(upperBound)+1;
    }
                        //// Set >> unique 1,2,3,4,5  > condition
    public static Set<Integer> generateRandomNumber(int numberOfProductNeeded, int totalNumberOfProduct){
        Set<Integer>generatedNumbers = new HashSet<>();
        while (generatedNumbers.size()<numberOfProductNeeded){
            int randomNumber = generateRandomNumber(totalNumberOfProduct);
            generatedNumbers.add(randomNumber);
        }
        return generatedNumbers;
    }

    public static boolean VerifyURL(WebDriver driver, String expectedURL) {
        try {
            generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    

}
