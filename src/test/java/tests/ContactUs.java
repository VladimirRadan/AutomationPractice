package tests;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Locale;

public class ContactUs {


    WebDriver driver;
    public static final String URL = "http://automationpractice.com/index.php";
    Faker faker;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        faker = new Faker(new Locale("us"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.get(URL);
    }


    @Test
    public void contactUs() {

        String projectRoot = System.getProperty("user.dir");

        driver.findElement(By.cssSelector("a[title='Contact Us']")).click();
        WebElement subjectHeading = driver.findElement(By.id("id_contact"));
        subjectHeading.click();
        Select selectYears = new Select(subjectHeading);
        selectYears.selectByValue("2");

        driver.findElement(By.id("email")).sendKeys(faker.internet().emailAddress());
        driver.findElement(By.id("id_order")).sendKeys("" + faker.number().numberBetween(5,10));
        driver.findElement(By.id("message")).sendKeys("Message :)");

        driver.findElement(By.id("fileUpload")).sendKeys(projectRoot + "/src/main/resources/invoice.txt");
        //driver.findElement(By.id("submitMessage")).click();

    }



}
