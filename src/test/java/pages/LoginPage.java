package pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.Driver;
import utilities.Util;

public class LoginPage extends BasePage {
    @AndroidFindBy(xpath = "//*[contains(@resource-id,':id/login')]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Giriş Yap / Üye Ol']")
    public MobileElement uyeOlYadaGirisYapButton;

    @iOSXCUITFindBy(xpath = "")
    @AndroidFindBy(xpath = "//*[contains(@resource-id,':id/allowNotify')]")
    public MobileElement iosAllowNotificationBtn;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,':id/email')]")
    @iOSXCUITFindBy(accessibility = "Login_User_MailTF")
    public MobileElement emailInputBox;

    @iOSXCUITFindBy(iOSNsPredicate = "name == \"LoginVC_PasswordTF\"")
    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@content-desc,':id/password')]")
    public MobileElement passwordInputBox;


    public void clickText(String text) {
        WebElement element;
        if (Util.isIOS()) {
            element = Driver.get().findElement(By.xpath("//*[@label='" + text + "']"));
        } else {
            element = Driver.get().findElement(By.xpath("//*[@text='" + text + "']"));
            Util.scrollTo(text);
        }
        element.click();
    }

}
