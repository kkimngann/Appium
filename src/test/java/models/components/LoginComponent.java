package models.components;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;

public class LoginComponent {
    private final AppiumDriver<MobileElement> appiumDriver;
    public final By emailInputElement = MobileBy.AccessibilityId("input-email");
    public final By emailInputEmailErrMsgElement = MobileBy.xpath("//*[contains(@text, 'Please enter a valid email address')]");
    public final By passwordInputElement = MobileBy.AccessibilityId("input-password");
    public final By emailInputPasswordErrMsgElement = MobileBy.xpath("//*[contains(@text, 'Please enter at least 8 characters')]");
    public final By loginBtnElement = MobileBy.AccessibilityId("button-LOGIN");

    public LoginComponent(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void inputEmail(String email){
        if(!email.isEmpty()){
            this.appiumDriver.findElement(emailInputElement).sendKeys(email);
        }
    }

    public void inputPassword(String password){
        if(!password.isEmpty()){
            this.appiumDriver.findElement(passwordInputElement).sendKeys(password);
        }
    }

    public void clickLogin(){
        this.appiumDriver.findElement(loginBtnElement).click();
    }

    public String getEmailErrMsg(){
        return this.appiumDriver.findElement(emailInputEmailErrMsgElement).getText().trim();
    }

    public String getPasswordErrMsg(){
        return this.appiumDriver.findElement(emailInputPasswordErrMsgElement).getText().trim();
    }
}
