package models.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import models.components.FormsComponent;
import models.components.LoginComponent;
import models.components.NavMenuComponent;

public class FormsPage {
    private final AppiumDriver<MobileElement> appiumDriver;

    public FormsPage(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public FormsComponent formsComponent(){
        return new FormsComponent(this.appiumDriver);
    }

    public NavMenuComponent navMenuComponent(){
        return new NavMenuComponent(this.appiumDriver);
    }
}

