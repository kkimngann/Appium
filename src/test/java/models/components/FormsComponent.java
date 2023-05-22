package models.components;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormsComponent {
    private final AppiumDriver<MobileElement> appiumDriver;
    private final By inputFieldSel = MobileBy.AccessibilityId("text-input");
    private final By inputtedFieldSel = MobileBy.AccessibilityId("input-text-result");
    private final By switchSel = MobileBy.AccessibilityId("switch");
    private final By dropdownSel = MobileBy.AccessibilityId("Dropdown");
    private final By selectListDialogSel = MobileBy.id("com.wdiodemoapp:id/select_dialog_listview");
    private final By selectListOptionsSel = MobileBy.id("android:id/text1");
    private final By btnActiveSel = MobileBy.AccessibilityId("button-Active");

    public FormsComponent(AppiumDriver<MobileElement> appiumDriver) {
        this.appiumDriver = appiumDriver;
    }

    public void inputField(String inputStr){
        this.appiumDriver.findElement(inputFieldSel).sendKeys(inputStr);
    }

    public String getInputResult(){
        return this.appiumDriver.findElement(inputtedFieldSel).getText();
    }

    public void switchToOn(){
        MobileElement switchElement = this.appiumDriver.findElement(switchSel);
        if(switchElement.getAttribute("checked").equals("false")){
            switchElement.click();
        }
    }

    public void selectDropdownOption(int optionIndex){
        MobileElement dropdownElement = this.appiumDriver.findElement(dropdownSel);
        dropdownElement.click();
        WebDriverWait wait = new WebDriverWait(appiumDriver, 5L);
        MobileElement selectListDialogElement = (MobileElement) wait.until(
                ExpectedConditions.visibilityOfElementLocated(selectListDialogSel));
        selectListDialogElement.findElements(selectListOptionsSel).get(optionIndex).click();
    }

    public void clickBtnActive(){
        this.appiumDriver.findElement(btnActiveSel).click();
    }
}
