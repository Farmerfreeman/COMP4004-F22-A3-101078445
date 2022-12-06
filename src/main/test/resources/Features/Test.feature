Feature: Testing Selenium Functionality
  Scenario: Testing Browser
    Given Open browser and application

    And WAIT FOR SERVER

    And Page is displayed

    And WAIT FOR SERVER

    When User connects
    
    And WAIT FOR SERVER
    
    Then Server should respond with "Server: You are now connected with id"
    And Server should respond with "Server: The game has started! Please wait your turn."
