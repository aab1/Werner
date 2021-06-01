package utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class  ConfigReader {

	static Properties properties;
	
	public ConfigReader(){
		
		try {
			File src = new File("./src/test/java/Configuration/Config.property");
			FileInputStream fis = new FileInputStream(src);
			properties = new Properties();
			properties.load(fis);
		} catch (Exception e) {
			System.out.println("Exception is: "+e.getMessage());
		}
		
	}
	
	public  String getChromePath(){
		String path = properties.getProperty("ChromeDriver");
		return path;	
	}
	
	public String getFirefoxPath() {
		return properties.getProperty("FirefoxDriver");
	}
	
	public String getInternetExplorerPath() {
		return properties.getProperty("InternetExplorerDriver");
	}
	
	public String getUrl(){
		return properties.getProperty("URL");
	}
	
	public String getEmailId() {
		return properties.getProperty("Email");
	}
	
	public String getPassword() {
		return properties.getProperty("Password");
	}
	

}
