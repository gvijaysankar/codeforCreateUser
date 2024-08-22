seleniumCode for Creating User, 
Create an autotest that creates an account with a username having 2 characters.
I have asked to create a user with 2 characters.
Details -
I have parameterized the user length, and I have mentioned the global variable for user length, create user works successfully for any length based on the declaration for the global variable unless the application accepts the User length.
Framework - Technologies used Java, Selenium Web Driver, Test NG, Page Factory, Page Objects
In this Framework, I have used 2 classes, one class is the main class and the other class is the page object class.
In the main class, the below functions are there-
1. Browser related function to instantiate the browser and Launch the browser
2. Random function to create user, password, and email based on the length
3. Test function, to initialize the testmainwebdriver1.java and call the functions to create a user and to perform negative scenarios
4. in case creating a user is not successful, written the function to take a screenshot in a new folder(created based on the time stamp)  on the desktop
5. after validations, quit the driver

In the Page Object class(class 2), the below functions are there-
1. constructor initialize the Page Factory, Page Factory is  a class that initializes all the web elements defined with annotation @FindBy in the class
2. All other functions related to creating users, and covering negative scenarios when the user enters invalid data, existing user, and does not enter the mandatory fields
