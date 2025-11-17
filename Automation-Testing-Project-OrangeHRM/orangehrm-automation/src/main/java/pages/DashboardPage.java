package pages;

import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;
import java.util.Arrays;

public class DashboardPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    By quickLaunchButtons = By.xpath("//div[@class='orangehrm-quick-launch']/div/button");
    By profileIcon = By.xpath("//p[@class='oxd-userdropdown-name']");
    By profileDropdownItems = By.xpath("//ul[@class='oxd-dropdown-menu']/li/a");
    By sidebar = By.xpath("//aside[contains(@class, 'oxd-sidepanel')]");
    By toggleButton = By.xpath("//button[@class='oxd-icon-button oxd-main-menu-button']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- METHODS ----------

    public boolean isDashboardLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader)).isDisplayed();
    }

    public boolean verifyQuickLaunchNavigation() {
        List<WebElement> quickLaunchItems = driver.findElements(quickLaunchButtons);

        for (int i = 0; i < quickLaunchItems.size(); i++) {
            quickLaunchItems = driver.findElements(quickLaunchButtons);
            WebElement item = quickLaunchItems.get(i);
            String menuName = item.getText();
            System.out.println("Clicking: " + menuName);

            item.click();

            // Wait for some visible page element depending on the menu
            if (menuName.contains("Assign Leave")) {
                WebElement header = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//h6[text()='Assign Leave']")));
                Assert.assertTrue(header.isDisplayed(), "Assign Leave page not displayed!");
            } 
            else if (menuName.contains("Leave List")) {
                WebElement header = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//h6[text()='Leave List']")));
                Assert.assertTrue(header.isDisplayed(), "Leave List page not displayed!");
            } 
            else if (menuName.contains("Timesheets")) {
                WebElement header = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//h6[text()='Timesheets']")));
                Assert.assertTrue(header.isDisplayed(), "Timesheet page not displayed!");
            }

            // Navigate back to Dashboard
            driver.navigate().back();
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(dashboardHeader));
        }
        return true;
    }


    public boolean verifyProfileDropdownOptions() {
        driver.findElement(profileIcon).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileDropdownItems));
        List<WebElement> items = driver.findElements(profileDropdownItems);
        List<String> expected = Arrays.asList("About", "Support", "Change Password", "Logout");

        for (String option : expected) {
            boolean found = items.stream().anyMatch(e -> e.getText().trim().equalsIgnoreCase(option));
            if (!found) return false;
        }
        return true;
    }

    public boolean verifySidebarToggle() {
        try {
            WebElement sidebarElement = driver.findElement(sidebar);
            WebElement toggleBtn = wait.until(ExpectedConditions.elementToBeClickable(toggleButton));

            // Use JavaScript click to avoid ElementClickInterceptedException
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggleBtn);
            Thread.sleep(1000); // wait for animation

            int widthCollapsed = sidebarElement.getSize().getWidth();

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggleBtn);
            Thread.sleep(1000);

            int widthExpanded = sidebarElement.getSize().getWidth();

            System.out.println("Sidebar collapsed width: " + widthCollapsed);
            System.out.println("Sidebar expanded width: " + widthExpanded);

            return widthExpanded > widthCollapsed;
        } catch (Exception e) {
            System.out.println("Sidebar toggle verification failed: " + e.getMessage());
            return false;
        }
    }

}

