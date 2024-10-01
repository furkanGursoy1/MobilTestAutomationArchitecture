package step_definitons;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.Config;
import utilities.Constants;
import utilities.Driver;
import utilities.Util;


public class Hooks {

    @Before
    public void setUp() {
        System.out.println(Driver.get().getPlatformName());
        if (Util.isIOS()) {
            //          getEnv(System.getProperty("env", ConfigurationReader.get("env")));
        } else if (Util.isAndroid()) {
            //         getEnv(System.getProperty("env", ConfigurationReader.get("env")));
        }

    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }

//        ---LOGS---
        try {
            scenario.log(Constants.getBuildNumber.get());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        Driver.closeDriver();
        String deviceName = Config.getDeviceName();
        System.out.println("Running test on device: " + deviceName);

    }

}