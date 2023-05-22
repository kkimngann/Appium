package test_flows.forms;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import models.pages.FormsPage;
import org.testng.Assert;
import test_flows.BaseFlow;

public class FormsFlow extends BaseFlow {

    public AppiumDriver<MobileElement> appiumDriver;

    public FormsFlow(AppiumDriver<MobileElement> appiumDriver) {
        super(appiumDriver);
        this.appiumDriver = appiumDriver;
    }

    public void fillForms(String inputText, int optionSelectedIndex){
        FormsPage formsPage = new FormsPage(this.appiumDriver);
        if(!inputText.isEmpty()){
            formsPage.formsComponent().inputField(inputText);
        }
        formsPage.formsComponent().switchToOn();
        if(optionSelectedIndex>=0 && optionSelectedIndex <=3)
        formsPage.formsComponent().selectDropdownOption(optionSelectedIndex);

    }

    public void verifyFormsDisplay(){
        Assert.assertTrue(true);
    }
}
