package models.components;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginSuccessAlertComponent {
    private final AppiumDriver<MobileElement> appiumDriver;
    private final By alertTitleElement = MobileBy.id("android:id/alertTitle");
    private final By alertContentElement = MobileBy.id("android:id/message");
    private final By alertButtonOKElement = MobileBy.id("android:id/button1");

    public LoginSuccessAlertComponent(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public String getTextAlertTitle(){
        WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
        wait.until(ExpectedConditions.visibilityOfElementLocated(alertTitleElement));
        return this.appiumDriver.findElement(this.alertTitleElement).getText();
    }

    public String getTextAlertContent(){
        return this.appiumDriver.findElement(this.alertContentElement).getText();
    }

    public String getTextAlertButtonOK(){
        return this.appiumDriver.findElement(this.alertButtonOKElement).getText();
    }

    public void clickOK(){
        this.appiumDriver.findElement(alertButtonOKElement).click();
    }
}
