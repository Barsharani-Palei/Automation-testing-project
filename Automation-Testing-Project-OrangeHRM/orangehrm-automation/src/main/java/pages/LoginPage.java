package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By username = By.name("username");
    By password = By.name("password");
    By loginBtn = By.xpath("//button[@type='submit']");
    By incorrectCred = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");
    By userError = By.xpath("(//span[text()='Required'])[1]");
    By passError = By.xpath("(//span[text()='Required'])[2]");
    By passwordField = By.name("password");
    By forgotLink = By.xpath("//p[@class='oxd-text oxd-text--p orangehrm-login-forgot-header']");
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        //this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
    public void setUsername(String user) {
        driver.findElement(username).sendKeys(user);
    }

    public void setPassword(String pass) {
        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }

    public String getIncorrectCred() {
        return driver.findElement(incorrectCred).getText();
    }
    
    public String getUserError() {
    	return driver.findElement(userError).getText();
    }
    
    public String getPassError() {
    	return driver.findElement(passError).getText();
    }
    
    public String getPassAttribute() {
    	return driver.findElement(passwordField).getAttribute("type");
    }
    
    public void clickForgotLink() {
    	driver.findElement(forgotLink).click();
    }
}
