package tests;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Locale;

public class LoginTest {

    WebDriver driver;
    public static final String URL = "https://the-internet.herokuapp.com/login";
    Faker faker;


    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("firefox") String browser) throws InterruptedException {
        //int wait = Integer.parseInt(waitTime);
        if (browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if (browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        faker = new Faker(new Locale("us"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(3000);
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }


    //custom helper metode
    public WebElement getElement(By locator){
        return driver.findElement(locator);
    }

    public void typeIn(By locator, String text){
        WebElement element = getElement(locator);
        element.sendKeys(text);
    }

    public void clickOnElement(By locator){
        getElement(locator).click();
    }


    By passwordField = By.name("password");
    By loginButton = By.className("radius");

    @Test
    @Parameters({"username", "password"})
    public void loginTest(@Optional("tomsmith") String username, @Optional("SuperSecretPassword!") String password){
        WebElement usernameField = driver.findElement(By.cssSelector("input[name='username']"));
        usernameField.sendKeys(username);

//        getElement(By.name("password")).sendKeys(password);
//        getElement(By.className("radius")).click();

        typeIn(passwordField,password);
        clickOnElement(loginButton);

//        driver.findElement(By.name("password")).sendKeys(password);
//        driver.findElement(By.className("radius")).click();

        WebElement loggedInText = driver.findElement(By.id("flash"));

        String axtualBackgroundColor = loggedInText.getCssValue("background-color");

        String expectedText = "You logged into a secure area!";

        String expectedColor;
        if (driver instanceof ChromeDriver){
            expectedColor = "rgba(93, 164, 35, 1)";
        }else {
            expectedColor = "rgb(93, 164, 35)";
        }

        String actual[] = loggedInText.getText().split("(?<=!)"); //regex


        String actualtext = actual[0];
        String actualText2 = loggedInText.getText().substring(0, loggedInText.getText().length()-1);

        boolean result = expectedText.equals(actualtext);

        Assert.assertTrue(result);

        Assert.assertEquals(actualtext, expectedText);
        Assert.assertEquals(axtualBackgroundColor, expectedColor);
    }





}
