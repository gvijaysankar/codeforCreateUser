package practice;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import org.testng.annotations.BeforeTest;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;


/*
     Create an autotest that creates an account with a username having 2 characters.
 */

public class  testmainwebdriver{
	
	private static  WebDriver driver=null;
	private static Logger LOGGER = Logger.getLogger(testmainwebdriver.class);
	String huburl=null;
	String browser=null;
	//User length to create
	int usrlenght=2;
	@BeforeTest
	public void beforeTest()
	{
	
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		huburl=System.getProperty("grid.hub.url");
		
	}
	
	public WebDriver getDriver(String browser, String platform,String version) throws MalformedURLException
	{
		DesiredCapabilities dc=new DesiredCapabilities();
		dc.setVersion(version);
		dc.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
		dc.setPlatform(Platform.VISTA);
		dc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
		dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
		if(huburl==null)
		{
		driver=new ChromeDriver();
		}
		else
		{
			driver=new RemoteWebDriver(new URL(huburl),dc);
		}
		return driver;
	}
	
	public static void screenShot(WebDriver driver) throws IOException
	{		
		String dir=System.getProperty("user.home")+"\\Desktop";
		File f=new File(dir);
		String date1=new SimpleDateFormat("ddMMyyy_HHmm").format(Calendar.getInstance().getTime());
		File f1=new File(f+"/"+date1);
		if(!f1.exists())
		{
			f1.mkdir();
		}
		
		String date2=new SimpleDateFormat("ddMMyyy").format(new Date());
		
		File f2=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		File f3=new File(f1+"/"+"date2.png");
		FileUtils.copyFile(f2, f3);
		
		
		
	}
	
	public static String generateRandomString(int length) throws IOException
	{		
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
	    String numbers = "0123456789";
	    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    for(int i = 0; i < length; i++)
	    {
	    int index = random.nextInt(alphaNumeric.length());
	    char randomChar = alphaNumeric.charAt(index);
	    sb.append(randomChar);
	    }
	    String randomString = sb.toString();
	   return randomString;
	}
	public static Map<String,String> updateUserDetails(String userName) throws IOException
	{
		Map<String,String> userdetails = new HashMap<String,String>();
		String password=generateRandomString(9);
	    String email=generateRandomString(8)+"@gmail.com";
	    userdetails.put("userName",userName);
	    userdetails.put("emailID",email);
	    userdetails.put("password",password);
	    return userdetails;
	}
	
	@Test
	public void Test1() throws Exception
	{
      //Provide the lenght
	  String userName=generateRandomString(usrlenght);   
      int userlenghth=userName.length();
      try
      {
      if(!(userlenghth==0) && !(userName.isEmpty()))
      {
    	  Map<String,String> userdetails =updateUserDetails(userName);
    	  driver= getDriver("CHROME", "Windows 7", "ANY");
          driver.get("https://try.vikunja.io/register");	
          testmainwebdriver1 register=new testmainwebdriver1(driver);
          //Verify the Negitive Scenarions first and check Application is giving proper error messages
          register.negitiveScenarios(userdetails);          
          register.createAccount(userdetails);
          Thread.sleep(5000);
          WebElement username1=driver.findElement(By.xpath(" //span[@class='username']"));
          if(username1!=null && username1.getText().equalsIgnoreCase(userdetails.get("userName")))
          {
        	  LOGGER.info("PASS::SignUp Success");
           }
          else
          {
        	  LOGGER.info("FAIL::SignUp Success");
        	  screenShot(driver);
          }
          
      }
      else
      {
    	 LOGGER.info("FAIL::Invalid User Length");
      }
      }
      
      catch(Exception e)
		{
			e.printStackTrace();
		}
      
      finally
		{
			 if(driver != null)
		     {
		       driver.quit();
		     }	
		
			LOGGER.info("*****************Completed ***************");
		}
	 
}
}



