package practice;

import java.util.Map;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;



public class  testmainwebdriver1 
{
private static Logger LOGGER = Logger.getLogger(testmainwebdriver1.class);
WebDriver driver;	
WebDriverWait wait;

@FindBy(xpath="//input[@name='username']")	
WebElement uname1;

@FindBy(xpath="//input[@name='email']")	
WebElement email1;

@FindBy(xpath="//input[@name='password']")	
WebElement password1;


@FindBy(xpath="//button[contains(@id,'register-submit')]")	
WebElement signup;

@FindBy(xpath="//div[text()='A user with this email address already exists.']")	
WebElement userexits1;

@FindBy(xpath="//div[text()='Invalid Data']")	
WebElement invaliddata1;

public testmainwebdriver1(WebDriver driver)
{
	this.driver=driver;
	PageFactory.initElements(driver,this);
}

public void createAccount(Map<String,String> userdetails)
{
	
	try
	{
	wait=new WebDriverWait(driver,5);
	
	//Success Account Creation
	  LOGGER.info("uname="+ userdetails.get("userName") + " " +"email1="+ userdetails.get("emailID") +""+ "password="+ userdetails.get("password"));
	(wait).until(ExpectedConditions.visibilityOf(uname1)).sendKeys(userdetails.get("userName"));
	(wait).until(ExpectedConditions.visibilityOf(email1)).sendKeys(userdetails.get("emailID"));	
	(wait).until(ExpectedConditions.visibilityOf(password1)).sendKeys(userdetails.get("password"));
	if(signup.isEnabled())
	{
		signup.click();
	}
	else
	{
	   LOGGER.info("FAIL::CreateAccount button is not enabled");
	   throw new Exception("CreateAccount button is not enabled");
	}
	}
	
	catch (Exception e) {
        e.printStackTrace();
  }
	
}

public String invalidTestData(String uname,String email,String password,String invaliddata,String existinguser) throws InterruptedException
{
	String text="";		
	try
	{
	uname1.sendKeys(uname);
	email1.sendKeys(email);
	password1.sendKeys(password);		
	signup.click();
	Thread.sleep(5000);
	if(!(invaliddata.isEmpty()))
	{
		text=invaliddata1.getText();
	}
	if(!(existinguser.isEmpty()))
	{
		text=userexits1.getText();
	}
		
	}
	catch (Exception e) {
		LOGGER.info("Issue while validating the Negitive scenarions for User creation");
        
  }	
	
	return text;
	
}

public String missingData(String uname,String email,String password) throws InterruptedException
{
	String text="";		
	try
	{
	uname1.sendKeys(uname);
	email1.sendKeys(email);
	password1.sendKeys(password);	
	if(signup.isDisplayed())
	{
		text="button disabled";
	}
	else
	{
		text="button is enabled";
	}
		
	}
	catch (Exception e) {
		LOGGER.info("Issue while validating the Negitive scenarions for User creation");
        
  }	
	
	return text;
	
}

/* Function to validate negitive scenarios */
public void negitiveScenarios(Map<String,String> userdetails) throws InterruptedException
{
	String text="";
   
	
	//negitive Scenario with invalid user
	text=invalidTestData("vijay.", userdetails.get("emailID"), userdetails.get("password"), "invalid", "");
	if(text.equalsIgnoreCase("Invalid Data"))
	{
		LOGGER.info("PASS::Application displaying errror message correctly for Invalid user data");
	}
	
	driver.navigate().refresh();
	Thread.sleep(3000);
	
	//negitive Scenario with password less than 8 characters
	 text=invalidTestData(userdetails.get("userName"), userdetails.get("emailID"), "pass", "invalid", "");
	  if(text.equalsIgnoreCase("Invalid Data"))
	  {
			LOGGER.info("PASS::Application displaying errror message correctly for Invalid Password");
	  }
	  
	  driver.navigate().refresh();
	  Thread.sleep(3000);
	  
	//negitive Scenario with existing user email
	  text=invalidTestData(userdetails.get("userName"), "gvijaysankar@gmail.com", userdetails.get("password"), "", "existinguseremail");
	  if(text.equalsIgnoreCase("A user with this email address already exists."))
	   {
			LOGGER.info("PASS::Application displaying errror message correctly when user trying to create user with existing user email");
	   }
	  
	  driver.navigate().refresh();
	  Thread.sleep(3000);
	  
	//negitive Scenario with existing user
	  text=invalidTestData("gvijaysankar", userdetails.get("emailID"), userdetails.get("password"), "", "existinguser");
	  if(text.equalsIgnoreCase("A user with this email address already exists."))
	   {
			LOGGER.info("PASS::Application displaying errror message correctly when user trying to create user with existing user email");
	   }
	  
	  driver.navigate().refresh();
	  Thread.sleep(3000);
	  
	  //enter only user name and email, check Create button is disabled
	  text=missingData("gvijaysankar", userdetails.get("emailID"), "");
	  if(text.equalsIgnoreCase("button disabled"))
	   {
			LOGGER.info("PASS::Create button is disabled");
	   }
	  else
	  {
		  LOGGER.info("FAIL::Create button is enabled");
	  }
	  
	  driver.navigate().refresh();
	  Thread.sleep(3000);
}
}



