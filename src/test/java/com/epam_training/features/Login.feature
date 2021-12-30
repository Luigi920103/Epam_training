Feature: Login

  Background:
    Given the user navigates to the base page
    And the user selects My Account button

  @RegularTest @Login
  Scenario: L001 Masked Password
    Then password field must has attribute type equal to password

  @RegularTest @Login
  Scenario: L002 Login successful
    When it log in with the correct credentials
    Then it must successfully log in to the web page

  @RegularTest @Login
  Scenario Outline: L003 Login unsuccessful "<case>"
    When it log in with the credentials "<user>", "<password>"
    Then the webpage must show "<error_Message>"
    Examples:
      | case                    | user          | password          | error_Message                             |
      | Incorrect credentials   | incorrectUser | incorrectPassword | Invalid username                          |
      | Empty password          | Correct       |                   | Password is required                      |
      | Empty user              |               | Correct           | Username is required                      |
      | Empty user and password |               |                   | Username is required                      |
      | Handles case sensitive  | Correct       | ePAMtRAINING2021  | The password you entered for the username |
