package LabirintTest.POM.Block;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Header {
    private  final WebDriver driver;
    public Header(WebDriver driver){
        this.driver=driver;
    }
    public void search(String searchValue){
        driver.findElement(By.id("search-field")).clear();
        driver.findElement(By.id("search-field")).sendKeys(searchValue, Keys.RETURN);
    }
    public int getCartCounter(){
        return Integer.parseInt(driver.findElement(By.cssSelector(".j-cart-count")).getText());
    }
    public Header awaitCartCounterToBe(int countToBe){
        WebDriverWait wait=new WebDriverWait(driver, Duration.of(4, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.textToBe(By.cssSelector(".j-cart-count"), Integer.toString(countToBe)));
        return this;
    }
}

