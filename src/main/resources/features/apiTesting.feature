Feature: Pet Store Api tests

  @api_test @delete_all_pets
  Scenario: Pet
    When the petstore endpoint is called
#      | categoryName | petName | status    | photoUrls          |
#      | small        | chintu  | available | https//:chintu.com |
    Then verify pet was created with correct data
    When update the pet name to Dolphin
    Then verify the pet name has updated to Dolphin