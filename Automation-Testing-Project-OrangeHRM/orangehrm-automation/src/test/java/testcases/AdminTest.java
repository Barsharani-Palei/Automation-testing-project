package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.AdminPage;
import utilities.BrowserFactory;

public class AdminTest {
    WebDriver driver;
    LoginPage loginPage;
    AdminPage adminPage;

    @BeforeClass
    public void setup() {
        driver = BrowserFactory.startBrowser("https://opensource-demo.orangehrmlive.com/");
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);

        // Perform login
        loginPage.setUsername("Admin");
        loginPage.setPassword("admin123");
        loginPage.clickLogin();
    }

    @Test(priority = 1, description = "TC_ADMIN_01 - Verify navigation to Admin module")
    public void verifyNavigationToAdmin() {
        adminPage.openAdminModule();
        Assert.assertTrue(adminPage.isAdminPageDisplayed(), "Admin page not displayed");
    }

  

    @Test(priority = 2, description = "TC_ADMIN_02 - Verify searching for a user")
    public void verifySearchUser() {
        adminPage.openAdminModule();
        adminPage.searchUser("Admin");
        Assert.assertTrue(adminPage.isAdminPageDisplayed(), "Search failed");
    }


    @Test(priority = 3, description = "TC_ADMIN_03 - Verify user roles displayed")
    public void verifyUserRolesDisplayed() {
        adminPage.openAdminModule();
        Assert.assertTrue(adminPage.verifyUserRolesDisplayed(), "User roles dropdown not visible");
    }
    
    @Test(priority = 4, description = "Verify search by employee name")
    public void testSearchEmployeeByName() {
        String employeeName = "Timothy Amiano"; // existing user

        adminPage.searchEmployeeByName(employeeName);

        //Assertion → Validate first row contains that employee name
        WebElement firstRecord = driver.findElement(
                By.xpath("//div[@class='oxd-table-body']//div[@role='row'][1]//div[3]")
        );

        Assert.assertTrue(firstRecord.getText().contains(employeeName),
                "Employee name search failed!");
    }


    @AfterClass
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}
