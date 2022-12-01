Feature: Testing Selenium Functionality
  Scenario: Testing Browser
    Given Open browser and application
    And Page is displayed
    When User enters "User" in field "username"
    And User submits form "connect"
    And User enters "Hello, Server!" in field "message"
    And User submits form "send"
    And WAIT FOR SERVER
    Then Server should respond with "User: Hello, Server!"
