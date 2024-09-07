package Listeners;

import Utilities.LogsUtilis;
import Utilities.Utility;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ITestResultListenerClass implements ITestListener {
    public void onTestStart(ITestResult result) {
        LogsUtilis.info("TestCase"+ result.getName()+"started");
    }

    public void onTestSuccess(ITestResult result) {
        LogsUtilis.info("TestCase" + result.getName() + "passed");
    }

    public void onTestSkipped(ITestResult result) {
        LogsUtilis.info("TestCase"+ result.getName() + "Skipped");
    }




}
