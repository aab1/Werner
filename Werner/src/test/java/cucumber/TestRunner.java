package cucumber;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.ExtentCucumberFormatter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"com.cucumber.listener.ExtentCucumberFormatter"},
					 features = {"src/test/java/cucumber"},
					 glue = {"cucumber.steps"},
					 tags = {"@demo"}
				)

//@underDev ~@ignore


public class TestRunner {
//{"com.cucumber.listener.ExtentCucumberFormatter"} 
	//{ "pretty", "junit:target/cucumber-reports/Cucumber.xml" } --> for junit xml report
	@BeforeClass
	public static void setup() {
		
		String dateNow = new SimpleDateFormat("ddMMyy").format(new GregorianCalendar().getTime());
		String timeNow = new SimpleDateFormat("hhmmss").format(new GregorianCalendar().getTime());
		String fileName = String.format("C:/Werner/report/%s/report_%s.html", dateNow, timeNow);
		
		
		ExtentCucumberFormatter.initiateExtentCucumberFormatter(new File(fileName));
	
		ExtentCucumberFormatter.loadConfig(new File("src/test/java/cucumber/resource/extent-config.xml"));
	
	
		ExtentCucumberFormatter.addSystemInfo("Browser Name", "Chrome");
		ExtentCucumberFormatter.addSystemInfo("Browser version", "v73");
		ExtentCucumberFormatter.addSystemInfo("Selenium version", "v3.141.59");
	
	
	}
	
	@AfterClass
    public static void tearDown() {
        
    }

}