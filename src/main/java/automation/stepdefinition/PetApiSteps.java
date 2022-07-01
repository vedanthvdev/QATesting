package automation.stepdefinition;

import automation.context.ContextManager;
import automation.core.framework.BaseStepDef;
import automation.petapi.Pet;
import automation.petapi.PetClientService;
import automation.petapi.PetServiceContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class PetApiSteps extends BaseStepDef {
    private final PetClientService petClientService;
    public PetApiSteps(PetClientService petClientService, ContextManager contextManager) {
        super(contextManager);
        this.petClientService = petClientService;
    }

    @When("the user creates a new pet with id: {int}")
    public void accountSpetstoreionEndpointIsCalled(int id) {
        getContextManager().setContext(PetServiceContext.class, new PetServiceContext());
        PetServiceContext petServiceContext = getContextManager().getOrCreateContext(PetServiceContext.class, PetServiceContext::new);
        petServiceContext.setPet(Pet.builder()
                                    .id(id)
                                    .build());

        petClientService.createPetRequest();
        petClientService.petIdToDeleteAfterTest();
    }

    @Then("verify the pet was created with correct data")
    public void verifyPetWasCreatedWithCorrectData() {
        PetServiceContext petServiceContext = getContextManager().getRequiredContext(PetServiceContext.class);
        assertThat(petClientService.getPetId())
                .as("Cannot find pet with id: " + petServiceContext.getPet().getId())
                .isEqualTo(petServiceContext.getPet().getId());
        assertThat(petClientService.getPetName())
                .as("Cannot find pet with name: "+ petServiceContext.getPet().getName())
                .isEqualTo(petServiceContext.getPet().getName());
        petClientService.petIdToDeleteAfterTest();

    }

    @When("the user updates the pet name to {word}")
    public void updateThePetNameAndVerify(String name) {
        PetServiceContext petServiceContext = getContextManager().getRequiredContext(PetServiceContext.class);
        petServiceContext.getPet().setName(name);
        petClientService.updatePetRequest();
    }

    @Then("verify the pet name is updated to {word}")
    public void verifyThePetNameHasUpdatedToDolphin(String name) {
        PetServiceContext petServiceContext = getContextManager().getRequiredContext(PetServiceContext.class);
        assertThat(petClientService.getPetName())
                .as("Cannot find pet with name: "+ petServiceContext.getPet().getName())
                .isEqualTo(name);
    }

    @When("the user deletes the pet with id: {int}")
    public void theUserDeletesThePet(int id) {
        petClientService.deletePet();
    }

    @Then("assert the pet has been deleted")
    public void assertThePetHasBeenDeleted() {
        petClientService.assertPetDeleted();
    }
}
