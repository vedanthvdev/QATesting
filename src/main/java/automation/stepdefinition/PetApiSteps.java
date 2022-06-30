package automation.stepdefinition;

import automation.petapi.Pet;
import automation.petapi.PetClientService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class PetApiSteps {
    private Pet pet;
    private final PetClientService petClientService;
    public PetApiSteps(PetClientService petClientService) {
        this.petClientService = petClientService;
    }

    @When("the user creates a new pet with id: {int}")
    public void accountSpetstoreionEndpointIsCalled(int id) {
        pet = Pet.builder()
                .id(id)
                .build();

        petClientService.createPetRequest(pet);

    }

    @Then("verify the pet was created with correct data")
    public void verifyPetWasCreatedWithCorrectData() {
        assertThat(petClientService.getPetId(pet))
                .as("Cannot find pet with id: " + pet.getId())
                .isEqualTo(pet.getId());
        assertThat(petClientService.getPetName(pet))
                .as("Cannot find pet with name: "+ pet.getName())
                .isEqualTo(pet.getName());
    }

    @When("the user updates the pet name to {word}")
    public void updateThePetNameAndVerify(String name) {
        pet = Pet.builder().name(name).build();
        petClientService.updatePetRequest(pet);
    }

    @Then("verify the pet name is updated to {word}")
    public void verifyThePetNameHasUpdatedToDolphin(String name) {
        assertThat(petClientService.getPetName(pet))
                .as("Cannot find pet with name: "+ pet.getName())
                .isEqualTo(name);
    }

    @When("the user deletes the pet with id: {int}")
    public void theUserDeletesThePet(int id) {
        petClientService.deleteAllPets(id);
    }

    @Then("assert the pet has been deleted")
    public void assertThePetHasBeenDeleted() {
        petClientService.assertPetPresent(pet.getId());
    }
}
