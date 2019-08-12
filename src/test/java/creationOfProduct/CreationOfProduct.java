package creationOfProduct;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.Random;

import static java.lang.System.getProperty;

public class CreationOfProduct {
    WebDriver driver = null;


    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(String browser) {
        driver = DriverManager.getDriver("browser");
    }


    @Test(dataProvider = "getData")
    public void signIN(String email, String password) {

        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        WebElement loginInput = driver.findElement(By.id("email"));
        loginInput.sendKeys(email);

        WebElement passwordInput = driver.findElement(By.id("passwd"));
        passwordInput.sendKeys(password);

        WebElement submitButton = driver.findElement(By.name("submitLogin"));
        submitButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("subtab-AdminCatalog")));
        WebElement adminCatalog = driver.findElement(By.id("subtab-AdminCatalog"));
        WebElement adminProducts = driver.findElement(By.id("subtab-AdminProducts"));

        Actions goToProducts = new Actions(driver);
        goToProducts.moveToElement(adminCatalog).pause(Duration.ofSeconds(5)).click(adminProducts).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product_catalog_list")));

        WebElement newProductButton = driver.findElement(By.id("page-header-desc-configuration-add"));
        newProductButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_content")));

        WebElement nameField = driver.findElement(By.id("form_step1_name_1"));
        Random random = new Random();
        int codeProduct = random.nextInt(1000) + 1;
        String codeProductString = Integer.toString(codeProduct);
        nameField.sendKeys(codeProductString);

        int numberProduct = random.nextInt(100) + 1;
        String numberProductString = Integer.toString(numberProduct);
        WebElement numberField = driver.findElement(By.id("form_step1_qty_0_shortcut"));
        numberField.sendKeys(numberProductString);

        int priceProduct10 = random.nextInt(1000) + 1;
        float priceProduct = priceProduct10 / 10;
        String priceProductString = Float.toString(priceProduct);
        WebElement priceField = driver.findElement(By.id("form_step1_price_shortcut"));
        priceField.sendKeys(priceProductString);
        WebElement saveProductButton = driver.findElement(By.xpath("//*[@id=\"form\"]/div[4]/div[2]/div/button[1]"));
        saveProductButton.click();

        //WebElement footerProduct = driver.findElement(By.className())

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl growl-notice growl-medium")));
        WebElement configMessage = driver.findElement(By.className("growl growl-notice growl-medium"));
        configMessage.click();


    }

    @DataProvider
    public Object[][] getData() {
        return new Object[][]{{"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}};
    }


}
