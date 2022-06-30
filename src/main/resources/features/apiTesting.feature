Feature: Pet Store Api tests

  @api_test @delete_all_pets
  Scenario: Pet
    When the user creates a new pet with id: 12345
#      | categoryName | petName | status    | photoUrls          |
#      | small        | chintu  | available | https//:chintu.com |
    Then verify the pet was created with correct data
    When the user updates the pet name to Dolphin
    Then verify the pet name is updated to Dolphin
    When the user deletes the pet with id: 12345
    Then assert the pet has been deleted