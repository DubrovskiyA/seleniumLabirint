package LabirintTest.PageFactory;

import LabirintTest.PageFactory.Block.BookCard;
import LabirintTest.PageFactory.Page.MainPage;
import LabirintTest.PageFactory.Page.SearchResultPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabirintPFTest {
    private WebDriver driver;
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
    public void testPOM(){
        MainPage mainPage= PageFactory.initElements(driver, MainPage.class);
        mainPage.open();
        mainPage.setCookie("cookie_policy","1");
        mainPage.getHeader().search("Java");
        SearchResultPage searchResultPage=PageFactory.initElements(driver,SearchResultPage.class);
        searchResultPage.setSortType("высокий рейтинг");
        searchResultPage.closeChip("нет в продаже");
        searchResultPage.closeChip("ожидаются");
        List<BookCard> books = searchResultPage.getAllBooks();
        for (BookCard book : books) {
            book.addBookToCart();
        }
        int cartCounter = searchResultPage
                .getHeader()
                .awaitCartCounterToBe(books.size())
                .getCartCounter();

        assertEquals(books.size(),cartCounter);
    }


}
