package LabirintTest.PageFactorySelenoid;

import LabirintTest.PageFactorySelenoid.Block.BookCard;
import LabirintTest.PageFactorySelenoid.Page.MainPage;
import LabirintTest.PageFactorySelenoid.Page.SearchResultPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LabirintPFTestSelenoid {
    private WebDriver driver;
    @BeforeEach
    public void setUp() throws MalformedURLException {
        String hubURL=System.getProperty("hub","http://localhost:4444/wd/hub");
        System.out.println("hubURL = " + hubURL);
//        сгенерировано selenoid ui
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "120.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            /* How to add test badge */
            put("name", "Test badge...");

            /* How to set session timeout */
            put("sessionTimeout", "15m");

            /* How to set timezone */
            put("env", new ArrayList<String>() {{
                add("TZ=UTC");
            }});

            /* How to add "trash" button */
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});

            put("enableVNC", true);

            /* How to enable video recording */
            put("enableVideo", false);
        }});
        driver = new RemoteWebDriver(new URL(hubURL+"/wd/hub"), options);
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
