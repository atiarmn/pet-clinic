Feature: Find Owner Feature

  Scenario: Find an existing owner by ID
    Given There are some owners with specific IDs
    When We want to find an owner with ID = 3
    Then The owner with ID = 3 will be found and returned successfully
