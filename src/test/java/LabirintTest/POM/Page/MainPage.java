package LabirintTest.POM.Page;

import LabirintTest.POM.Block.Header;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class MainPage {
    private final WebDriver driver;
    private Header header;
    public MainPage(WebDriver driver){
        this.driver=driver;
        header=new Header(driver);
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
