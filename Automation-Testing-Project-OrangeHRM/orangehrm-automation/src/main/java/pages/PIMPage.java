package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;

public class PIMPage {
    WebDriver driver;
    WebDriverWait wait;

    // ---------- LOCATORS ----------
    By pimMenu = By.xpath("//span[text()='PIM']");
    By addEmployeeBtn = By.xpath("//a[text()='Add Employee']");
    By empListBtn = By.xpath("//a[text()='Employee List']");
    By firstName = By.name("firstName");
    By lastName = By.name("lastName");
    By empId = By.xpath("//label[text()='Employee Id']/following::input[1]");
    By saveBtn = By.xpath("//button[@type='submit']");
    By profilePhoto = By.xpath("//input[@type='file']");
    By searchName = By.xpath("//label[text()='Employee Name']/following::input[1]");
    By searchBtn = By.xpath("//button[normalize-space()='Search']");
    By editIcon = By.xpath("//i[@class='oxd-icon bi-pencil-fill']");
    By noRecords = By.xpath("//span[text()='No Records Found']");
    By employeeTable = By.xpath("//div[@role='table']//div[@role='row']");
    By requiredMsg = By.xpath("//span[text()='Required']");
    By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");

    public PIMPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public void openPIMModule() {
        driver.findElement(pimMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addEmployeeBtn));
    }

    public void openAddEmployee() {
        driver.findElement(addEmployeeBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
    }

    public void openEmployeeList() {
        driver.findElement(empListBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBtn));
    }

    public void addEmployee(String fName, String lName) {
        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(saveBtn).click();
        // wait for either success or Personal Details
        wait.until(ExpectedConditions.visibilityOfElementLocated(personalDetailsHeader));
    }

    public boolean isEmployeeAdded() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(personalDetailsHeader)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean validateMandatoryFields() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();

        //driver.findElement(saveBtn).click();
        return driver.findElements(requiredMsg).size() > 0;
    }

    public boolean isEmpIdGenerated() {
        String idValue = driver.findElement(empId).getAttribute("value");
        return !idValue.isEmpty();
    }

    // ✅ FIXED uploadPhoto
    public void uploadPhoto(String path) {
        WebElement photoInput = wait.until(ExpectedConditions.presenceOfElementLocated(profilePhoto));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", photoInput);
        photoInput.sendKeys(path);
    }

    // ✅ FIXED searchEmployee
    public boolean searchEmployee(String name) {
        driver.findElement(searchName).clear();
        driver.findElement(searchName).sendKeys(name);
        driver.findElement(searchBtn).click();

        // wait for table or no record
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(employeeTable),
                ExpectedConditions.visibilityOfElementLocated(noRecords)
        ));

        // verify at least one row or not found
        List<WebElement> rows = driver.findElements(employeeTable);
        boolean found = !rows.isEmpty() && driver.findElements(noRecords).isEmpty();
        return found;
    }

    public void editEmployee(String name) {
        openEmployeeList();
        driver.findElement(searchName).clear();
        driver.findElement(searchName).sendKeys(name);
        driver.findElement(searchBtn).click();
        wait.until(ExpectedConditions.elementToBeClickable(editIcon));
        driver.findElement(editIcon).click();
    }
    

}
