package cucumber.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.helper.PagesBase;
import cucumber.pages.HomePage;
import cucumber.pages.SearchResultPage;

public class searchItemSteps {
	PagesBase pageBase = new PagesBase();
	HomePage homePage;
	SearchResultPage searchResultPage;

	@Given("^I navigate to google homepage$")
	public void i_navigate_to_google_homepage() throws Throwable {
		homePage = pageBase.navigateToGoogleHomePage();
//		homePage.isHomePageDisplayed();
	}

	@When("^I search for \"(.*?)\"$")
	public void i_search_for(String item) throws Throwable {
		searchResultPage = homePage.searchItem(item);
	}

	@When("^I proceed to the fifth pagination$")
	public void i_proceed_to_the_fifth_pagination() throws Throwable {
		searchResultPage.clickFifthPagination();
	}

	@When("^I select the sixth link from the buttom of the page$")
	public void i_select_the_sixth_link_from_the_buttom_of_the_page() throws Throwable {
		searchResultPage.clickSixItemFromButtom();
	}

	@Then("^The page is displayed$")
	public void the_page_is_displayed() throws Throwable {
	    
	}
}
