package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.DashboardPage;
import pages.LoginPage;
import utilities.BrowserFactory;

public class DashboardTest {
	WebDriver driver;
    LoginPage login;
    DashboardPage dashboard;

    @BeforeClass
    public void setup() {
        driver = BrowserFactory.startBrowser("https://opensource-demo.orangehrmlive.com/");
        login = new LoginPage(driver);
        dashboard = new DashboardPage(driver);
        login.setUsername("Admin");
        login.setPassword("admin123");
        login.clickLogin();
    }

    @Test(priority = 1,description = "TC_DASHBOARD_01 - Validate Dashboard loads correctly after successful login")
    public void testDashboardLoad() {
        Assert.assertTrue(dashboard.isDashboardLoaded(), "Dashboard did not load correctly!");
    }
    
    @Test(priority = 2,description = "TC_DASHBOARD_02 - Validate Quick Launch navigation")
    public void testQuickLaunchNavigation() {
        Assert.assertTrue(dashboard.verifyQuickLaunchNavigation(), "Quick Launch navigation failed!");
    }

    @Test(priority = 3,description = "TC_DASHBOARD_03 - Verify profile dropdown options")
    public void testProfileDropdownOptions() {
        Assert.assertTrue(dashboard.verifyProfileDropdownOptions(), "Profile dropdown options not displayed correctly!");
    }
    
    @Test(priority = 4,description = "TC_DASHBOARD_04 - Verify sidebar expand and collapse")
    public void testSidebarToggle() {
        Assert.assertTrue(dashboard.verifySidebarToggle(), "Sidebar did not expand/collapse correctly!");
    }
    
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
