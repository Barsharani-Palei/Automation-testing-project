package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class LogoutPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//p[@class='oxd-userdropdown-name']")
    WebElement profileIcon;

    @FindBy(xpath = "//a[normalize-space()='Logout']")
    WebElement logoutButton;

    @FindBy(xpath = "//h5[normalize-space()='Login']")
    WebElement loginHeader;

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    public boolean isLogoutOptionVisible() {
        profileIcon.click();
        wait.until(ExpectedConditions.visibilityOf(logoutButton));
        return logoutButton.isDisplayed();
    }

    public void clickLogout() {
        profileIcon.click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public boolean isAtLoginPage() {
        wait.until(ExpectedConditions.visibilityOf(loginHeader));
        return loginHeader.isDisplayed();
    }
}
