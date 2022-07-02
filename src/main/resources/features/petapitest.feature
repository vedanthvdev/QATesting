Feature: Pet Store Api tests

#  @delete_all_pets tag can be used to delete all the pets used in the scenario(At the end of the test)
  @api_test @delete_all_pets
  Scenario Outline: Pet
    When the user creates a new pet
      | id    | name          | status    |
      | 22222 | black panther | available |
    Then verify the pet was created with correct data
    When the user updates the pet name to <updateName>
    Then verify the pet name is updated to <updateName>
    When the user deletes the pet
    Then assert the pet has been deleted

    Examples:
    | updateName |
    | Thanos     |