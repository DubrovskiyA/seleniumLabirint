package LabirintTest.PageFactorySelenoid.Page;

import LabirintTest.PageFactorySelenoid.Block.Header;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    private final WebDriver driver;
    private Header header;
    public MainPage(WebDriver driver){
        this.driver=driver;
        header= PageFactory.initElements(driver, Header.class);
    }
    public void open(){
        driver.manage().window().maximize();
        driver.get("https://labirint.ru");
    }
    public void setCookie(String name, String value){
        driver.manage().addCookie(new Cookie(name,value));
    }

    public Header getHeader() {
        return header;
    }

}
