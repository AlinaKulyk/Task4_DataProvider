package labTest;

import model.RozetkaFilter;
import model.RozetkaFilters;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import util.StringToNumberConverter;
import util.WebDriverSingletone;
import util.XMLtoObject;

import java.util.List;

public class SumVerifyTest {

        private WebDriverSingletone webDriverSingletone;

        @BeforeClass
        public void appSetup () {
            webDriverSingletone = WebDriverSingletone.getInstance();
        }

        @BeforeMethod
        public void testsSetUp() {
            WebDriver webDriver = webDriverSingletone.getDriver();
            webDriver.get("https://rozetka.com.ua/");
        }

        @DataProvider(parallel = true)
        public Object[][] products() {
            XMLtoObject xmLtoObject = new XMLtoObject();
            RozetkaFilters rozetkaFilters = xmLtoObject.convert();
            List<RozetkaFilter> rozetkaFilterList = rozetkaFilters.getRozetkaFilters();
            int rowAmount = rozetkaFilterList.size();
            int columnAmount = 1;
            Object[][] rozetkaFilterArray = new Object[rowAmount][columnAmount];
            for (int i = 0; i < rozetkaFilterList.size(); i++) {
                rozetkaFilterArray[i][0] = rozetkaFilterList.get(i);
            }
            return rozetkaFilterArray;
        }


        @Test(dataProvider = "products")
        public void givenFilter_whenTheMostExpensiveProductAddedToCart_thenTotalPriceLessThanBound (RozetkaFilter rozetkaFilter){
            WebDriver webDriver = webDriverSingletone.getDriver();
            String orderPriceTotal = new HomePage(webDriver)
                .searchByKeyword(rozetkaFilter.getProductGroup())
                .filterByBrand(rozetkaFilter.getBrand())
                .sortThings("От дорогих к дешевым")
                .chooseMostExpensiveGood()
                .pressButtonBuy()
                .getOrderPriceTotal();
            int actualOrderPriceTotal = StringToNumberConverter.parsePrice(orderPriceTotal, "₴");
            int expectedOrderPriceTotalMaxBound = rozetkaFilter.getSum();
            Assert.assertTrue(actualOrderPriceTotal < expectedOrderPriceTotalMaxBound);
            
        }

        @AfterMethod
        public void tearDown() {
            webDriverSingletone.closeDriver();
        }
    }

