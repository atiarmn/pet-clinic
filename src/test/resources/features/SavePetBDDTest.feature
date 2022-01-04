Feature: Save Pet Feature

  Scenario: Saving a pet in the pets of an owner
    Given There is an owner named "testOwner" and a pet named "testPet"
    When The owner wants to take "testPet" for himself
    Then "testPet" will be added to his pet collection successfully
