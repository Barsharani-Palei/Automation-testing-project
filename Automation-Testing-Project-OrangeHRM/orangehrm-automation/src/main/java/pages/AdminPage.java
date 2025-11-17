package pages;

import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class AdminPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By adminTab = By.xpath("//span[text()='Admin']");
    By addBtn = By.xpath("//button[normalize-space()='Add']");
    By usernameField = By.xpath("//label[text()='Username']/following::input[1]");
    By passwordField = By.xpath("//label[text()='Password']/following::input[1]");
    By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/following::input[1]");
    By saveBtn = By.xpath("//button[normalize-space()='Save']");
    By searchField = By.xpath("//label[text()='Username']/following::input[1]");
    By searchBtn = By.xpath("//button[normalize-space()='Search']");
    By deleteBtn = By.xpath("(//button[@class='oxd-icon-button oxd-table-cell-action-space'])[1]");
    By confirmDeleteBtn = By.xpath("//button[normalize-space()='Yes, Delete']");
    By userRoleDropdown = By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-text')]");

    
    By searchEmployeeName = By.xpath("//label[text()='Employee Name']/../following-sibling::div//input");
   
    By resultsTable = By.xpath("//div[@class='oxd-table-body']");

    // Constructor
    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Navigate to Admin module
    public void openAdminModule() {
        wait.until(ExpectedConditions.elementToBeClickable(adminTab)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addBtn));
    }

    // Add new user
    public void addNewUser(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);
        driver.findElement(saveBtn).click();
    }

    // Search for a user
    public void searchUser(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchField)).clear();
        driver.findElement(searchField).sendKeys(username);
        driver.findElement(searchBtn).click();
    }

    // Delete a user
    public void deleteUser() {
        wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteBtn)).click();
    }

    // Verify User Roles dropdown visible
    public boolean verifyUserRolesDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userRoleDropdown)).isDisplayed();
    }

    // Verify Admin tab opened successfully
    public boolean isAdminPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(addBtn)).isDisplayed();
    }
    
    public void searchEmployeeByName(String empName) {
        // Navigate to Admin Tab (click Admin in left menu)
        driver.findElement(adminTab).click();

        // Wait for page load
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchEmployeeName));

        // Enter employee name inside Auto Suggest field
        WebElement empField = driver.findElement(searchEmployeeName);
        empField.click();
        empField.sendKeys(empName);

        // Wait for dropdown suggestion and click first suggestion
        By dropdownOption = By.xpath("//div[@role='option']//span[contains(text(),'" + empName + "')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownOption)).click();

        // Click Search
        driver.findElement(searchBtn).click();

        // Wait for results table
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTable));
    }

}
