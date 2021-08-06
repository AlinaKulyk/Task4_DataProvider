package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DomHelpers;

public class LaptopsPage extends AbstractPage{

    @FindBy(xpath = "//div[@data-filter-name='producer']")
    private WebElement brandSearchBlock;

    @FindBy(xpath = "(//a[@class='goods-tile__heading ng-star-inserted'])[1]")
    private WebElement nameOfMostExpensiveGood;

    private By firstGoodLocator =
        By.xpath("(//a[@class='goods-tile__picture ng-star-inserted'])[1]");

    public LaptopsPage(WebDriver webDriver) {
        super(webDriver);
    }

    public LaptopsPage filterByBrand(String brand) {
        return filterBrandsByKeyword(brand).selectBrand(brand);
    }

    private LaptopsPage filterBrandsByKeyword (final String keyword) {
        WebDriverWait wait = new WebDriverWait(webDriver, DEFAULT_TIMEOUT);
        WebDriverWait waitForUpdate = new WebDriverWait(webDriver, UPDATE_TIMEOUT);
        WebElement brandSearchInputElement =
            brandSearchBlock.findElement(By.xpath("//input[@name='searchline']"));
        try {
            waitForUpdate.until(ExpectedConditions.stalenessOf(brandSearchInputElement));
        } catch (TimeoutException ignored) {}
        brandSearchInputElement =
            brandSearchBlock.findElement(By.xpath("//input[@name='searchline']"));
        wait.until(ExpectedConditions.elementToBeClickable(brandSearchInputElement));
        brandSearchInputElement.sendKeys(keyword);
        try {
            waitForUpdate.until(ExpectedConditions.stalenessOf(brandSearchBlock));
        } catch (TimeoutException ignored) {}
        return new LaptopsPage(webDriver);
    }

    private LaptopsPage selectBrand (final String brandName) {
        brandSearchBlock = webDriver.findElement(By.xpath("//div[@data-filter-name='producer']"));

        WebElement brandSearchLabelElement =
            DomHelpers.waitForElementToBeRefreshedAndClickable(
                webDriver,
                brandSearchBlock,
                By.xpath(
                    String.format("//label[contains(text(),'%s')]", brandName)
                )
            );
        brandSearchLabelElement.click();
        return new LaptopsPage(webDriver);
    }

    public LaptopsPage sortThings(String orderText) {
        WebDriverWait wait = new WebDriverWait(webDriver, UPDATE_TIMEOUT);
        WebElement firstGood = webDriver.findElement(firstGoodLocator);
        Select sortSelect =
            new Select(webDriver.findElement(By.xpath("//rz-sort/select")));
        sortSelect.selectByVisibleText(orderText);
        try {
            wait.until(ExpectedConditions.stalenessOf(firstGood));
        } catch (TimeoutException ignored) {}
        return new LaptopsPage(webDriver);
    }

    public AddToCartPage chooseMostExpensiveGood() {
        WebDriverWait wait = new WebDriverWait(webDriver, DEFAULT_TIMEOUT);
        WebElement mostExpensiveGood =
            DomHelpers.waitForElementToBeRefreshedAndClickable(webDriver, firstGoodLocator);
        mostExpensiveGood.click();
        return new AddToCartPage(webDriver);
    }

    public String getNameOfMostExpensiveGood() {
        waitVisibilityOfElement(DEFAULT_TIMEOUT,nameOfMostExpensiveGood);
        return nameOfMostExpensiveGood.getText();
    }
}

