Feature: Login to Mintable Application

  Scenario: Validation for broken images in stores page
    Given user is in store page
    When user successfully navigated to the page
    Then validate for broken image

  Scenario: Search for BoardApe
    Given user is in store page
    When user type as 'bored ape'
    Then validate 'BoredApeYachtClub' is visible

  Scenario: Successful login
    Given user is in store page
    When user type as 'muaazster@gmail.com' in username
    And user type as 'Mintable@1' in password
    Then system allows user to login
