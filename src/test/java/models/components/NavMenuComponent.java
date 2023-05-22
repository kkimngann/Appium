package models.components;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class NavMenuComponent {
    private final AppiumDriver<MobileElement> appiumDriver;
    private final static By homeMenuElement = MobileBy.AccessibilityId("Home");
    private final static By webviewMenuElement = MobileBy.AccessibilityId("Webview");
    private final static By loginMenuElement = MobileBy.AccessibilityId("Login");
    private final static By swipeMenuElement = MobileBy.AccessibilityId("Swipe");
    private final static By formsMenuElement = MobileBy.AccessibilityId("Forms");
    private final static By dragMenuElement = MobileBy.AccessibilityId("Drag");


    public NavMenuComponent(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void selectHome(){
        this.appiumDriver.findElement(homeMenuElement).click();
    }

    public void selectWebview(){
        this.appiumDriver.findElement(webviewMenuElement).click();
    }

    public void selectLogin(){
        this.appiumDriver.findElement(loginMenuElement).click();
    }


    public void selectForms(){
        this.appiumDriver.findElement(formsMenuElement).click();
    }

    public void selectSwipe(){
        this.appiumDriver.findElement(swipeMenuElement).click();
    }

    public void selectDrag(){
        this.appiumDriver.findElement(dragMenuElement).click();
    }

}
