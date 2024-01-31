package LabirintTest.PageFactoryDockerManually.Block;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.openqa.selenium.By.cssSelector;

public class Header {
    private  final WebDriver driver;
    @FindBy(css="#search-field")
    private WebElement searchInput;
    @FindBy(css=".j-cart-count")
    private WebElement cartCounter;
    private By cartIconLocator =cssSelector(".j-cart-count");
    public Header(WebDriver driver){
        this.driver=driver;
    }
    public void search(String searchValue){
        searchInput.clear();
        searchInput.sendKeys(searchValue, Keys.RETURN);
    }
    public int getCartCounter(){
        return Integer.parseInt(cartCounter.getText());
    }
    public Header awaitCartCounterToBe(int countToBe){
        WebDriverWait wait=new WebDriverWait(driver, Duration.of(4, ChronoUnit.SECONDS));
        wait.until(ExpectedConditions.textToBe(cartIconLocator, Integer.toString(countToBe)));
        return this;
    }
}

