package automation.stepdefinition;

import automation.petservice.pet.Pet;
import automation.petservice.pet.PetClientService;
import automation.petservice.pet.submodules.CategoryName;
import automation.petservice.pet.submodules.TagName;
import com.google.common.collect.ImmutableList;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PetStoreApiSteps {
    private Pet createPet;
    private final PetClientService petClientService;
    public PetStoreApiSteps(PetClientService petClientService) {

        this.petClientService = petClientService;
    }

    @When("the petstore endpoint is called")
    public void accountSpetstoreionEndpointIsCalled() {
        String[] a = {"photoURL"};

        createPet =  new Pet.Builder()
                .withId(11111)
                .withCategoryName(new CategoryName.Builder()
                        .withId(0)
                        .withName("CategoryName")
                        .build())
                .withPetName("PetName")
                .withPhotoUrls(a)
                .withTagName(ImmutableList.of(new TagName.Builder()
                        .withId(0)
                        .withName("TagName")
                        .build()))
                .withStatus("Status")
                .build();

        petClientService.createPetRequest(createPet);

    }

    @Then("verify pet was created with correct data")
    public void verifyPetWasCreatedWithCorrectData() {
        assertThat(petClientService.getPetId(createPet))
                .as("Cannot find pet with id: " + createPet.getId())
                .isEqualTo(createPet.getId());
        assertThat(petClientService.getPetName(createPet))
                .as("Cannot find pet with name: "+ createPet.getPetName())
                .isEqualTo(createPet.getPetName());
    }

    @Then("update the pet name and verify the update")
    public void updateThePetNameAndVerify() {
        Response response = RestAssured
                .given()
//                .log().all()
                .header("Content-Type", "application/json")
                .get("https://petstore.swagger.io/v2/pet/{petId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();

        response
                .then()
                .assertThat()
                .body("name", equalTo("PetName"))
                .body("status", equalTo("Status"));
    }

}
