package test_flows;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import models.components.NavMenuComponent;
import models.pages.LoginPage;

public class BaseFlow {
    private final AppiumDriver<MobileElement> appiumDriver;

    public BaseFlow(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void gotoLoginPage(){
        NavMenuComponent navMenuComponent = new NavMenuComponent(this.appiumDriver);
        navMenuComponent.selectLogin();
    }

    public void gotoFormsPage(){
        NavMenuComponent navMenuComponent = new NavMenuComponent(this.appiumDriver);
        navMenuComponent.selectForms();
    }
}
