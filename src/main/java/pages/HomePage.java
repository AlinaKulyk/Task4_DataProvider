package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage{

    @FindBy(name = "search")
    private WebElement searchField;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    private HomePage inputSearchKeyword (final String keyword) {
        searchField.sendKeys(keyword);
        waitForPageLoadComplete(DEFAULT_TIMEOUT);
        return new HomePage(webDriver);
    }


    private LaptopsPage selectSearchSuggestItem (final String keyword) {
        WebElement searchSuggestItemElement = null;
        try { searchSuggestItemElement = webDriver.findElement(
            By.xpath(
                String.format("//div[contains(@class, 'search-suggest')]//p[contains(@class, 'search-suggest__heading') and text()[normalize-space(.)='Перейти в категорию']]/following-sibling::ul//a[contains(@class, 'search-suggest__item-text') and text()[normalize-space(.)='%s']]", keyword)
            )
        );}
        catch (NoSuchElementException exception) {
            webDriver.findElement(By.name("search")).sendKeys(Keys.ENTER);
            return new LaptopsPage(webDriver);
        }
        searchSuggestItemElement.click();
        return new LaptopsPage(webDriver);
    }

    public LaptopsPage searchByKeyword (final String keyword) {
        return inputSearchKeyword(keyword).selectSearchSuggestItem(keyword);
    }
}
