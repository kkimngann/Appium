package test_flows.authentication;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import models.components.LoginComponent;
import models.components.LoginSuccessAlertComponent;
import models.pages.LoginPage;
import org.apache.commons.validator.routines.EmailValidator;
import test_flows.BaseFlow;


public class LoginFlow extends BaseFlow {
    private final AppiumDriver<MobileElement> appiumDriver;
    private final String email;
    private final String password;

    public LoginFlow(AppiumDriver<MobileElement> appiumDriver, String email, String password) {
        super(appiumDriver);
        this.appiumDriver = appiumDriver;
        this.email = email;
        this.password = password;
    }

    public void login(){
        LoginPage loginPage = new LoginPage(this.appiumDriver);
        if(!this.email.isEmpty()){
            loginPage.loginComponent().inputEmail(this.email);
        }
        if(!this.password.isEmpty()){
            loginPage.loginComponent().inputPassword(this.password);
        }
        loginPage.loginComponent().clickLogin();
    }

    public void verifyLogin(){
        LoginPage loginPage = new LoginPage(this.appiumDriver);
        LoginComponent loginComponent = loginPage.loginComponent();
        LoginSuccessAlertComponent loginSuccessAlertComponent = loginPage.loginSuccessAlertComponent();
        boolean isValidEmail = EmailValidator.getInstance().isValid(email);
        boolean isValidPassword = password.length() >= 8;
        System.out.printf("Email: %s, %b | Password: %s, %b\n", email, isValidEmail, password, isValidPassword);

        if(!isValidEmail){
            verifyInvalidEmail(loginComponent);
        }

        if(!isValidPassword){
            verifyInvalidPassword(loginComponent);
        }

        if(isValidEmail && isValidPassword){
            verifyCorrectLoginInfo(loginSuccessAlertComponent);
            loginPage.loginSuccessAlertComponent().clickOK();
        }
    }

    private void verifyInvalidEmail(LoginComponent loginComponent) {
        String actualMsg = loginComponent.getEmailErrMsg();
        String expectedMsg = "Please enter a valid email address";
        if(actualMsg.equals(expectedMsg)){
            System.out.println("Show password invalid email correctly");
        }
        else{
            System.out.println("Show password invalid email incorrectly");
            System.out.println("\tActual: " + actualMsg);
            System.out.println("\tExpected: " + expectedMsg);
        }
    }

    private void verifyInvalidPassword(LoginComponent loginComponent) {
        String actualMsg = loginComponent.getPasswordErrMsg();
        String expectedMsg = "Please enter at least 8 characters";
        if(actualMsg.equals(expectedMsg)){
            System.out.println("Show password invalid password correctly");
        }
        else{
            System.out.println("Show password invalid password incorrectly");
            System.out.println("\tActual: " + actualMsg);
            System.out.println("\tExpected: " + expectedMsg);
        }
    }

    private void verifyCorrectLoginInfo(LoginSuccessAlertComponent loginSuccessAlertComponent) {
        String actualText= loginSuccessAlertComponent.getTextAlertTitle();
        String expectedText = "Success";
        if(actualText.equals(expectedText)){
            System.out.println("Show alert title correctly");
        }
        else{
            System.out.println("Show alert title incorrectly");
            System.out.println("\tActual: " + actualText);
            System.out.println("\tExpected: " + expectedText);
        }

        actualText = loginSuccessAlertComponent.getTextAlertContent();
        expectedText = "You are logged in!";
        if(actualText.equals(expectedText)){
            System.out.println("Show alert content correctly");
        }
        else{
            System.out.println("Show alert content incorrectly");
            System.out.println("\tActual: " + actualText);
            System.out.println("\tExpected: " + expectedText);
        }

        actualText = loginSuccessAlertComponent.getTextAlertButtonOK();
        expectedText = "OK";
        if(actualText.equals(expectedText)){
            System.out.println("Show alert button OK correctly");
        }
        else{
            System.out.println("Show alert button OK incorrectly");
            System.out.println("\tActual: " + actualText);
            System.out.println("\tExpected: " + expectedText);
        }
    }
}
