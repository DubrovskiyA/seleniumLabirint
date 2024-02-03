package internet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class TheInternetSelenoid extends Assertions {
    private WebDriver driver;
    @BeforeEach
    public void setUp() throws MalformedURLException {
//        сгенерировано selenoid ui
        String hub=System.getProperty("hub","http://localhost:4444");
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "119.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            /* How to add test badge */
            put("name", "Test badge...");

            /* How to set session timeout */
            put("sessionTimeout", "15m");

            /* How to set timezone */
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});

            /* How to add "trash" button */
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
//            put("enableVNC", true);

            /* How to enable video recording */
            put("enableVideo", false);
        }});
        driver = new RemoteWebDriver(new URL(hub+"/wd/hub"), options);
    }
    @AfterEach
    public void tearsDown(){
        driver.quit();
    }

    @Test
    public void formAuthentication(){
        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        String textToBe="You logged into a secure area!";
        String flash = driver.findElement(By.id("flash")).getText();
        assertTrue(flash.contains(textToBe));
        String textLogout = driver.findElement(By.cssSelector("a.button")).getText();
        assertEquals("Logout",textLogout);
    }
//    @Test
//    public void iFrames(){
//        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
//        driver.get("https://the-internet.herokuapp.com/iframe");
//        driver.switchTo().frame("mce_0_ifr");
//        String p = driver.findElement(By.cssSelector("p")).getText();
//        assertEquals("Your content goes here.",p);
//    }
//    @Test
//    public void multipleWindows(){
//        driver.get("https://the-internet.herokuapp.com/windows");
//        driver.findElement(By.cssSelector("#content a")).click();
//        Object[] objects = driver.getWindowHandles().toArray();
//        driver.switchTo().window(objects[1].toString());
//        String newWindow = driver.getTitle();
//        assertEquals("New Window", newWindow);
//    }
//    @Test
//    public void notificationMessages(){
//        driver.get("https://the-internet.herokuapp.com/notification_message_rendered");
//        String flash = driver.findElement(By.id("flash")).getText();
//        assertTrue(flash.contains("Action successful"));
//    }
}
