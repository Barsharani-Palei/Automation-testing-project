package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LogoutPage;
import pages.LoginPage;
import utilities.BrowserFactory;

public class LogoutTest {
    WebDriver driver;
    LoginPage login;
    LogoutPage logout;

    @BeforeClass
    public void setup() {
        driver = BrowserFactory.startBrowser("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        login = new LoginPage(driver);
        logout = new LogoutPage(driver);

        // Perform login
        login.setUsername("Admin");
        login.setPassword("admin123");
        login.clickLogin();
    }

    @Test(priority = 1, description = "TC_LOGOUT_03 - Verify logout option visible")
    public void verifyLogoutOptionVisible() {
        Assert.assertTrue(logout.isLogoutOptionVisible(), "Logout option should be visible");
    }

    @Test(priority = 2, description = "TC_LOGOUT_01 - Verify user can logout")
    public void verifyUserCanLogout() {
        logout.clickLogout();
        Assert.assertTrue(logout.isAtLoginPage(), "User should be redirected to login page");
    }

    @Test(priority = 3, description = "TC_LOGOUT_02 - Verify session expires")
    public void verifySessionExpires() throws InterruptedException {
    	login.setUsername("Admin");
        login.setPassword("admin123");
        Thread.sleep(6000); // simulate 10 min idle 
        driver.navigate().refresh();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Session should expire and redirect to login page");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
