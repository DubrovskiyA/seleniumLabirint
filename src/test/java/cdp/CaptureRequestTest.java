package cdp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v119.log.Log;
import org.openqa.selenium.devtools.v119.network.Network;
import org.openqa.selenium.devtools.v119.network.model.Request;
import org.openqa.selenium.devtools.v119.network.model.RequestId;
import org.openqa.selenium.devtools.v119.network.model.Response;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CaptureRequestTest {
    private WebDriver driver;
    private Map<String, Request> requests;
    private Map<String, Response> responses;
    private Map<String, String> responsesBodies;
    @BeforeEach
    public void SetUp(){
        requests=new HashMap<>();
        responses=new HashMap<>();
        responsesBodies=new HashMap<>();
        driver=new ChromeDriver();
        DevTools devTools=((HasDevTools)driver).getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(), requestWillBeSent -> {
            String requestId=requestWillBeSent.getRequestId().toString();
            requests.put(requestId,requestWillBeSent.getRequest());
        });
        devTools.addListener(Network.responseReceived(),responseReceived -> {
            String requestId=responseReceived.getRequestId().toString();
            responses.put(requestId,responseReceived.getResponse());

            String body= devTools.send(Network.getResponseBody(new RequestId(requestId))).getBody();
            responsesBodies.put(requestId,body);
        });
    }
    @AfterEach
    public void TearDown(){
        if (driver!=null){
            driver.quit();
        }
        for (String requestId : requests.keySet()) {
            Request request=requests.get(requestId);
            if (request.getUrl().startsWith("https://todo-app")){
                Response response = responses.get(requestId);
                String body = responsesBodies.get(requestId);

                System.out.println(request.getMethod()+"/"+request.getUrl()+" "+request.getPostData().orElse("NO DATA"));
                System.out.println(response.getStatus()+" "+response.getStatusText());
                System.out.println(body);
            }
        }
    }
    @Test
    public void test(){
        String url="https://sky-todo-list.herokuapp.com";
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        List<WebElement> elements = driver.findElements(By.cssSelector("table tr"));
        System.out.println("elements = " + elements.size());
    }
}
