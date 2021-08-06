package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.WebDriverSingletone;

public class SumAndItemCheckingPage extends AbstractPage{

    @FindBy(xpath = "//div[@class='cart-receipt__sum-price']")
    private WebElement price;

    @FindBy(xpath = "//a[@class='cart-product__title']")
    private WebElement nameOfGood;

    public SumAndItemCheckingPage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getOrderPriceTotal() {
        waitVisibilityOfElement(DEFAULT_TIMEOUT,price);
        return price.getText();
    }

  public String getNameOfGood() {
      waitVisibilityOfElement(DEFAULT_TIMEOUT,nameOfGood);
      return nameOfGood.getText();
  }

}

