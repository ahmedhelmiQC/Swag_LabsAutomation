package Listeners;

import Pages.P02_LandingPage;
import Utilities.LogsUtilis;
import Utilities.Utility;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

import static DriverFactory.DriverFactory.getDriver;

public class IInvokedMethodListenerClass implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            LogsUtilis.info("TestCase" + testResult.getName() + "failed");
            Utility.takeScreenShot(getDriver(),testResult.getName()); /// valid tes case
            Utility.takeFullScreenshot(getDriver(),new P02_LandingPage(getDriver()).getNumberOfProductsOnCartIcon());
        }
    }
}
