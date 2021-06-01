package objectRepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cucumber.helper.Helper;

public class CustomerSupportPageElements extends Helper{
	
	private static WebElement element;
	
	public static WebElement getFileAClaimButtonElement() throws Exception
	{
		 try {
			 element = getElementByCssSelector("#main > div > div > section > div > p:nth-child(3) > a");
		} catch (Exception e) {
			element = waitExplicitly().until(ExpectedConditions.visibilityOf(getElementByCssSelector("#main > div > div > section > div > p:nth-child(3) > a")));
		}
		
		return element;
	}
	

}
