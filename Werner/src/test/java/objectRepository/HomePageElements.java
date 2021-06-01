package objectRepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cucumber.helper.Helper;

public class HomePageElements extends Helper{

	private static WebElement element;
	
	public static WebElement getGoogleLogoElement() throws Exception
	{
		 try {
			 element = getElementByCssSelector("img[alt='Google']");
		} catch (Exception e) {
			element = waitExplicitly().until(ExpectedConditions.visibilityOf(getElementByCssSelector("img[alt='Google']")));
		}
		
		return element;
	}
	
	public static WebElement getSearchFieldElement() throws Exception
	{
		 try {
			 element = getElementByCssSelector("[name=\"q\"]");
		} catch (Exception e) {
			element = waitExplicitly().until(ExpectedConditions.visibilityOf(getElementByXPath("//input[@title='Search']")));
		}
		
		return element;
	}
	
		
}
