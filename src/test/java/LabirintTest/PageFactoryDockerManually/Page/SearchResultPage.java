package LabirintTest.PageFactoryDockerManually.Page;

import LabirintTest.PageFactoryDockerManually.Block.BookCard;
import LabirintTest.PageFactoryDockerManually.Block.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class SearchResultPage {
    private final WebDriver driver;
    private Header header;
    @FindBy(css=".desktop-subnavigagions-block .sorting-value")
    private WebElement sortTypeMenu;
    @FindBy(css=".product-card")
    private List<WebElement> productCards;
    @FindBy(css=".desktop-subnavigagions-block .filter-reset")
    private List<WebElement> listOfChips;
    @FindBy(css=".loading-panel")
    private WebElement loader;


    public SearchResultPage(WebDriver driver){
        this.driver=driver;
        header= PageFactory.initElements(driver, Header.class);
    }
    public void setSortType(String sortType){
        sortTypeMenu.click();
        driver.findElement(By.cssSelector("[data-event-content=\""+sortType+"\"]")).click();
        waitLoader();
    }


    public List<BookCard> getAllBooks(){
        List<BookCard> bookCards = new ArrayList<>();
        for (WebElement element : productCards) {
            BookCard bookCard=new BookCard(element);
            bookCards.add(bookCard);
        }
        return bookCards;
    }
    public void closeChip(String label){
        for (WebElement chip : listOfChips) {
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
        waitSpinner.until(ExpectedConditions.invisibilityOf(loader));
    }

}
