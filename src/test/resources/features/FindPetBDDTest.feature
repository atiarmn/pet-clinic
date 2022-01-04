Feature: Find Pet Feature

  Scenario: Find an existing pet by ID
    Given There are some pets with specific IDs
    When We want to find a pet with ID = 5
    Then The pet with ID = 5 will be found and returned successfully
