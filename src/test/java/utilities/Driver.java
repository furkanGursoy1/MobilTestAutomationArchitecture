package utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Driver {
    static String platformName = System.getProperty("platform", ConfigurationReader.get("platform"));
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static String url = ConfigurationReader.get("APPIUM_URL");
    private static String userName = ConfigurationReader.get("browserStackUsername");
    private static String accessKey = ConfigurationReader.get("browserStackaccessKey");

    private Driver() {
    }

    public static AppiumDriver get() {
        if (driver.get() == null) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            switch (platformName) {
                case "android":
                    try {
                        driver.set(new AndroidDriver<MobileElement>(new URL(url), Config.getCapabilities(platformName)));
                        driver.get().manage().timeouts().implicitlyWait(Constants.DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ios":
                    try {
                        driver.set(new IOSDriver<MobileElement>(new URL(url), Config.getCapabilities(platformName)));
                        driver.get().manage().timeouts().implicitlyWait(Constants.WAIT_TIME_15, TimeUnit.SECONDS);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "android-bs":
                    try {
                        driver.set(new AndroidDriver<MobileElement>(new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"), Config.getCapabilities(platformName)));
                        driver.get().manage().timeouts().implicitlyWait(Constants.WAIT_TIME_15, TimeUnit.SECONDS);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ios-bs":
                    try {
                        driver.set(new IOSDriver<MobileElement>(new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"), Config.getCapabilities(platformName)));
                        driver.get().manage().timeouts().implicitlyWait(Constants.WAIT_TIME_15, TimeUnit.SECONDS);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return driver.get();
    }

    public static void closeDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}