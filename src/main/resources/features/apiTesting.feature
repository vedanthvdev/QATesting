Feature: Pet Store Api tests

  @run_all @api_test
  Scenario: Pet
    When the petstore endpoint is called
#      | categoryName | petName | status    | photoUrls          |
#      | small        | chintu  | available | https//:chintu.com |
    Then verify pet was created with correct data
#    Then update the pet name and verify the update