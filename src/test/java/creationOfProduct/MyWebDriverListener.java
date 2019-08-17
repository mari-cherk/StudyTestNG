package creationOfProduct;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.testng.Reporter;

public class MyWebDriverListener extends AbstractWebDriverEventListener {

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        Reporter.log("Before opening url:"+url+" ... ",true);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        Reporter.log(url+" opened in the browser",true);
        Reporter.log("Page Title:" + driver.getTitle(),true);
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        Reporter.log("Typing text to "+element,true);
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        Reporter.log("value set to the"+element+" field",true);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        Reporter.log("Clicking on the element - "+element,true);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        Reporter.log(" element was clicked",true);
    }
}
