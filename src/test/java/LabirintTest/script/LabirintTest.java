package LabirintTest.script;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabirintTest {
    WebDriver driver;
    @BeforeEach
    public void setUp(){
        driver=new ChromeDriver();
    }
    @AfterEach
    public void tearsDown(){
        if (driver!=null){
            driver.quit();
        }
    }
    @Test
    public void test() throws InterruptedException {
        driver.get("https://labirint.ru");
        driver.manage().window().maximize();
        driver.manage().addCookie(new Cookie("cookie_policy","1"));
        driver.findElement(By.id("search-field")).sendKeys("Java", Keys.RETURN);
        driver.findElement(By.cssSelector(".desktop-subnavigagions-block .sorting-value")).click();
        driver.findElement(By.cssSelector("[data-event-content=\"высокий рейтинг\"]")).click();
        WebDriverWait waitSpinner=new WebDriverWait(driver,Duration.of(4,ChronoUnit.SECONDS));
        waitSpinner.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".loading-panel"))));
        driver.findElement(By.cssSelector(".desktop-subnavigagions-block [href*='rating&way=back&available=1&wait=1&preorder=1']")).click();
        waitSpinner.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".loading-panel"))));
        driver.findElement(By.cssSelector(".desktop-subnavigagions-block [href*='rating&way=back&available=1&preorder=1']")).click();
        waitSpinner.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".loading-panel"))));
        List<WebElement> productCards = driver.findElements(By.cssSelector(".product-card"));
        int i=0;
        for (WebElement p : productCards) {
            p.findElement(By.cssSelector("._btn")).click();
            i++;
        }
        WebDriverWait wait=new WebDriverWait(driver, Duration.of(4, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".j-cart-count"), Integer.toString(i)));
//        Thread.sleep(4000);
        String basketCount = driver.findElement(By.cssSelector(".j-cart-count")).getText();
        assertEquals("60",basketCount);
    }
}
