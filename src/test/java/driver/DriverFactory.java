package driver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverFactory implements MobileCapabilityTypeEx {
    private AppiumDriver<MobileElement> appiumDriver = null;
    public static AppiumDriver<MobileElement> createDriver(Platforms platform) {

        if(platform == null){
            System.out.println("Please provide platform android or ios");
            return null;
        }

        AppiumDriver<MobileElement> appiumDriver = null;
        Exception exception = null;

        try {
            // Desired Capabilities
            DesiredCapabilities desiredCaps = new DesiredCapabilities();
            desiredCaps.setCapability(PLATFORM_NAME, "Android");
            desiredCaps.setCapability(AUTOMATION_NAME, "uiautomator2");
            desiredCaps.setCapability(UDID, "Pixel_3a_API_33_arm64-v8a");
            desiredCaps.setCapability(APP_PACKAGE, "com.wdiodemoapp");
            desiredCaps.setCapability(APP_ACTIVITY, "com.wdiodemoapp.MainActivity");

            URL appiumServer = new URL("http://localhost:4723/wd/hub");

            switch (platform){
                case android:
                    appiumDriver = new AndroidDriver<MobileElement>(appiumServer, desiredCaps);
                    appiumDriver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
                    break;
            }

        } catch (Exception e){
            exception = e;
        }

        if (appiumDriver == null) {
            throw new RuntimeException(exception.getMessage());
        }
        return appiumDriver;
    }

    public AppiumDriver<MobileElement> createDriver(Platforms platform, String udid, String systemPort) {
        if(this.appiumDriver == null){
            if(platform == null || udid == null || systemPort == null){
                System.out.println("Please provide platform android or ios, udid and system port");
                return null;
            }
            Exception exception = null;

            try {
                // Desired Capabilities
                DesiredCapabilities desiredCaps = new DesiredCapabilities();
                desiredCaps.setCapability(PLATFORM_NAME, "Android");
                desiredCaps.setCapability(AUTOMATION_NAME, "uiautomator2");
                desiredCaps.setCapability(UDID, udid);
                //desiredCaps.setCapability(SYSTEM_PORT, Integer.parseInt(systemPort));
                desiredCaps.setCapability(APP_PACKAGE, "com.wdiodemoapp");
                desiredCaps.setCapability(APP_ACTIVITY, "com.wdiodemoapp.MainActivity");
                URL appiumServer = new URL("http://localhost:4723");
                //URL appiumServer = new URL("http://localhost:4444/wd/hub");

                switch (platform){
                    case android:
                        appiumDriver = new AndroidDriver<MobileElement>(appiumServer, desiredCaps);
                        appiumDriver.manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
                        break;
                }

            } catch (Exception e){
                exception = e;
            }

            if (appiumDriver == null) {
                throw new RuntimeException(exception.getMessage());
            }
            return appiumDriver;
        }
        return appiumDriver;
    }

    public void quitAppiumSession(){
        if(appiumDriver != null){
            appiumDriver.quit();
        }
    }
}


