package step_definitons;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;
import utilities.ConfigurationReader;
import utilities.Util;

public class LoginStepDefinitions {
    LoginPage loginPage = new LoginPage();

    @Given("kullanici login sayfasina gider")
    public void kullanici_login_sayfasina_gider() {
        if (Util.isIOS()) {
            loginPage.iosAllowNotificationBtn.click();
            loginPage.clickText("Allow");
            loginPage.clickText("Allow");
        }
        loginPage.uyeOlYadaGirisYapButton.click();
    }


    @When("kullanici dogru bilgileri girer")
    public void kullanici_dogru_bilgileri_girer() {
        if (Util.isIOS()) {
            loginPage.emailInputBox.sendKeys(ConfigurationReader.get("emailIos"));
        } else {
            loginPage.emailInputBox.sendKeys(ConfigurationReader.get("email"));
        }
        Util.refresh();
        loginPage.passwordInputBox.sendKeys(ConfigurationReader.get("password"));

    }


    @Then("kullanici anasayfaya yonlendirilir")
    public void kullanici_anasayfaya_yonlendirilir() {

    }

}

