package test;

import driver.DriverFactory;
import driver.Platforms;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BaseTest {
    private static final List<DriverFactory> driverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverThread;
    protected String udid;
    protected String systemPort;

    @BeforeTest
    @Parameters({"udid", "systemPort"})
    public void initAppiumSession(String udid,  String systemPort){
        this.udid = udid;
        this.systemPort = systemPort;
        driverThread = ThreadLocal.withInitial(()->{
            DriverFactory driverThread = new DriverFactory();
            driverThreadPool.add(driverThread);
            return driverThread;
        });
    }
    protected AppiumDriver<MobileElement> getDriver() {
        return driverThread.get().createDriver(Platforms.android, udid, systemPort);
        //return driverThread.get().createDriver(Platforms.android);
    }

    @AfterTest(alwaysRun = true)
    public void endAppiumSession(){
        driverThread.get().quitAppiumSession();
    }

    @AfterMethod(description = "Capture screenshot if failed")
    public void capturFaileScreenshot(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getName();
            Calendar calendar = new GregorianCalendar();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            String dateTaken = year + "-" + month + "-" + date + " " + hour + ":" + min + ":" + sec;

            File screenshot = getDriver().getScreenshotAs(OutputType.FILE);
            String fileLocation = System.getProperty("user.dir").concat("/screenshots/").concat(dateTaken).concat("png");
            try {
                FileUtils.copyFile(screenshot, new File(fileLocation));

                // Get file content then attach to Allure reporter
                Path screenshotContentPath = Paths.get(fileLocation);
                InputStream inputStream = Files.newInputStream(screenshotContentPath);
                Allure.addAttachment(testName + "-" + dateTaken, inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
