package test.Form;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.BaseTest;
import test_data.DataObjectBuilder;
import test_data.models.FormInputData;
import test_data.models.LoginInfoData;
import test_flows.forms.FormsFlow;

public class FormsTest extends BaseTest {

    @Test(dataProvider = "formInputData")
    public void testFormInput(FormInputData testData){
        FormsFlow formsFlow = new FormsFlow(getDriver());
        formsFlow.gotoFormsPage();
        formsFlow.fillForms(testData.getTextInput(), testData.getSelectOptionIndex());
        formsFlow.verifyFormsDisplay();
    }

    @DataProvider
    public FormInputData[] formInputData(){
        String filePath = "/src/test/java/test_data/forms/FormsInput.json";
        return DataObjectBuilder.buildDataObject(filePath, FormInputData[].class);
    }
}
