
import static org.junit.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.jboss.aerogear.security.otp.Totp;

public class LoginTest {
    private String userid = TestConstants.TESTUSER;
    private String password = TestConstants.TESTPASSWORD;
    private String url = TestConstants.LOGIN;

    String otpKeyStr;
    String twoFactorCode;

    @Test(timeout = 60000)
    public void test() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        Thread.sleep(TestConstants.SLEEP_INTERVAL);

        driver.findElement(By.name("email")).sendKeys(userid);

        WebElement element = driver.findElement(By.name("password"));
        element.sendKeys(password);
        element.sendKeys(Keys.RETURN);
        Thread.sleep(TestConstants.SLEEP_INTERVAL);

        try {
            WebElement otpelement = driver.findElement(By.name("optCode"));
            otpelement.sendKeys(enteringAuthy());
        } catch (NoSuchElementException e) {
            fail("Login failed !");
        } finally {
            Thread.sleep(TestConstants.SLEEP_INTERVAL);
            //driver.close();
        }

    }

    public String enteringAuthy() {
        otpKeyStr = "l2lxvg6fdryabyp2"; // <- this 2FA secret key.
        Totp totp = new Totp(otpKeyStr);
        twoFactorCode = totp.now();
        return twoFactorCode;
    }

}
