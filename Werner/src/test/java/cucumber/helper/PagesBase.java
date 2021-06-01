package cucumber.helper;

import cucumber.pages.HomePage;
//import org.openqa.selenium.support.ui.ExpectedConditions;


public class PagesBase extends Helper{
	
	public HomePage navigateToGoogleHomePage() throws Exception
	{
		launchUrl(configreader.getUrl());
		return new HomePage();	
	}
	
}
