package listeners;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.TimeoutError;
import config.ConfigReader;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int retryCount = 1;
    private static int maxRetryCount = Integer.parseInt(ConfigReader.getProperty("test.retry"))+1; // number of retries

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            Throwable throwable = result.getThrowable();
            if (throwable != null && containsTimeout(throwable)) {
                retryCount++;
                result.setAttribute("retryCount", retryCount);
                System.out.println("Retrying test " + result.getName() + " due to timeout: attempt " + retryCount);
                return true;
            }
        }
        return false;
    }

    private boolean containsTimeout(Throwable throwable) {
        // Check if exception is TimeoutError or message contains "timeout" (case-insensitive)
        if (throwable instanceof TimeoutError) {
            return true;
        }
        String message = throwable.getMessage();
        if (message != null && message.toLowerCase().contains("timeout") || message.toLowerCase().contains("to be visible") || message.toLowerCase().contains("error") ) {
            return true;
        }

        // Check nested cause
        if (throwable.getCause() != null) {
            return containsTimeout(throwable.getCause());
        }
        return false;
    }


    public static void retryStep(Runnable step, ExtentTest parentTest) {
        ExtentTest test =parentTest;
        int attempt = 1;
//        maxRetryCount= Integer.parseInt(ConfigReader.getProperty("test.retry"))+1;
        while (attempt < maxRetryCount) {
            try {
//                page.reload();
                step.run(); // execute step
                return;     // success → exit
            } catch (Exception e) {
                if (attempt >= 0) {
//                    System.out.println("retry");
                    test = parentTest.createNode("Retry " + attempt);
                }
                attempt++;
                ExtentTest retryNode = test.createNode("Attempt " + attempt);
                retryNode.addScreenCaptureFromBase64String("retry");
                retryNode.fail("Step failed: " + e.getMessage());
                if (attempt > maxRetryCount || !isRetryable(e)) {
                    throw e; // stop retrying
                }
            }
        }
    }

    private static boolean isRetryable(Exception e) {
        return e instanceof TimeoutError || (e.getMessage() != null && e.getMessage().toLowerCase().contains("timeout")) && e.getMessage().toLowerCase().contains("to be visible");
    }
}
