package LabirintTest.POM.Block;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BookCard {
    private WebElement bookCard;
    public BookCard(WebElement element){
        bookCard=element;
    }
    public void addBookToCart(){
        bookCard.findElement(By.cssSelector("._btn")).click();
    }
}
