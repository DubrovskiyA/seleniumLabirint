package LabirintTest.POM.Page;

import LabirintTest.POM.Block.BookCard;
import LabirintTest.POM.Block.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {
    private final WebDriver driver;
    private Header header;
    public SearchResultPage(WebDriver driver){
        this.driver=driver;
        header=new Header(driver);
    }
    public void setSortType(String sortType){
        driver.findElement(By.cssSelector(".desktop-subnavigagions-block .sorting-value")).click();
        driver.findElement(By.cssSelector("[data-event-content=\""+sortType+"\"]")).click();
        waitLoader();
    }


    public List<BookCard> getAllBooks(){
        List<WebElement> elements = driver.findElements(By.cssSelector(".product-card"));
        List<BookCard> bookCards = new ArrayList<>();
        for (WebElement element : elements) {
            BookCard bookCard=new BookCard(element);
            bookCards.add(bookCard);
        }
        return bookCards;
    }
    public void closeChip(String label){
        List<WebElement> chips = driver.findElements(By.cssSelector(".desktop-subnavigagions-block .filter-reset"));
        for (WebElement chip : chips) {
            String text = chip.getText();
            if (text.equalsIgnoreCase(label)){
                chip.findElement(By.cssSelector(".filter-reset__icon")).click();
                waitLoader();
                break;
            }
        }
    }

    public Header getHeader() {
        return header;
    }
    private void waitLoader() {
        WebDriverWait waitSpinner=new WebDriverWait(driver, Duration.of(4, ChronoUnit.SECONDS));
        waitSpinner.until(ExpectedConditions.stalenessOf(driver.findElement(By.cssSelector(".loading-panel"))));
    }

}
