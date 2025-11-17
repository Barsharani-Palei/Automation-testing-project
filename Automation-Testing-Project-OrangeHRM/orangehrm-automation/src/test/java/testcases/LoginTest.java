package testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utilities.BrowserFactory;

public class LoginTest {
    WebDriver driver;
    LoginPage login;
   

    @BeforeMethod
    public void setup() {
        driver = BrowserFactory.startBrowser("https://opensource-demo.orangehrmlive.com/");
        login = new LoginPage(driver);
    }

    @Test(priority = 1, description = " TC_LOGIN_01 - Validate login with empty fields")
    public void testBlankFieldLogin() {
    	login.setUsername("");
        login.setPassword("");
        login.clickLogin();
        Assert.assertEquals(login.getUserError(), "Required");
        Assert.assertEquals(login.getPassError(), "Required");
    }
    
    @Test(priority = 2, description = " TC_LOGIN_02 - Validate login with invalid username")
    public void testInvaidUsername() {
    	login.setUsername("user");
        login.setPassword("admin123");
        login.clickLogin();
        Assert.assertEquals(login.getIncorrectCred(), "Invalid credentials");
    }
    
    @Test(priority = 3, description = " TC_LOGIN_03 - Validate login with wrong password")
    public void testWrongPass() {
        login.setUsername("Admin");
        login.setPassword("adm123");
        login.clickLogin();
        Assert.assertEquals(login.getIncorrectCred(), "Invalid credentials");
    }

    @Test(priority = 4, description = " TC_LOGIN_06 - Validate login with valid credentials")
    public void testValidLogin() {
        login.setUsername("Admin");
        login.setPassword("admin123");
        login.clickLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "User not redirected to Dashboard!");
    }

    @Test(priority = 5, description = "TC_LOGIN_04 - Validate that the password field is masked")
    public void testPasswordFieldIsMasked() {
        Assert.assertEquals(login.getPassAttribute(), "password", "Password field is not masked!");
    }
    
    @Test(priority = 6, description = "TC_LOGIN_05 - Validate forgot password page opens")
    public void testForgotPasswordLink() {
    	login.clickForgotLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("requestPasswordResetCode"), "User not redirected to reset password!");
    }
    
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

