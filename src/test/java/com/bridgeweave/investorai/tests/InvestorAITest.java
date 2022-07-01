package com.bridgeweave.investorai.tests;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InvestorAITest {

    private static final String APP = "https://github.com/Puttajunjaiah/investorai-cryptos/raw/main/src/app/InvestorAiCrypto_1.0.7.apk";
    private static final String APPIUM = "http://localhost:4723/wd/hub";

    private AndroidDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "com.investorai.crypto");
        caps.setCapability("appActivity", "com.investorai.applauncher.presentation.MainActivity");
        caps.setCapability("app", APP);
        driver = new AndroidDriver(new URL(APPIUM), caps);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
    	String titleText = "";
        WebDriverWait wait = new WebDriverWait(driver, 20);
        
        //wait for the app to load
        WebElement getStarted = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.investorai.crypto:id/btn_getStart")));
        getStarted.click();

        WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.investorai.crypto:id/btn_login")));
        
        //Enter username, password and tap Login
        driver.findElement(MobileBy.id("com.investorai.crypto:id/et_username")).sendKeys("xyz@bridgeweave.com");
        driver.findElement(MobileBy.id("com.investorai.crypto:id/et_password")).sendKeys("xxxxxxx");
        loginBtn.click();
        
        WebElement agreeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.investorai.crypto:id/btn_agree")));
        agreeBtn.click();
        
        //wait for the chart to load
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.investorai.crypto:id/chart1")));
        wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.investorai.crypto:id/rb_allocationGraphCurrent")));

        //perform swipe up action to reach 'Add Strategy' button on the screen
        PointerInput finger = new PointerInput(Kind.TOUCH, "finger");
        Interaction moveToStart = finger.createPointerMove(Duration.ZERO, Origin.viewport(), 520, 1530);
        Interaction pressDown = finger.createPointerDown(MouseButton.LEFT.asArg());
        Interaction moveToEnd = finger.createPointerMove(Duration.ofMillis(1000), Origin.viewport(), 520, 200);
        Interaction pressUp = finger.createPointerUp(MouseButton.LEFT.asArg());

        Sequence swipe = new Sequence(finger, 0);
        swipe.addAction(moveToStart);
        swipe.addAction(pressDown);
        swipe.addAction(moveToEnd);
        swipe.addAction(pressUp);

        driver.perform(Arrays.asList(swipe));

        //click 'Add Strategy' button
        driver.findElement(MobileBy.id("com.investorai.crypto:id/btn_addStrategy")).click();

        WebElement alphaHunter = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.xpath("//android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/androidx.cardview.widget.CardView[2]/android.view.ViewGroup/android.widget.Button")));
        alphaHunter.click();
        WebElement apiKey = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.investorai.crypto:id/txt_account_name")));
        apiKey.click();
        WebElement invAmount = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.id("com.investorai.crypto:id/edit_investmentamount")));
        invAmount.sendKeys("500");
        driver.findElement(MobileBy.id("com.investorai.crypto:id/btn_convert_moreUSD")).click();
        driver.findElement(MobileBy.id("com.investorai.crypto:id/title"));
      
    }
}
