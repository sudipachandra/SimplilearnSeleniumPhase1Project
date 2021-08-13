import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ProductDetailsPage;
import util.DBUtil;

public class ProductList extends DBUtil {
	
	private static String insertStatement = "insert into amazonlist values(?,?,?)";
	
	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", "/Users/sudipa/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		try {
			driver.get("https://www.amazon.in/");
			driver.manage().window().maximize();
			ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
			productDetailsPage.search("oppo");
			Thread.sleep(4000);
			List<WebElement> searchResults = productDetailsPage.grabNSearchResults(3);
			
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con.prepareStatement(insertStatement);
			for (WebElement searchResult : searchResults) {
				Map<Integer, String> dbColumnNameValues = productDetailsPage.toDbCoulmnValues(searchResult);
				for (Map.Entry<Integer, String> entry : dbColumnNameValues.entrySet()) {
					ps.setString(entry.getKey(), entry.getValue());
				}
				int result = ps.executeUpdate();
				if (result > 0) {
					System.out.println("insertion successful");
				} else {
					System.out.println("insertion not successful");
				}
			}

		String expectedName = "OPPO A74 5G (Fantastic Purple,6GB RAM,128GB Storage) - 5G Android Smartphone | 5000 mAh Battery | 18W Fast Charge | 90Hz LCD Display";
		String expectedPrice = "17,990";
		String expectedImage = "https://m.media-amazon.com/images/I/71geVdy6-OS._AC_UY218_.jpg";
		String expectedPrice1 = "16,000";
		//String expectedOriginalPrice = "5,081";
		String sql1 = "select * from amazonlist where price = '17,990'";
			PreparedStatement ps1 = con.prepareStatement(sql1);
			ResultSet rs = ps1.executeQuery();
			boolean status = false;
			while(rs.next()) {
			String actualName = rs.getString(1);
			System.out.println(actualName);
			String actualPrice = rs.getString(2);
			System.out.println(actualPrice);
			String actualImage = rs.getString(3);
			System.out.println(actualImage);
			//String actualOriginalPrice = rs.getString(4);
			//System.out.println(actualOriginalPrice);
			if(actualName.equals(expectedName)&&actualPrice.equals(expectedPrice)&&actualImage.equals(expectedImage)) {
			System.out.println("test case passed for condition1");	
			status = true;
			//break;
			}
			if(status==false){
				System.out.println("test case failed for condition1");
			}
			
			if(actualName.equals(expectedName)&&actualPrice.equals(expectedPrice1)&&actualImage.equals(expectedImage)) {
				System.out.println("test case passed for condition2");	
				status = true;
			    break;
			}else {
				System.out.println("test case failed for condition2");
			}
			
			
		}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			driver.close();
		}
	}
}
