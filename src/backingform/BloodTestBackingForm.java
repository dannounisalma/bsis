package backingform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import model.bloodtesting.BloodTest;
import model.bloodtesting.BloodTestCategory;


public class BloodTestBackingForm {

    @JsonIgnore
    private BloodTest bloodTest;

    private Integer numberOfConfirmatoryTests;

    public BloodTestBackingForm() {
        bloodTest = new BloodTest();
    }

    public BloodTest getBloosTest() {
        return bloodTest;
    }
    
    public void setId(Integer id){
        bloodTest.setId(id);
    }

    public void setBloosTest(BloodTest bloodTest) {
        this.bloodTest = bloodTest;
    }

    public String getTestName() {
        return bloodTest.getTestName();
    }

    public String getTestNameShort() {
        return bloodTest.getTestNameShort();
    }

    public BloodTest getBloodTest() {
        return bloodTest;
    }

    public Integer getNumberOfConfirmatoryTests() {
        return numberOfConfirmatoryTests;
    }

    public void setBloodTest(BloodTest bloodTest) {
        this.bloodTest = bloodTest;
    }

    public void setNumberOfConfirmatoryTests(Integer numberOfConfirmatoryTests) {
        this.numberOfConfirmatoryTests = numberOfConfirmatoryTests;
    }

    public void setTestName(String testName) {
        bloodTest.setTestName(testName);
    }

    public void setTestNameShort(String testNameShort) {
        bloodTest.setTestNameShort(testNameShort);
    }

    public void setCategory(String Category) {
        bloodTest.setCategory(BloodTestCategory.valueOf(Category));
    }
    
    public void setvalidResults(String validResults){
        bloodTest.setValidResults(validResults);
    }
    
    public void setIsActive(Boolean isActive){
        bloodTest.setIsActive(isActive);
    }
    
    public void setNegativeResults(String negativeResults){
        bloodTest.setNegativeResults(negativeResults);
    }
    
    public void setPositiveResults(String positiveResults){
        bloodTest.setNegativeResults(positiveResults);
    }
    
    public void setRankInCategory(Integer rankInCategory){
        bloodTest.setRankInCategory(rankInCategory);
    }

}
