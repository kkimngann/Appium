package driver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.MutableCapabilities;
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
                /*// Desired Capabilities
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
                }*/
                MutableCapabilities caps = new MutableCapabilities();
                caps.setCapability("platformName", "Android");
                caps.setCapability("appium:app", "storage:filename=Android-NativeDemoApp-0.4.0.apk");  // The filename of the mobile app
                caps.setCapability("appium:deviceName", "Google Pixel 4 GoogleAPI Emulator");
                caps.setCapability("appium:deviceOrientation", "portrait");
                caps.setCapability("appium:platformVersion", "13.0");
                caps.setCapability("appium:automationName", "UiAutomator2");
                MutableCapabilities sauceOptions = new MutableCapabilities();
                sauceOptions.setCapability("username", "oauth-nganntk-9119c");
                sauceOptions.setCapability("accessKey", "3d0c4fc1-023b-47c0-8e3a-13c97e8c921b");
                sauceOptions.setCapability("build", "appium-build-MSXNW");
                sauceOptions.setCapability("name", "First test");
                caps.setCapability("sauce:options", sauceOptions);
                switch (platform){
                    case android:
                        URL url = new URL("https://ondemand.us-west-1.saucelabs.com:443/wd/hub");
                        appiumDriver = new AndroidDriver<MobileElement>(url, caps);
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


