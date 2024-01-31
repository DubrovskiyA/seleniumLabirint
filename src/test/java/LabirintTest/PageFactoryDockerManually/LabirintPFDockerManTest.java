package LabirintTest.PageFactoryDockerManually;

import LabirintTest.PageFactoryDockerManually.Block.BookCard;
import LabirintTest.PageFactoryDockerManually.Page.MainPage;
import LabirintTest.PageFactoryDockerManually.Page.SearchResultPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabirintPFDockerManTest {
    private WebDriver driver;
    @BeforeEach
    public void setUp() throws MalformedURLException {
        driver=new RemoteWebDriver(new URL("http://localhost:4444"), new ChromeOptions());
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
        SearchResultPage searchResultPage=PageFactory.initElements(driver, SearchResultPage.class);
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
