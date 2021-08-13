package pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailsPage {
	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"twotabsearchtextbox\"]")
	private WebElement searchBox;

	@FindBy(xpath = "//*[@id=\"nav-search-submit-button\"]")
	private WebElement searchButton;
	
	private String companyNameFieldXpath = "//span[@class=\"a-size-medium a-color-base a-text-normal\"]";
	private String priceFieldXpath = "//span[@class=\"a-price-whole\"]";
	private String imageFieldXpath = "//img[@data-image-latency=\"s-product-image\"]";
//	private String originalPriceXpath = "//span[@class=\"a-price a-text-price\")]//span[@class=\"a-offscreen\"]";
	
	public ProductDetailsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public ProductDetailsPage search(String searchTerm) throws InterruptedException {
		searchBox.sendKeys(searchTerm);
		searchButton.click();
		Thread.sleep(4000);

		return this;
	}
	
	public List<WebElement> grabNSearchResults(int numSearchResults) {
		List<WebElement> searchResults = new ArrayList<>();
		
		for (int i = 1; i <= numSearchResults; ++i) {
			String xpath = "//div[@data-index=\"" + i + "\"]";
			WebElement searchResult = driver.findElement(By.xpath(xpath));
			searchResults.add(searchResult);
		}
		
		return searchResults;
	}
	
	public Map<Integer, String> toDbCoulmnValues(WebElement element) {
		Map<Integer, String> dbColumnValues = new HashMap<>();
		String xpath = "//div[@data-index=\"" + element.getAttribute("data-index") + "\"]";
		String name = element.findElement(By.xpath(xpath + companyNameFieldXpath)).getText();
		String price = element.findElement(By.xpath(xpath + priceFieldXpath)).getText();
		String image = element.findElement(By.xpath(xpath + imageFieldXpath)).getAttribute("src");
		//String originalPrice = element.findElement(By.xpath(xpath + originalPriceXpath)).getText();
		
//		System.out.println(element);
//		System.out.println(element.findElement(By.xpath(xpath + originalPriceXpath)).getText());
//	
		
		dbColumnValues.put(1, name);
		dbColumnValues.put(2, price);
		dbColumnValues.put(3, image);
		//dbColumnValues.put(4, originalPrice);
		
		return dbColumnValues;
	}
}
