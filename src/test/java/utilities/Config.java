package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.MutableCapabilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Config {
    private static final List<String> DEVICE_NAMES_IOS = new ArrayList<>();

    static {
        DEVICE_NAMES_IOS.add("iPhone 15");
        DEVICE_NAMES_IOS.add("iPhone 11");
        DEVICE_NAMES_IOS.add("iPhone 12");
        DEVICE_NAMES_IOS.add("iPhone 15 Pro Max");
        DEVICE_NAMES_IOS.add("iPhone 13");
    }

    private static final List<String> DEVICE_NAMES_ANDROID = new ArrayList<>();

    static {
        DEVICE_NAMES_ANDROID.add("Samsung Galaxy S23 Ultra");
        DEVICE_NAMES_ANDROID.add("Samsung Galaxy S23");
        DEVICE_NAMES_ANDROID.add("Xiaomi Redmi Note 11");
        DEVICE_NAMES_ANDROID.add("OnePlus 11R");
        DEVICE_NAMES_ANDROID.add("Google Pixel 7");
    }

    private static String currentDeviceName = "";

    public static MutableCapabilities getCapabilities(String platformName) {
        System.out.println("SEÇİLEN PLATFORM = " + platformName);

        String iosAppPath = System.getProperty("user.dir") + ConfigurationReader.get("APP_PATH_IOS");
        String androidAppPath = System.getProperty("user.dir") + ConfigurationReader.get("APP_PATH_ANDROID");

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("unicodeKeyboard", "true");
        capabilities.setCapability("resetKeyboard", "true");
        //capabilities.setCapability("mobile", "type");
        capabilities.setCapability("simulatorStartupTimeout", 90000);
        capabilities.setCapability("newCommandTimeout", 120);
        capabilities.setCapability("commandTimeout", 120);
        capabilities.setCapability(MobileCapabilityType.ACCEPT_INSECURE_CERTS, true);
        capabilities.setCapability(MobileCapabilityType.ACCEPT_SSL_CERTS, true);

        switch (platformName) {

            case "ios":
                System.out.println("[IOS Capabilites]");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, ConfigurationReader.get("PLATFORM_NAME_IOS"));
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, ConfigurationReader.get("PLATFORM_VERSION_IOS"));
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigurationReader.get("DEVICE_NAME_IOS"));
                capabilities.setCapability(MobileCapabilityType.UDID, ConfigurationReader.get("UDID_IOS"));
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ConfigurationReader.get("AUTOMATION_NAME_IOS"));
                capabilities.setCapability(MobileCapabilityType.APP, iosAppPath);
                capabilities.setCapability("sendKeyStrategy", "grouped");
                capabilities.setCapability(IOSMobileCapabilityType.INTER_KEY_DELAY, 10);
                //capabilities.setCapability("allowTouchIdEnroll", true);
                //capabilities.setCapability(IOSMobileCapabilityType.CONNECT_HARDWARE_KEYBOARD,false);
                break;
            case "ios-bs":
                Random random = new Random();
                String deviceNameIos = DEVICE_NAMES_IOS.get(random.nextInt(DEVICE_NAMES_IOS.size()));
                System.out.println("[BROWSERSTACK IOS Capabilites]");
                capabilities.setCapability("sendKeyStrategy", "grouped");
                capabilities.setCapability(IOSMobileCapabilityType.INTER_KEY_DELAY, 10);
                capabilities.setCapability("device", deviceNameIos);
                capabilities.setCapability("project", "Project Name");
                capabilities.setCapability("build", "iOS Tests");
                capabilities.setCapability("name", Constants.getBuildNumber.get());
                capabilities.setCapability("app", "app id");

                if ("iPhone 11".equals(deviceNameIos)) {
                    capabilities.setCapability("os_version", "15");
                } else if ("iPhone 12".equals(deviceNameIos)) {
                    capabilities.setCapability("os_version", "14");
                } else if ("iPhone 15".equals(deviceNameIos)) {
                    capabilities.setCapability("os_version", "17");
                } else if ("iPhone 15 Pro Max".equals(deviceNameIos)) {
                    capabilities.setCapability("os_version", "17");
                } else {
                    capabilities.setCapability("os_version", "17");
                }
                currentDeviceName = deviceNameIos;
                break;


            case "android":
                System.out.println("[ANDROID Capabilites]");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, ConfigurationReader.get("PLATFORM_NAME_ANDROID"));
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, ConfigurationReader.get("PLATFORM_VERSION_ANDROID"));
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, ConfigurationReader.get("DEVICE_NAME_ANDROID"));
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, ConfigurationReader.get("AUTOMATION_NAME_ANDROID"));
                capabilities.setCapability(MobileCapabilityType.APP, androidAppPath);
                capabilities.setCapability("autoAcceptAlerts", "true");
                capabilities.setCapability("autoGrantPermissions", "true");
                break;

            case "android-bs":
                Random randomAndroid = new Random();
                String deviceNameAndroid = DEVICE_NAMES_ANDROID.get(randomAndroid.nextInt(DEVICE_NAMES_ANDROID.size()));
                System.out.println("[BROWSERSTACK ANDROID Capabilites]");
                capabilities.setCapability("autoAcceptAlerts", "true");
                capabilities.setCapability("autoGrantPermissions", "true");
                capabilities.setCapability("device", deviceNameAndroid);
                capabilities.setCapability("os_version", "13.0");
                capabilities.setCapability("project", "Project Name");
                capabilities.setCapability("build", "Android Regression");
                capabilities.setCapability("name", Constants.getBuildNumber.get());
                capabilities.setCapability("app", "app id");
                capabilities.setCapability("browserstack.appium_version", ConfigurationReader.get("APPIUM_VERSION"));
                capabilities.setCapability("autoAcceptAlerts", "true");
                capabilities.setCapability("autoGrantPermissions", "true");
                capabilities.setCapability("sendKeys", "true");
                capabilities.setCapability("interactiveDebugging", "true");

                if ("Samsung Galaxy S23".equals(deviceNameAndroid)) {
                    capabilities.setCapability("os_version", "13.0");
                } else if ("Samsung Galaxy S23 Ultra".equals(deviceNameAndroid)) {
                    capabilities.setCapability("os_version", "13.0");
                } else if ("Xiaomi Redmi Note 11".equals(deviceNameAndroid)) {
                    capabilities.setCapability("os_version", "11.0");
                } else if ("OnePlus 11R".equals(deviceNameAndroid)) {
                    capabilities.setCapability("os_version", "13.0");
                } else {
                    capabilities.setCapability("os_version", "13.0");
                }
                currentDeviceName = deviceNameAndroid;
                break;

            default:
                System.out.println("Geçersiz Platform İsmi: " + platformName + " Platform İsmi Şunlar Olabilir: android, ios, ios-bs, android-bs ");
/*            case "realDeviceAndroidBP":
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("deviceName", "BP");
                capabilities.setCapability("udid", "83CVB19C26013738");
                capabilities.setCapability("automationName", "UiAutomator2");
                capabilities.setCapability(CapabilityType.VERSION, "10.0");
//                    cap.setCapability("adbExecTimeOut", "50000");
                capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
                capabilities.setCapability("appium:adbExecTimeOut", "50000");
                break;*/
        }
        String pipelineEnvironment = ConfigurationReader.get("KOSUM_ORTAM");
        if ("pipeline".equalsIgnoreCase(pipelineEnvironment)) {
            if ("ios-bs".equals(platformName)) {
                capabilities.setCapability("device", DEVICE_NAMES_IOS.get(0));
                capabilities.setCapability("os_version", "17");
                currentDeviceName = DEVICE_NAMES_IOS.get(0);
            } else if ("android-bs".equals(platformName)) {
                capabilities.setCapability("device", DEVICE_NAMES_ANDROID.get(0));
                capabilities.setCapability("os_version", "13.0");
                currentDeviceName = DEVICE_NAMES_ANDROID.get(0);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            String capsAsString = mapper.writeValueAsString(capabilities.toJson());
            System.setProperty("capabilities", capsAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return capabilities;
    }

    public static String getDeviceName() {
        return currentDeviceName;
    }

}