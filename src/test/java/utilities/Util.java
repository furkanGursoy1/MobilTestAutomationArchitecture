package utilities;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

public class Util {
    public static WebDriverWait wait;

    public static String generateEmail() {
        String eMail = "";
        for (int i = 0; i < 30; i++) {
            eMail += (char) ((Math.random() * 25) + 97);
        }
        eMail += "@gmail.com";
        return eMail;
    }

    public static ThreadLocal<String> generateBuildNumber() {
        ThreadLocal<String> buildNo = new ThreadLocal<>();
        String text = "";
        for (int i = 0; i < 15; i++) {
            text += (char) ((Math.random() * 25) + 97);
        }
        buildNo.set("Build Name: "+text);
        System.out.println("buildNo.get() = " + buildNo.get());
        if (buildNo.get() == null || buildNo.get().isEmpty()) {
            generateBuildNumber();
        }
        return buildNo;
    }

    public static MobileElement waitForElementToBeClickable(MobileElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), timeToWaitInSec);
        return (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static int getNumbersInText(WebElement element) {
        String text = element.getText().trim();
        System.out.println("text = " + text);
        String numeric = "";
        for (int i = 0; i < text.length(); i++) {
            if (StringUtils.isNumeric(text.charAt(i) + "")) {
                numeric += text.charAt(i);
            }
        }
        System.out.println("numeric = " + numeric);
        return Integer.parseInt(numeric);
    }

    public static String generateRandomText() {
        String randomTxt = "";
        for (int i = 0; i < 4; i++) {
            randomTxt += (char) ((Math.random() * 25) + 97);
        }
        System.out.println("randomTxt = " + randomTxt);
        return randomTxt;
    }

    public static Integer generateRandomNumber(Integer limit) {
        Random random = new Random();
        return random.nextInt(limit);
    }

    public static void scrollTo(String text) {
        Driver.get().findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"" + text + "\").instance(0))"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void sendKeysToMobileElementWithControlA(MobileElement mobileElement, String text) {
        mobileElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), text);
    }


    public static void scrollDown(int times) {
        Dimension size = Driver.get().manage().window().getSize();
        int startX = size.width / 2;
        int endX = startX;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        for (int i = 0; i < times; i++) {
            TouchAction t = new TouchAction((PerformsTouchActions) Driver.get());
            t.press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(endX, endY))
                    .release()
                    .perform();
        }
        sleep(500);
    }

    public static void scrollDownHalf(int times) {
        Dimension size = Driver.get().manage().window().getSize();
        int startX = size.width / 2;
        int endX = startX;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.5);

        for (int i = 0; i < times; i++) {
            TouchAction t = new TouchAction((PerformsTouchActions) Driver.get());
            t.press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(endX, endY))
                    .release()
                    .perform();
        }
    }

    public static void scrollToElementByRatio(int times,double ratio, By locator) {
        Dimension size = Driver.get().manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * ratio);

        for (int i = 0; i < times; i++) {
            TouchAction t = new TouchAction(Driver.get());
            t.press(PointOption.point(startX, startY))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(startX, endY))
                    .release()
                    .perform();

            setImplicitWait(0);
            try {
                if (Driver.get().findElement(locator).isDisplayed()) {
                    break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        resetImplicitWait();
    }

    public static void swipeByElements(AndroidElement startElement, AndroidElement endElement) {
        int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
        int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);

        int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
        int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);

        new TouchAction(Driver.get())
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(endX, endY))
                .release().perform();
    }

    public static void scrollToElement(MobileElement element, int scrollTimes) {
        for (int i = 0; i < scrollTimes; i++) {
            try {
                setImplicitWait(Constants.WAIT_TIME_3);
                if (element.isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                Util.scrollDownHalf(1);
            }
        }
        setImplicitWait(Constants.WAIT_TIME_15);
    }

    public static void scrollToElementAndClick(MobileElement element, int scrollTimes) {
        for (int i = 0; i < scrollTimes; i++) {
            try {
                setImplicitWait(Constants.WAIT_TIME_3);
                if (element.isDisplayed()) {
                    element.click();
                    break;
                }
            } catch (Exception e) {
                Util.scrollDownHalf(1);
            }
        }
        setImplicitWait(Constants.WAIT_TIME_15);
    }

    public static void up() {
        PointOption pointOptionStart, pointOptionEnd;
        Dimension dims = Driver.get().manage().window().getSize();

        pointOptionStart = PointOption.point((int) (dims.width * 0.9), dims.height / 2);
        pointOptionEnd = PointOption.point((int) (dims.width * 0.2), dims.height / 2);

        new TouchAction(Driver.get())
                .press(pointOptionStart)
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(200)))
                .moveTo(pointOptionEnd)
                .release().perform();
    }


    public static void scrollToTextAndClick(String text, int scrollTimes) {
        setImplicitWait(3);
        WebElement element;
        for (int i = 0; i < scrollTimes; i++) {
            try {
                if (System.getProperty("platform", ConfigurationReader.get("platform")).contains("ios")) {
                    element = Driver.get().findElement(By.xpath("//*[@label='" + text + "']"));
                } else {
                    element = Driver.get().findElement(By.xpath("//*[@text='" + text + "']"));
                }

//                Util.waitForVisibility((MobileElement) element,3);
                setImplicitWait(Constants.WAIT_TIME_3);
                if (element.isDisplayed()) {
                    element.click();
                    break;
                }
            } catch (Exception e) {
                Util.scrollDownHalf(1);
            }
        }
        setImplicitWait(Constants.WAIT_TIME_15);
    }

    public static List<String> getTextOfList(List<MobileElement> list) {
        List<String> newList = new ArrayList<>();

        for (MobileElement each : list) {
            System.out.println("Product: " + each.getText());
            newList.add(each.getText());
        }
        return newList;
    }

    public static List<String> getTextOfListWeb(List<WebElement> list) {
        List<String> newList = new ArrayList<>();

        for (WebElement each : list) {
            System.out.println("Product: " + each.getText());
            newList.add(each.getText());
        }
        return newList;
    }

    public static void sleep(int miliSeconds) {
        try {
            Thread.sleep(miliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void moveElementToElement(MobileElement el1, MobileElement el2) {

        TouchAction t = new TouchAction((PerformsTouchActions) Driver.get());
        t.press(ElementOption.element(el1))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(ElementOption.element(el2))
                .release()
                .perform();
    }

    public static boolean isElementExist(By by) {
        boolean result = !(Driver.get().findElements(by).isEmpty());
        return result;
    }

    public static boolean isElementExist(By by, int time) {
        setImplicitWait(time);
        boolean result = !(Driver.get().findElements(by).isEmpty());
        setImplicitWait(Constants.WAIT_TIME_15);
        return result;
    }

    public static void waitForVisibility(MobileElement element) {
        wait = new WebDriverWait(Driver.get(), Constants.WAIT_TIME_20);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisibility(MobileElement element, long second) {
        wait = new WebDriverWait(Driver.get(), second);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void refresh() {
    }

    public static void checkBannersOfList(List<MobileElement> elements, MobileElement element, By by) {
        try {
            String urlString = "";

            for (int i = 0; i < elements.size(); i++) {
                try {
                    urlString = elements.get(i).findElement(by).getAttribute("href");
                    System.out.println(urlString);
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    System.out.println(connection.getResponseMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("urlString = " + urlString);
                    System.out.println("e.getMessage() = " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkBannerOfElement(By by) {
        String urlString = "";

        try {
            urlString = Driver.get().findElement(by).getAttribute("href");
            System.out.println(urlString);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            System.out.println(connection.getResponseMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("urlString = " + urlString);
            System.out.println("e.getMessage() = " + e.getMessage());
        }
    }

    public static String isClickable(MobileElement element) {
        String isClickable = element.getAttribute("clickable");
        System.out.println("isClickable = " + isClickable);
        return isClickable;
    }

    public static String isEnabled(MobileElement element) {
        String isEnabled = element.getAttribute("enabled");
        System.out.println("isEnabled = " + isEnabled);
        return isEnabled;
    }

    public static String isVisible(MobileElement element) {
        String isVisible = element.getAttribute("visible");
        System.out.println("isVisible = " + isVisible);
        return isVisible;
    }

    public static String isDisplayed(MobileElement element) {
        String isDisplayed = element.getAttribute("displayed");
        System.out.println("isDisplayed = " + isDisplayed);
        return isDisplayed;
    }

    public static String isCheckable(MobileElement element) {
        String isCheckable = element.getAttribute("checkable");
        System.out.println("isCheckable = " + isCheckable);
        return isCheckable;
    }

    public static String isChecked(MobileElement element) {
        String isChecked = element.getAttribute("checked");
        System.out.println("isChecked = " + isChecked);
        return isChecked;
    }

    public static void swipeLeft(int swipeTimes) {
        Driver.get().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        for (int i = 0; i < swipeTimes; i++) {
            Dimension size = Driver.get().manage().window().getSize();
            int anchor = (int) (size.height * 0.1);
            int startPoint = (int) (size.width * 0.8);
            int endPoint = (int) (size.width * 0.2);

            new TouchAction(Driver.get())
                    .press(point(startPoint, anchor))
                    .waitAction(waitOptions(ofMillis(500)))
                    .moveTo(point(endPoint, anchor))
                    .release().perform();
        }
    }

    public static void setImplicitWait(int waitTime) {
        Driver.get().manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
    }

    public static void waitForStaleness(MobileElement element, int waitTime) {
        WebDriverWait wait = new WebDriverWait(Driver.get(), waitTime);
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(element)));
    }

    public static void waitForStaleElement(MobileElement element, int loopCount) {
        for (int i = 0; i < loopCount; i++) {
            try {
                System.out.println("element.getText() = " + element.getText());
                break;
            } catch (Exception e) {
                System.out.println("Stale Element Hatasi");
            }
        }
    }

    public static boolean isAndroid() {
        return Util.getOS().contains("android");
    }

    public static boolean isIOS() {
        return Util.getOS().contains("ios");
    }

    public static String getOS() {
        return System.getProperty("platform", ConfigurationReader.get("platform"));
    }

    public static void tapByCoordinates(int x, int y) {
        new TouchAction(Driver.get())
                .tap(point(x, y))
                .waitAction(waitOptions(Duration.ofMillis(250))).perform();
    }

    public static void pressByCoordinates(int x, int y) {
        new TouchAction(Driver.get())
                .press(point(x, y))
                .waitAction(waitOptions(ofMillis(250)))
                .release()
                .perform();
    }

    public static void swipeScreen(Direction dir) {
        sleep(2000);
        System.out.println("swipeScreen(): dir: '" + dir + "'"); // always log your actions
        // Animation default time:
        //  - Android: 300 ms
        //  - iOS: 200 ms
        // final value depends on your app and could be greater
        final int ANIMATION_TIME = 200; // ms
        final int PRESS_TIME = 200; // ms
        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;
        // init screen variables
        Dimension dims = Driver.get().manage().window().getSize();
        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        switch (dir) {
            case DOWN: // center of footer
                pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
                break;
            case UP: // center of header
                pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
                break;
            case LEFT: // center of left side
                pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
                break;
            case RIGHT: // center of right side
                pointOptionEnd = PointOption.point(dims.width - edgeBorder, dims.height / 2);
                break;
            default:
                throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
        }
        // execute swipe using TouchAction
        try {
            new TouchAction(Driver.get())
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }
        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
        Util.sleep(1000);
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT;
    }

    public static void tapByElement(MobileElement element) {
        new TouchAction(Driver.get())
                .tap(tapOptions().withElement(element(element)))
                .waitAction(waitOptions(Duration.ofMillis(250))).perform();
    }

    public static void verifyElementIsNotExist(By locator) {
        setImplicitWait(3);
        System.out.println("Driver.get().findElements(locator).size() = " + Driver.get().findElements(locator).size());
        Assert.assertFalse(Driver.get().findElements(locator).size() > 0);
        resetImplicitWait();
    }

    public static void resetImplicitWait() {
        Driver.get().manage().timeouts().implicitlyWait(Constants.DEFAULT_WAIT_TIME, TimeUnit.SECONDS);

    }

    public static int convertWebelementToInt(WebElement element) {
        int i = Integer.parseInt(element.getText().trim());
        System.out.println("i = " + i);
        return i;
    }


}
