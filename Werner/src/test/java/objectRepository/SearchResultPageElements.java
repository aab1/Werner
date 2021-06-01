package objectRepository;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cucumber.helper.Helper;

public class SearchResultPageElements extends Helper{

	private static WebElement element;
	
	public static WebElement getPageFiveElement() throws Exception
	{
		 try {
			 element = getElementByCssSelector("[aria-label=\"Page 5\"]");
		} catch (Exception e) {
			element = waitExplicitly().until(ExpectedConditions.visibilityOf(getElementByCssSelector("[aria-label=\"Page 5\"]")));
		}
		
		return element;
	}
	
	public static WebElement getSixthItemElement() throws Exception
	{
		 try {
			 element = getElementByXPath("//div[@class='g'][5]/div/div/div/a/h3");
		} catch (Exception e) {
			element = waitExplicitly().until(ExpectedConditions.visibilityOf(getElementByXPath("//div[@class='g'][5]/div/div/div/a/h3")));
		}
		
		return element;
	}
	
		
}
