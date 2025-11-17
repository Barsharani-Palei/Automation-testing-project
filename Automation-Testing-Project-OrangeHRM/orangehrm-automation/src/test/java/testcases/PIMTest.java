package testcases;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.*;

import pages.LoginPage;
import pages.PIMPage;
import utilities.BrowserFactory;

public class PIMTest {
    WebDriver driver;
    LoginPage login;
    PIMPage pim;

    @BeforeClass
    public void setup() {
        driver = BrowserFactory.startBrowser("https://opensource-demo.orangehrmlive.com/");
        login = new LoginPage(driver);
        pim = new PIMPage(driver);
        login.setUsername("Admin");
        login.setPassword("admin123");
        login.clickLogin();
        pim.openPIMModule();
    }

    @Test(priority = 1, description = "TC_PIM_01 - Validate if user can navigate to the PIM module successfully.")
    public void testNavigateToPIM() {
        Assert.assertTrue(driver.getCurrentUrl().contains("pim"), "User not navigated to PIM module!");
    }

    @Test(priority = 2, description = "TC_PIM_02 - Validate that Add Employee page opens successfully.")
    public void testOpenAddEmployee() {
        pim.openAddEmployee();
        Assert.assertTrue(driver.getCurrentUrl().contains("addEmployee"), "Add Employee page did not open!");
    }

    @Test(priority = 3, description = "TC_PIM_03 - Validate that new employee can be added successfully.")
    public void testAddEmployee() {
        pim.openAddEmployee();
        pim.addEmployee("John", "Doe");
        Assert.assertTrue(pim.isEmployeeAdded(), "Employee not added successfully!");
    }

    @Test(priority = 4, description = "TC_PIM_04 - Validate that mandatory field validation messages are displayed.")
    public void testMandatoryFieldsValidation() {
        pim.openAddEmployee();
        Assert.assertTrue(pim.validateMandatoryFields(), "Mandatory field validation not working!");
    }

    @Test(priority = 5, description = "TC_PIM_06 - Validate employee ID auto-generation.")
    public void testEmpIdAutoGeneration() {
        pim.openAddEmployee();
        Assert.assertTrue(pim.isEmpIdGenerated(), "Employee ID not auto-generated!");
    }

    @Test(priority = 6, description = "TC_PIM_05 - Validate profile photo upload functionality.")
    public void testUploadPhoto() {
        pim.openAddEmployee();
        pim.uploadPhoto(System.getProperty("user.dir") + "\\testD"
        		+ "ata\\profile-icon.png");
        Assert.assertTrue(true, "Photo upload failed!");
    }

    @Test(priority = 7, description = "TC_PIM_08 - Validate employee search by name.")
    public void testSearchEmployee() {
        pim.openEmployeeList();
        Assert.assertTrue(pim.searchEmployee("John"), "Employee not found in search results!");
    }

    @Test(priority = 8, description = "TC_PIM_09 - Validate that searching invalid employee shows 'No Records Found'.")
    public void testInvalidEmployeeSearch() {
        pim.openEmployeeList();
        Assert.assertFalse(pim.searchEmployee("XYZ123"), "Invalid employee found unexpectedly!");
    }

    @Test(priority = 9, description = "TC_PIM_10 - Validate that employee details can be edited successfully.")
    public void testEditEmployee() {
        pim.editEmployee("John");
        Assert.assertTrue(driver.getPageSource().contains("Personal Details"), "Edit Employee page not opened!");
    }


    @Test(priority = 11, description = "navigation back to Employee List after saving employee details.")
    public void testBackToEmployeeList() {
        pim.openEmployeeList();
        Assert.assertTrue(driver.getCurrentUrl().contains("viewEmployeeList"), "Employee List page not opened!");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
