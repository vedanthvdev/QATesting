package automation.stepdefinition;

import automation.petservice.pet.Pet;
import automation.petservice.pet.PetClientService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class PetStoreApiSteps {
    private Pet pet;
    private final PetClientService petClientService;
    public PetStoreApiSteps(PetClientService petClientService) {
        this.petClientService = petClientService;
    }

    @When("the petstore endpoint is called")
    public void accountSpetstoreionEndpointIsCalled() {
        pet = Pet.builder().build();

        petClientService.createPetRequest(pet);

    }

    @Then("verify pet was created with correct data")
    public void verifyPetWasCreatedWithCorrectData() {
        assertThat(petClientService.getPetId(pet))
                .as("Cannot find pet with id: " + pet.getId())
                .isEqualTo(pet.getId());
        assertThat(petClientService.getPetName(pet))
                .as("Cannot find pet with name: "+ pet.getName())
                .isEqualTo(pet.getName());
    }

    @When("update the pet name to {word}")
    public void updateThePetNameAndVerify(String name) {
        pet = Pet.builder().name(name).build();
        petClientService.updatePetRequest(pet);
    }

    @Then("verify the pet name has updated to {word}")
    public void verifyThePetNameHasUpdatedToDolphin(String name) {
        assertThat(petClientService.getPetName(pet))
                .as("Cannot find pet with name: "+ pet.getName())
                .isEqualTo(name);
    }
}
