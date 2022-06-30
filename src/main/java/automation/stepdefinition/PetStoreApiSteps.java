package automation.stepdefinition;

import automation.petservice.pet.Pet;
import automation.petservice.pet.PetClientService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PetStoreApiSteps {
    private Pet pet;
    private final PetClientService petClientService;
    public PetStoreApiSteps(PetClientService petClientService) {

        this.petClientService = petClientService;
    }

    @When("the petstore endpoint is called")
    public void accountSpetstoreionEndpointIsCalled() {

        pet =  new Pet.Builder()
//                .withId(11111)
//                .withCategoryName(new CategoryName.Builder()
//                        .withId(0)
//                        .withName("CategoryName")
//                        .build())
//                .withPetName("PetName")
////                .withPhotoUrls(a)
//                .withTagName(ImmutableList.of(new TagName.Builder()
//                        .withId(0)
//                        .withName("TagName")
//                        .build()))
//                .withStatus("Status")
                .build();

        petClientService.createPetRequest(pet);

    }

    @Then("verify pet was created with correct data")
    public void verifyPetWasCreatedWithCorrectData() {
        assertThat(petClientService.getPetId(pet))
                .as("Cannot find pet with id: " + pet.getId())
                .isEqualTo(pet.getId());
        assertThat(petClientService.getPetName(pet))
                .as("Cannot find pet with name: "+ pet.getPetName())
                .isEqualTo(pet.getPetName());
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
