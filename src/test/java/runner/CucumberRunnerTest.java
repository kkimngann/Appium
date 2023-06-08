package runner;

import driver.DriverFactory;
import driver.Platforms;
import io.appium.java_client.AppiumDriver;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@CucumberOptions(tags="${cucumber.filter.tags}",
        features = "src/test/resources/features", glue = {"stepdefinitions"},
        plugin = { "pretty", "json:target/cucumber-reports/cucumber.json",	"html:target/cucumber-reports/cucumberreport.html" }, monochrome = true)
public class CucumberRunnerTest extends AbstractTestNGCucumberTests {
    private final static List<DriverFactory> driverThreadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverThread;
    private static Platforms platform;
    protected AppiumDriver driver;

    protected AppiumDriver getDriver(){
        if(this.driver == null){
            this.driver = driverThread.get().createDriver(platform);
            return this.driver;
        }
        return this.driver;
    }

    @BeforeTest(description = "Init appium session")
    @Parameters({"platform"})
    public void initAppiumSession(Platforms platform){
        this.platform = platform;
        driverThread = ThreadLocal.withInitial(()->{
            DriverFactory driverThread = new DriverFactory();
            driverThreadPool.add(driverThread);
            return driverThread;
        });
    }

    @AfterTest(alwaysRun = true)
    public void closeBrowserSession(){
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