package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class AddToCartPage extends AbstractPage {

    @FindBy(xpath = "//button[@class='buy-button button button_with_icon button_color_green button_size_large ng-star-inserted']")
    private WebElement buttonBuy;

    public AddToCartPage(WebDriver webDriver) {
        super(webDriver);

    }

    public SumAndItemCheckingPage pressButtonBuy() {

        Actions actions = new Actions(webDriver);
        actions.moveToElement(buttonBuy).perform();
        waitVisibilityOfElement(DEFAULT_TIMEOUT,buttonBuy);
        buttonBuy.click();
        waitForPageLoadComplete(DEFAULT_TIMEOUT);

        return new SumAndItemCheckingPage(webDriver);
    }
}
