package LabirintTest.PageFactorySelenoid.Block;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookCard {
    private WebElement bookCard;
    @FindBy(css=".btn-tocart.buy-link")
    private WebElement buyButton;
    public BookCard(WebElement element){
        bookCard=element;
        PageFactory.initElements(element,this);
    }
    public void addBookToCart(){
        buyButton.click();
    }
}
