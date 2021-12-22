Feature: Login
  Background:
    Given the user navigates to "http://practice.automationtesting.in/"
    And the user selects "My Account" button

  @RegularTest
  Scenario Outline: Masked Password
    Then password field must has attribute type equal to password

  Scenario Outline: Login successful
    When it log in with the credentials <user>, <password>
    Then it must successfully log in to the web page
    Examples:
      |user					                    |password		    |
      |"bouquauraufroiku-4172@yopmail.com"		|"EpamTraining2021*"|

  Scenario Outline: Login unsuccessful
    When it log in with the credentials <user>, <password>
    Then the webpage must show <error_Message>
    Examples:
      |user					              |password				  |error_Message				    |
      |"incorrectUser"		              |"incorrectPassword"	  |"Invalid username"			    |
      |"bouquauraufroiku-4172@yopmail.com"|""					  |"Password is required"			|
      |""					              |"EpamTraining2021*"	  |"Username is required"			|
      |"bouquauraufroiku-4172@yopmail.com"|"ePAMtRAINING2021"     |"The password you entered for the username"	|

