package pages;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Driver;

public class BasePage {
    public WebDriverWait wait;

    public BasePage() {
        PageFactory.initElements(new AppiumFieldDecorator(Driver.get()), this);
        wait = new WebDriverWait(Driver.get(), 10);
    }
}
