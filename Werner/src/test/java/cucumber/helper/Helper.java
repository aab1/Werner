package cucumber.helper;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.ConfigReader;
import java.time.Duration;
public class Helper 
{
		
	public static WebDriver driver;
	private static Select select;
	private static Actions action;
	
	
	static
	{
		driver = null;
		select = null;
		action = null;
	}
	
	public static ConfigReader   configreader = new ConfigReader();
	
	public static void isElementSelected(WebElement element) throws Exception
	{
		Assert.assertEquals(element.isSelected(),true);
	}
	
	public static void failTest(String message) throws Exception
	{
		saveScreenshot();
		Assert.fail(message);
	}
	
	
	
	
	public static void waitImplicitly() throws Exception{
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS) ;
	}
	
	public static WebDriverWait waitExplicitly()throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, 50);
		return wait;
	}
	
	public void waitForThisPageToLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(pageLoadCondition);
	}
			
	/*####################################################################
	 * This method helps to work with alert on page
	 * ####################################################################*/
	public static void acceptAlert()
	{
		driver.switchTo().alert().accept();
	}
	/*####################################################################
	Uses - This method launches Firefox Browser
	It is used in LaunchBrowser() method to initialise driver to Firefox.
	######################################################################*/
	private static WebDriver initFirefox() throws Exception
	{
		//return new FirefoxDriver();
		System.setProperty("webdriver.gecko.driver",configreader.getFirefoxPath());
		return new FirefoxDriver();
		
	}
	
	 /*######################################################################
	Uses - This method launches Chrome Browser
	It is used in LaunchBrowser() method to initialise the driver to Chrome
	########################################################################*/
	private static WebDriver initChrome() throws Exception
	{
		//System.setProperty("webdriver.chrome.driver", "./lib/browser/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", configreader.getChromePath());
		return new ChromeDriver();
		
	}
	
	/*##################################################################
	Uses - This method launches IE Browser
	It is used in LaunchBrowser() method to initialise the driver to IE
	####################################################################*/
	private static WebDriver initInternetExplorer() throws Exception
	{
		//System.setProperty("webdriver.ie.driver", "./lib/browser/IEDriverServer.exe");
		System.setProperty("webdriver.ie.driver", configreader.getInternetExplorerPath());
		return new InternetExplorerDriver();
	}
	
	/*#############################################################
	Uses - This method launches any browser assigned to it
	It takes Chrome, Firefox and IE and initialises the driver
	###############################################################*/
	public static void launchBrowser(String browserName) throws Exception
	{
		if(browserName.equalsIgnoreCase("chrome")) {
			driver = initChrome();
		}else if(browserName.equalsIgnoreCase("firefox")) {
			driver = initFirefox();
		}else if(browserName.equalsIgnoreCase("internetexplorer")) {
			driver = initInternetExplorer();
		}else {
			System.out.println(browserName + " is not recognised, so Chrome browser is launched");
			driver = initChrome();
		}
		
		driver.manage().window().maximize();
		
	}
	/*##############################################################
	 * This method helps to move to an element that is not in view
	 *############################################################## */
	
	public static void moveToElement(WebElement element) throws Exception
	{		
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
	}
	
	
	
	/*################################################################
	 * This method simply refresh the current page displayed and it helps to overcome the 
	 * stale element reference:element is not attached to the to the page document error
	 * ################################################################*/
	public static void refreshCurrentPage() throws Exception
	{
		driver.navigate().refresh();
	}
	
	/*#####################################################
	Uses - This method closes any browser open by selenium
	It is used to clean up afterwards
	#######################################################*/
	public static void closeBrowser() throws Exception
	{
		driver.manage().deleteAllCookies();
		//driver.close();
		driver.quit();
	}
	
	/*#########################################################
	Uses - This method navigates to any url passed in. 
	Note - url must have this format -> http://www.example.com
	It is called after LaunchBrowser() method has been called.
	###########################################################*/
	public static void launchUrl(String url) throws Exception
	{
		driver.navigate().to(url);
		//driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS); 
	}
	
	/*#########################################################
	 * This method helps to switch to frame. is accepts web element
	 * 
	 * #########################################################*/
	public static void switchToFrame(WebElement element)throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}
	
	/*#########################################################
	 * This method helps to switch from current frame back to default window. 
	 * 
	 * #########################################################*/
	public static void switchOutOfFrame() throws Exception
	{
		driver.switchTo().defaultContent();
	}

	
    /*#########################################################
	Uses - This method helps to hover over any element
	It takes in any WebElement of interest
	###########################################################*/
	public static void hoverOver(WebElement element) throws Exception
	{
		action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}
	
	/*#########################################################
	Uses - This method helps to use the Actions class
	
	###########################################################*/
	public static Actions getAction() {
		return action = new Actions(driver);
	}

	/*#######################################################################
	Uses - This method helps to assert/validate that an element is displayed
	It takes in any WebElement of interest
	#########################################################################*/
	public static void verifyAnElementIsDisplayed(WebElement element) throws Exception
	{
		Assert.assertTrue(element + " is not displayed", element.isDisplayed());
	}
	/*#######################################################################
	Uses - This method helps to assert/validate that title starts with the expected title
	#########################################################################*/
	public static void verifyTitleIsDisplayed(String titlePrefix) throws Exception
	{
		Assert.assertTrue("Title should start differently",driver.getTitle().startsWith(titlePrefix));
	}
	

	/*#######################################################################
	Uses - This method helps to check that correct url is displayed
	#########################################################################*/
	public static void verifyCorrectUrlIsDisplayed(String expectedUrl) throws Exception
	{
		String actualURL = driver.getCurrentUrl();
		Assert.assertEquals(actualURL, expectedUrl );
	}
	
	/*#######################################################################
	Uses - This method helps to check that url contain a given text
	@param enter the text you want to verify in the url
	#########################################################################*/
	public static void verifyUrlContainsText(String expectedTextInUrl) throws Exception
	{
		waitImplicitly();
		String actualURL = driver.getCurrentUrl();
		Assert.assertTrue("Current Url does not contain "+expectedTextInUrl, actualURL.contains(expectedTextInUrl));
	}
	
	/*#######################################################################
	Uses - This method helps to assert/validate that an element is selected
	It takes in any WebElement of interest
	#########################################################################*/
	public static void verifyAnElementIsSelected(WebElement element) throws Exception
	{
		Assert.assertTrue(element + " is not displayed", element.isSelected());
	}
	
	public static void clickAction(WebElement element) throws Exception
	{
		new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
		action = new Actions(driver);
		action.moveToElement(element).click().build().perform();
	}
	/*##########################################################
	Uses - This method helps to click on an element of interest
	It takes in any WebElement of interest
	############################################################*/
	public static void clickAnElement(WebElement element) throws Exception
	{
		element.click();
	}
	
	public static void clickAnElementFollowingATimeOut(int timeout, String xpath) throws Exception
	{
		 new WebDriverWait(driver, timeout).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		 getElementByXPath(xpath).click();
		
	}
	
	/*
	 * This moves the mouse to the middle of the element and click
	 * it solves the element not clickable at point x, y  error*/
	public static void moveToElementAndClick(WebElement element) throws Exception
	{
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
	}
	/*#######################################################################################
	 * This method helps to click an element when  exception is related to disable or visibility
	 *####################################################################################### */
	public static void clickAnElementUsingJavaScript(WebElement element) throws Exception
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", element);
	}

	/*#######################################################################################
	 * This method helps to right click an element
	 * @param It takes the webElement 
	 * #####################################################################################*/
	public static void rightClickAnElement(WebElement element)
	{
		action = new Actions(driver);
		action.contextClick(element).build().perform();
	}
	
	/*###############################################################
	Uses - This method helps to type given value into a field
	It takes in any WebElement of interest and the value to type in
	#################################################################*/
	public static void typeGivenValueInto(WebElement element, String text) throws Exception
	{
		element.clear();
		element.sendKeys(text);
	}
	
	/*#########################################################
	 * This method helps to type given text into a text box using
	 * action class.  
	 * #########################################################
	 * */
	public static void typeGivenTextInto(WebElement element, String text) throws Exception
	{
		 action = new Actions(driver);
 		 action.moveToElement(element).sendKeys(element, text).build().perform();
 		 
	}
	
	/*
	 * This method helps to press keyboard enter key
	 * It takes the WebElement of the point where the enter key should be pressed 
	 */
	 public static void pressEnterKey(WebElement element) throws Exception
	 {
		 element.sendKeys(Keys.RETURN);
	 }
	/*#######################################################################
	Uses - This method helps to assert/validate that an element is enabled
	It takes in any WebElement of interest
	#########################################################################*/
	public static void VerifyAnElementIsEnabled(WebElement element) throws Exception
	{
		Assert.assertTrue(element + " is not displayed", element.isEnabled());
	}
	
	/*#######################################################################
	Uses - This method helps to assert/validate that an element is disabled
	It takes in any WebElement of interest
	#########################################################################*/
	public static void VerifyAnElementIsDisabled(WebElement element) throws Exception
	{
		Assert.assertFalse(element + " is displayed", element.isEnabled());
	}
	
	/*########################################################################################
	Uses - This method helps to assert/validate that an element contains a given text snippet
	It takes the full text and the text snippet needed to be validated 
	##########################################################################################*/
	public static void verifyTextContainSnippet(String mainText, String snippet) throws Exception
	{
		Assert.assertTrue(snippet + " is not contained in " + mainText, 
				mainText.toLowerCase().contains(snippet.toLowerCase()));
	}
	
	/*##################################################################################################
	Uses - This method helps to assert/validate that a list of element are displayed e.g. Search Result
	It takes in List of WebElement of interest
	####################################################################################################*/
	public static void verifyListOfElementsAreDisplayed(List<WebElement> element) throws Exception
	{
		Assert.assertTrue(element + " is not displayed", element.size()>0);
	}
	
	/*###########################################################################
	Uses - This method helps to assert/validate the number of elements displayed
	It takes in a List of WebElement of interest
	#############################################################################*/
	public static void verifyListOfElementsAreDisplayed(List<WebElement> element, int size) throws Exception
	{
		Assert.assertTrue(element + " is not displayed", element.size()>size);
	}
	/*###################################################################################################
	Uses - This method helps to assert/validate the number of elements displayed is less than a given number i.e. size
	It takes in a List of WebElement of interest
	#####################################################################################################*/
	public static void verifyListOfElementsDisplayedIsLessThanGivenSize(List<WebElement> element, int size) throws Exception
	{
		Assert.assertTrue(element + " is not displayed", element.size()<size);
	}
	
	/*###################################################################################################################
	Uses - This method helps to select element from a dropdown by specifying the index of the item needed to be selected
	It takes in the dropdown as a WebElement and the index as a number
	#####################################################################################################################*/
	public static void selectByIndex(WebElement element, int index) throws Exception
	{
		select = new Select(element);
		select.selectByIndex(index);
	}
	
	/*###################################################################################################################
	Uses - This method helps to select element from a dropdown by specifying the index of the item needed to be selected
	It takes in the dropdown as a WebElement and the index as a number
	#####################################################################################################################*/
	public static void selectByValue(WebElement element, String value) throws Exception
	{
		select = new Select(element);
		select.selectByValue(value);
	}
	
	/*###################################################################################################################
	Uses - This method helps to select element from a dropdown by specifying the Text of the item needed to be selected
	It takes in the dropdown as a WebElement and the Text as a String
	#####################################################################################################################*/
	public static void selectByText(WebElement element, String text) throws Exception
	{
		select = new Select(element);
		select.selectByVisibleText(text);
	}
	
	
	public static File takeScreenShot() throws Exception
	{
		return ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	}
	
	public static void saveScreenshot() throws Exception
	{
		String dateNow = new SimpleDateFormat("ddMMyy").format(new GregorianCalendar().getTime());
		String timeNow = new SimpleDateFormat("hhmmss").format(new GregorianCalendar().getTime());
		
		String fileName = String.format(".\\Screenshots\\%s\\screenshot_%s", dateNow, timeNow);
		
		File screenshot = takeScreenShot();
		
		FileUtils.copyFile(screenshot, new File(fileName));
			
	}
	
	/*##########################################################################
	Uses - This method helps to find element by specifying the Id of the element
	It takes in the Id as a string
	############################################################################*/
	public static WebElement getElementById(String id) throws Exception
	{
		By locator = By.id(id);
		return getElement(locator);
	}
	
	/*###################################################################################
	Uses - This method helps to find List of elements by specifying the Id of the element
	It takes in the Id as a string
	#####################################################################################*/
	public static List<WebElement> getElementsById(String id) throws Exception
	{
		By locator = By.id(id);
		return getElements(locator);
	}
	
	/*##################################################################################
	Uses - This method helps to find element by specifying the classname of the element
	It takes in the className as a string
	####################################################################################*/
	public static WebElement getElementByClassName(String className) throws Exception
	{
		By locator = By.className(className);
		return getElement(locator);
	}
	
	/*###################################################################################
	Uses - This method helps to find elements by specifying the className of the element
	It takes in the className as a string
	#####################################################################################*/
	public static List<WebElement> getElementsByClassName(String className) throws Exception
	{
		By locator = By.className(className);
		return getElements(locator);
	}
	
	/*##############################################################################################
	Uses - This method helps to find element by specifying the cssSelector identifier of the element
	It takes in the cssSelector identifier as a string
	################################################################################################*/
	public static WebElement getElementByCssSelector(String cssSelector) throws Exception
	{
		By locator = By.cssSelector(cssSelector);
		return getElement(locator);
	}
	
	/*###############################################################################################
	Uses - This method helps to find elements by specifying the cssSelector identifier of the element
	It takes in the cssSelector identifier as a string
	#################################################################################################*/
	public static List<WebElement> getElementsByCssSelector(String cssSelector) throws Exception
	{
		By locator = By.cssSelector(cssSelector);
		return getElements(locator);
	}
	
	/*##########################################################################################
	Uses - This method helps to find element by specifying the XPath identifier of the element
	It takes in the XPath identifier as a string
	############################################################################################*/
	public static WebElement getElementByXPath(String xpath) throws Exception
	{
		By locator = By.xpath(xpath);
		return getElement(locator);
	}
	
	/*##########################################################################################
	Uses - This method helps to find element by specifying the link text identifier of the element
	It takes in the link text identifier as a string
	############################################################################################*/
	
	public static WebElement getElementByLinkText(String linkText) throws Exception
	{
		By locator = By.linkText(linkText);
		return getElement(locator);
	}
	/*##########################################################################################
	Uses - This method helps to find elements by specifying the XPath identifier of the element
	It takes in the XPath identifier as a string
	############################################################################################*/
	public static List<WebElement> getElementsByXPath(String xpath) throws Exception
	{
		By locator = By.xpath(xpath);
		return getElements(locator);
	}
	
	/*###############################################################################
	 * This method helps to retrieve text on a page
	############################################################################### */
	public String getPageSource() throws Exception
	 {
	  return driver.getPageSource();
	 }
	
	/*#############################################################################
	Uses - This method helps to find element by specifying the name of the element
	It takes in the name as a string
	###############################################################################*/
	public static WebElement getElementByName(String name) throws Exception
	{
		By locator = By.name(name);
		return getElement(locator);
	}
	
	/*#############################################################################
	Uses - This method helps to find element by specifying the name of the element
	It takes in the name as a string
	###############################################################################*/
	public static List<WebElement> getElementsByName(String name) throws Exception
	{
		By locator = By.name(name);
		return getElements(locator);
	}
	
	private static WebElement getElement(By locator) throws Exception
	{
		WebElement element = null;
		int tryCount = 0;
		
		while (element == null)
		{
			try
			{
				element = driver.findElement(locator);
				return new WebDriverWait(driver, 1)
	                       .until(ExpectedConditions.visibilityOfElementLocated(locator));
			} 
			catch(Exception e)
			{
				if(tryCount == 3)
				{
					saveScreenshot();
					System.out.println(element.toString() + " cannot be found");
					throw e;
				}
				
				tryCount++;
			}
		}
		System.out.println(element.toString() + " is now retrieved");
		return element;
	}
	
	private static List<WebElement> getElements(By locator) throws Exception
	{
		List<WebElement> element = null;
		int tryCount = 0;
		
		while (element == null)
		{
			try
			{
				element = driver.findElements(locator);
				return new WebDriverWait(driver, 1)
	                       .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			} 
			catch(Exception e)
			{
				if(tryCount == 3)
				{
					saveScreenshot();
					System.out.println(element.toString() + " cannot be found");
					throw e;
				}
				
				tryCount++;
			}
		}
		System.out.println(element.toString() + " is now retrieved");
		return element;
	}
	
	//########################### some useful non selenium methods###########################################################
	/*
	 * This method removes a character from a word
	 * */
	public static String removeChar(String word, char charToRemove)
	{
	    for(int i = 0; i < word.length(); i++)
	    {
	        if(word.charAt(i) == charToRemove)
	        {
	            String newWord = word.substring(0, i) + word.substring(i + 1);
	            return removeChar(newWord, charToRemove);
	        }
	    }

	    return word;
	}
	
	/*This method checks date range e.g. it can check if the difference between 2 dates is over a year
	 * must instantiate SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	 * */
	public static boolean checkForPeriodLimitInYears(Date dateFrom, Date dateTo, int periodInYear){

	    Period p = Period.between(dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
	            dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

	    return ((p.getYears() < periodInYear)?true:false);
	}

	/*
	 * This method checks date range e.g. it can check if the difference between 2 dates is over a year
	 * checking if the period is more than the specified number of years (last
	 * */
	public static void isDateMoreThanSpecifiedNumberOfYears(String dateFrom, String dateTo, int numYear)throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		if (checkForPeriodLimitInYears(sdf.parse(dateFrom), sdf.parse(dateTo), numYear)) {
			if(numYear > 1) {
				System.out.println("Number of years is more than a year as expected" );
			}else {
				System.out.println("less than " + numYear + " year");
			}
			
		} else {
			failTest("Start and  end date range is more than expected number of years" );
		}
	}
	/*
	 * This method checks the number of days between 2 dates is not more than a particular number of days
	 * it returns boolean depending on the number of days entered @param(i.e. periodInDays)
	 *  */
	public static boolean checkForPeriodLimitInDays(Date dateFrom, Date dateTo, int periodInDays){

	    Period p = Period.between(dateFrom.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
	            dateTo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

	    return ((p.getDays()<= periodInDays)?true:false);
	}
	
	// checking if the period is more than the specified number of days (last parameter)
	public static void isDateMoreThanSpecifiedNumberOfDays(String dateFrom, String dateTo, int numDays)throws Exception
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		 
		if (checkForPeriodLimitInDays(sdf.parse(dateFrom), sdf.parse(dateTo), numDays)) {
			System.out.println("less than or equal to " + numDays );
		} else {
			//System.out.println("more than " + numDays );
			failTest("Default date range is more than expected number of days");
		}
	}
	
	/* This method check that date format is correct
	 * @param it take the format of the date e.g. dd/MM/yyyy
	 * @param it takes the date that needs to be tested e.g. 20130925
	 * */
	public static boolean isDateValidFormat(String format, String value) 
	{
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
	
	/*
	 * This method checks if a file is downloaded
	 * @param it takes the path to which the file will be downloaded  and the name of the file to be downloaded
	 * E.g. C:\\Users\\egrvw4j\\Downloads", fileName = Utilization.csv
	 * it also deletes the file after validating that it is successfully downloaded
	 * */
	public static boolean isFileDownloaded(String downloadPath, String fileName) throws Exception
	{
		File dir = new File(downloadPath);
		File[] dirContents = dir.listFiles();

		for (int i = 0; i < dirContents.length; i++) {
			dirContents[i].getName();
			if (dirContents[i].getName().equals(fileName)) {
				// File has been found, it can now be deleted:
				dirContents[i].delete();
				return true;
			}
		}
		return false;
	}
	/*#############################################################################################################
	 * This method returns the difference between 2 strings
	 * @param enter the shorter string as first parameter(actual)
	 * @param enter the longer string as second parameter(expected)
	 * */
	 public static String getStringDifference(String actual, String expected)
	    {
	        int i = 0;
	        int j = 0;
	        String result = "";
	        
	        while (j < expected.length())
	        {
	            if (actual.charAt(i) != expected.charAt(j) || i == actual.length())
	                result += expected.charAt(j);
	            else
	                i++;
	            j++;
	        }
	        return result;
	       // System.out.println(result); 
	    }

	 /*##############################################################################################################
	  * This method helps to compare 2 list 
	  * it returns true if list is equal and false otherwise
	  * This method ignores order of element in the list  and it does not allow duplicate
	  * So the list must contain the same elements*/
	private static class Count {
		public int count = 0;
	}

	public static boolean compareLists(final List<String> list1, final List<String> list2) {
		// (list1, list1) is always true
		if (list1 == list2)
			return true;

		// If either list is null, or the lengths are not equal, they can't possibly
		// match
		if (list1 == null || list2 == null || list1.size() != list2.size())
			return false;

		// (switch the two checks above if (null, null) should return false)

		Map<String, Count> counts = new HashMap<>();

		// Count the items in list1
		for (String item : list1) {
			if (!counts.containsKey(item))
				counts.put(item, new Count());
			counts.get(item).count += 1;
		}

		// Subtract the count of items in list2
		for (String item : list2) {
			// If the map doesn't contain the item here, then this item wasn't in list1
			if (!counts.containsKey(item))
				return false;
			counts.get(item).count -= 1;
		}

		// If any count is nonzero at this point, then the two lists don't match
		for (Map.Entry<String, Count> entry : counts.entrySet()) {
			if (entry.getValue().count != 0)
				return false;
		}

		return true;
	}
	
	/*
	 * This method return the difference between 2 lists of type String 
	 * */
	public static List<String> getListDifference(List<String> appList_ActualList,
			List<String> expectedList_formFeatureFile) {
		List<String> toReturn = new ArrayList<String>(appList_ActualList);
		toReturn.removeAll(expectedList_formFeatureFile);

		return toReturn;
	}
	/*
	 * This method gets the list of items from feature file and returns it in a list 
	 * */
	public  List<String> getFeatureFileData(List<List<String>> data) throws Exception
	{
		List<String> expectedAppData = new ArrayList<String>();
		String DataFromFeatureFile;
		for (int i = 0; i < data.size(); i++) {
			DataFromFeatureFile = data.get(i).toString();
			DataFromFeatureFile = DataFromFeatureFile.substring(1);
			DataFromFeatureFile = removeChar(DataFromFeatureFile, ']');
			expectedAppData.add(DataFromFeatureFile);
//			System.out.println("Data From Feature File "+DataFromFeatureFile);
		}
		return expectedAppData;
	}
	/*
	 * ##############################################################################################
	 * This methods to waits for file download to complete
	 * */
	  protected void waitForFileDownload(int totalTimeoutInMillis, String expectedFileName) throws IOException { 
		  Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(100))
			        .pollingEvery(Duration.ofMillis(600)).ignoring(NoSuchElementException.class);
		  
//          FluentWait<WebDriver> wait = new FluentWait(this.driver)
//                                 .withTimeout(totalTimeoutInMillis, TimeUnit.MILLISECONDS)
//                                 .pollingEvery(200, TimeUnit.MILLISECONDS);
          File fileToCheck = getDownloadsDirectory()
                             .resolve(expectedFileName)
                             .toFile();

          wait.until((WebDriver wd) -> fileToCheck.exists());

      }


	public synchronized Path getDownloadsDirectory() {
		Path downloadsDirectory = Paths.get("C:\\Users\\egrvw4j\\Downloads");
		if (downloadsDirectory == null) {

			try {
				downloadsDirectory = Files.createDirectory(downloadsDirectory);
			} catch (IOException ex) {
				throw new RuntimeException("Failed to create temporary downloads directory");
			}
		}
		return downloadsDirectory;
	}
	// ##############################################################################################
	/*
	 * This method check that app date is a date before the expected date
	 * it can be used when checking if a date is less than another date
	 * */
	 public static boolean isDateBeforeAGivenDate(String appDate, String expectedDateFromFeatureFile)
	    {
	    	String [] appDateArray= appDate.split("/");
	    	String [] expectedDateArray= expectedDateFromFeatureFile.split("/");
	    	
	    	LocalDate date = LocalDate.of(Integer.parseInt(appDateArray[2]), Integer.parseInt(appDateArray[0]), Integer.parseInt(appDateArray[1])); // yr,mth,dd takes app date
	    	
	    	if (date.isBefore(LocalDate.of(Integer.parseInt(expectedDateArray[2]),Integer.parseInt(expectedDateArray[0]),Integer.parseInt(expectedDateArray[1]))))
	    	{ //takes feature file date
//	    		System.out.println(date+" is before "+ LocalDate.of(Integer.parseInt(expectedDateArray[2]),Integer.parseInt(expectedDateArray[0]),Integer.parseInt(expectedDateArray[1])));
	    		return true;
	    	}
//	    	System.out.println(date+" is NOT before "+ LocalDate.of(Integer.parseInt(expectedDateArray[2]),Integer.parseInt(expectedDateArray[0]),Integer.parseInt(expectedDateArray[1])));
    		return false;
	    }
	 
	 /*
		 * This method check that app date is a date after the expected date
		 * it can be used when checking if a date is greater than another date
		 * */
	 public static boolean isDateAfterAGivenDate(String appDate, String expectedDateFromFeatureFile)
	    {
	    	String [] appDateArray= appDate.split("/");
	    	String [] expectedDateArray= expectedDateFromFeatureFile.split("/");
	    	
	    	LocalDate date = LocalDate.of(Integer.parseInt(appDateArray[2]), Integer.parseInt(appDateArray[0]), Integer.parseInt(appDateArray[1])); // yr,mth,dd takes app date
	    	
	    	if (date.isAfter(LocalDate.of(Integer.parseInt(expectedDateArray[2]),Integer.parseInt(expectedDateArray[0]),Integer.parseInt(expectedDateArray[1]))))
	    	{ //takes feature file date
//	    		System.out.println(date+" is after "+ LocalDate.of(Integer.parseInt(expectedDateArray[2]),Integer.parseInt(expectedDateArray[0]),Integer.parseInt(expectedDateArray[1]))); 
	    		return true;
	    	}
	    	System.out.println(date+" is NOT after "+ LocalDate.of(Integer.parseInt(expectedDateArray[2]),Integer.parseInt(expectedDateArray[0]),Integer.parseInt(expectedDateArray[1]))); 
    		return false;
	    }
	 
	public static boolean checkDateFormat(String inputDate) {
		String regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputDate);
		//System.out.println(inputDate + " : " + matcher.matches());
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	/*==================================================================================================================================
	 * This method removes space from a String
	 *=================================================================================================================================== 
	 *  */
	public static String removeSpace(String s) {
        String withoutspaces = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ')
                withoutspaces += s.charAt(i);

        }
        return withoutspaces;

    }
	
	/*==================================================================================================================================
	 * This method validates lists by comparing it to a given list such as headers or list from a drop down
	 * @param it takes expected list from a feature file
	 * @param it takes the xpath of the actual list from the application
	 *=================================================================================================================================== 
	 *  */
	public void validateList(List<List<String>> expectedList, List<WebElement> xpathOfAppElements) throws Exception {
		List<String> actualAppList = new ArrayList<String>();
		List<WebElement> actualAppElements = xpathOfAppElements;
		for (int i = 0; i < actualAppElements.size(); i++) {
			try {
				moveToElement(actualAppElements.get(i));
				if (actualAppElements.get(i).isDisplayed()&& (!actualAppElements.get(i).getText().isEmpty())) {
					actualAppList.add(actualAppElements.get(i).getText());
//					System.out.println("app text: "+actualAppElements.get(i).getText());
				}
			} catch (Exception e) {
				refreshCurrentPage();
				actualAppElements =xpathOfAppElements;
				moveToElement(actualAppElements.get(i));
				if (actualAppElements.get(i).isDisplayed()) {
					actualAppList.add(actualAppElements.get(i).getText());
				}
			}
		}
		if (compareLists(actualAppList, getFeatureFileData(expectedList)) == false) {
//			System.out.println(getListDifference(actualAppList, getFeatureFileexpectedList(expectedList)));
			if (!getListDifference(actualAppList, getFeatureFileData(expectedList)).isEmpty()) {
				failTest(
						"The list displayed is not correct due to the following difference as shown on the app \n"
								+ getListDifference(actualAppList, getFeatureFileData(expectedList)));
			} else if (!getListDifference(getFeatureFileData(expectedList), actualAppList).isEmpty()) {
				failTest(
						"The list displayed is not correct is NOT correct due to the following expected List that is not displayed  as shown in the feature file: \n"
								+ getListDifference(getFeatureFileData(expectedList), actualAppList));
			}
		}
	}
}
