Feature: Login to HRM

  Scenario: Login with valid credentials
    Given user is home page
    When user enter admin as username
    And user enter admin123 as password
    Then user should be login
