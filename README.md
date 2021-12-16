# Epam Training - practice.automationtesting
Base framework to be used in the exercise delivered for Epam Training

#Prerequisites
To be able to run this project locally you will need to have the following apps installed.
* Java / SDK 8 or higher
* maven
* intelliJ with cucumber plugins (optional)

# Run the Test cases
The framework includes the test case for the despegar page for the challenge
In order to see the script in execution you need to run the file `src\test\java\com\Epam_training\features\Login.feature` or the runner `src\test\java\Runner.java`

Also, you can execute it through maven for do this you have to execute the following code in the terminal located on the root folder of the project
* mvn clean test or 
* mvn verify

# Also, keep in mind the content of the variables in this Cucumber.feature
(*`It applies to all`*) 

Name of user
* name="user" value="bouquauraufroiku-4172@yopmail.com"

the error that is shown to the user if provides the incorrect credentials
*  name="error_Message" value="Invalid username*"


## Using the IDE plugin
Right-click the feature file and select the option "Run" or you can run from the Runner file Right-click over it and select the option "Run"