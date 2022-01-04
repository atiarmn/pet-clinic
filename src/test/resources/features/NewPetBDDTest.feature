Feature: New Pet Feature

  Scenario: Adding a pet to the pets of on owner
    Given There is an owner named "testOwner"
    When He wants to have another pet
    Then A new pet will be added to his pet collection successfully
