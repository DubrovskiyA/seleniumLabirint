package LabirintTest.PageFactoryDockerTestcontainers;

import LabirintTest.PageFactoryDockerTestcontainers.Block.BookCard;
import LabirintTest.PageFactoryDockerTestcontainers.Page.MainPage;
import LabirintTest.PageFactoryDockerTestcontainers.Page.SearchResultPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.VncRecordingContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.containers.BrowserWebDriverContainer.*;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.*;
import static org.testcontainers.containers.VncRecordingContainer.*;
import static org.testcontainers.containers.VncRecordingContainer.VncRecordingFormat.*;

@Testcontainers
public class LabirintPFDockerManTestcontainers {
    private WebDriver driver;
    @Container
    public BrowserWebDriverContainer<?> container =
            new BrowserWebDriverContainer<>("selenium/standalone-chrome:latest")
                    .withExposedPorts(7900);
//                    .withRecordingMode(RECORD_ALL, Path.of("vids").toFile(), MP4);
    @BeforeEach
    public void setUp() {
        driver=new RemoteWebDriver(container.getSeleniumAddress(),new ChromeOptions());
    }
//    @AfterEach здесь не нужен

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
