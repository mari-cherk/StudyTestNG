package creationOfProduct;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.Random;


public class CreationOfProduct {
    EventFiringWebDriver driver = null;

    private static final String mCHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int STR_LENGTH = 9;
    Random random = new Random();
    String nameProductString;
    String numberProductString;
    String priceProductString;


    @BeforeTest
    @Parameters({"browser"})
    public void setBrowser(String browser) {
        driver = new EventFiringWebDriver(DriverManager.getDriver("browser"));
        MyWebDriverListener listener = new MyWebDriverListener();
        driver.register(listener);

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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("page-header-desc-configuration-add")));

        WebElement newProductButton = driver.findElement(By.id("page-header-desc-configuration-add"));
        newProductButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form_content")));

        WebElement nameField = driver.findElement(By.id("form_step1_name_1"));


        nameProductString = createRandomString();
        nameField.sendKeys(nameProductString);

        int numberProduct = random.nextInt(100) + 1;
        numberProductString = Integer.toString(numberProduct);
        WebElement numberField = driver.findElement(By.id("form_step1_qty_0_shortcut"));
        numberField.clear();
        numberField.sendKeys(numberProductString);

        int priceProduct10 = random.nextInt(1000) + 1;
        float priceProduct;
        priceProduct = (float) priceProduct10 * (float) 0.1;
        priceProductString = Float.toString(priceProduct);
        WebElement priceField = driver.findElement(By.id("form_step1_price_shortcut"));
        priceField.clear();
        priceField.sendKeys(priceProductString);

        WebElement switchInput = driver.findElement(By.xpath("//*[@id=\"form\"]/div[4]/div[1]/div"));
        switchInput.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));
        WebElement configMessage = driver.findElement(By.className("growl-close"));
        configMessage.click();

        WebElement saveProductButton = driver.findElement(By.xpath("//input[@class=\"btn btn-primary save uppercase\"]"));
        saveProductButton.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-close")));
        WebElement configMessage2 = driver.findElement(By.className("growl-close"));
        configMessage2.click();




    }

    @Test(dependsOnMethods = "addingProduct")
    public void checkingProduct() throws InterruptedException {

        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("products")));

        WebElement allProducts = driver.findElement(By.xpath("//*[@id=\"content\"]/section/a"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", allProducts);
        allProducts.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-md-6")));

        while(!elemetIsPresent(By.linkText(nameProductString))){
            if(elemetIsPresent(By.xpath("//*[@class=\"next js-search-link\"]"))) {
            driver.findElement(By.xpath("//*[@class=\"next js-search-link\"]")).click();
        Thread.sleep(3000);

           }
        }

        WebElement createdProduct = driver.findElement(By.linkText(nameProductString));
        createdProduct.click();

        String getNameOfproduct = driver.findElement(By.xpath("//*[@class=\"h1\"]")).getText();
        System.out.println(getNameOfproduct);
        Assert.assertEquals(getNameOfproduct, nameProductString, "Wrong name");

        String getPriceOfProduct = driver.findElement(By.xpath("//span[@itemprop=\"price\"]")).getAttribute("content");
        System.out.println(getPriceOfProduct);
        Assert.assertEquals(getPriceOfProduct, priceProductString, "Wrong price");

        String numberOfProduct = driver.findElement(By.xpath("//*[@id=\"product-details\"]/div[1]/span")).getText().replaceAll("\\D", "");
        System.out.println(numberOfProduct);
        Assert.assertEquals(numberProductString, numberOfProduct, "Wrong number of products");

    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }


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

    public boolean elemetIsPresent(By by){
        return driver.findElements(by).size() > 0;
    }


}
