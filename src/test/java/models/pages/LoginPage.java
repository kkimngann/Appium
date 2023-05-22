package models.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import models.components.LoginComponent;
import models.components.LoginSuccessAlertComponent;
import models.components.NavMenuComponent;


public class LoginPage {
    private final AppiumDriver<MobileElement> appiumDriver;

    public LoginPage(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public LoginComponent loginComponent(){
        return new LoginComponent(this.appiumDriver);
    }

    public NavMenuComponent navMenuComponent(){
        return new NavMenuComponent(this.appiumDriver);
    }

    public LoginSuccessAlertComponent loginSuccessAlertComponent(){
        return new LoginSuccessAlertComponent(this.appiumDriver);
    }
}
