package cucumber.pages;

import objectRepository.SearchResultPageElements;

public class SearchResultPage extends HomePage{
	
	public void clickFifthPagination() throws Exception {
		clickAnElementUsingJavaScript(SearchResultPageElements.getPageFiveElement());
	}
	
	public void clickSixItemFromButtom() throws Exception {
		clickAnElementUsingJavaScript(SearchResultPageElements.getSixthItemElement());
	}

}
