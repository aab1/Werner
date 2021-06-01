package cucumber.pages;

import org.openqa.selenium.support.ui.ExpectedConditions;

import cucumber.helper.PagesBase;
import objectRepository.HomePageElements;

public class HomePage extends PagesBase{
	
	public void isHomePageDisplayed() throws Exception
	{
		try {
			verifyAnElementIsDisplayed(HomePageElements.getGoogleLogoElement());
		} catch (Exception e) {
			verifyAnElementIsDisplayed(waitExplicitly().until(ExpectedConditions.visibilityOf(HomePageElements.getGoogleLogoElement())));
		}
	}
	
	public SearchResultPage searchItem(String item) throws Exception
	{
		typeGivenValueInto(HomePageElements.getSearchFieldElement(),item);
		pressEnterKey(HomePageElements.getSearchFieldElement());
		return new SearchResultPage();
	}
	

	
	
}
