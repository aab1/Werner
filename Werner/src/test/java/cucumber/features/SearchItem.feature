Feature: Search an Item feature



Background: Pre-Condition
			Given I navigate to google homepage
			

@demo
Scenario: Search Item 
	When I search for "iPhone 12 Pro Max" 
	And I proceed to the fifth pagination
	And I select the sixth link from the buttom of the page
	Then The page is displayed