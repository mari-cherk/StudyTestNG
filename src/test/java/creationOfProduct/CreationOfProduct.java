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
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;
import java.util.Random;

import static java.lang.System.getProperty;

public class CreationOfProduct {
    WebDriver driver = null;

    private static final String mCHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int STR_LENGTH = 9;
    Random random = new Random();


    @BeforeTest
    @Parameters({"browser"})
    public void setBrowser(String browser) {
        driver = DriverManager.getDriver("browser");
    }


    @Test(dataProvider = "getData")
    public void addingProduct(String email, String password) {

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
        WebElement adminProducts = driver.findElement(By.xpath("//*[@id=\"subtab-AdminProducts\"]"));

        Actions goToProducts = new Actions(driver);
        goToProducts.moveToElement(adminCatalog).pause(Duration.ofSeconds(5)).click(adminProducts).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product_catalog_list")));

        WebElement newProductButton = driver.findElement(By.id("page-header-desc-configuration-add"));
        newProductButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_content")));

        WebElement nameField = driver.findElement(By.id("form_step1_name_1"));
        //Random random = new Random();



        //int nameProduct = random.nextInt(1000) + 1;
        //String nameProductString = Integer.toString(nameProduct);
        String nameProductString = createRandomString();
        nameField.sendKeys(nameProductString);

        int numberProduct = random.nextInt(100) + 1;
        String numberProductString = Integer.toString(numberProduct);
        WebElement numberField = driver.findElement(By.id("form_step1_qty_0_shortcut"));
        numberField.clear();
        numberField.sendKeys(numberProductString);

        int priceProduct10 = random.nextInt(1000) + 1;
        float priceProduct = priceProduct10 / 10;
        String priceProductString = Float.toString(priceProduct);
        WebElement priceField = driver.findElement(By.id("form_step1_price_shortcut"));
        priceField.clear();
        priceField.sendKeys(priceProductString);

        WebElement swichInput = driver.findElement(By.xpath("//*[@id=\"form\"]/div[4]/div[1]/div"));
        swichInput.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));
        WebElement configMessage = driver.findElement(By.className("growl-close"));
        configMessage.click();

        WebElement saveProductButton = driver.findElement(By.xpath("//*[@id=\"form\"]/div[4]/div[2]/div"));
        saveProductButton.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));
        configMessage = driver.findElement(By.className("growl-close"));
        configMessage.click();




    }

    //@Test(dataProvider = "getData")
    //public void checkingProduct(String email, String password) {

        //driver.get("http://prestashop-automation.qatestlab.com.ua/");

        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        //WebElement loginInput = driver.findElement(By.id("email"));
        //loginInput.sendKeys(email);

        //WebElement passwordInput = driver.findElement(By.id("passwd"));
        //passwordInput.sendKeys(password);

        //WebElement submitButton = driver.findElement(By.name("submitLogin"));
        //submitButton.click();


    //}

    //@AfterTest
    //public void closeBrowser() {
        //driver.quit();
    //}


    @DataProvider
    public Object[][] getData() {
        return new Object[][]{{"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}};
    }

    public String createRandomString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < STR_LENGTH; i++) {
            int number = random.nextInt(mCHAR.length());
            char ch = mCHAR.charAt(number);
            builder.append(ch);
        }
        return builder.toString();
    }


}
